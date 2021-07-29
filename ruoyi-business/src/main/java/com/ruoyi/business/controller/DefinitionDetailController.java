package com.ruoyi.business.controller;

import com.ruoyi.business.domain.DefinitionDetail;
import com.ruoyi.business.service.IDefinitionDetailService;
import com.ruoyi.business.service.IProcessService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 流程定义明细Controller
 * 
 * @author wolfcode
 * @date 2021-07-19
 */
@Controller
@RequestMapping("/business/definitionDetail")
public class DefinitionDetailController extends BaseController
{
    private String prefix = "business/definitionDetail";

    @Autowired
    private IDefinitionDetailService definitionDetailService;

    @Autowired
    private IProcessService processService;

    @RequiresPermissions("business:definitionDetail:view")
    @GetMapping("/{definitionId}")
    public String definitionDetail(@PathVariable("definitionId") Long definitionId, ModelMap mmap)
    {
        mmap.put("definitionId", definitionId);
        return prefix + "/definitionDetail";
    }

    /**
     * 查询流程定义明细列表
     */
    @RequiresPermissions("business:definitionDetail:list")
    @PostMapping("/list/{definitionInfo.id}")
    @ResponseBody
    public TableDataInfo list(DefinitionDetail definitionDetail)
    {
        startPage();
        List<DefinitionDetail> list = definitionDetailService.selectDefinitionDetailList(definitionDetail);
        return getDataTable(list);
    }

    /**
     * 导出流程定义明细列表
     */
    @RequiresPermissions("business:definitionDetail:export")
    @Log(title = "流程定义明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DefinitionDetail definitionDetail)
    {
        List<DefinitionDetail> list = definitionDetailService.selectDefinitionDetailList(definitionDetail);
        ExcelUtil<DefinitionDetail> util = new ExcelUtil<DefinitionDetail>(DefinitionDetail.class);
        return util.exportExcel(list, "流程定义明细数据");
    }

    /**
     * 新增流程定义明细
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存流程定义明细
     */
    @RequiresPermissions("business:definitionDetail:add")
    @Log(title = "流程定义明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DefinitionDetail definitionDetail)
    {
        return toAjax(definitionDetailService.insertDefinitionDetail(definitionDetail));
    }

    /**
     * 修改流程定义明细
     */
    @GetMapping("/edit/{definitionInfoId}")
    public String edit(@PathVariable("definitionInfoId") Long definitionInfoId, ModelMap mmap)
    {
        DefinitionDetail definitionDetail = definitionDetailService.selectDefinitionDetailById(definitionInfoId);
        mmap.put("definitionDetail", definitionDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存流程定义明细
     */
    @RequiresPermissions("business:definitionDetail:edit")
    @Log(title = "流程定义明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DefinitionDetail definitionDetail)
    {
        return toAjax(definitionDetailService.updateDefinitionDetail(definitionDetail));
    }

    /**
     * 删除流程定义明细
     */
    @RequiresPermissions("business:definitionDetail:remove")
    @Log(title = "流程定义明细", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(definitionDetailService.deleteDefinitionDetailByIds(ids));
    }


    @Log(title = "流程定义明细", businessType = BusinessType.OTHER)
    @PostMapping( "/deploy")
    @ResponseBody
    public AjaxResult deploy(MultipartFile processDefinition, Long definitionId) {

        if (processDefinition != null && processDefinition.getSize()>0) { //判断是否为空, 是否有内容
            //获取源文件名
            String originalFilename = processDefinition.getOriginalFilename();

            //获取文件后缀, 得到格式
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

            //支持把文件和png打包zip, 支持直接部署zip格式
            if ("bpmn".equals(extName) || "zip".equals(extName)) {
                //将上传文件储存到服务器中, 路径在配置文件中设置
                try {

                    String fileName = FileUploadUtils.upload(processDefinition);

                    //得到文件储存路径
                    String filePath = RuoYiConfig.getProfile() + fileName.substring(Constants.RESOURCE_PREFIX.length() + 1);

                    //将服务器上的文件部署到Activiti中
                    definitionDetailService.deploy(definitionId, filePath);
                    return AjaxResult.success("部署成功");

                } catch (IOException e) {
                    e.printStackTrace();
                    return AjaxResult.error("文件上传失败");

                }
            } else {
                return AjaxResult.error("部署失败, 文件格式错误");
            }
        } else {
            return AjaxResult.error("文件上传失败, 未检测到有效文件");

        }
    }


    @Log(title = "查询流程图, 流程文件", businessType = BusinessType.OTHER)
    @GetMapping( "/readResource")
    @ResponseBody
    public void readResource(String deployId, String type, HttpServletResponse response) throws IOException {

        InputStream inputStream = null;
        if ("xml".equals(type)) {
            inputStream = processService.getInputStream(deployId);
        } else if ("png".equals(type)) {
            inputStream = processService.getProcessImg(deployId);
        }
        IOUtils.copy(inputStream, response.getOutputStream());
    }
}
