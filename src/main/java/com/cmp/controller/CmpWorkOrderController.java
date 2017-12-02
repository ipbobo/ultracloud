package com.cmp.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.activiti.service.ActivitiService;
import com.cmp.entity.Project;
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpOpServeService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.ProjectService;
import com.cmp.sid.CmpDict;
import com.cmp.sid.CmpOpServe;
import com.cmp.sid.CmpWorkOrder;
import com.cmp.sid.RelateTask;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

@Controller
public class CmpWorkOrderController extends BaseController{
	@Resource
	private CmpWorkOrderService cmpWorkOrderService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="cmpDictService")
	private CmpDictService cmpDictService;
	
	@Resource(name="projectService")
	private ProjectService projectService;
	
	@Resource
	private ActivitiService activitiService;
	
	@Resource
	private CmpOpServeService cmpOpServeService;
	
	@RequestMapping(value="/queryUserApplyWorkOrderPre")
	public ModelAndView querUserApplyWorkOrderPre(Page page) throws Exception{
		Session session = Jurisdiction.getSession();
		ModelAndView mv = new ModelAndView();
		User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
		if (userr == null) {
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user == null) {
				mv.setViewName("system/index/login");
				return mv;
			}
			userr = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
			session.setAttribute(Const.SESSION_USERROL, userr);						//存入session	
		}
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USERNAME", userr.getUSERNAME());
		page.setPd(pd);
		
		
		//工单查询
		List<PageData> workOrderList = new ArrayList<PageData>();
		workOrderList = cmpWorkOrderService.listUserWorkorderByPd(page);
		//中文转化
		Map appTypeNameMap =  getAppTypeNameMap();
		Map workStatusMap = getWorkorderStatusNameMap();
		Map projectNameMap = getProjectNameMap();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (PageData workorderpd : workOrderList) {
			workorderpd.put("projectName", projectNameMap.get(workorderpd.get("projectCode") == null ? "" : workorderpd.get("projectCode")));
			workorderpd.put("statusName", workStatusMap.get(workorderpd.get("status") == null ? "" : workorderpd.get("status")));
			workorderpd.put("appTypeName", appTypeNameMap.get(workorderpd.get("appType") == null ? "" : workorderpd.get("appType")));
			workorderpd.put("createTime", workorderpd.get("createTime") == null ? "" : df.format(workorderpd.get("createTime")));
			
		}
		
		
		
		//工单类型
		List<CmpDict> workorderTypeList =  cmpDictService.getCmpDictList("workorder_type");
		//工单状态
		List<CmpDict> workorderStatusList =  cmpDictService.getCmpDictList("workorder_status");
		//项目名称
		List<Project>  projectList = projectService.listAllProject();
		
		//获取登录用户类型
		Map<String, String> qxMap = new HashMap<String, String>();
		String userType = userr.getRole().getTYPE();
		if (userType != null && (userType.equals("applicant") || userType.equals("admin")) ) {
			qxMap.put("query", "1");
		}else if (userType != null && userType.equals("audit")) {
			qxMap.put("check", "1");
		}else if (userType != null && userType.equals("executor")) {
			qxMap.put("execute", "1");
		}
		
		mv.addObject("workOrderList", workOrderList);
		mv.addObject("workorderTypeList", workorderTypeList);
		mv.addObject("workorderStatusList", workorderStatusList);
		mv.addObject("projectList", projectList);
		mv.addObject("appTypeNameMap", getAppTypeNameMap());
		mv.addObject("workorderStatusNameMap", getWorkorderStatusNameMap());
		mv.addObject("QX", qxMap); // 右侧按钮权限
		mv.setViewName("workorder/query_user_workorder");
		return mv;
	}
	
	@RequestMapping(value="/goWorkorderCheck")
	public ModelAndView goWorkorderCheck(String appNo) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		if (appNo == null || appNo.length() == 0) {
			return null;
		}
		CmpWorkOrder toCheckWorkorder = cmpWorkOrderService.findByAppNo(appNo);
		String toCheckUrl = "";
		//是资源申请，跳资源申请审核页面或运维申请审核页面
		if (toCheckWorkorder.getAppType()!= null && toCheckWorkorder.getAppType().equals("1")) {
			toCheckUrl = "workorder/applycheck";
		}else if (toCheckWorkorder.getAppType()!= null && toCheckWorkorder.getAppType().equals("2")) {
			toCheckUrl = "workorder/opercheck";
		}
		mv.addObject("workorder", toCheckWorkorder);
		mv.setViewName(toCheckUrl);
		return mv;
	}
	
	@RequestMapping(value="/goWorkorderExecute")
	public ModelAndView goWorkorderExecute(String appNo) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		if (appNo == null || appNo.length() == 0) {
			return null;
		}
		CmpWorkOrder toExcuteWorkorder = cmpWorkOrderService.findByAppNo(appNo);
		String toExecuteUrl = "";
		//是资源申请，跳资源申请实施页面或运维申请实施页面
		if (toExcuteWorkorder.getAppType()!= null && toExcuteWorkorder.getAppType().equals("1")) {
			toExecuteUrl = "workorder/applyexecute";
		}else if (toExcuteWorkorder.getAppType()!= null && toExcuteWorkorder.getAppType().equals("2")) {
			toExecuteUrl = "workorder/operexecute";
		}
		mv.addObject("workorder", toExcuteWorkorder);
		mv.setViewName(toExecuteUrl);
		return mv;
	}
	
	
	@RequestMapping(value="/goWorkorderView")
	public ModelAndView goWorkorderView(String appNo) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		if (appNo == null || appNo.length() == 0) {
			return null;
		}
		CmpWorkOrder toViewWorkorder = cmpWorkOrderService.findByAppNo(appNo);
	
		//获取工作流程图,查询流程定义
		HistoricProcessInstance hpi = activitiService.findProcessInst(toViewWorkorder.getProcInstId());
		ActivityImpl workorderImag = null;
		if (!toViewWorkorder.getStatus().equals("5")) {
			//流程执行未完毕
			workorderImag = activitiService.getProcessMap(hpi.getProcessDefinitionId(), hpi.getId()); 
		}
		//获取流程信息
		List<RelateTask> relateTaskList = new ArrayList<RelateTask>();
		List<HistoricActivityInstance> hisActInst = activitiService.getHisActInfo(toViewWorkorder.getProcInstId());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (HistoricActivityInstance hai : hisActInst) {
			if(hai.getActivityName() == null || hai.getActivityName().equals("")) {
				continue;
			}
			RelateTask rt = new RelateTask();
			rt.setTaskNo(hai.getActivityId());
			rt.setAssignee(hai.getAssignee());
			rt.setStatus(hai.getEndTime() != null ? "关闭" : "新建");
			rt.setResult(hai.getEndTime() != null ? "符合申请" : "未处理");
			rt.setTime(hai.getEndTime() == null? "无" : df.format(hai.getEndTime()));
			relateTaskList.add(rt);
		}
		
		mv.addObject("relateTaskList", relateTaskList);
		mv.addObject("hisActInst", hisActInst);
		mv.addObject("workorderImag", workorderImag);
		mv.addObject("procDefId", hpi.getProcessDefinitionId());
		//是资源申请，跳资源申请审核页面或运维申请审核页面
		String toCheckUrl = "";
		if (toViewWorkorder.getAppType()!= null && toViewWorkorder.getAppType().equals("1")) {
			
			toCheckUrl = "workorder/applyview";
		}else if (toViewWorkorder.getAppType()!= null && toViewWorkorder.getAppType().equals("2")) {
			//查询工单关联的运维信息
			CmpOpServe opServe = cmpOpServeService.findByOrderNo(toViewWorkorder.getOrderNo());
			cmpOpServeService.encase(opServe);  //中文填充
			mv.addObject("opServe", opServe);
			toCheckUrl = "workorder/operview";
		}
		mv.addObject("workorder", toViewWorkorder);
		mv.setViewName(toCheckUrl);
		return mv;
	}
	
	
	/**工单审核
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doCheck")
	@ResponseBody
	public Object doCheck(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String resultInfo = "审核失败";
		String appNo = request.getParameter("appNo");
		Session session = Jurisdiction.getSession();
		
		CmpWorkOrder toCheckWorkorder = cmpWorkOrderService.findByAppNo(appNo);
		if (toCheckWorkorder == null) {
			resultInfo = "审核失败,工单号不存在";
			map.put("result", resultInfo);	
			return map;
		}
		User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
		if (userr == null) {
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user == null) {
				map.put("result", resultInfo);	
				return map;
			}
			userr = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
			session.setAttribute(Const.SESSION_USERROL, userr);						//存入session	
		}
		
		
		List<Task> userTaskList = activitiService.findGroupList(userr.getUSERNAME(), 1, 100);
		for (Task task : userTaskList) {
			if (task.getProcessInstanceId().equals(toCheckWorkorder.getProcInstId())) {
				activitiService.claimTask(task.getId(), userr.getUSERNAME());
				activitiService.handleTask(appNo, toCheckWorkorder.getProcInstId(), userr.getUSERNAME(), null, null);
				
				//更新工单(流程实例ID 和 工单状态)
				Map<String, String> updateParams = new HashMap<String, String>();
				updateParams.put("status", "2");  //进入运维执行状态
				updateParams.put("procInstId", toCheckWorkorder.getProcInstId());
				cmpWorkOrderService.updateWorkOrder(appNo, updateParams);
				
				resultInfo = "审核成功";
				map.put("result", resultInfo);	
				return map;
			}
		}
		map.put("result", resultInfo);				//返回结果
		return map;
	}
	
	/**工单执行
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doExecute")
	@ResponseBody
	public Object doExecute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String resultInfo = "执行失败";
		String appNo = request.getParameter("appNo");
		Session session = Jurisdiction.getSession();
		
		CmpWorkOrder toCheckWorkorder = cmpWorkOrderService.findByAppNo(appNo);
		if (toCheckWorkorder == null) {
			resultInfo = "执行失败,工单号不存在";
			map.put("result", resultInfo);	
			return map;
		}
		User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
		if (userr == null) {
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user == null) {
				map.put("result", resultInfo);	
				return map;
			}
			userr = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
			session.setAttribute(Const.SESSION_USERROL, userr);						//存入session	
		}
		
		
		List<Task> userTaskList = activitiService.findGroupList(userr.getUSERNAME(), 1, 100);
		for (Task task : userTaskList) {
			if (task.getProcessInstanceId().equals(toCheckWorkorder.getProcInstId())) {
				activitiService.claimTask(task.getId(), userr.getUSERNAME());
				activitiService.handleTask(appNo, toCheckWorkorder.getProcInstId(), userr.getUSERNAME(), null, null);
				
				//更新工单(流程实例ID 和 工单状态)
				Map<String, String> updateParams = new HashMap<String, String>();
				updateParams.put("status", "5");  //进入运维执行状态
				updateParams.put("procInstId", toCheckWorkorder.getProcInstId());
				cmpWorkOrderService.updateWorkOrder(appNo, updateParams);
				
				resultInfo = "执行成功";
				map.put("result", resultInfo);	
				return map;
			}
		}
		map.put("result", resultInfo);				//返回结果
		return map;
	}
	
	
	/**导出工单信息到EXCEL
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/workorderExcel")
	public ModelAndView exportExcel(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Session session = Jurisdiction.getSession();
			User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
			if (userr == null) {
				User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
				if (user == null) {
					mv.setViewName("system/index/login");
					return mv;
				}
				userr = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
				session.setAttribute(Const.SESSION_USERROL, userr);						//存入session	
			}
			pd = this.getPageData();
			pd.put("USERNAME", userr.getUSERNAME());
			page.setPd(pd);
			
			Map appTypeNameMap =  getAppTypeNameMap();
			Map workStatusMap = getWorkorderStatusNameMap();
			Map projectNameMap = getProjectNameMap();
			List<PageData> workOrderList = new ArrayList<PageData>();
			workOrderList = cmpWorkOrderService.listUserWorkorderByPd(page);
			
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("工单号"); 		//1
				titles.add("工单类型");  		//2
				titles.add("工单状态");			//3
				titles.add("项目名称");			//4
				titles.add("工单时间");			//5
				dataMap.put("titles", titles);
				List<PageData> varList = new ArrayList<PageData>();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(int i=0;i<workOrderList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", workOrderList.get(i).getString("appNo"));		//1
					vpd.put("var2", appTypeNameMap.get(workOrderList.get(i).getString("appType") == null ?  "" : workOrderList.get(i).getString("appType")));		//2
					vpd.put("var3", workStatusMap.get(workOrderList.get(i).getString("status") == null ? "" : workOrderList.get(i).getString("status") ));			//3
					vpd.put("var4", projectNameMap.get(workOrderList.get(i).getString("projectCode") == null ? "" : workOrderList.get(i).getString("projectCode")));	//4
					vpd.put("var5", df.format(workOrderList.get(i).get("createTime")));		//5
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
				mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/** 
	 * 获取流程图像 
	 *@author
	 * @return 
	 */  
	@RequestMapping(value="/findworkflowPic")  
	public void findPic(String procDefId,HttpServletResponse response){  
	    //HttpServletResponse response = ServletActionContext.getResponse();  
	    try {  
	        InputStream pic = activitiService.findProcessPic(procDefId);  
	          
	        byte[] b = new byte[1024];  
	        int len = -1;  
	        while((len = pic.read(b, 0, 1024)) != -1) {  
	            response.getOutputStream().write(b, 0, len);  
	        }  
	  
	    } catch (Exception e) {  
	        logger.error("获取图片失败。。。");  
	        e.printStackTrace();  
	    }  
	}  
	
	
	
	public Map getAppTypeNameMap() {
		Map<String, String> appTypeNameMap = new HashMap<String, String>();
		List<CmpDict> workorderTypeList =  cmpDictService.getCmpDictList("workorder_type");
		for (CmpDict workorderDict  : workorderTypeList) {
			appTypeNameMap.put(workorderDict.getDictCode(), workorderDict.getDictValue());
		}
		return appTypeNameMap;
	}
	
	public Map getWorkorderStatusNameMap() {
		Map<String, String> workorderStatusNameMap = new HashMap<String, String>();
		List<CmpDict> workorderStatusList = cmpDictService.getCmpDictList("workorder_status");
		for (CmpDict workorderStatusDict  : workorderStatusList) {
			workorderStatusNameMap.put(workorderStatusDict.getDictCode(), workorderStatusDict.getDictValue());
		}
		return workorderStatusNameMap;
	}
	
	
	public Map getProjectNameMap() throws Exception {
		Map<String, String> projectNameMap = new HashMap<String, String>();
		List<Project>  projectList = projectService.listAllProject();
		for (Project p : projectList) {
			projectNameMap.put(p.getId(), p.getName());
		}
		return projectNameMap;
		
	}
	
	public String getUserName(String userId) throws Exception {
		User userr = userService.getUserAndRoleById(userId);
		return userr.getNAME();
	}
	
	
	
}
