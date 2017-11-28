package com.cmp.workflow.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.LeaveService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 在线办公(工作流与业务结合调试用)
 * 
 * @author liuweixing
 *
 */
@Controller
@RequestMapping(value = "/leave")
public class LeaveController extends BaseController {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private LeaveService leaveService;
	@Autowired
	 private TaskService taskService; 

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView resAppPre() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("leave/apply_edit");
		return mv;
	}

	/**
	 * 启动请假工作流
	 */
	@RequestMapping(value = "/startWorkflow")
	public ModelAndView startWorkflow() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Usergroup");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("id", this.get32UUID()); // ID 主键

		try {
			String currentUserId = Jurisdiction.getUsername();
			Map<String, Object> variables = new HashMap<String, Object>();
			identityService.setAuthenticatedUserId(currentUserId);
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(pd.getString("type"), variables);
			String pId = processInstance.getId();
			pd.put("procInstId", pId);
			pd.put("USERNAME", currentUserId);
			leaveService.save(pd);
			logger.info("流程已启动，流程ID：" + processInstance.getId());
		} catch (Exception e) {
			logger.error("启动流程失败" + e.toString(), e);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
    /**
     * 签收任务列表
     */
	@SuppressWarnings("finally")
	@RequestMapping(value="/signtasklist")
	public ModelAndView signtasklist(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表TaskVo");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		try {
			String currentUserId = Jurisdiction.getUsername();			
			// 根据当前人的ID查询
	        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateUser(currentUserId);	        
			List<Task> tasks = taskQuery.listPage(page.getCurrentPage(), page.getShowCount());
			
			List<PageData>	varList = new ArrayList<PageData>();
			long count = taskQuery.count();
			page.setTotalResult((int) count);
			for(Task task:tasks){
				PageData pageDeta = new PageData();
				
				varList.add(pageDeta);
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
			}	
			
			mv.setViewName("leave/signtask_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
					
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			return mv;
		}
	}
	
	/**签收任务
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/signtask")
	public void signtask(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "签收任务claim");
		PageData pd = new PageData();
		pd = this.getPageData();
		String taskId = pd.getString("taskId");
		taskService.claim(taskId, Jurisdiction.getUsername());
		logger.info("签收成功");
		out.write("success");
		out.close();
	}
    
    /**
     * 待办任务列表
     */
	@SuppressWarnings("finally")
	@RequestMapping(value="/todotasklist")
	public ModelAndView todotasklist(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表TaskVo");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		try {
			String currentUserId = Jurisdiction.getUsername();			
			// 根据当前人的ID查询
	        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(currentUserId);	        
			List<Task> tasks = taskQuery.listPage(page.getCurrentPage(), page.getShowCount());
			
			List<PageData>	varList = new ArrayList<PageData>();
			long count = taskQuery.count();
			page.setTotalResult((int) count);
			for(Task task:tasks){
				PageData pageDeta = new PageData();
				
				varList.add(pageDeta);
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
			}	
			
			mv.setViewName("leave/todotask_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
					
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			return mv;
		}
	}
	

	
	/**办理任务
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/complete")
	public void complete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "办理任务complete");
		PageData pd = new PageData();
		pd = this.getPageData();
		String taskId = pd.getString("taskId");
		taskService.complete(taskId, pd);
		logger.info("办理任务成功");
		out.write("success");
		out.close();
	}
}
