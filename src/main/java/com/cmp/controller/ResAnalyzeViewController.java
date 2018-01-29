package com.cmp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.sid.ResChartItem;
import com.cmp.sid.Serrie;
import com.fh.controller.base.BaseController;

@RequestMapping(value="/resanalyze")
@Controller
public class ResAnalyzeViewController extends BaseController {

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
			Map<String, ResChartItem> analyzeMap = new HashMap<String, ResChartItem>();
			ResChartItem cpuUsedItem = new ResChartItem();
			cpuUsedItem.setChartTitle("CPU使用情况");
			cpuUsedItem.setTitleArr(new String[]{"总量","已使用"});
			cpuUsedItem.setxDataArr(new String[]{"wmware-1","wmware-2","OpenStack-1"});
			Serrie cpuSerrie1 = new Serrie();
			cpuSerrie1.setName("总量");
			cpuSerrie1.setValue(new String[]{"10","20","30"});
			
			Serrie cpuSerrie2 = new Serrie();
			cpuSerrie2.setName("已使用");
			cpuSerrie2.setValue(new String[]{"3","6","10"});
			
			cpuUsedItem.setyDataArr(new Serrie[]{cpuSerrie1, cpuSerrie2});
			analyzeMap.put("cpu", cpuUsedItem);
			
			
			ResChartItem vmUsedItem = new ResChartItem();
			vmUsedItem.setChartTitle("虚拟机使用情况");
			vmUsedItem.setTitleArr(new String[]{"总量","已使用"});
			vmUsedItem.setxDataArr(new String[]{"wmware-1","wmware-2","OpenStack-1"});
			Serrie vmserrie1 = new Serrie();
			vmserrie1.setName("总量");
			vmserrie1.setValue(new String[]{"10","20","30"});
			
			Serrie vmserrie2 = new Serrie();
			vmserrie2.setName("已使用");
			vmserrie2.setValue(new String[]{"3","6","10"});
			
			vmUsedItem.setyDataArr(new Serrie[]{vmserrie1, vmserrie2});
			analyzeMap.put("cpu", cpuUsedItem);
			analyzeMap.put("vm", vmUsedItem);
			return analyzeMap;
		}
	
}
