package com.cmp.workflow.controller;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.util.DateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 流程模型控制器
 */
@Controller
@RequestMapping(value = "/workflowmodel")
public class ModelController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;
	
    /**
     * 模型列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"列表workflowmodel");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		ModelQuery modelQuery=repositoryService.createModelQuery();
		List<Model> list =new ArrayList<Model>();
		List<PageData>	varList = new ArrayList<PageData>();
		// 查询流程模型  act_re_model
		if(StringUtils.isNotBlank(keywords)){
			list = modelQuery.modelNameLike("%"+keywords+"%").orderByCreateTime().desc().listPage(page.getCurrentPage(), page.getShowCount());
		}else{
			list = modelQuery.list();//.orderByCreateTime().desc().listPage(1, page.getShowCount());//page.getCurrentPage()
		}
		
		long count = modelQuery.count();
		page.setTotalResult((int) count);
		for(Model model : list) {
			PageData pageDeta = new PageData();
			pageDeta.put("category", model.getCategory());
			pageDeta.put("ceateTime", DateUtil.dateToString(model.getCreateTime(), DateUtil.DEFAULT_FORMAT));
			pageDeta.put("deploymentId", model.getDeploymentId());
			pageDeta.put("id", model.getId());
			pageDeta.put("key", model.getKey());
			pageDeta.put("lastUpdateTime", DateUtil.dateToString(model.getLastUpdateTime(), DateUtil.DEFAULT_FORMAT));
			pageDeta.put("metaInfo", model.getMetaInfo());
			pageDeta.put("name", model.getName());
			pageDeta.put("tenantId", model.getTenantId());
			pageDeta.put("version", model.getVersion());
			
			varList.add(pageDeta);
		}
		
		mv.setViewName("workflow/model_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
    
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", "1");
		mv.setViewName("workflow/model_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}
    
	/** 创建模型
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response)throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"去创建workflowmodel");
		PageData pd = new PageData();
		pd = this.getPageData();
		String name = pd.getString("name");
		String key = pd.getString("key");
		String description = pd.getString("description");
		
		try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(key));

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

            //response.sendRedirect(request.getContextPath() + "/act-process-editor/modeler.html?modelId=" + modelData.getId());
        } catch (Exception e) {
            logger.error("创建模型失败", e);
        }
		
		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	 /**编辑模型
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public void goEdit(HttpServletRequest request, HttpServletResponse response)throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"去编辑workflowmodel");
		PageData pd = new PageData();
		pd = this.getPageData();
		String modelId = pd.getString("id");
		 try {
			 response.sendRedirect(request.getContextPath() + "/act-process-editor/modeler.html?modelId=" + modelId);
		 }catch (Exception e) {
				logger.error(e.toString(),e);
		}			
	}	
	
	/**根据Model部署流程
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/deploy")
	public void deploy(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"部署workflowmodel");
		PageData pd = new PageData();
		pd = this.getPageData();
		String modelId = pd.getString("id");
		
		try {        	
			Model modelData = repositoryService.getModel(modelId);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper()
			.readTree(repositoryService.getModelEditorSource(modelData.getId()));
			byte[] bpmnBytes = null;
			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);
			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment()
					.name(modelData.getName()).addString(processName, new String(bpmnBytes,"UTF-8")).deploy();
			out.write("success");
			logger.info("模型部署流程成功，部署ID=" + deployment.getId());
	    } catch (Exception e) {
			logger.error("根据模型部署流程失败：modelId=" +modelId);
	    } finally {
	    	out.close();
	    }
	}

    /**
     * 导出model对象为指定类型
     * @param modelId 模型ID
     * @param type 导出文件类型(bpmn\json)
     */
    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) {
    	logBefore(logger, Jurisdiction.getUsername()+"导出workflowmodel");
		PageData pd = new PageData();
		pd = this.getPageData();
		String modelId = pd.getString("id");
		String type = "bpmn";
		
    	try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 处理异常
            if (bpmnModel.getMainProcess() == null) {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.getOutputStream().println("no main process, can't export for type: " + type);
                response.flushBuffer();
                return;
            }
            String filename = "";
            byte[] exportBytes = null;	
            String mainProcessId = bpmnModel.getMainProcess().getId();
            
            switch (type) {
                case "bpmn": {
                    BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                    exportBytes = xmlConverter.convertToXML(bpmnModel);
                    filename = mainProcessId + ".bpmn20.xml";
                    break;
                }	
                case "json": {
                    exportBytes = modelEditorSource;
                    filename = mainProcessId + ".json";
                    break;
                }
            }	
            ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
            IOUtils.copy(in, response.getOutputStream());

            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId="+modelId+"type="+type, e);
        }       
    }
    
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"删除workflowmodel");
		PageData pd = new PageData();
		pd = this.getPageData();
		String modelId = pd.getString("id");
		
		try {
			repositoryService.deleteModel(modelId);
			out.write("success");
		} catch (Exception e) {
			logger.error(e.toString(),e);
		} finally {
			out.close();
		}
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"批量删除workflowmodel");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			for(int i = 0; i< ArrayDATA_IDS.length; i++) {
				repositoryService.deleteModel(ArrayDATA_IDS[i]);
			}
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
}
