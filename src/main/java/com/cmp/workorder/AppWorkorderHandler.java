package com.cmp.workorder;

import com.cmp.entity.DeployedSoft;
import com.cmp.entity.HostMonitorInfo;
import com.cmp.entity.Medium;
import com.cmp.entity.Project;
import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.CloudArchManager;
import com.cmp.mgr.CloudArchManagerAdapter;
import com.cmp.mgr.bean.CloneVmRequest;
import com.cmp.mgr.bean.CloneVmResponse;
import com.cmp.service.*;
import com.cmp.service.autodeploy.AutoDeployConfigService;
import com.cmp.service.resourcemgt.CloudplatformService;
import com.cmp.service.resourcemgt.DatacenterService;
import com.cmp.service.resourcemgt.HostmachineService;
import com.cmp.service.servicemgt.AreaService;
import com.cmp.service.servicemgt.EnvironmentService;
import com.cmp.service.servicemgt.MirrorService;
import com.cmp.sid.*;
import com.cmp.util.PageDataUtil;
import com.cmp.util.ShellUtil;
import com.cmp.util.StringUtil;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.util.Logger;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

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

	@Resource
	private EnvironmentService environmentService;

	@Resource
	private AreaService areaService;

	@Resource
	private HostmachineService hostmachineService;
	
	@Resource
	private ZabbixMonitorService zabbixMonitorService;

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
		List<PageData> cloudplatformList = cloudplatformService.list(new Page(), false);
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
		List<CmpOrder> orderList = null;
		List<CmpCloudInfo> cmpCloudInfoList = new ArrayList<CmpCloudInfo>();
		CloudInfoCollect cloudInfoCollect = new CloudInfoCollect();
		try {
			orderList = cmpOrderService.getOrderDtlByAppNo(cmpWorkorder.getAppNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (orderList == null || orderList.size() == 0) {
			return null;
		}
		resMap.put("orderList", orderList);
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

		for (CmpOrder orderInfo : orderList) {
			//虚拟机汇总信息
			if (orderInfo.getVirNum() != null) {
				int vmNum = Integer.parseInt(orderInfo.getVirNum());
				cloudInfoCollect.setVmCount(cloudInfoCollect.getVmCount() + vmNum);
				cloudInfoCollect.setCpuTotal(cloudInfoCollect.getCpuTotal() + orderInfo.getCpu() == null ?
						0 : vmNum * Integer.parseInt(StringUtil.getInteger(cmpDictService.getCmpDict("cpu", orderInfo.getCpu()).getDictValue())));
				cloudInfoCollect.setMemoryTotal(cloudInfoCollect.getMemoryTotal() + orderInfo.getMemory() == null ?
						0 : Integer.parseInt(orderInfo.getMemory()));
				if (orderInfo.getDiskSize() == null) {
					cloudInfoCollect.setDiskTotal(cloudInfoCollect.getDiskTotal() + 0);
				} else {
					int diskTotal = 0;
					String[] disks = orderInfo.getDiskSize().split(",");
					for (String oneDiskSize : disks) {
						diskTotal += Integer.parseInt(oneDiskSize);
					}
					cloudInfoCollect.setDiskTotal(cloudInfoCollect.getDiskTotal() + diskTotal);
				}
			}
			//添加虚拟机信息
			CmpCloudInfo cmpCloudInfo = getCmpCloudInfo(cmpWorkorder, orderInfo);
			cmpCloudInfoList.add(cmpCloudInfo);
		}
		resMap.put("cloudInfoCollect", cloudInfoCollect);
		resMap.put("cmpCloudInfoList", cmpCloudInfoList);
		return resMap;
	}

	public CmpCloudInfo getCmpCloudInfo(CmpWorkOrder cmpWorkorder, CmpOrder orderInfo) throws Exception {
		CmpCloudInfo cloudInfo = new CmpCloudInfo();
		cloudInfo.setOsTypeName(cmpDictService.getCmpDict("os_type", orderInfo.getOsType()).getDictValue());

		if (orderInfo.getDiskSize() == null) {
			cloudInfo.setDataDiskInfo("");
		} else {
			StringBuffer deskInfo = new StringBuffer();
			String[] diskTypes = orderInfo.getDiskType().split(",");
			String[] disks = orderInfo.getDiskSize().split(",");
			int index = 0;
			for (String oneDiskSize : disks) {
				deskInfo.append(cmpDictService.getCmpDict("disk_type", diskTypes[index]).getDictValue() + ":" + oneDiskSize + "G ");
				index++;
			}
			cloudInfo.setDataDiskInfo(deskInfo.toString());
		}
		cloudInfo.setSysDiskSize(orderInfo.getDiskSize());
		cloudInfo.setCpu(orderInfo.getCpu());
		cloudInfo.setMemory(orderInfo.getMemory());

		List<VirtualMachine> vmList = virtualMachineService.findByAppNo(cmpWorkorder.getAppNo());
		if (vmList == null || vmList.size() == 0) {
			cloudInfo.setSoftStatus("未安装");
			cloudInfo.setOsStatus("未安装");
		} else {
			VirtualMachine vm = vmList.get(0);
			cloudInfo.setSoftStatus(vm.getSoftStatus());
			cloudInfo.setOsStatus(vm.getOsStatus());
		}
		cloudInfo.setExpireDate(orderInfo.getExpireDate());
		PageData env_pd = new PageData();
		env_pd.put("id", orderInfo.getEnvCode());
		env_pd = environmentService.findById(env_pd);
		cloudInfo.setEnvironment(env_pd.getString("name") == null ? "" : env_pd.getString("name"));

		PageData mirrorTp_pd = new PageData();
		mirrorTp_pd.put("id", orderInfo.getImgCode());
		mirrorTp_pd = mirrorService.findTemplateById(mirrorTp_pd);
		cloudInfo.setImgCodeName(mirrorTp_pd.getString("name") == null ? "" : mirrorTp_pd.getString("name"));

		cloudInfo.setResTypeName(cmpDictService.getCmpDict("res_type", orderInfo.getResType()).getDictValue());
		cloudInfo.setVmNum(orderInfo.getVirNum());
		cloudInfo.setAppNo(orderInfo.getAppNo());
		cloudInfo.setOrderNo(orderInfo.getOrderNo());

		PageData area_pd = new PageData();
		area_pd.put("id", orderInfo.getAreaCode());
		area_pd = areaService.findById(area_pd);
		cloudInfo.setAreaName(area_pd.getString("name") == null ? "" : area_pd.getString("name"));

		//项目信息
		PageData p_pd = new PageData();
		p_pd.put("id", orderInfo.getProjectCode());
		p_pd = ProjectService.findById(p_pd);
		Project project = (Project) PageDataUtil.mapToObject(p_pd, Project.class);
		cloudInfo.setProjectName(project.getName());

		String softNameStr = "";
		String softCodeStr = orderInfo.getSoftCode();
		if (softCodeStr != null && softCodeStr.length() != 0) {
			String[] softCodeArr = softCodeStr.split(",");
			for (String softCode : softCodeArr) {
				PageData m_pd = new PageData();
				m_pd.put("id", softCode);
				m_pd = mediumService.findById(m_pd);
				String softName = m_pd.getString("name");
				softNameStr = softNameStr + softName + "  ";
			}
		}

		cloudInfo.setSoft(softNameStr);
		cloudInfo.setApplicant(orderInfo.getApplyUserId());
		cloudInfo.setExecuteStatus(orderInfo.getExecuteStatus());
		//......................................................
		return cloudInfo;
	}

	//创建虚拟机
	public Map<String, Object> executeWork(PageData pd, CmpWorkOrder cmpWorkorder) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String orderNo = pd.getString("orderNo");
		if (orderNo == null) {
			return null;
		}
		CmpOrder orderInfo = null;
		List<CmpOrder> orderList = null;
		try {
			orderList = cmpOrderService.getOrderDtl(orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (orderList == null || orderList.size() == 0) {
			return null;
		}
		orderInfo = orderList.get(0);
		ShellUtil.addMsgLog(orderInfo.getOrderNo(), "准备安装虚拟机。");
		PageData i_pd = new PageData();
		i_pd.put("id", orderInfo.getProjectCode());
		i_pd = ProjectService.findById(i_pd);
		Project pj = (Project) PageDataUtil.mapToObject(i_pd, Project.class);
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

		//部署虚拟机
		String DEF_USERNAME = "root";
		String DEF_PWD = "password";
		int DEF_CONN_PORT = 22;
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
		if (ipArr.length != vmCount) {
			resMap.put("ERROR", "IP地址选择不正确");
			ShellUtil.addMsgLog(orderInfo.getOrderNo(), "虚拟机IP地址选择不正确，请选择" + vmCount + "个IP地址");
			ShellUtil.addMsgLog(orderInfo.getOrderNo(), "cmp:redo");
			return resMap;
		}
		for (int vmIndex = 0; vmIndex < vmCount; vmIndex++) {
			Map<String, Object> deployOut = deployVM(ipArr[vmIndex], DEF_USERNAME, DEF_PWD, pj.getId(), pj.getName(), orderInfo, pd);
			if (deployOut.get("resultCode").equals("0")) {
				//查询系统连接情况
				VirtualMachine vm = (VirtualMachine) deployOut.get("vm");
				boolean bPing = false;
				for (int index = 0; index < 20; index++) {
					ShellUtil.addMsgLog(orderInfo.getOrderNo(), "虚拟机启动中，尝试连接虚拟机...");
					if ((bPing = ShellUtil.ping2(vm.getIp(), DEF_CONN_PORT, ShellUtil.CONN_TIME_OUT))) {
						break;
					}
				}
				if (!bPing) {
					ShellUtil.addMsgLog(orderInfo.getOrderNo(), "虚拟机:[" + vm.getIp() + "] 连接异常, IP地址无法连接! 无法执行shell脚本安装软件。");
					continue;
				} else {
					ShellUtil.addMsgLog(orderInfo.getOrderNo(), "虚拟机:[" + vm.getIp() + "] 启动完成, 准备部署软件。");
				}

				//部署虚拟机成功，部署软件
				String softCodeStr = orderInfo.getSoftCode();
				String[] softCodeArr = softCodeStr.split(",");
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
							return new Long((long) pdParam1.get("number")).intValue() - new Long((long) pdParam2.get("number")).intValue();
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
					script_pd = scriptService.findByMediumId(script_pd);
					String remoteScriptUrl = script_pd.getString("url");//脚本路径
					String scriptId = String.valueOf(script_pd.get("id") == null ? "" : script_pd.get("id"));
					//下载脚本
					String localScriptUrl = ShellUtil.LOCAL_SCRIPT_DIR + "/" + scriptId + ".sh";
					int downRes = downloadScript(vm, remoteScriptUrl, localScriptUrl);
					if (downRes == -1) {
						ShellUtil.addMsgLog(orderInfo.getOrderNo(), "下载软件安装脚本异常，无法连接脚本服务器");
						continue;
					} else if (downRes == -2) {
						ShellUtil.addMsgLog(orderInfo.getOrderNo(), "下下载软件安装脚本异常，脚本保存出错(查看路径[" + localScriptUrl + "]是否存在)");
						continue;
					}
					String shellcmd = "." + localScriptUrl + " " + shellParamBuf.toString().trim();
					//执行软件安装脚本命令
					installSoft(vm, orderInfo, softCode, softName, shellcmd);
				}
			} else if (deployOut.get("resultCode").equals("-1")) {
				Map<String, String> exeParams = new HashMap<String, String>();
				exeParams.put("executeStatus", "3");
				ShellUtil.addMsgLog(orderInfo.getOrderNo(), "虚拟机执行部署脚本出错，部署异常。");
				ShellUtil.addMsgLog(orderInfo.getOrderNo(), "cmp:error");
				return resMap;
			}
		}

		//获取项目信息，修改项目中的资源使用额度
		PageData p_pd = new PageData();
		p_pd.put("id", orderInfo.getProjectCode());
		p_pd = ProjectService.findById(p_pd);
		int pCpuUsed = (p_pd.get("cpu_used") == null ? 0 : (Integer) p_pd.get("cpu_used")) + Integer.parseInt(pd.getString("CPU")) * vmCount;
		int pMemoryUsed = (p_pd.get("memory_used") == null ? 0 : (Integer) p_pd.get("memory_used")) + Integer.parseInt(pd.getString("memory")) * vmCount;
		int pDiskUsed = (p_pd.get("disk_used") == null ? 0 : (Integer) p_pd.get("disk_used")) + Integer.parseInt(pd.getString("diskSize")) * vmCount;
		p_pd.put("cpu_used", pCpuUsed);
		p_pd.put("memory_used", pMemoryUsed);
		p_pd.put("disk_used", pDiskUsed);
		Project project = (Project) PageDataUtil.mapToObject(p_pd, Project.class);
		ProjectService.editUsedQuota(p_pd);

		//获取部门信息，修改部门资源使用额度
		String deptId = project.getDEPARTMENT_ID();
		if (deptId == null) {
			resMap.put("result", "执行异常! 工单对应的部门信息异常");
			ShellUtil.addMsgLog(orderInfo.getOrderNo(), " 工单对应的部门信息异常, 更新部门虚拟机使用额度信息异常");
		} else {
			PageData d_pd = new PageData();
			d_pd.put("DEPARTMENT_ID", deptId);
			d_pd = departmentService.findById(d_pd);
			int dCpuUsed = (d_pd.get("cpu_used") == null ? 0 : (Integer) d_pd.get("cpu_used")) + Integer.parseInt(pd.getString("CPU")) * vmCount;
			int dMemoryUsed = (d_pd.get("memory_used") == null ? 0 : (Integer) d_pd.get("memory_used")) + Integer.parseInt(pd.getString("memory")) * vmCount;
			int dDiskUsed = (d_pd.get("disk_used") == null ? 0 : (Integer) d_pd.get("disk_used")) + Integer.parseInt(pd.getString("diskSize")) * vmCount;
			d_pd.put("cpu_used", dCpuUsed);
			d_pd.put("memory_used", dMemoryUsed);
			d_pd.put("disk_used", dDiskUsed);
			departmentService.editUsedQuota(d_pd);
		}

		//所有安装完毕设置结束标志
		Map<String, String> exeParams = new HashMap<String, String>();
		exeParams.put("executeStatus", "2");
		cmpOrderService.updateExecuteStatus(orderInfo.getOrderNo(), exeParams);
		ShellUtil.addMsgLog(orderInfo.getOrderNo(), "cmp:success");
		return resMap;
	}

	//部署虚拟机
	public Map<String, Object> deployVM(String ip, String loginName, String loginPwd, String projectId, String projectName, CmpOrder orderInfo, PageData pd) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		VirtualMachine vm = new VirtualMachine();
		//添加虚拟机
		//int currentVMNum = virtualMachineService.countByProject(projectId);
		String vmName = generateVMName(projectName,ip,orderInfo.getVirName());//projectName + "_" + (currentVMNum + 1);//虚拟机的名字为当前项目名称+项目拥有的虚拟机总数+1
		String osName = cmpDictService.getCmpDict("os_type", orderInfo.getOsType()).getDictValue();
		String osBitNum = cmpDictService.getCmpDict("os_bit_num", orderInfo.getOsBitNum()).getDictValue();
		String fullOS = osName + "_" + osBitNum;
		String installOS = cmpDictService.getCmpDict("install_OS", fullOS).getDictValue();  //安装的系统软件
		String diskSize = orderInfo.getDiskSize();
		String diskType = orderInfo.getDiskType();
		String[] diskTypeList = diskType.split(",");
		String[] diskSizeList = diskSize.split(",");
		List<DiskInfo> diskInfoList = new ArrayList<DiskInfo>();
		for (int i = 0; i < diskSizeList.length; i++) {
			DiskInfo di = new DiskInfo();
			di.setDiskSize(diskSizeList[i]);
			di.setDiskType(diskTypeList[i] != null ? cmpDictService.getCmpDict("disk_type", diskTypeList[i]).getDictValue() : "");
			diskInfoList.add(di);
		}
		//安装虚拟机
		ShellUtil.addMsgLog(orderInfo.getOrderNo(), "开始安装虚拟机: " + vmName + " IP:" + ip);
		String cloudPlatformId = pd.getString("cloudPlatformId");
		String datacenterId = pd.getString("datacenterId");
		String clusterId = pd.getString("clusterId");
		//判断实施平台ID和申请平台ID是否一致
		if (cloudPlatformId == null || !cloudPlatformId.equals(orderInfo.getPlatType())) {
			resMap.put("resultCode", "-1");
			resMap.put("resultMsg", "执行异常!平台类型和申请的不一致 ");
			ShellUtil.addMsgLog(orderInfo.getOrderNo(), "虚拟机:[" + ip + "] 执行异常!平台类型和申请的不一致");
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
			platformManagerType = "com.cmp.mgr.vmware.VMWareCloudArchManager";
		} else if (platFormType != null && platFormType.equals("openstack")) {
			platformManagerType = "com.cmp.mgr.openstack.OpenstatckCloudArchManager";
		} else if (platFormType != null && platFormType.equals("kvm")) {
			platformManagerType = "com.cmp.mgr.KvmCloudArchManager";
		} else {
			resMap.put("resultCode", "-1");
			resMap.put("resultMsg", "执行异常!不支持的平台类型 ");
			ShellUtil.addMsgLog(orderInfo.getOrderNo(), "虚拟机:[" + ip + "] 执行异常!不支持的平台类型");
			return resMap;
		}

		PageData datacenterPd = new PageData();
		datacenterPd.put("id", datacenterId);
		datacenterPd = datacenterService.findById(datacenterPd);

		//查询镜像模板
		String imageTpId = orderInfo.getImgCode();
		PageData imagePd = new PageData();
		imagePd.put("id", imageTpId);
		imagePd = mirrorService.findTemplateById(imagePd);
		platForm.setPlatformManagerType(platformManagerType);
		CloudArchManager cloudArchManager = cloudArchManagerAdapter.getCloudArchManagerAdaptee(platForm);
		CloneVmRequest cloneVmRequest = new CloneVmRequest();
		cloneVmRequest.setCpuSize(Integer.parseInt(pd.getString("CPU")));
		cloneVmRequest.setRamSize(Long.parseLong(pd.getString("memory")) * 1024);
		cloneVmRequest.setVmName(vmName);
		cloneVmRequest.setDcName(datacenterPd.getString("name"));
		cloneVmRequest.setTplName(imagePd.getString("name"));
		cloneVmRequest.setIp(ip);

		String hostMachineUUID = null;

		try {
			CloneVmResponse response = cloudArchManager.cloneVirtualMachine(cloneVmRequest);
			hostMachineUUID = response.getHostMor();
			
			HostMonitorInfo hostInfo = new HostMonitorInfo();
			hostInfo.setUUID(response.getUuid());
			hostInfo.setIp(ip);
			hostInfo.setPort("10050");
			hostInfo.setVisibleName(vmName);
			hostInfo.setMonitorType(0);
			hostInfo.setOsName("Linux");

			zabbixMonitorService.registerToZabbix(hostInfo);
		} catch (Exception e) {
			e.printStackTrace();
			resMap.put("resultCode", "-1");
			resMap.put("resultMsg", "虚拟机[" + vmName + "]模板安装异常。");
			ShellUtil.addMsgLog(orderInfo.getOrderNo(), "虚拟机[" + vmName + "]模板安装异常。");
			return resMap;
		}

		PageData hm_pd = new PageData();
		hm_pd.put("uuid", hostMachineUUID);
		hm_pd = hostmachineService.findByUUID(hm_pd);
		String hostmachineId = hm_pd.getString("id");
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
		vm.setHostmachineId(hostmachineId == null ? "" : hostmachineId);
		vm.setAppNo(orderInfo.getAppNo());
		vm.setUser(orderInfo.getApplyUserId());
		vm.setDuedate(orderInfo.getExpireDate());
		virtualMachineService.add(vm);
		//所有安装完毕设置结束标志
		logger.info("远程虚拟机创建完毕");
		ShellUtil.addMsgLog(orderInfo.getOrderNo(), "虚拟机:[" + ip + "]虚拟机安装完毕!");
		resMap.put("resultCode", "0");
		resMap.put("vm", vm);
		resMap.put("resultMsg", "虚拟机安装执行成功! ");
		return resMap;
	}

	public Map installSoft(VirtualMachine vm, CmpOrder orderInfo, String mediumId, String softName, String shellcmd) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ShellUtil.addMsgLog(orderInfo.getOrderNo(), "准备部署软件[" + softName + "]");
		//执行软件安装脚本
		ShellUtil shellUtil = new ShellUtil(vm.getIp(), ShellUtil.DEF_PORT, vm.getUsername(),
				vm.getPassword(), ShellUtil.DEF_CHARSET);
		shellUtil.exec(shellcmd, orderInfo.getAppNo());

		//添加虚拟机中间件
		PageData m_pd = new PageData();
		m_pd.put("id", mediumId);
		m_pd = mediumService.findById(m_pd);
		Medium medium = (Medium) PageDataUtil.mapToObject(m_pd, Medium.class);
		DeployedSoft deployedSoft = new DeployedSoft();
		deployedSoft.setVirtualmachineId(String.valueOf(vm.getId()));
		deployedSoft.setSoftName(medium.getName());
		deployedSoft.setStatus("已部署");
		deployedSoft.setSoftType(medium.getType());
		deployedSoft.setSoftVersion(medium.getVersion());
		deployedSoft.setVirtualmachineName(String.valueOf(vm.getName()));
		deployedSoftService.add(deployedSoft);

		ShellUtil.addMsgLog(orderInfo.getOrderNo(), "虚拟机[" + vm.getName() + "] 软件 [" + softName + "] 部署完毕!");
		resMap.put("resultCode", "0");
		resMap.put("resultMsg", "虚拟机软件 [" + softName + " ]安装执行成功! ");
		return resMap;
	}

	public int downloadScript(VirtualMachine vm, String remoteUrl, String localPath) {
		ShellUtil shellUtil = new ShellUtil(vm.getIp(), ShellUtil.DEF_PORT, vm.getUsername(),  
				vm.getPassword() , ShellUtil.DEF_CHARSET);
		shellUtil.exec("curl -k -u " + shellUtil.DEF_SHELL_FILE_USERNAME + ":" +  shellUtil.DEF_SHELL_FILE_PASSWORD + " " +remoteUrl  + " -o " + localPath );
//		if (!shellUtil.checkFileExist(localPath)) {
//			return -2;
//		}
		return 0;
	}

	public String generateVMName(String projectName, String ip, String virName) {
		//Date date = new Date();
		//SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd");
		//String vmName = projectName + "_" +dateFormat.format(date) + date.getTime(); 
		return projectName + "_" +virName + "_" + ip ;
	}

}
