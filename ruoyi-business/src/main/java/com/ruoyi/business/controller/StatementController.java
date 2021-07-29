package com.ruoyi.business.controller;

import com.ruoyi.business.domain.Statement;
import com.ruoyi.business.service.IStatementService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 结算单Controller
 * 
 * @author wolfcode
 * @date 2021-05-17
 */
@Controller
@RequestMapping("/business/statement")
public class StatementController extends BaseController
{
    private String prefix = "business/statement";

    @Autowired
    private IStatementService statementService;

    @RequiresPermissions("business:statement:view")
    @GetMapping()
    public String statement()
    {
        return prefix + "/statement";
    }

    /**
     * 查询结算单列表
     */
    @RequiresPermissions("business:statement:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Statement statement)
    {
        startPage();
        List<Statement> list = statementService.selectStatementList(statement);
        return getDataTable(list);
    }

    /**
     * 新增结算单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存结算单
     */
    @RequiresPermissions("business:statement:add")
    @Log(title = "结算单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Statement statement)
    {
        return toAjax(statementService.insertStatement(statement));
    }

    /**
     * 修改结算单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Statement statement = statementService.selectStatementById(id);
        mmap.put("statement", statement);
        return prefix + "/edit";
    }

    /**
     * 修改保存结算单
     */
    @RequiresPermissions("business:statement:edit")
    @Log(title = "结算单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Statement statement)
    {
        return toAjax(statementService.updateStatement(statement));
    }

    /**
     * 删除结算单
     */
    @RequiresPermissions("business:statement:remove")
    @Log(title = "结算单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(statementService.deleteStatementByIds(ids));
    }
    @RequiresPermissions("business:statement:delete")
    @Log(title = "结算单", businessType = BusinessType.DELETE)
    @PostMapping( "/delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id)
    {
        return toAjax(statementService.delete(id));
    }
    /**
     * 结算单明细页面
     */
    @RequiresPermissions("business:statement:detail")
    @GetMapping( "/detail/{sid}")
    public String detail(@PathVariable("sid") Long sid,ModelMap mmap)
    {
        Statement statement = statementService.selectStatementById(sid);
        mmap.put("statement", statement);
        if(Statement.STATUS_CONSUME.equals(statement.getStatus())){
            return "business/statementItem/statementItem";
        }else{
            return "business/statementItem/statementDetail.html";
        }
    }



}
