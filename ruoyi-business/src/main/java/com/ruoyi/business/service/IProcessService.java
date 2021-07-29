package com.ruoyi.business.service;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface IProcessService {

    Deployment deploy(String filePath) throws FileNotFoundException;

    InputStream getInputStream(String deployId) throws FileNotFoundException;

    InputStream getProcessImg(String deployId);

    ProcessInstance startAuditProcess(String definitionKey, Long businessKey, HashMap<String, Object> variable);

    Task getCurrentTask(String processInstanceId);

    void setCandidateForCurrentNode(String taskKey, Long userId);

    InputStream getProcessImgByInstanceId(String instanceId);

    void deleteProcessInstance(String instanceId);

    Long getTaskCount(String definitionKey, String auditorId);

    List<Task> selectTodoTask(String definitionKey, String auditorId, Integer pageNum, Integer pageSize);

    ProcessInstance getProcessInstanceById(String processInstanceId);

    Task claimAndCompleteTask(String taskId, boolean booleanAudit, String comment);

    Task getCurrentTaskByTaskId(Long taskId);

    String getBusinessKey(String processInstanceId);

    Task claimAndCompleteTaskReApply(Long taskId, BigDecimal discountPrice, String comment);

    Long getHistoricTaskCount(String definitionKey, String auditorId);

    List<HistoricTaskInstance> selectHistoricTaskList(String definitionKey, String auditorId, Integer beginIndex, Integer pageSize);

    String getBusinessKeyByHistoric(String processInstanceId);

    List<HistoricActivityInstance> getHistoricTask(String instanceId);

    String getComment(String taskId);
}

