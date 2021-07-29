package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.Statement;
import com.ruoyi.business.domain.StatementItem;
import com.ruoyi.business.mapper.StatementItemMapper;
import com.ruoyi.business.mapper.StatementMapper;
import com.ruoyi.business.service.IStatementItemService;
import com.ruoyi.business.service.IStatementService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 结算单明细Service业务层处理
 * 
 * @author wolfcode
 * @date 2021-05-17
 */
@Service
public class StatementItemServiceImpl implements IStatementItemService
{
    @Autowired
    private StatementItemMapper statementItemMapper;
    @Autowired
    private IStatementService statementService;
    @Autowired
    private StatementMapper statementMapper;

    /**
     * 查询结算单明细列表
     * 
     * @param statementItem 结算单明细
     * @return 结算单明细
     */
    @Override
    public List<Map> selectStatementItemList(StatementItem statementItem)
    {
        return statementItemMapper.selectStatementItemList(statementItem);
    }

    /**
     * 新增结算单明细
     * @param items
     * @return
     */
    @Override
    @Transactional
    public int saveItems(List<StatementItem> items) {
        //最有一个元素存储的是优惠金额
        StatementItem tempItem = items.remove(items.size() - 1);
        BigDecimal discountAmount = tempItem.getItemPrice();
        Long statementId = tempItem.getStatementId();
        //删除之前的明细项
        statementItemMapper.deleteStatementItemBySId(statementId);
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal totalQuantity = new BigDecimal(0);
        //插入新的明细项
        if(items.size()>0){
            statementItemMapper.insertItems(items);
            for(StatementItem item:items){
                totalAmount = totalAmount.add(item.getItemPrice().multiply(item.getItemQuantity()));
                totalQuantity = totalQuantity.add(item.getItemQuantity());
            }
        }
        //如果优惠金额比总金额还要更多，属于非法逻辑
        if(totalAmount.compareTo(discountAmount)<0){
            throw new BusinessException("非法操作,优惠金额大于总金额");
        }
        //更新结算单
        statementService.updateAmount(statementId,totalAmount,totalQuantity,discountAmount);
        return 1;
    }
    /**
     * 根据结算单ID删除结算单明细
     * @param sid
     * @return
     */
    @Override
    public int deleteStatementItemBySId(Long sid) {
        return statementItemMapper.deleteStatementItemBySId(sid);
    }
    /**
     * 结算单支付
     * @param statementId
     * @return
     */
    @Override
    public int payStatement(Long statementId) {
        //获取当前登录用户
        SysUser user = ShiroUtils.getSysUser();
        return statementMapper.payStatement(statementId,user.getUserId(), Statement.STATUS_PAID);
    }
}
