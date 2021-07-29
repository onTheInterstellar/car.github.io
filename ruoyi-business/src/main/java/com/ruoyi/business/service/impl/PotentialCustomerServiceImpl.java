package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.FollowInfo;
import com.ruoyi.business.domain.PotentialCustomer;
import com.ruoyi.business.mapper.PotentialCustomerMapper;
import com.ruoyi.business.service.IPotentialCustomerService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 潜在客户Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-07-26
 */
@Service
public class PotentialCustomerServiceImpl implements IPotentialCustomerService 
{
    @Autowired
    private PotentialCustomerMapper potentialCustomerMapper;

    /**
     * 查询潜在客户
     * 
     * @param id 潜在客户ID
     * @return 潜在客户
     */
    @Override
    public PotentialCustomer selectPotentialCustomerById(Long id)
    {
        return potentialCustomerMapper.selectPotentialCustomerById(id);
    }

    /**
     * 查询潜在客户列表
     * 
     * @param potentialCustomer 潜在客户
     * @return 潜在客户
     */
    @Override
    public List<PotentialCustomer> selectPotentialCustomerList(PotentialCustomer potentialCustomer)
    {
        if (SecurityUtils.getSubject().hasRole("shopOwner")) {
            return potentialCustomerMapper.selectPotentialCustomerList(potentialCustomer);
        } else {
            potentialCustomer.setFollowUpUser(ShiroUtils.getUserId());
            return potentialCustomerMapper.selectPotentialCustomerList(potentialCustomer);
        }
    }

    /**
     * 新增潜在客户
     * 
     * @param potentialCustomer 潜在客户
     * @return 结果
     */
    @Override
    public int insertPotentialCustomer(PotentialCustomer potentialCustomer) {
        int num = potentialCustomerMapper.selectPotentialCustomerByPhone(potentialCustomer.getPhone());
        if (num > 0 ) {
            return 0;
        } else {
            potentialCustomer.setCreateTime(DateUtils.getNowDate());
            potentialCustomer.setFollowUpUser(ShiroUtils.getUserId());
            return potentialCustomerMapper.insertPotentialCustomer(potentialCustomer);
        }
    }

    /**
     * 修改潜在客户
     * 
     * @param potentialCustomer 潜在客户
     * @return 结果
     */
    @Override
    public int updatePotentialCustomer(PotentialCustomer potentialCustomer)
    {
        return potentialCustomerMapper.updatePotentialCustomer(potentialCustomer);
    }

    /**
     * 删除潜在客户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePotentialCustomerByIds(String ids)
    {
        return potentialCustomerMapper.deletePotentialCustomerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除潜在客户信息
     * 
     * @param id 潜在客户ID
     * @return 结果
     */
    @Override
    public int deletePotentialCustomerById(Long id)
    {
        return potentialCustomerMapper.deletePotentialCustomerById(id);
    }


    /**
     * 添加潜在客户跟进
     * @param followInfo
     */
    @Override
    public void addFollowInfo(FollowInfo followInfo) {
        PotentialCustomer potentialCustomer = new PotentialCustomer();
        potentialCustomer.setFollowUpUser(ShiroUtils.getUserId());
        potentialCustomer.setId(Long.valueOf(followInfo.getCustomerId()));
        potentialCustomer.setLastFollowUpTime(followInfo.getFollowUpTime());
        potentialCustomerMapper.insertCustomerFollowInfo(followInfo);
        potentialCustomerMapper.updatePotentialCustomer(potentialCustomer);
    }

    @Override
    public List<FollowInfo> selectHistoryList(Long customerId) {
        List<FollowInfo> list = potentialCustomerMapper.selectHistoryList(customerId);
        return list;
    }
}
