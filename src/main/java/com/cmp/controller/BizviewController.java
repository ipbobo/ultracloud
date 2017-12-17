package com.cmp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.BizviewService;
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpLogService;
import com.cmp.service.servicemgt.EnvironmentService;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.PageData;

//业务视图总览
@Controller
public class BizviewController extends BaseController {
	@Resource
	private CmpDictService cmpDictService;
	@Resource
	private BizviewService bizviewService;
	@Resource
	private EnvironmentService environmentService;
	@Resource
	private CmpLogService cmpLogService;
	
	//业务视图总览列表查询
	@RequestMapping(value="/bizview/list")
	public ModelAndView getBizviewList(HttpServletRequest request, HttpServletResponse response, Page page) throws Exception{
		String bizviewType=request.getParameter("bizviewType");
		if(StringUtils.isBlank(bizviewType)){
			bizviewType="env";//默认环境
		}
		
		String subBizviewType=request.getParameter("subBizviewType");
		PageData pd=getPageData(page, "applyUserId", StringUtil.getUserId());
		ModelAndView mv = new ModelAndView();
		mv.addObject("bizviewTypeList", cmpDictService.getCmpDictList("bizview_type"));//业务视图总览类型列表
		mv.addObject("envCodeList", environmentService.getEnvList());//环境列表
		mv.addObject("cmpRes", bizviewService.getCmpResDtl(bizviewType, subBizviewType));//资源详细信息
		mv.addObject("cloudHostList", cmpLogService.getLogPageList(page));//日志列表分页查询
		mv.addObject("pd", pd);
		mv.setViewName("bizview/bizview_qry_list");
		return mv;
	}
}