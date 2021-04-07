package com.ruoyi.project.system.service;



import java.util.List;
import java.util.Map;


public interface ISysURPService
{
    /**
     * 通过用户ID查询角色、部门名称和用户名称
     *
     * @param userId 用户ID
     * @return 存放角色、部门名称项目名称的集合
     */
    public Map<String,String> selectRoleAndDeptByUserId(Long userId);
    /**
     * 通过用户ID查询项目名称
     *
     * @param userId 用户ID
     * @return 存放项目名称和用户名称的集合
     */
    public String  selectProByUserId(Long userId);
    /**
     * 通过部门id查询项目信息
     *
     * @param deptId 部门id
     * @return 项目信息
     */
    public List<String> selectProByDeptName(String deptName);
    /**
     * 获取部门名称集合
     */
    public List<String> selectDeptList(Long deptId);
    /**
     * 获取项目名称与项目经理匹配关系
     */
    public List<Map<String, String>> selectProAndManager();



    /**
     * 获取项目名称、项目经理、部门名称匹配关系
     */
    public Map<String, String> selectUDP(Long userId);
}
