package com.cmp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.MediumService;
import com.cmp.service.ProjectService;
import com.cmp.service.bi.BIDatacenterService;
import com.cmp.service.bi.BiBillDayService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 报表中心 控制层
 * 
 * @author liuweixing
 *
 */
@Controller
@RequestMapping(value = "/bidatacenter")
public class BIDatacenterController extends BaseController {
	
	@Resource(name = "biDatacenterService")
	private BIDatacenterService biDatacenterService;

	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	@Resource(name = "projectService")
	private ProjectService projectService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "mediumService")
	private MediumService mediumService;
	
	@Resource(name = "biBillDayService")
	private BiBillDayService biBillDayService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dictionariesService;
	
	/**
	 * 计费报表列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listBillDay")
	public ModelAndView listBillDay(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表BillDay");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		//查询项目列表
		List<PageData>	projectList = projectService.listAll(pd);
		mv.addObject("projectList", projectList);
		
		//查询部门树
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		
		//查询用户列表
		List<PageData> userList = userService.listAllUser(pd);
		mv.addObject("userList", userList);
		
		List<PageData> varList = biDatacenterService.listBillBIData(pd);
		mv.setViewName("bi/bill_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * 资源报表列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listResource")
	public ModelAndView listResource(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表ResourceBI");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		//查询项目列表
		List<PageData>	projectList = projectService.listAll(pd);
		mv.addObject("projectList", projectList);
		
		//查询部门树
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		
		//查询用户列表
		List<PageData> userList = userService.listAllUser(pd);
		mv.addObject("userList", userList);
		
		
		String date = pd.getString("date");
		if(null == date) {
			pd.put("date", DateUtil.getDay());
		}
		List<PageData> varList = biDatacenterService.listResourceBIData(pd);
		mv.setViewName("bi/resource_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * 软件台帐报表列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listSoftware")
	public ModelAndView listSoftware(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Software");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		//查询项目列表
		List<PageData>	projectList = projectService.listAll(pd);
		mv.addObject("projectList", projectList);
		
		//查询部门树
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		
		//查询用户列表
		List<PageData> userList = userService.listAllUser(pd);
		mv.addObject("userList", userList);
		
		//查询软件类型列表
		List<PageData> softTypeList = mediumService.listAllSoftType(pd);
		mv.addObject("softTypeList", softTypeList);
		
		String date = pd.getString("date");
		if(null == date) {
			pd.put("date", DateUtil.getDay());
		}
		List<PageData> varList = biDatacenterService.listSoftwareBIData(pd);
		mv.setViewName("bi/resource_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * 云主机资产台帐报表列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listVirtual")
	public ModelAndView listVirtual(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Virtual");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		//查询项目列表
		List<PageData>	projectList = projectService.listAll(pd);
		mv.addObject("projectList", projectList);
		
		//查询部门树
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		
		//查询用户列表
		List<PageData> userList = userService.listAllUser(pd);
		mv.addObject("userList", userList);
		
		//查询虚拟机状态列表
		List<Dictionaries> dictionariesList = dictionariesService.listSubDictByBianma("virtual_status");
		mv.addObject("dictionariesList", dictionariesList);
		
		List<PageData> varList = biBillDayService.listVBiVirtualBill(pd);
		mv.setViewName("bi/virtual_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
}
