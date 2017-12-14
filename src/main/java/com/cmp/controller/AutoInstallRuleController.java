package com.cmp.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.ClusterService;
import com.cmp.service.servicemgt.AutoInstallRuleService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 自动安装规则 控制层
 */
@Controller
@RequestMapping(value = "/autoinstallrule")
public class AutoInstallRuleController extends BaseController {

	String menuUrl = "autoinstallrule/list.do"; // 菜单地址(权限用)

	@Resource(name = "autoInstallRuleService")
	private AutoInstallRuleService autoInstallRuleService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dictionariesService;
	
	@Resource(name = "clusterService")
	private ClusterService clusterService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增autoinstallrule");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USERNAME", Jurisdiction.getUsername());
		autoInstallRuleService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除autoinstallrule");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		autoInstallRuleService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改autoinstallrule");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USERNAME", Jurisdiction.getUsername());
		autoInstallRuleService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表autoinstallrule");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		mv.setViewName("service/autoinstallrule_list");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * Vmware集群列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listVmware")
	public ModelAndView listVmware(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表autoinstallrule");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		pd.put("type", "vmware");
		page.setPd(pd);
		
		List<PageData> varList = autoInstallRuleService.list(page); // 列出列表
		mv.addObject("varList", varList);
		
		List<PageData> clusterList = clusterService.listAll(pd);
		mv.addObject("clusterList", clusterList);
		
		mv.setViewName("service/autoinstallrule_vmware_list");
		
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * Openstack集群列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listOpenStack")
	public ModelAndView listOpenStack(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表autoinstallrule");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		pd.put("type", "OpenStack");
		page.setPd(pd);
		List<PageData> varList = autoInstallRuleService.list(page); // 列出列表
		mv.addObject("varList", varList);
		
		List<PageData> clusterList = clusterService.listAll(pd);
		mv.addObject("clusterList", clusterList);
		
		mv.setViewName("service/autoinstallrule_openstack_list");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		
		List<Dictionaries> subDictionariesList = dictionariesService.listSubDictByBianma("autoinstall_rule");
		List<Dictionaries> grandsonDictionariesList = dictionariesService.listGrandsonDictByBianma("autoinstall_rule");
		for(Dictionaries subD : subDictionariesList) {
			List<Dictionaries> realDList = new ArrayList<Dictionaries>();
			for(Dictionaries grandsonD : grandsonDictionariesList) {
				if(grandsonD.getPARENT_ID().equals(subD.getDICTIONARIES_ID())) {
					realDList.add(grandsonD);
				}
			}
			mv.addObject(subD.getBIANMA() + "_List", realDList);
		}
		
		List<PageData> clusterList = clusterService.listAll(pd);
		mv.addObject("clusterList", clusterList);

		mv.setViewName("service/autoinstallrule_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		
		List<Dictionaries> subDictionariesList = dictionariesService.listSubDictByBianma("autoinstall_rule");
		List<Dictionaries> grandsonDictionariesList = dictionariesService.listGrandsonDictByBianma("autoinstall_rule");
		for(Dictionaries subD : subDictionariesList) {
			List<Dictionaries> realDList = new ArrayList<Dictionaries>();
			for(Dictionaries grandsonD : grandsonDictionariesList) {
				if(grandsonD.getPARENT_ID().equals(subD.getDICTIONARIES_ID())) {
					realDList.add(grandsonD);
				}
			}
			mv.addObject(subD.getBIANMA() + "List", realDList);
		}
		
		List<PageData> clusterList = clusterService.listAll(pd);
		mv.addObject("clusterList", clusterList);
		
		pd = autoInstallRuleService.findById(pd); // 根据ID读取
		mv.setViewName("service/autoinstallrule_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除autoInstallRule");
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
			autoInstallRuleService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
}
