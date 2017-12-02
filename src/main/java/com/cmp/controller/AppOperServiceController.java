package com.cmp.controller;

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
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpOpServeService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.sid.CmpDict;
import com.cmp.sid.CmpOpServe;
import com.cmp.sid.CmpWorkOrder;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;


/**
 * 运维服务
 * @author Administrator
 *
 */
@Controller
public class AppOperServiceController  extends BaseController {
	@Resource
	private CmpDictService cmpDictService;
	
	@Resource
	private CmpOpServeService cmpOpServeService;
	
	@Resource
	private CmpWorkOrderService  cmpWorkOrderService;
	
	@Resource
	private ActivitiService activitiService;
	
	@Resource
	private UserService userService;
	
	//运维服务申请表单查询
	@RequestMapping(value="/reqOperServicePre")
	public ModelAndView reqOperServicePre() throws Exception{
		List<CmpDict> serviceTypeList=cmpDictService.getCmpDictList("service_type");//服务类型列表查询
		List<CmpDict> operTypeList=cmpDictService.getCmpDictList("oper_type");//操作类型查询
		List<CmpDict> vmList=cmpDictService.getCmpDictList("plat_type");//虚拟机类型查询
		List<CmpDict> middlewareList=cmpDictService.getCmpDictList("middleware");//中间件查询
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("serviceTypeList", serviceTypeList);
		mv.addObject("operTypeList", operTypeList);
		mv.addObject("vmList", vmList);
		mv.addObject("middlewareList", middlewareList);
		mv.setViewName("operservice/req_oper_service");
		return mv;
	}
	
	
	@RequestMapping(value="/onServiceTypeSelected")
	@ResponseBody
	public List<CmpDict> queryOpType(String serviceType){
		if (serviceType == null || serviceType.length() == 0) {
			return null;
		}
		List<CmpDict> opTypeList=cmpDictService.getCmpDictList("oper_type_" + serviceType);
		return opTypeList;
	}
	
	//运维服务申请递交处理
	@RequestMapping(value="/subOperService")
	public ModelAndView subOperService(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String serviceType = request.getParameter("service_type");
		String operType = request.getParameter("oper_type");
		String vm = request.getParameter("vm");
		String vmMsg = request.getParameter("vm_msg");
		String middleware = request.getParameter("middleware");
		String middlewareMsg = request.getParameter("middleware_msg");
		String appmsg = request.getParameter("app_msg"); //申请说明
		String errMsg = "";
		ModelAndView mv = new ModelAndView();
		mv.setViewName("operservice/req_oper_service");
		
		if (serviceType == null || serviceType.length() == 0) {
			errMsg = "服务类型不正确";
			mv.addObject("retMsg",errMsg);
			return mv;
		}
		if (operType == null || operType.length() == 0) {
			errMsg = "操作类型不正确";
			mv.addObject("retMsg",errMsg);
			return mv;
		}
		
		CmpOpServe opServe = new CmpOpServe();
		opServe.setAppmsg(appmsg);
		opServe.setMiddleware(middleware);
		opServe.setMiddlewareMsg(middlewareMsg);
		opServe.setOperType(operType);
		opServe.setServiceType(serviceType);
		opServe.setVm(vm);
		opServe.setVmMsg(vmMsg);
		cmpOpServeService.saveCmpOpServe(opServe);
		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
		CmpWorkOrder workworder = new CmpWorkOrder();
		workworder.setOrderNo(String.valueOf(opServe.getId() == null ? "" : opServe.getId()));
		workworder.setAppType("2"); //运维服务申请
		workworder.setStatus("0");  //工作流初始状态
		workworder.setApplyUserId(user.getUSERNAME());
		cmpWorkOrderService.addWordOrder(workworder);
		
		//启动工作流
		String procDefKey = "oper_workflow";
		String procInstId = activitiService.start(procDefKey, user.getUSERNAME(), workworder.getAppNo(), null);
		
		//拾取任务并完成
		List<Task> userTaskList = activitiService.findGroupList(user.getUSERNAME(), 1, 100);
		for (Task task : userTaskList) {
			if (task.getProcessInstanceId().equals(procInstId)) {
				activitiService.claimTask(task.getId(), user.getUSERNAME());
			}
		}
		//完成申请任务
		//List<User> userList = userService.listAllUserByRoldId(pd)
		activitiService.handleTask(workworder.getAppNo(), procInstId,  user.getUSERNAME(), null, null);
		
		//更新工单(流程实例ID 和 工单状态)
		Map<String, String> updateParams = new HashMap<String, String>();
		updateParams.put("procInstId", procInstId);
		updateParams.put("status", "1");
		cmpWorkOrderService.updateWorkOrder(workworder.getAppNo(), updateParams);
		
		mv.addObject("retMsg","递交成功!");
		return mv;
	}
	
}
