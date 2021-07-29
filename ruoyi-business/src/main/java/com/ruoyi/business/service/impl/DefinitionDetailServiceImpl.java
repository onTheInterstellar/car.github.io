package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.DefinitionDetail;
import com.ruoyi.business.domain.DefinitionInfo;
import com.ruoyi.business.mapper.DefinitionDetailMapper;
import com.ruoyi.business.service.IDefinitionDetailService;
import com.ruoyi.business.service.IProcessService;
import com.ruoyi.common.core.text.Convert;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * 流程定义明细Service业务层处理
 * 
 * @author wolfcode
 * @date 2021-07-19
 */
@Service
public class DefinitionDetailServiceImpl implements IDefinitionDetailService 
{
    @Autowired
    private DefinitionDetailMapper definitionDetailMapper;

    @Autowired
    private IProcessService processService;

    /**
     * 查询流程定义明细
     * 
     * @param definitionInfoId 流程定义明细ID
     * @return 流程定义明细
     */
    @Override
    public DefinitionDetail selectDefinitionDetailById(Long definitionInfoId)
    {
        return definitionDetailMapper.selectDefinitionDetailById(definitionInfoId);
    }

    /**
     * 查询流程定义明细列表
     * 
     * @param definitionDetail 流程定义明细
     * @return 流程定义明细
     */
    @Override
    public List<DefinitionDetail> selectDefinitionDetailList(DefinitionDetail definitionDetail)
    {
        return definitionDetailMapper.selectDefinitionDetailList(definitionDetail);
    }

    /**
     * 新增流程定义明细
     * 
     * @param definitionDetail 流程定义明细
     * @return 结果
     */
    @Override
    public int insertDefinitionDetail(DefinitionDetail definitionDetail)
    {
        return definitionDetailMapper.insertDefinitionDetail(definitionDetail);
    }

    /**
     * 修改流程定义明细
     * 
     * @param definitionDetail 流程定义明细
     * @return 结果
     */
    @Override
    public int updateDefinitionDetail(DefinitionDetail definitionDetail)
    {
        return definitionDetailMapper.updateDefinitionDetail(definitionDetail);
    }

    /**
     * 删除流程定义明细对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDefinitionDetailByIds(String ids)
    {
        return definitionDetailMapper.deleteDefinitionDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除流程定义明细信息
     * 
     * @param definitionInfoId 流程定义明细ID
     * @return 结果
     */
    @Override
    public int deleteDefinitionDetailById(Long definitionInfoId)
    {
        return definitionDetailMapper.deleteDefinitionDetailById(definitionInfoId);
    }

    @Override
    @Transactional  //保证数据的完整性
    public void deploy(Long definitionId, String filePath) throws FileNotFoundException {
        //将文件部署到activiti中, 生成deployment对象,
        Deployment deployment = processService.deploy(filePath);
        //副总装detail对象, 将数据存到数据鲁中
        DefinitionInfo definitionInfo = new DefinitionInfo();
        definitionInfo.setId(definitionId);

        DefinitionDetail definitionDetail = new DefinitionDetail();
        definitionDetail.setDefinitionInfo(definitionInfo);
        definitionDetail.setDeploymentId(deployment.getId());
        definitionDetail.setDeployKey(deployment.getKey());
        definitionDetailMapper.insertDefinitionDetail(definitionDetail);
    }
}
