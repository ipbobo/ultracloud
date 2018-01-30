package com.cmp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.libvirt.jna.virConnectAuth;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.entity.tcc.TccCapability;
import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.CloudArchManager;
import com.cmp.mgr.CloudArchManagerAdapter;
import com.cmp.service.VirtualMachineService;
import com.cmp.service.resourcemgt.CloudplatformService;
import com.cmp.sid.ResChartItem;
import com.cmp.sid.Serrie;
import com.cmp.sid.VirtualMachine;
import com.fh.controller.base.BaseController;
import com.fh.util.PageData;

@RequestMapping(value="/resanalyze")
@Controller
public class ResAnalyzeViewController extends BaseController {
		@Resource
		private CloudArchManagerAdapter cloudArchManagerAdapter;
		
		@Resource
		private CloudplatformService cloudplatformService;
		
		@Resource
		private VirtualMachineService virtualMachineService;
		//业务视图总览列表查询
		@RequestMapping(value="list")
		public ModelAndView getResTrendViewList(HttpServletRequest request, HttpServletResponse response) throws Exception{
			ModelAndView mv = new ModelAndView();
			
			mv.setViewName("res/res_analyze_list");
			return mv;
		}
		
		@RequestMapping(value="queryBar")
		@ResponseBody
		public Object executeLine() throws Exception{
			
			Map <String , AnalyzeItem> analyzeItemMap = new HashMap<String , AnalyzeItem>();
			List<PageData> cPdList = cloudplatformService.listAll(null, false);
			List<String> plNameList = new ArrayList<String>();
			List<String> plIdList = new ArrayList<String>();
			for (PageData cloudplatformPd : cPdList) {
				String platformManagerType;
				String type = cloudplatformPd.getString("type");
				if (type != null && type.equals("vmware")) {
					platformManagerType="com.cmp.mgr.vmware.VMWareCloudArchManager";
				}else if (type != null && type.equals("openstack")) {
					platformManagerType="com.cmp.mgr.openstack.OpenstatckCloudArchManager";
				}else if (type != null && type.equals("kvm")) {
					platformManagerType="com.cmp.mgr.KvmCloudArchManager";
				}else {
					continue;
				}
				plNameList.add(cloudplatformPd.getString("name"));
				plIdList.add(cloudplatformPd.getString("id"));
				TccCloudPlatform platForm = new TccCloudPlatform();
				platForm.setCloudplatformUser(cloudplatformPd.getString("username"));
				platForm.setCloudplatformPassword(cloudplatformPd.getString("password"));
				platForm.setCloudplatformIp(cloudplatformPd.getString("ip"));
				
				platForm.setPlatformManagerType(platformManagerType);
				CloudArchManager cloudArchManager = cloudArchManagerAdapter.getCloudArchManagerAdaptee(platForm);
				TccCapability tccCapability = cloudArchManager.getCapability();
				AnalyzeItem analyzeItem = new AnalyzeItem();
				analyzeItem.setPlatform(type);
				analyzeItem.setCpuTotal(tccCapability.getSupportedVcpus());
				analyzeItem.setMemoryTotal((int)(tccCapability.getSupportedMemory()/1024/1024));
				analyzeItem.setDiskTotal((int)(tccCapability.getSupportedStorage()/1024/1024));
				analyzeItemMap.put(type, analyzeItem);
				
			}
			//总量
			String[] cpuTotalArr = new String[plNameList.size()];
			String[] diskTotalArr = new String[plNameList.size()];
			String[] memoryTotalArr = new String[plNameList.size()];
			
			//计算已使用
			int[] cpuNums = new int[plNameList.size()]; 
			int[] diskNums = new int[plNameList.size()]; 
			int[] memoryNums = new int[plNameList.size()];
			
			List<VirtualMachine> vmList = virtualMachineService.findAll();
			for (VirtualMachine vm : vmList) {
				int index = plIdList.indexOf(vm.getPlatform());		
				if (index < 0) {
					continue;
				}
				cpuNums[index] = cpuNums[index] + Integer.parseInt(vm.getCpu() == null ? "0" : vm.getCpu());
				diskNums[index] = diskNums[index] + Integer.parseInt(vm.getDatadisk() == null ? "0" : vm.getDatadisk());
				memoryNums[index] = memoryNums[index] + Integer.parseInt(vm.getMemory() == null ? "0" : vm.getMemory());
			}
			
			String[] cpuUsedNumstr = new String[cpuNums.length];
			String[] diskUsedNumstr = new String[diskNums.length];
			String[] memoryUsedNumstr = new String[memoryNums.length];
			for (int i = 0 ; i < plNameList.size() ;i++) {
				cpuUsedNumstr[i] = String.valueOf(cpuNums[i]);
				diskUsedNumstr[i] = String.valueOf(diskNums[i]);
				memoryUsedNumstr[i] = String.valueOf(memoryNums[i]);
				cpuTotalArr[i] = String.valueOf(analyzeItemMap.get(plIdList.get(i)).getCpuTotal());
				diskTotalArr[i] = String.valueOf(analyzeItemMap.get(plIdList.get(i)).getDiskTotal());
				memoryTotalArr[i] = String.valueOf(analyzeItemMap.get(plIdList.get(i)).getMemoryTotal());
			}
			
			String[] plNameArr = plNameList.toArray(new String[plNameList.size()]);
			//CPU使用情况
			Map<String, ResChartItem> analyzeMap = new HashMap<String, ResChartItem>();
			
			ResChartItem cpuUsedItem = new ResChartItem();
			cpuUsedItem.setChartTitle("CPU使用情况");
			cpuUsedItem.setTitleArr(new String[]{"总量","已使用"});
			cpuUsedItem.setxDataArr(plNameArr);
			Serrie cpuSerrie1 = new Serrie();
			cpuSerrie1.setName("总量");
			cpuSerrie1.setValue(cpuTotalArr);
			Serrie cpuSerrie2 = new Serrie();
			cpuSerrie2.setName("已使用");
			cpuSerrie2.setValue(cpuUsedNumstr);
			cpuUsedItem.setyDataArr(new Serrie[]{cpuSerrie1, cpuSerrie2});
			analyzeMap.put("cpu", cpuUsedItem);
			
			
			ResChartItem memoryUsedItem = new ResChartItem();
			memoryUsedItem.setChartTitle("内存使用情况");
			memoryUsedItem.setTitleArr(new String[]{"总量","已使用"});
			memoryUsedItem.setxDataArr(plNameArr);
			Serrie memoryserrie1 = new Serrie();
			memoryserrie1.setName("总量");
			memoryserrie1.setValue(memoryTotalArr);
			Serrie memoryserrie2 = new Serrie();
			memoryserrie2.setName("已使用");
			memoryserrie2.setValue(memoryUsedNumstr);
			memoryUsedItem.setyDataArr(new Serrie[]{memoryserrie1, memoryserrie2});
			
			ResChartItem diskUsedItem = new ResChartItem();
			diskUsedItem.setChartTitle("磁盘使用情况");
			diskUsedItem.setTitleArr(new String[]{"总量","已使用"});
			diskUsedItem.setxDataArr(plNameArr);
			Serrie diskserrie1 = new Serrie();
			diskserrie1.setName("总量");
			diskserrie1.setValue(diskTotalArr);
			Serrie diskserrie2 = new Serrie();
			diskserrie2.setName("已使用");
			diskserrie2.setValue(diskUsedNumstr);
			diskUsedItem.setyDataArr(new Serrie[]{diskserrie1, diskserrie2});
			
			analyzeMap.put("cpu", cpuUsedItem);
			analyzeMap.put("memory", memoryUsedItem);
			analyzeMap.put("disk", diskUsedItem);
			return analyzeMap;
		}
		
		class AnalyzeItem{
			private String platform;
			private int cpuTotal;
			private int memoryTotal;
			private int diskTotal;
			public String getPlatform() {
				return platform;
			}
			public void setPlatform(String platform) {
				this.platform = platform;
			}
			public int getCpuTotal() {
				return cpuTotal;
			}
			public void setCpuTotal(int cpuTotal) {
				this.cpuTotal = cpuTotal;
			}
			public int getMemoryTotal() {
				return memoryTotal;
			}
			public void setMemoryTotal(int memoryTotal) {
				this.memoryTotal = memoryTotal;
			}
			public int getDiskTotal() {
				return diskTotal;
			}
			public void setDiskTotal(int diskTotal) {
				this.diskTotal = diskTotal;
			}
			
			
		}
	
}
