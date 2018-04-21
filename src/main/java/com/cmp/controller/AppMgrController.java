package com.cmp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.activiti.service.ActivitiService;
import com.cmp.service.CmpCommonService;
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpLogService;
import com.cmp.service.CmpOrderService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.MediumService;
import com.cmp.service.ProjectService;
import com.cmp.service.resourcemgt.CloudplatformService;
import com.cmp.service.servicemgt.AreaService;
import com.cmp.service.servicemgt.EnvironmentService;
import com.cmp.service.servicemgt.MirrorService;
import com.cmp.service.servicemgt.NumprocedureService;
import com.cmp.sid.CmpDict;
import com.cmp.sid.CmpOrder;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

//申请管理
@Controller
public class AppMgrController extends BaseController {
	private static String processDefinitionKey="resapp";
	@Resource
	private CmpCommonService cmpCommonService;
	@Resource
	private CmpDictService cmpDictService;
	@Resource
	private ActivitiService activitiService;
	@Resource
	private CmpOrderService cmpOrderService;
	@Resource
	private CmpWorkOrderService cmpWorkOrderService;
	@Resource
	private ProjectService projectService;
	@Resource
	private EnvironmentService environmentService;
	@Resource
	private MediumService mediumService;
	@Resource
	private MirrorService mirrorService;
	@Resource
	private CloudplatformService cloudplatformService;
	@Resource
	private AreaService areaService;
	@Resource
	private NumprocedureService numprocedureService;
	@Resource
	private CmpLogService cmpLogService;
	@Value("${uploadFilePath}")
	private String uploadFilePath;//上传文件路径
	
	//资源申请预查询
	@RequestMapping(value="/resAppPre")
	public ModelAndView resAppPre(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		String areaCodeId=null;//平台类型
		List<CmpDict> areaCodeList=areaService.getAreaCodeList();//区域列表
		if(areaCodeList!=null && !areaCodeList.isEmpty()){
			CmpDict cmpDict=areaCodeList.get(0);//第一项
			cmpDict.setDictDefault("1");//默认选择第一项
			mv.addObject("defaultAreaCode", cmpDict.getDictCode());//默认平台类型
			mv.addObject("areaCodeList", areaCodeList);//区域列表
			areaCodeId=cmpDict.getDictCode();//区域
		}
		
		mv.addObject("deployTypeList", cmpDictService.getCmpDictList("deploy_type"));//部署类型列表
		String envCodeId=null;//环境代码
		List<CmpDict> envList=environmentService.getEnvList(areaCodeId);
		if(envList!=null && !envList.isEmpty()){
			CmpDict cmpDict=envList.get(0);//第一项
			cmpDict.setDictDefault("1");//默认选择第一项
			mv.addObject("defaultEnvCode", cmpDict.getDictCode());//默认环境
			mv.addObject("defaultDiskNum", cmpDict.getDiskNum());//挂载云磁盘数量
			mv.addObject("defaultDiskMaxNum", cmpDict.getDiskMaxNum());//每块云磁盘最大值
			mv.addObject("defaultSoftNum", cmpDict.getSoftNum());//安装软件数量
			mv.addObject("envCodeList", envList);//环境列表
			envCodeId=cmpDict.getDictCode();//平台类型
		}
		
		String platTypeId=null;//平台类型
		List<CmpDict> platTypeList=cloudplatformService.getPlatTypeList(areaCodeId, envCodeId);//平台类型列表
		if(platTypeList!=null && !platTypeList.isEmpty()){
			CmpDict cmpDict=platTypeList.get(0);//第一项
			cmpDict.setDictDefault("1");//默认选择第一项
			mv.addObject("defaultPlatType", cmpDict.getDictCode());//默认平台类型
			mv.addObject("platTypeList", platTypeList);//平台类型列表
			platTypeId=cmpDict.getDictCode();//平台类型
		}
		
		String applyUserId=StringUtil.getUserName();//申请者
		mv.addObject("projectList", projectService.getProjectList());//项目列表
		mv.addObject("resTypeList", cmpDictService.getCmpDictList("res_type"));//资源类型列表
		mv.addObject("recommendTypeList", numprocedureService.getRecommendTypeList());//推荐配置列表
		mv.addObject("cpuList", cmpDictService.getCmpDictList("cpu"));//CPU列表
		mv.addObject("memoryList", cmpDictService.getCmpDictList("memory"));//内存列表
		mv.addObject("osTypeList", cmpDictService.getCmpDictList("os_type"));//OS类型列表
		mv.addObject("osBitNumList", cmpDictService.getCmpDictList("os_bit_num"));//位数列表
		mv.addObject("imgCodeList", mirrorService.getImgList(new PageData("platTypeId", platTypeId)));//模板列表
		mv.addObject("diskTypeList", cmpDictService.getCmpDictList("disk_type"));//磁盘类型列表
		//mv.addObject("diskSizeList", cmpDictService.getCmpDictList("disk_size"));//磁盘大小列表
		mv.addObject("softCodeList", mediumService.getSoftList());//软件代码列表
		//mv.addObject("cmpOrder", StringUtils.isBlank(orderNo)?null:cmpOrderService.getOrderDtl(orderNo));//清单详细信息
		mv.addObject("shoppingCartNum", cmpOrderService.getShoppingCartNum(applyUserId));//购物车列表大小
		mv.addObject("buyHisNum", cmpOrderService.getBuyHisNum(applyUserId));//已购历史列表大小
		mv.addObject("cmpPrice", cmpOrderService.getCmpPrice());//计算价格
		mv.setViewName("appmgr/resapp_qry_input");
		return mv;
	}
	
