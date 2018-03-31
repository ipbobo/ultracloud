package com.cmp.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.cmp.sid.PlatformAnalyze;
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
			Map <String , AnalyzeItem> analyzeItemMap = new HashMap<String , AnalyzeItem>();
			List<PageData> cPdList = cloudplatformService.listAll(null, false);
			List<String> plNameList = new ArrayList<String>();		//平台名称列表
			List<String> plIdList = new ArrayList<String>();		//平台ID列表
			for (PageData cloudplatformPd : cPdList) {
				String platformManagerType;
				String type = cloudplatformPd.getString("type");
				if (type != null && type.equals("vmware")) {
					platformManagerType="com.cmp.mgr.vmware.VMWareCloudArchManager";
				}else if (type != null && type.equals("openstack")) {
					platformManagerType="com.cmp.mgr.openstack.OpenstatckCloudArchManager";
					continue;	//...............................................暂时无法做openstack
				}else if (type != null && type.equals("kvm")) {
					platformManagerType="com.cmp.mgr.KvmCloudArchManager";
					continue;	//...............................................暂时无法做KVM
				}else {
					continue;
				}
				
				TccCloudPlatform platForm = new TccCloudPlatform();
				platForm.setCloudplatformUser(cloudplatformPd.getString("username"));
				platForm.setCloudplatformPassword(cloudplatformPd.getString("password"));
				platForm.setCloudplatformIp(cloudplatformPd.getString("ip"));
				
				platForm.setPlatformManagerType(platformManagerType);
				CloudArchManager cloudArchManager = cloudArchManagerAdapter.getCloudArchManagerAdaptee(platForm);
				TccCapability tccCapability = cloudArchManager.getCapability();
				if (tccCapability == null ) {
					continue;
				}
				AnalyzeItem analyzeItem = new AnalyzeItem();
				analyzeItem.setPlatform(type);
				analyzeItem.setCpuTotal(tccCapability.getSupportedVcpus());
				analyzeItem.setMemoryTotal((int)(tccCapability.getSupportedMemory()/1024/1024/1024));
				analyzeItem.setDiskTotal((int)(tccCapability.getSupportedStorage()/1024/1024/1024));
				analyzeItemMap.put(cloudplatformPd.getString("id"), analyzeItem);
				
				plNameList.add(cloudplatformPd.getString("name"));
				plIdList.add(cloudplatformPd.getString("id"));
				
			}
			
			//资源分析
			int currDate = 7;	//分析天数
			int[] currAppCpuNum =  new int[plNameList.size()];
			int[] currAppDiskNum =  new int[plNameList.size()];
			int[] currAppMemoryNum =  new int[plNameList.size()];
			List<VirtualMachine> currAppVmList = virtualMachineService.findCurrentDay(String.valueOf(currDate));
			if (currAppVmList != null && currAppVmList.size() >0) {
				for (VirtualMachine appVm : currAppVmList) {
					int index = plIdList.indexOf(appVm.getPlatform());
					if (index < 0) {
						continue;
					}
					currAppCpuNum[index] = currAppCpuNum[index]  + Integer.parseInt(appVm.getCpu() == null ? "0" : appVm.getCpu());
					currAppDiskNum[index] = currAppDiskNum[index]  +  Integer.parseInt(appVm.getDatadisk() == null ? "0" : appVm.getDatadisk());
					currAppMemoryNum[index] = currAppMemoryNum[index]  + Integer.parseInt(appVm.getMemory() == null ? "0" : appVm.getMemory());
				}
			}
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
			
			//总量
			String[] cpuTotalArr = new String[plNameList.size()];
			String[] diskTotalArr = new String[plNameList.size()];
			String[] memoryTotalArr = new String[plNameList.size()];
			
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
			
			
			List<PlatformAnalyze> platformAnalyzeList = new ArrayList<PlatformAnalyze>();
			for (int i = 0 ; i < plNameList.size() ;i++) {
				PlatformAnalyze platformAnalyze = new PlatformAnalyze();
				if (plNameList.get(i) == null) {
					continue;
				}
				platformAnalyze.setPlatformName(plNameList.get(i));
				platformAnalyze.setCputotal(String.valueOf(cpuNums[i]));
				platformAnalyze.setCpuUsedPercent(getNumberPercent(Integer.parseInt(cpuTotalArr[i]), cpuNums[i]));
				platformAnalyze.setCpuAppPerDays(String.valueOf(getDiv(currAppCpuNum[i], currDate)));
				if ((Integer.parseInt(cpuUsedNumstr[i])/cpuNums[i]) > 1) {
					platformAnalyze.setCpuSupportAppDays("0");
				}else {
					int z =  cpuNums[i] - Integer.parseInt(cpuUsedNumstr[i]);
					if (Integer.parseInt(platformAnalyze.getCpuAppPerDays()) == 0) {
						platformAnalyze.setCpuSupportAppDays("∞");
					}else {
						platformAnalyze.setCpuSupportAppDays(String.valueOf(getDiv(z, Integer.parseInt(platformAnalyze.getCpuAppPerDays()))));
					}
					
				}
				
				platformAnalyze.setMemorytotal(String.valueOf(memoryNums[i]));
				platformAnalyze.setMemoryUsedPercent(getNumberPercent(Integer.parseInt(memoryTotalArr[i]), memoryNums[i]));
				platformAnalyze.setMemoryAppPerDays(String.valueOf(getDiv(currAppMemoryNum[i], currDate)));
				if ((Integer.parseInt(memoryUsedNumstr[i])/memoryNums[i]) > 1) {
					platformAnalyze.setMemorySupportAppDays("0");
				}else {
					int z =  memoryNums[i] - Integer.parseInt(memoryUsedNumstr[i]);
					if (Integer.parseInt(platformAnalyze.getMemoryAppPerDays()) == 0) {
						platformAnalyze.setMemorySupportAppDays("∞");
					}else {
						platformAnalyze.setMemorySupportAppDays(String.valueOf(getDiv(z, Integer.parseInt(platformAnalyze.getMemoryAppPerDays()))));
					}
				}
				
				platformAnalyze.setDisktotal(String.valueOf(diskNums[i]));
				platformAnalyze.setDiskUsedPercent(getNumberPercent(Integer.parseInt(diskTotalArr[i]), diskNums[i]));
				platformAnalyze.setDiskAppPerDays(String.valueOf(getDiv(currAppDiskNum[i], currDate)));
				if ((Integer.parseInt(diskUsedNumstr[i])/diskNums[i]) > 1) {
					platformAnalyze.setDiskSupportAppDays("0");
				}else {
					int z =  diskNums[i] - Integer.parseInt(diskUsedNumstr[i]);
					if (Integer.parseInt(platformAnalyze.getDiskAppPerDays()) == 0) {
						platformAnalyze.setDiskSupportAppDays("∞");
					}else {
						platformAnalyze.setDiskSupportAppDays(String.valueOf(getDiv(z, Integer.parseInt(platformAnalyze.getDiskAppPerDays()))));
					}
				}
				platformAnalyzeList.add(platformAnalyze);
			}
			mv.addObject("platformAnalyzeList",platformAnalyzeList);
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
					continue;	//...............................................暂时无法做openstack
				}else if (type != null && type.equals("kvm")) {
					platformManagerType="com.cmp.mgr.KvmCloudArchManager";
					continue;	//...............................................暂时无法做KVM
				}else {
					continue;
				}
				TccCloudPlatform platForm = new TccCloudPlatform();
				platForm.setCloudplatformUser(cloudplatformPd.getString("username"));
				platForm.setCloudplatformPassword(cloudplatformPd.getString("password"));
				platForm.setCloudplatformIp(cloudplatformPd.getString("ip"));
				
				platForm.setPlatformManagerType(platformManagerType);
				CloudArchManager cloudArchManager = cloudArchManagerAdapter.getCloudArchManagerAdaptee(platForm);
				TccCapability tccCapability = cloudArchManager.getCapability();
				if (tccCapability == null ) {
					continue;
				}
				AnalyzeItem analyzeItem = new AnalyzeItem();
				analyzeItem.setPlatform(type);
				analyzeItem.setCpuTotal(tccCapability.getSupportedVcpus());
				analyzeItem.setMemoryTotal((int)(tccCapability.getSupportedMemory()/1024/1024/1024));
				analyzeItem.setDiskTotal((int)(tccCapability.getSupportedStorage()/1024/1024/1024));
				analyzeItemMap.put(cloudplatformPd.getString("id"), analyzeItem);
				plNameList.add(cloudplatformPd.getString("name"));
				plIdList.add(cloudplatformPd.getString("id"));
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
			Map<String, Object> analyzeMap = new HashMap<String, Object>();
			
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
		
		
		public String getNumberPercent(int num1, int num2) {
			if (num1 == 0) {
				return "0%";
			}
			if (num2 == 0) {
				return "100%";
			}
			
			DecimalFormat df=new DecimalFormat("0.00");
			String result = (df.format((float)num2/num1*100)); 
			
			return (result + "%");
		} 
	
		
		public int getDiv(int a, int b) {
			return a%b==0?a/b:a/b+1;
		}
}
