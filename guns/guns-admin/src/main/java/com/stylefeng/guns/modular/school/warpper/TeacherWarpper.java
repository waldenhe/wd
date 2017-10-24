package com.stylefeng.guns.modular.school.warpper;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.Recorddata;
import com.stylefeng.guns.common.persistence.model.Teacherdata;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.school.dto.TeacherDto;
import com.stylefeng.guns.modular.school.service.IRecordService;

public class TeacherWarpper extends BaseControllerWarpper {

    private TeacherDto dto;

    private IRecordService recordService;

    public TeacherWarpper(List<Teacherdata> obj) {
        super(obj);
    }

    protected void warpTheMap(Teacherdata map) {
        if (StringUtils.isNotBlank(map.getSex())) {
            map.setSex(ConstantFactory.me().findInDictById(Integer.parseInt(map.getSex())).getName());
        }
        /*if (StringUtils.isNotBlank(map.getDuty())) {
            map.setDuty(ConstantFactory.me().findInDictById(Integer.parseInt(map.getDuty())).getName());
        }
        if (StringUtils.isNotBlank(map.getDepartment())) {
            map.setDepartment(ConstantFactory.me().findInDictById(Integer.parseInt(map.getDepartment())).getName());
        }*/

        if (1 == map.getIdType()) {
            map.setOther5("普通职工");
            map.setOther6("1次");
            Wrapper<Recorddata> wrapper  = new EntityWrapper<>();
            wrapper.and("cardId={0}", map.getCardId());
            wrapper.gt("createTime", DateUtil.getDay().concat(" 00:00:00")).and().lt("createTime", DateUtil.getDay().concat(" 23:59:59"));
            //当日剩余
            int today = recordService.selectCount(wrapper);
            map.setOther7(String.format("%s次", map.getIdType()-today));
        } else if (-1 == map.getIdType()) {
            map.setOther5("特殊职工");
            map.setOther6("无限次");
            map.setOther7("无限次");
        } else if (map.getIdType() > 1) {
            map.setOther5("临时职工");
            map.setOther6(String.format("%s次", map.getIdType()));
            Wrapper<Recorddata> wrapper  = new EntityWrapper<>();
            wrapper.and("cardId={0}", map.getCardId());
            wrapper.gt("createTime", DateUtil.getDay().concat(" 00:00:00")).and().lt("createTime", DateUtil.getDay().concat(" 23:59:59"));
            //当日剩余
            int today = recordService.selectCount(wrapper);
            map.setOther7(String.format("%s次", map.getIdType()-today));
        } else {
            map.setOther6("--");
            map.setOther5("类型异常");
            map.setOther7("--");
        }
        Wrapper<Recorddata> wrapper = new EntityWrapper<>();
        wrapper.and("cardId={0}", map.getCardId());
        wrapper.gt(StringUtils.isNotBlank(dto.getStartTime()), "createTime", dto.getStartTime());
        wrapper.lt(StringUtils.isNotBlank(dto.getStartTime()), "createTime", dto.getStartTime());
        //累计条件内总刷卡时间
        int total = recordService.selectCount(wrapper);
        map.setOther8(String.format("%s次", total));
    }

    public List<Teacherdata> warp(TeacherDto dto,IRecordService recordService) {
        this.dto = dto;
        this.recordService = recordService;
        
        @SuppressWarnings("unchecked")
        List<Teacherdata> list = (List<Teacherdata>) this.obj;
        for (Teacherdata teacherdata : list) {
            warpTheMap(teacherdata);
        }
        return list;
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        // TODO 重写
    }

}
