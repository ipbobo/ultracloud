package com.cmp.workorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.DeployedSoft;
import com.cmp.service.CmpOpServeService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.DeployedSoftService;
import com.cmp.service.ScriptService;
import com.cmp.service.VirtualMachineService;
import com.cmp.sid.CmpOpServe;
import com.cmp.sid.CmpWorkOrder;
import com.cmp.sid.VirtualMachine;
import com.fh.util.PageData;

@Service("operWorkorderHandler")
public class OperWorkorderHandler implements IWorkorderHandler {
	
	
	@Resource
	private CmpWorkOrderService cmpWorkOrderService;
	
	@Resource
	private CmpOpServeService cmpOpServeService;
	
	@Resource
	private DeployedSoftService deployedSoftService;
	
	@Resource
	private VirtualMachineService virtualMachineService;
	
	@Resource
	private ScriptService scriptService;

	@Override
	public Map<String, Object> toWorkorderView(CmpWorkOrder cmpWorkorder) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap = buildViewInfo(cmpWorkorder, resMap);
		if (resMap.get("toPageUrl") == null) {
			resMap.put("toPageUrl", "workorder/operview");
		}
		return resMap;
	}

	@Override
	public Map<String, Object> toWorkorderCheck(CmpWorkOrder cmpWorkorder) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap = buildViewInfo(cmpWorkorder, resMap);
		resMap.put("userRole", "check");  //设置此值，可在页面中看到操作按钮，没有就不显示
		if (resMap.get("toPageUrl") == null) {
			resMap.put("toPageUrl", "workorder/opercheck");
		}
		return resMap;
	}

	@Override
	public Map<String, Object> toWorkorderExecute(CmpWorkOrder cmpWorkorder) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap = buildViewInfo(cmpWorkorder, resMap);
		resMap.put("userRole", "execute");  //设置此值，可在页面中看到操作按钮，没有就不显示
		if (resMap.get("toPageUrl") == null) {
			resMap.put("toPageUrl", "workorder/operexecute");
		}
		return resMap;
	}

	@Override
	public Map<String, Object> executeWork(PageData pd, CmpWorkOrder workOrder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<String, Object> buildViewInfo(CmpWorkOrder cmpWorkorder, Map<String, Object> resMap) throws Exception {
		//工单信息
		CmpOpServe opServe = cmpOpServeService.findByOrderNo(cmpWorkorder.getOrderNo());
		String serviceType = opServe.getServiceType();
		try {
			cmpOpServeService.encase(opServe);  //中文填充
		} catch (Exception e) {
			e.printStackTrace();
		}
		resMap.put("opServe", opServe);
		resMap.put("workorder", cmpWorkorder);
		resMap.put("serviceType", serviceType);
		if (serviceType.equals("1")) {
			List<DeployedSoft> rebootSoftMap = new ArrayList<DeployedSoft>();
			String vm = opServe.getVm();  // 在此格式：虚拟机1:软件1，软件2|虚拟机2:软件1,软件2|
			String[] vmlist = vm.split("\\|");
			for (String dep_softs : vmlist) {
				if (dep_softs == null || dep_softs.length() == 0) {
					continue;
				}
				String[] softs = dep_softs.split(",");
				for (String softId : softs) {
					if (softId == null || softId.length() == 0) {
						continue;
					}
					DeployedSoft depSoft = deployedSoftService.findById(softId);
					rebootSoftMap.add(depSoft);
				}
			}
			resMap.put("rebootSoft", rebootSoftMap);
			
			//查询重启中间件script列表
			List<PageData> scriptList = scriptService.listAll(new PageData());
			resMap.put("scriptList", scriptList);
		}
		if (serviceType.equals("2")) {
			List<DeployedSoft> installSoftList= new ArrayList<DeployedSoft>();
			String vm = opServe.getDeploySoftId();  // 在此格式：虚拟机1，虚拟机2
			String[] softs = vm.split(",");
			for (String softId : softs) {
				if (softId == null || softId.length() == 0) {
					continue;
				}
				DeployedSoft depSoft = deployedSoftService.findById(softId);
				installSoftList.add(depSoft);
			}
			resMap.put("installSoftList", installSoftList);
		}
		if (serviceType.equals("3")) {
			List<VirtualMachine> vmList = new ArrayList<VirtualMachine>();
			String vm = opServe.getVm();  // 在此格式：虚拟机1，虚拟机2
			String[] vmIds = vm.split(",");
			for (String vmId : vmIds) {
				if (vmId == null || vmId.length() == 0) {
					continue;
				}
				VirtualMachine virtualMachine = virtualMachineService.findById(vmId);
				vmList.add(virtualMachine);
			}
			resMap.put("vmList", vmList);
		}
		if (serviceType.equals("3") || serviceType.equals("4") || serviceType.equals("5") || serviceType.equals("6") || serviceType.equals("7")|| serviceType.equals("8")) {
			List<VirtualMachine> vmList = new ArrayList<VirtualMachine>();
			String vm = opServe.getVm();  // 在此格式：虚拟机1，虚拟机2
			String[] vmIds = vm.split(",");
			for (String vmId : vmIds) {
				if (vmId == null || vmId.length() == 0) {
					continue;
				}
				VirtualMachine virtualMachine = virtualMachineService.findById(vmId);
				vmList.add(virtualMachine);
			}
			resMap.put("vmList", vmList);
		}
		return resMap;
	}

}
