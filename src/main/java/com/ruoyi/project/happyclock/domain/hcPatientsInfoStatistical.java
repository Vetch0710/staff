package com.ruoyi.project.happyclock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 统计医生pt个数类
 */
public class hcPatientsInfoStatistical {
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private String rowNum;

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    @Excel(name = "用药月份")
    private String inputtime;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String doctorname;

    /** pt个数 */
    @Excel(name = "PT个数")
    private String PTNum;

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getPTNum() {
        return PTNum;
    }

    public void setPTNum(String PTNum) {
        this.PTNum = PTNum;
    }

    @Override
    public String toString() {
        return "hcPatientsInfoStatistical{" +
                "inputtime='" + inputtime + '\'' +
                ", doctorname='" + doctorname + '\'' +
                ", PTNum='" + PTNum + '\'' +
                '}';
    }
}

