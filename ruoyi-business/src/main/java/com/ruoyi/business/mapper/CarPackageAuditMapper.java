package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.CarPackageAudit;

/**
 * 审核列Mapper接口
 * 
 * @author ruoyi
 * @date 2021-07-20
 */
public interface CarPackageAuditMapper 
{
    /**
     * 查询审核列
     * 
     * @param id 审核列ID
     * @return 审核列
     */
    public CarPackageAudit selectCarPackageAuditById(Long id);

    /**
     * 查询审核列列表
     * 
     * @param carPackageAudit 审核列
     * @return 审核列集合
     */
    public List<CarPackageAudit> selectCarPackageAuditList(CarPackageAudit carPackageAudit);

    /**
     * 新增审核列
     * 
     * @param carPackageAudit 审核列
     * @return 结果
     */
    public int insertCarPackageAudit(CarPackageAudit carPackageAudit);

    /**
     * 修改审核列
     * 
     * @param carPackageAudit 审核列
     * @return 结果
     */
    public int updateCarPackageAudit(CarPackageAudit carPackageAudit);

    /**
     * 删除审核列
     * 
     * @param id 审核列ID
     * @return 结果
     */
    public int deleteCarPackageAuditById(Long id);

    /**
     * 批量删除审核列
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCarPackageAuditByIds(String[] ids);
}
