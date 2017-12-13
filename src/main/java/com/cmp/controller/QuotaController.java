package com.cmp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.ProjectService;
import com.cmp.sid.CmpOrder;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 配置管理
 * 
 * @author liuweixing
 *
 */
@Controller
@RequestMapping(value = "/quota")
public class QuotaController extends BaseController {

	@Resource(name = "dictionariesService")
	private DictionariesService dictionariesService;
	
	@Resource(name = "departmentService")
	private DepartmentManager departmentService;
	
	@Resource(name = "projectService")
	private ProjectService projectService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Model model, String DEPARTMENT_ID) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表quota");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 查询快照配额
		List<Dictionaries> dictionariesList = dictionariesService.listSubDictByBianma("snapshoot_maxun");
		mv.addObject("dictionariesList", dictionariesList);
		for (Dictionaries dictionaries : dictionariesList) {
			if ("snapshoot_manual_num".equals(dictionaries.getBIANMA())) {
				pd.put("snapshoot_manual_num", dictionaries.getNAME());
			} else if ("snapshoot_auto_num".equals(dictionaries.getBIANMA())) {
				pd.put("snapshoot_auto_num", dictionaries.getNAME());
			}
		}
		departmentService.listALLSubDepartmentByParentId("0");
		//查询部门树
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartment2("0"));
		String json = arr.toString();
		json = json.replaceAll("DEPARTMENT_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name")
				.replaceAll("subDepartment", "nodes").replaceAll("hasDepartment", "checked").replaceAll("treeurl", "url");
		model.addAttribute("zTreeNodes", json);
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);

		mv.setViewName("service/quota_list");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * 指定组织的所有子组织列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listALLSubDepartment")
	public ModelAndView listALLSubDepartment(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "子组织department");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID") ? "" : pd.get("DEPARTMENT_ID").toString();
		if (null != pd.get("id") && !"".equals(pd.get("id").toString())) {
			DEPARTMENT_ID = pd.get("id").toString();
		}
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID); // 上级ID
		page.setPd(pd);
		List<PageData> varList = departmentService.listALLSubDepartmentByParentId(DEPARTMENT_ID);
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID); // 上级ID
		mv.setViewName("service/quota_department_list");
		mv.addObject("varList", varList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/saveSnapshootMax")
	public ModelAndView saveSnapshootMax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			String snapshoot_manual_num = request.getParameter("snapshoot_manual_num");
			String snapshoot_auto_num = request.getParameter("snapshoot_auto_num");

			List<Dictionaries> dictionariesList = dictionariesService.listSubDictByBianma("snapshoot_maxun");
			for (Dictionaries dictionaries : dictionariesList) {
				if ("snapshoot_manual_num".equals(dictionaries.getBIANMA())) {
					dictionaries.setNAME(snapshoot_manual_num);
					dictionariesService.edit(dictionaries);
				} else if ("snapshoot_auto_num".equals(dictionaries.getBIANMA())) {
					dictionaries.setNAME(snapshoot_auto_num);
					dictionariesService.edit(dictionaries);
				}
			}

			mv.addObject("retMsg", "保存成功!");
		} catch (Exception e) {
			logger.error("保存快照配额错误：" + e);
			mv.addObject("retMsg", "保存失败!");
		}
		return mv;
	}
	
	/**
	 * 修改部门配额
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/editDepartmentQuota")
	public ModelAndView editDepartmentQuota() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改department");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		departmentService.editQuota(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改部门配额页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEditDepartmentQuota")
	public ModelAndView goEditDepartmentQuota() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DEPARTMENT_ID = pd.getString("DEPARTMENT_ID");
		pd = departmentService.findById(pd); // 根据ID读取
		mv.addObject("pd", pd); // 放入视图容器
		pd.put("DEPARTMENT_ID", pd.get("PARENT_ID").toString()); // 用作上级信息
		mv.addObject("pds", departmentService.findById(pd)); // 传入上级所有信息
		mv.addObject("DEPARTMENT_ID", pd.get("PARENT_ID").toString()); // 传入上级ID，作为子ID用
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID); // 复原本ID
		mv.setViewName("service/quota_department_edit");
		mv.addObject("msg", "editDepartmentQuota");
		return mv;
	}
	
	@RequestMapping(value = "/listProject")
	public ModelAndView listProject(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> projectList = projectService.list(page); // 列出Attached列表
		mv.setViewName("service/quota_project_list");
		mv.addObject("projectList", projectList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * 去修改项目配额页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEditProjectQuota")
	public ModelAndView goEditProjectQuota() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		pd = projectService.findById(pd); // 根据ID读取
		mv.addObject("pd", pd); // 放入视图容器
		mv.setViewName("service/quota_project_edit");
		mv.addObject("msg", "editProjectQuota");
		return mv;
	}
	
	/**
	 * 修改项目配额
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/editProjectQuota")
	public ModelAndView editProjectQuota() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改project");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		projectService.editQuota(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

}
