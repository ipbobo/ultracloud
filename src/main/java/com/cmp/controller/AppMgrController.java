package com.cmp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CmpDictService;
import com.cmp.service.CmpOrderService;
import com.cmp.sid.CmpDict;
import com.cmp.sid.CmpOrder;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;

//申请管理
@Controller
public class AppMgrController extends BaseController {
	@Resource
	private CmpDictService cmpDictService;
	@Resource
	private CmpOrderService cmpOrderService;
	
	//资源申请预查询
	@RequestMapping(value="/resAppPre")
	public ModelAndView resAppPre() throws Exception{
		List<CmpDict> areaCodeList=cmpDictService.getCmpDictList("area_code");//数据字典列表查询
		List<CmpDict> platTypeList=cmpDictService.getCmpDictList("plat_type");//数据字典列表查询
		List<CmpDict> deployTypeList=cmpDictService.getCmpDictList("deploy_type");//数据字典列表查询
		List<CmpDict> envCodeList=cmpDictService.getCmpDictList("env_code");//数据字典列表查询
		List<CmpDict> projectCodeList=cmpDictService.getCmpDictList("project_code");//数据字典列表查询
		List<CmpDict> resTypeList=cmpDictService.getCmpDictList("res_type");//数据字典列表查询
		List<CmpDict> recommendTypeList=cmpDictService.getCmpDictList("recommend_type");//数据字典列表查询
		List<CmpDict> cpuList=cmpDictService.getCmpDictList("cpu");//数据字典列表查询
		List<CmpDict> memoryList=cmpDictService.getCmpDictList("memory");//数据字典列表查询
		List<CmpDict> osTypeList=cmpDictService.getCmpDictList("os_type");//数据字典列表查询
		List<CmpDict> osBitNumList=cmpDictService.getCmpDictList("os_bit_num");//数据字典列表查询
		List<CmpDict> imgCodeList=cmpDictService.getCmpDictList("img_code");//数据字典列表查询
		List<CmpDict> diskTypeList=cmpDictService.getCmpDictList("disk_type");//数据字典列表查询
		List<CmpDict> diskSizeList=cmpDictService.getCmpDictList("disk_size");//数据字典列表查询
		List<CmpDict> softNameList=cmpDictService.getCmpDictList("soft_name");//数据字典列表查询
		List<CmpDict> softVerList=cmpDictService.getCmpDictList("soft_ver");//数据字典列表查询
		ModelAndView mv = new ModelAndView();
		mv.addObject("areaCodeList", areaCodeList);//区域列表
		mv.addObject("platTypeList", platTypeList);//平台类型列表
		mv.addObject("deployTypeList", deployTypeList);//部署类型列表
		mv.addObject("envCodeList", envCodeList);//环境列表
		mv.addObject("projectCodeList", projectCodeList);//项目列表
		mv.addObject("resTypeList", resTypeList);//资源类型列表
		mv.addObject("recommendTypeList", recommendTypeList);//推荐配置列表
		mv.addObject("cpuList", cpuList);//CPU列表
		mv.addObject("memoryList", memoryList);//内存列表
		mv.addObject("osTypeList", osTypeList);//OS类型列表
		mv.addObject("osBitNumList", osBitNumList);//位数列表
		mv.addObject("imgCodeList", imgCodeList);//模板列表
		mv.addObject("diskTypeList", diskTypeList);//磁盘类型列表
		mv.addObject("diskSizeList", diskSizeList);//磁盘大小列表
		mv.addObject("softNameList", softNameList);//软件名称列表
		mv.addObject("softVerList", softVerList);//软件版本列表
		mv.setViewName("appmgr/resapp_qry_input");
		return mv;
	}
	
