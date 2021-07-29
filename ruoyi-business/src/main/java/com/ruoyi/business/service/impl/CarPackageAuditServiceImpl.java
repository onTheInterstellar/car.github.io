package com.ruoyi.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.ruoyi.business.domain.CarPackageAudit;
import com.ruoyi.business.domain.HistoricActivity;
import com.ruoyi.business.domain.ServiceItem;
import com.ruoyi.business.mapper.CarPackageAuditMapper;
import com.ruoyi.business.service.ICarPackageAuditService;
import com.ruoyi.business.service.IProcessService;
import com.ruoyi.business.service.IServiceItemService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 审核列Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-07-20
 */
@Service
public class CarPackageAuditServiceImpl implements ICarPackageAuditService 
{
    @Autowired
    private CarPackageAuditMapper carPackageAuditMapper;

    @Autowired
    private IProcessService processService;

    @Autowired
    private IServiceItemService serviceItemService;

    @Autowired
    private ISysUserService sysUserService;
    /**
     * 查询审核列
     * 
     * @param id 审核列ID
     * @return 审核列
     */
    @Override
    public CarPackageAudit selectCarPackageAuditById(Long id)
    {
        return carPackageAuditMapper.selectCarPackageAuditById(id);
    }

    /**
     * 查询审核列列表
     * 
     * @param carPackageAudit 审核列
     * @return 审核列
     */
    @Override
    public List<CarPackageAudit> selectCarPackageAuditList(CarPackageAudit carPackageAudit)
    {
        carPackageAudit.setCreateBy(ShiroUtils.getUserId().toString());
        //查询carPackageAudit 对象
        List<CarPackageAudit> audits = carPackageAuditMapper.selectCarPackageAuditList(carPackageAudit);
        for (CarPackageAudit audit : audits) {
            //取出 serviceItemInfo 解析, 得到serviceItem 赋值
            ServiceItem item = JSON.parseObject(audit.getServiceItemInfo(), ServiceItem.class);
            audit.setServiceItem(item);
            //根据流程实例id 获取当前任务
            Task currentTask = processService.getCurrentTask(audit.getInstanceId());
            if (currentTask != null) {
                audit.setTaskId(currentTask.getId());
                audit.setTaskName(currentTask.getName());
            } else {
                audit.setTaskName("已结束");
            }

        }
        return audits;
    }

    /**
     * 新增审核列
     * 
     * @param carPackageAudit 审核列
     * @return 结果
     */
    @Override
    public int insertCarPackageAudit(CarPackageAudit carPackageAudit)
    {
        carPackageAudit.setCreateTime(DateUtils.getNowDate());
        return carPackageAuditMapper.insertCarPackageAudit(carPackageAudit);
    }

    /**
     * 修改审核列
     * 
     * @param carPackageAudit 审核列
     * @return 结果
     */
    @Override
    public int updateCarPackageAudit(CarPackageAudit carPackageAudit)
    {
        return carPackageAuditMapper.updateCarPackageAudit(carPackageAudit);
    }

    /**
     * 删除审核列对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCarPackageAuditByIds(String ids)
    {
        return carPackageAuditMapper.deleteCarPackageAuditByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除审核列信息
     * 
     * @param id 审核列ID
     * @return 结果
     */
    @Override
    public int deleteCarPackageAuditById(Long id)
    {
        return carPackageAuditMapper.deleteCarPackageAuditById(id);
    }

    public void cancelApply(String instanceId, String carPackageId) {
        //i. 修改carPackageAudit 表中正在审核的数据 , 状态改成撤销, 且不应该有审核人
        CarPackageAudit carPackageAudit = carPackageAuditMapper.selectCarPackageAuditById(Long.valueOf(carPackageId));
        //setStatus
        carPackageAudit.setStatus(CarPackageAudit.STATUS_CANCEL);
        //setAuditors
        carPackageAudit.setAuditors("");
        carPackageAuditMapper.updateCarPackageAudit(carPackageAudit);
        //ii. 杨修服务项 , 审核状态改成 0
        ServiceItem item = JSON.parseObject(carPackageAudit.getServiceItemInfo(), ServiceItem.class);
        item.setAuditStatus(ServiceItem.AUDITSTATUS_INIT);
        //这里因为原来方法的罗技, 不能使用原来的更新方法, 应该在重写一个
        serviceItemService.updateServiceItemNoCondition(item);
        //1) 通过serviceItemInfo修改 serviceItem实例 并更新
              //  删除流程实例
        processService.deleteProcessInstance(instanceId);
    }

