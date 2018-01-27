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

import com.cmp.sid.ResTrendItem;
import com.cmp.sid.Serrie;
import com.fh.controller.base.BaseController;

@RequestMapping(value="/restrend")
@Controller
public class ResTrendViewController extends BaseController {

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
			List<ResTrendItem> trendList = new ArrayList<ResTrendItem>();
			ResTrendItem trendItem = new ResTrendItem();
			trendItem.setChartTitle("CPU申请量(个)");
			trendItem.setTitleArr(new String[]{"分类1","分类2"});
			trendItem.setxDataArr(new String[]{"2018.1.22","2018.1.23","2018.1.24","2018.1.25"});
			Serrie serrie1 = new Serrie();
			serrie1.setName("分类1");
			serrie1.setValue(new String[]{"1","5","2","4"});
			
			Serrie serrie2 = new Serrie();
			serrie2.setName("分类2");
			serrie2.setValue(new String[]{"3","6","7","2"});
			
			trendItem.setyDataArr(new Serrie[]{serrie1, serrie2});
			trendList.add(trendItem);
			return trendList;
		}
	
}
