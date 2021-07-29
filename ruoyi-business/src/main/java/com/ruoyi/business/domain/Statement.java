package com.ruoyi.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算单对象 t_statement
 * 
 * @author wolfcode
 * @date 2021-05-17
 */
public class Statement extends BaseEntity
{
    public static final Integer STATUS_CONSUME = 0;//消费中
    public static final Integer STATUS_PAID = 1;//已支付
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String customerName;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String customerPhone;

    /** 到店时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "到店时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
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

    /** 预约单ID */
    @Excel(name = "预约单ID")
    private Long maintenanceId;

    /** 结算状态 */
    @Excel(name = "结算状态")
    private Integer status;

    /** 收款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /** 收款人id */
    @Excel(name = "收款人id")
    private SysUser payee;

    /** 总消费金额 */
    @Excel(name = "总消费金额")
    private BigDecimal totalAmount;

    /** 服务项数量 */
    @Excel(name = "服务项数量")
    private BigDecimal totalQuantity;

    /** 折扣金额 */
    @Excel(name = "折扣金额")
    private BigDecimal discountAmount;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String info;

    /** 修改时间 */
    private Date modifyTime;

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
    public void setCustomerPhone(String customerPhone) 
    {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone() 
    {
        return customerPhone;
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

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public void setMaintenanceId(Long maintenanceId)
    {
        this.maintenanceId = maintenanceId;
    }

    public Long getMaintenanceId() 
    {
        return maintenanceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public Date getPayTime() 
    {
        return payTime;
    }

    public SysUser getPayee() {
        return payee;
    }

    public void setPayee(SysUser payee) {
        this.payee = payee;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }
    public void setTotalQuantity(BigDecimal totalQuantity) 
    {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalQuantity() 
    {
        return totalQuantity;
    }
    public void setDiscountAmount(BigDecimal discountAmount) 
    {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountAmount() 
    {
        return discountAmount;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerName", getCustomerName())
            .append("customerPhone", getCustomerPhone())
            .append("actualArrivalTime", getActualArrivalTime())
            .append("licensePlate", getLicensePlate())
            .append("carSeries", getCarSeries())
            .append("serviceType", getServiceType())
            .append("maintenanceId", getMaintenanceId())
            .append("status", getStatus())
            .append("payTime", getPayTime())
            .append("totalAmount", getTotalAmount())
            .append("totalQuantity", getTotalQuantity())
            .append("discountAmount", getDiscountAmount())
            .append("info", getInfo())
            .append("createTime", getCreateTime())
            .append("modifyTime", getModifyTime())
            .toString();
    }
}
