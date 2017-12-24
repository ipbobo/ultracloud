package com.cmp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.entity.ShellMessage;
import com.cmp.util.ShellUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.PageData;

//SHELL执行查询
@Controller
public class ShellController extends BaseController{

	
	@RequestMapping(value="/queryShell")
	@ResponseBody
	public Object queryShell() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String , Object> resultMap = new HashMap<String , Object>();
		Map<Integer, String> currentShellMsg =  (Map<Integer, String>) ShellUtil.getShellMsgMap().get(pd.get("shellId"));
		int currentLine = Integer.valueOf((pd.get("currentLine") != null ? String.valueOf(pd.get("currentLine")): "0"));
//		if (currentLine != 0) {
//			for (int i = 1; i < currentLine; i++) {
//				currentShellMsg.remove(i);
//			}
//		}
		resultMap.put("currentShellMsg", currentShellMsg);
		resultMap.put("length", currentShellMsg.size());	
		return resultMap;
	}
}
