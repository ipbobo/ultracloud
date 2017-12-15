package com.cmp.workorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.Project;
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpOrderService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.ProjectService;
import com.cmp.service.VirtualMachineService;
import com.cmp.sid.CloudInfoCollect;
import com.cmp.sid.CmpCloudInfo;
import com.cmp.sid.CmpOrder;
import com.cmp.sid.CmpWorkOrder;
import com.cmp.sid.VirtualMachine;
import com.cmp.util.PageDataUtil;
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
			int diskTotal = 0;
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
		if (cmpWorkorder.getVirtualMachineId() == null || cmpWorkorder.getVirtualMachineId().length() == 0) {
			cloudInfo.setSoftStatus("未安装");
			cloudInfo.setOsStatus("未安装");
		}else {
			VirtualMachine vm = virtualMachineService.findById(cmpWorkorder.getVirtualMachineId());
			cloudInfo.setSoftStatus(vm.getSoftStatus());
			cloudInfo.setOsStatus(vm.getOsStatus());
		}
		cloudInfo.setExpireDate(orderInfo.getExpireDate());
		//......................................................
		return cloudInfo;
	}

}
