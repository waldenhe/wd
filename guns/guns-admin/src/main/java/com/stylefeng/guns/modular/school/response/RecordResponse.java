package com.stylefeng.guns.modular.school.response;
/**
 * 刷卡响应信息
 * @author 
 *
 */
public class RecordResponse {
	//姓名、部门、职务、照片
	
	/**
	 * 教师姓名
	 */
	private String teachername;
	
	/**
	 * 教师部门
	 */
	private String department; 
	
	/**
	 * 教师职务
	 */
	private String duty;
	
	/**
	 * 照片
	 */
	private String other11;
	
	/**
	 * 响应信息
	 */
	private String message;
	
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getOther11() {
		return other11;
	}

	public void setOther11(String other11) {
		this.other11 = other11;
	}

	@Override
	public String toString() {
		return "RecordResponse [teachername=" + teachername + ", department=" + department + ", duty=" + duty
				+ ", other11=" + other11 + ", message=" + message + "]";
	}
}