	//环境代码列表查询
	@RequestMapping(value="/getEnvCodeList", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String getEnvCodeList(String areaCodeId) throws Exception{
		String envCodeId=null;//环境代码
		int diskNumId=15;//挂载云磁盘数量
		int diskMaxNumId=32768;//每块云磁盘最大值
		int softNumId=15;//安装软件数量
		List<CmpDict> envList=environmentService.getEnvList(areaCodeId);
		if(envList!=null && !envList.isEmpty()){
			CmpDict cmpDict=envList.get(0);//第一项
			cmpDict.setDictDefault("1");//默认选择第一项
			envCodeId=cmpDict.getDictCode();//平台类型
			diskNumId=cmpDict.getDiskNum();//挂载云磁盘数量
			diskMaxNumId=cmpDict.getDiskMaxNum();//每块云磁盘最大值
			softNumId=cmpDict.getSoftNum();//安装软件数量
		}
		
		return StringUtil.getRetStr("0", "调用成功", "dataList", envList, "defaultEnvCode", envCodeId, "defaultDiskNum", diskNumId, "defaultDiskMaxNum", diskMaxNumId, "defaultSoftNum", softNumId);
	}
	
	//平台类型列表查询
	@RequestMapping(value="/getPlatTypeList", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String getPlatTypeList(String areaCodeId, String envCodeId) throws Exception{
		String platTypeId=null;//平台类型
		List<CmpDict> platTypeList=cloudplatformService.getPlatTypeList(areaCodeId, envCodeId);//平台类型列表
		if(platTypeList!=null && !platTypeList.isEmpty()){
			CmpDict cmpDict=platTypeList.get(0);//第一项
			cmpDict.setDictDefault("1");//默认选择第一项
			platTypeId=cmpDict.getDictCode();//默认平台类型
		}
		
		return StringUtil.getRetStr("0", "调用成功", "dataList", platTypeList, "defaultPlatType", platTypeId);
	}
	
	//模板列表查询
	@RequestMapping(value="/getImgList", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String getImgList(String platTypeId, String osType, String osBitNum) throws Exception{
		List<CmpDict> imgList=mirrorService.getImgList(new PageData("platTypeId", platTypeId, "osType", osType, "osBitNum", osBitNum));//模板列表
		return StringUtil.getRetStr("0", "调用成功", "dataList", imgList);
	}
	
	//套餐申请预查询
	@RequestMapping(value="/pckgAppPre")
	public ModelAndView pckgAppPre() throws Exception{
		ModelAndView mv = new ModelAndView();
		String areaCodeId=null;//平台类型
		List<CmpDict> areaCodeList=areaService.getAreaCodeList();//区域列表
		if(areaCodeList!=null && !areaCodeList.isEmpty()){
			CmpDict cmpDict=areaCodeList.get(0);//第一项
			cmpDict.setDictDefault("1");//默认选择第一项
			mv.addObject("defaultAreaCode", cmpDict.getDictCode());//默认平台类型
			mv.addObject("areaCodeList", areaCodeList);//区域列表
			areaCodeId=cmpDict.getDictCode();//区域
		}
		
		mv.addObject("deployTypeList", cmpDictService.getCmpDictList("deploy_type"));//部署类型列表
		String envCodeId=null;//环境代码
		List<CmpDict> envList=environmentService.getEnvList(areaCodeId);
		if(envList!=null && !envList.isEmpty()){
			CmpDict cmpDict=envList.get(0);//第一项
			cmpDict.setDictDefault("1");//默认选择第一项
			mv.addObject("defaultEnvCode", cmpDict.getDictCode());//默认环境
			mv.addObject("envCodeList", envList);//环境列表
			envCodeId=cmpDict.getDictCode();//平台类型
		}
		
		List<CmpDict> platTypeList=cloudplatformService.getPlatTypeList(areaCodeId, envCodeId);//平台类型列表
		if(platTypeList!=null && !platTypeList.isEmpty()){
			CmpDict cmpDict=platTypeList.get(0);//第一项
			cmpDict.setDictDefault("1");//默认选择第一项
			mv.addObject("defaultTcplatType", cmpDict.getDictCode());//默认平台类型
			mv.addObject("platTypeList", platTypeList);//环境列表
		}
		
		mv.addObject("projectList", projectService.getProjectList());//项目列表
		mv.addObject("resTypeList", cmpDictService.getCmpDictList("res_type"));//资源类型列表
		mv.addObject("recommendTypeList", numprocedureService.getRecommendTypeList());//推荐配置列表
		mv.addObject("cpuList", cmpDictService.getCmpDictList("cpu"));//CPU列表
		mv.addObject("memoryList", cmpDictService.getCmpDictList("memory"));//内存列表
		mv.addObject("osTypeList", cmpDictService.getCmpDictList("os_type"));//OS类型列表
		mv.addObject("osBitNumList", cmpDictService.getCmpDictList("os_bit_num"));//位数列表
		mv.addObject("imgCodeList", mirrorService.getImgList(new PageData("platTypeId", null)));//模板列表
		mv.addObject("diskTypeList", cmpDictService.getCmpDictList("disk_type"));//磁盘类型列表
		mv.addObject("diskSizeList", cmpDictService.getCmpDictList("disk_size"));//磁盘大小列表
		mv.addObject("softCodeList", mediumService.getSoftList());//软件代码列表
		mv.addObject("pckgList", cmpOrderService.getPckgList(StringUtil.getUserName()));//套餐列表查询
		mv.setViewName("appmgr/resapp_pckg_input");
		return mv;
	}
	
	//购物车列表查询
	@RequestMapping(value="/getShoppingCartList")
	public ModelAndView getShoppingCartList() throws Exception{
		List<CmpOrder> shoppingCartList=cmpOrderService.getShoppingCartList(StringUtil.getUserName());//购物车列表查询
		ModelAndView mv = new ModelAndView();
		mv.addObject("shoppingCartList", shoppingCartList);//软件版本列表
		mv.addObject("shoppingCartNum", shoppingCartList!=null?shoppingCartList.size():0);//软件版本列表大小
		mv.setViewName("appmgr/shoppingcart_qry_list");
		return mv;
	}
	
	//已购历史列表查询
	@RequestMapping(value="/getBuyHisList")
	public ModelAndView getBuyHisList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String beginDate=request.getParameter("beginDate");//开始日期
		String endDate=request.getParameter("endDate");//结束日期
		String projCode=request.getParameter("projCode");//项目
		PageData pd=getPageData("beginDate", beginDate, "endDate", endDate, "projCode", projCode, "applyUserId", StringUtil.getUserName());
		List<CmpOrder> buyHisList=cmpOrderService.getBuyHisList(pd);//已购历史列表查询
		ModelAndView mv = new ModelAndView();
		mv.addObject("pd", pd);
		mv.addObject("projectList", projectService.getProjectList());//项目列表
		mv.addObject("buyHisList", buyHisList);//已购历史列表
		mv.addObject("buyHisNum", buyHisList!=null?buyHisList.size():0);//已购历史列表大小
		mv.setViewName("appmgr/buyhis_qry_list");
		return mv;
	}
	
	//软件参数列表查询
	@RequestMapping(value="/getParamList")
	public ModelAndView getParamList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String softCode=request.getParameter("softCode");//软件代码
		List<CmpDict> paramList=mediumService.getSoftParamList(softCode);//软件参数列表查询
		StringBuffer sb=new StringBuffer();
		if(paramList!=null && !paramList.isEmpty()){
			for(CmpDict cmpDict: paramList){
				if(sb.length()!=0){
					sb.append(",");
				}
				
				sb.append(cmpDict.getDictCode()).append(":").append(cmpDict.getDictValue());
			}
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("appmgr/resapp_softparam_input");
		mv.addObject("softParamStr", sb.toString());//软件参数字符串
		mv.addObject("paramList", paramList);//软件参数列表
		return mv;
	}
		
	//加入清单
	@RequestMapping(value="/addList", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String addList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			CmpOrder cmpOrder=getParam(request);//获取参数Bean
			String errMsg = checkParam(cmpOrder);//参数校验
			if (errMsg != null) {
				logger.error(errMsg);
			}
			
			User user=StringUtil.getUserInfo();//获取登录用户
			cmpOrder.setStatus("0");//状态：0-未提交；1-已提交
			cmpOrder.setAppNo(null);
			cmpOrder.setApplyUserId(user.getUSERNAME());//用户名
			cmpOrder.setDeptId(user.getDEPARTMENT_ID());//部门ID
			cmpOrder.setExecuteStatus("0");
			cmpOrder.setPckgName(null);
			cmpOrderService.saveCmpOrder(cmpOrder);//新增清单或套餐
			cmpOrderService.saveSoftParams(cmpOrder.getOrderNo(), cmpOrder.getSoftCode(), cmpOrder.getSoftParam());//软件参数列表新增，软件参数：path:/tomcat,user:admin,passwd:admin|path:/tomcat,user:admin,passwd:admin
			cmpLogService.addCmpLog("1", "加入清单", "加入清单成功", "0", StringUtil.getClientIp(request));//新增日志
			return StringUtil.getRetStr("0", "加入清单成功");
		} catch (Exception e) {
	    	logger.error("加入清单时错误："+e);
	    	cmpLogService.addCmpLog("1", "加入清单", "加入清单时错误："+e, "-1", StringUtil.getClientIp(request));//新增日志
	    	return StringUtil.getRetStr("-1", "加入清单时错误："+e);
	    }
	}
	
	//加入套餐清单
	@RequestMapping(value="/addPckgList", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String addPckgList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			CmpOrder cmpOrder=getPckgParam(request);//获取套餐参数Bean
			String errMsg = checkPckgParam(cmpOrder);//参数校验
			if (errMsg != null) {
				logger.error(errMsg);
			}
			
			cmpOrder.setStatus("0");//状态：0-未提交；1-已提交
			cmpOrderService.addPckgList(cmpOrder);//新增套餐清单
			cmpLogService.addCmpLog("1", "加入套餐清单", "加入套餐清单成功", "0", StringUtil.getClientIp(request));//新增日志
			return StringUtil.getRetStr("0", "加入套餐清单成功");
		} catch (Exception e) {
	    	logger.error("加入套餐清单时错误："+e);
	    	cmpLogService.addCmpLog("1", "加入套餐清单", "加入套餐清单时错误："+e, "-1", StringUtil.getClientIp(request));//新增日志
	    	return StringUtil.getRetStr("-1", "加入套餐清单时错误："+e);
	    }
	}
	