	//套餐申请预查询
	@RequestMapping(value="/pckgAppPre")
	public ModelAndView pckgAppPre() throws Exception{
		List<CmpDict> areaCodeList=cmpDictService.getCmpDictList("area_code");//数据字典列表查询
		List<CmpDict> platTypeList=cmpDictService.getCmpDictList("plat_type");//数据字典列表查询
		List<CmpDict> deployTypeList=cmpDictService.getCmpDictList("deploy_type");//数据字典列表查询
		List<CmpDict> envCodeList=cmpDictService.getCmpDictList("env_code");//数据字典列表查询
		List<CmpDict> projectCodeList=cmpDictService.getCmpDictList("project_code");//数据字典列表查询
		List<CmpDict> resTypeList=cmpDictService.getCmpDictList("res_type");//数据字典列表查询
		List<CmpDict> recommendTypeList=cmpDictService.getCmpDictList("recommend_type");//数据字典列表查询
		List<CmpDict> cpuList=cmpDictService.getCmpDictList("cpu");//数据字典列表查询
		List<CmpDict> memoryList=cmpDictService.getCmpDictList("memory");//数据字典列表查询
		List<CmpDict> osTypeList=cmpDictService.getCmpDictList("os_type");//数据字典列表查询
		List<CmpDict> osBitNumList=cmpDictService.getCmpDictList("os_bit_num");//数据字典列表查询
		List<CmpDict> imgCodeList=cmpDictService.getCmpDictList("img_code");//数据字典列表查询
		List<CmpDict> diskTypeList=cmpDictService.getCmpDictList("disk_type");//数据字典列表查询
		List<CmpDict> diskSizeList=cmpDictService.getCmpDictList("disk_size");//数据字典列表查询
		List<CmpDict> softNameList=cmpDictService.getCmpDictList("soft_name");//数据字典列表查询
		List<CmpDict> softVerList=cmpDictService.getCmpDictList("soft_ver");//数据字典列表查询
		List<CmpOrder> pckgList=cmpOrderService.getPckgList();//套餐列表查询
		ModelAndView mv = new ModelAndView();
		mv.addObject("areaCodeList", areaCodeList);//区域列表
		mv.addObject("platTypeList", platTypeList);//平台类型列表
		mv.addObject("deployTypeList", deployTypeList);//部署类型列表
		mv.addObject("envCodeList", envCodeList);//环境列表
		mv.addObject("projectCodeList", projectCodeList);//项目列表
		mv.addObject("resTypeList", resTypeList);//资源类型列表
		mv.addObject("recommendTypeList", recommendTypeList);//推荐配置列表
		mv.addObject("cpuList", cpuList);//CPU列表
		mv.addObject("memoryList", memoryList);//内存列表
		mv.addObject("osTypeList", osTypeList);//OS类型列表
		mv.addObject("osBitNumList", osBitNumList);//位数列表
		mv.addObject("imgCodeList", imgCodeList);//模板列表
		mv.addObject("diskTypeList", diskTypeList);//磁盘类型列表
		mv.addObject("diskSizeList", diskSizeList);//磁盘大小列表
		mv.addObject("softNameList", softNameList);//软件名称列表
		mv.addObject("softVerList", softVerList);//软件版本列表
		mv.addObject("pckgList", pckgList);//软件版本列表
		mv.setViewName("appmgr/resapp_pckg_input");
		return mv;
	}
	
	/*@RequestMapping(value="/getDictList", produces={"application/json;charset=UTF-8"})
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
	}*/
	
	//加入清单
	@RequestMapping(value="/addList")
	public ModelAndView addList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CmpOrder cmpOrder=getParam(request);//获取参数Bean
		String errMsg = checkParam(cmpOrder);//参数校验
		if (errMsg != null) {
			logger.error(errMsg);
		}
		
