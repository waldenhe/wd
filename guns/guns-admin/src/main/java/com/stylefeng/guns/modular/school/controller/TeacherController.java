package com.stylefeng.guns.modular.school.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.model.Teacherdata;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.modular.school.dto.TeacherDto;
import com.stylefeng.guns.modular.school.request.FileBucket;
import com.stylefeng.guns.modular.school.service.IRecordService;
import com.stylefeng.guns.modular.school.service.ITeacherService;
import com.stylefeng.guns.modular.school.warpper.TeacherWarpper;
import com.stylefeng.guns.modular.system.service.IDictService;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.io.file.FileReader;
import com.xiaoleilu.hutool.poi.excel.ExcelReader;
import com.xiaoleilu.hutool.poi.excel.ExcelUtil;
import com.xiaoleilu.hutool.util.IdcardUtil;

/**
 * 教师控制器
 *
 * @author walden
 * @Date 2017-10-08 15:14:40
 */
@Controller
@RequestMapping("/school/teacher")
public class TeacherController extends BaseController {

	private String PREFIX = "/school/teacher/";

	@Autowired
	GunsProperties gunsProperties;

	@Autowired
	IDictService dictService;
	@Autowired
	ITeacherService teacherService;
	@Autowired
	IRecordService recordService;
	
	@RequestMapping(value = "/downloadModel", method = RequestMethod.GET)
	public void downloadModel(HttpServletResponse response) throws IOException {
		 	OutputStream os = response.getOutputStream();// 取得输出流
	        response.reset();// 清空输出流
	        response.setHeader("Content-disposition", "attachment; filename=model.xlsx");// 设定输出文件头
	        response.setContentType("application/msexcel");// 定义输出类型
	        File file = FileUtil.file("excelmodel/model.xlsx");
	        if (file==null) {
				throw new BussinessException(500, "模版文件不存在", null);
			}
	        FileReader reader = new FileReader(file);
	        os.write(reader.readBytes());
	        os.flush();
	        os.close(); // 关闭流
	}
	
	@RequestMapping(value = "/teacherUpload", method = RequestMethod.GET)
	public String getSingleUploadPage(ModelMap model) {
		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);
		return PREFIX + "teacher_upload.html";
	}

	@RequestMapping(value = "/upload")
	@ResponseBody
	public Tip upload(@RequestParam("file") MultipartFile picture) {
		try {
			InputStream fileInputStream = picture.getInputStream();
			ExcelReader reader = ExcelUtil.getReader(fileInputStream, 0);
			List<List<Object>> list = reader.read();
			if (CollectionUtils.isNotEmpty(list)) {
				teacherService.addBatchTeacher(list);
			}
		}catch(BussinessException e) {
		    return new ErrorTip(500, e.getMessage());
		} catch (Exception e) {
		    e.printStackTrace();
		    return new ErrorTip(500, "批量导入教师信息出错");
		}
		return SUCCESS_TIP;
	}

	/**
	 * 部门
	 * 
	 * @return
	 */
	@RequestMapping(value = "/department")
	@ResponseBody
	public List<Map<String, Object>> departmentSelect(@RequestParam @NotNull Integer pid) {
		return dictService.selectByPId(pid);

	}

	/**
	 * 职务
	 * 
	 * @return
	 */
	@RequestMapping(value = "/duty")
	@ResponseBody
	public List<Map<String, Object>> dutySelect(@RequestParam @NotNull Integer pid) {
		return dictService.selectByPId(pid);

	}

	/**
	 * 跳转到教师首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "teacher.html";
	}

	/**
	 * 跳转到添加教师
	 */
	@RequestMapping("/teacher_add")
	public String teacherAdd() {
		return PREFIX + "teacher_add.html";
	}

	/**
	 * 跳转到修改教师
	 */
	@RequestMapping("/teacher_update/{teacherId}")
	public String teacherUpdate(@PathVariable Integer teacherId, Model model) {
		Teacherdata teacherdata = teacherService.selectById(teacherId);
		model.addAttribute("teacher", teacherdata);
		return PREFIX + "teacher_edit.html";
	}

	/**
	 * 获取教师列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@Valid TeacherDto teacherDto, BindingResult result) {
		if (result.hasErrors()) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		Page<Teacherdata> page = new PageFactory<Teacherdata>().defaultPage();
		Wrapper<Teacherdata> wrapper = new EntityWrapper<Teacherdata>();
		wrapper.and(StringUtils.isNotBlank(teacherDto.getCardId()), "cardId={0}", teacherDto.getCardId());
		wrapper.like(StringUtils.isNotBlank(teacherDto.getTeachername()), "teachername", teacherDto.getTeachername());
		wrapper.and(StringUtils.isNotBlank(teacherDto.getDepartment()), "department={0}", teacherDto.getDepartment());
		wrapper.and(StringUtils.isNotBlank(teacherDto.getDuty()), "duty={0}", teacherDto.getDuty());
		wrapper.and(StringUtils.isNotBlank(teacherDto.getSex()), "sex={0}", teacherDto.getSex());
		wrapper.and(teacherDto.getTeachernum() != null, "teachernum={0}", teacherDto.getTeachernum());
		if (teacherDto != null && teacherDto.getIdType() != null) {
			if (1 == teacherDto.getIdType() || -1 == teacherDto.getIdType()) {
				wrapper.and("idType={0}", teacherDto.getIdType());
			} else {
				wrapper.gt("idType", 1);
			}
		}

		page.setRecords(new TeacherWarpper(teacherService.selectPage(page, wrapper).getRecords()).warp(teacherDto,
				recordService));
		return super.packForBT(page);
	}

	/**
	 * 新增教师
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(@Valid TeacherDto teacherDto, BindingResult result) {
		if (result.hasErrors()) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}

		if (!teacherService.validateFieldOnly("teachernum", teacherDto.getTeachernum().toString())) {
			throw new BussinessException(400, "教工号重复,请重新输入", null);
		}

		if (!teacherService.validateFieldOnly("cardId", teacherDto.getCardId().toString())) {
			throw new BussinessException(400, "教工身份证号重复,请重新输入", null);
		}

		if (!IdcardUtil.isValidCard(teacherDto.getCardId())) {
			throw new BussinessException(400, "教工身份证号格式不正确,请重新输入", null);
		}

		teacherService.addTeacher(teacherDto);

		return super.SUCCESS_TIP;
	}

	/**
	 * 删除教师
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam Integer teacherId) {
		teacherService.deleteById(teacherId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改教师
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(@Valid TeacherDto teacherDto, BindingResult result) {

		if (result.hasErrors()) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}

		if (!IdcardUtil.isValidCard(teacherDto.getCardId())) {
			throw new BussinessException(400, "教工身份证号格式不正确,请重新输入", null);
		}

		teacherService.updateTeacher(teacherDto);

		return super.SUCCESS_TIP;
	}

	/**
	 * 教师详情
	 */
	@RequestMapping(value = "/detail")
	@ResponseBody
	public Object detail() {
		return null;
	}
}