	//提交申请
	@RequestMapping(value="/appCommit", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String appCommit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			String orderNoStr=request.getParameter("orderNoStr");//清单ID字符串
			String totalAmtStr=request.getParameter("totalAmtStr");//总价格字符串
			String allProjectCode=request.getParameter("allProjectCode");//所有项目代码
			String[] orderNos=orderNoStr.split(",");
			if(orderNos!=null && orderNos.length>0){
				String[] totalAmts=totalAmtStr.split(",");//总价格
				Double allTotalAmt=0.00;
				for(String totalAmt: totalAmts){
					allTotalAmt+=Double.parseDouble(totalAmt);
				}
				
				String applyUserId=StringUtil.getUserName();//获取登录用户
				String appNo=cmpCommonService.getAppNo("cmp_workorder");
				Map<String, Object> variables=new HashMap<String, Object>();
				variables.put("btnName", "提交");
				variables.put("USERNAME", user.getUSERNAME());
				String procInstId=activitiService.start(processDefinitionKey, applyUserId, appNo, variables);//流程启动
				cmpWorkOrderService.addWorkOrder(appNo, allTotalAmt+"", applyUserId, procInstId, allProjectCode);//提交申请
				//添加任务拾取
				List<Task> userTaskList = activitiService.findByProcessInstId(procInstId);
				for (Task task : userTaskList) {
					if (task.getProcessInstanceId().equals(procInstId)) {
						activitiService.claimTask(task.getId(), applyUserId);
					}
				}
				
				Map<String, Object> variablesMap = new HashMap<String, Object>();
				activitiService.handleTask(appNo, procInstId, applyUserId, null, variablesMap);//更新工单状态
				//更新工单(流程实例ID 和 工单状态)
				Map<String, String> updateParams = new HashMap<String, String>();
				updateParams.put("procInstId", procInstId);
				//updateParams.put("status", "1");
				cmpWorkOrderService.updateWorkOrder(appNo, updateParams);
				for(int i=0;i<orderNos.length;i++){
					cmpOrderService.updateCmpOrderStatus(getPageData("orderNo", orderNos[i], "totalAmt", totalAmts[i], "appNo", appNo));//更新清单状态
				}
			}
			
			cmpLogService.addCmpLog("1", "提交申请", "提交申请成功", "0", StringUtil.getClientIp(request));//新增日志
			return StringUtil.getRetStr("0", "提交申请成功");
		} catch (Exception e) {
	    	logger.error("提交申请时错误："+e);
	    	cmpLogService.addCmpLog("1", "提交申请", "提交申请时错误："+e, "-1", StringUtil.getClientIp(request));//新增日志
	    	return StringUtil.getRetStr("-1", "提交申请时错误："+e);
	    }
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
			
