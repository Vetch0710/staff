package com.ruoyi.project.system.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.Query;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
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
import com.ruoyi.project.system.util.WordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        System.out.println("***********" + staff);
//        if (UserConstants.NOT_UNIQUE.equals(staffService.checkStaffIdUnique(staff.getUserId()))) {
//            return AjaxResult.error("新增员工'" + staff.getUserId() + "'失败，该员工ID");
//        }

        return toAjax(staffService.updateStaff(staff));
    }

    /**
     * 照片上传
     */
    @ApiOperation("简历模板上传")
    @Log(title = "简历模板上传", businessType = BusinessType.UPDATE)
    @PostMapping("/upload")
    public AjaxResult uploadTemp(@RequestBody MultipartFile file, HttpServletRequest request) throws IOException {

        if (!file.isEmpty()) {
            return  AjaxResult.error("上传成功");
        }

        return AjaxResult.error("上传文件异常，请联系管理员");
//        return AjaxResult.success();
    }



    @Log(title = "简历下载", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('resume:staff:export')")
    @PostMapping("/export")
    public AjaxResult export(@RequestBody MultipartFile file, QueryVo queryVo) {
        if (file==null){
            return AjaxResult.error("模板文件为空");
        }
        try {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            SysUser user = loginUser.getUser();
            String userName = user.getUserName();
            Map<String, String> role = urpService.selectRoleAndDeptByUserId(user.getUserId());
            if (!"管理员".equals(role.get("roleName"))) {
                    queryVo.setDeptName(role.get("deptName"));
                    if ("项目经理".equals(role.get("roleName"))) {
                        String proName = urpService.selectProByUserId(user.getUserId());
                        queryVo.setProjectName(proName);
                        queryVo.setProjectManager(user.getUserName());
                    }
            }
            List<HashMap<String, String>> list = staffService.exportStaff(queryVo);
            System.out.println(list);
            InputStream is = file.getInputStream();
//            map.put("degree", "C:/Users/75440/Desktop/upload/profile/2021/04/11/ee299936a0d49b94f601a0eb4687e1f7.jpeg");
            List<String> files=new ArrayList<>();
            for (HashMap<String, String> map : list) {
                String filename = map.get("workLevel") + "-" + map.get("userName") + ".docx";
//                map.get()
                String downloadPath = RuoYiConfig.getDownloadPath() + userName+"/"+filename;
                File desc = new File(downloadPath);
                if (!desc.getParentFile().exists()) {
                    desc.getParentFile().mkdirs();
                }
                is = file.getInputStream();
                OutputStream os = new FileOutputStream(downloadPath);
                if (  WordUtil.changWord(is, os, map, 100, 100)){
                    files.add(downloadPath);
                }
            }
//            is.close();
            //生成新的word文档
            System.out.println(files);
            String zip = WordUtil.createZip(files, userName);
            return AjaxResult.success(zip);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
//        try {
//            for (HashMap<String, String> stringStringHashMap : list) {
//                WordUtil.changWord(file.getInputStream(),);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//
//        }

        return AjaxResult.error("fileName");
    }

    /**
     * 删除员工信息
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
     * 照片上传
     */
    @ApiOperation("上传证件照")
    @Log(title = "上传证件照", businessType = BusinessType.UPDATE)
    @PostMapping("/upload/{type}")
//    @RequestHeader("accessToken") String accessToken
    public AjaxResult avatar(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable String type) throws IOException {

//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        System.out.println("*****" + loginUser.getUser());

        if (!file.isEmpty()) {
//            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String path = FileUploadUtils.upload(RuoYiConfig.getProfilePath(type), file);

            if (StringUtils.isEmpty(path)) {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", path);
            ajax.put("type", type);
            return ajax;
        }

        return AjaxResult.error("上传图片异常，请联系管理员");
//        return AjaxResult.success();
    }



}