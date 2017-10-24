package com.stylefeng.guns.modular.school.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.model.Teacherdata;
import com.stylefeng.guns.modular.school.dto.TeacherDto;

/**
 * 教师Service
 *
 * @author walden
 * @Date 2017-10-08 15:14:40
 */
public interface ITeacherService extends IService<Teacherdata>{
    /**
     * 新增教职工
     * @param teacherDto
     * @return
     */
    public Integer addTeacher(TeacherDto teacherDto);
    
    /**
     * 新增教职工
     * @param teacherDto
     * @return
     */
    public Integer addBatchTeacher(List<List<Object>> teacherDto) throws BussinessException;
    
    /**
     * 更新教职工
     * @param teacherDto
     * @return
     */
    public Integer updateTeacher(TeacherDto teacherDto);
    
    /**
     * 验证字段值是否唯一
     * @param field
     * @param value
     * @return
     */
    public boolean validateFieldOnly(String field,String value);
    
}
