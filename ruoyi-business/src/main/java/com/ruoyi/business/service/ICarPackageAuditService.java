package com.ruoyi.business.service;

import com.ruoyi.business.domain.CarPackageAudit;
import com.ruoyi.business.domain.HistoricActivity;
import com.ruoyi.business.domain.ServiceItem;

import java.util.List;

/**
 * 审核列Service接口
 * 
 * @author ruoyi
 * @date 2021-07-20
 */
public interface ICarPackageAuditService 
{
    /**
     * 查询审核列
     * 
     * @param id 审核列ID
     * @return 审核列
     */
    public CarPackageAudit selectCarPackageAuditById(Long id);

    /**
     * 查询审核列列表
     * 
     * @param carPackageAudit 审核列
     * @return 审核列集合
     */
    public List<CarPackageAudit> selectCarPackageAuditList(CarPackageAudit carPackageAudit);

    /**
     * 新增审核列
     * 
     * @param carPackageAudit 审核列
     * @return 结果
     */
    public int insertCarPackageAudit(CarPackageAudit carPackageAudit);

    /**
     * 修改审核列
     * 
     * @param carPackageAudit 审核列
     * @return 结果
     */
    public int updateCarPackageAudit(CarPackageAudit carPackageAudit);

    /**
     * 批量删除审核列
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCarPackageAuditByIds(String ids);

    /**
     * 删除审核列信息
     * 
     * @param id 审核列ID
     * @return 结果
     */
    public int deleteCarPackageAuditById(Long id);

    void cancelApply(String instanceId, String carPackageId);

    List<CarPackageAudit> selectTodoList(CarPackageAudit carPackageAudit);

    void complete(Long taskId, String auditStatus, String comment);

    void serviceItemsUpdate(Long carPackageAuditId, ServiceItem serviceItem);

    void reApply(String taskId, String carPackageAuditId);

    List<CarPackageAudit> selectDoneList(CarPackageAudit carPackageAudit);

    List<HistoricActivity> listHistory(String instanceId);
}
