package com.ruoyi.project.system.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.aspectj.lang.annotation.Excels;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vetch
 * @Description
 * @create 2021-04-02-11:16
 */
public class SysStaff {
    private static final long serialVersionUID = 1L;
    /** 员工编号 */
    private String userId;
    /** 员工姓名 */
    private String userName;
    /** 性别 */
    private String sex;
    /** 性别 */
    private Integer age;
    /** 出生日期 */
    private String birthDay ;
    /** 手机号码 */
    private String phone;
    /** 民族 */
    private String nation;
    /** 婚姻状况 */
    private String marital;
    /** 户口所在地 */
    private String hometown;
    /** 最高学历*/
    private String eduBackground;
    /** 毕业院校 */
    private String school ;
    /** 专业 */
        private String major ;
    /** 毕业时间 */
    private String graduationDay ;
    /** 工龄 */
    private String workDay  ;
    /** 职工级别 */
    private String workLevel  ;
    /** 所属部门 */
    private String department   ;
    /** 所属项目组 */
    private String projectTeam   ;
    /** 项目经理 */
    private String leader  ;
    /** 身份证正面 */
    private String idCardFront   ;
    /** 身份证反面 */
    private String idCardBack   ;
    /** 毕业证 */
    private String diploma  ;
    /** 学位证 */
    private String degree  ;
    /** 信息填写状态 */
    private String isFinish  ;
    private String idCard  ;
    private String position  ;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public SysStaff(String userId, String userName, String sex, Integer age, String birthDay, String phone, String nation, String marital, String hometown, String eduBackground, String school, String major, String graduationDay, String workDay, String workLevel, String department, String projectTeam, String leader, String idCardFront, String idCardBack, String diploma, String degree, String isFinish, String idCard, String position) {
        this.userId = userId;
        this.userName = userName;
        this.sex = sex;
        this.age = age;
        this.birthDay = birthDay;
        this.phone = phone;
        this.nation = nation;
        this.marital = marital;
        this.hometown = hometown;
        this.eduBackground = eduBackground;
        this.school = school;
        this.major = major;
        this.graduationDay = graduationDay;
        this.workDay = workDay;
        this.workLevel = workLevel;
        this.department = department;
        this.projectTeam = projectTeam;
        this.leader = leader;
        this.idCardFront = idCardFront;
        this.idCardBack = idCardBack;
        this.diploma = diploma;
        this.degree = degree;
        this.isFinish = isFinish;
        this.idCard = idCard;
        this.position = position;
    }

    public SysStaff() {
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birth_day) {
        this.birthDay = birth_day;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getEduBackground() {
        return eduBackground;
    }

    public void setEduBackground(String eduBackground) {
        this.eduBackground = eduBackground;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGraduationDay() {
        return graduationDay;
    }

    public void setGraduationDay(String graduationDay) {
        this.graduationDay = graduationDay;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getWorkLevel() {
        return workLevel;
    }

    public void setWorkLevel(String workLevel) {
        this.workLevel = workLevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(String projectTeam) {
        this.projectTeam = projectTeam;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    @Override
    public String toString() {
        return "SysStaff{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", birthDay='" + birthDay + '\'' +
                ", phone='" + phone + '\'' +
                ", nation='" + nation + '\'' +
                ", marital='" + marital + '\'' +
                ", hometown='" + hometown + '\'' +
                ", eduBackground='" + eduBackground + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", graduationDay='" + graduationDay + '\'' +
                ", workDay='" + workDay + '\'' +
                ", workLevel='" + workLevel + '\'' +
                ", department='" + department + '\'' +
                ", projectTeam='" + projectTeam + '\'' +
                ", leader='" + leader + '\'' +
                ", idCardFront='" + idCardFront + '\'' +
                ", idCardBack='" + idCardBack + '\'' +
                ", diploma='" + diploma + '\'' +
                ", degree='" + degree + '\'' +
                ", isFinish='" + isFinish + '\'' +
                ", idCard='" + idCard + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
