package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.StatementItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 结算单明细Mapper接口
 * 
 * @author wolfcode
 * @date 2021-05-17
 */
public interface StatementItemMapper 
{
    /**
     * 查询结算单明细列表
     * 
     * @param statementItem 结算单明细
     * @return 结算单明细集合
     */
    public List<Map> selectStatementItemList(StatementItem statementItem);

    /**
     * 删除指定结算单下的所有明细
     * @param statementId
     */
    int deleteStatementItemBySId(Long statementId);

    /**
     * 批量插入结算单明细
     * @param items
     * @return
     */
    int insertItems(@Param("items") List<StatementItem> items);
}
