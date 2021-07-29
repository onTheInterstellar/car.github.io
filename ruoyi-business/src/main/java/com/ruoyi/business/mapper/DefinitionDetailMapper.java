package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.DefinitionDetail;

/**
 * 流程定义明细Mapper接口
 * 
 * @author wolfcode
 * @date 2021-07-19
 */
public interface DefinitionDetailMapper 
{
    /**
     * 查询流程定义明细
     * 
     * @param definitionInfoId 流程定义明细ID
     * @return 流程定义明细
     */
    public DefinitionDetail selectDefinitionDetailById(Long definitionInfoId);

    /**
     * 查询流程定义明细列表
     * 
     * @param definitionDetail 流程定义明细
     * @return 流程定义明细集合
     */
    public List<DefinitionDetail> selectDefinitionDetailList(DefinitionDetail definitionDetail);

    /**
     * 新增流程定义明细
     * 
     * @param definitionDetail 流程定义明细
     * @return 结果
     */
    public int insertDefinitionDetail(DefinitionDetail definitionDetail);

    /**
     * 修改流程定义明细
     * 
     * @param definitionDetail 流程定义明细
     * @return 结果
     */
    public int updateDefinitionDetail(DefinitionDetail definitionDetail);

    /**
     * 删除流程定义明细
     * 
     * @param definitionInfoId 流程定义明细ID
     * @return 结果
     */
    public int deleteDefinitionDetailById(Long definitionInfoId);

    /**
     * 批量删除流程定义明细
     * 
     * @param definitionInfoIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDefinitionDetailByIds(String[] definitionInfoIds);
}
