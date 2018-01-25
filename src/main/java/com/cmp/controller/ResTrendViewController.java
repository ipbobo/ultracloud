package com.cmp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
}
