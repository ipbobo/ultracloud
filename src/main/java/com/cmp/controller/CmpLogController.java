package com.cmp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CmpLogService;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.PageData;

//日志管理
@Controller
public class CmpLogController extends BaseController {
	@Resource
	private CmpLogService cmpLogService;
	
	@RequestMapping(value="/log/list")
	public ModelAndView getLogList(Page page) throws Exception{
		PageData pd=getPageData(page, "applyUserId", StringUtil.getUserName());//登录用户
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", cmpLogService.getLogPageList(page));//日志列表分页查询
		mv.addObject("pd", pd);
		mv.setViewName("logmgr/log_qry_list");
		return mv;
	}
	
	//删除日志
	@RequestMapping(value="/log/deleteAll", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String deleteAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			String ids=request.getParameter("ids");//日志ID字符串
			cmpLogService.delCmpLog(ids);//删除日志
			return StringUtil.getRetStr("0", "删除日志成功");
		} catch (Exception e) {
	    	logger.error("删除日志时错误："+e);
	    	return StringUtil.getRetStr("-1", "删除日志时错误："+e);
	    }
	}
}