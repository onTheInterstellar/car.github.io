package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class Customer extends BaseEntity {
    @Excel(name = "消费者ID")
    private Long id;
    @Excel(name = "消费者名称")
    private String name;
    @Excel(name = "联系方式")
    private String phone;
    @Excel(name = "来源渠道")
    private Long sourceValue;
    @Excel(name = "意向校区")
    private Long schoolValue;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", sourceValue=" + sourceValue +
                ", schoolValue=" + schoolValue +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(Long sourceValue) {
        this.sourceValue = sourceValue;
    }

    public Long getSchoolValue() {
        return schoolValue;
    }

    public void setSchoolValue(Long schoolValue) {
        this.schoolValue = schoolValue;
    }
}
