package com.cmp.controller;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.entity.MirrorTemplateMap;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.service.ResourceService;
import com.cmp.service.resourcemgt.CloudplatformService;
import com.cmp.service.resourcemgt.DatacenterService;
import com.cmp.service.servicemgt.MirrorService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 镜像 控制层
 */
@Controller
@RequestMapping(value = "/mirror")
public class MirrorController extends BaseController {

	String menuUrl = "mirror/list.do"; // 菜单地址(权限用)

	@Resource(name = "mirrorService")
	private MirrorService mirrorService;

	@Resource(name = "datacenterService")
	private DatacenterService datacenterService;

	@Resource(name = "cloudplatformService")
	private CloudplatformService cloudplatformService;

	@Resource(name = "resourceService")
	private ResourceService resourceService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Mirror");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USERNAME", Jurisdiction.getUsername());
		mirrorService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除Mirror");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		mirrorService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改Mirror");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USERNAME", Jurisdiction.getUsername());
		mirrorService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表mirror");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		mv.setViewName("service/mirror_list");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listVmware")
	public ModelAndView listVmware(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表mirror");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = mirrorService.list(page); // 列出列表
		mv.setViewName("service/mirror_vmware_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listOpenStack")
	public ModelAndView listOpenStack(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表mirror");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		// ToDO

		mv.setViewName("service/mirror_openstack_list");
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

		List<PageData> cloudplatformList = cloudplatformService.listAll(pd, false);
		mv.addObject("cloudplatformList", cloudplatformList);

		mv.setViewName("service/mirror_edit");
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

		List<PageData> cloudplatformList = cloudplatformService.listAll(pd, false);
		mv.addObject("cloudplatformList", cloudplatformList);

		pd = mirrorService.findById(pd); // 根据ID读取
		mv.setViewName("service/mirror_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除mirror");
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
			mirrorService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 去绑定模板页
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goBindingtemplate")
	@ResponseBody
	public Object goBindingtemplate() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd = mirrorService.findById(pd); // 根据ID读取

		List<PageData> notBindList = mirrorService.listAllOutByMirrorId(pd); // 列出列表
		mv.addObject("notBindList", notBindList);
		
		List<PageData> alreadyBindList = mirrorService.listAllInByMirrorId(pd); // 列出列表
		mv.addObject("alreadyBindList", alreadyBindList);

		mv.setViewName("service/mirror_bindingtemplate");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 同步镜像模板
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/synctemplate")
	public void synctemplate(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "确认初始化Cloudplatform");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();

		out.write("success");
		out.close();
	}

	/**
	 * 同步镜像模板
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/goSynctemplate")
	public ModelAndView goSynctemplate() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "数据初始化mirror");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("type", "vmware");
		// 取出所有的wmware云平台
		List<PageData> cpList = cloudplatformService.listAll(pd, false);

		// 取出所有的wmware镜像模板
		List<PageData> dbVMTList = mirrorService.listTemplateByType(pd);
		Map<String, PageData> dbVMTMap = new HashMap<String, PageData>();
		List<PageData> existList = new ArrayList<PageData>();
		List<PageData> newList = new ArrayList<PageData>();
		if (null != dbVMTList) {
			for (PageData dbVMTPD : dbVMTList) {
				dbVMTPD.put("status", "exists");
				dbVMTMap.put(dbVMTPD.getString("cloudplatform_id") + "#" + dbVMTPD.getString("uuid"), dbVMTPD);
			}
		}

		if (null != cpList) {
			for (PageData cloudPD : cpList) {
				List<TccVirtualMachine> vmtList = resourceService.syncMirroTemplate(cloudPD);
				if (null != vmtList) {
					for (TccVirtualMachine tvm : vmtList) {
						if (dbVMTMap.keySet().contains(cloudPD.getString("id") + "#" + tvm.getUUID())) {
							existList.add(dbVMTMap.get(cloudPD.getString("id") + "#" + tvm.getUUID()));
						} else {
							PageData newPD = new PageData();
							newPD.put("cloudplatform_id", cloudPD.getString("id"));
							newPD.put("cloudplatform_name", cloudPD.getString("name"));
							newPD.put("name", tvm.getName());
							newPD.put("url", tvm.getXmlDesc());
							newPD.put("ostype", tvm.getOSType());
							newPD.put("uuid", tvm.getUUID());
							newPD.put("type", "vmware");
							newPD.put("status", "add");
							newPD.put("USERNAME", Jurisdiction.getUsername());
							newList.add(newPD);
						}
					}
				}
			}
		}

		for (PageData newPD : newList) {
			mirrorService.saveTemplate(newPD);
		}

		List<PageData> varList = new ArrayList<PageData>();
		varList.addAll(existList);
		varList.addAll(newList);

		mv.setViewName("service/mirror_vmware_syncinfo");
		mv.addObject("pd", pd);
		mv.addObject("varList", varList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 未绑定模板
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listNotbind")
	public ModelAndView listNotbind(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表mirrortempldate");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		List<PageData> varList = mirrorService.listAllOutByMirrorId(pd); // 列出列表
		mv.setViewName("service/mirror_bindingtemplate_notbind");

		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 已绑定模板
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listAlreadyBind")
	public ModelAndView listAlreadyBind(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表mirrortempldate");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		List<PageData> varList = mirrorService.listAllInByMirrorId(pd); // 列出列表
		mv.addObject("varList", varList);

		mv.setViewName("service/mirror_bindingtempldate_alreadybind");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 绑定镜像模板
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/bind")
	@ResponseBody
	public Object bind() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "绑定镜像模板");
		PageData pd = new PageData();
		pd = this.getPageData();
		String type = pd.getString("type");
		BigInteger id = new BigInteger(pd.getString("id"));
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			List<MirrorTemplateMap> newList = new ArrayList<MirrorTemplateMap>();
			List<BigInteger> idList = new ArrayList<BigInteger>();
			String ArrayDATA_IDS[] = DATA_IDS.split(",");

			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				MirrorTemplateMap mtMap = new MirrorTemplateMap();
				mtMap.setMirrortemplate_id(new BigInteger(ArrayDATA_IDS[i]));
				mtMap.setMirror_id(id);
				
				idList.add(new BigInteger(ArrayDATA_IDS[i]));
				newList.add(mtMap);
			}

			if (newList.size() > 0) {
				if("bind".equals(type)) {
					mirrorService.insertAllMirrorTemplateMap(newList);
				} else {
					mirrorService.deleteAllMirrorTemplateMap(idList);
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "success");
		return AppUtil.returnObject(pd, map);
	}
}
