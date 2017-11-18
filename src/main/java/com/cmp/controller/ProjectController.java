package com.cmp.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.ProjectService;
import com.cmp.service.UserGroupService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {

	String menuUrl = "project/list.do"; // 菜单地址(权限用)

	@Resource(name = "projectService")
	private ProjectService projectService;
	
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	
	@Resource(name="userGroupService")
	private UserGroupService userGroupService;
	
	@Resource(name="departmentService")
	private DepartmentManager departmentService;

	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Project");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = projectService.list(page); // 列出Attached列表
		mv.setViewName("project/project_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Project");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		projectService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改Project");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		projectService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//查询项目等级
		List<Dictionaries> dictionariesList = dictionariesService.listSubDictByBianma("project_type");
		mv.addObject("dictionariesList", dictionariesList);
		
		//查询所有审核角色类型的用户组
		Page page = this.getPage();
		PageData pagedata = page.getPd();
		pagedata.put("type", "audit");
		List<PageData>	usergroupList = userGroupService.list(page);	
		mv.addObject("usergroupList", usergroupList);
		
		//查询部门树
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect(Jurisdiction.getDEPARTMENT_ID(),zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		
		mv.setViewName("project/project_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = projectService.findById(pd); // 根据ID读取
		
		//查询项目等级
		List<Dictionaries> dictionariesList = dictionariesService.listSubDictByBianma("project_type");
		mv.addObject("dictionariesList", dictionariesList);
				
		//查询所有审核角色类型的用户组
		Page page = this.getPage();
		PageData pagedata = page.getPd();
		pagedata.put("type", "audit");
		List<PageData>	usergroupList = userGroupService.list(page);	
		mv.addObject("usergroupList", usergroupList);
				
		//查询部门树
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect(Jurisdiction.getDEPARTMENT_ID(),zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		mv.addObject("depname", departmentService.findById(pd).getString("NAME"));
		
		mv.setViewName("project/project_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"删除Project");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		projectService.delete(pd);
		out.write("success");
		out.close();
	}

	@RequestMapping(value = "/deleteAll")
	public @ResponseBody Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Project");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			projectService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
