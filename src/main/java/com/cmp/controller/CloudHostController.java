package com.cmp.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CloudHostService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/cloudhost")
public class CloudHostController extends BaseController {

	String menuUrl = "cloudhost/list.do";

	@Resource(name = "cloudHostService")
	private CloudHostService cloudHostService;

	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart"); // 开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd"); // 结束时间
		if (lastLoginStart != null && !"".equals(lastLoginStart)) {
			pd.put("lastLoginStart", lastLoginStart + " 00:00:00");
		}
		if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
			pd.put("lastLoginEnd", lastLoginEnd + " 00:00:00");
		}
		if (!"2".equals(pd.getString("TYPE"))) { // 1：收信箱 2：发信箱
			pd.put("TYPE", 1);
		}
		pd.put("FROM_USERNAME", Jurisdiction.getUsername()); // 当前用户名
		page.setPd(pd);
		List<PageData> varList = cloudHostService.list(page); // 列出Fhsms列表
		mv.setViewName("console/cloudhost/cloud_host_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限

		return mv;
	}

	@RequestMapping(value = "/start")
	public ResponseEntity<String> start(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.start(ls);
		} catch (Exception e) {
			return ok("FAILURE");
		}

		return ok("SUCCESS");
	}

	@RequestMapping(value = "/stop")
	public ResponseEntity<String> stop(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.stop(ls);
		} catch (Exception e) {
			return ok("FAILURE");
		}

		return ok("SUCCESS");
	}

	@RequestMapping(value = "/restart")
	public ResponseEntity<String> restart(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.restart(ls);
		} catch (Exception e) {
			return ok("FAILURE");
		}

		return ok("SUCCESS");
	}

	@RequestMapping(value = "/suspend")
	public ResponseEntity<String> suspend(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.suspend(ls);
		} catch (Exception e) {
			return ok("FAILURE");
		}

		return ok("SUCCESS");
	}

	@RequestMapping(value = "/resume")
	public ResponseEntity<String> resume(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.resume(ls);
		} catch (Exception e) {
			return ok("FAILURE");
		}

		return ok("SUCCESS");
	}

	@RequestMapping(value = "/destroy")
	public ResponseEntity<String> destroy(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.destroy(ls);
		} catch (Exception e) {
			return ok("FAILURE");
		}

		return ok("SUCCESS");
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
