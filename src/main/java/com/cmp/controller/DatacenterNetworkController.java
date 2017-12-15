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
 * 数据中心网络 控制层
 */
@Controller
@RequestMapping(value = "/datacenternetwork")
public class DatacenterNetworkController extends BaseController {

	String menuUrl = "datacenternetwork/list.do"; // 菜单地址(权限用)

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
		logBefore(logger, Jurisdiction.getUsername() + "列表Datacenternetwork");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		mv.setViewName("resource/datacenternetwork_list");
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
		logBefore(logger, Jurisdiction.getUsername() + "列表Datacenternetwork");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		// 分页查询宿主机
		List<PageData> varList = datacenternetworkService.list(page);
		mv.setViewName("resource/datacenternetwork_type_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

}
