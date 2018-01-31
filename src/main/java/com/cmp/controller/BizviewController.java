package com.cmp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.BizviewService;
import com.cmp.service.CmpDictService;
import com.cmp.service.ProjectService;
import com.cmp.service.servicemgt.EnvironmentService;
import com.cmp.sid.CmpDict;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.fhoa.department.impl.DepartmentService;
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
	private DepartmentService departmentService;
	@Resource
	private ProjectService projectService;
	
	//业务视图总览列表查询
	@RequestMapping(value="/bizview/list")
	public ModelAndView getBizviewList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bizview/bizview_qry_list");
		return mv;
	}
	
	//计算列表查询
	@RequestMapping(value="/bizview/callist")
	public ModelAndView getCalList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String bizviewType=request.getParameter("bizviewType");//业务视图总览类型
		String subBizviewType=request.getParameter("subBizviewType");//子业务视图总览类型
		if(StringUtils.isBlank(bizviewType)){
			bizviewType="env";//默认环境
		}
		
		List<CmpDict> subBizviewTypeList=new ArrayList<CmpDict>();
		if("env".equals(bizviewType)){//环境
			subBizviewTypeList=environmentService.getEnvList();//环境列表
		}else if("dept".equals(bizviewType)){//部门
			subBizviewTypeList=departmentService.getDeptList();//部门列表
		}else if("proj".equals(bizviewType)){//项目
			subBizviewTypeList=projectService.getProjectList();//项目列表
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("bizviewType", bizviewType);//业务视图总览类型
		mv.addObject("subBizviewType", subBizviewType);//子业务视图总览类型
		mv.addObject("bizviewTypeList", cmpDictService.getCmpDictList("bizview_type"));//业务视图总览类型列表
		mv.addObject("subBizviewTypeList", subBizviewTypeList);//子业务视图总览类型列表
		mv.addObject("cmpRes", bizviewService.getCmpResDtl("cal", bizviewType, subBizviewType));//资源详细信息
		mv.setViewName("bizview/cal_qry_list");
		return mv;
	}
	
	//存储列表查询
	@RequestMapping(value="/bizview/storelist")
	public ModelAndView getStoreList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String bizviewType=request.getParameter("bizviewType");//业务视图总览类型
		String subBizviewType=request.getParameter("subBizviewType");//子业务视图总览类型
		if(StringUtils.isBlank(bizviewType)){
			bizviewType="env";//默认环境
		}
		
		List<CmpDict> subBizviewTypeList=new ArrayList<CmpDict>();
		if("env".equals(bizviewType)){//环境
			subBizviewTypeList=environmentService.getEnvList();//环境列表
		}else if("dept".equals(bizviewType)){//部门
			subBizviewTypeList=departmentService.getDeptList();//部门列表
		}else if("proj".equals(bizviewType)){//项目
			subBizviewTypeList=projectService.getProjectList();//项目列表
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("bizviewType", bizviewType);//业务视图总览类型
		mv.addObject("subBizviewType", subBizviewType);//子业务视图总览类型
		mv.addObject("bizviewTypeList", cmpDictService.getCmpDictList("bizview_type"));//业务视图总览类型列表
		mv.addObject("subBizviewTypeList", subBizviewTypeList);//子业务视图总览类型列表
		mv.addObject("cmpRes", bizviewService.getCmpResDtl("store", bizviewType, subBizviewType));//资源详细信息
		mv.setViewName("bizview/store_qry_list");
		return mv;
	}
	
	//云主机列表查询
	@RequestMapping(value="/bizview/cloudhostlist")
	public ModelAndView getCloudHostlist(HttpServletRequest request, HttpServletResponse response, Page page) throws Exception{
		String operType=request.getParameter("operType");//操作类型：cal-计算；store-存储
		String bizviewType=request.getParameter("bizviewType");//业务视图总览类型
		String subBizviewType=request.getParameter("subBizviewType");//子业务视图总览类型
		PageData pd=getPageData(page, "operType", operType, "bizviewType", bizviewType, "subBizviewType", subBizviewType);
		ModelAndView mv = new ModelAndView();
		mv.addObject("cloudHostList", bizviewService.getCloudHostPageList(page));//云主机列表分页查询
		mv.addObject("pd", pd);
		mv.setViewName("bizview/cloudhost_qry_list");
		return mv;
	}
}