package com.ruoyi.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 流程定义明细对象 t_definition_detail
 * 
 * @author wolfcode
 * @date 2021-07-19
 */
public class DefinitionDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程定义id */
    @Excel(name = "流程定义id")
    private DefinitionInfo definitionInfo;

    /** 流程部署id */
    @Excel(name = "流程部署id")
    private String deploymentId;

    /** 部署时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "部署时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deployTime;

    //流程描述
    private String description;

    //部署的Key
    private String deployKey;

    //版本号
    private String version;

    @Override
    public String toString() {
        return "DefinitionDetail{" +
                "definitionInfo=" + definitionInfo +
                ", deploymentId='" + deploymentId + '\'' +
                ", deployTime=" + deployTime +
                ", description='" + description + '\'' +
                ", deployKey='" + deployKey + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public DefinitionInfo getDefinitionInfo() {
        return definitionInfo;
    }

    public void setDefinitionInfo(DefinitionInfo definitionInfo) {
        this.definitionInfo = definitionInfo;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Date getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Date deployTime) {
        this.deployTime = deployTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeployKey() {
        return deployKey;
    }

    public void setDeployKey(String deployKey) {
        this.deployKey = deployKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
