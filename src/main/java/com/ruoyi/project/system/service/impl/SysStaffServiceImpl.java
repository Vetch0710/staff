package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import com.ruoyi.project.system.domain.*;
import com.ruoyi.project.system.domain.vo.QueryVo;
import com.ruoyi.project.system.mapper.*;
import com.ruoyi.project.system.service.ISysConfigService;
import com.ruoyi.project.system.service.ISysStaffService;
import com.ruoyi.project.system.service.ISysUserService;
import com.ruoyi.project.system.util.CalculateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysStaffServiceImpl implements ISysStaffService {
    private static final Logger log = LoggerFactory.getLogger(SysStaffServiceImpl.class);

    @Autowired
    private SysStaffMapper staffMapper;

//    @Autowired
////    private SysRoleMapper roleMapper;
////
////    @Autowired
////    private SysPostMapper postMapper;
////
////    @Autowired
////    private SysUserRoleMapper userRoleMapper;
////
////    @Autowired
////    private SysUserPostMapper userPostMapper;
////
////    @Autowired
////    private ISysConfigService configService;



/*    *//**
     * 根据条件分页查询员工信息列表
     *
     * @param staff 员工信息
     * @return 员工信息集合信息
     *//*
    @Override
    public List<SysStaff> selectStaffList(SysStaff staff) {
        return staffMapper.selectUserList(staff);
    }*/

    /**
     * 通过查询条件查询用户
     *
     * @param queryVo 查询条件
     * @return 员工信息集合信息
     */
//    @DataScope(staffAlias = "s")
    public List<SysStaff> selectStaffByQuery(QueryVo queryVo) {
        return staffMapper.selectStaffByQuery(queryVo);
    }

    ;


    /**
     * 新增员工信息
     *
     * @param staff 员工信息
     * @return 结果
     */
    @Transactional
    public int insertStaff(SysStaff staff) {
        updateAgeAndWorkDay(staff);
        updateStaffFinish(staff);

        return staffMapper.insertStaff(staff);
    }

    /**
     * 判断并修改用户信息完成情况
     *
     * @param staff 员工信息
     * @return 结果
     */
    @Transactional
    public void updateStaffFinish(SysStaff staff)  {
        try {
            Class<?> clazz = staff.getClass(); //得到类对象
            Field[] fs = clazz.getDeclaredFields(); //得到属性集合
            for (Field field : fs) {      //遍历属性
                field.setAccessible(true); //设置属性是可以访问的（私有的也可以）
                if (!"isFinish".equals(field.getName())) {
                    if (field.get(staff) == null || field.get(staff) == "" ){
                        staff.setIsFinish("0");
                        break;
                    }
                }
            }
            staff.setIsFinish("1");
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    /**
     * 校验员工ID是否唯一
     *
     * @param userId 员工编号
     * @return 结果
     */
    @Override
    public String checkStaffIdUnique(String userId) {
            int count = staffMapper.checkStaffIdUnique(userId);
            if (count > 0)
            {
                return UserConstants.NOT_UNIQUE;
            }
            return UserConstants.UNIQUE;

    }


    /**
     * 判断并修改用户信息完成情况
     *
     * @param staff 员工信息
     * @return 结果
     */
    public void updateAgeAndWorkDay(SysStaff staff)  {
        int age = CalculateTime.getAgeByBirthday(staff.getBirthDay());
        staff.setAge(age);
        String workDay = staff.getGraduationDay();
        if (!"".equals(workDay) && null != workDay) {
            workDay = CalculateTime.getWorkDay(staff.getGraduationDay());
            staff.setWorkDay(workDay);
        }
    }

    /**
     * 修改保存用户信息
     *
     * @param staff 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateStaff(SysStaff staff)  {
        updateAgeAndWorkDay(staff);
        updateStaffFinish(staff);

        return staffMapper.updateStaff(staff);
    }



    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteStaffById(String userId) {
        return staffMapper.deleteStaffById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteStaffByIds(String[] userIds) {
        return staffMapper.deleteStaffByIds(userIds);
    }


}
