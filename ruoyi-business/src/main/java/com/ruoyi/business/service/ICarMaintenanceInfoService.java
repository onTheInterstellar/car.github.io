package com.ruoyi.business.service;

import com.ruoyi.business.domain.CarMaintenanceInfo;

import java.util.List;

/**
 * 养修信息预约Service接口
 * 
 * @author wolfcode
 * @date 2021-05-13
 */
public interface ICarMaintenanceInfoService 
{
    /**
     * 查询养修信息预约
     * 
     * @param id 养修信息预约ID
     * @return 养修信息预约
     */
    public CarMaintenanceInfo selectCarMaintenanceInfoById(Long id);

    /**
     * 查询养修信息预约列表
     * 
     * @param carMaintenanceInfo 养修信息预约
     * @return 养修信息预约集合
     */
    public List<CarMaintenanceInfo> selectCarMaintenanceInfoList(CarMaintenanceInfo carMaintenanceInfo);

    /**
     * 新增养修信息预约
     * 
     * @param carMaintenanceInfo 养修信息预约
     * @return 结果
     */
    public int insertCarMaintenanceInfo(CarMaintenanceInfo carMaintenanceInfo);

    /**
     * 修改养修信息预约
     * 
     * @param carMaintenanceInfo 养修信息预约
     * @return 结果
     */
    public int updateCarMaintenanceInfo(CarMaintenanceInfo carMaintenanceInfo);

    /**
     * 批量删除养修信息预约
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCarMaintenanceInfoByIds(String ids);

    /**
     * 删除养修信息预约信息
     * 
     * @param id 养修信息预约ID
     * @return 结果
     */
    public int deleteCarMaintenanceInfoById(Long id);

    /**
     * 更新养修信息预约状态
     * @param id
     * @param status
     * @return
     */
    int changeStatus(Long id, Integer status);

    /**
     * 用户到店
     * @param id
     * @return
     */
    int arrival(Long id);
    /**
     * 生成结算单信息
     * @param mainteenanceId
     * @return 结算单的ID
     */
    Long generateStatement(Long mainteenanceId);
}
