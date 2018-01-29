package com.cmp.controller;

import java.util.ArrayList;
import java.util.Collection;
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

import com.cmp.service.CmpOrderService;
import com.cmp.service.servicemgt.EnvironmentService;
import com.cmp.sid.ResChartItem;
import com.cmp.sid.Serrie;
import com.fh.controller.base.BaseController;
import com.fh.util.PageData;

@RequestMapping(value="/restrend")
@Controller
public class ResTrendViewController extends BaseController {
	
	@Resource
	private CmpOrderService cmpOrderService;

	@Resource
	private EnvironmentService environmentService;
	//业务视图总览列表查询
		@RequestMapping(value="list")
		public ModelAndView getResTrendViewList(HttpServletRequest request, HttpServletResponse response) throws Exception{
			ModelAndView mv = new ModelAndView();
			
			mv.setViewName("res/res_trend_list");
			return mv;
		}
		
		@RequestMapping(value="queryLine")
		@ResponseBody
		public Object executeLine() throws Exception{
			//获取所有最近几天的资源申请记录
			Map<String, List<PageData>> orderMap = new HashMap<String, List<PageData>>();
			Map<String, String> titleMap = new HashMap<String, String>();
			List<PageData> orderList = cmpOrderService.selectByCurrentDate();
			for (PageData pd : orderList) {
				if (pd.get("envCode") != null && titleMap.get(pd.get("envCode").toString()) == null) {
					PageData envpd = new PageData();
					envpd.put("id", pd.get("envCode"));
					envpd = environmentService.findById(envpd);
					String envName = envpd.getString("name");
					titleMap.put(pd.get("envCode").toString(), envName);
				}
				String key_ordertime = pd.getString("ordertime");
				List<PageData> oneDayOrderList = orderMap.get(key_ordertime);
				if (oneDayOrderList == null) {
					oneDayOrderList = new ArrayList<PageData>();
					oneDayOrderList.add(pd);
					orderMap.put(key_ordertime, oneDayOrderList);
				}else {
					oneDayOrderList.add(pd);
				}
			}
			
			String[] xDataArr = (String[]) orderMap.keySet().toArray(new String[orderMap.keySet().size()]);
			//计算CPU申请个数
			Map<String, TrendItem> trendCpuMap = new HashMap<String, TrendItem>();
			Map<String, TrendItem> trendDiskMap = new HashMap<String, TrendItem>();
			Map<String, TrendItem> trendVmMap = new HashMap<String, TrendItem>();
			Map<String, TrendItem> trendMemoryMap = new HashMap<String, TrendItem>();
			for(String keydate : orderMap.keySet()) {
				List<PageData> pdList = orderMap.get(keydate);
				for (PageData pd : pdList) {
					String type = (String)pd.get("envCode");
					if (trendCpuMap.get(type) == null) {
						TrendItem trendCpuItem = new TrendItem();
						trendCpuItem.setType(type);
						for (String oneDate : xDataArr) {
							trendCpuItem.getValueList().put(oneDate, 0);
						}
						trendCpuMap.put(type, trendCpuItem);
					}
						trendCpuMap.get(type).getValueList().put(keydate, (trendCpuMap.get(type).getValueList().get(keydate) + (Integer.parseInt((pd.getString("cpu"))) * Integer.parseInt((pd.getString("virNum"))))));
					
					if (trendDiskMap.get(type) == null) {
						TrendItem trendDiskItem = new TrendItem();
						trendDiskItem.setType(type);
						for (String oneDate : xDataArr) {
							trendDiskItem.getValueList().put(oneDate, 0);
						}
						trendDiskMap.put(type, trendDiskItem);
					}
						String appDiskSize = pd.getString("diskSize");
						String[] diskSizeArr = appDiskSize.split(",");
						int DiskTotal = 0;
						for (String sDiskSize : diskSizeArr) {
							DiskTotal += Integer.parseInt(sDiskSize);
						}
						trendDiskMap.get(type).getValueList().put(keydate, (trendDiskMap.get(type).getValueList().get(keydate) + (DiskTotal * Integer.parseInt((pd.getString("virNum"))))));
						
					if (trendVmMap.get(type) == null) {
						TrendItem trendVmItem = new TrendItem();
						trendVmItem.setType(type);
						for (String oneDate : xDataArr) {
							trendVmItem.getValueList().put(oneDate, 0);
						}
						trendVmMap.put(type, trendVmItem);
					}
					trendVmMap.get(type).getValueList().put(keydate, (trendVmMap.get(type).getValueList().get(keydate) + Integer.parseInt((pd.getString("virNum")))));
					
					if (trendMemoryMap.get(type) == null) {
						TrendItem trendMemoryItem = new TrendItem();
						trendMemoryItem.setType(type);
						for (String oneDate : xDataArr) {
							trendMemoryItem.getValueList().put(oneDate, 0);
						}
						trendMemoryMap.put(type, trendMemoryItem);
					}
					trendMemoryMap.get(type).getValueList().put(keydate, (trendMemoryMap.get(type).getValueList().get(keydate) + (Integer.parseInt((pd.getString("memory"))) * Integer.parseInt((pd.getString("virNum"))))));
	
				}
			}
			
			Collection<String> titleItems = titleMap.values();
			
			Map<String, ResChartItem> chartMap = new HashMap<String, ResChartItem>();
			ResChartItem cpuTrendItem = new ResChartItem();
			cpuTrendItem.setChartTitle("CPU申请量(个)");
			cpuTrendItem.setTitleArr((String[])titleItems.toArray(new String[titleItems.size()]));
			cpuTrendItem.setxDataArr(xDataArr);
			serrieMaker(cpuTrendItem, trendCpuMap);
			chartMap.put("cpu", cpuTrendItem);
			
			ResChartItem diskTrendItem = new ResChartItem();
			diskTrendItem.setChartTitle("硬盘申请量(GB)");
			diskTrendItem.setTitleArr((String[])titleItems.toArray(new String[titleItems.size()]));
			diskTrendItem.setxDataArr(xDataArr);
			serrieMaker(diskTrendItem, trendDiskMap);
			chartMap.put("disk", diskTrendItem);
			
			ResChartItem vmTrendItem = new ResChartItem();
			vmTrendItem.setChartTitle("虚拟机服务申请量(个)");
			vmTrendItem.setTitleArr((String[])titleItems.toArray(new String[titleItems.size()]));
			vmTrendItem.setxDataArr(xDataArr);
			serrieMaker(vmTrendItem, trendVmMap);
			chartMap.put("vm", vmTrendItem);
			
			ResChartItem memoryTrendItem = new ResChartItem();
			memoryTrendItem.setChartTitle("内存申请量(GB)");
			memoryTrendItem.setTitleArr((String[])titleItems.toArray(new String[titleItems.size()]));
			memoryTrendItem.setxDataArr(xDataArr);
			serrieMaker(memoryTrendItem, trendMemoryMap);
			chartMap.put("memory", memoryTrendItem);
			
//			Serrie serrie1 = new Serrie();
//			serrie1.setName("分类1");
//			serrie1.setValue(new String[]{"1","5","2","4"});
//			
//			Serrie serrie2 = new Serrie();
//			serrie2.setName("分类2");
//			serrie2.setValue(new String[]{"3","6","7","2"});
			
//			trendItem.setyDataArr(new Serrie[]{serrie1, serrie2});
			return chartMap;
		}
		
		
		public void serrieMaker(ResChartItem charItem, Map<String, TrendItem> trendMap) {
			List<Serrie> serrieList = new ArrayList<Serrie>();
			for (String TrendItemKey : trendMap.keySet()) {
				TrendItem ti = trendMap.get(TrendItemKey);
				Serrie serrie = new Serrie();
				serrie.setName(ti.getType());
				Integer[] nums = ((Integer[])ti.getValueList().values().toArray(new Integer[ti.getValueList().values().size()]));
				String[] strNums = new String[nums.length];
				for (int i=0; i<nums.length;i++) {
					strNums[i] = nums[i].toString();
				}
				serrie.setValue(strNums);
				serrieList.add(serrie);
			}
			charItem.setyDataArr((Serrie[])serrieList.toArray(new Serrie[serrieList.size()]));
		}
	
		class TrendItem{
			private String type;
			private String typeName;
			private Map<String, Integer> valueList =new HashMap<String, Integer>();
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getTypeName() {
				return typeName;
			}
			public void setTypeName(String typeName) {
				this.typeName = typeName;
			}
			public Map<String, Integer> getValueList() {
				return valueList;
			}
			public void setValueList(Map<String, Integer> valueList) {
				this.valueList = valueList;
			}
			
			
		}
}
