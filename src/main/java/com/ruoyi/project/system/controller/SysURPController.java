package com.ruoyi.project.system.controller;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysStaff;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.domain.vo.QueryVo;
import com.ruoyi.project.system.service.ISysDeptService;
import com.ruoyi.project.system.service.ISysRoleService;
import com.ruoyi.project.system.service.ISysStaffService;
import com.ruoyi.project.system.service.ISysURPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/resume/urp")
public class SysURPController extends BaseController {
    @Autowired
    private ISysStaffService staffService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysURPService urpService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 各种匹配关系
     */
//    @PreAuthorize("@ss.hasPermi('resume:staff:list')")
    @GetMapping("/list")
    public AjaxResult getDPM() {
        HashMap<String, Object> result = new HashMap<>();

        //全部的部门
        List<String> deptNames = urpService.selectDeptList(null);
        result.put("departmentNames", deptNames);
        //依照部门名称获取项目集合
        List<Object> deptAndPro = new ArrayList<>();
        for (String dName : deptNames) {
            HashMap<String, Object> mid = new HashMap<>();
            List<String> proNames = urpService.selectProByDeptName(dName);
            mid.put("departmentName", dName);
            mid.put("projectName", proNames);
            deptAndPro.add(mid);
        }
        result.put("deptAndPro", deptAndPro);
        //部门和部门经理匹配关系
        List<Map<String, String>> proAndManager = urpService.selectProAndManager();
        result.put("proAndManager", proAndManager);

        //该用户的部门、部门经理、部门名
        Map<String, String> maps = urpService.selectUDP(tokenService.getLoginUser(ServletUtils.getRequest()).getUser().getUserId());
        result.put("udp", maps);
        return AjaxResult.success(result);
    }


}