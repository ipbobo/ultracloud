package com.cmp.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.SnapshotService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/snapshot")
public class SnapshotController extends BaseController {

	String menuUrl = "snapshot/list.do";

	@Resource(name = "snapshotService")
	private SnapshotService snapshotService;

	@Resource(name = "userService")
	private UserService userService;

	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}

		//pd.put("FROM_USERNAME", Jurisdiction.getUsername());
		page.setPd(pd);
		List<PageData> varList = snapshotService.list(page);
		mv.setViewName("console/snapshot/snapshot");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);

		User userr = userService.getCurrrentUserAndRole();
		if (userr != null) {
			mv.addObject("QX", "audit".equalsIgnoreCase(userr.getRole().getTYPE()) ? 0 : 1);
		} else {
			mv.addObject("QX", 0);
		}

		return mv;
	}

	@RequestMapping(value = "/save")
	public @ResponseBody Object save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "发送站内信");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		// //校验权限（站内信用独立的按钮权限,在此就不必校验新增权限）
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> pdList = new ArrayList<PageData>();
		String msg = "ok"; // 发送状态
		int count = 0; // 统计发送成功条数
		int zcount = 0; // 理论条数
		String USERNAME = pd.getString("USERNAME"); // 对方用户名
		if (null != USERNAME && !"".equals(USERNAME)) {
			USERNAME = USERNAME.replaceAll("；", ";");
			USERNAME = USERNAME.replaceAll(" ", "");
			String[] arrUSERNAME = USERNAME.split(";");
			zcount = arrUSERNAME.length;
			try {
				pd.put("STATUS", "2"); // 状态
				for (int i = 0; i < arrUSERNAME.length; i++) {
					pd.put("SANME_ID", this.get32UUID()); // 共同ID
					pd.put("SEND_TIME", DateUtil.getTime()); // 发送时间
					pd.put("FHSMS_ID", this.get32UUID()); // 主键1
					pd.put("TYPE", "2"); // 类型2：发信
					pd.put("FROM_USERNAME", Jurisdiction.getUsername()); // 发信人
					pd.put("TO_USERNAME", arrUSERNAME[i]); // 收信人
					snapshotService.save(pd); // 存入发信
					pd.put("FHSMS_ID", this.get32UUID()); // 主键2
					pd.put("TYPE", "1"); // 类型1：收信
					pd.put("FROM_USERNAME", arrUSERNAME[i]); // 发信人
					pd.put("TO_USERNAME", Jurisdiction.getUsername()); // 收信人
					snapshotService.save(pd);
					count++;
				}
				msg = "ok";
			} catch (Exception e) {
				msg = "error";
			}
		} else {
			msg = "error";
		}
		pd.put("msg", msg);
		pd.put("count", count); // 成功数
		pd.put("ecount", zcount - count); // 失败数
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Fhsms");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		snapshotService.delete(pd);
		out.write("success");
		out.close();
	}

	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/fhsms/fhsms_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "/goView")
	public ModelAndView goView() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if ("1".equals(pd.getString("TYPE")) && "2".equals(pd.getString("STATUS"))) { // 在收信箱里面查看未读的站内信时去数据库改变未读状态为已读
			snapshotService.edit(pd);
		}
		pd = snapshotService.findById(pd); // 根据ID读取
		mv.setViewName("system/fhsms/fhsms_view");
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Fhsms");
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
			snapshotService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
