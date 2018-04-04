package com.cmp.workorder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.DeployedSoft;
import com.cmp.entity.Medium;
import com.cmp.entity.Project;
import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.mgr.CloudArchManager;
import com.cmp.mgr.CloudArchManagerAdapter;
import com.cmp.mgr.bean.CloneVmRequest;
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpOrderService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.DeployedSoftService;
import com.cmp.service.MediumService;
import com.cmp.service.ProjectService;
import com.cmp.service.ScriptParamService;
import com.cmp.service.ScriptService;
import com.cmp.service.VirtualMachineService;
import com.cmp.service.autodeploy.AutoDeployConfigService;
import com.cmp.service.resourcemgt.CloudplatformService;
import com.cmp.service.resourcemgt.DatacenterService;
import com.cmp.service.resourcemgt.HostmachineService;
import com.cmp.service.servicemgt.MirrorService;
import com.cmp.sid.AutoDeployNode;
import com.cmp.sid.AutoDeployScriptNode;
import com.cmp.sid.CloudInfoCollect;
import com.cmp.sid.CmpCloudInfo;
import com.cmp.sid.CmpOrder;
import com.cmp.sid.CmpWorkOrder;
import com.cmp.sid.DiskInfo;
import com.cmp.sid.VirtualMachine;
import com.cmp.util.PageDataUtil;
import com.cmp.util.ShellUtil;
import com.cmp.util.StringUtil;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.util.Const;
import com.fh.util.Logger;
import com.fh.util.PageData;

@Service("appWorkorderHandler")
public class AppWorkorderHandler implements IWorkorderHandler {

	private static Logger logger = Logger.getLogger(AppWorkorderHandler.class);
	
	@Resource
	private CmpWorkOrderService cmpWorkOrderService;
	
	@Resource
	private CmpOrderService cmpOrderService;
	
	@Resource 
	private CmpDictService cmpDictService;
	
	@Resource 
	private ProjectService ProjectService;
	
	@Resource
	private VirtualMachineService virtualMachineService;
	
	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private DeployedSoftService deployedSoftService;
	
	@Resource
	private MediumService mediumService;
	
	@Resource
	private CloudArchManagerAdapter cloudArchManagerAdapter;
	
	@Resource
	private CloudplatformService cloudplatformService;
	
	@Resource
	private DatacenterService datacenterService;
	
	@Resource
	private AutoDeployConfigService autoDeployConfigService;
	
	
	@Resource
	private ScriptParamService scriptParamService;
	
	@Resource
	private ScriptService scriptService;
	
