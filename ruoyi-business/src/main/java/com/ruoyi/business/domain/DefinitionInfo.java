package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 流程定义对象 t_definition_info
 * 
 * @author wolfcode
 * @date 2021-07-19
 */
public class DefinitionInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String name;

    /** 流程key */
    @Excel(name = "流程key")
    private String definitionKey;

    /** 流程描述 */
    @Excel(name = "流程描述")
    private String description;

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
    public void setDefinitionKey(String definitionKey) 
    {
        this.definitionKey = definitionKey;
    }

    public String getDefinitionKey() 
    {
        return definitionKey;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("definitionKey", getDefinitionKey())
            .append("description", getDescription())
            .toString();
    }
}
