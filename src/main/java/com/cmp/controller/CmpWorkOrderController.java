package com.cmp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
//		pd.put("appType", pd.get("workorder_type"));
//		pd.put("status", pd.get("workorder_status"));
//		pd.put("projectCode", pd.get("project"));
//		pd.put("id", pd.get("workorder_id"));
//		pd.put("time", pd.get("workorder_time"));
//		pd.put("applyUserId", userr.getUSER_ID());
		page.setPd(pd);
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
