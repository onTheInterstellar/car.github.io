package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Customer;

import java.util.List;

/**
 * 岗位信息 数据层
 * 
 * @author ruoyi
 */
public interface CustomerMapper
{
    /**
     * 查询岗位数据集合
     * 
     * @param customer 岗位信息
     * @return 岗位数据集合
     */
    public List<Customer> selectCustomerList(Customer customer);

    /**
     * 查询所有岗位
     * 
     * @return 岗位列表
     */
    public List<Customer> selectCustomerAll();

    /**
     * 根据用户ID查询岗位
     * 
     * @param userId 用户ID
     * @return 岗位列表
     */
//    public List<Customer> selectCustomersByUserId(Long userId);

    /**
     * 通过岗位ID查询岗位信息
     * 
     * @param customerId 岗位ID
     * @return 角色对象信息
     */
    public Customer selectCustomerById(Long customerId);

    /**
     * 批量删除岗位信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomerByIds(Long[] ids);

    /**
     * 修改岗位信息
     * 
     * @param customer 岗位信息
     * @return 结果
     */
    public int updateCustomer(Customer customer);

    /**
     * 新增岗位信息
     * 
     * @param customer 岗位信息
     * @return 结果
     */
    public int insertCustomer(Customer customer);

    /**
     * 校验岗位名称
     * 
     * @param customerName 岗位名称
     * @return 结果
     */
    public Customer checkCustomerNameUnique(String customerName);

    /**
     * 校验岗位编码
     * 
     * @param customerCode 岗位编码
     * @return 结果
     */
    public Customer checkCustomerCodeUnique(String customerCode);
}
