package com.stylefeng.guns.modular.school.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.model.Canteen;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.school.service.ICanteenService;

/**
 * 食堂信息控制器
 *
 * @author walden
 * @Date 2017-10-22 16:45:32
 */
@Controller
@RequestMapping("/school/canteen")
public class CanteenController extends BaseController {

	private String PREFIX = "/school/canteen/";
	@Autowired
	ICanteenService canteenService;

	/**
	 * 跳转到食堂信息首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "canteen.html";
	}

	/**
	 * 跳转到添加食堂信息
	 */
	@RequestMapping("/canteen_add")
	public String canteenAdd() {
		return PREFIX + "canteen_add.html";
	}

	/**
	 * 跳转到修改食堂信息
	 */
	@RequestMapping("/canteen_update/{canteenId}")
	public String canteenUpdate(@PathVariable Integer canteenId, Model model) {
	    Canteen canteen = canteenService.selectById(canteenId);
        model.addAttribute("canteen", canteen);
		return PREFIX + "canteen_edit.html";
	}

	/**
	 * 获取食堂信息列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@RequestParam(required = false) String mac,@RequestParam(required = false) String name, @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
		Page<Canteen> page = new PageFactory<Canteen>().defaultPage();
		Wrapper<Canteen> wrapper = new EntityWrapper<>();
		
		wrapper.and(StringUtils.isNotEmpty(mac), "macaddr={0}", mac);
		wrapper.like(StringUtils.isNotEmpty(name), "name", name);
        wrapper.gt(StringUtils.isNotBlank(startTime), "createTime", startTime);
        wrapper.lt(StringUtils.isNotBlank(endTime), "createTime", endTime).orderBy("id desc");
        
		canteenService.selectPage(page, wrapper);
		return super.packForBT(page);
	}
	
	   /**
     * 获取食堂信息列表
     */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public Object listAll() {
        return canteenService.selectList(null);
    }

	/**
	 * 新增食堂信息
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(@Valid Canteen canteen,BindingResult result) {
	    if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
	    canteen.setCreatetime(DateUtil.getTime());
	    canteenService.insert(canteen);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除食堂信息
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete() {
		return SUCCESS_TIP;
	}

	/**
	 * 修改食堂信息
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(@Valid Canteen canteen, BindingResult result) {
	    if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
	    canteenService.updateById(canteen);
		return super.SUCCESS_TIP;
	}

	/**
	 * 食堂信息详情
	 */
	@RequestMapping(value = "/detail")
	@ResponseBody
	public Object detail() {
		return null;
	}
}
