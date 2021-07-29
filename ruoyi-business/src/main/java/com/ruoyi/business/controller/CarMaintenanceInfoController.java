package com.ruoyi.business.controller;

import com.ruoyi.business.domain.CarMaintenanceInfo;
import com.ruoyi.business.service.ICarMaintenanceInfoService;
import com.ruoyi.business.service.IServiceItemService;
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
 * 养修信息预约Controller
 * 
 * @author wolfcode
 * @date 2021-05-13
 */
@Controller
@RequestMapping("/business/carMaintenanceInfo")
public class CarMaintenanceInfoController extends BaseController
{
    private String prefix = "business/carMaintenanceInfo";

    @Autowired
    private ICarMaintenanceInfoService carMaintenanceInfoService;
    @Autowired
    private IStatementService statementService;

    @RequiresPermissions("business:carMaintenanceInfo:view")
    @GetMapping()
    public String carMaintenanceInfo()
    {
        return prefix + "/carMaintenanceInfo";
    }

    /**
     * 查询养修信息预约列表
     */
    @RequiresPermissions("business:carMaintenanceInfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CarMaintenanceInfo carMaintenanceInfo)
    {
        startPage();
        List<CarMaintenanceInfo> list = carMaintenanceInfoService.selectCarMaintenanceInfoList(carMaintenanceInfo);
        return getDataTable(list);
    }

    /**
     * 新增养修信息预约
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存养修信息预约
     */
    @RequiresPermissions("business:carMaintenanceInfo:add")
    @Log(title = "养修信息预约", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CarMaintenanceInfo carMaintenanceInfo)
    {
        return toAjax(carMaintenanceInfoService.insertCarMaintenanceInfo(carMaintenanceInfo));
    }

    /**
     * 修改养修信息预约
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        CarMaintenanceInfo carMaintenanceInfo = carMaintenanceInfoService.selectCarMaintenanceInfoById(id);
        mmap.put("carMaintenanceInfo", carMaintenanceInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存养修信息预约
     */
    @RequiresPermissions("business:carMaintenanceInfo:edit")
    @Log(title = "养修信息预约", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CarMaintenanceInfo carMaintenanceInfo)
    {
        return toAjax(carMaintenanceInfoService.updateCarMaintenanceInfo(carMaintenanceInfo));
    }

    /**
     * 删除养修信息预约
     */
    @RequiresPermissions("business:carMaintenanceInfo:remove")
    @Log(title = "养修信息预约", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(carMaintenanceInfoService.deleteCarMaintenanceInfoByIds(ids));
    }
    /**
     * 客户到店
     */
    @RequiresPermissions("business:carMaintenanceInfo:arrival")
    @Log(title = "养修信息预约", businessType = BusinessType.UPDATE)
    @GetMapping( "/arrival")
    @ResponseBody
    public AjaxResult arrival(Long id)
    {
        return toAjax(carMaintenanceInfoService.arrival(id));
    }
    /**
     * 客户取消
     */
    @RequiresPermissions("business:carMaintenanceInfo:cancel")
    @Log(title = "养修信息预约", businessType = BusinessType.UPDATE)
    @GetMapping( "/cancel")
    @ResponseBody
    public AjaxResult cancel(Long id)
    {
        return toAjax(carMaintenanceInfoService.changeStatus(id, CarMaintenanceInfo.STATUS_CANCEL));
    }
    /**
     * 生成结算单
     */
    @RequiresPermissions("business:carMaintenanceInfo:generateStatement")
    @PostMapping("/generateStatement")
    @ResponseBody
    public AjaxResult generateStatement(Long mainteenanceId)
    {
        Long statementId = statementService.selectStatementByMainteenanceId(mainteenanceId);
        if(statementId==null){
            statementId = carMaintenanceInfoService.generateStatement(mainteenanceId);
        }
        return AjaxResult.success(statementId);
    }
}
