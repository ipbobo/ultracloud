package com.cmp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CloudplatformService;
import com.cmp.service.ClusterService;
import com.cmp.service.DatacenterService;
import com.cmp.service.DatacenternetworkService;
import com.cmp.service.HostmachineService;
import com.cmp.service.ResourceService;
import com.cmp.service.StorageService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 宿主机 控制层
 */
@Controller
@RequestMapping(value = "/hostmachine")
public class HostmachineController extends BaseController {

	String menuUrl = "hostmachine/list.do"; // 菜单地址(权限用)

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
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Hostmachine");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		mv.setViewName("resource/hostmachine_list");
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
		logBefore(logger, Jurisdiction.getUsername() + "列表Hostmachine");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		pd.put("type", "vmware");
		page.setPd(pd);
		// 分页查询宿主机
		List<PageData> varList = hostmachineService.list(page);
		mv.setViewName("resource/hostmachine_vmware_list");
		mv.addObject("varList", varList);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表Cloudplatform");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		pd.put("type", "OpenStack");
		page.setPd(pd);
		// 分页查询宿主机
		List<PageData> varList = hostmachineService.list(page);
		mv.setViewName("resource/hostmachine_openstack_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
}
