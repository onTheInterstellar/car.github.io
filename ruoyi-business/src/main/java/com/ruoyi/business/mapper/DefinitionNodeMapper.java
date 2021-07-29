package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.DefinitionNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程定义节点信息Mapper接口
 * 
 * @author wolfcode
 * @date 2021-07-20
 */
public interface DefinitionNodeMapper 
{
    /**
     * 查询流程定义节点信息
     * 
     * @param id 流程定义节点信息ID
     * @return 流程定义节点信息
     */
    public DefinitionNode selectDefinitionNodeById(Long id);

    /**
     * 查询流程定义节点信息列表
     * 
     * @param definitionNode 流程定义节点信息
     * @return 流程定义节点信息集合
     */
    public List<DefinitionNode> selectDefinitionNodeList(DefinitionNode definitionNode);

    /**
     * 新增流程定义节点信息
     * 
     * @param definitionNode 流程定义节点信息
     * @return 结果
     */
    public int insertDefinitionNode(DefinitionNode definitionNode);

    /**
     * 修改流程定义节点信息
     * 
     * @param definitionNode 流程定义节点信息
     * @return 结果
     */
    public int updateDefinitionNode(DefinitionNode definitionNode);

    /**
     * 删除流程定义节点信息
     * 
     * @param id 流程定义节点信息ID
     * @return 结果
     */
    public int deleteDefinitionNodeById(Long id);

    /**
     * 批量删除流程定义节点信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDefinitionNodeByIds(String[] ids);

    void addRelation(DefinitionNode definitionNode);

    void deleteRelationByNodeId(@Param("ids") String[] ids);
}
