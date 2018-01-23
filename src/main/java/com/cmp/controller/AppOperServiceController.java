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
import com.cmp.entity.CmpResultInfo;
import com.cmp.entity.DeployedSoft;
import com.cmp.entity.Medium;
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpLogService;
import com.cmp.service.CmpOpServeService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.DeployedSoftService;
import com.cmp.service.MediumService;
import com.cmp.service.ProjectService;
import com.cmp.service.ScriptParamService;
import com.cmp.service.ScriptService;
import com.cmp.service.VirtualMachineService;
import com.cmp.sid.CmpDict;
import com.cmp.sid.CmpOpServe;
import com.cmp.sid.CmpWorkOrder;
import com.cmp.sid.VirtualMachine;
import com.cmp.util.PageDataUtil;
import com.cmp.util.ShellUtil;
import com.cmp.util.StringUtil;
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
	
	@Resource
	private ScriptParamService scriptParamService;
	
	@Resource
	private ScriptService scriptService;
	
	@Resource
	private ProjectService projectService;
	
	@Resource
	private CmpLogService cmpLogService;
	
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
		pd = this.getPageData();
		pd.put("USERNAME", userr.getUSERNAME());
		List<CmpDict> serviceTypeList=cmpDictService.getCmpDictList("service_type");//服务类型列表查询
		//List<CmpDict> vmList=cmpDictService.getCmpDictList("plat_type");//虚拟机类型查询
		//List<CmpDict> middlewareList=cmpDictService.getCmpDictList("middleware");//中间件查询
		
		//查询用户拥有的虚拟机及虚拟机上部署的中间件
		List<VirtualMachine> vmList = virtualMachineService.findByUser(userr.getNAME());
		//生成虚拟机对应的项目列表，用于条件查询
		Map<String, String> projectNameMap = new HashMap<String, String>();
		for (VirtualMachine onevm : vmList) {
			if (onevm.getProjectId() == null || "".equals(onevm.getProjectId())) {
				continue;
			}
			projectNameMap.put(onevm.getProjectId(), onevm.getProjectId());
		}
		projectNameMap.forEach((k,v)->{  
			try {
			PageData p_pd = new PageData();
			p_pd.put("id", k);
			p_pd = projectService.findById(p_pd);
			projectNameMap.put(k, (String)p_pd.get("name"));
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
        });  
		Map<String, Medium> mediumMap = new HashMap<String, Medium>();
		List<PageData> mpdList = mediumService.listAll(new PageData());
		for (PageData spd : mpdList) {
			Medium medium = (Medium) PageDataUtil.mapToObject(spd, Medium.class);
			mediumMap.put(medium.getName(), medium);
		}
		//特殊过滤
		String serviceType = "";
		String vmId = "";
		if (pd.get("serviceType") != null ) {
			serviceType = String.valueOf(pd.get("serviceType"));
			List<CmpDict> newServiceTypeList = new ArrayList<CmpDict>();
			for (CmpDict cd : serviceTypeList) {
				if (cd.getDictCode().equals(serviceType)) {
					newServiceTypeList.add(cd);
				}
			}
			serviceTypeList = newServiceTypeList;
		}
		if (pd.get("vmId") != null ) {
			vmId = String.valueOf(pd.get("vmId"));
			List<VirtualMachine> newVmList = new ArrayList<VirtualMachine>();
			for (VirtualMachine vm : vmList) {
				if (vm.getId().equals(vmId)) {
					newVmList.add(vm);
				}
			}
			vmList = newVmList;
		}
		mv.addObject("projectNameMap", projectNameMap);
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
		String vm = pd.getString("vm");
		if (vm == null || vm.length() == 0) {
			resultInfo = "虚拟机选择不正确";
			map.put("result", resultInfo);
			return map;
		}
		String appmsg = pd.getString("app_msg"); //申请说明
		
		String vmMsg = pd.getString("vm_msg");
		String install_soft = pd.getString("install_soft");
		String soft_version = pd.getString("soft_version");
		String breakdown_time = pd.getString("breakdown_time");
		String except_solve_time = pd.getString("except_solve_time");
		String except_result = pd.getString("except_result");
		String breakdown_level = pd.getString("breakdown_level");
		String breakdown_info = pd.getString("breakdown_info");
		String partition_info = pd.getString("partition_info");
		String directory = pd.getString("directory");
		String directory2 = pd.getString("directory2");
		String exp_time = pd.getString("exp_time");
		String directory3 = pd.getString("directory3");
		String vip_num = pd.getString("vip_num");
		String rootpwd = pd.getString("rootpwd");
		String exp_time_pwd = pd.getString("exp_time_pwd");
		String remark1 =  pd.getString("remark1");
		String file_name = pd.getString("file_name");
		CmpOpServe opServe = new CmpOpServe();
		
		//获取申请工单虚拟机的项目信息
		String projectId = "";
		for (String vmId: vm.split(",")) {
			if (vmId == null) {
				continue;
			}
			VirtualMachine virtualMachine = virtualMachineService.findById(vmId);
			if (projectId.equals("")) {
				projectId = virtualMachine.getProjectId();
			} else {
				if (!projectId.equals(virtualMachine.getProjectId())) {
					resultInfo = "请选择同项目的虚拟机";
					map.put("result", resultInfo);
					return map;
				}
			}
		}
		if (serviceType.equals("1")) {
			//虚拟机启停
			opServe.setAppmsg(appmsg);
			opServe.setMiddleware("");
			opServe.setMiddlewareMsg("");
			opServe.setOperType(operType);
			opServe.setServiceType(serviceType);
			opServe.setVm(vm);		
			opServe.setRemark1(remark1);//启停的而虚拟机及软件   软件1，软件2|软件1,软件2|
			opServe.setVmMsg(vmMsg); //虚拟机启停说明
			opServe.setWorkflow(CmpOpServe.OP_MIDDLEWATE_RESTART);
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
			opServe.setWorkflow(CmpOpServe.OP_SOFTWARE_INSTALL);
			opServe.setDeploySoftId(deploySoftIds);
		}else if (serviceType.equals("3")) {
			opServe.setAppmsg(appmsg);
			opServe.setMiddleware("");
			opServe.setMiddlewareMsg("");
			opServe.setOperType(operType);
			opServe.setServiceType(serviceType);
			opServe.setVm(vm);		//虚拟机列表  虚拟机1，虚拟机2
			opServe.setWorkflow(CmpOpServe.OP_FAULT_HANDLE);
			opServe.setBreakdownTime(breakdown_time);
			opServe.setBreakdownInfo(breakdown_info);
			opServe.setBreakdownLevel(breakdown_level);
			opServe.setExceptSolveTime(except_solve_time);
			opServe.setExceptResult(except_result);
		}else if (serviceType.equals("4")) {
			opServe.setAppmsg(appmsg);
			opServe.setMiddleware("");
			opServe.setMiddlewareMsg("");
			opServe.setOperType(operType);
			opServe.setServiceType(serviceType);
			opServe.setVm(vm);		//虚拟机列表  虚拟机1，虚拟机2
			opServe.setWorkflow(CmpOpServe.OP_SYSTEM_PARTITION);
			opServe.setPartitionInfo(partition_info);
		}else if (serviceType.equals("5")) {
			opServe.setAppmsg(appmsg);
			opServe.setMiddleware("");
			opServe.setMiddlewareMsg("");
			opServe.setOperType(operType);
			opServe.setServiceType(serviceType);
			opServe.setVm(vm);			//虚拟机列表  虚拟机1，虚拟机2
			opServe.setWorkflow(CmpOpServe.OP_CREATE_SYSTEMFILE);
		}else if (serviceType.equals("6")) {
			opServe.setAppmsg(appmsg);
			opServe.setMiddleware("");
			opServe.setMiddlewareMsg("");
			opServe.setOperType(operType);
			opServe.setServiceType(serviceType);
			opServe.setVm(vm);			//虚拟机列表  虚拟机1，虚拟机2
			opServe.setDirectory(directory);
			opServe.setWorkflow(CmpOpServe.OP_MOUNT_DISK);
		}else if (serviceType.equals("7")) {
			opServe.setAppmsg(appmsg);
			opServe.setMiddleware("");
			opServe.setMiddlewareMsg("");
			opServe.setOperType(operType);
			opServe.setServiceType(serviceType);
			opServe.setVm(vm);			//虚拟机列表  虚拟机1，虚拟机2
			opServe.setDirectory(directory2);
			opServe.setExpTime(exp_time_pwd);
			opServe.setWorkflow(CmpOpServe.OP_ROOT_APPLY);
			opServe.setRemark1(rootpwd);
		}else if (serviceType.equals("8")) {
			opServe.setAppmsg(appmsg);
			opServe.setMiddleware("");
			opServe.setMiddlewareMsg("");
			opServe.setOperType(operType);
			opServe.setServiceType(serviceType);
			opServe.setVm(vm);			//虚拟机列表  虚拟机1，虚拟机2
			opServe.setDirectory(directory3);
			opServe.setVipNum(vip_num);
			opServe.setWorkflow(CmpOpServe.OP_VIP_ADD);
		}
		cmpOpServeService.saveCmpOpServe(opServe);
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
		CmpWorkOrder workworder = new CmpWorkOrder();
		workworder.setOrderNo(String.valueOf(opServe.getId() == null ? "" : opServe.getId()));
		workworder.setAppType("2"); //运维服务申请
		workworder.setStatus("0");  //工作流初始状态
		workworder.setApplyUserId(user.getUSERNAME());
		workworder.setProjectCode(projectId);
		cmpWorkOrderService.addWordOrder(workworder);
		
		//启动工作流
		String procDefKey = opServe.getWorkflow();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("USERNAME", user.getUSERNAME());
		String procInstId = activitiService.start(procDefKey, user.getUSERNAME(), workworder.getAppNo(), variables);
		
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
		Map<String, Object> variablesMap = new HashMap<String, Object>();
		variablesMap.put("appNo", workworder.getAppNo());
		activitiService.handleTask(workworder.getAppNo(), procInstId,  user.getUSERNAME(), null, variablesMap);
		
		//更新工单(流程实例ID 和 工单状态)
		Map<String, String> updateParams = new HashMap<String, String>();
		updateParams.put("procInstId", procInstId);
		updateParams.put("status", "1");
		cmpWorkOrderService.updateWorkOrder(workworder.getAppNo(), updateParams);
		
		cmpLogService.addCmpLog("1", "申请运维服务", "申请运维服务成功", "0", StringUtil.getClientIp(getRequest()));
		resultInfo = "申请成功!";
		map.put("result", resultInfo);
		return map;
	}
	
	
	/**
	 * 查询脚本参数
	 * @param serviceType
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryScriptParams")
	@ResponseBody
	public List<PageData> queryScriptParams(String scriptId) throws Exception{
		if (scriptId == null || scriptId.length() == 0) {
			return null;
		}
		PageData pd = new PageData();
		pd.put("script_id", scriptId);
		List<PageData> params =  scriptParamService.listAll(pd);;
		return params;
	}
	
	
	/**
	 * 执行重启中间件脚本
	 * @param serviceType
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doRebootSoft")
	@ResponseBody
	public Object doRebootSoft(String appNo, String rebootSoftIds, String scriptId, String params) throws Exception{
		CmpResultInfo resultInfo = new CmpResultInfo();
		resultInfo.setResultCode(CmpResultInfo.ERROR);
		if (scriptId == null || scriptId.length() == 0) {
			resultInfo.setResultMsg("脚本未选择");
			return resultInfo;
		}
		if (rebootSoftIds == null || rebootSoftIds.length() == 0) {
			resultInfo.setResultMsg("重启的中间件未选择");
			return resultInfo;
		}
		if (appNo == null || appNo.length() == 0) {
			logger.error("doMountDisk : 工单不存在");
			resultInfo.setResultMsg("工单不存在");
			return resultInfo;
		}
		String[] rebootSoftIdList = rebootSoftIds.split(",");
		if (rebootSoftIdList == null || rebootSoftIdList.length == 0) {
			resultInfo.setResultMsg("待重启的中间件不存在 ");
			return resultInfo;
		}
		initExecuteShell(appNo);
		for (String rebootSoftId : rebootSoftIdList) {
			if (rebootSoftId == null || "".equals(rebootSoftId)) {
				continue;
			}
			DeployedSoft deployedSoft = deployedSoftService.findById(rebootSoftId);
			if (deployedSoft == null || deployedSoft.getVirtualmachineId() == null) {
				logger.error("doRebootSoft : 工单中的部署软件不存在");
				resultInfo.setResultMsg("工单中的部署软件不存在");
				return resultInfo;
			}
			String vmId = deployedSoft.getVirtualmachineId();
			PageData scriptPd = new PageData();
			scriptPd.put("id", scriptId);
			scriptPd = scriptService.findById(scriptPd);
			String scriptUrl = scriptPd.getString("url");
			String scriptName = scriptPd.getString("name");
			String cmds = "." + scriptUrl + scriptName + " " + params;
			
			int executeRet = 0;
			if ((executeRet = executeShell(vmId, cmds, appNo)) != 0) {
				logger.error("doRebootSoft : 中间件重启失败--" + executeRet);
				resultInfo.setResultMsg("中间件重启失败");
				return resultInfo;
			}
		}
		endExecuteShell(appNo);
		cmpLogService.addCmpLog("1", "执行中间件启停", "执行中间件启停成功", "0", StringUtil.getClientIp(getRequest()));
		resultInfo.setResultMsg("执行成功");
		resultInfo.setResultCode(CmpResultInfo.SUCCESS);
		return resultInfo;
	}
	
	/**
	 * 执行绑定磁盘
	 * @param serviceType
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doMountDisk")
	@ResponseBody
	public Object doMountDisk(String appNo, String netname, String manager_status, String bind_router) throws Exception{
		CmpResultInfo resultInfo = new CmpResultInfo();
		resultInfo.setResultCode(CmpResultInfo.ERROR);
		if (appNo == null || appNo.length() == 0) {
			resultInfo.setResultMsg("工单号不存在");
			return resultInfo;
		}
		if (netname == null || netname.length() == 0) {
			resultInfo.setResultMsg("网络名称未输入");
			return resultInfo;
		}
		CmpWorkOrder workorder = cmpWorkOrderService.findByAppNo(appNo);
		if (workorder == null) {
			logger.error("doMountDisk : 工单不存在");
			resultInfo.setResultMsg("工单不存在");
			return resultInfo;
		}
		initExecuteShell(appNo);
		//执行磁盘挂载
		endExecuteShell(appNo);
		cmpLogService.addCmpLog("1", "执行磁盘挂载", "执行磁盘挂载成功", "0", StringUtil.getClientIp(getRequest()));
		resultInfo.setResultMsg("执行成功");
		resultInfo.setResultCode(CmpResultInfo.SUCCESS);
		return resultInfo;
	}
	
	
	
	/**
	 * 执行软件安装脚本
	 * @param serviceType
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doInstallSoft")
	@ResponseBody
	public Object doInstallSoft(String appNo, String installSoftIds, String scriptId, String params) throws Exception{
		CmpResultInfo resultInfo = new CmpResultInfo();
		resultInfo.setResultCode(CmpResultInfo.ERROR);
		if (scriptId == null || scriptId.length() == 0) {
			resultInfo.setResultMsg("未选择脚本");
			return resultInfo;
		}
		if (installSoftIds == null || installSoftIds.length() == 0) {
			resultInfo.setResultMsg("待安装软件未选择");
			return resultInfo;
		}
		if (appNo == null || appNo.length() == 0) {
			resultInfo.setResultMsg("工单不存在");
			return resultInfo;
		}
		String[] installSoftIdList = installSoftIds.split(",");
		if (installSoftIdList == null || installSoftIdList.length == 0) {
			return null;
		}
		initExecuteShell(appNo);
		for (String installSoftId : installSoftIdList) {
			if (installSoftId == null || "".equals(installSoftId)) {
				continue;
			}
			DeployedSoft deployedSoft = deployedSoftService.findById(installSoftId);
			if (deployedSoft == null || deployedSoft.getVirtualmachineId() == null) {
				logger.error("doRebootSoft : 工单中的部署软件不存在");
				resultInfo.setResultMsg("工单中的部署软件不存在");
				return resultInfo;
			}
			String vmId = deployedSoft.getVirtualmachineId();
			
			
			PageData scriptPd = new PageData();
			scriptPd.put("id", scriptId);
			scriptPd = scriptService.findById(scriptPd);
			String scriptUrl = scriptPd.getString("url");
			String scriptName = scriptPd.getString("name");
			String cmds = "." + scriptUrl + scriptName + " " + params;
			initExecuteShell(appNo);
			int executeRet = 0;
			if ((executeRet = executeShell(vmId, cmds, appNo)) != 0) {
				logger.error("doInstallSoft : 软件安装失败--" + executeRet);
				resultInfo.setResultMsg("软件安装失败");
				return resultInfo;
			}
		}
		endExecuteShell(appNo);
		cmpLogService.addCmpLog("1", "执行软件安装", "执行软件安装成功", "0", StringUtil.getClientIp(getRequest()));
		resultInfo.setResultMsg("执行成功");
		resultInfo.setResultCode(CmpResultInfo.SUCCESS);
		return resultInfo;
	}
	
	
	public int  executeShell(String vmId, String cmds, String appNo) throws Exception {
		VirtualMachine vm = virtualMachineService.findById(vmId);
		if (vm == null) {
			logger.debug("doRebootSoft:虚拟机不存在");
			return -1;
		}
		String userName = vm.getUsername();
		String passwd = vm.getPassword();
		String ip = vm.getIp();
		//***************执行脚本
		ShellUtil shellUtil = new ShellUtil(ip, ShellUtil.DEF_PORT, userName,  
				passwd, ShellUtil.DEF_CHARSET);
		shellUtil.exec(cmds,  appNo);
		return 0;
	}
	
	
	public void initExecuteShell(String appNo) throws Exception {
		Map<String , String> exeParams = new HashMap<String , String>();
		exeParams.put("executeStatus", "1");
		cmpWorkOrderService.updateExecuteStatus(appNo, exeParams);
	}
	
	
	public void endExecuteShell(String appNo) throws Exception {
		Map<String , String> exeParams = new HashMap<String , String>();
		exeParams.put("executeStatus", "2");
		cmpWorkOrderService.updateExecuteStatus(appNo, exeParams);
		ShellUtil.executeFinished(appNo);
	}
	
}
