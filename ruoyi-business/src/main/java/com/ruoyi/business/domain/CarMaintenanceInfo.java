package com.ruoyi.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 养修信息预约对象 t_car_maintenance_info
 * 
 * @author wolfcode
 * @date 2021-05-13
 */
public class CarMaintenanceInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    public static final Integer STATUS_APPOINTMENT = 0;//预约中
    public static final Integer STATUS_ARRIVAL = 1;//已到店
    public static final Integer STATUS_CANCEL = 2;//用户取消
    public static final Integer STATUS_OVERTIME = 3;//超时取消
    public static final Integer STATUS_SETTLE  = 4;//已结算
    /**  */
    private Long id;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String customerName;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String customerPhone;

    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date appointmentTime;

    /** 实际到店时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date actualArrivalTime;

    /** 车牌号码 */
    @Excel(name = "车牌号码")
    private String licensePlate;

    /** 汽车类型 */
    @Excel(name = "汽车类型")
    private String carSeries;

    /** 服务类型 */
    @Excel(name = "服务类型")
    private Integer serviceType;

    /** 修改时间 */
    private Date modifyTime;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String info;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setAppointmentTime(Date appointmentTime)
    {
        this.appointmentTime = appointmentTime;
    }

    public Date getAppointmentTime() 
    {
        return appointmentTime;
    }
    public void setActualArrivalTime(Date actualArrivalTime) 
    {
        this.actualArrivalTime = actualArrivalTime;
    }

    public Date getActualArrivalTime() 
    {
        return actualArrivalTime;
    }
    public void setLicensePlate(String licensePlate) 
    {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() 
    {
        return licensePlate;
    }
    public void setCarSeries(String carSeries) 
    {
        this.carSeries = carSeries;
    }

    public String getCarSeries() 
    {
        return carSeries;
    }
    public void setServiceType(Integer serviceType) 
    {
        this.serviceType = serviceType;
    }

    public Integer getServiceType() 
    {
        return serviceType;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerName", getCustomerName())
            .append("customerPhone", getCustomerPhone())
            .append("appointmentTime", getAppointmentTime())
            .append("actualArrivalTime", getActualArrivalTime())
            .append("licensePlate", getLicensePlate())
            .append("carSeries", getCarSeries())
            .append("serviceType", getServiceType())
            .append("createTime", getCreateTime())
            .append("modifyTime", getModifyTime())
            .append("info", getInfo())
            .append("status", getStatus())
            .toString();
    }
}
