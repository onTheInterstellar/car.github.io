package com.ruoyi.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 潜在客户对象 t_potential_customer
 * 
 * @author ruoyi
 * @date 2021-07-26
 */
public class PotentialCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String name;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String phone;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String info;

    /** 跟进人 */
    @Excel(name = "跟进人")
    private Long followUpUser;

    /** 最后跟进时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后跟进时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastFollowUpTime;

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
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }
    public void setFollowUpUser(Long followUpUser) 
    {
        this.followUpUser = followUpUser;
    }

    public Long getFollowUpUser() 
    {
        return followUpUser;
    }
    public void setLastFollowUpTime(Date lastFollowUpTime) 
    {
        this.lastFollowUpTime = lastFollowUpTime;
    }

    public Date getLastFollowUpTime() 
    {
        return lastFollowUpTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("phone", getPhone())
            .append("info", getInfo())
            .append("createTime", getCreateTime())
            .append("followUpUser", getFollowUpUser())
            .append("lastFollowUpTime", getLastFollowUpTime())
            .toString();
    }
}
