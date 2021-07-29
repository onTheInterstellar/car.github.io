package com.ruoyi.system.service;


import com.ruoyi.system.domain.Customer;

import java.util.List;

/**
 * 岗位信息 服务层
 * 
 * @author ruoyi
 */
public interface ICustomerService
{
    /**
     * 查询岗位信息集合
     * 
     * @param customer 岗位信息
     * @return 岗位信息集合
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
    /*public List<Customer> selectCustomersByUserId(Long userId);*/

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
     * @throws Exception 异常
     */
    public int deleteCustomerByIds(String ids) throws Exception;

    /**
     * 新增保存岗位信息
     * 
     * @param customer 岗位信息
     * @return 结果
     */
    public int insertCustomer(Customer customer);

    /**
     * 修改保存岗位信息
     * 
     * @param customer 岗位信息
     * @return 结果
     */
    public int updateCustomer(Customer customer);

    /**
     * 通过岗位ID查询岗位使用数量
     * 
     * @param customerId 岗位ID
     * @return 结果
     */
    /*public int countUserCustomerById(Long customerId);*/

    /**
     * 校验岗位名称
     * 
     * @param customer 岗位信息
     * @return 结果
     */
    public String checkCustomerNameUnique(Customer customer);

    /**
     * 校验岗位编码
     * 
     * @param customer 岗位信息
     * @return 结果
     */
    public String checkCustomerCodeUnique(Customer customer);
}
