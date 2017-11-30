package com.cmp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.activiti.service.ActivitiService;
import com.cmp.entity.Project;
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.ProjectService;
import com.cmp.sid.CmpDict;
import com.cmp.sid.CmpWorkOrder;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
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
		
		
		//查询用户组任务
		//List<Task> userTaskList = activitiService.findGroupList(userr.getUSERNAME(), page.getCurrentPage()+1, page.getShowCount());
		//查询工作流中到个人的工单
		//com.cmp.util.Page<TaskBean> taskBeans = activitiService.getPersonalTaskList(userr.getUSERNAME(), page.getCurrentPage()+1, page.getShowCount());
		
		//根据用户类别查询不同的工单
		//申请者可查询自己申请的工单
		List<PageData> workOrderList = new ArrayList<PageData>();
		workOrderList = cmpWorkOrderService.listUserWorkorderByPd(page);
		
		
		
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
		String toCheckUrl = "";
		//是资源申请，跳资源申请审核页面或运维申请审核页面
		if (toViewWorkorder.getAppType()!= null && toViewWorkorder.getAppType().equals("1")) {
			toCheckUrl = "workorder/applyview";
		}else if (toViewWorkorder.getAppType()!= null && toViewWorkorder.getAppType().equals("2")) {
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
				updateParams.put("status", "4");  //进入运维执行状态
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
	
	
	public String getUserName(String userId) throws Exception {
		User userr = userService.getUserAndRoleById(userId);
		return userr.getNAME();
	}
	
	
	
}