	@Resource
	private MirrorService mirrorService;
	
	
	@Override
	public Map<String, Object> toWorkorderView(CmpWorkOrder cmpWorkorder) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap = buildViewInfo(cmpWorkorder, resMap);
		resMap.put("toPageUrl", "workorder/applyview");
		return resMap;
	}
	
	@Override
	public Map<String, Object> toWorkorderCheck(CmpWorkOrder cmpWorkorder) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap = buildViewInfo(cmpWorkorder, resMap);
		resMap.put("toPageUrl", "workorder/applycheck");
		return resMap;
	}

	@Override
	public Map<String, Object> toWorkorderExecute(CmpWorkOrder cmpWorkorder) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap = buildViewInfo(cmpWorkorder, resMap);
		//获取云平台信息
		List<PageData> cloudplatformList =  cloudplatformService.list(new Page(), false);
		resMap.put("cloudplatformList", cloudplatformList);
		resMap.put("toPageUrl", "workorder/applyexecute");
		return resMap;
	}
	
	@Override
	public Map<String, Object> toWorkorderVerify(CmpWorkOrder cmpWorkorder) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap = buildViewInfo(cmpWorkorder, resMap);
		resMap.put("toPageUrl", "workorder/applyverify");
		return resMap;
	}
	
	public Map<String, Object> buildViewInfo(CmpWorkOrder cmpWorkorder, Map<String, Object> resMap) throws Exception {
		//工单信息
		CmpOrder orderInfo = null;
		List<CmpOrder> orderList = null;
		try {
			orderList = cmpOrderService.getOrderDtl(cmpWorkorder.getOrderNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (orderList == null || orderList.size() == 0) {
			return null;
		}
		orderInfo = orderList.get(0);
		resMap.put("orderInfo", orderInfo);
		resMap.put("workorder", cmpWorkorder);
		//项目信息
		PageData p_pd = new PageData();
		p_pd.put("id", cmpWorkorder.getProjectCode());
		p_pd = ProjectService.findById(p_pd);
		Project project = (Project) PageDataUtil.mapToObject(p_pd, Project.class);
		resMap.put("project", project);
		
		//部门信息
		String deptId = project.getDEPARTMENT_ID();
		if (deptId == null) {
			return null;
		}
		PageData d_pd = new PageData();
		d_pd.put("DEPARTMENT_ID", deptId);
		d_pd = departmentService.findById(d_pd);
		Department dept = (Department) PageDataUtil.mapToObject(d_pd, Department.class);
		resMap.put("department", dept);
		
		//虚拟机汇总信息
		if (cmpWorkorder.getVirNum() != null) {
			CloudInfoCollect cloudInfoCollect = new CloudInfoCollect();
			int vmNum = Integer.parseInt(orderInfo.getVirNum());
			cloudInfoCollect.setVmCount(vmNum);
			cloudInfoCollect.setCpuTotal(orderInfo.getCpu() == null ? 
					0 : vmNum*Integer.parseInt(StringUtil.getInteger(cmpDictService.getCmpDict("cpu", orderInfo.getCpu()).getDictValue())));
			cloudInfoCollect.setMemoryTotal(orderInfo.getMemory() == null ? 
					0 : Integer.parseInt( orderInfo.getMemory()));
			if (orderInfo.getDiskSize() == null) {
				cloudInfoCollect.setDiskTotal(0);
			}else {
				int diskTotal = 0;
				String[] disks = orderInfo.getDiskSize().split(",");
				for (String oneDiskSize : disks) {
					diskTotal += Integer.parseInt(oneDiskSize);
				}
				cloudInfoCollect.setDiskTotal(diskTotal);
			}
			resMap.put("cloudInfoCollect", cloudInfoCollect);
		}
		//虚拟机信息
		CmpCloudInfo cmpCloudInfo = getCmpCloudInfo(cmpWorkorder, orderInfo);
		resMap.put("cmpCloudInfo", cmpCloudInfo);
		return resMap;
	}

	
	
	
	public CmpCloudInfo getCmpCloudInfo(CmpWorkOrder cmpWorkorder, CmpOrder orderInfo) throws Exception {
		CmpCloudInfo cloudInfo = new CmpCloudInfo();
		cloudInfo.setOsTypeName(cmpDictService.getCmpDict("os_type", orderInfo.getOsType()).getDictValue());
		
		
		if (orderInfo.getDiskSize() == null) {
			cloudInfo.setDataDiskInfo("");
		}else {
			StringBuffer deskInfo = new StringBuffer();
			String[] diskTypes = orderInfo.getDiskType().split(",");
			String[] disks = orderInfo.getDiskSize().split(",");
			int index = 0;
			for (String oneDiskSize : disks) {
				deskInfo.append(cmpDictService.getCmpDict("disk_type", diskTypes[index]).getDictValue() + ":" +oneDiskSize+"G ");
				index++;
			}
			cloudInfo.setDataDiskInfo(deskInfo.toString());
		}
		cloudInfo.setSysDiskSize(CmpCloudInfo.SYS_DISK_SIZE);
		cloudInfo.setCpu(orderInfo.getCpu());
		cloudInfo.setMemory(orderInfo.getMemory());
		
		List<VirtualMachine> vmList = virtualMachineService.findByAppNo(cmpWorkorder.getAppNo());
		if (vmList == null || vmList.size() == 0) {
			cloudInfo.setSoftStatus("未安装");
			cloudInfo.setOsStatus("未安装");
		}else {
			VirtualMachine vm = vmList.get(0);
			cloudInfo.setSoftStatus(vm.getSoftStatus());
			cloudInfo.setOsStatus(vm.getOsStatus());
		}
		cloudInfo.setExpireDate(orderInfo.getExpireDate());
		//......................................................
		return cloudInfo;
	}

	//创建虚拟机
	public Map<String, Object> executeWork(PageData pd, CmpWorkOrder workOrder) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		//获取项目信息，修改项目中的资源使用额度
		PageData p_pd = new PageData();
		p_pd.put("id", workOrder.getProjectCode());
		p_pd = ProjectService.findById(p_pd);
		int pCpuUsed = (p_pd.get("cpu_used") == null? 0 : (Integer)p_pd.get("cpu_used")) + Integer.parseInt(pd.getString("CPU"));
		int pMemoryUsed = (p_pd.get("memory_used") == null? 0 : (Integer)p_pd.get("memory_used")) + Integer.parseInt(pd.getString("memory"));
		int pDiskUsed = (p_pd.get("disk_used") == null? 0 : (Integer)p_pd.get("disk_used"))+ Integer.parseInt(pd.getString("diskSize"));
		p_pd.put("cpu_used", pCpuUsed);
		p_pd.put("memory_used", pMemoryUsed);
		p_pd.put("disk_used", pDiskUsed);
		
		Project project = (Project) PageDataUtil.mapToObject(p_pd, Project.class);
		String deptId = project.getDEPARTMENT_ID();
		if (deptId == null) {
			resMap.put("result", "执行异常! 工单对应的部门信息异常");
			return resMap;
		}
		ProjectService.editUsedQuota(p_pd);
		
		//获取部门信息，修改部门资源使用额度
		PageData d_pd = new PageData();
		d_pd.put("DEPARTMENT_ID", deptId);
		d_pd = departmentService.findById(d_pd);
		int dCpuUsed = (d_pd.get("cpu_used") == null ? 0: (Integer)d_pd.get("cpu_used"))+ Integer.parseInt(pd.getString("CPU"));
		int dMemoryUsed = (d_pd.get("memory_used") == null ? 0: (Integer)d_pd.get("memory_used")) + Integer.parseInt(pd.getString("memory"));
		int dDiskUsed = (d_pd.get("disk_used") == null ? 0: (Integer)d_pd.get("disk_used"))  + Integer.parseInt(pd.getString("diskSize"));
		d_pd.put("cpu_used", dCpuUsed);
		d_pd.put("memory_used", dMemoryUsed);
		d_pd.put("disk_used", dDiskUsed);
		departmentService.editUsedQuota(d_pd);
		
//		String autodeployid = pd.getString("auto_deploy_config_id");
//		PageData a_pd = new PageData();
//		a_pd.put("id", autodeployid);
//		LinkedList<AutoDeployNode> autoDeployNodeList = new LinkedList<AutoDeployNode>();
//		List<PageData> autoDeployList =  autoDeployConfigService.listAllInNodeById(a_pd);
//		for (PageData autoDeployItem : autoDeployList) {
//			
//			AutoDeployNode autoDeployNode = new AutoDeployNode();
//			autoDeployNode.setId(String.valueOf(autoDeployItem.get("id")));
//			autoDeployNode.setName(autoDeployItem.getString("name"));
//			autoDeployNode.setDetail(autoDeployItem.getString("detail"));
//			autoDeployNode.setOrderNum(String.valueOf(autoDeployItem.get("ordernum")));
//			autoDeployNode.setScriptId(autoDeployItem.getString("script_id"));
//			autoDeployNode.setConfigId(autodeployid);
//			LinkedList<AutoDeployScriptNode> scriptNodeList = new LinkedList<AutoDeployScriptNode>();
//			PageData s_pd = new PageData();
//			
//			String scriptId = autoDeployItem.getString("script_id");
//			if (scriptId != null && !"".equals(scriptId)){
//				s_pd.put("script_id", scriptId);
//				PageData scriptPd = scriptService.findMediumById(s_pd);
//				autoDeployNode.setScriptUrl(scriptPd.getString("url"));
//				autoDeployNode.setMediumId(String.valueOf(scriptPd.get("id")));
//				List<PageData> paramsList = scriptParamService.listAll(s_pd);
//				for (PageData paramPd : paramsList) {
//					AutoDeployScriptNode scriptNode = new AutoDeployScriptNode();
//					scriptNode.setId(String.valueOf(paramPd.get("id")));
//					scriptNode.setScriptId(String.valueOf(paramPd.get("script_id")));
//					scriptNode.setDefaultVal(paramPd.getString("value"));
//					scriptNode.setParamKey(paramPd.getString("param_key"));
//					scriptNode.setName(paramPd.getString("name"));
//					scriptNode.setNumber(paramPd.getString("number"));
//					scriptNode.setValue(pd.getString(paramPd.getString("param_key")));  //获取页面写入的参数值。
//					scriptNodeList.add(scriptNode);
//				}
//				scriptNodeList.sort(new Comparator<AutoDeployScriptNode>() {
//					public int compare(AutoDeployScriptNode o1, AutoDeployScriptNode o2) {
//						Integer n1 = Integer.parseInt(o1.getNumber());
//						Integer n2 = Integer.parseInt(o2.getNumber());
//						return n1.compareTo(n2);
//					}
//				});
//			}
//			String executeParam = "";
//			for (AutoDeployScriptNode scriptNode : scriptNodeList) {
//				executeParam = executeParam + scriptNode.getValue() + " ";
//			}
//			autoDeployNode.setExecuteParams(executeParam);
//			autoDeployNode.setScriptNodeList(scriptNodeList);
//			autoDeployNodeList.add(autoDeployNode);
//		}
//		autoDeployNodeList.sort(new Comparator<AutoDeployNode>() {
//			public int compare(AutoDeployNode o1, AutoDeployNode o2) {
//				Integer n1 = Integer.parseInt(o1.getOrderNum());
//				Integer n2 = Integer.parseInt(o2.getOrderNum());
//				return n1.compareTo(n2);
//			}
//		});
		
		CmpOrder orderInfo = null;
		List<CmpOrder> orderList = null;
		try {
			orderList = cmpOrderService.getOrderDtl(workOrder.getOrderNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (orderList == null || orderList.size() == 0) {
			return null;
		}
		orderInfo = orderList.get(0);
		
		//部署虚拟机
		String DEF_USERNAME = "root";
		String DEF_PWD = "r00t0neio";
		int vmCount = Integer.parseInt(orderInfo.getVirNum());
		if (vmCount < 1) {
			resMap.put("ERROR", "部署的虚拟机数不正确");
			return resMap;
		}
		String ips = pd.getString("ip"); //页面传入IP列表，用","分隔
		if (ips == null || ips.length() == 0) {
			resMap.put("ERROR", "IP地址选择不正确");
			return resMap;
		}
		String[] ipArr = ips.split(",");
		for (int vmIndex = 0; vmIndex < vmCount; vmIndex++) {
			Map<String, Object> deployOut = deployVM(ipArr[vmIndex],DEF_USERNAME, DEF_PWD, project.getId(), project.getName(), workOrder, orderInfo, pd);
			if (deployOut.get("resultCode").equals("0")) {
				//部署虚拟机成功，部署软件
				String softCodeStr = orderInfo.getSoftCode();
				String[] softCodeArr =  softCodeStr.split(",");
				for (String softCode : softCodeArr) {
					StringBuffer shellParamBuf = new StringBuffer(); //shell脚本执行参数
					PageData m_pd = new PageData();
					m_pd.put("id", softCode);
					m_pd = mediumService.findById(m_pd);
					String softName = m_pd.getString("name");
					PageData script_pd = new PageData();
					script_pd.put("medium_id", softCode);
					List<PageData> defScriptParamList = scriptService.findDefParamsByMediumId(script_pd);
					defScriptParamList.sort(new Comparator<PageData>() {
								public int compare(PageData pdParam1, PageData pdParam2) {
									return new Long((long)pdParam1.get("number")).intValue() - new Long((long)pdParam2.get("number")).intValue();
								}
					});
					for (PageData defParam : defScriptParamList) {
						String paramKey = defParam.getString("param_key");
						if (pd.getString(paramKey) != null) {
							shellParamBuf.append(" " + pd.getString(paramKey));
						} else {
							shellParamBuf.append(" " + defParam.getString("value"));
						}
					}
					String shellUrl = scriptService.findByMediumId(script_pd).getString("url");//脚本路径
					String shellcmd = shellUrl + " " +shellParamBuf.toString().trim();  //执行脚本命令
					installSoft((VirtualMachine)deployOut.get("vm"), workOrder, softCode, softName, shellcmd);
				}
			}
		}
		
		
		//所有安装完毕设置结束标志
		Map<String , String> exeParams = new HashMap<String , String>();
		exeParams.put("executeStatus", "2");
		cmpWorkOrderService.updateExecuteStatus(workOrder.getAppNo(), exeParams);
		ShellUtil.addMsgLog(workOrder.getAppNo(), "cmp:success");
		return resMap;
	}

	//部署虚拟机
	public Map<String, Object> deployVM(String ip, String loginName, String loginPwd,  String projectId, String projectName, CmpWorkOrder workOrder, CmpOrder orderInfo, PageData pd) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		VirtualMachine vm = new VirtualMachine();
		//添加虚拟机
		int currentVMNum = virtualMachineService.countByProject(projectId);
		String vmName = projectName + "_" + (currentVMNum + 1);//虚拟机的名字为当前项目名称+项目拥有的虚拟机总数+1
		String osName = cmpDictService.getCmpDict("os_type", orderInfo.getOsType()).getDictValue();
		String osBitNum = cmpDictService.getCmpDict("os_bit_num", orderInfo.getOsBitNum()).getDictValue();
		String fullOS = osName +"_" +osBitNum;
		String installOS = cmpDictService.getCmpDict("install_OS", fullOS).getDictValue();  //安装的系统软件
		String diskSize = orderInfo.getDiskSize();
		String diskType = orderInfo.getDiskType();
		String[] diskTypeList = diskType.split(",");
		String[] diskSizeList = diskSize.split(",");
		List<DiskInfo> diskInfoList = new ArrayList<DiskInfo>();
		for (int i = 0; i < diskSizeList.length; i++) {
			DiskInfo di = new DiskInfo();
			di.setDiskSize(diskSizeList[i]);
			di.setDiskType(diskTypeList[i] != null? cmpDictService.getCmpDict("disk_type", diskTypeList[i]).getDictValue() : "");
			diskInfoList.add(di);
		}
		//安装虚拟机
		ShellUtil.addMsgLog(workOrder.getAppNo(), "准备安装虚拟机: " + vmName);
		String cloudPlatformId = pd.getString("cloudPlatformId");
		String datacenterId = pd.getString("datacenterId");
		String clusterId = pd.getString("clusterId");
		//判断实施平台ID和申请平台ID是否一致
		if (cloudPlatformId == null || !cloudPlatformId.equals(orderInfo.getPlatType())) {
			resMap.put("resultCode", "-1");
			resMap.put("resultMsg", "执行异常!平台类型和申请的不一致 ");
			ShellUtil.addMsgLog(workOrder.getAppNo(), "虚拟机:[" + ip + "] 执行异常!平台类型和申请的不一致");
			return resMap;
		}
		
		//查询cloudplatform
		PageData c_pd = new PageData();
		c_pd.put("id", cloudPlatformId);
		PageData cloudplatformPd = cloudplatformService.findById(c_pd, false);
		TccCloudPlatform platForm = new TccCloudPlatform();
		platForm.setCloudplatformUser(cloudplatformPd.getString("username"));
		platForm.setCloudplatformPassword(cloudplatformPd.getString("password"));
		platForm.setCloudplatformIp(cloudplatformPd.getString("ip"));
		String platFormType = cloudplatformPd.getString("type");
		String platformManagerType = "";
		if (platFormType != null && platFormType.equals("vmware")) {
			platformManagerType="com.cmp.mgr.vmware.VMWareCloudArchManager";
		}else if (platFormType != null && platFormType.equals("openstack")) {
			platformManagerType="com.cmp.mgr.openstack.OpenstatckCloudArchManager";
		}else if (platFormType != null && platFormType.equals("kvm")) {
			platformManagerType="com.cmp.mgr.KvmCloudArchManager";
		}else {
			resMap.put("resultCode", "-1");
			resMap.put("resultMsg", "执行异常!不支持的平台类型 ");
			ShellUtil.addMsgLog(workOrder.getAppNo(), "虚拟机:[" + ip + "] 执行异常!不支持的平台类型");
			return resMap;
		}
		
		PageData datacenterPd = new PageData();
		datacenterPd.put("id", datacenterId);
		datacenterPd = datacenterService.findById(datacenterPd);
		
		//查询镜像模板
		String imageTpId = orderInfo.getImgCode();
		PageData  imagePd = new PageData();
		imagePd.put("id", imageTpId);
		imagePd = mirrorService.findTemplateById(imagePd);
		platForm.setPlatformManagerType(platformManagerType);
		CloudArchManager cloudArchManager = cloudArchManagerAdapter.getCloudArchManagerAdaptee(platForm);
		CloneVmRequest cloneVmRequest = new CloneVmRequest();
		cloneVmRequest.setCpuSize(Integer.parseInt(pd.getString("CPU")));
		cloneVmRequest.setRamSize(Long.parseLong(pd.getString("memory"))*1024);
		cloneVmRequest.setVmName(vmName);
		cloneVmRequest.setDcName(datacenterPd.getString("name"));
		cloneVmRequest.setTplName(imagePd.getString("name"));
		cloneVmRequest.setIp(ip);
		String hostMachineUUID = cloudArchManager.cloneVirtualMachine(cloneVmRequest);
		
		//TccVirtualMachine  vmInst = cloudArchManager.getVirtualMachineByName(vmName);
		//String vmIp = vmInst.getIpAddress();
		String softCode = orderInfo.getSoftCode();
		vm.setCpu(pd.getString("CPU"));
		vm.setMemory(pd.getString("memory"));
		vm.setDatadisk(diskInfoList.size() > 0 ? diskInfoList.get(0).getDiskSize() : "300");
		vm.setProjectId(projectId);
		vm.setOs(fullOS);
		vm.setOsStatus("未安装");
		vm.setMountDiskSize(diskSize);
		vm.setMountDiskType(diskType);
		vm.setSoft(softCode);
		vm.setSoftStatus("未安装");
		vm.setName(vmName);
		vm.setIp(ip);
		vm.setUsername(loginName);
		vm.setPassword(loginPwd);
		vm.setStatus("0");
		vm.setType(platFormType);
		vm.setPlatform(cloudPlatformId);
		vm.setHostmachineId(hostMachineUUID);
		vm.setAppNo(workOrder.getAppNo());
		vm.setUser(workOrder.getApplyUserId());
		vm.setDuedate(workOrder.getExpireDate());
		virtualMachineService.add(vm);
		//所有安装完毕设置结束标志
		logger.info("远程虚拟机创建完毕");
		ShellUtil.addMsgLog(workOrder.getAppNo(), "虚拟机:[" + ip + "]虚拟机安装完毕!");
		ShellUtil.addMsgLog(workOrder.getAppNo(), "虚拟机:[" + ip + "]准备安装相关软件!");
		resMap.put("resultCode", "0");
		resMap.put("vm", vm);
		resMap.put("resultMsg", "虚拟机安装执行成功! ");
		return resMap;
	}

	public Map installSoft(VirtualMachine vm, CmpWorkOrder workOrder, String mediumId, String softName, String shellcmd) throws Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
		//执行软件安装脚本
		ShellUtil shellUtil = new ShellUtil(vm.getIp(), ShellUtil.DEF_PORT, vm.getUsername(),  
				vm.getPassword() , ShellUtil.DEF_CHARSET);
		shellUtil.exec("./" + shellcmd, workOrder.getAppNo());
		
		//添加虚拟机中间件
		PageData m_pd = new PageData();
		m_pd.put("id", mediumId);
		m_pd = mediumService.findById(m_pd);
		Medium medium = (Medium) PageDataUtil.mapToObject(m_pd, Medium.class);
		DeployedSoft deployedSoft = new DeployedSoft();
		deployedSoft.setVirtualmachineId(String.valueOf(vm.getId()));
		deployedSoft.setSoftName(medium.getName());
		deployedSoft.setStatus("未部署");
		deployedSoft.setSoftType(medium.getType());
		deployedSoft.setSoftVersion(medium.getVersion());
		deployedSoft.setVirtualmachineName(String.valueOf(vm.getName()));
		deployedSoftService.add(deployedSoft);
			
		ShellUtil.addMsgLog(workOrder.getAppNo(), "虚拟机[" + vm.getIp() + "] 软件 [" + softName + "] 部署完毕!");
		resMap.put("resultCode", "0");
		resMap.put("resultMsg", "虚拟机软件 [" + softName +" ]安装执行成功! ");
		return resMap;
	}
}
