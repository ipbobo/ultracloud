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

import com.cmp.service.system.SystemNoticeService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 系统公告
 * @author liuweixing
 *
 */
@Controller
@RequestMapping(value="/systemnotice")
public class SystemNoticeController extends BaseController {
	
	String menuUrl = "system/list.do"; //菜单地址(权限用)
	
	@Resource(name="systemNoticeService")
	private SystemNoticeService systemNoticeService;
	
	@Resource(name="userService")
	private UserManager userService;
	/**发送站内信
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"发送系统公告");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限（站内信用独立的按钮权限,在此就不必校验新增权限）
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> pdList = new ArrayList<PageData>();
		
		List<PageData> touserList = userService.listAllUser(new PageData());
		
		String msg = "ok";		        //发送状态
		int count = 0;			        //统计发送成功条数
		int zcount = touserList.size();  //理论条数
		StringBuffer sb = new StringBuffer();
		
		try {
			pd.put("STATUS", "0");										//状态
			pd.put("TYPE", "1");
			for(PageData user : touserList) {
				if(!Jurisdiction.getUsername().equals(user.getString("USERNAME"))) {
					pd.put("SANME_ID", this.get32UUID());					//共同ID
					pd.put("SEND_TIME", DateUtil.getTime());				//发送时间
					pd.put("FHSMS_ID", this.get32UUID());					//主键1
					pd.put("FROM_USERNAME", Jurisdiction.getUsername());	//发信人
					pd.put("TO_USERNAME", user.getString("USERNAME"));		//收信人
					sb.append(user.getString("USERNAME")).append(",");
					systemNoticeService.save(pd);	                        //存入发信
					count++;
				}
			}
			msg = "ok";
		} catch (Exception e) {
			msg = "error";
		}
		pd.put("msg", msg);
		pd.put("count", count);						//成功数
		pd.put("ecount", 0);				//失败数
		pdList.add(pd);
		map.put("list", pdList);
		map.put("TO_USERNAME", sb.toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Fhsms");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		systemNoticeService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"列表Fhsms");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
		}
		if(!"2".equals(pd.getString("TYPE"))){					//1：收信箱 2：发信箱
			pd.put("TYPE", 1);
		}
		page.setPd(pd);	
		List<PageData>	varList = systemNoticeService.list(page);		//列出Fhsms列表
		mv.setViewName("system/systemnotice_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());				//按钮权限
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listSystemNotice")
	public ModelAndView listSystemNotice(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"列表Fhsms");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
		}
		if(!"2".equals(pd.getString("TYPE"))){					//1：收信箱 2：发信箱
			pd.put("TYPE", 1);
		}
		pd.put("TO_USERNAME", Jurisdiction.getUsername()); 	//当前用户名
		page.setPd(pd);	
		List<PageData>	varList = systemNoticeService.list(page);		//列出Fhsms列表
		mv.setViewName("system/systemnotice_list_windows");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());				//按钮权限
		return mv;
	}
	
	/**去发站内信界面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/systemnotice_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去查看页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goView")
	public ModelAndView goView()throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.getUsername().equals(pd.getString("TO_USERNAME")) && "0".equals(pd.getString("STATUS"))) { //如果是本人在读系统公告，则改变未读状态为已读
			systemNoticeService.edit(pd);
		}
		pd = systemNoticeService.findById(pd);	//根据ID读取
		mv.setViewName("system/systemnotice_view");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除SystemNotice");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			systemNoticeService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
