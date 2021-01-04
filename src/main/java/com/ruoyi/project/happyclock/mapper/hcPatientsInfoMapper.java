package com.ruoyi.project.happyclock.mapper;

import java.util.List;
import com.ruoyi.project.happyclock.domain.hcPatientsInfo;
import com.ruoyi.project.happyclock.domain.hcPatientsInfoStatistical;
import com.ruoyi.project.happyclock.domain.hcPatientsInfoStatisticalNumber;

/**
 * PT管理-信息维护Mapper接口
 * 
 * @author light
 * @date 2020-12-28
 */
public interface hcPatientsInfoMapper 
{
    /**
     * 查询PT管理-信息维护
     * 
     * @param patientsId PT管理-信息维护ID
     * @return PT管理-信息维护
     */
    public hcPatientsInfo selecthcPatientsInfoById(Integer patientsId);

    /**
     * 查询PT管理-信息维护列表
     * 
     * @param hcPatientsInfo PT管理-信息维护
     * @return PT管理-信息维护集合
     */
    public List<hcPatientsInfo> selecthcPatientsInfoList(hcPatientsInfo hcPatientsInfo);

    /**
     * 新增PT管理-信息维护
     * 
     * @param hcPatientsInfo PT管理-信息维护
     * @return 结果
     */
    public int inserthcPatientsInfo(hcPatientsInfo hcPatientsInfo);

    /**
     * 修改PT管理-信息维护
     * 
     * @param hcPatientsInfo PT管理-信息维护
     * @return 结果
     */
    public int updatehcPatientsInfo(hcPatientsInfo hcPatientsInfo);

    /**
     * 删除PT管理-信息维护
     * 
     * @param patientsId PT管理-信息维护ID
     * @return 结果
     */
    public int deletehcPatientsInfoById(Integer patientsId);

    /**
     * 批量删除PT管理-信息维护
     * 
     * @param patientsIds 需要删除的数据ID
     * @return 结果
     */
    public int deletehcPatientsInfoByIds(Integer[] patientsIds);

    /**
     * 查询PT管理-闹钟提醒
     *
     * @param hcPatientsInfo PT管理-信息维护
     * @return PT管理-信息维护集合
     */
    public List<hcPatientsInfo> selecthcPatientsInfoListRemind(hcPatientsInfo hcPatientsInfo);
    /**
     * 查询PT管理-闹钟提醒
     *
     * @param hcPatientsInfoStatistical PT管理-信息维护
     * @return PT管理-信息维护集合
     */
    public List<hcPatientsInfoStatistical> selecthcPatientsInfoStatisticalList(hcPatientsInfoStatistical hcPatientsInfoStatistical);
    /**
     * 查询PT管理-闹钟提醒
     *
     * @param hcPatientsInfoStatisticalNumber PT管理-信息维护
     * @return PT管理-信息维护集合
     */
    public List<hcPatientsInfoStatisticalNumber> selecthcPatientsInfoStatisticalListNum(hcPatientsInfoStatisticalNumber hcPatientsInfoStatisticalNumber);
}
