package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.DefinitionNode;
import com.ruoyi.business.mapper.DefinitionNodeMapper;
import com.ruoyi.business.service.IDefinitionNodeService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程定义节点信息Service业务层处理
 * 
 * @author wolfcode
 * @date 2021-07-20
 */
@Service
public class DefinitionNodeServiceImpl implements IDefinitionNodeService 
{
    @Autowired
    private DefinitionNodeMapper definitionNodeMapper;

    /**
     * 查询流程定义节点信息
     * 
     * @param id 流程定义节点信息ID
     * @return 流程定义节点信息
     */
    @Override
    public DefinitionNode selectDefinitionNodeById(Long id)
    {
        return definitionNodeMapper.selectDefinitionNodeById(id);
    }

    /**
     * 查询流程定义节点信息列表
     * 
     * @param definitionNode 流程定义节点信息
     * @return 流程定义节点信息
     */
    @Override
    public List<DefinitionNode> selectDefinitionNodeList(DefinitionNode definitionNode)
    {
        return definitionNodeMapper.selectDefinitionNodeList(definitionNode);
    }

    /**
     * 新增流程定义节点信息
     * 
     * @param definitionNode 流程定义节点信息
     * @return 结果
     */
    @Override
    public int insertDefinitionNode(DefinitionNode definitionNode)
    {
        //插入中间表
        int num = definitionNodeMapper.insertDefinitionNode(definitionNode);
        definitionNodeMapper.addRelation(definitionNode);
        return num;
    }

    /**
     * 修改流程定义节点信息
     * 
     * @param definitionNode 流程定义节点信息
     * @return 结果
     */
    @Override
    public int updateDefinitionNode(DefinitionNode definitionNode)
    {
        deleteDefinitionNodeByIds(definitionNode.getId().toString());
        return insertDefinitionNode(definitionNode);
    }

    /**
     * 删除流程定义节点信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDefinitionNodeByIds(String ids)
    {
        definitionNodeMapper.deleteRelationByNodeId(Convert.toStrArray(ids));
        return definitionNodeMapper.deleteDefinitionNodeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除流程定义节点信息信息
     * 
     * @param id 流程定义节点信息ID
     * @return 结果
     */
    @Override
    public int deleteDefinitionNodeById(Long id)
    {
        return definitionNodeMapper.deleteDefinitionNodeById(id);
    }
}
