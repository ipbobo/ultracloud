package com.cmp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CloudHostService;
import com.cmp.service.CmpDictService;
import com.cmp.service.ProjectService;
import com.cmp.service.ResviewService;
import com.cmp.service.resourcemgt.ClusterService;
import com.cmp.service.resourcemgt.DatacenterService;
import com.cmp.service.servicemgt.EnvironmentService;
import com.cmp.sid.CmpDict;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.util.PageData;

//业务视图总览
@Controller
public class ResviewController extends BaseController {
	@Resource
	private CmpDictService cmpDictService;
	@Resource
	private ResviewService resviewService;
	@Resource
	private EnvironmentService environmentService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private ProjectService projectService;
	@Resource
	private DatacenterService datacenterService;
	@Resource
	private ClusterService clusterService;
	@Resource
	private CloudHostService cloudHostService;
	
	//业务视图总览列表查询
	@RequestMapping(value="/resview/list")
	public ModelAndView getBizviewList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("resview/resview_qry_list");
		return mv;
	}
	
	//计算列表查询
	@RequestMapping(value="/resview/callist")
	public ModelAndView getCalList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String bizviewType=request.getParameter("bizviewType");//业务视图总览类型
		String subBizviewType=request.getParameter("subBizviewType");//子业务视图总览类型
		List<CmpDict> bizviewTypeList=datacenterService.getDataCenterList();//数据中心列表
		if(bizviewTypeList!=null && !bizviewTypeList.isEmpty()){
			CmpDict cmpDict=bizviewTypeList.get(0);//第一项
			if(StringUtils.isBlank(bizviewType)){
				bizviewType=cmpDict.getDictCode();//默认数据中心
			}
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("bizviewType", bizviewType);//业务视图总览类型
		mv.addObject("subBizviewType", subBizviewType);//子业务视图总览类型
		mv.addObject("bizviewTypeList", bizviewTypeList);//业务视图总览类型列表
		mv.addObject("subBizviewTypeList", clusterService.getClusterList(bizviewType));//子业务视图总览类型列表
		mv.addObject("cmpRes", resviewService.getCmpResDtl("cal", bizviewType, subBizviewType));//资源详细信息
		mv.setViewName("resview/cal_qry_list");
		return mv;
	}
	
	//存储列表查询
	@RequestMapping(value="/resview/storelist")
	public ModelAndView getStoreList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String bizviewType=request.getParameter("bizviewType");//业务视图总览类型
		String subBizviewType=request.getParameter("subBizviewType");//子业务视图总览类型
		List<CmpDict> bizviewTypeList=datacenterService.getDataCenterList();//数据中心列表
		if(bizviewTypeList!=null && !bizviewTypeList.isEmpty()){
			CmpDict cmpDict=bizviewTypeList.get(0);//第一项
			if(StringUtils.isBlank(bizviewType)){
				bizviewType=cmpDict.getDictCode();//默认数据中心
			}
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("bizviewType", bizviewType);//业务视图总览类型
		mv.addObject("subBizviewType", subBizviewType);//子业务视图总览类型
		mv.addObject("bizviewTypeList", bizviewTypeList);//业务视图总览类型列表
		mv.addObject("subBizviewTypeList", clusterService.getClusterList(bizviewType));//子业务视图总览类型列表
		mv.addObject("cmpRes", resviewService.getCmpResDtl("store", bizviewType, subBizviewType));//资源详细信息
		mv.setViewName("resview/store_qry_list");
		return mv;
	}
	
	//物理机列表查询
	@RequestMapping(value="/resview/hostlist")
	public ModelAndView getHostlist(HttpServletRequest request, HttpServletResponse response, Page page) throws Exception{
		String bizviewType=request.getParameter("bizviewType");//业务视图总览类型
		String subBizviewType=request.getParameter("subBizviewType");//子业务视图总览类型
		PageData pd=getPageData(page, "bizviewType", bizviewType, "subBizviewType", subBizviewType);
		ModelAndView mv = new ModelAndView();
		mv.addObject("hostList", resviewService.getHostPageList(page));//云主机列表分页查询
		mv.addObject("pd", pd);
		mv.setViewName("resview/host_qry_list");
		return mv;
	}
}