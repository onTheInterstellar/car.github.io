package com.ruoyi.business.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 审核列对象 t_car_package_audit
 * 
 * @author ruoyi
 * @date 2021-07-20
 */
public class CarPackageAudit extends BaseEntity
{
    public static final String DEFINITION_KEY = "carPackageAudit";//汽车套餐流程key
    public static final Integer STATUS_IN_ROGRESS = 0;//审核中
    public static final Integer STATUS_REJECT = 1;//审核拒绝
    public static final Integer STATUS_PASS = 2;//审核通过
    public static final Integer STATUS_CANCEL = 3;//审核撤销
    private static final long serialVersionUID = 1L;
    /** $column.columnComment */
    private Long id;
    /** 审核服务项ID */
    private ServiceItem serviceItem;
    /** 服务项信息(审核完成才存储，JSON格式.) */
    private String serviceItemInfo;
    /** 状态【审核中0/审核拒绝1/审核通过/审核撤销】 */
    private Integer status;
    /** 流程定义ID */
    private String processDefinitionId;
    /** 流程实例ID */
    private String instanceId;
    /** 任务ID */
    private String taskId;
    /** 任务名称 */
    private String taskName;
    /** 申请人 */
    private String createByName;
    /** 当前节点审核人**/
    private String auditors;
    //任务完成时间
    private Date doneTime;


    @Override
    public String toString() {
        return "CarPackageAudit{" +
                "id=" + id +
                ", serviceItem=" + serviceItem +
                ", serviceItemInfo='" + serviceItemInfo + '\'' +
                ", status=" + status +
                ", processDefinitionId='" + processDefinitionId + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", createByName='" + createByName + '\'' +
                ", auditors='" + auditors + '\'' +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public String getServiceItemInfo() {
        return serviceItemInfo;
    }

    public void setServiceItemInfo(String serviceItemInfo) {
        this.serviceItemInfo = serviceItemInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getAuditors() {
        return auditors;
    }

    public void setAuditors(String auditors) {
        this.auditors = auditors;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }
}
