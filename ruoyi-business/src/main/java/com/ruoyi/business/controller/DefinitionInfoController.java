package com.ruoyi.business.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.business.domain.DefinitionInfo;
import com.ruoyi.business.service.IDefinitionInfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 流程定义Controller
 * 
 * @author wolfcode
 * @date 2021-07-19
 */
@Controller
@RequestMapping("/business/definitionInfo")
public class DefinitionInfoController extends BaseController
{
    private String prefix = "business/definitionInfo";

    @Autowired
    private IDefinitionInfoService definitionInfoService;

    @RequiresPermissions("business:definitionInfo:view")
    @GetMapping()
    public String definitionInfo()
    {
        return prefix + "/definitionInfo";
    }

    /**
     * 查询流程定义列表
     */
    @RequiresPermissions("business:definitionInfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DefinitionInfo definitionInfo)
    {
        startPage();
        List<DefinitionInfo> list = definitionInfoService.selectDefinitionInfoList(definitionInfo);
        return getDataTable(list);
    }

    /**
     * 导出流程定义列表
     */
    @RequiresPermissions("business:definitionInfo:export")
    @Log(title = "流程定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DefinitionInfo definitionInfo)
    {
        List<DefinitionInfo> list = definitionInfoService.selectDefinitionInfoList(definitionInfo);
        ExcelUtil<DefinitionInfo> util = new ExcelUtil<DefinitionInfo>(DefinitionInfo.class);
        return util.exportExcel(list, "流程定义数据");
    }

    /**
     * 新增流程定义
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存流程定义
     */
    @RequiresPermissions("business:definitionInfo:add")
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DefinitionInfo definitionInfo)
    {
        return toAjax(definitionInfoService.insertDefinitionInfo(definitionInfo));
    }

    /**
     * 修改流程定义
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DefinitionInfo definitionInfo = definitionInfoService.selectDefinitionInfoById(id);
        mmap.put("definitionInfo", definitionInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存流程定义
     */
    @RequiresPermissions("business:definitionInfo:edit")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DefinitionInfo definitionInfo)
    {
        return toAjax(definitionInfoService.updateDefinitionInfo(definitionInfo));
    }

    /**
     * 删除流程定义
     */
    @RequiresPermissions("business:definitionInfo:remove")
    @Log(title = "流程定义", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(definitionInfoService.deleteDefinitionInfoByIds(ids));
    }
}
