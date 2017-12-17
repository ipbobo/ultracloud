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
import com.cmp.entity.UserGroupUserMap;
import com.cmp.service.MirrorService;
import com.cmp.service.resourcemgt.DatacenterService;
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
		List<PageData> varList = mirrorService.list(page); // 列出列表
		mv.setViewName("service/mirror_list");
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

		List<PageData> dcList = datacenterService.listAll(pd);
		mv.addObject("dcList", dcList);

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

		List<PageData> dcList = datacenterService.listAll(pd);
		mv.addObject("dcList", dcList);

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

		List<PageData> notBindingList = mirrorService.listAllOutByMirrorId(pd);
		mv.addObject("notBindingList", notBindingList);
		List<PageData> bindedList = mirrorService.listAllInByMirrorId(pd);
		mv.addObject("bindedList", bindedList);

		pd = mirrorService.findById(pd); // 根据ID读取
		mv.setViewName("service/mirror_bindingtemplate");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 绑定镜像模板
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/bindingtemplate")
	@ResponseBody
	public Object bindingtemplate() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "绑定镜像模板");
		PageData pd = new PageData();
		pd = this.getPageData();
		BigInteger id = new BigInteger(pd.getString("id"));
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			List<MirrorTemplateMap> newList = new ArrayList<MirrorTemplateMap>();
			String ArrayDATA_IDS[] = DATA_IDS.split(",");

			List<MirrorTemplateMap> existList = mirrorService.listMirrorTemplateMap(pd);
			List<BigInteger> deleteIdList = new ArrayList<BigInteger>();
			if (null != existList && existList.size() > 0) {
				StringBuffer sb = new StringBuffer();

				for (MirrorTemplateMap mirrorTemplateMap : existList) {
					sb.append(mirrorTemplateMap.getMirrortemplate_id() + ",");
					if (!DATA_IDS.contains(mirrorTemplateMap.getMirrortemplate_id() + "")) {
						deleteIdList.add(mirrorTemplateMap.getId());
					}
				}

				for (int i = 0; i < ArrayDATA_IDS.length; i++) {
					if (!sb.toString().contains(ArrayDATA_IDS[i])) {
						MirrorTemplateMap mtMap = new MirrorTemplateMap();
						mtMap.setMirrortemplate_id(new BigInteger(ArrayDATA_IDS[i]));
						mtMap.setMirror_id(id);
						newList.add(mtMap);
					}
				}
			} else {
				for (int i = 0; i < ArrayDATA_IDS.length; i++) {
					MirrorTemplateMap mtMap = new MirrorTemplateMap();
					mtMap.setMirrortemplate_id(new BigInteger(ArrayDATA_IDS[i]));
					mtMap.setMirror_id(id);
					newList.add(mtMap);
				}
			}

			if (deleteIdList.size() > 0) {
				mirrorService.deleteAllMirrorTemplateMap(deleteIdList);
			}
			if (newList.size() > 0) {
				mirrorService.insertAllMirrorTemplateMap(newList);
			}
		} else {
			mirrorService.deleteByMirrorId(id);
		}

		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
}
