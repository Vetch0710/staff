package com.ruoyi.project.happyclock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 患者查询模块-患者查询功能对象 hc_patients_inf
 *
 * @author light
 * @date 2020-12-24
 */
public class hcPatientsInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    private String beginTime;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    private String endTime;
    /** 患者信息ID */
    private Long patientsId;

    /** 序号 */
    @Excel(name = "序号")
    private String rowNum;

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    /** 用药日期 */
    @Excel(name = "用药日期")
    private String inputtime;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String doctorname;

    /** 医院code */
    private String hospname;

    /** 医院名称 */
    @Excel(name = "医院名称")
    private String real_hospname;


    /** 用量（支） */
    @Excel(name = "用量", readConverterExp = "支=")
    private String dosage;

    /** 患者姓名 */
    @Excel(name = "PT姓名")
    private String patientname;
    /** 录入人 */
    @Excel(name = "录入人")
    private String real_inputorname;
    /** 期限天数 */
    @Excel(name = "期限天数")
    private String dayNum;
    /**
     * 提醒日期=inputtime+day_num
     */
    @Excel(name = "提醒日期")
    private String  notitime;

    public String getNotitime() {
        return notitime;
    }

    public void setNotitime(String notitime) {
        this.notitime = notitime;
    }

    /** 录入人code */
    private String inputorname;

    /** 状态（0=未确认、1=已确认）code */
    private String sureState;

    /** 状态（0=未确认、1=已确认） */
    @Excel(name = "状态", readConverterExp = "0==未确认、1=已确认")
    private String real_sure_state;

    public String getReal_hospname() {
        return real_hospname;
    }

    public void setReal_hospname(String real_hospname) {
        this.real_hospname = real_hospname;
    }

    public String getReal_inputorname() {
        return real_inputorname;
    }

    public void setReal_inputorname(String real_inputorname) {
        this.real_inputorname = real_inputorname;
    }

    public String getReal_sure_state() {
        return real_sure_state;
    }

    public void setReal_sure_state(String real_sure_state) {
        this.real_sure_state = real_sure_state;
    }

    public void setPatientsId(Long patientsId)
    {
        this.patientsId = patientsId;
    }

    public Long getPatientsId()
    {
        return patientsId;
    }
    public void setInputtime(String inputtime)
    {
        this.inputtime = inputtime;
    }

    public String getInputtime()
    {
        return inputtime;
    }
    public void setDoctorname(String doctorname)
    {
        this.doctorname = doctorname;
    }

    public String getDoctorname()
    {
        return doctorname;
    }
    public void setHospname(String hospname)
    {
        this.hospname = hospname;
    }

    public String getHospname()
    {
        return hospname;
    }
    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    public String getDosage()
    {
        return dosage;
    }
    public void setDayNum(String dayNum)
    {
        this.dayNum = dayNum;
    }

    public String getDayNum()
    {
        return dayNum;
    }
    public void setPatientname(String patientname)
    {
        this.patientname = patientname;
    }

    public String getPatientname()
    {
        return patientname;
    }
    public void setInputorname(String inputorname)
    {
        this.inputorname = inputorname;
    }

    public String getInputorname()
    {
        return inputorname;
    }
    public void setSureState(String sureState)
    {
        this.sureState = sureState;
    }

    public String getSureState()
    {
        return sureState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("patientsId", getPatientsId())
                .append("inputtime", getInputtime())
                .append("doctorname", getDoctorname())
                .append("hospname", getHospname())
                .append("dosage", getDosage())
                .append("dayNum", getDayNum())
                .append("patientname", getPatientname())
                .append("inputorname", getInputorname())
                .append("sureState", getSureState())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("begintime", getBeginTime())
                .append("endtime", getEndTime())
                .append("real_hospname", getReal_hospname())
                .append("real_inputorname",getReal_inputorname())
                .append("real_sure_state",getReal_sure_state())
                .toString();
    }
}
