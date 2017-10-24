package com.stylefeng.guns.modular.school.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.DSEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.model.Canteen;
import com.stylefeng.guns.common.persistence.model.Recorddata;
import com.stylefeng.guns.common.persistence.model.Teacherdata;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.school.dao.CanteenMapper;
import com.stylefeng.guns.modular.school.dao.RecorddataMapper;
import com.stylefeng.guns.modular.school.response.RecordResponse;
import com.stylefeng.guns.modular.school.service.IRecordService;
import com.stylefeng.guns.modular.school.service.ITeacherService;
import com.xiaoleilu.hutool.util.NetUtil;

/**
 * 打卡记录Service
 *
 * @author fengshuonan
 * @Date 2017-10-08 15:14:55
 */
@Service
@Transactional
public class RecordServiceImpl extends ServiceImpl<RecorddataMapper, Recorddata> implements IRecordService {

    @Autowired
    ITeacherService teacherService;
    
    @Autowired
    RecorddataMapper recorddataMapper;
    
    @Autowired
    CanteenMapper canteenMapper;

    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public int selectCount(Wrapper<Recorddata> wrapper) {
        // TODO Auto-generated method stub
        return super.selectCount(wrapper);
    }

    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public Page<Recorddata> selectPage(Page<Recorddata> page, Wrapper<Recorddata> wrapper) {
        return super.selectPage(page, wrapper);
    }

    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public Page<Recorddata> selectPageByMy(Page<Recorddata> page, Wrapper<Recorddata> wrapper) {
        page.setRecords(recorddataMapper.selectPageByMy(page, wrapper));
        return page;
    }

    @Override
    public Page<Recorddata> selectPage(Page<Recorddata> page) {
        // TODO Auto-generated method stub
        return super.selectPage(page);
    }

    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public RecordResponse record(String cardId) {

        RecordResponse response = new RecordResponse();

        if (StringUtils.isBlank(cardId)) {
            throw new BussinessException(400, "刷卡失败，参数异常", null);
        }
        Wrapper<Teacherdata> wrapper = new EntityWrapper<Teacherdata>();
        wrapper.and("cardId={0}", cardId);
        List<Teacherdata> teacherdatas = teacherService.selectList(wrapper);

        if (CollectionUtils.isEmpty(teacherdatas)) {
            throw new BussinessException(500, "刷卡失败，非教师身份", null);
        }

        if (teacherdatas.size() > 1) {
            throw new BussinessException(500, "刷卡失败，信息有误，存在多个相同信息", null);
        }
        Teacherdata teacherdata = teacherdatas.get(0);

        BeanUtils.copyProperties(teacherdata, response);

        int idType = teacherdata.getIdType();

        Wrapper<Recorddata> rWrapper = new EntityWrapper<>();
        rWrapper.and("cardId={0}", cardId);
        rWrapper.gt("createTime", DateUtil.getDay().concat(" 00:00:00")).and().lt("createTime",
                DateUtil.getDay().concat(" 23:59:59"));
        // 当日已刷次数
        int today = selectCount(rWrapper);

        Recorddata recorddata = new Recorddata();
        recorddata.setCardId(cardId);
        recorddata.setCreateTime(DateUtil.getTime());
        String mac = null;
        if (StringUtils.isEmpty(gunsProperties.getCanteenMac())) {
            mac = NetUtil.getLocalMacAddress();
        } else {
            mac = gunsProperties.getCanteenMac();
        }
        recorddata.setOther2(mac);

        if (1 == idType) {// 普通教师
            if (today > 0) {// 以刷卡
                throw new BussinessException(500, "刷卡失败，已超限", null);
            } else {
                insert(recorddata);
                response.setMessage("刷卡成功,剩余0次");
            }
        } else if (idType > 1) {// 临时教师
            if (today >= idType) {
                throw new BussinessException(500, "刷卡失败，已超限", null);
            } else {
                insert(recorddata);
                response.setMessage(String.format("刷卡成功,剩余%s次", idType - today - 1));
            }
        } else if (-1 == idType) {// 特殊教师
            insert(recorddata);
            response.setMessage(String.format("刷卡成功,已刷%s次", today + 1));
        } else {// 非教师身份
            throw new BussinessException(500, "刷卡失败，非教师身份", null);
        }
        return response;
    }

    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public boolean deleteById(Serializable id) {
        // TODO Auto-generated method stub
        return super.deleteById(id);
    }

    @Autowired
    GunsProperties gunsProperties;

    @Override
    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    public void initCanteen() {
        String mac = null;
        if (StringUtils.isEmpty(gunsProperties.getCanteenMac())) {
            mac = NetUtil.getLocalMacAddress();
        } else {
            mac = gunsProperties.getCanteenMac();
        }
        Wrapper<Canteen> waWrapper = new EntityWrapper<>();
        waWrapper.and(StringUtils.isNotBlank(mac), "macaddr={0}", mac);
        Integer count = canteenMapper.selectCount(waWrapper);
        if (count == null || 0 == count.intValue()) {
            Canteen canteen = new Canteen();
            canteen.setMacaddr(mac.toUpperCase());
            canteen.setAddr(gunsProperties.getCanteenAddr());
            canteen.setCreatetime(DateUtil.getTime());
            canteen.setName(gunsProperties.getCanteenName());
            canteenMapper.insert(canteen);
        }
    }
}
