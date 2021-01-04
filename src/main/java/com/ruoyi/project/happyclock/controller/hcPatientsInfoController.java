package com.ruoyi.project.happyclock.controller;

import java.util.List;

import com.ruoyi.project.happyclock.domain.hcPatientsInfoStatistical;
import com.ruoyi.project.happyclock.domain.hcPatientsInfoStatisticalNumber;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.happyclock.domain.hcPatientsInfo;
import com.ruoyi.project.happyclock.service.IhcPatientsInfoService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * PT管理-信息维护Controller
 *
 * @author light
 * @date 2020-12-28
 */
@Api("用户信息管理")
@RestController
@RequestMapping("/happyclock/info")
public class hcPatientsInfoController extends BaseController
{
    @Autowired
    private IhcPatientsInfoService hcPatientsInfoService;

    /**
     * 查询PT管理-信息维护列表
     */
    @ApiOperation("获取用户列表")
    @PreAuthorize("@ss.hasPermi('happyclock:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(hcPatientsInfo hcPatientsInfo)
    {
        startPage();
        List<hcPatientsInfo> list = hcPatientsInfoService.selecthcPatientsInfoList(hcPatientsInfo);
        return getDataTable(list);
    }

    /**
     * 导出PT管理-信息维护列表
     */
    @ApiOperation("导出人员信息")
    @PreAuthorize("@ss.hasPermi('happyclock:info:export')")
    @Log(title = "PT管理-信息维护", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(hcPatientsInfo hcPatientsInfo)
    {
        List<hcPatientsInfo> list = hcPatientsInfoService.selecthcPatientsInfoList(hcPatientsInfo);
        ExcelUtil<hcPatientsInfo> util = new ExcelUtil<hcPatientsInfo>(hcPatientsInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 获取PT管理-信息维护详细信息
     */
    @PreAuthorize("@ss.hasPermi('happyclock:info:query')")
    @GetMapping(value = "/{patientsId}")
    public AjaxResult getInfo(@PathVariable("patientsId") Integer patientsId)
    {
        return AjaxResult.success(hcPatientsInfoService.selecthcPatientsInfoById(patientsId));
    }

    /**
     * 新增PT管理-信息维护
     */
    @PreAuthorize("@ss.hasPermi('happyclock:info:add')")
    @Log(title = "PT管理-信息维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody hcPatientsInfo hcPatientsInfo)
    {
        return toAjax(hcPatientsInfoService.inserthcPatientsInfo(hcPatientsInfo));
    }

    /**
     * 修改PT管理-信息维护
     */
    @PreAuthorize("@ss.hasPermi('happyclock:info:edit')")
    @Log(title = "PT管理-信息维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody hcPatientsInfo hcPatientsInfo)
    {
        return toAjax(hcPatientsInfoService.updatehcPatientsInfo(hcPatientsInfo));
    }

    /**
     * 删除PT管理-信息维护
     */
    @PreAuthorize("@ss.hasPermi('happyclock:info:remove')")
    @Log(title = "PT管理-信息维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{patientsIds}")
    public AjaxResult remove(@PathVariable Integer[] patientsIds)
    {
        return toAjax(hcPatientsInfoService.deletehcPatientsInfoByIds(patientsIds));
    }

    /**
     * 查询PT管理-信息维护列表
     */
    @ApiOperation("小闹钟提醒接口")
    @PreAuthorize("@ss.hasPermi('happyclock:info:listRemind')")
    @GetMapping("/listRemind")
    public TableDataInfo listRemind(hcPatientsInfo hcPatientsInfo)
    {
        List<hcPatientsInfo> list = hcPatientsInfoService.selecthcPatientsInfoListRemind(hcPatientsInfo);
        return getDataTable(list);
    }

    /**
     * 查询PT管理-信息维护列表
     */
    @ApiOperation("获取统计列表")
    @PreAuthorize("@ss.hasPermi('happyclock:info:statisticalList')")
    @GetMapping("/statisticalList")
    public TableDataInfo statisticalList(hcPatientsInfoStatistical hcPatientsInfoStatistical)
    {
        startPage();
        List<hcPatientsInfoStatistical> list = hcPatientsInfoService.selecthcPatientsInfoStatisticalList(hcPatientsInfoStatistical);
        return getDataTable(list);
    }

    /**
     * 导出PT管理-信息维护列表
     */
    @ApiOperation("导出统计信息")
    @PreAuthorize("@ss.hasPermi('happyclock:info:exportStatistical')")
    @Log(title = "PT管理-信息维护", businessType = BusinessType.EXPORT)
    @GetMapping("/exportStatistical")
    public AjaxResult exportStatistical(hcPatientsInfoStatistical hcPatientsInfoStatistical)
    {
        List<hcPatientsInfoStatistical> list = hcPatientsInfoService.selecthcPatientsInfoStatisticalList(hcPatientsInfoStatistical);
        ExcelUtil<hcPatientsInfoStatistical> util = new ExcelUtil<hcPatientsInfoStatistical>(hcPatientsInfoStatistical.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 查询PT管理-信息维护列表
     */
    @ApiOperation("获取统计列表PT次数")
    @PreAuthorize("@ss.hasPermi('happyclock:info:statisticalListNum')")
    @GetMapping("/statisticalListNum")
    public TableDataInfo statisticalNumList(hcPatientsInfoStatisticalNumber hcPatientsInfoStatisticalNumber)
    {
        startPage();
        List<hcPatientsInfoStatisticalNumber> list = hcPatientsInfoService.selecthcPatientsInfoStatisticalListNum(hcPatientsInfoStatisticalNumber);
        return getDataTable(list);
    }

    /**
     * 导出PT管理-信息维护列表
     */
    @ApiOperation("导出统计信息PT次数")
    @PreAuthorize("@ss.hasPermi('happyclock:info:exportStatisticalNum')")
    @Log(title = "PT管理-信息维护", businessType = BusinessType.EXPORT)
    @GetMapping("/exportStatisticalNum")
    public AjaxResult exportStatisticalNum(hcPatientsInfoStatisticalNumber hcPatientsInfoStatisticalNumber)
    {
        List<hcPatientsInfoStatisticalNumber> list = hcPatientsInfoService.selecthcPatientsInfoStatisticalListNum(hcPatientsInfoStatisticalNumber);
        ExcelUtil<hcPatientsInfoStatisticalNumber> util = new ExcelUtil<hcPatientsInfoStatisticalNumber>(hcPatientsInfoStatisticalNumber.class);
        return util.exportExcel(list, "info");
    }
}
