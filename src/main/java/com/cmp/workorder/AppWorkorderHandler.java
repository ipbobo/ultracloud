package com.cmp.workorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.DeployedSoft;
import com.cmp.entity.Medium;
import com.cmp.entity.Project;
import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.CloudArchManager;
import com.cmp.mgr.CloudArchManagerAdapter;
import com.cmp.mgr.bean.CreateVmRequest;
import com.cmp.mgr.impl.VMWareCloudArchManager;
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpOrderService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.DeployedSoftService;
import com.cmp.service.MediumService;
import com.cmp.service.ProjectService;
import com.cmp.service.VirtualMachineService;
import com.cmp.service.resourcemgt.CloudplatformService;
import com.cmp.sid.CloudInfoCollect;
import com.cmp.sid.CmpCloudInfo;
import com.cmp.sid.CmpOrder;
import com.cmp.sid.CmpWorkOrder;
import com.cmp.sid.DiskInfo;
import com.cmp.sid.VirtualMachine;
import com.cmp.util.PageDataUtil;
import com.cmp.util.ShellUtil;
import com.cmp.util.StringUtil;
import com.fh.entity.system.Department;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.util.PageData;

@Service("appWorkorderHandler")
public class AppWorkorderHandler implements IWorkorderHandler {

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
		resMap.put("toPageUrl", "workorder/applyexecute");
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
					0 : vmNum*Integer.parseInt(StringUtil.getInteger(cmpDictService.getCmpDict("memory", orderInfo.getMemory()).getDictValue())));
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

	@Override
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
		
		
		//测试添加虚拟机
		
		//rhel6_64Guest
		//centos64Guest
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
		int currentVMNum = virtualMachineService.countByProject(project.getId());
		String vmName = project.getName() + "_" + (currentVMNum+1); //虚拟机的名字为当前项目名称+项目拥有的虚拟机INDEX
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
		String cloudplatformId = pd.getString("cloudplatformId");
		String datacenterId = pd.getString("datacenterId");
		String clusterId = pd.getString("clusterId");
		
		//查询cloudplatform
		PageData c_pd = new PageData();
		c_pd.put("id", cloudplatformId);
		PageData cloudplatformPd = cloudplatformService.findById(c_pd, false);
		String platFormName = cloudplatformPd.getString("name");		//云平台名
		
		TccCloudPlatform platForm = new TccCloudPlatform();
		platForm.setCloudplatformUser(cloudplatformPd.getString("username"));
		platForm.setCloudplatformPassword(cloudplatformPd.getString("password"));
		platForm.setCloudplatformIp(cloudplatformPd.getString("ip"));
		String platformManagerType = "";
		if (orderInfo.getPlatType() != null && orderInfo.getPlatType().equals("vmware")) {
			platformManagerType="com.cmp.mgr.impl.VMWareCloudArchManager";
		}else if (orderInfo.getPlatType() != null && orderInfo.getPlatType().equals("openstack")) {
			platformManagerType="com.cmp.mgr.impl.OpenstatckCloudArchManager";
		}else if (orderInfo.getPlatType() != null && orderInfo.getPlatType().equals("kvm")) {
			platformManagerType="com.cmp.mgr.impl.KvmCloudArchManager";
		}
		platForm.setPlatformManagerType(platformManagerType);
		
		CloudArchManager cloudArchManager = cloudArchManagerAdapter.getCloudArchManagerAdaptee(platForm);
		CreateVmRequest cvq = new CreateVmRequest();
		cvq.setCupCount(Integer.parseInt(pd.getString("CPU")));
		cvq.setMemSizeMB(Long.parseLong(pd.getString("memory")));
		cvq.setDiskSizeKB(Long.parseLong(diskInfoList.get(0).getDiskSize())*1024*1024);
		cvq.setDiskMode("persistent");					
		cvq.setDcName("DC1");
		cvq.setVmName(vmName);
		cvq.setNetName("VM Network");					//网络名称  ---   datacenter_network表中根据 数据中心 ID获取 				
		cvq.setRpName("Resources");						//资源池 --- 先写死
		cvq.setNicName("VMXNET 3");						//写死先
		cvq.setDsName("datastore2-raid5-2.5t");			//调接口获取
		cvq.setGuestOs(installOS);
		//cloudArchManager.createVirtualMachine(cvq);
		
		String softCode = orderInfo.getSoftCode();
		String[] softs = softCode.split(",");
		VirtualMachine vm = new VirtualMachine();
		vm.setCpu(pd.getString("CPU"));
		vm.setMemory(pd.getString("memory"));
		//暂时先设置第一个磁盘
		vm.setDatadisk(diskInfoList.size() > 0 ? diskInfoList.get(0).getDiskSize() : "300");
		vm.setProjectId(workOrder.getProjectCode());
		vm.setOs(fullOS);
		vm.setOsStatus("未安装");
		vm.setSoft(softCode);
		vm.setSoftStatus("未安装");
		vm.setName(vmName);
		vm.setIp("192.168.1.101");
		vm.setUsername("test");
		vm.setPassword("pwd123");
		vm.setStatus("0");
		vm.setHostmachineId(182837323);
		vm.setAppNo(workOrder.getAppNo());
		vm.setUser(workOrder.getApplyUserId());
		virtualMachineService.add(vm);
		
		
		//添加虚拟机中间件
		for (String oneSoft : softs) {
			PageData s_pd = new PageData();
			s_pd.put("id", oneSoft);
			s_pd = mediumService.findById(s_pd);
			Medium medium = (Medium) PageDataUtil.mapToObject(s_pd, Medium.class);
			DeployedSoft deployedSoft = new DeployedSoft();
			deployedSoft.setVirtualmachineId(String.valueOf(vm.getId()));
			deployedSoft.setSoftName(medium.getName());
			deployedSoft.setStatus("0");
			deployedSoft.setSoftType(medium.getType());
			deployedSoft.setSoftVersion(medium.getVersion());
			deployedSoft.setVirtualmachineName(String.valueOf(vm.getName()));
			deployedSoftService.add(deployedSoft);
			
			//执行软件安装脚本
			ShellUtil shellUtil = new ShellUtil("118.242.40.216", 7001, "root",  
	                "r00t0neio", "utf-8");
			shellUtil.exec("./test.sh", workOrder.getAppNo());
		}
		resMap.put("result", "执行成功!");
		return resMap;
	}

}
