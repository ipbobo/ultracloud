package com.cmp.controller;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.OrganizationService;
import com.fh.controller.base.BaseController;
import com.fh.util.PageData;

/**
 * 组织 控制层
 * 
 * @author liuweixing
 *
 */
@Controller
@RequestMapping(value = "/organization")
public class OrganizationController extends BaseController {

	@Resource(name = "organizationService")
	private OrganizationService organizationService;

	@RequestMapping(value = "/listAllOrganization")
	public ModelAndView listAllDepartment(Model model, String DEPARTMENT_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			JSONArray arr = JSONArray.fromObject(organizationService.listAllDepartment("0"));
			String json = arr.toString();
			json = json.replaceAll("DEPARTMENT_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name")
					.replaceAll("subDepartment", "nodes").replaceAll("hasDepartment", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);
			mv.addObject("pd", pd);
			mv.setViewName("permission/organization_ztree");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

}