    @Override
    public List<CarPackageAudit> selectTodoList(CarPackageAudit carPackageAudit) {

        //获取 当前用户id
        String auditorId = ShiroUtils.getUserId().toString();

        Long countOfTask = processService.getTaskCount(CarPackageAudit.DEFINITION_KEY, auditorId);

        if (countOfTask > 0) {
            PageDomain pageDomain = TableSupport.buildPageRequest();
            Integer pageNum = pageDomain.getPageNum();
            Integer pageSize = pageDomain.getPageSize();
            Integer beginIndex = (pageNum - 1) * pageSize;

            List<Task> todoTaskList = processService.selectTodoTask(
                    CarPackageAudit.DEFINITION_KEY,
                    auditorId,
                    beginIndex,
                    pageSize
            );
            ArrayList<CarPackageAudit> list = new ArrayList<>();
            for (Task task : todoTaskList) {
                ProcessInstance processInstance = processService.getProcessInstanceById(task.getProcessInstanceId());
                String businessKey = processInstance.getBusinessKey();
                CarPackageAudit carPackage = carPackageAuditMapper.selectCarPackageAuditById(Long.valueOf(businessKey));
                carPackage.setServiceItem(JSON.parseObject(carPackage.getServiceItemInfo(), ServiceItem.class));
                carPackage.setTaskId(task.getId());
                carPackage.setTaskName(task.getName());
                SysUser sysUser = sysUserService.selectUserById(ShiroUtils.getUserId());
                carPackage.setCreateByName(sysUser.getUserName());

                list.add(carPackage);
            }
            Page<CarPackageAudit> carPackage = new Page<>();
            carPackage.setPageNum(pageNum);
            carPackage.setPageSize(pageSize);
            carPackage.setTotal(list.size());
            carPackage.addAll(list);
            return carPackage;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    @Transactional
    public List<CarPackageAudit> selectDoneList(CarPackageAudit carPackageAudit) {
        //获取当前登录的用户id
        String auditorId = ShiroUtils.getUserId().toString();

        //processService.getHistoricTaskCount 获取总数
        Long countOfTask = processService.getHistoricTaskCount(CarPackageAudit.DEFINITION_KEY, auditorId);

        //判断 是否有数值
        if (countOfTask > 0) {
            //if count > 0 , 获取前台传入的pageNum, 和pageSize
            PageDomain pageDomain = TableSupport.buildPageRequest();
            Integer pageNum = pageDomain.getPageNum();
            Integer pageSize = pageDomain.getPageSize();
            Integer beginIndex = (pageNum - 1) * pageSize;

            //processService.selectHistoricTaskList( , , , )
            List<HistoricTaskInstance> historicTaskList = processService.selectHistoricTaskList(
                    CarPackageAudit.DEFINITION_KEY,
                    auditorId,
                    beginIndex,
                    pageSize
            );

            ArrayList<CarPackageAudit> list = new ArrayList<>();
            //遍历， 获取历史流程实例 processService.getHistoricProcessInstanceById(processInstanceId)
            for (HistoricTaskInstance historicTaskInstance : historicTaskList) {
                //获取businessKey, 获取carPackage对象
                String businessKey = processService
                        .getBusinessKeyByHistoric(historicTaskInstance.getProcessInstanceId());

                CarPackageAudit historyAudit = carPackageAuditMapper
                        .selectCarPackageAuditById(Long.valueOf(businessKey));

                //设置 serviceItem, TaskName, taskId, doneTime
                historyAudit.setServiceItem(
                        JSON.parseObject(historyAudit.getServiceItemInfo(), ServiceItem.class)
                );

                historyAudit.setTaskId(historicTaskInstance.getId());
                historyAudit.setTaskName(historicTaskInstance.getName());
                historyAudit.setDoneTime(historicTaskInstance.getEndTime());

                historyAudit.setCreateByName(sysUserService
                        .selectUserById(Long.valueOf(historyAudit.getCreateBy())).getUserName());

                list.add(historyAudit);
            }
            Page<CarPackageAudit> carPackageAuditsList = new Page<>();
            carPackageAuditsList.setTotal(list.size());
            carPackageAuditsList.setPageSize(pageSize);
            carPackageAuditsList.setPageNum(pageNum);
            carPackageAuditsList.addAll(list);

            return carPackageAuditsList;

        } else {

            return Collections.EMPTY_LIST;
        }

    }

    @Override
    @Transactional
    public void complete(Long taskId, String auditStatus, String comment) {

        boolean booleanAudit = BooleanUtils.toBoolean(auditStatus);
        String commentStr = booleanAudit?"[同意]":"[拒绝]";
        if (StringUtils.isEmpty(comment)) {
            commentStr += comment;
        }
        //  任务属于候选人任务,先领取.
        Task currentTask = processService.getCurrentTaskByTaskId(taskId);
        //  给任务添加批注信息
        String businessKey = processService.getBusinessKey(currentTask.getProcessInstanceId());
        //  给任务添加流程变量 流程变量的名字当前处理任务的id  流程的值 auditStatus
        Task nextTask = processService.claimAndCompleteTask(currentTask.getId(), booleanAudit, commentStr);
        //判断流程是否已经结束了.
        //如果流程结束了,审核就通过
        //如何判断流程已经结束了?
        //根据流程实例ID获取下一个节点. 下一个节点为空: 流程已经结束,审核通过  下一个不为空: 流程没有结束
        CarPackageAudit carPackageAudit = carPackageAuditMapper.selectCarPackageAuditById(Long.valueOf(businessKey));
        List<String> userName = new ArrayList<>();
        if (nextTask == null) {
            //下一个节点为空: 流程已经结束
            //    t_car_package_audit和t_service_item修改审核状态
            carPackageAudit.setStatus(CarPackageAudit.STATUS_PASS);
            carPackageAudit.setAuditors("");
            carPackageAuditMapper.updateCarPackageAudit(carPackageAudit);
            ServiceItem serviceItem = JSON.parseObject(carPackageAudit.getServiceItemInfo(), ServiceItem.class);
            serviceItem.setAuditStatus(ServiceItem.AUDITSTATUS_APPROVED);
            serviceItemService.updateServiceItemNoCondition(serviceItem);
        } else {
            String taskDefinitionKey = nextTask.getTaskDefinitionKey();
            List<SysUser> currentAuditor = serviceItemService.selectAuditorsByTaskKey(taskDefinitionKey);
            //下一个节点不为空: 流程节点没有结束
            //    如果auditStatus为true
            if (booleanAudit) {
                //        根据下一个节点的Id在t_definition_node寻找该节点的审核人,给该节点设置候选人 , 同时当前的审核人也应该改变
                // , 将auditors重新设置
                for (SysUser sysUser : currentAuditor) {
                    processService.setCandidateForCurrentNode(nextTask.getId(), sysUser.getUserId());
                    userName.add(sysUser.getUserName());
                }
            } else {
                //    如果auditStatus为false
                String createBy = carPackageAudit.getCreateBy();
                processService.setCandidateForCurrentNode(nextTask.getId(), Long.valueOf(createBy));
                //        获取到该流程发起人,给当前节点设置对应的候选人为当前流程发起人, 设置auditors       sql
                userName.add(sysUserService.selectUserById(Long.valueOf(createBy)).getUserName());
                //        t_car_package_audit状态修改成审核拒绝 1
                carPackageAudit.setStatus(CarPackageAudit.STATUS_REJECT);
            }
            carPackageAudit.setAuditors(JSON.toJSONString(userName));
        }
        carPackageAuditMapper.updateCarPackageAudit(carPackageAudit);
    }

    @Override
    @Transactional
    public void serviceItemsUpdate(Long carPackageAuditId, ServiceItem serviceItem) {

        //修改serviceItem
        serviceItemService.updateServiceItemNoCondition(serviceItem);
        //修改carPackageAudit
        CarPackageAudit carPackageAudit = carPackageAuditMapper.selectCarPackageAuditById(carPackageAuditId);
        String itemStr = JSON.toJSONString(serviceItemService.selectServiceItemById(serviceItem.getId()));
        carPackageAudit.setServiceItemInfo(itemStr);
        carPackageAuditMapper.updateCarPackageAudit(carPackageAudit);
    }

    @Override
    @Transactional  //原子性
    public void reApply(String taskId, String carPackageAuditId) {

        CarPackageAudit carPackageAudit = carPackageAuditMapper.selectCarPackageAuditById(Long.valueOf(carPackageAuditId));
        ServiceItem item = JSON.parseObject(carPackageAudit.getServiceItemInfo(), ServiceItem.class);
        BigDecimal discountPrice = item.getDiscountPrice();
        // 领取任务, 处理任务
        // 获取当前节点  processService.
        // processService.claimAndCompleteTask(taskId,"重新申请");  //方法重载
        Task task = processService.claimAndCompleteTaskReApply(Long.valueOf(taskId), discountPrice, "重新提交");
        List<SysUser> auditors= serviceItemService.selectAuditorsByTaskKey(task.getTaskDefinitionKey());

        List<String> userName = new ArrayList<>();
        for (SysUser sysUser : auditors) {
            processService.setCandidateForCurrentNode(task.getId(), sysUser.getUserId());
            userName.add(sysUser.getUserName());
        }
        //获取下一个节点
        //分配节点候选人
        //修改快照表信息  状态和审核人
        carPackageAudit.setStatus(CarPackageAudit.STATUS_IN_ROGRESS);
        carPackageAudit.setAuditors(JSON.toJSONString(userName));
        carPackageAuditMapper.updateCarPackageAudit(carPackageAudit);
    }


    @Override
    public List<HistoricActivity> listHistory(String instanceId) {

        //获取历史任务
        //设置任务名称, 开始时间, 结束时间, 耗时
        List<HistoricActivityInstance> historicTask = processService.getHistoricTask(instanceId);

        List<HistoricActivity> historicActivityList = new ArrayList<>();

        for (HistoricActivityInstance task : historicTask) {
            HistoricActivity historicActivity = new HistoricActivity();
            BeanUtils.copyProperties(task, historicActivity);
            SysUser sysUser = null;
            if (task.getAssignee() != null) {
                sysUser = sysUserService.selectUserById(Long.valueOf(task.getAssignee()));
            }
            if (sysUser != null) {
                historicActivity.setAssigneeName(sysUser.getUserName());
            }
            //获取历史批注
            //设置审批意见
            String comment = processService.getComment(task.getTaskId());
            historicActivity.setComment(comment);
            historicActivityList.add(historicActivity);
        }

        return historicActivityList;
    }
}






















