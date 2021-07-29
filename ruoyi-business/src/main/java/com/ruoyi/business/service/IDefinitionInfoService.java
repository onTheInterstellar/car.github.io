package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.DefinitionInfo;

/**
 * 流程定义Service接口
 * 
 * @author wolfcode
 * @date 2021-07-19
 */
public interface IDefinitionInfoService 
{
    /**
     * 查询流程定义
     * 
     * @param id 流程定义ID
     * @return 流程定义
     */
    public DefinitionInfo selectDefinitionInfoById(Long id);

    /**
     * 查询流程定义列表
     * 
     * @param definitionInfo 流程定义
     * @return 流程定义集合
     */
    public List<DefinitionInfo> selectDefinitionInfoList(DefinitionInfo definitionInfo);

    /**
     * 新增流程定义
     * 
     * @param definitionInfo 流程定义
     * @return 结果
     */
    public int insertDefinitionInfo(DefinitionInfo definitionInfo);

    /**
     * 修改流程定义
     * 
     * @param definitionInfo 流程定义
     * @return 结果
     */
    public int updateDefinitionInfo(DefinitionInfo definitionInfo);

    /**
     * 批量删除流程定义
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDefinitionInfoByIds(String ids);

    /**
     * 删除流程定义信息
     * 
     * @param id 流程定义ID
     * @return 结果
     */
    public int deleteDefinitionInfoById(Long id);
}
