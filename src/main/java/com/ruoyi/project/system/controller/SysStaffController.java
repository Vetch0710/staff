package com.ruoyi.project.system.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.Query;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.domain.server.SysFile;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysStaff;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.domain.vo.QueryVo;
import com.ruoyi.project.system.service.*;
import com.ruoyi.project.system.util.RemoteFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@Api("简历信息管理")
@RestController
@RequestMapping("/resume/staff")
public class SysStaffController extends BaseController {
    @Autowired
    private ISysStaffService staffService;


    @Autowired
    private ISysURPService urpService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取员工信息列表
     */
    @ApiOperation("获取员工列表")
    @PreAuthorize("@ss.hasPermi('resume:staff:list')")
    @GetMapping("/list")
    public TableDataInfo list(QueryVo queryVo) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
//        System.out.println("/////" + user);
        Long userId = user.getUserId();
        Map<String, String> role = urpService.selectRoleAndDeptByUserId(userId);
        List<SysStaff> list = new ArrayList<>();
        if (!"管理员".equals(role.get("roleName"))) {
            if (queryVo.getDeptName() != null && !"".equals(queryVo.getDeptName()) && !role.get("deptName").equals(queryVo.getDeptName())) {
                return getDataTable(list);
            } else {
                queryVo.setDeptName(role.get("deptName"));
                if ("项目经理".equals(role.get("roleName"))) {
                    String proName = urpService.selectProByUserId(userId);
                    if ((!"".equals(queryVo.getProjectName()) && !proName.equals(queryVo.getProjectName()) && queryVo.getProjectName() != null) || (!"".equals(queryVo.getProjectManager()) && queryVo.getProjectManager() != null && !user.getUserName().equals(queryVo.getProjectManager()))) {
                        return getDataTable(list);
                    }
                    queryVo.setProjectName(proName);
                    queryVo.setProjectManager(user.getUserName());
                    queryVo.setDeptName(role.get("deptName"));
                }
            }
        }
        startPage();
        list = staffService.selectStaffByQuery(queryVo);
//        System.out.println("=============="+list);
        return getDataTable(list);
    }


//    /**
//     * 根据用户编号获取详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('system:user:query')")
//    @GetMapping(value = {"/", "/{userId}"})
//    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
//        AjaxResult ajax = AjaxResult.success();
//        ajax.put("roles", roleService.selectRoleAll());
//        ajax.put("posts", postService.selectPostAll());
//        if (StringUtils.isNotNull(userId)) {
//            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
//            ajax.put("postIds", postService.selectPostListByUserId(userId));
//            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
//        }
//        return ajax;
//    }

    /**
     * 新增用户
     */
    @ApiOperation("添加员工")
    @PreAuthorize("@ss.hasPermi('resume:staff:add')")
    @Log(title = "简历信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysStaff staff) {
        System.out.println(staff);
        if (UserConstants.NOT_UNIQUE.equals(staffService.checkStaffIdUnique(staff.getUserId()))) {
            return AjaxResult.error("新增员工'" + staff.getUserId() + "'失败，该员工ID已存在");
        }

        return toAjax(staffService.insertStaff(staff));
    }

    /**
     * 修改用户
     */
    @ApiOperation("修改员工")
    @PreAuthorize("@ss.hasPermi('resume:staff:edit')")
    @Log(title = "简历信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysStaff staff) {
        System.out.println("***********"+staff);
//        if (UserConstants.NOT_UNIQUE.equals(staffService.checkStaffIdUnique(staff.getUserId()))) {
//            return AjaxResult.error("新增员工'" + staff.getUserId() + "'失败，该员工ID");
//        }

        return toAjax(staffService.updateStaff(staff));
    }

    /**
     * 删除用户
     */
    @ApiOperation("删除员工")
    @PreAuthorize("@ss.hasPermi('resume:staff:remove')")
    @Log(title = "简历信息管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable String[] Ids) {
        if (Ids.length > 1) {
            return toAjax(staffService.deleteStaffByIds(Ids));
        } else {
            return toAjax(staffService.deleteStaffById(Ids[0]));
        }
    }

    /**
     * 头像上传
     */
    @ApiOperation("上传证件照")
    @Log(title = "上传证件照", businessType = BusinessType.UPDATE)
    @PostMapping("/upload/{type}")
//    @RequestHeader("accessToken") String accessToken
    public AjaxResult avatar(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable String type) throws IOException {

//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        System.out.println("*****" + loginUser.getUser());

        if (!file.isEmpty()) {
            String fileResult = RemoteFile.uploadFile(file,request);

            if (StringUtils.isEmpty(fileResult)) {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", fileResult);
            ajax.put("type", type);
            return ajax;
        }

        return AjaxResult.error("上传图片异常，请联系管理员");
//        return AjaxResult.success();
    }

}