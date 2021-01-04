package com.ruoyi.project.happyclock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 统计医生下的患者pt次数类
 */
public class hcPatientsInfoStatisticalNumber {
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

    /** pt姓名 */
    @Excel(name = "PT姓名")
    private String patientname;

    /** 次数 */
    @Excel(name = "次数")
    private String num;

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



    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    @Override
    public String toString() {
        return "hcPatientsInfoStatisticalNumber{" +
                "inputtime='" + inputtime + '\'' +
                ", doctorname='" + doctorname + '\'' +
                ", patientname='" + patientname + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
