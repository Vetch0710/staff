package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.SysStaff;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.domain.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */

public interface SysStaffMapper
{
    /**
     * 根据条件分页查询员工信息列表
     *
     * @param staff 员工信息
     * @return 员工信息集合信息
     */
    public List<SysStaff> selectStaffList(SysStaff staff);

    /**
     * 通过查询条件查询用户
     *
     * @param queryVo 查询条件
     * @return 员工信息集合信息
     */
    public List<SysStaff> selectStaffByQuery(QueryVo queryVo);


    /**
     * 新增员工信息
     *
     * @param staff 员工信息
     * @return 结果
     */
    public int insertStaff(SysStaff staff);

    /**
     * 修改员工信息
     *
     * @param staff 员工信息
     * @return 结果
     */
    public int updateStaff(SysStaff staff);


    /**
     * 校验员工ID是否唯一
     *
     * @param userId 员工编号
     * @return 结果
     */
    public int checkStaffIdUnique(String userId) ;



    /**
     * 通过员工编号删除员工
     *
     * @param userId 员工编号
     * @return 结果
     */
    public int deleteStaffById(String userId);

    /**
     * 批量删除员工信息
     *
     * @param userIds 需要删除的员工编号
     * @return 结果
     */
    public int deleteStaffByIds(String[] userIds);

    /**
     * 根据员工id查询员工信息
     *
     * @param queryVo 查询条件
     * @return 结果
     */
    public List<HashMap<String,String>> exportStaff(@Param("queryVo") QueryVo queryVo,@Param("id")String id);


}
