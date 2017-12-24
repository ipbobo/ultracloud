package com.cmp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.ProjectService;
import com.cmp.service.bi.BIDatacenterService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.system.user.impl.UserService;
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
		
		List<PageData> varList = biDatacenterService.listAllBillDay(pd);
		mv.setViewName("bi/bill_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
}
