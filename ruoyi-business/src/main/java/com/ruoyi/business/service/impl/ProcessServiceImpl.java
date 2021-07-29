package com.ruoyi.business.service.impl;

import com.ruoyi.business.service.IProcessService;
import com.ruoyi.common.utils.ShiroUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Service
public class ProcessServiceImpl implements IProcessService {

    @Autowired  //因为配置了activiti 所以会自动生成processEngine 对象, 此处直接使用就可
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired  //因为配置了activiti 所以会自动生成processEngine 对象, 此处直接使用就可
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Override
    public Deployment deploy(String filePath) throws FileNotFoundException {

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deployment = null;

        if (filePath.endsWith(".zip")){
            deployment = deploymentBuilder.addZipInputStream(new ZipInputStream(new FileInputStream(filePath))).deploy();
        } else {
            deployment = deploymentBuilder.addInputStream(filePath, new FileInputStream(filePath)).deploy();
        }

        return deployment;
    }

    @Override
    public InputStream getInputStream(String deployId) throws FileNotFoundException {

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        String resourceName = processDefinition.getResourceName();
        return new FileInputStream(resourceName);
    }

    @Override
    public InputStream getProcessImg(String deployId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployId).singleResult();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel,
                Collections.EMPTY_LIST, Collections.EMPTY_LIST, "宋体",
                "宋体", "宋体");
        return inputStream;
    }

    @Override
    public ProcessInstance startAuditProcess(String definitionKey, Long businessKey, HashMap<String, Object> variable) {
        return runtimeService.startProcessInstanceByKey(definitionKey, businessKey.toString(), variable);
    }


    @Override
    public Task getCurrentTask(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId)
                .singleResult();
    }

    @Override
    public void setCandidateForCurrentNode(String taskId, Long userId) {
        taskService.addCandidateUser(taskId, userId.toString());
    }

    @Override
    public InputStream getProcessImgByInstanceId(String instanceId) {

        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(instanceId).singleResult();

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(instanceId).singleResult();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());

        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        //获取目前正在流转的节点
        InputStream inputStream;
        if (processInstance != null) {

            List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstance.getId());

            inputStream = diagramGenerator.generateDiagram(bpmnModel,
                    activeActivityIds, Collections.EMPTY_LIST, "宋体",
                    "宋体", "宋体");
        } else {
            inputStream = diagramGenerator.generateDiagram(bpmnModel,
                    Collections.EMPTY_LIST, Collections.EMPTY_LIST, "宋体",
                    "宋体", "宋体");
        }
        return inputStream;
    }

    @Override
    public void deleteProcessInstance(String instanceId) {
        runtimeService.deleteProcessInstance(instanceId, "流程撤销");
    }

    @Override
    public Long getTaskCount(String definitionKey, String auditorId) {
        return taskService.createTaskQuery().processDefinitionKey(definitionKey).taskCandidateUser(auditorId).count();
    }

    /**
     * 查询指定处理人的所有历史任务
     * @param definitionKey
     * @param auditorId
     * @return
     */
    @Override
    public Long getHistoricTaskCount(String definitionKey, String auditorId) {
        return historyService.createHistoricTaskInstanceQuery()
                .processDefinitionKey(definitionKey)
                .taskAssignee(auditorId)
                .finished()
                .count();
    }

    @Override
    public ProcessInstance getProcessInstanceById(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        return processInstance;
    }

    @Override
    public List<Task> selectTodoTask(String definitionKey, String auditorId, Integer pageNum, Integer pageSize) {

        return taskService.createTaskQuery()
                .processDefinitionKey(definitionKey)
                .taskCandidateUser(auditorId)
                .orderByTaskCreateTime()
                .desc()
                .listPage(pageNum, pageSize);

    }

    /**
     * 查询历史已完成任务
     * @param definitionKey
     * @param auditorId
     * @param beginIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<HistoricTaskInstance> selectHistoricTaskList(String definitionKey, String auditorId, Integer beginIndex, Integer pageSize) {

        return historyService.createHistoricTaskInstanceQuery()
                .processDefinitionKey(definitionKey)
                .taskAssignee(auditorId)
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .listPage(beginIndex, pageSize);

    }

    @Override
    @Transactional
    public Task claimAndCompleteTask(String taskId, boolean booleanAudit, String comment) {

        //获取任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //领取
        taskService.claim(taskId, ShiroUtils.getUserId().toString());
        //任务实例id
        String processInstanceId = task.getProcessInstanceId();
        //任务key
        String taskDefinitionKey = task.getTaskDefinitionKey();
        //设置变量
        Map<String, Object> variable = new HashMap<>();
        variable.put(taskDefinitionKey, booleanAudit);
        //添加批注
        taskService.addComment(taskId, processInstanceId, comment);
        taskService.setVariables(taskId, variable);
        //完成任务
        taskService.complete(taskId);

        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }

    /**
     * 重新提交, 完成任务
     * @param taskId
     * @param discountPrice
     * @param comment
     * @return
     */
    @Override
    public Task claimAndCompleteTaskReApply(Long taskId, BigDecimal discountPrice, String comment) {
        //获取任务
        Task task = taskService.createTaskQuery().taskId(taskId.toString()).singleResult();
        //领取
        taskService.claim(taskId.toString(), ShiroUtils.getUserId().toString());
        //任务实例id
        String processInstanceId = task.getProcessInstanceId();
        //修改流程变量
        taskService.setVariable(taskId.toString(), "money", discountPrice.doubleValue());
        //完成任务
        taskService.complete(taskId.toString());

        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }

    @Override
    public Task getCurrentTaskByTaskId(Long taskId) {
        return taskService.createTaskQuery().taskId(taskId.toString()).singleResult();
    }

    @Override
    public String getBusinessKey(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();
        return processInstance.getBusinessKey();
    }

    @Override
    public String getBusinessKeyByHistoric(String processInstanceId) {

        return historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult()
                .getBusinessKey();
    }

    @Override
    public List<HistoricActivityInstance> getHistoricTask(String instanceId) {

        List<HistoricActivityInstance> userTask = historyService.createHistoricActivityInstanceQuery()
                .activityType("userTask")
                .processInstanceId(instanceId)
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .desc()
                .list();

        return userTask;
    }

    @Override
    public String getComment(String taskId) {

        List<Comment> taskComments = taskService.getTaskComments(taskId,"comment");

        //这里添加是否为0 的条件很重要, 因为外层有一个循环有时候会导致集合为空
        if (taskComments != null && taskComments.size() > 0) {
            return taskComments.get(0).getFullMessage();
        } else {
            return "";
        }
    }
}