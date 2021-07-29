package com.ruoyi.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.business.domain.CarPackageAudit;
import com.ruoyi.business.domain.ServiceItem;
import com.ruoyi.business.mapper.ServiceItemMapper;
import com.ruoyi.business.service.ICarPackageAuditService;
import com.ruoyi.business.service.IProcessService;
import com.ruoyi.business.service.IServiceItemService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 养修服务项Service业务层处理
 * 
 * @author wolfcode
 * @date 2021-05-14
 */
@Service
public class ServiceItemServiceImpl implements IServiceItemService
{

    @Autowired
    private ICarPackageAuditService carPackageAuditService;

    @Autowired
    private IProcessService processService;

    @Autowired
    private ServiceItemMapper serviceItemMapper;
    /**
     * 查询养修服务项
     * 
     * @param id 养修服务项ID
     * @return 养修服务项
     */
    @Override
    public ServiceItem selectServiceItemById(Long id)
    {
        return serviceItemMapper.selectServiceItemById(id);
    }

    /**
     * 查询养修服务项列表
     * 
     * @param serviceItem 养修服务项
     * @return 养修服务项
     */
    @Override
    public List<ServiceItem> selectServiceItemList(ServiceItem serviceItem)
    {
        return serviceItemMapper.selectServiceItemList(serviceItem);
    }

    /**
     * 新增养修服务项
     * 
     * @param serviceItem 养修服务项
     * @return 结果
     */
    @Override
    public int insertServiceItem(ServiceItem serviceItem)
    {
        serviceItem.setCreateTime(DateUtils.getNowDate());
        if(ServiceItem.CARPACKAGE_YES.equals(serviceItem.getCarPackage())){
            serviceItem.setAuditStatus(ServiceItem.AUDITSTATUS_INIT);//设置初始化
        }
        return serviceItemMapper.insertServiceItem(serviceItem);
    }

    /**
     * 修改养修服务项
     * 
     * @param serviceItem 养修服务项
     * @return 结果
     */
    @Override
    public int updateServiceItem(ServiceItem serviceItem)
    {
        ServiceItem oldObj = selectServiceItemById(serviceItem.getId());
        //处于上架状态的商品不能修改
        if(ServiceItem.SALESTATUS_ON.equals(oldObj.getSaleStatus())){
            throw new BusinessException("上架服务项目不能修改，请下架后再修改");
        }else if(ServiceItem.AUDITSTATUS_AUDITING.equals(oldObj.getAuditStatus())){
            throw new BusinessException("服务项目正在审核中,不可修改");
        }
        //如果是审核通过，此时修改，需要将其状态变更为初始化
        if(ServiceItem.AUDITSTATUS_APPROVED.equals(oldObj.getAuditStatus())){
            serviceItem.setAuditStatus(ServiceItem.AUDITSTATUS_INIT);
        }
        serviceItem.setVersion(oldObj.getVersion());
        return serviceItemMapper.updateServiceItem(serviceItem);
    }

    /**
     * 
     * @param item
     */
    @Override
    public void updateServiceItemNoCondition(ServiceItem item) {
        serviceItemMapper.updateServiceItemNoCondition(item);
    }

    @Override
    public List<SysUser> selectAuditorsByTaskKey(String taskDefinitionKey) {
        return serviceItemMapper.selectAuditorsByTaskKey(taskDefinitionKey);
    }

    /**
     * 删除养修服务项对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteServiceItemByIds(String ids)
    {
        return serviceItemMapper.deleteServiceItemByIds(Convert.toStrArray(ids));
    }
    /**
     * 下架
     *
     * @param id 需要下架的服务项ID
     * @return
     */
    @Override
    public int saleOff(Long id) {
        return serviceItemMapper.changeSaleStatus(id,ServiceItem.SALESTATUS_OFF);
    }
    /**
     * 上架
     * @param id 需要上架的服务ID
     * @return
     */
    @Override
    public int saleOn(Long id) {
        ServiceItem serviceItem = selectServiceItemById(id);
        //如果不是套餐，可以直接上架
        //如果是套餐，只有审核成功才能上架
        if(ServiceItem.CARPACKAGE_YES.equals(serviceItem.getCarPackage())
                && !ServiceItem.AUDITSTATUS_APPROVED.equals(serviceItem.getAuditStatus())){
            throw new BusinessException("套餐服务项需要审核通过之后才能上架");
        }
        return serviceItemMapper.changeSaleStatus(id,ServiceItem.SALESTATUS_ON);
    }
    /**
     * 查询上家的养修服务项列表
     *
     * @param serviceItem 养修服务项
     * @return 养修服务项集合
     */
    @Override
    public List<ServiceItem> selectAllSaleOnList(ServiceItem serviceItem) {
        return serviceItemMapper.selectAllSaleOnList(serviceItem);
    }


