package com.cmp.workflow.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.workflow.entity.ProcessInstanceVo;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
/**
 * 运行中流程管理
 */
@Controller
@RequestMapping(value = "/instance")
public class InstanceController  extends BaseController {

	 @Autowired
	 private RuntimeService runtimeService;
	 @Autowired
	 private TaskService taskService;
	 @Autowired
	 private HistoryService historyService;

	/**
	 * 运行中流程列表
	 */
	 @RequestMapping(value="/listPro")
	 public ModelAndView listPro(Page page) throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"列表ProcessInstanceVo");
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String keywords = pd.getString("keywords");				//关键词检索条件
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			page.setPd(pd);
			
			ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
			List<ProcessInstance> list =new ArrayList<ProcessInstance>();
			if(StringUtils.isNotBlank(keywords)){
				list = query.processInstanceTenantIdLike("%"+keywords+"%")
						.orderByProcessInstanceId().desc()
						.listPage(page.getCurrentPage(), page.getShowCount());
			}else{
				list = query.orderByProcessInstanceId().desc()
						.listPage(page.getCurrentPage(), page.getShowCount());
			}
			long count = query.count();
			page.setTotalResult((int) count);
			List<PageData>	varList = new ArrayList<PageData>();
			for(ProcessInstance t:list) {
				PageData pageDeta = new PageData();
				
				ProcessInstanceVo vo=new ProcessInstanceVo(t.getId(),t.getProcessInstanceId() ,t.getProcessDefinitionId());		
				// 设置当前任务信息
				Task task=taskService.createTaskQuery().processInstanceId(t.getProcessInstanceId()).active().orderByTaskCreateTime().desc().singleResult();	
				
				pageDeta.put("id", vo.getId());
				pageDeta.put("taskId", task.getId());
				pageDeta.put("taskDefinitionKey", task.getTaskDefinitionKey());
				pageDeta.put("name", task.getName());
				pageDeta.put("processDefinitionId", task.getProcessDefinitionId());
				pageDeta.put("processInstanceId", task.getProcessInstanceId());
				pageDeta.put("priority", task.getPriority());
				pageDeta.put("createTime", task.getCreateTime());
				pageDeta.put("dueDate", task.getDueDate());
				pageDeta.put("description", task.getDescription());
				pageDeta.put("owner", task.getOwner());
				pageDeta.put("assignee", task.getAssignee());
				
				varList.add(pageDeta);
			}
			
			mv.setViewName("workflow/instancePro_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			return mv;
	}
	 
		/**
		 * 历史流程列表
		 * @param page
		 * @param keyWord
		 * @return
		 */
		@RequestMapping(value="/listHis")
		public ModelAndView list(Page page) throws Exception {
			logBefore(logger, Jurisdiction.getUsername()+"列表HistoricProcessInstance");
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String keywords = pd.getString("keywords");				//关键词检索条件
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			page.setPd(pd);
			
			HistoricProcessInstanceQuery query=historyService.createHistoricProcessInstanceQuery().finished()
					.orderByProcessInstanceEndTime().desc();
			List<HistoricProcessInstance> list = query.listPage(page.getCurrentPage(), page.getShowCount());
			long count = query.count();
			page.setTotalResult((int) count);
			List<PageData>	varList = new ArrayList<PageData>();
			for (HistoricProcessInstance t : list) {
				PageData pageDeta = new PageData();
				pageDeta.put("id", t.getId());
				pageDeta.put("processDefinitionId", t.getProcessDefinitionId());
				pageDeta.put("startTime", t.getStartTime());
				pageDeta.put("endTime", t.getEndTime());
				pageDeta.put("deleteReason", t.getDeleteReason());
				varList.add(pageDeta);
			}
			
			mv.setViewName("workflow/instanceHis_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			return mv;
		}
		
		/**
		 * 个人运行中流程列表
		 */
		@RequestMapping(value = "/listProByCurrentUserId")
		public ModelAndView listProByCurrentUserId(Page page) throws Exception {
			logBefore(logger, Jurisdiction.getUsername() + "列表ProcessInstanceVo");
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String keywords = pd.getString("keywords"); // 关键词检索条件
			if (null != keywords && !"".equals(keywords)) {
				pd.put("keywords", keywords.trim());
			}
			page.setPd(pd);

			ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().involvedUser(Jurisdiction.getUsername()).active().orderByProcessInstanceId().desc();
			List<ProcessInstance> list = query.listPage(page.getCurrentPage(), page.getShowCount());
			List<PageData> varList = new ArrayList<PageData>();
			for (ProcessInstance t : list) {
				PageData pageDeta = new PageData();

				ProcessInstanceVo vo = new ProcessInstanceVo(t.getId(), t.getProcessInstanceId(), t.getProcessDefinitionId());
				// 设置当前任务信息
				Task task=taskService.createTaskQuery().processInstanceId(t.getProcessInstanceId()).active().orderByTaskCreateTime().desc().singleResult();

				pageDeta.put("id", vo.getId());
				pageDeta.put("taskId", task.getId());
				pageDeta.put("taskDefinitionKey", task.getTaskDefinitionKey());
				pageDeta.put("name", task.getName());
				pageDeta.put("processDefinitionId", task.getProcessDefinitionId());
				pageDeta.put("processInstanceId", task.getProcessInstanceId());
				pageDeta.put("priority", task.getPriority());
				pageDeta.put("createTime", task.getCreateTime());
				pageDeta.put("dueDate", task.getDueDate());
				pageDeta.put("description", task.getDescription());
				pageDeta.put("owner", task.getOwner());
				pageDeta.put("assignee", task.getAssignee());

				varList.add(pageDeta);
			}

			mv.setViewName("leave/instancePro_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			return mv;
		}
		
		/**
		 * 个人历史流程列表
		 * @param page
		 * @param keyWord
		 * @return
		 */
		@RequestMapping(value="/listHisByCurrentUserId")
		public ModelAndView listHisByCurrentUserId(Page page) throws Exception {
			logBefore(logger, Jurisdiction.getUsername() + "列表HistoricProcessInstance");
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String keywords = pd.getString("keywords");				//关键词检索条件
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			page.setPd(pd);
			
			// 根据当前人的ID查询
			HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery()
					.startedBy(Jurisdiction.getUsername()).finished()
					.orderByProcessInstanceEndTime().desc();
			List<HistoricProcessInstance> list = query.listPage(page.getCurrentPage(), page.getShowCount());
			List<PageData>	varList = new ArrayList<PageData>();
			for (HistoricProcessInstance t : list) {
				PageData pageDeta = new PageData();
				pageDeta.put("id", t.getId());
				pageDeta.put("processDefinitionId", t.getProcessDefinitionId());
				pageDeta.put("startTime", t.getStartTime());
				pageDeta.put("endTime", t.getEndTime());
				pageDeta.put("deleteReason", t.getDeleteReason());
				varList.add(pageDeta);
			}
			
			mv.setViewName("leave/instanceHis_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			return mv;
		}
		
		/**去流程图弹出页
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/goInstance")
		public void goInstance(HttpServletRequest request, HttpServletResponse response)throws Exception {
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String pdId = pd.getString("processDefinitionId");
			String pIId = pd.getString("processInstanceId");
//			mv.setViewName("/act-process-editor/diagram-viewer/index.html?processDefinitionId="+pdId+"&processInstanceId="+pIId);
//			mv.addObject("pd", pd);
//			return mv;
			
			response.sendRedirect(request.getContextPath() + "/act-process-editor/diagram-viewer/index.html?processDefinitionId="+pdId+"&processInstanceId="+pIId);
		}
	
}
