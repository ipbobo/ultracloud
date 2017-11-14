package com.cmp.activiti.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cmp.activiti.bean.TaskBean;
import com.cmp.util.Page;

//Activiti服务
@Service
public class ActivitiService {
	private static Logger logger = Logger.getLogger(ActivitiService.class);
	@Resource
	RepositoryService repositoryService;//仓库服务
	@Resource
	RuntimeService runtimeService;//流程实例服务
	@Resource
	TaskService taskService;//任务服务
	@Resource
	IdentityService identityService;//标识服务
	@Resource
	FormService formService;//表单服务
	@Resource
	ManagementService managementService;//管理服务
	@Resource
	HistoryService historyService;//历史服务
	
  	//任务列表查询-processDefinitionKey
  	public Page<TaskBean> getTaskList(int currPage, int qryNum) {
  		int totalNum=(int)taskService.createTaskQuery().count();
  		Page<TaskBean> page=new Page<TaskBean>(currPage, qryNum);
  		page.setTotalCount(totalNum);
  		List<Task> taskList=taskService.createTaskQuery().orderByTaskCreateTime().desc().listPage((currPage-1)*qryNum, currPage*qryNum);
  		List<TaskBean> list=new ArrayList<TaskBean>();
  		for(Task task: taskList){
  			TaskBean taskBean=new TaskBean();
  			Map<String, String> appMap=getAppMap(task.getId());//获取业务key及申请人
  			taskBean.setAppNo(appMap.get("businessKey"));//获取业务key
  			taskBean.setApplyUserId(appMap.get("applyUserId"));//获取申请人
  			taskBean.setTaskId(task.getId());
  			taskBean.setTaskName(task.getName());
  			taskBean.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime()));
  			taskBean.setAssignee(task.getAssignee());//处理人
  			Map<String, String> candidateMap=getCandidateMap(task.getId());//获取候选人及候选组
  			taskBean.setUserId(candidateMap.get("userId"));//候选人
  			taskBean.setGroupId(candidateMap.get("groupId"));//候选组
  			taskBean.setProcessInstanceId(task.getProcessInstanceId());
  			taskBean.setExecutionId(task.getExecutionId());
  			taskBean.setProcessDefinitionId(task.getProcessDefinitionId());
  			list.add(taskBean);
  		}
  		
  		page.setResults(list);
  		return page;
  	}
  	
  	//个人任务列表查询
  	public Page<TaskBean> getPersonalTaskList(String currUser, int currPage, int qryNum) {
  		int totalNum=(int)taskService.createTaskQuery().taskAssignee(currUser).count();
  		Page<TaskBean> page=new Page<TaskBean>(currPage, qryNum);
  		page.setTotalCount(totalNum);
  		List<Task> taskList=taskService.createTaskQuery().taskAssignee(currUser).orderByTaskCreateTime().desc().listPage((currPage-1)*qryNum, currPage*qryNum);
  		List<TaskBean> list=new ArrayList<TaskBean>();
  		for(Task task: taskList){
  			TaskBean taskBean=new TaskBean();
  			Map<String, String> appMap=getAppMap(task.getId());//获取业务key及申请人
  			taskBean.setAppNo(appMap.get("businessKey"));//获取业务key
  			taskBean.setApplyUserId(appMap.get("applyUserId"));//获取申请人
  			taskBean.setTaskId(task.getId());
  			taskBean.setTaskName(task.getName());
  			taskBean.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime()));
  			taskBean.setAssignee(task.getAssignee());//处理人
  			Map<String, String> candidateMap=getCandidateMap(task.getId());//获取候选人及候选组
  			taskBean.setUserId(candidateMap.get("userId"));//候选人
  			taskBean.setGroupId(candidateMap.get("groupId"));//候选组
  			taskBean.setProcessInstanceId(task.getProcessInstanceId());
  			taskBean.setExecutionId(task.getExecutionId());
  			taskBean.setProcessDefinitionId(task.getProcessDefinitionId());
  			list.add(taskBean);
  		}
  		
  		page.setResults(list);
  		return page;
  	}
  	
  	//未签收任务列表查询
  	public Page<TaskBean> getUnsignedTaskList(String currUser, int currPage, int qryNum) {
  		int  totalNum=(int)taskService.createTaskQuery().taskCandidateUser(currUser).count();
  		Page<TaskBean> page=new Page<TaskBean>(currPage, qryNum);
  		page.setTotalCount(totalNum);
  		List<Task> taskList=taskService.createTaskQuery().taskCandidateUser(currUser).orderByTaskCreateTime().desc().listPage((currPage-1)*qryNum, currPage*qryNum);
  		List<TaskBean> list=new ArrayList<TaskBean>();
  		for(Task task: taskList){
  			TaskBean taskBean=new TaskBean();
  			Map<String, String> appMap=getAppMap(task.getId());//获取业务key及申请人
  			taskBean.setAppNo(appMap.get("businessKey"));//获取业务key
  			taskBean.setApplyUserId(appMap.get("applyUserId"));//获取申请人
  			taskBean.setTaskId(task.getId());
  			taskBean.setTaskName(task.getName());
  			taskBean.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime()));
  			taskBean.setAssignee(task.getAssignee());//处理人
  			Map<String, String> candidateMap=getCandidateMap(task.getId());//获取候选人及候选组
  			taskBean.setUserId(candidateMap.get("userId"));//候选人
  			taskBean.setGroupId(candidateMap.get("groupId"));//候选组
  			taskBean.setProcessInstanceId(task.getProcessInstanceId());
  			taskBean.setExecutionId(task.getExecutionId());
  			taskBean.setProcessDefinitionId(task.getProcessDefinitionId());
  			list.add(taskBean);
  		}
  		
  		page.setResults(list);
  		return page;
  	}
  	
  	//未完成流程实例列表查询
  	public Page<ProcessInstance> getUnfinishedProcessInstanceQuery(int currPage, int qryNum) {
  		int  totalNum=(int)runtimeService.createProcessInstanceQuery().active().count();
		Page<ProcessInstance> page=new Page<ProcessInstance>(currPage, qryNum);
		page.setTotalCount(totalNum);
		List<ProcessInstance> processInstanceList=runtimeService.createProcessInstanceQuery().active().orderByProcessInstanceId().desc().listPage((currPage-1)*qryNum, currPage*qryNum);
		page.setResults(processInstanceList);
		return page;
  	}
  	
  	//已完成流程实例列表查询
  	public Page<HistoricProcessInstance> getFinishedProcessInstanceQuery(int currPage, int qryNum) {
  		int  totalNum=(int)historyService.createHistoricProcessInstanceQuery().finished().count();
		Page<HistoricProcessInstance> page=new Page<HistoricProcessInstance>(currPage, qryNum);
		page.setTotalCount(totalNum);
		List<HistoricProcessInstance> processInstanceList=historyService.createHistoricProcessInstanceQuery().finished().orderByProcessInstanceStartTime().desc().listPage((currPage-1)*qryNum, currPage*qryNum);
		page.setResults(processInstanceList);
		return page;
  	}
  	
  	//获取业务key及申请人
  	private Map<String, String> getAppMap(String taskId){
  		Map<String, String> map=new HashMap<String, String>();
  		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();//任务处理
  		ProcessInstance processInstance=runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
  		List<IdentityLink> list=runtimeService.getIdentityLinksForProcessInstance(processInstance.getId());
  		map.put("businessKey", processInstance.getBusinessKey());
  		if(list!=null && !list.isEmpty()){
  			for(IdentityLink il: list){
  				if("starter".equals(il.getType())){
  					map.put("applyUserId", il.getUserId());
  					break;//退出循环
  				}
  			}
  		}
  		
  		return map;
  	}
  	
  	//获取候选人及候选组
  	private Map<String, String> getCandidateMap(String taskId){
  		Map<String, String> map=new HashMap<String, String>();
  		List<IdentityLink> list=taskService.getIdentityLinksForTask(taskId);
  		if(list!=null && !list.isEmpty()){
  			StringBuffer groupIdsb=new StringBuffer();
  			StringBuffer userIdsb=new StringBuffer();
  			for(IdentityLink il: list){
  				if("candidate".equals(il.getType())){
  					if(!StringUtils.isBlank(il.getGroupId())){
  						if(groupIdsb.length()==0){
  							groupIdsb.append(il.getGroupId());
  						}else{
  							groupIdsb.append(","+il.getGroupId());
  						}
  					}
  					
  					if(!StringUtils.isBlank(il.getUserId())){
  						if(userIdsb.length()==0){
  							userIdsb.append(il.getUserId());
  						}else{
  							userIdsb.append(","+il.getUserId());
  						}
  					}
  				}
  			}
  			
  			map.put("groupId", groupIdsb.toString());
  			map.put("userId", userIdsb.toString());
  		}
  		
  		return map;
  	}
  	
	//流程发布
	public String deploy(){
		Deployment deployment=repositoryService.createDeployment().name("helloWorld入门程序").addClasspathResource("activiti.helloworld.bpmn").addClasspathResource("activiti.helloworld.png").deploy();
		String processDefinitionId=repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult().getId();//获取流程定义ID
		logger.info("add processDefinition="+processDefinitionId+", Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
		return processDefinitionId;
	}
	
	//流程启动
	public String start(String processDefinitionKey, String applyUserId, String appNo, Map<String, Object> variables){
		identityService.setAuthenticatedUserId(applyUserId);//设置当前的用户ID
		ProcessInstance processInstance=runtimeService.startProcessInstanceByKey(processDefinitionKey, appNo, variables);
		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee(applyUserId).singleResult();
		if(task!=null){//如果任务不为空
			taskService.complete(task.getId(), variables);//完成任务
		}
		
		//dbCenter.save(cmpAppList, CmpAppList.class.getName());
		return processInstance.getId();
	}
	
	//任务处理
	public String handleTask(String taskId, String apprDesc, Map<String, Object> variables){
		try{
			Task task=taskService.createTaskQuery().taskId(taskId).singleResult();//任务处理
			ProcessInstance processInstance=runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			taskService.complete(taskId, variables);//完成任务
			//String hql="update CmpAppList a set a.status=1, a.apprDesc='"+apprDesc+"', a.lastUpdateTime='"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"' where a.appNo='"+processInstance.getBusinessKey()+"'";
			//dbCenter.hqlUpdate(CmpAppList.class.getName(), hql);//更新审批表
			return "任务处理成功"+processInstance.getBusinessKey();
		}catch(ActivitiObjectNotFoundException e){
			return "任务不存在或已被处理";
		}catch(Exception e){
			return "任务处理失败："+e;
		}
	}
	
	//任务处理
	public String handleTask(String appNo, String processInstanceId, String currUser, String apprDesc, Map<String, Object> variables){
		Task task=taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(currUser).singleResult();
		if(task==null){
			return "任务不存在或已被处理";
		}
		
		taskService.complete(task.getId(), variables);//完成任务
		//String hql="update CmpAppList a set a.status=1, a.apprDesc='"+apprDesc+"', a.lastUpdateTime='"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"' where a.appNo='"+appNo+"'";
		//dbCenter.hqlUpdate(CmpAppList.class.getName(), hql);//更新审批表
		return "任务处理成功"+appNo;
	}
}