		cmpOrderService.saveCmpOrder(cmpOrder);//新增清单
		ModelAndView mv = new ModelAndView();
		mv.setViewName("appmgr/resapp_qry_input");
		return mv;
	}
	
	//加入套餐清单
	@RequestMapping(value="/addPckgList")
	public ModelAndView addPckgList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CmpOrder cmpOrder=getPckgParam(request);//获取套餐参数Bean
		String errMsg = checkParam(cmpOrder);//参数校验
		if (errMsg != null) {
			logger.error(errMsg);
		}
		
		cmpOrderService.addPckgList(cmpOrder);//新增套餐清单
		ModelAndView mv = new ModelAndView();
		mv.setViewName("appmgr/resapp_qry_input");
		return mv;
	}
	
	//保存为套餐预查询
	@RequestMapping(value="/savePckgPre")
	public ModelAndView savePckgPre(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("appmgr/resapp_savepckg_input");
		return mv;
	}
	
	//保存为套餐
	@RequestMapping(value="/savePckg", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String savePckg(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			CmpOrder cmpOrder=getParam(request);//获取参数Bean
			String errMsg = checkParam(cmpOrder);//参数校验
			if (errMsg != null) {
				logger.error(errMsg);
			}
			
			cmpOrderService.saveCmpOrder(cmpOrder);//新增清单
			return StringUtil.getRetStr("0", "保存套餐成功");
		} catch (Exception e) {
	    	logger.error("保存套餐时错误："+e);
	    	return StringUtil.getRetStr("-1", "保存套餐时错误："+e);
	    }
	}
	
	//删除套餐
	@RequestMapping(value="/delPckg", produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String delPckg(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			String pckgId=request.getParameter("pckgId");//套餐ID
			cmpOrderService.delPckg(pckgId);//新增清单
			return StringUtil.getRetStr("0", "删除套餐成功");
		} catch (Exception e) {
			logger.error("删除套餐时错误："+e);
			return StringUtil.getRetStr("-1", "删除套餐时错误："+e);
		}
	}
	
	//获取参数Bean
	public CmpOrder getParam(HttpServletRequest request){
		CmpOrder cmpOrder=new CmpOrder();
		cmpOrder.setAreaCode(request.getParameter("areaCode"));//地域
		cmpOrder.setPlatType(request.getParameter("platType"));//平台类型
		cmpOrder.setDeployType(request.getParameter("deployType"));//部署类型
		cmpOrder.setEnvCode(request.getParameter("envCode"));//环境
		cmpOrder.setResType(request.getParameter("resType"));//资源类型
		cmpOrder.setVirName(request.getParameter("virName"));//虚拟机名称
		cmpOrder.setVirIp(request.getParameter("virIp"));//虚拟机IP
		cmpOrder.setCpu(request.getParameter("cpu"));//CPU
		cmpOrder.setMemory(request.getParameter("memory"));//内存
		cmpOrder.setDiskType(request.getParameter("diskTypeStr"));//磁盘类型字符串
		cmpOrder.setDiskSize(request.getParameter("diskSizeStr"));//磁盘大小字符串
		cmpOrder.setDiskEncrypt(request.getParameter("diskEncryptStr"));//磁盘加密字符串
		cmpOrder.setSoftName(request.getParameter("softNameStr"));//软件名称字符串
		cmpOrder.setSoftVer(request.getParameter("softVerStr"));//软件版本字符串
		cmpOrder.setSoftParam(request.getParameter("softParamStr"));//软件参数字符串
		cmpOrder.setProjectCode(request.getParameter("projectCode"));//项目
		cmpOrder.setOsType(request.getParameter("osType"));//操作系统
		cmpOrder.setOsBitNum(request.getParameter("osBitNum"));//位数
		cmpOrder.setImgCode(request.getParameter("imgCode"));//镜像代码
		cmpOrder.setImgUserName(request.getParameter("imgUserName"));//镜像用户名
		cmpOrder.setImgUserPass(request.getParameter("imgUserPass"));//镜像用户密码
		cmpOrder.setImgPath(request.getParameter("imgPath"));//镜像路径
		cmpOrder.setImgExpireDate(request.getParameter("imgExpireDate"));//镜像到期时间
		cmpOrder.setExpireDate(request.getParameter("expireDate"));//到期时间
		cmpOrder.setVirNum(request.getParameter("virNum"));//数量
		cmpOrder.setPckgFlag(request.getParameter("pckgFlag"));//套餐标志：0-否；1-是
		cmpOrder.setPckgName(request.getParameter("pckgName"));//套餐名称
		return cmpOrder;
	}
	
	//获取套餐参数Bean
	public CmpOrder getPckgParam(HttpServletRequest request){
		CmpOrder cmpOrder=new CmpOrder();
		cmpOrder.setAreaCode(request.getParameter("tcareaCode"));//地域
		cmpOrder.setPlatType(request.getParameter("tcplatType"));//平台类型
		cmpOrder.setDeployType(request.getParameter("tcdeployType"));//部署类型
		cmpOrder.setVirName(request.getParameter("tcvirName"));//虚拟机名称
		cmpOrder.setId(Long.parseLong(request.getParameter("pckgId")));//套餐ID
		return cmpOrder;
	}
	
	//参数校验
	private String checkParam(CmpOrder cmpOrder) {
		return null;
	}
}