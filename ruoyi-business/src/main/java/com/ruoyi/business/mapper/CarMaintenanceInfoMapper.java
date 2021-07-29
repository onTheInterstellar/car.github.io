package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.CarMaintenanceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 养修信息预约Mapper接口
 * 
 * @author ruoyi
 * @date 2021-05-13
 */
public interface CarMaintenanceInfoMapper 
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
     * 删除养修信息预约
     * 
     * @param id 养修信息预约ID
     * @return 结果
     */
    public int deleteCarMaintenanceInfoById(Long id);

    /**
     * 批量删除养修信息预约
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCarMaintenanceInfoByIds(String[] ids);

    /**
     * 修改养修信息状态
     * @param id 需要修改的数据ID
     * @param status 需要修改的状态
     * @return
     */
    int changeStatus(@Param("id") Long id, @Param("status") Integer status);
    /**
     * 用户到店
     * @param id 需要修改的数据ID
     * @param status 需要修改的状态
     * @return
     */
    int arrival(@Param("id") Long id, @Param("status") Integer status);
}
