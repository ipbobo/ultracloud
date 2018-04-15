package com.cmp.controller;

import java.util.ArrayList;
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
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
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
		List<PageData> varList = datacenternetworkService.list(page, false);
		mv.setViewName("resource/datacenternetwork_type_list");
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
		
		List<PageData> varList = datacenterService.listAll(pd);
		mv.addObject("varList", varList);
		
		mv.setViewName("resource/datacenternetwork_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Datacenternetwork");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("id", this.get32UUID());
		pd.put("uuid", pd.getString("id"));
		
		//设置云平台id
		String datacenterId = pd.getString("datacenter_id");
		PageData dcPD = new PageData();
		dcPD.put("id", datacenterId);
		dcPD = datacenterService.findById(dcPD);
		pd.put("cpf_id", dcPD.getString("cpf_id"));
		
		datacenternetworkService.save(pd, false);
		
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
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
		
		pd = datacenternetworkService.findById(pd, false);
		
		PageData cloudPD = new PageData();
		cloudPD.put("id", pd.getString("cpf_id"));
		List<PageData> varList = datacenterService.listAll(cloudPD);
		mv.addObject("varList", varList);
		
		//List<PageData> clusterList = clusterService.listAll(pd);
		//mv.addObject("clusterList", clusterList);
		
		PageData dcPD = new PageData();
		dcPD.put("datacenter_id", pd.getString("datacenter_id"));
		List<PageData> networkList =  datacenternetworkService.findLabelByDatacenterId(dcPD);
		mv.addObject("networkList", networkList);
		
		mv.setViewName("resource/datacenternetwork_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"修改DataCenterNetwork");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		datacenternetworkService.edit(pd, false);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 数据中心选择网络
	 * @param serviceType
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/toNetworkQuery")
	@ResponseBody
	public List<PageData> toNetworkQuery(String dataCenterId) throws Exception{
		if (dataCenterId == null || dataCenterId.length() == 0) {
			return null;
		}
		PageData pd = new PageData();
		pd.put("datacenter_id", dataCenterId);
		List<PageData> networkList =  datacenternetworkService.findLabelByDatacenterId(pd);
		return networkList;
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除datacenternetwork");
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
			datacenternetworkService.deleteAll(ArrayDATA_IDS, false);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

}
