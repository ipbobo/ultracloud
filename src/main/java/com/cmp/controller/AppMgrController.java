package com.cmp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CmpDictService;
import com.cmp.sid.CmpDict;
import com.fh.controller.base.BaseController;

//申请管理
@Controller
public class AppMgrController extends BaseController {
	@Resource
	private CmpDictService cmpDictService;
	
	//资源申请预查询
	@RequestMapping(value="/appMgrPre")
	public ModelAndView appMgrPre() throws Exception{
		List<CmpDict> platTypeList=cmpDictService.getCmpDictList("plat_type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("platTypeList", platTypeList);//数据字典列表
		mv.setViewName("appmgr/resapp_qry_input");
		return mv;
	}
}