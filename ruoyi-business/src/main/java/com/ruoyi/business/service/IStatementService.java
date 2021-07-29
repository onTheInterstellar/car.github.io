package com.ruoyi.business.service;

import com.ruoyi.business.domain.Statement;

import java.math.BigDecimal;
import java.util.List;

/**
 * 结算单Service接口
 * 
 * @author wolfcode
 * @date 2021-05-17
 */
public interface IStatementService 
{
    /**
     * 查询结算单
     * 
     * @param id 结算单ID
     * @return 结算单
     */
    public Statement selectStatementById(Long id);

    /**
     * 查询结算单列表
     * 
     * @param statement 结算单
     * @return 结算单集合
     */
    public List<Statement> selectStatementList(Statement statement);

    /**
     * 新增结算单
     * 
     * @param statement 结算单
     * @return 结果
     */
    public int insertStatement(Statement statement);

    /**
     * 修改结算单
     * 
     * @param statement 结算单
     * @return 结果
     */
    public int updateStatement(Statement statement);

    /**
     * 批量删除结算单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStatementByIds(String ids);

    /**
     * 删除结算单信息
     * 
     * @param id 结算单ID
     * @return 结果
     */
    public int deleteStatementById(Long id);
    /**
     * 更新结算单的金额
     * @param statementId 结算单ID
     * @param totalAmount 消费总金额
     * @param totalQuantity 消费服务项个数
     * @param discountAmount 总优惠基恩
     */
    int updateAmount(Long statementId, BigDecimal totalAmount, BigDecimal totalQuantity, BigDecimal discountAmount);


    /**
     * 删除指定结算单
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据预约单信息查询结算单ID
     * @param mainteenanceId
     * @return
     */
    Long selectStatementByMainteenanceId(Long mainteenanceId);
}
