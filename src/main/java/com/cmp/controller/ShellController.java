package com.cmp.controller;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmp.util.ShellUtil;
import com.fh.controller.base.BaseController;
import com.fh.util.PageData;

//SHELL执行查询
@Controller
public class ShellController extends BaseController{

	
	@RequestMapping(value="/queryShell")
	@ResponseBody
	public Object queryShell() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		LinkedList<String> currentShellMsg =  (LinkedList<String>) ShellUtil.getShellMsgMap().get(pd.get("shellId"));
		return currentShellMsg;
	}
	
}
