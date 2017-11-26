package com.cmp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CmpDictService;
import com.cmp.service.CmpOpServeService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.sid.CmpDict;
import com.cmp.sid.CmpOpServe;
import com.cmp.sid.CmpWorkOrder;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
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
	private CmpOpServeService CmpOpServeService;
	
	@Resource
	private CmpWorkOrderService  cmpWorkOrderService;
	
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
		CmpOpServeService.saveCmpOpServe(opServe);
		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
		CmpWorkOrder workworder = new CmpWorkOrder();
		workworder.setAppNo(String.valueOf(opServe.getId() == null ? "" : opServe.getId()));
		workworder.setAppType("2"); //暂定。运维服务申请
		workworder.setStatus("0");
		workworder.setApplyUserId(String.valueOf(user.getUSER_ID()));
		cmpWorkOrderService.addWordOrder(workworder);
		
		mv.addObject("retMsg","递交成功!");
		return mv;
	}
	
}
