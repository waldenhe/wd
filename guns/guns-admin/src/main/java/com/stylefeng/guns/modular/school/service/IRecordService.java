package com.stylefeng.guns.modular.school.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.model.Recorddata;
import com.stylefeng.guns.modular.school.response.RecordResponse;

/**
 * 打卡记录Service
 *
 * @author walden
 * @Date 2017-10-08 15:14:55
 */
public interface IRecordService extends IService<Recorddata>{

    /**
     * 刷卡
     * @param cardId 身份证号
     * @return
     */
    public RecordResponse record(String cardId) throws BussinessException;
    
    /**
     * 初始化食堂信息
     */
    public void initCanteen();
    
    /**
     * 自定义分页查询
    * @Title: selectPageByMy  
    * @Description: TODO(这里用一句话描述这个方法的作用)  
    * @param @param page
    * @param @param wrapper
    * @param @return    设定文件  
    * @return Page<Recorddata>    返回类型  
    * @throws
     */
    public Page<Recorddata> selectPageByMy(Page<Recorddata> page, Wrapper<Recorddata> wrapper);
    
}
