package com.ruoyi.business.controller;

import com.ruoyi.business.domain.StatementItem;
import com.ruoyi.business.service.IStatementItemService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 结算单明细Controller
 * 
 * @author wolfcode
 * @date 2021-05-17
 */
@Controller
@RequestMapping("/business/statementItem")
public class StatementItemController extends BaseController
{
    private String prefix = "business/statementItem";

    @Autowired
    private IStatementItemService statementItemService;

    @RequiresPermissions("business:statementItem:view")
    @GetMapping()
    public String statementTtem()
    {
        return prefix + "/statementItem";
    }

    /**
     * 查询结算单明细列表
     */
    @RequiresPermissions("business:statementItem:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StatementItem statementItem)
    {
        startPage();
        List<Map> list = statementItemService.selectStatementItemList(statementItem);
        return getDataTable(list);
    }
    @RequiresPermissions("business:statementItem:saveItems")
    @Log(title = "养修服务项", businessType = BusinessType.INSERT)
    @PostMapping("/saveItems")
    @ResponseBody
    public AjaxResult saveItems(@RequestBody List<StatementItem> items)
    {
        return toAjax(statementItemService.saveItems(items));
    }
    /**
     * 结算单支付
     */
    @RequiresPermissions("business:statementItem:payStatement")
    @PostMapping("/payStatement")
    @ResponseBody
    public AjaxResult payStatement(Long statementId)
    {
        return toAjax(statementItemService.payStatement(statementId));
    }
}
