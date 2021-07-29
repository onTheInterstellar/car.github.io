package com.ruoyi.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 结算单明细对象 t_statement_item
 * 
 * @author wolfcode
 * @date 2021-05-17
 */
public class StatementItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 结算单ID */
    private Long statementId;

    /** 服务项明细ID */
    private Long itemId;

    /** 服务项明细版本号 */
    private Long itemVersion;

    /** 服务项名称 */
    @Excel(name = "服务项名称")
    private String itemName;

    /** 服务项价格 */
    @Excel(name = "服务项价格")
    private BigDecimal itemPrice;

    /** 购买数量 */
    @Excel(name = "购买数量")
    private BigDecimal itemQuantity;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStatementId(Long statementId) 
    {
        this.statementId = statementId;
    }

    public Long getStatementId() 
    {
        return statementId;
    }
    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }
    public void setItemVersion(Long itemVersion) 
    {
        this.itemVersion = itemVersion;
    }

    public Long getItemVersion() 
    {
        return itemVersion;
    }
    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }
    public void setItemPrice(BigDecimal itemPrice) 
    {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getItemPrice() 
    {
        return itemPrice;
    }

    public BigDecimal getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(BigDecimal itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("statementId", getStatementId())
            .append("itemId", getItemId())
            .append("itemVersion", getItemVersion())
            .append("itemName", getItemName())
            .append("itemPrice", getItemPrice())
            .append("itemQuantity", getItemQuantity())
            .toString();
    }
}
