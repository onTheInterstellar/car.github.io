package com.ruoyi.business.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.business.domain.CarPackageAudit;
import com.ruoyi.business.domain.HistoricActivity;
import com.ruoyi.business.domain.ServiceItem;
import com.ruoyi.business.service.ICarPackageAuditService;
import com.ruoyi.business.service.IProcessService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 审核列Controller
 * 
 * @author ruoyi
 * @date 2021-07-20
 */
@Controller
    @RequestMapping("/business/carPackageAudit")
public class CarPackageAuditController extends BaseController {
    private String prefix = "business/carPackageAudit";

    @Autowired
    private ICarPackageAuditService carPackageAuditService;

    @Autowired
    private IProcessService processService;

    @RequiresPermissions("business:carPackageAudit:view")
    @GetMapping()
    public String carPackageAudit() {
        return prefix + "/carPackageAudit";
    }

    /**
     * 查询审核列列表
     */
    @RequiresPermissions("business:carPackageAudit:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CarPackageAudit carPackageAudit) {
        startPage();
        List<CarPackageAudit> list = carPackageAuditService.selectCarPackageAuditList(carPackageAudit);
        return getDataTable(list);
    }

    /**
     * 导出审核列列表
     */
    @RequiresPermissions("business:carPackageAudit:export")
    @Log(title = "审核列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CarPackageAudit carPackageAudit) {
        List<CarPackageAudit> list = carPackageAuditService.selectCarPackageAuditList(carPackageAudit);
        ExcelUtil<CarPackageAudit> util = new ExcelUtil<CarPackageAudit>(CarPackageAudit.class);
        return util.exportExcel(list, "审核列数据");
    }

    /**
     * 新增审核列
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存审核列
     */
    @RequiresPermissions("business:carPackageAudit:add")
    @Log(title = "审核列", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CarPackageAudit carPackageAudit) {
        return toAjax(carPackageAuditService.insertCarPackageAudit(carPackageAudit));
    }

    /**
     * 修改审核列
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CarPackageAudit carPackageAudit = carPackageAuditService.selectCarPackageAuditById(id);
        mmap.put("carPackageAudit", carPackageAudit);
        return prefix + "/edit";
    }

    /**
     * 修改保存审核列
     */
    @RequiresPermissions("business:carPackageAudit:edit")
    @Log(title = "审核列", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CarPackageAudit carPackageAudit) {
        return toAjax(carPackageAuditService.updateCarPackageAudit(carPackageAudit));
    }

    /**
     * 删除审核列
     */
    @RequiresPermissions("business:carPackageAudit:remove")
    @Log(title = "审核列", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(carPackageAuditService.deleteCarPackageAuditByIds(ids));
    }


    @RequestMapping("/processImg")
    @ResponseBody
    public void processImg(String instanceId, HttpServletResponse response) throws IOException {
        InputStream inputStream = processService.getProcessImgByInstanceId(instanceId);
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @RequestMapping("/cancelApply")
    @ResponseBody
    public AjaxResult remove(String instanceId, String carPackageId) {

        carPackageAuditService.cancelApply(instanceId, carPackageId);

        return AjaxResult.success("撤销成功");
    }

    @RequestMapping("/todoPage")
    public String todoPage() {
        return prefix + "/todoPage";
    }

    @RequestMapping("/todoList")
    @ResponseBody
    public TableDataInfo todoList(CarPackageAudit carPackageAudit) {
        List<CarPackageAudit> list = carPackageAuditService.selectTodoList(carPackageAudit);
        return getDataTable(list);
    }

    @RequestMapping("/donePage")
    public String donePage() {
        return prefix + "/donePage";
    }


    @RequestMapping("/doneTaskList")
    @ResponseBody
    public TableDataInfo doneTaskList(CarPackageAudit carPackageAudit) {
        List<CarPackageAudit> list = carPackageAuditService.selectDoneList(carPackageAudit);
        return getDataTable(list);
    }

    @RequestMapping("/showAuditDialog/{taskId}")
    public String showAuditDialog(@PathVariable("taskId") Long taskId, ModelMap mmap) {
        mmap.put("taskId", taskId);
        return prefix + "/taskVerify";
    }

    @RequestMapping("/complete/{taskId}")
    @ResponseBody
    public AjaxResult complete(@PathVariable("taskId") Long taskId, String auditStatus, String comment) {
        carPackageAuditService.complete(taskId, auditStatus, comment);
        return AjaxResult.success();
    }


    @RequestMapping("/serviceItemsEditPage")
    public String serviceItemsEditPage(Long carPackageAuditId, ModelMap mmap) {

        CarPackageAudit carPackageAudit = carPackageAuditService.selectCarPackageAuditById(carPackageAuditId);
        ServiceItem serviceItem = JSON.parseObject(carPackageAudit.getServiceItemInfo(), ServiceItem.class);
        mmap.put("serviceItem", serviceItem);
        // 这个是为了保存的时候能够找到  对应的carPackageAudit 修改 serviceItemInfo  后端用隐藏域保存, th:field
        mmap.put("carPackageAuditId", carPackageAuditId);
        return prefix + "/serviceItemsEditPage";

    }

    @RequestMapping("/serviceItemsUpdate")
    @ResponseBody
    public AjaxResult serviceItemsUpdate(Long carPackageAuditId, ServiceItem serviceItem) {
        carPackageAuditService.serviceItemsUpdate(carPackageAuditId, serviceItem);
        return AjaxResult.success();
    }

    @RequestMapping("/reApply")
    @ResponseBody
    public AjaxResult reApply(String taskId, String carPackageAuditId) {
        carPackageAuditService.reApply(taskId, carPackageAuditId);
        return AjaxResult.success();
    }

    //添加 历史审核方法
    @RequestMapping("/historyPage/{instanceId}")
    public String listHistory(@PathVariable("instanceId") Long instanceId, ModelMap mmap) {
        mmap.put("instanceId", instanceId);
        return prefix + "/historyPage";
    }

    @RequestMapping("/listHistory")
    @ResponseBody
    public TableDataInfo listHistory(String instanceId) {
        startPage();
        List<HistoricActivity> historicActivityList = carPackageAuditService.listHistory(instanceId);
        return getDataTable(historicActivityList);
    }

}



































