package com.cmp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CmpDictService;
import com.cmp.sid.CmpDict;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;

//申请管理
@Controller
public class AppMgrController extends BaseController {
	@Resource
	private CmpDictService cmpDictService;
	
	//资源申请预查询
	@RequestMapping(value="/appMgrPre")
	public ModelAndView appMgrPre() throws Exception{
		List<CmpDict> areaCodeList=cmpDictService.getCmpDictList("area_code");//数据字典列表查询
		List<CmpDict> platTypeList=cmpDictService.getCmpDictList("plat_type");//数据字典列表查询
		List<CmpDict> deployTypeList=cmpDictService.getCmpDictList("deploy_type");//数据字典列表查询
		List<CmpDict> envCodeList=cmpDictService.getCmpDictList("env_code");//数据字典列表查询
		List<CmpDict> projectList=cmpDictService.getCmpDictList("project");//数据字典列表查询
		List<CmpDict> resTypeList=cmpDictService.getCmpDictList("res_type");//数据字典列表查询
		List<CmpDict> recommendTypeList=cmpDictService.getCmpDictList("recommend_type");//数据字典列表查询
		List<CmpDict> cpuList=cmpDictService.getCmpDictList("cpu");//数据字典列表查询
		List<CmpDict> memoryList=cmpDictService.getCmpDictList("memory");//数据字典列表查询
		List<CmpDict> osTypeList=cmpDictService.getCmpDictList("os_type");//数据字典列表查询
		List<CmpDict> modelList=cmpDictService.getCmpDictList("model");//数据字典列表查询
		List<CmpDict> diskTypeList=cmpDictService.getCmpDictList("disk_type");//数据字典列表查询
		List<CmpDict> diskSizeList=cmpDictService.getCmpDictList("disk_size");//数据字典列表查询
		List<CmpDict> softNameList=cmpDictService.getCmpDictList("soft_name");//数据字典列表查询
		List<CmpDict> softVerList=cmpDictService.getCmpDictList("soft_ver");//数据字典列表查询
		ModelAndView mv = new ModelAndView();
		mv.addObject("areaCodeList", areaCodeList);//区域列表
		mv.addObject("platTypeList", platTypeList);//平台类型列表
		mv.addObject("deployTypeList", deployTypeList);//部署类型列表
		mv.addObject("envCodeList", envCodeList);//环境列表
		mv.addObject("projectList", projectList);//项目列表
		mv.addObject("resTypeList", resTypeList);//资源类型列表
		mv.addObject("recommendTypeList", recommendTypeList);//推荐配置列表
		mv.addObject("cpuList", cpuList);//CPU列表
		mv.addObject("memoryList", memoryList);//内存列表
		
		mv.addObject("osTypeList", osTypeList);//OS类型列表
		mv.addObject("modelList", modelList);//模板列表
		mv.addObject("diskTypeList", diskTypeList);//磁盘类型列表
		mv.addObject("diskSizeList", diskSizeList);//磁盘大小列表
		mv.addObject("softNameList", softNameList);//软件名称列表
		mv.addObject("softVerList", softVerList);//软件版本列表
		mv.setViewName("appmgr/resapp_qry_input");
		return mv;
	}
	
	@RequestMapping(value="/getDictList", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String getDataDiskTypeList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String operType=request.getParameter("operType");//操作类型：disk-磁盘；soft-软件安装
		if("disk".equals(operType)){//操作类型：disk-磁盘；soft-软件安装
			List<CmpDict> dataList=cmpDictService.getCmpDictList("disk_type");//数据字典列表查询
			List<CmpDict> subDataList=cmpDictService.getCmpDictList("disk_size");//数据字典列表查询
			return StringUtil.getRetStr("0", "调用成功", "dataList", dataList, "subDataList", subDataList);
		}else if("soft".equals(operType)){//操作类型：disk-磁盘；soft-软件安装
			List<CmpDict> dataList=cmpDictService.getCmpDictList("soft_name");//数据字典列表查询
			List<CmpDict> subDataList=cmpDictService.getCmpDictList("soft_ver");//数据字典列表查询
			return StringUtil.getRetStr("0", "调用成功", "dataList", dataList, "subDataList", subDataList);
		}
		
		return StringUtil.getRetStr("-", "调用失败：不支持的操作类型");
	}
	
	//加入清单
	@RequestMapping(value="/addList")
	public ModelAndView addList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, String> paramMap=getParam(request);//获取参数Map
		String errMsg = checkParam(paramMap);//参数校验
		if (errMsg != null) {
			logger.error(errMsg);
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("appmgr/resapp_qry_input");
		return mv;
	}
	
	//获取参数Map
	public Map<String, String> getParam(HttpServletRequest request){
		Map<String, String> paramMap=new HashMap<String, String>();
		paramMap.put("platType", request.getParameter("platType"));
		paramMap.put("osType", request.getParameter("osType"));
		paramMap.put("releaseVer", request.getParameter("releaseVer"));
		paramMap.put("modelId", request.getParameter("modelId"));
		paramMap.put("expireDate", request.getParameter("expireDate"));
		paramMap.put("count", request.getParameter("count"));
		return paramMap;
	}
	
	//参数校验
	private String checkParam(Map<String, String> paramMap) {
		return null;
	}
}