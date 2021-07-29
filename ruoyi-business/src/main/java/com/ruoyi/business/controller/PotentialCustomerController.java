package com.ruoyi.business.controller;

import com.ruoyi.business.domain.FollowInfo;
import com.ruoyi.business.domain.PotentialCustomer;
import com.ruoyi.business.service.IPotentialCustomerService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 潜在客户Controller
 * 
 * @author ruoyi
 * @date 2021-07-26
 */
@Controller
@RequestMapping("/business/potentialCustomer")
public class PotentialCustomerController extends BaseController
{
    private String prefix = "business/potentialCustomer";

    @Autowired
    private IPotentialCustomerService potentialCustomerService;

    @RequiresPermissions("business:potentialCustomer:view")
    @GetMapping()
    public String potentialCustomer()
    {
        return prefix + "/potentialCustomer";
    }

    /**
     * 查询潜在客户列表
     */
    @RequiresPermissions("business:potentialCustomer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PotentialCustomer potentialCustomer)
    {
        startPage();
        List<PotentialCustomer> list = potentialCustomerService.selectPotentialCustomerList(potentialCustomer);
        return getDataTable(list);
    }

    /**
     * 导出潜在客户列表
     */
    @RequiresPermissions("business:potentialCustomer:export")
    @Log(title = "潜在客户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PotentialCustomer potentialCustomer)
    {
        List<PotentialCustomer> list = potentialCustomerService.selectPotentialCustomerList(potentialCustomer);
        ExcelUtil<PotentialCustomer> util = new ExcelUtil<PotentialCustomer>(PotentialCustomer.class);
        return util.exportExcel(list, "潜在客户数据");
    }

    /**
     * 新增潜在客户
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存潜在客户
     */
    @RequiresPermissions("business:potentialCustomer:add")
    @Log(title = "潜在客户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PotentialCustomer potentialCustomer)
    {
        return toAjax(potentialCustomerService.insertPotentialCustomer(potentialCustomer));
    }

    /**
     * 修改潜在客户
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        PotentialCustomer potentialCustomer = potentialCustomerService.selectPotentialCustomerById(id);
        mmap.put("potentialCustomer", potentialCustomer);
        return prefix + "/edit";
    }

    /**
     * 修改保存潜在客户
     */
    @RequiresPermissions("business:potentialCustomer:edit")
    @Log(title = "潜在客户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PotentialCustomer potentialCustomer)
    {
        return toAjax(potentialCustomerService.updatePotentialCustomer(potentialCustomer));
    }

    /**
     * 删除潜在客户
     */
    @RequiresPermissions("business:potentialCustomer:remove")
    @Log(title = "潜在客户", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(potentialCustomerService.deletePotentialCustomerByIds(ids));
    }


    @RequestMapping("/followPage")
    public String followPage(Long customerId, ModelMap mmap) {
        mmap.put("customerId", customerId);
        return prefix + "/followPage";
    }


    @RequestMapping("/follow")
    @ResponseBody
    public AjaxResult reApply(FollowInfo followInfo) {
        potentialCustomerService.addFollowInfo(followInfo);
        return AjaxResult.success();
    }

    @RequestMapping("/historyPage")
    public String historyPage(Long customerId, ModelMap mmap) {
        mmap.put("customerId", customerId);
        return prefix + "/historyPage";
    }


    @RequestMapping("/history")
    @ResponseBody
    public TableDataInfo history(Long customerId) {
        List<FollowInfo> historyList = potentialCustomerService.selectHistoryList(customerId);
        return getDataTable(historyList);
    }


}
