package com.cmp.workflow.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
/**
 * 流程定义管理
 */
@Controller
@RequestMapping(value = "/process")
public class ProcessController  extends BaseController {

	@Autowired
	private RepositoryService repositoryService;

	/**
	 * 查找已定义部署流程列表
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表ProcessInstanceVo");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> processDefinitionList =new ArrayList<ProcessDefinition>();
		if(StringUtils.isNotBlank(keywords)){
			processDefinitionList = query.latestVersion().processDefinitionNameLike("%"+keywords+"%").orderByDeploymentId().desc()
														.listPage(page.getCurrentPage(), page.getShowCount());
		}else{
			processDefinitionList = query.latestVersion().orderByDeploymentId().desc().listPage(page.getCurrentPage(), page.getShowCount());
		}
		long count = query.count();
		page.setTotalResult((int) count);
		List<PageData> varList = new ArrayList<PageData>();
		for (ProcessDefinition processDef : processDefinitionList) {
			PageData pageDeta = new PageData();
			
			String deploymentId = processDef.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
			pageDeta.put("id", processDef.getId());
			pageDeta.put("deploymentId", deploymentId);
			pageDeta.put("name", deployment.getName());
			pageDeta.put("key", processDef.getKey());
			pageDeta.put("version", processDef.getVersion());
			pageDeta.put("deploymentTime", deployment.getDeploymentTime());
			pageDeta.put("resourceName", processDef.getResourceName());
			pageDeta.put("diagramResourceName", processDef.getDiagramResourceName());
			
			varList.add(pageDeta);
		}
	
		mv.setViewName("workflow/process_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * 查找已定义部署流程历史列表
	 * @return
	 */
	@RequestMapping(value = "/listHis")
	public ModelAndView listHis(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表ProcessInstanceVo");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String processDefinitionKey = pd.getString("processDefinitionKey");
		
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> processDefinitionList =new ArrayList<ProcessDefinition>();
		processDefinitionList = query.processDefinitionKey(processDefinitionKey).orderByProcessDefinitionVersion().desc().listPage(page.getCurrentPage(), page.getShowCount());
		long count = query.count();
		page.setTotalResult((int) count);
		List<PageData> varList = new ArrayList<PageData>();
		for (ProcessDefinition processDef : processDefinitionList) {
			PageData pageDeta = new PageData();
			
			String deploymentId = processDef.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
			pageDeta.put("id", processDef.getId());
			pageDeta.put("deploymentId", deploymentId);
			pageDeta.put("name", deployment.getName());
			pageDeta.put("key", processDef.getKey());
			pageDeta.put("version", processDef.getVersion());
			pageDeta.put("deploymentTime", deployment.getDeploymentTime());
			pageDeta.put("resourceName", processDef.getResourceName());
			pageDeta.put("diagramResourceName", processDef.getDiagramResourceName());
			
			varList.add(pageDeta);
		}
	
		mv.setViewName("workflow/process_listhis");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**删除部署的流程，级联删除流程实例
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"删除Process");
		PageData pd = new PageData();
		pd = this.getPageData();
		String processDefinitionId = pd.getString("id");
		
		try {
			ProcessDefinition processDef =repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
			repositoryService.deleteDeployment(processDef.getDeploymentId(), true);
			logger.info("删除部署流程成功");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除process");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			for(int i = 0; i< ArrayDATA_IDS.length; i++) {
				ProcessDefinition processDef =repositoryService.createProcessDefinitionQuery().processDefinitionId(ArrayDATA_IDS[i]).singleResult();
				repositoryService.deleteDeployment(processDef.getDeploymentId(), true);
			}
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 上传流程模型
	 * @param file
	 * @return
	 */
    @RequestMapping(value = "uploadModel")
	public ModelAndView uploadModel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"上传流程模型");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("username", Jurisdiction.getUsername()); // 创建者
		String filePath = PathUtil.getClasspath() + Const.FILEPATHFILEOA + pd.getString("url");
		File file = new File(filePath);
		String fileName = file.getName();
		InputStream fileInputStream = new FileInputStream(file);
		Deployment deployment = null;
		String extension = FilenameUtils.getExtension(fileName);
		if (extension.equals("zip") || extension.equals("bar")) {
			ZipInputStream zip = new ZipInputStream(fileInputStream);
			deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
		} else {
			deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
		}
		if(deployment!=null){
			logger.info("上传成功");
		}
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
    
    /**
     * 转换成流程模型
     * @param processDefinitionId
     * @return
     */
    @RequestMapping(value = "convertToModel")
    public ModelAndView convertToModel() throws Exception {	
    	logBefore(logger, Jurisdiction.getUsername()+"上传流程模型");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String processDefinitionId = pd.getString("processDefinitionId");
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
				processDefinition.getResourceName());
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

		BpmnJsonConverter converter = new BpmnJsonConverter();
		com.fasterxml.jackson.databind.node.ObjectNode modelNode = converter.convertToJson(bpmnModel);
		Model modelData = repositoryService.newModel();
		modelData.setKey(processDefinition.getKey());
		modelData.setName(processDefinition.getName());
		modelData.setCategory(processDefinition.getDeploymentId());

		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
		modelData.setMetaInfo(modelObjectNode.toString());

		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
		logger.info("转换成功");
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
    
    
    /**
     * 读取资源，通过部署ID
     *
     * @param processDefinitionId 流程定义
     * @param resourceType        资源类型(xml|image)
     * @throws Exception
     */
    @RequestMapping(value = "resource/read")
    public void loadByDeployment(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType,
                                 HttpServletResponse response) throws Exception {
    	response.setCharacterEncoding("UTF-8");
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
}
