package com.cmp.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cmp.service.resourcemgt.HostmachineService;
import com.cmp.service.resourcemgt.VirtualMService;
import com.cmp.service.servicemgt.MirrorService;
import com.cmp.util.DateUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

/**
 * KVM主机 控制层
 */
@Controller
@RequestMapping(value = "/kvm")
public class KVMController extends BaseController {

	private static final String SUCCESS = "SUCCESS";
	private static final String FAILURE = "FAILURE";

	String menuUrl = "kvm/list.do"; // 菜单地址(权限用)

	@Resource(name = "hostmachineService")
	private HostmachineService hostmachineService;

	@Resource(name = "virtualMService")
	private VirtualMService virtualMService;

	/**
	 * 按类型查询列表
	 *
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表kvm");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		// 分页查询kvm主机
		// List<PageData> varList = hostmachineService.listKVM(page, false);
		mv.setViewName("resource/kvm_list");
		// mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@Resource(name = "mirrorService")
	private MirrorService mirrorService;

	/**
	 * 查询宿主机列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listHostmachine")
	public ModelAndView listHostmachine(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表kvm");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("type", "kvm");
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		// 分页查询kvm主机
		List<PageData> varList = hostmachineService.listKVM(page, false);
		mv.setViewName("resource/kvm_hostmachine_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 查询虚拟机列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listVirtual")
	public ModelAndView listVirtual(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表kvm");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("type", "kvm");
		String keywords = pd.getString("keywords");
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		List<PageData> varList = virtualMService.vmList(page);
		List<PageData> hostList = hostmachineService.listAllHostId();
		mv.setViewName("resource/kvm_virtual_list");
		mv.addObject("varList", varList);
		mv.addObject("hostList", hostList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC());

		return mv;
	}

	/**
	 * 查询模板列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listTemplate")
	public ModelAndView listTemplate(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表kvm");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("type", "kvm");
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		// 分页查询kvm主机
		List<PageData> varList = mirrorService.listTemplateByType(pd);
		mv.setViewName("resource/kvm_template_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
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
		logBefore(logger, Jurisdiction.getUsername() + "新增kvm");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("type", "kvm");
		pd.put("id", UuidUtil.get32UUID());
		hostmachineService.save(pd, false);
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
	public void delete(PrintWriter out) {
		logBefore(logger, Jurisdiction.getUsername() + "删除kvm");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			hostmachineService.delete(pd, false);
			out.write("success");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.write("failure");
			out.close();
		}
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改kvm");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("type", "kvm");
		hostmachineService.edit(pd, false);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
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

		mv.setViewName("resource/kvm_edit");
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

		pd = hostmachineService.findById(pd, false); // 根据ID读取
		mv.setViewName("resource/kvm_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除kvm");
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
			hostmachineService.deleteAll(ArrayDATA_IDS, false);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goListVirtualmachine")
	public ModelAndView goListVirtualmachine(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		List<PageData> varList = hostmachineService.listVirtual(page, false);
		mv.addObject("varList", varList);
		mv.setViewName("resource/virtual_list_windows");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * kvm数据同步
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/syncKVMData")
	public void syncKVMData(PrintWriter out) {
		logBefore(logger, Jurisdiction.getUsername() + "确认初始化kvm");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();

		// 同步kvm数据
		try {
			pd = hostmachineService.findById(pd, false);
			hostmachineService.syncKVMData(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			out.write("failure");
			out.close();
		}
	}

	@RequestMapping(value = "/createVm")
	public ResponseEntity<String> createVm(Reader reader) {
		try {
			Map map = JSON.parseObject(IOUtils.toString(reader));
			virtualMService.createVm(new PageData(map));
			return ok(SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}
	}

	@RequestMapping(value = "/startVm")
	public ResponseEntity<String> startVm(@RequestParam("ls[]") List<Integer> ls) {
		try {
			virtualMService.startVm(ls);
			return ok(SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}
	}

	@RequestMapping(value = "/stopVm")
	public ResponseEntity<String> stopVm(@RequestParam("ls[]") List<Integer> ls) {
		try {
			virtualMService.stopVm(ls);
			return ok(SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}
	}

	@RequestMapping(value = "/rebootVm")
	public ResponseEntity<String> rebootVm(@RequestParam("ls[]") List<Integer> ls) {
		try {
			virtualMService.rebootVm(ls);
			return ok(SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}
	}

	@RequestMapping(value = "/suspendVm")
	public ResponseEntity<String> suspendVm(@RequestParam("ls[]") List<Integer> ls) {
		try {
			virtualMService.suspendVm(ls);
			return ok(SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}
	}

	@RequestMapping(value = "/resumeVm")
	public ResponseEntity<String> resumeVm(@RequestParam("ls[]") List<Integer> ls) {
		try {
			virtualMService.resumeVm(ls);
			return ok(SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}
	}

	@RequestMapping(value = "/destroyVm")
	public ResponseEntity<String> destroyVm(@RequestParam("ls[]") List<Integer> ls) {
		try {
			virtualMService.destroyVm(ls);
			return ok(SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}
	}

	@RequestMapping(value = "/console")
	public ModelAndView console() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("console/cloudhost/console");

		return mv;
	}

}
