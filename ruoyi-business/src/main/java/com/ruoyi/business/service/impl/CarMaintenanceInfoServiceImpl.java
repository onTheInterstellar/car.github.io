package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.CarMaintenanceInfo;
import com.ruoyi.business.domain.Statement;
import com.ruoyi.business.mapper.CarMaintenanceInfoMapper;
import com.ruoyi.business.service.ICarMaintenanceInfoService;
import com.ruoyi.business.service.IStatementService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 养修信息预约Service业务层处理
 * 
 * @author wolfcode
 * @date 2021-05-13
 */
@Service
public class CarMaintenanceInfoServiceImpl implements ICarMaintenanceInfoService
{
    @Autowired
    private CarMaintenanceInfoMapper carMaintenanceInfoMapper;
    @Autowired
    private IStatementService statementService;
    /**
     * 查询养修信息预约
     * 
     * @param id 养修信息预约ID
     * @return 养修信息预约
     */
    @Override
    public CarMaintenanceInfo selectCarMaintenanceInfoById(Long id)
    {
        return carMaintenanceInfoMapper.selectCarMaintenanceInfoById(id);
    }
    /**
     * 查询养修信息预约列表
     * 
     * @param carMaintenanceInfo 养修信息预约
     * @return 养修信息预约
     */
    @Override
    public List<CarMaintenanceInfo> selectCarMaintenanceInfoList(CarMaintenanceInfo carMaintenanceInfo)
    {
        return carMaintenanceInfoMapper.selectCarMaintenanceInfoList(carMaintenanceInfo);
    }
    /**
     * 新增养修信息预约
     * 
     * @param carMaintenanceInfo 养修信息预约
     * @return 结果
     */
    @Override
    public int insertCarMaintenanceInfo(CarMaintenanceInfo carMaintenanceInfo)
    {
        carMaintenanceInfo.setCreateTime(DateUtils.getNowDate());
        return carMaintenanceInfoMapper.insertCarMaintenanceInfo(carMaintenanceInfo);
    }
    /**
     * 修改养修信息预约
     * 
     * @param carMaintenanceInfo 养修信息预约
     * @return 结果
     */
    @Override
    public int updateCarMaintenanceInfo(CarMaintenanceInfo carMaintenanceInfo)
    {
        return carMaintenanceInfoMapper.updateCarMaintenanceInfo(carMaintenanceInfo);
    }
    /**
     * 删除养修信息预约对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCarMaintenanceInfoByIds(String ids)
    {
        return carMaintenanceInfoMapper.deleteCarMaintenanceInfoByIds(Convert.toStrArray(ids));
    }
    /**
     * 删除养修信息预约信息
     * 
     * @param id 养修信息预约ID
     * @return 结果
     */
    @Override
    public int deleteCarMaintenanceInfoById(Long id)
    {
        return carMaintenanceInfoMapper.deleteCarMaintenanceInfoById(id);
    }

    /**
     * 客户到店
     * @param id
     * @return
     */
    @Override
    public int arrival(Long id) {
        return carMaintenanceInfoMapper.arrival(id, CarMaintenanceInfo.STATUS_ARRIVAL);
    }

    /**
     * 修改状态
     * @param id
     * @param status
     * @return
     */
    @Override
    public int changeStatus(Long id, Integer status) {
        return carMaintenanceInfoMapper.changeStatus(id,status);
    }
    /**
     * 生成结算单信息
     * @param mainteenanceId
     * @return 结算单的ID
     */
    @Override
    @Transactional
    public Long generateStatement(Long mainteenanceId) {
        CarMaintenanceInfo carMaintenanceInfo = carMaintenanceInfoMapper.selectCarMaintenanceInfoById(mainteenanceId);
        //修改状态，状态变成结算生成
        carMaintenanceInfoMapper.changeStatus(mainteenanceId,CarMaintenanceInfo.STATUS_SETTLE);
        Statement statement = generate(carMaintenanceInfo);
        statementService.insertStatement(statement);
        return statement.getId();
    }
    private Statement generate(CarMaintenanceInfo carMaintenanceInfo) {
        Statement statement = new Statement();
        statement.setActualArrivalTime(carMaintenanceInfo.getActualArrivalTime());//实际到店时间
        statement.setCarSeries(carMaintenanceInfo.getCarSeries());//汽车类型
        statement.setCustomerName(carMaintenanceInfo.getCustomerName());//客户名称
        statement.setLicensePlate(carMaintenanceInfo.getLicensePlate());//车牌号码
        statement.setMaintenanceId(carMaintenanceInfo.getId());//预约单ID
        statement.setServiceType(carMaintenanceInfo.getServiceType());//服务类型
        statement.setCustomerPhone(carMaintenanceInfo.getCustomerPhone());//用户联系方式
        return statement;
    }
}
