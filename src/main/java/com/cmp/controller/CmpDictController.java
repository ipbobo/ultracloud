package com.cmp.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmp.service.CmpDictService;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;

//申请管理
@Controller
public class CmpDictController extends BaseController {
	@Resource
	private CmpDictService cmpDictService;
	
	//资源申请预查询
	@RequestMapping(value="/reloadCache", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String reloadCache() throws Exception{
		cmpDictService.reloadCache();//重新加载缓存
		return StringUtil.getRetStr("0", "调用成功");
	}
}