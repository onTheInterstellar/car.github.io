package com.ruoyi.business.service;

import com.ruoyi.business.domain.DefinitionDetail;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * 流程定义明细Service接口
 * 
 * @author wolfcode
 * @date 2021-07-19
 */
public interface IDefinitionDetailService 
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
     * 批量删除流程定义明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDefinitionDetailByIds(String ids);

    /**
     * 删除流程定义明细信息
     * 
     * @param definitionInfoId 流程定义明细ID
     * @return 结果
     */
    public int deleteDefinitionDetailById(Long definitionInfoId);

    void deploy(Long definitionId, String filePath) throws FileNotFoundException;
}
