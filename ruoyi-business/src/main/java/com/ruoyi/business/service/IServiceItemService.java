package com.ruoyi.business.service;

import com.ruoyi.business.domain.ServiceItem;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * 养修服务项Service接口
 * 
 * @author wolfcode
 * @date 2021-05-14
 */
public interface IServiceItemService
{
    /**
     * 查询养修服务项
     * 
     * @param id 养修服务项ID
     * @return 养修服务项
     */
    public ServiceItem selectServiceItemById(Long id);

    /**
     * 查询养修服务项列表
     * 
     * @param serviceItem 养修服务项
     * @return 养修服务项集合
     */
    public List<ServiceItem> selectServiceItemList(ServiceItem serviceItem);

    /**
     * 新增养修服务项
     * 
     * @param serviceItem 养修服务项
     * @return 结果
     */
    public int insertServiceItem(ServiceItem serviceItem);

    /**
     * 修改养修服务项
     * 
     * @param serviceItem 养修服务项
     * @return 结果
     */
    public int updateServiceItem(ServiceItem serviceItem);

    /**
     * 批量删除养修服务项
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteServiceItemByIds(String ids);

    /**
     * 下架
     *
     * @param id 需要下架的服务项ID
     * @return
     */
    int saleOff(Long id);

    /**
     * 上架
     * @param id 需要上架的服务ID
     * @return
     */
    int saleOn(Long id);
    /**
     * 查询上家的养修服务项列表
     *
     * @param serviceItem 养修服务项
     * @return 养修服务项集合
     */
    List<ServiceItem> selectAllSaleOnList(ServiceItem serviceItem);

    int startAuditById(Long id);

    void updateServiceItemNoCondition(ServiceItem item);

    List<SysUser> selectAuditorsByTaskKey(String taskDefinitionKey);
}
