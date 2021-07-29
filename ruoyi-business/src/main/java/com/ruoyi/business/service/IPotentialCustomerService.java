package com.ruoyi.business.service;

import com.ruoyi.business.domain.FollowInfo;
import com.ruoyi.business.domain.PotentialCustomer;

import java.util.List;

/**
 * 潜在客户Service接口
 * 
 * @author ruoyi
 * @date 2021-07-26
 */
public interface IPotentialCustomerService 
{
    /**
     * 查询潜在客户
     * 
     * @param id 潜在客户ID
     * @return 潜在客户
     */
    public PotentialCustomer selectPotentialCustomerById(Long id);

    /**
     * 查询潜在客户列表
     * 
     * @param potentialCustomer 潜在客户
     * @return 潜在客户集合
     */
    public List<PotentialCustomer> selectPotentialCustomerList(PotentialCustomer potentialCustomer);

    /**
     * 新增潜在客户
     * 
     * @param potentialCustomer 潜在客户
     * @return 结果
     */
    public int insertPotentialCustomer(PotentialCustomer potentialCustomer);

    /**
     * 修改潜在客户
     * 
     * @param potentialCustomer 潜在客户
     * @return 结果
     */
    public int updatePotentialCustomer(PotentialCustomer potentialCustomer);

    /**
     * 批量删除潜在客户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePotentialCustomerByIds(String ids);

    /**
     * 删除潜在客户信息
     * 
     * @param id 潜在客户ID
     * @return 结果
     */
    public int deletePotentialCustomerById(Long id);

    void addFollowInfo(FollowInfo followInfo);

    List<FollowInfo> selectHistoryList(Long customerId);
}
