package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.DefinitionInfoMapper;
import com.ruoyi.business.domain.DefinitionInfo;
import com.ruoyi.business.service.IDefinitionInfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 流程定义Service业务层处理
 * 
 * @author wolfcode
 * @date 2021-07-19
 */
@Service
public class DefinitionInfoServiceImpl implements IDefinitionInfoService 
{
    @Autowired
    private DefinitionInfoMapper definitionInfoMapper;

    /**
     * 查询流程定义
     * 
     * @param id 流程定义ID
     * @return 流程定义
     */
    @Override
    public DefinitionInfo selectDefinitionInfoById(Long id)
    {
        return definitionInfoMapper.selectDefinitionInfoById(id);
    }

    /**
     * 查询流程定义列表
     * 
     * @param definitionInfo 流程定义
     * @return 流程定义
     */
    @Override
    public List<DefinitionInfo> selectDefinitionInfoList(DefinitionInfo definitionInfo)
    {
        return definitionInfoMapper.selectDefinitionInfoList(definitionInfo);
    }

    /**
     * 新增流程定义
     * 
     * @param definitionInfo 流程定义
     * @return 结果
     */
    @Override
    public int insertDefinitionInfo(DefinitionInfo definitionInfo)
    {
        return definitionInfoMapper.insertDefinitionInfo(definitionInfo);
    }

    /**
     * 修改流程定义
     * 
     * @param definitionInfo 流程定义
     * @return 结果
     */
    @Override
    public int updateDefinitionInfo(DefinitionInfo definitionInfo)
    {
        return definitionInfoMapper.updateDefinitionInfo(definitionInfo);
    }

    /**
     * 删除流程定义对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDefinitionInfoByIds(String ids)
    {
        return definitionInfoMapper.deleteDefinitionInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除流程定义信息
     * 
     * @param id 流程定义ID
     * @return 结果
     */
    @Override
    public int deleteDefinitionInfoById(Long id)
    {
        return definitionInfoMapper.deleteDefinitionInfoById(id);
    }
}
