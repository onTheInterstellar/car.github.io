package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.Statement;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 结算单Mapper接口
 * 
 * @author wolfcode
 * @date 2021-05-17
 */
public interface StatementMapper 
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
     * 删除结算单
     * 
     * @param id 结算单ID
     * @return 结果
     */
    public int deleteStatementById(Long id);

    /**
     * 批量删除结算单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStatementByIds(String[] ids);

    int updateAmount(@Param("statementId") Long statementId, @Param("totalAmount") BigDecimal totalAmount, @Param("totalQuantity") BigDecimal totalQuantity, @Param("discountAmount") BigDecimal discountAmount);

    int payStatement(@Param("statementId") Long statementId, @Param("userId") Long userId, @Param("status") Integer status);

    Long selectStatementByMainteenanceId(Long mainteenanceId);
}
