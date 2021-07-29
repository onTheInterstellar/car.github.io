package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 流程定义节点信息对象 t_definition_node
 * 
 * @author wolfcode
 * @date 2021-07-20
 */
public class DefinitionNode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    private Long[] auditors;

    /** 流程定义ID */
    private Long definitionInfoId;

    /** 节点key */
    @Excel(name = "节点key")
    private String nodeKey;

    /** 节点描述 */
    @Excel(name = "节点描述")
    private String nodeDescription;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDefinitionInfoId(Long definitionInfoId) 
    {
        this.definitionInfoId = definitionInfoId;
    }

    public Long getDefinitionInfoId() 
    {
        return definitionInfoId;
    }
    public void setNodeKey(String nodeKey) 
    {
        this.nodeKey = nodeKey;
    }

    public String getNodeKey() 
    {
        return nodeKey;
    }
    public void setNodeDescription(String nodeDescription) 
    {
        this.nodeDescription = nodeDescription;
    }

    public String getNodeDescription() 
    {
        return nodeDescription;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("definitionInfoId", getDefinitionInfoId())
            .append("nodeKey", getNodeKey())
            .append("nodeDescription", getNodeDescription())
            .toString();
    }

    public Long[] getAuditors() {
        return auditors;
    }

    public void setAuditors(Long[] auditors) {
        this.auditors = auditors;
    }
}
