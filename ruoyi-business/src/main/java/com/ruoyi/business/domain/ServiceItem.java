package com.ruoyi.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 养修服务项对象 t_service_item
 * 
 * @author wolfcode
 * @date 2021-05-14
 */
public class ServiceItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    public static final Integer CARPACKAGE_NO = 0;//不是套餐
    public static final Integer CARPACKAGE_YES = 1;//是套餐
    public static final Integer AUDITSTATUS_INIT = 0;//初始化
    public static final Integer AUDITSTATUS_AUDITING = 1;//审核中
    public static final Integer AUDITSTATUS_APPROVED = 2;//审核通过
    public static final Integer AUDITSTATUS_REPLY = 3;//重新调整
    public static final Integer AUDITSTATUS_NO_REQUIRED = 4;//无需审核
    public static final Integer SALESTATUS_OFF = 0;//下架
    public static final Integer SALESTATUS_ON = 1;//上架

    /**  */
    private Long id;

    /** 服务项名称 */
    @Excel(name = "服务项名称")
    private String name;

    /** 服务项原价 */
    @Excel(name = "服务项原价")
    private BigDecimal originalPrice;

    /** 服务项折扣价 */
    @Excel(name = "服务项折扣价")
    private BigDecimal discountPrice;

    /** 是否套餐*/
    @Excel(name = "是否套餐")
    private Integer carPackage;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String info;

    /** 修改时间 */
    private Date modifyTime;

    /** 服务分类 */
    @Excel(name = "服务分类")
    private Integer serviceCatalog;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private Integer auditStatus;

    /** 上架状态 */
    @Excel(name = "上架状态")
    private Integer saleStatus;

    /** 版本号 */
    private Long version;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setOriginalPrice(BigDecimal originalPrice) 
    {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice() 
    {
        return originalPrice;
    }
    public void setDiscountPrice(BigDecimal discountPrice) 
    {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getDiscountPrice() 
    {
        return discountPrice;
    }
    public void setCarPackage(Integer carPackage) 
    {
        this.carPackage = carPackage;
    }

    public Integer getCarPackage() 
    {
        return carPackage;
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
    public void setServiceCatalog(Integer serviceCatalog) 
    {
        this.serviceCatalog = serviceCatalog;
    }

    public Integer getServiceCatalog() 
    {
        return serviceCatalog;
    }
    public void setAuditStatus(Integer auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditStatus() 
    {
        return auditStatus;
    }
    public void setSaleStatus(Integer saleStatus) 
    {
        this.saleStatus = saleStatus;
    }

    public Integer getSaleStatus() 
    {
        return saleStatus;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("originalPrice", getOriginalPrice())
            .append("discountPrice", getDiscountPrice())
            .append("carPackage", getCarPackage())
            .append("info", getInfo())
            .append("createTime", getCreateTime())
            .append("modifyTime", getModifyTime())
            .append("serviceCatalog", getServiceCatalog())
            .append("auditStatus", getAuditStatus())
            .append("saleStatus", getSaleStatus())
            .append("version", getVersion())
            .toString();
    }
}
