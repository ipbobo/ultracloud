package com.cmp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.sid.CmpOrder;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
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

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表quota");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);

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

		mv.setViewName("service/quota_list");
		mv.addObject("pd", pd);
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
}
