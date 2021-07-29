package com.ruoyi.business.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.business.domain.DefinitionNode;
import com.ruoyi.business.service.IDefinitionNodeService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程定义节点信息Controller
 * 
 * @author wolfcode
 * @date 2021-07-20
 */
@Controller
@RequestMapping("/business/definitionNode")
public class DefinitionNodeController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    private String prefix = "business/definitionNode";

    @Autowired
    private IDefinitionNodeService definitionNodeService;

    @RequiresPermissions("business:definitionNode:view")
    @GetMapping("/{definitionId}")
    public String definitionNode(@PathVariable("definitionId") String definitionId, ModelMap map)
    {
        map.put("definitionId", definitionId);
        return prefix + "/definitionNode";
    }

    /**
     * 查询流程定义节点信息列表
     */
    @RequiresPermissions("business:definitionNode:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DefinitionNode definitionNode)
    {
        startPage();
        List<DefinitionNode> list = definitionNodeService.selectDefinitionNodeList(definitionNode);
        return getDataTable(list);
    }

    /**
     * 导出流程定义节点信息列表
     */
    @RequiresPermissions("business:definitionNode:export")
    @Log(title = "流程定义节点信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DefinitionNode definitionNode)
    {
        List<DefinitionNode> list = definitionNodeService.selectDefinitionNodeList(definitionNode);
        ExcelUtil<DefinitionNode> util = new ExcelUtil<DefinitionNode>(DefinitionNode.class);
        return util.exportExcel(list, "流程定义节点信息数据");
    }

    /**
     * 新增流程定义节点信息
     */
    @GetMapping("/add/{definitionId}")
    //@ResponseBody
    public String add(@PathVariable("definitionId") String definitionId, ModelMap map)
    {
        List<SysUser> users = userService.selectUserList();
        map.put("users", users);
        map.put("definitionId", definitionId);
        return prefix + "/add";
    }

    /**
     * 新增保存流程定义节点信息
     */
    @RequiresPermissions("business:definitionNode:add")
    @Log(title = "流程定义节点信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DefinitionNode definitionNode)
    {
        return toAjax(definitionNodeService.insertDefinitionNode(definitionNode));
    }

    /**
     * 修改流程定义节点信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        List<SysUser> users = userService.selectUserList();
        mmap.put("users", users);
        DefinitionNode definitionNode = definitionNodeService.selectDefinitionNodeById(id);
        List<Long> auditors = userService.selectAuditorsByNodeId(id);
        mmap.put("auditors", JSON.toJSONString(auditors));
        mmap.put("definitionNode", definitionNode);
        return prefix + "/edit";
    }

    /**
     * 修改保存流程定义节点信息
     */
    @RequiresPermissions("business:definitionNode:edit")
    @Log(title = "流程定义节点信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DefinitionNode definitionNode)
    {
        return toAjax(definitionNodeService.updateDefinitionNode(definitionNode));
    }

    /**
     * 删除流程定义节点信息
     */
    @RequiresPermissions("business:definitionNode:remove")
    @Log(title = "流程定义节点信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(definitionNodeService.deleteDefinitionNodeByIds(ids));
    }
}