    @Override
    @Transactional
    public int startAuditById(Long id) {
        //1. 判断是否为套餐, 转态是否为 0
        ServiceItem serviceItem = serviceItemMapper.selectServiceItemById(id);
        if (serviceItem.getCarPackage().equals(ServiceItem.CARPACKAGE_NO)) {
            throw new BusinessException("不是套餐, 无法上架");
        }
        if (!serviceItem.getAuditStatus().equals(ServiceItem.AUDITSTATUS_INIT)) {
            throw new BusinessException("目前不在初始状态, 无法上架");
        }

        //2. 查询对应的serviceItem对象, 将对象的转态该成一
        //serviceItem.setAuditStatus(1); 此处1等于写死了, 所以需要改成  类中定义的审核状态
        serviceItem.setAuditStatus(ServiceItem.AUDITSTATUS_AUDITING);
        serviceItemMapper.updateServiceItemNoCondition(serviceItem);  // 将serviceItem 更新

        //3. 将修改后的对象更新到数据路, 并将对象转成JSON字符串
        String serviceItemInfo = JSON.toJSONString(serviceItem);

        //4. 创建CarPackageAudit 对象, 存入serviceItemInfo  , 状态0 表示进行中
        CarPackageAudit carPackageAudit = new CarPackageAudit();
        carPackageAudit.setServiceItemInfo(serviceItemInfo);
        //carPackageAudit.setStatus(0);
        carPackageAudit.setCreateBy(ShiroUtils.getUserId().toString());   //设置当前的审核人
        carPackageAuditService.insertCarPackageAudit(carPackageAudit);  //将目前的carPackageAudit保存到数据库

        //5. mybatis自动更新id 取出id 作为businessKey,  discount_price 做为开启流程的变量money
        Long businessKey = carPackageAudit.getId();
        HashMap<String, Object> variable = new HashMap<>();
        variable.put("money", serviceItem.getDiscountPrice().doubleValue());   //要装成double, 不要没法转成double类型

        //6. 定义processSer..startAuditProcess(DEFINITION_KEY, business, Map<dis_or>) 返回流程实例
        ProcessInstance processInstance = processService.startAuditProcess(CarPackageAudit.DEFINITION_KEY, businessKey, variable);
        //7. 获取节点key, 将实例id设置进对象
        String instanceId = processInstance.getId();
        carPackageAudit.setInstanceId(instanceId);
        //8. 根据节点key获取节点id->userId
        Task currentTask = processService.getCurrentTask(instanceId);
        //Task currentTask = processService.getCurrentTask(CarPackageAudit.DEFINITION_KEY);
        //通过流程定义key 获取到的task 是所有该流程定义下的当前任务, 而不是当前流程实例的任务
        List<SysUser> auditors = serviceItemMapper.selectAuditorsByTaskKey(currentTask.getTaskDefinitionKey());
        List<String> auditorName = new ArrayList<>();
        for (SysUser auditor : auditors) {
            //10. 为节点设置候选人
            processService.setCandidateForCurrentNode(currentTask.getId(), auditor.getUserId());
            auditorName.add(auditor.getUserName());
        }
        //9. 将集合转成JSON字符, 将字符串作为auditors
        String jsonStringOfName = JSON.toJSONString(auditorName);
        carPackageAudit.setAuditors(jsonStringOfName);
        //11. 更新package_audit
        carPackageAuditService.updateCarPackageAudit(carPackageAudit);
        //12. 返回一个数字
        return 1;
    }

}
