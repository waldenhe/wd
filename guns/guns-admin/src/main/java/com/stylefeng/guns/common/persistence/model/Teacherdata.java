package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author walden
 * @since 2017-10-08
 */
public class Teacherdata extends Model<Teacherdata> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 身份证号
     */
	@TableField
	private String cardId;
    /**
     * 教工号
     */
	private Integer teachernum;
    /**
     * 教师姓名
     */
	private String teachername;
    /**
     * 部门
     */
	private String department;
    /**
     * 职务
     */
	private String duty;
    /**
     * 性别
     */
	private String sex;
    /**
     * 1-普通，具体次数-临时，-1无限次数
     */
	private Integer idType;
	private Integer other1;
	private Integer other2;
	private Integer other3;
	private Integer other4;
	private String other5;
	private String other6;
	private String other7;
	private String other8;
	private String other9;
	private String other10;
    /**
     * 照片类型
     */
	private String other11;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Integer getTeachernum() {
		return teachernum;
	}

	public void setTeachernum(Integer teachernum) {
		this.teachernum = teachernum;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public Integer getOther1() {
		return other1;
	}

	public void setOther1(Integer other1) {
		this.other1 = other1;
	}

	public Integer getOther2() {
		return other2;
	}

	public void setOther2(Integer other2) {
		this.other2 = other2;
	}

	public Integer getOther3() {
		return other3;
	}

	public void setOther3(Integer other3) {
		this.other3 = other3;
	}

	public Integer getOther4() {
		return other4;
	}

	public void setOther4(Integer other4) {
		this.other4 = other4;
	}

	public String getOther5() {
		return other5;
	}

	public void setOther5(String other5) {
		this.other5 = other5;
	}

	public String getOther6() {
		return other6;
	}

	public void setOther6(String other6) {
		this.other6 = other6;
	}

	public String getOther7() {
		return other7;
	}

	public void setOther7(String other7) {
		this.other7 = other7;
	}

	public String getOther8() {
		return other8;
	}

	public void setOther8(String other8) {
		this.other8 = other8;
	}

	public String getOther9() {
		return other9;
	}

	public void setOther9(String other9) {
		this.other9 = other9;
	}

	public String getOther10() {
		return other10;
	}

	public void setOther10(String other10) {
		this.other10 = other10;
	}

	public String getOther11() {
		return other11;
	}

	public void setOther11(String other11) {
		this.other11 = other11;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Teacherdata{" +
			"id=" + id +
			", cardId=" + cardId +
			", teachernum=" + teachernum +
			", teachername=" + teachername +
			", department=" + department +
			", duty=" + duty +
			", sex=" + sex +
			", idType=" + idType +
			", other1=" + other1 +
			", other2=" + other2 +
			", other3=" + other3 +
			", other4=" + other4 +
			", other5=" + other5 +
			", other6=" + other6 +
			", other7=" + other7 +
			", other8=" + other8 +
			", other9=" + other9 +
			", other10=" + other10 +
			", other11=" + other11 +
			"}";
	}
}
