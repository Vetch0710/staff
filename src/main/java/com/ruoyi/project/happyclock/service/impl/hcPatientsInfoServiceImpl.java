package com.ruoyi.project.happyclock.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.happyclock.domain.hcPatientsInfoStatistical;
import com.ruoyi.project.happyclock.domain.hcPatientsInfoStatisticalNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.happyclock.mapper.hcPatientsInfoMapper;
import com.ruoyi.project.happyclock.domain.hcPatientsInfo;
import com.ruoyi.project.happyclock.service.IhcPatientsInfoService;

/**
 * PT管理-信息维护Service业务层处理
 * 
 * @author light
 * @date 2020-12-28
 */
@Service
public class hcPatientsInfoServiceImpl implements IhcPatientsInfoService 
{
    @Autowired
    private hcPatientsInfoMapper hcPatientsInfoMapper;

    /**
     * 查询PT管理-信息维护
     * 
     * @param patientsId PT管理-信息维护ID
     * @return PT管理-信息维护
     */
    @Override
    public hcPatientsInfo selecthcPatientsInfoById(Integer patientsId)
    {
        return hcPatientsInfoMapper.selecthcPatientsInfoById(patientsId);
    }

    /**
     * 查询PT管理-信息维护列表
     * 
     * @param hcPatientsInfo PT管理-信息维护
     * @return PT管理-信息维护
     */
    @Override
    public List<hcPatientsInfo> selecthcPatientsInfoList(hcPatientsInfo hcPatientsInfo)
    {
        List<hcPatientsInfo> list = hcPatientsInfoMapper.selecthcPatientsInfoList(hcPatientsInfo);
        if(list != null){
            for(int i = 0 ; i < list.size(); i++){
                list.get(i).setRowNum(i+1+"");
            }
        }

        return list;
    }

    /**
     * 新增PT管理-信息维护
     * 
     * @param hcPatientsInfo PT管理-信息维护
     * @return 结果
     */
    @Override
    public int inserthcPatientsInfo(hcPatientsInfo hcPatientsInfo)
    {
        hcPatientsInfo.setCreateTime(DateUtils.getNowDate().toString());
        return hcPatientsInfoMapper.inserthcPatientsInfo(hcPatientsInfo);
    }

    /**
     * 修改PT管理-信息维护
     * 
     * @param hcPatientsInfo PT管理-信息维护
     * @return 结果
     */
    @Override
    public int updatehcPatientsInfo(hcPatientsInfo hcPatientsInfo)
    {
        hcPatientsInfo.setUpdateTime(DateUtils.getNowDate().toString());
        return hcPatientsInfoMapper.updatehcPatientsInfo(hcPatientsInfo);
    }

    /**
     * 批量删除PT管理-信息维护
     * 
     * @param patientsIds 需要删除的PT管理-信息维护ID
     * @return 结果
     */
    @Override
    public int deletehcPatientsInfoByIds(Integer[] patientsIds)
    {
        return hcPatientsInfoMapper.deletehcPatientsInfoByIds(patientsIds);
    }

    /**
     * 删除PT管理-信息维护信息
     * 
     * @param patientsId PT管理-信息维护ID
     * @return 结果
     */
    @Override
    public int deletehcPatientsInfoById(Integer patientsId)
    {
        return hcPatientsInfoMapper.deletehcPatientsInfoById(patientsId);
    }

    @Override
    public List<hcPatientsInfo> selecthcPatientsInfoListRemind(hcPatientsInfo hcPatientsInfo) {

        return hcPatientsInfoMapper.selecthcPatientsInfoListRemind(hcPatientsInfo);
    }

    @Override
    public List<hcPatientsInfoStatistical> selecthcPatientsInfoStatisticalList(hcPatientsInfoStatistical hcPatientsInfoStatistical) {
        List<hcPatientsInfoStatistical> list = hcPatientsInfoMapper.selecthcPatientsInfoStatisticalList(hcPatientsInfoStatistical);
        if(list != null){
            for(int i = 0 ; i < list.size(); i++){
                list.get(i).setRowNum(i+1+"");
            }
        }
        return list;
    }

    @Override
    public List<hcPatientsInfoStatisticalNumber> selecthcPatientsInfoStatisticalListNum(hcPatientsInfoStatisticalNumber hcPatientsInfoStatisticalNumber) {
        List<hcPatientsInfoStatisticalNumber> list = hcPatientsInfoMapper.selecthcPatientsInfoStatisticalListNum(hcPatientsInfoStatisticalNumber);
        if(list != null){
            for(int i = 0 ; i < list.size(); i++){
                list.get(i).setRowNum(i+1+"");
            }
        }
        return list;

    }
}
