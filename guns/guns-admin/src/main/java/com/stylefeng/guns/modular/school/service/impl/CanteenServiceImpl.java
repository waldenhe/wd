package com.stylefeng.guns.modular.school.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.DSEnum;
import com.stylefeng.guns.common.persistence.model.Canteen;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import com.stylefeng.guns.modular.school.dao.CanteenMapper;
import com.stylefeng.guns.modular.school.service.ICanteenService;

/**
 * 食堂信息Service
 *
 * @author walden
 * @Date 2017-10-22 16:45:32
 */
@Service
@Transactional
public class CanteenServiceImpl extends ServiceImpl<CanteenMapper, Canteen> implements ICanteenService {
	
	@DataSource(name = DSEnum.DATA_SOURCE_BIZ)
	@Override
	public Page<Canteen> selectPage(Page<Canteen> page, Wrapper<Canteen> wrapper) {
		return super.selectPage(page, wrapper);
	}
	@DataSource(name = DSEnum.DATA_SOURCE_BIZ)
	@Override
	public List<Canteen> selectList(Wrapper<Canteen> wrapper) {
	    // TODO Auto-generated method stub
	    return super.selectList(wrapper);
	}
	
}
