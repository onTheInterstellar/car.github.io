package com.ruoyi.business.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Customer;
import com.ruoyi.system.service.ICustomerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户信息操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/business/customer")
public class CustomerController extends BaseController
{
    private String prefix = "business/customer";

    @Autowired
    private ICustomerService customerService;

    @RequiresPermissions("business:customer:view")
    @GetMapping()
    public String operlog()
    {
        return prefix + "/customer";
    }

    @RequiresPermissions("business:customer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Customer customer)
    {
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }

    @Log(title = "客户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("business:customer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Customer customer)
    {
        List<Customer> list = customerService.selectCustomerList(customer);
        ExcelUtil<Customer> util = new ExcelUtil<Customer>(Customer.class);
        return util.exportExcel(list, "客户数据");
    }

    @RequiresPermissions("business:customer:remove")
    @Log(title = "客户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(customerService.deleteCustomerByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 新增客户
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存客户
     */
    @RequiresPermissions("business:customer:add")
    @Log(title = "客户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Customer customer)
    {
        if (UserConstants.CUSTOMER_NAME_NOT_UNIQUE.equals(customerService.checkCustomerNameUnique(customer)))
        {
            return error("新增客户'" + customer.getName() + "'失败，客户名称已存在");
        }
        else if (UserConstants.CUSTOMER_PHONE_NOT_UNIQUE.equals(customerService.checkCustomerCodeUnique(customer)))
        {
            return error("新增客户'" + customer.getName() + "'失败，客户编码已存在");
        }
        customer.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改客户
     */
    @GetMapping("/edit/{customerId}")
    public String edit(@PathVariable("customerId") Long customerId, ModelMap mmap)
    {
        mmap.put("customer", customerService.selectCustomerById(customerId));
        return prefix + "/edit";
    }

    /**
     * 修改保存客户
     */
    @RequiresPermissions("business:customer:edit")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Customer customer)
    {
        if (UserConstants.CUSTOMER_NAME_NOT_UNIQUE.equals(customerService.checkCustomerNameUnique(customer)))
        {
            return error("修改客户'" + customer.getName() + "'失败，客户名称已存在");
        }
        else if (UserConstants.CUSTOMER_PHONE_NOT_UNIQUE.equals(customerService.checkCustomerCodeUnique(customer)))
        {
            return error("修改客户'" + customer.getName() + "'失败，客户编码已存在");
        }
        customer.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 校验客户名称
     */
    @PostMapping("/checkCustomerNameUnique")
    @ResponseBody
    public String checkCustomerNameUnique(Customer customer)
    {
        return customerService.checkCustomerNameUnique(customer);
    }

    /**
     * 校验客户编码
     */
    @PostMapping("/checkCustomerCodeUnique")
    @ResponseBody
    public String checkCustomerCodeUnique(Customer customer)
    {
        return customerService.checkCustomerCodeUnique(customer);
    }
}
