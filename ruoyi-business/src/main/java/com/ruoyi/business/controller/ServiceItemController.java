package com.ruoyi.business.controller;

import com.ruoyi.business.domain.ServiceItem;
import com.ruoyi.business.service.IServiceItemService;
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
 * 养修服务项Controller
 * 
 * @author wolfcode
 * @date 2021-05-14
 */
@Controller
@RequestMapping("/business/serviceItem")
public class ServiceItemController extends BaseController
{
    private String prefix = "business/serviceItem";
    @Autowired
    private IServiceItemService serviceItemService;

    @RequiresPermissions("business:serviceItem:view")
    @GetMapping()
    public String serviceItem()
    {
        return prefix + "/serviceItem";
    }

    /**
     * 查询养修服务项列表
     */
    @RequiresPermissions("business:serviceItem:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ServiceItem serviceItem)
    {
        startPage();
        List<ServiceItem> list = serviceItemService.selectServiceItemList(serviceItem);
        return getDataTable(list);
    }
    @RequiresPermissions("business:serviceItem:selectAllSaleOnList")
    @PostMapping("/selectAllSaleOnList")
    @ResponseBody
    public TableDataInfo selectAllSaleOnList(ServiceItem serviceItem)
    {
        List<ServiceItem> list = serviceItemService.selectAllSaleOnList(serviceItem);
        return getDataTable(list);
    }

    /**
     * 新增养修服务项
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存养修服务项
     */
    @RequiresPermissions("business:serviceItem:add")
    @Log(title = "养修服务项", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ServiceItem serviceItem)
    {
        return toAjax(serviceItemService.insertServiceItem(serviceItem));
    }

    /**
     * 修改养修服务项
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ServiceItem serviceItem = serviceItemService.selectServiceItemById(id);
        mmap.put("serviceItem", serviceItem);
        return prefix + "/edit";
    }

    /**
     * 修改保存养修服务项
     */
    @RequiresPermissions("business:serviceItem:edit")
    @Log(title = "养修服务项", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ServiceItem serviceItem)
    {
        return toAjax(serviceItemService.updateServiceItem(serviceItem));
    }

    /**
     * 删除养修服务项
     */
    @RequiresPermissions("business:serviceItem:remove")
    @Log(title = "养修服务项", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(serviceItemService.deleteServiceItemByIds(ids));
    }
    /**
     * 下架
     */
    @RequiresPermissions("business:serviceItem:saleOff")
    @Log(title = "养修服务项", businessType = BusinessType.UPDATE)
    @PostMapping( "/saleOff")
    @ResponseBody
    public AjaxResult saleOff(Long id)
    {
        return toAjax(serviceItemService.saleOff(id));
    }
    /**
     * 上架
     */
    @RequiresPermissions("business:serviceItem:saleOn")
    @Log(title = "养修服务项", businessType = BusinessType.UPDATE)
    @PostMapping( "/saleOn")
    @ResponseBody
    public AjaxResult saleOn(Long id)
    {
        return toAjax(serviceItemService.saleOn(id));
    }

    @Log(title = "养修服务项", businessType = BusinessType.OTHER)
    @PostMapping("/startAudit")
    @ResponseBody
    public AjaxResult startAudit(Long id)
    {
        return toAjax(serviceItemService.startAuditById(id));
    }

}
