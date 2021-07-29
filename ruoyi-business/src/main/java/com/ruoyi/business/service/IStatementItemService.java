package com.ruoyi.business.service;

import com.ruoyi.business.domain.StatementItem;

import java.util.List;
import java.util.Map;

/**
 * 结算单明细Service接口
 * 
 * @author wolfcode
 * @date 2021-05-17
 */
public interface IStatementItemService 
{

    /**
     * 查询结算单明细列表
     * 
     * @param statementItem 结算单明细
     * @return 结算单明细集合
     */
    public List<Map> selectStatementItemList(StatementItem statementItem);

    /**
     * 新增结算单明细
      * @param items
     * @return
     */
    int saveItems(List<StatementItem> items);

    /**
     * 根据结算单ID删除结算单明细
     * @param sid
     * @return
     */
    int deleteStatementItemBySId(Long sid);
    /**
     * 结算单支付
     * @param statementId
     * @return
     */
    int payStatement(Long statementId);
}
