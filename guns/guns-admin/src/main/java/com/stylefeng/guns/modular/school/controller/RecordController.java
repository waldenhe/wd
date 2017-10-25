package com.stylefeng.guns.modular.school.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.Recorddata;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.school.service.IRecordService;

/**
 * 打卡记录控制器
 *
 * @author walden
 * @Date 2017-10-08 15:14:55
 */
@Controller
@RequestMapping("/school/record")
public class RecordController extends BaseController {

    private String PREFIX = "/school/record/";
    @Autowired
    IRecordService recordService;

    /**
     * 跳转到打卡记录首页
     */
    @RequestMapping("")
    public String index() {
    	recordService.initCanteen();
        return PREFIX + "record.html";
    }

    /**
     * 跳转到添加打卡记录
     */
    @RequestMapping("/record_add")
    public String recordAdd() {
        return PREFIX + "record_add.html";
    }

    /**
     * 跳转到修改打卡记录
     */
    @RequestMapping("/record_update/{recordId}")
    public String recordUpdate(@PathVariable Integer recordId, Model model) {
        return PREFIX + "record_edit.html";
    }

    /**
     * 获取打卡记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String cardId,@RequestParam(required = false) String idType,@RequestParam(required = false) String mac, @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {

        Page<Recorddata> page = new PageFactory<Recorddata>().defaultPage();
        Wrapper<Recorddata> wrapper = new EntityWrapper<Recorddata>();
        wrapper.where("1={0}", 1).and(StringUtils.isNotBlank(cardId), "t1.cardId={0}", cardId)
        .and(StringUtils.isNotEmpty(mac),"t1.other2={0}",mac)
        .gt(StringUtils.isNotBlank(startTime), "t1.createTime", startTime)
        .lt(StringUtils.isNotBlank(endTime), "t1.createTime", endTime).orderBy("t1.id desc");
        if (StringUtils.isNotBlank(idType)) {
            if ("1".equals(idType) || "-1".equals(idType)) {
                wrapper.and("t3.idType={0}", idType);
            } else {
                wrapper.gt("t3.idType", 1);
            }
        }
        recordService.selectPageByMy(page, wrapper);
        return super.packForBT(page);
    }

    /**
     * 新增打卡记录(模拟)
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestParam(required = true) String cardId) {
        return recordService.record(cardId);
    }

    /**
     * 删除打卡记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer recordId) {
    	recordService.deleteById(recordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改打卡记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 打卡记录详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
