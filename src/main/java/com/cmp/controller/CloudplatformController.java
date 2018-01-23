package com.cmp.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.ResourceService;
import com.cmp.service.StorageService;
import com.cmp.service.resourcemgt.CloudplatformService;
import com.cmp.service.resourcemgt.ClusterService;
import com.cmp.service.resourcemgt.DatacenterService;
import com.cmp.service.resourcemgt.DatacenternetworkService;
import com.cmp.service.resourcemgt.HostmachineService;
import com.cmp.util.DateUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 云平台 控制层
 */
@Controller
@RequestMapping(value = "/cloudplatform")
public class CloudplatformController extends BaseController {

	String menuUrl = "cloudplatform/list.do"; // 菜单地址(权限用)

	@Resource(name = "cloudplatformService")
	private CloudplatformService cloudplatformService;

	@Resource(name = "resourceService")
	private ResourceService resourceService;

	@Resource(name = "datacenterService")
	private DatacenterService datacenterService;
	
	@Resource(name = "clusterService")
	private ClusterService clusterService;

	@Resource(name = "hostmachineService")
	private HostmachineService hostmachineService;

	@Resource(name = "storageService")
	private StorageService storageService;

	@Resource(name = "datacenternetworkService")
	private DatacenternetworkService datacenternetworkService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Cloudplatform");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("id", this.get32UUID());
		cloudplatformService.save(pd, false);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除Cloudplatform");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		cloudplatformService.delete(pd, false);
		out.write("success");
		out.close();
	}
	
	/**
	 * 确认初始化
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/init")
	public void init(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "确认初始化Cloudplatform");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd = cloudplatformService.findById(pd, false);
		cloudplatformService.delete(pd, true);
		pd.put("version", DateUtil.dateToString(new Date(), DateUtil.TIMESTAMP_FORMAT));
		cloudplatformService.save(pd, true);
		
		out.write("success");
		out.close();
	}
	
	

	/**
	 * 跳云平台数据初始化页面
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/goInit")
	public ModelAndView goInit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "数据初始化Cloudplatform");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String cpf_id = pd.getString("id");
		pd = cloudplatformService.findById(pd, true);
		
		// 同步云平台数据 
		resourceService.syncCloudData(pd);

		// 查询所有宿主机记录
		List<PageData> hmList = hostmachineService.listAll(pd, true);
		// 查询所有存储记录
		List<PageData> storageList = storageService.listAll(pd, true);
		// 查询所有分配的网络记录
		List<PageData> dcnList = datacenternetworkService.listAll(pd, true);

		mv.addObject("hmList", hmList);
		mv.addObject("storageList", storageList);
		mv.addObject("dcnList", dcnList);
		mv.setViewName("resource/cloudplatform_init");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	
	/**
	 * 更新同步数据为选中并复制到正式表中
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSelectData")
	public ModelAndView updateSelectData() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Cloudplatform");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String hostmachineIds = pd.getString("hostmachineIds");
		String storageIds = pd.getString("storageIds");
		String dcnIds = pd.getString("dcnIds");
		
		resourceService.updateSelectData(hostmachineIds, storageIds, dcnIds);
		
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改Cloudplatform");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		cloudplatformService.edit(pd, false);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表Cloudplatform");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		mv.setViewName("resource/cloudplatform_list");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * 按类型查询列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listType")
	public ModelAndView listType(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Cloudplatform");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = cloudplatformService.list(page, false); // 列出列表
		if("vmware".equals(pd.getString("type"))) {
			mv.setViewName("resource/cloudplatform_vmware_list");
		} else {
			mv.setViewName("resource/cloudplatform_openstack_list");
		}
		mv.addObject("varList", varList);
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
		mv.setViewName("resource/cloudplatform_edit");
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
		pd = cloudplatformService.findById(pd, false); // 根据ID读取
		mv.setViewName("resource/cloudplatform_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Cloudplatform");
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
			cloudplatformService.deleteAll(ArrayDATA_IDS, false);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
}
