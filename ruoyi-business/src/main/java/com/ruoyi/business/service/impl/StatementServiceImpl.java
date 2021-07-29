package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.Statement;
import com.ruoyi.business.mapper.StatementMapper;
import com.ruoyi.business.service.IStatementItemService;
import com.ruoyi.business.service.IStatementService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 结算单Service业务层处理
 * 
 * @author wolfcode
 * @date 2021-05-17
 */
@Service
public class StatementServiceImpl implements IStatementService
{
    @Autowired
    private StatementMapper statementMapper;
    @Autowired
    private IStatementItemService statementItemService;

    /**
     * 查询结算单
     * 
     * @param id 结算单ID
     * @return 结算单
     */
    @Override
    public Statement selectStatementById(Long id)
    {
        return statementMapper.selectStatementById(id);
    }

    /**
     * 查询结算单列表
     * 
     * @param statement 结算单
     * @return 结算单
     */
    @Override
    public List<Statement> selectStatementList(Statement statement)
    {
        return statementMapper.selectStatementList(statement);
    }

    /**
     * 新增结算单
     * 
     * @param statement 结算单
     * @return 结果
     */
    @Override
    public int insertStatement(Statement statement)
    {
        statement.setCreateTime(DateUtils.getNowDate());
        return statementMapper.insertStatement(statement);
    }

    /**
     * 修改结算单
     * 
     * @param statement 结算单
     * @return 结果
     */
    @Override
    public int updateStatement(Statement statement)
    {
        return statementMapper.updateStatement(statement);
    }

    /**
     * 删除结算单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteStatementByIds(String ids)
    {
        return statementMapper.deleteStatementByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除结算单信息
     * 
     * @param id 结算单ID
     * @return 结果
     */
    @Override
    public int deleteStatementById(Long id)
    {
        return statementMapper.deleteStatementById(id);
    }
    /**
     * 更新结算单的金额
     * @param statementId 结算单ID
     * @param totalAmount 消费总金额
     * @param totalQuantity 消费服务项个数
     * @param discountAmount 总优惠基恩
     */
    @Override
    public int updateAmount(Long statementId, BigDecimal totalAmount, BigDecimal totalQuantity, BigDecimal discountAmount) {
        return statementMapper.updateAmount(statementId,totalAmount,totalQuantity,discountAmount);
    }


    /**
     * 删除指定结算单
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int delete(Long id) {
        //删除结算单（只能删除消费中的）
        int count = statementMapper.deleteStatementById(id);
        if(count>0){
            //删除结算单明细
            statementItemService.deleteStatementItemBySId(id);
        }
        return count;
    }
    /**
     * 根据预约单信息查询结算单ID
     * @param mainteenanceId
     * @return
     */
    @Override
    public Long selectStatementByMainteenanceId(Long mainteenanceId) {
        return statementMapper.selectStatementByMainteenanceId(mainteenanceId);
    }
}
