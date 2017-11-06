package com.cmp.controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.OrganizationService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.Jurisdiction;
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
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表organization");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");					//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			DEPARTMENT_ID = pd.get("id").toString();
		}
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = organizationService.list(page);	//列出Dictionaries列表
		mv.addObject("pd", organizationService.findById(pd));		//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);			//上级ID
		mv.setViewName("permission/organization_list");
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());				//按钮权限
		return mv;
	}

}
