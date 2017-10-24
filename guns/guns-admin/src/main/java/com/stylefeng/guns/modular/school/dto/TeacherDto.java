package com.stylefeng.guns.modular.school.dto;

public class TeacherDto {
    
    
    private Integer id;
    
    /**
     * 身份证号
     */
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
    
    /**
     * 刷卡开始时间
     */
    private String startTime;
    /**
     * 刷卡结束时间
     */
    private String endTime;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    @Override
    public String toString() {
        return "TeacherDto [id=" + id + ", cardId=" + cardId + ", teachernum=" + teachernum + ", teachername=" + teachername
                + ", department=" + department + ", duty=" + duty + ", sex=" + sex + ", idType=" + idType + ", startTime="
                + startTime + ", endTime=" + endTime + "]";
    }

}
