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

import com.cmp.entity.Area;
import com.cmp.service.servicemgt.AreaEnvironmentService;
import com.cmp.service.servicemgt.AreaService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 区域 控制层
 */
@Controller
@RequestMapping(value = "/area")
public class AreaController extends BaseController {

	String menuUrl = "area/list.do"; // 菜单地址(权限用)

	@Resource(name = "areaService")
	private AreaService areaService;

	@Resource(name = "areaEnvironmentService")
	private AreaEnvironmentService areaEnvironmentService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增area");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("id", this.get32UUID());

		areaService.save(pd);

		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				PageData aePD = new PageData();
				aePD.put("area_id", pd.getString("id"));
				aePD.put("environment_id", ArrayDATA_IDS[i]);
				areaEnvironmentService.save(aePD);
			}
		}

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
		logBefore(logger, Jurisdiction.getUsername() + "修改area");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		areaService.edit(pd);

		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			PageData delPD = new PageData();
			delPD.put("area_id", pd.getString("id"));
			areaEnvironmentService.delete(delPD);

			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				PageData aePD = new PageData();
				aePD.put("area_id", pd.getString("id"));
				aePD.put("environment_id", ArrayDATA_IDS[i]);
				areaEnvironmentService.save(aePD);
			}
		}

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
		logBefore(logger, Jurisdiction.getUsername() + "列表area");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		List<PageData> areaList = areaService.list(page); // 列出列表
		List<PageData> varList = new ArrayList<PageData>();
		Map<String, Area> map = new HashMap<String, Area>();
		if (null != areaList) {
			for (PageData areaPD : areaList) {
				Area area = map.get(areaPD.getString("name"));
				if (null == area) {
					area = new Area();
					area.setId(areaPD.getString("id"));
					area.setName(areaPD.getString("name"));
					area.setNum(1);
					area.setEnvironment_name(areaPD.getString("environment_name"));
				} else {
					area.setNum(area.getNum() + 1);
					area.setEnvironment_name(area.getEnvironment_name() + ", " + areaPD.getString("environment_name"));
				}

				map.put(areaPD.getString("name"), area);
			}
		}

		if (null != map) {
			for (Area area : map.values()) {
				PageData var = new PageData();
				var.put("id", area.getId());
				var.put("name", area.getName());
				var.put("num", area.getNum());
				var.put("environment_name", area.getEnvironment_name());

				varList.add(var);
			}
		}

		mv.addObject("varList", varList);

		mv.setViewName("service/area_list");
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

		List<PageData> notBindingList = areaService.listAllOutAreaByPdId(pd);
		mv.addObject("notBindingList", notBindingList);
		List<PageData> bindedList = areaService.listAllInByAreaId(pd);
		mv.addObject("bindedList", bindedList);

		mv.setViewName("service/area_edit");
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

		List<PageData> notBindingList = areaService.listAllOutAreaByPdId(pd);
		mv.addObject("notBindingList", notBindingList);
		List<PageData> bindedList = areaService.listAllInByAreaId(pd);
		mv.addObject("bindedList", bindedList);

		pd = areaService.findById(pd); // 根据ID读取
		mv.setViewName("service/area_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除area");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		areaService.delete(pd);
		out.write("success");
		out.close();
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除area");
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
			areaService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
}
