package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Customer;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.CustomerMapper;
import com.ruoyi.system.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /*@Autowired
    private CustomerMapper userCustomerMapper;*/

    /**
     * 查询岗位信息集合
     *
     * @param customer 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<Customer> selectCustomerList(Customer customer)
    {
        return customerMapper.selectCustomerList(customer);
    }

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<Customer> selectCustomerAll()
    {
        return customerMapper.selectCustomerAll();
    }

    /**
     * 根据用户ID查询岗位
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    /*@Override
    public List<Customer> selectCustomersByUserId(Long userId)
    {
        List<Customer> userCustomers = customerMapper.selectCustomersByUserId(userId);
        List<Customer> customers = customerMapper.selectCustomerAll();
        for (Customer customer : customers)
        {
            for (Customer userRole : userCustomers)
            {
                if (customer.getId().longValue() == userRole.getId().longValue())
                {
                    customer.setFlag(true);
                    break;
                }
            }
        }
        return customers;
    }
*/
    /**
     * 通过岗位ID查询岗位信息
     *
     * @param customerId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public Customer selectCustomerById(Long customerId)
    {
        return customerMapper.selectCustomerById(customerId);
    }

    /**
     * 批量删除岗位信息
     *
     * @param ids 需要删除的数据ID
     * @throws Exception
     */
    @Override
    public int deleteCustomerByIds(String ids) throws BusinessException
    {
        Long[] customerIds = Convert.toLongArray(ids);
        for (Long customerId : customerIds)
        {
            Customer customer = selectCustomerById(customerId);
            /*if (countUserCustomerById(customerId) > 0)
            {
                throw new BusinessException(String.format("%1$s已分配,不能删除", customer.getCustomerName()));
            }*/
        }
        return customerMapper.deleteCustomerByIds(customerIds);
    }

    /**
     * 新增保存岗位信息
     *
     * @param customer 岗位信息
     * @return 结果
     */
    @Override
    public int insertCustomer(Customer customer)
    {
        return customerMapper.insertCustomer(customer);
    }

    /**
     * 修改保存岗位信息
     *
     * @param customer 岗位信息
     * @return 结果
     */
    @Override
    public int updateCustomer(Customer customer)
    {
        return customerMapper.updateCustomer(customer);
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param customerId 岗位ID
     * @return 结果
     */
    /*@Override
    public int countUserCustomerById(Long customerId)
    {
        return userCustomerMapper.countUserCustomerById(customerId);
    }*/

    /**
     * 校验岗位名称是否唯一
     *
     * @param customer 岗位信息
     * @return 结果
     */
    @Override
    public String checkCustomerNameUnique(Customer customer)
    {
        Long customerId = StringUtils.isNull(customer.getId()) ? -1L : customer.getId();
        Customer info = customerMapper.checkCustomerNameUnique(customer.getName());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != customerId.longValue())
        {
            return UserConstants.CUSTOMER_NAME_NOT_UNIQUE;
        }
        return UserConstants.CUSTOMER_NAME_UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param customer 岗位信息
     * @return 结果
     */
    @Override
    public String checkCustomerCodeUnique(Customer customer)
    {
        Long customerId = StringUtils.isNull(customer.getId()) ? -1L : customer.getId();
        Customer info = customerMapper.checkCustomerCodeUnique(customer.getPhone());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != customerId.longValue())
        {
            return UserConstants.CUSTOMER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.CUSTOMER_PHONE_UNIQUE;
    }
}
