package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.ServiceItem;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 养修服务项Mapper接口
 * 
 * @author wolfcode
 * @date 2021-05-14
 */
public interface ServiceItemMapper 
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
     * 修改养修服务项, 忽略版本号
     *
     * @param serviceItem 养修服务项
     * @return 结果
     */
    public int updateServiceItemNoCondition(ServiceItem serviceItem);


    /**
     * 批量删除养修服务项
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteServiceItemByIds(String[] ids);

    /**
     * 将服务项的状态修改成指定值
     * @param id 需要修改的服务项ID
     * @param status 需要修改成什么样的状态值
     * @return
     */
    int changeSaleStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 查询所有上架的服务项
     * @param serviceItem
     * @return
     */
    List<ServiceItem> selectAllSaleOnList(ServiceItem serviceItem);

    /**
     * 将审核状态从指定状态变成成对应的状态
     * @param id
     * @param originalStatus 原始状态值
     * @param currentStatus  需要变成的状态值
     * @return
     */
    int changeAuditStatus(@Param("id") Long id, @Param("originalStatus") Integer originalStatus, @Param("currentStatus") Integer currentStatus);

    List<SysUser> selectAuditorsByTaskKey(String taskKey);
}