			User user=StringUtil.getUserInfo();//获取登录用户
			cmpOrder.setAppNo(null);
			cmpOrder.setApplyUserId(user.getUSERNAME());//用户名
			cmpOrder.setDeptId(user.getDEPARTMENT_ID());//部门ID
			cmpOrder.setExecuteStatus("0");
			cmpOrderService.saveCmpOrder(cmpOrder);//新增清单或套餐
			cmpOrderService.saveSoftParams(cmpOrder.getOrderNo(), cmpOrder.getSoftCode(), cmpOrder.getSoftParam());//软件参数列表新增，软件参数：path:/tomcat,user:admin,passwd:admin|path:/tomcat,user:admin,passwd:admin
			cmpLogService.addCmpLog("1", "保存套餐", "保存套餐成功", "0", StringUtil.getClientIp(request));//新增日志
			return StringUtil.getRetStr("0", "保存套餐成功");
		} catch (Exception e) {
	    	logger.error("保存套餐时错误："+e);
	    	cmpLogService.addCmpLog("1", "保存套餐", "保存套餐时错误："+e, "-1", StringUtil.getClientIp(request));//新增日志
	    	return StringUtil.getRetStr("-1", "保存套餐时错误："+e);
	    }
	}
	
	//清空购物车
	@RequestMapping(value="/clearShoppingCart", produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String clearShoppingCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			cmpOrderService.clearShoppingCart(StringUtil.getUserName());//清空购物车
			cmpLogService.addCmpLog("3", "清空购物车", "清空购物车成功", "0", StringUtil.getClientIp(request));//新增日志
			return StringUtil.getRetStr("0", "清空购物车成功");
		} catch (Exception e) {
			logger.error("清空购物车时错误："+e);
			cmpLogService.addCmpLog("3", "清空购物车", "清空购物车时错误："+e, "-1", StringUtil.getClientIp(request));//新增日志
			return StringUtil.getRetStr("-1", "清空购物车时错误："+e);
		}
	}
	
	//删除清单
	@RequestMapping(value="/delCmpOrder", produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String delCmpOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			String orderNo=request.getParameter("orderNo");//清单ID
			cmpOrderService.delCmpOrder(orderNo);//删除清单
			cmpLogService.addCmpLog("3", "删除清单", "删除清单成功", "0", StringUtil.getClientIp(request));//新增日志
			return StringUtil.getRetStr("0", "删除清单成功");
		} catch (Exception e) {
			logger.error("删除清单时错误："+e);
			cmpLogService.addCmpLog("3", "删除清单", "删除清单时错误："+e, "-1", StringUtil.getClientIp(request));//新增日志
			return StringUtil.getRetStr("-1", "删除清单时错误："+e);
		}
	}
	
	//删除套餐
	@RequestMapping(value="/delPckg", produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String delPckg(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			String pckgId=request.getParameter("pckgId");//套餐ID
			cmpOrderService.delPckg(pckgId);//删除套餐
			cmpLogService.addCmpLog("3", "删除套餐", "删除套餐成功", "0", StringUtil.getClientIp(request));//新增日志
			return StringUtil.getRetStr("0", "删除套餐成功");
		} catch (Exception e) {
			logger.error("删除套餐时错误："+e);
			cmpLogService.addCmpLog("3", "删除套餐", "删除套餐时错误："+e, "-1", StringUtil.getClientIp(request));//新增日志
			return StringUtil.getRetStr("-1", "删除套餐时错误："+e);
		}
	}
	
	@RequestMapping(value="/uploadFile", produces={"text/html;charset=UTF-8;", "application/json;"})
	@ResponseBody
	public String uploadFile(HttpServletRequest request, @RequestParam(value = "uploadFile", required = true) MultipartFile uploadFile) throws Exception {
		return FileUpload.fileUpEx(uploadFile, PathUtil.getClasspath()+uploadFilePath, new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+"_"+uploadFile.getOriginalFilename());//上传
	}
	
	//获取参数Bean
	private CmpOrder getParam(HttpServletRequest request){
		CmpOrder cmpOrder=new CmpOrder();
		cmpOrder.setAreaCode(request.getParameter("areaCode"));//地域
		cmpOrder.setEnvCode(request.getParameter("envCode"));//环境
		cmpOrder.setPlatType(request.getParameter("platType"));//平台类型
		cmpOrder.setDeployType(request.getParameter("deployType"));//部署类型
		cmpOrder.setResType(request.getParameter("resType"));//资源类型
		cmpOrder.setVirName(request.getParameter("virName"));//虚拟机名称
		cmpOrder.setVirIp(request.getParameter("virIp"));//虚拟机IP
		cmpOrder.setCpu(request.getParameter("cpu"));//CPU
		cmpOrder.setMemory(request.getParameter("memory"));//内存
		cmpOrder.setDiskType(request.getParameter("diskTypeStr"));//磁盘类型字符串
		cmpOrder.setDiskSize(request.getParameter("diskSizeStr"));//磁盘大小字符串
		cmpOrder.setDiskEncrypt(request.getParameter("diskEncryptStr"));//磁盘加密字符串
		cmpOrder.setSoftCode(request.getParameter("softCodeStr"));//软件代码字符串
		cmpOrder.setSoftParam(request.getParameter("softParamStr"));//软件参数字符串
		cmpOrder.setProjectCode(request.getParameter("projectCode"));//项目
		cmpOrder.setOsType(request.getParameter("osType"));//操作系统
		cmpOrder.setOsBitNum(request.getParameter("osBitNum"));//位数
		cmpOrder.setImgCode(request.getParameter("imgCode"));//镜像代码
		cmpOrder.setImgUserName(request.getParameter("imgUserName"));//镜像用户名
		cmpOrder.setImgUserPass(request.getParameter("imgUserPass"));//镜像用户密码
		cmpOrder.setImgPath(request.getParameter("imgPath"));//镜像路径
		cmpOrder.setExpireDate(request.getParameter("expireDate"));//到期时间
		cmpOrder.setVirNum(request.getParameter("virNum"));//数量
		cmpOrder.setFileName(request.getParameter("uploadFileName"));//文件名
		cmpOrder.setStatus(request.getParameter("status"));//状态：0-待提交；1-已提交；T-套餐
		cmpOrder.setPckgName(request.getParameter("pckgName"));//套餐名称
		return cmpOrder;
	}
	
	//获取套餐参数Bean
	private CmpOrder getPckgParam(HttpServletRequest request){
		CmpOrder cmpOrder=new CmpOrder();
		cmpOrder.setAreaCode(request.getParameter("tcareaCode"));//地域
		cmpOrder.setEnvCode(request.getParameter("tcenvCode"));//环境
		cmpOrder.setPlatType(request.getParameter("tcplatType"));//平台类型
		cmpOrder.setDeployType(request.getParameter("tcdeployType"));//部署类型
		cmpOrder.setVirName(request.getParameter("tcvirName"));//虚拟机名称
		cmpOrder.setOrderNo(request.getParameter("pckgId"));//套餐ID
		return cmpOrder;
	}
	
	//参数校验
	private String checkParam(CmpOrder cmpOrder) {
		if(!StringUtils.isBlank(cmpOrder.getFileName())){
			cmpOrder.setFileName(cmpOrder.getFileName());
		}
		
		return null;
	}
		
	//套餐参数校验
	private String checkPckgParam(CmpOrder cmpOrder) {
		return null;
	}
}