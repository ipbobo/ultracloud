package com.cmp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.activiti.service.ActivitiService;
import com.cmp.entity.DeployedSoft;
import com.cmp.entity.Medium;
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpOpServeService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.DeployedSoftService;
import com.cmp.service.MediumService;
import com.cmp.service.VirtualMachineService;
import com.cmp.sid.CmpDict;
import com.cmp.sid.CmpOpServe;
import com.cmp.sid.CmpWorkOrder;
import com.cmp.sid.VirtualMachine;
import com.cmp.util.PageDataUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;


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
	
	@Resource
	private DeployedSoftService deployedSoftService;
	
	@Resource
	private VirtualMachineService virtualMachineService;
	
	@Resource
	private MediumService mediumService;
	
	//运维服务申请表单查询
	@RequestMapping(value="/reqOperServicePre")
	public ModelAndView reqOperServicePre() throws Exception{
		ModelAndView mv = new ModelAndView();
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
		PageData pd = new PageData();
		pd.put("USERNAME", userr.getUSERNAME());
		List<CmpDict> serviceTypeList=cmpDictService.getCmpDictList("service_type");//服务类型列表查询
		//List<CmpDict> vmList=cmpDictService.getCmpDictList("plat_type");//虚拟机类型查询
		//List<CmpDict> middlewareList=cmpDictService.getCmpDictList("middleware");//中间件查询
		
		//查询用户拥有的虚拟机及虚拟机上部署的中间件
		List<VirtualMachine> vmList = virtualMachineService.findByUser(userr.getNAME());
//		Map<String , List<DeployedSoft>> deployedSoftMap = new HashMap<String , List<DeployedSoft>>();
//		for (VirtualMachine vm : vmList) {
//			List<DeployedSoft> softList = deployedSoftService.findByVmId(vm.getId());
//			deployedSoftMap.put(vm.getId(), softList);
//		}
//		mv.addObject("deployedSoftMap", deployedSoftMap);
		//查询所有的可安装软件
		Map<String, Medium> mediumMap = new HashMap<String, Medium>();
		List<PageData> mpdList = mediumService.listAll(new PageData());
		for (PageData spd : mpdList) {
			Medium medium = (Medium) PageDataUtil.mapToObject(spd, Medium.class);
			mediumMap.put(medium.getName(), medium);
		}
		mv.addObject("softName", mediumMap.keySet());
		mv.addObject("serviceTypeList", serviceTypeList);
		mv.addObject("vmList", vmList);
		mv.setViewName("operservice/req_oper_service");
		return mv;
	}
	
	//通过虚拟机ID 查询部署的软件
	@RequestMapping(value="/queryDeployedSoft")
	@ResponseBody
	public List<DeployedSoft> queryDeployedSoft(String vmId) throws Exception{
		if (vmId == null || vmId.length() == 0) {
			return null;
		}
		List<DeployedSoft> softList = deployedSoftService.findByVmId(vmId);
		return softList;
	}
	
	//通过虚拟机ID 查询部署的软件
		@RequestMapping(value="/querySoftVersion")
		@ResponseBody
	public List<String> querySoftVersion(String softName) throws Exception{
			if (softName == null || softName.length() == 0) {
				return null;
			}
			List<String> softVersions = new ArrayList<String>();
			List<PageData> mpdList = mediumService.listAll(new PageData());
			for (PageData spd : mpdList) {
				Medium medium = (Medium) PageDataUtil.mapToObject(spd, Medium.class);
				if (medium.getName() != null && medium.getName().equals(softName)) {
					softVersions.add(medium.getVersion());
				}
			}
			return softVersions;
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
	@ResponseBody
	public Object subOperService() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String resultInfo = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String serviceType = pd.getString("service_type");
		if (serviceType == null || serviceType.length() == 0) {
			resultInfo = "服务类型不正确";
			map.put("result", resultInfo);
			return map;
		}
		String operType = pd.getString("oper_type");
		if (operType == null || operType.length() == 0) {
			resultInfo = "操作类型不正确";
			map.put("result", resultInfo);
			return map;
		}
		String appmsg = pd.getString("app_msg"); //申请说明
		String vm = pd.getString("vm");
		String vmMsg = pd.getString("vm_msg");
		String install_soft = pd.getString("install_soft");
		String soft_version = pd.getString("soft_version");
		String breakdown_time = pd.getString("breakdown_time");
		String except_solve_time = pd.getString("except_solve_time");
		String except_result = pd.getString("except_result");
		String breakdown_level = pd.getString("breakdown_level");
		String breakdown_info = pd.getString("breakdown_info");
		
		CmpOpServe opServe = new CmpOpServe();
		if (serviceType.equals("1")) {
			//虚拟机启停
			opServe.setAppmsg(appmsg);
			opServe.setMiddleware("");
			opServe.setMiddlewareMsg("");
			opServe.setOperType(operType);
			opServe.setServiceType(serviceType);
			opServe.setVm(vm);		//启停的而虚拟机及软件   软件1，软件2|软件1,软件2|
			opServe.setVmMsg(vmMsg); //虚拟机启停说明
			opServe.setWorkflow("oper_workflow");
			cmpOpServeService.saveCmpOpServe(opServe);
		}else if (serviceType.equals("2")) {
			String[] vmIds = vm.split(",");
			String deploySoftIds = "";
			for (String vmId: vmIds) {
				if (vmId == null) {
					continue;
				}
				VirtualMachine virtualMachine = virtualMachineService.findById(vmId);
				DeployedSoft deployedSoft = new DeployedSoft();
				deployedSoft.setVirtualmachineName(virtualMachine.getName());
				deployedSoft.setVirtualmachineId(virtualMachine.getId());
				deployedSoft.setSoftName(install_soft);
				deployedSoft.setSoftVersion(soft_version);
				deployedSoft.setStatus("未安装");
				deployedSoftService.add(deployedSoft);
				if (deploySoftIds.length() > 0) {
					deploySoftIds = deploySoftIds + "," + deployedSoft.getId();
				}else {
					deploySoftIds = deployedSoft.getId();
				}
			}
			opServe.setAppmsg(appmsg);
			opServe.setMiddleware("");
			opServe.setMiddlewareMsg("");
			opServe.setOperType(operType);
			opServe.setServiceType(serviceType);
			opServe.setVm(vm);		//虚拟机列表  虚拟机1，虚拟机2
			opServe.setWorkflow("oper_workflow");
			opServe.setDeploySoftId(deploySoftIds);
			cmpOpServeService.saveCmpOpServe(opServe);
		}else if (serviceType.equals("3")) {
			opServe.setAppmsg(appmsg);
			opServe.setMiddleware("");
			opServe.setMiddlewareMsg("");
			opServe.setOperType(operType);
			opServe.setServiceType(serviceType);
			opServe.setVm(vm);		//虚拟机列表  虚拟机1，虚拟机2
			opServe.setWorkflow("oper_workflow");
			opServe.setBreakdownTime(breakdown_time);
			opServe.setBreakdownInfo(breakdown_info);
			opServe.setBreakdownLevel(breakdown_level);
			opServe.setExceptSolveTime(except_solve_time);
			opServe.setExceptResult(except_result);
			cmpOpServeService.saveCmpOpServe(opServe);
		}
		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
		CmpWorkOrder workworder = new CmpWorkOrder();
		workworder.setOrderNo(String.valueOf(opServe.getId() == null ? "" : opServe.getId()));
		workworder.setAppType("2"); //运维服务申请
		workworder.setStatus("0");  //工作流初始状态
		workworder.setApplyUserId(user.getUSERNAME());
		cmpWorkOrderService.addWordOrder(workworder);
		
		//启动工作流
		String procDefKey = opServe.getWorkflow();
		String procInstId = activitiService.start(procDefKey, user.getUSERNAME(), workworder.getAppNo(), null);
		
		//拾取任务并完成
		List<Task> userTaskList = activitiService.findGroupList(user.getUSERNAME(), 1, 100);
		for (Task task : userTaskList) {
			if (task.getProcessInstanceId().equals(procInstId)) {
				activitiService.claimTask(task.getId(), user.getUSERNAME());
				//写入流程注释
				activitiService.addComment(task.getId(), procInstId, user.getUSERNAME(), appmsg);
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
		
		resultInfo = "申请成功!";
		map.put("result", resultInfo);
		return map;
	}
	
}
