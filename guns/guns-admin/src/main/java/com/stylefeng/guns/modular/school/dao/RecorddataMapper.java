package com.stylefeng.guns.modular.school.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.stylefeng.guns.common.persistence.model.Recorddata;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author walden
 * @since 2017-10-08
 */
public interface RecorddataMapper extends BaseMapper<Recorddata> {
    public List<Recorddata>  selectPageByMy(Pagination page,@Param("ew") Wrapper<Recorddata> wrapper);
}