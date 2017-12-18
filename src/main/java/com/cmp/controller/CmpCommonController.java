package com.cmp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmp.sid.CmpDict;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;
import com.fh.dao.DaoSupport;

@Controller
public class CmpCommonController extends BaseController {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//数据列表查询
	@RequestMapping(value="/getDataList", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String getDataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			//String bizType=request.getParameter("bizType");//获取业务类型
			List<CmpDict> dataList=new ArrayList<CmpDict>();
			return StringUtil.getRetStr("0", "数据列表查询成功", "dataList", dataList);
		} catch (Exception e) {
	    	logger.error("数据列表查询时错误："+e);
	    	return StringUtil.getRetStr("-1", "数据列表查询时错误："+e);
	    }
	}
}