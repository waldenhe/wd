package com.stylefeng.guns.modular.school.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.DSEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.model.Teacherdata;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import com.stylefeng.guns.modular.school.dao.TeacherdataMapper;
import com.stylefeng.guns.modular.school.dto.TeacherDto;
import com.stylefeng.guns.modular.school.service.ITeacherService;
import com.xiaoleilu.hutool.util.IdcardUtil;

/**
 * 教师Service
 *
 * @author walden
 * @Date 2017-10-08 15:14:40
 */
@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherdataMapper, Teacherdata> implements ITeacherService {
    @Resource
    TeacherdataMapper teacherdataMapper;

    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public Integer addTeacher(TeacherDto teacherDto) {
        Teacherdata dTeacherdata = new Teacherdata();
        BeanUtils.copyProperties(teacherDto, dTeacherdata);
        return teacherdataMapper.insert(dTeacherdata);
    }
    
    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public boolean validateFieldOnly(String field, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(field, value);
        List<Teacherdata> result= teacherdataMapper.selectByMap(map);
        if (CollectionUtils.isNotEmpty(result)) {
            return false;
        }
        return true;

    }
    
    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public boolean deleteById(Serializable id) {
        // TODO Auto-generated method stub
        return super.deleteById(id);
    }

    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public Page<Teacherdata> selectPage(Page<Teacherdata> page, Wrapper<Teacherdata> wrapper) {
        return super.selectPage(page, wrapper);
    }
    
    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public Teacherdata selectById(Serializable id) {
        // TODO Auto-generated method stub
        return super.selectById(id);
    }
    
    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    @Override
    public Integer updateTeacher(TeacherDto teacherDto) {
        Teacherdata dTeacherdata = new Teacherdata();
        BeanUtils.copyProperties(teacherDto, dTeacherdata);
        return teacherdataMapper.updateById(dTeacherdata);
    }
    
    public enum SEX{
    	man("30",1,"男"),woman("31",0,"女"),unknow("-2",0,"未知");
    	private SEX(String db_value,int value,String des) {
			this.db_value = db_value;
			this.value = value;
			this.des = des;
		}
    	
    	public static String getDbValue(int value) {
    		for (SEX sex : SEX.values()) {
				if(sex.value ==value) {
					return sex.db_value;
				}
			}
    		return unknow.db_value;
    	}
    	
    	String db_value;
    	int value;
    	String des;
    }
    
    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
	@Override
	public Integer addBatchTeacher(List<List<Object>> list)  throws BussinessException{
    	list.remove(0);
		for (List<Object> object : list) {
			TeacherDto teacherDto = new TeacherDto();
			
			teacherDto.setTeachername(object.get(0).toString());
			teacherDto.setTeachernum(Integer.parseInt(object.get(1).toString()));
			teacherDto.setDepartment(object.get(2).toString());
			teacherDto.setDuty(object.get(3).toString());
			teacherDto.setCardId(object.get(4).toString());
			teacherDto.setIdType(Integer.parseInt(object.get(5).toString()));
			
			if (!validateFieldOnly("teachernum", teacherDto.getTeachernum().toString())) {
				throw new BussinessException(400,String.format("教工号[%s]重复,请重新输入", teacherDto.getTeachernum().toString()) , null);
			}

			if (!validateFieldOnly("cardId", teacherDto.getCardId().toString())) {
				throw new BussinessException(400, String.format("教工身份证号[%s]重复,请重新输入", teacherDto.getCardId().toString()), null);
			}

			if (!IdcardUtil.isValidCard(teacherDto.getCardId())) {
				throw new BussinessException(400, String.format("教工身份证号[%s]格式不正确,请重新输入", teacherDto.getCardId().toString()), null);
			}
			teacherDto.setSex(SEX.getDbValue(IdcardUtil.getGenderByIdCard(teacherDto.getCardId())));
			addTeacher(teacherDto);
		}
		return null;
	}
}
