package com.cmp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.activiti.service.ActivitiService;
import com.cmp.service.CmpCommonService;
import com.cmp.service.CmpDictService;
import com.cmp.service.CmpOrderService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.ProjectService;
import com.cmp.sid.CmpOrder;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;

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
	
	//资源申请预查询
	@RequestMapping(value="/resAppPre")
	public ModelAndView resAppPre(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String orderId=request.getParameter("orderId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("areaCodeList", cmpDictService.getCmpDictList("area_code"));//区域列表
		mv.addObject("platTypeList", cmpDictService.getCmpDictList("plat_type"));//平台类型列表
		mv.addObject("deployTypeList", cmpDictService.getCmpDictList("deploy_type"));//部署类型列表
		mv.addObject("envCodeList", cmpDictService.getCmpDictList("env_code"));//环境列表
		mv.addObject("projectList", projectService.getProjectList());//项目列表
		mv.addObject("resTypeList", cmpDictService.getCmpDictList("res_type"));//资源类型列表
		mv.addObject("recommendTypeList", cmpDictService.getCmpDictList("recommend_type"));//推荐配置列表
		mv.addObject("cpuList", cmpDictService.getCmpDictList("cpu"));//CPU列表
		mv.addObject("memoryList", cmpDictService.getCmpDictList("memory"));//内存列表
		mv.addObject("osTypeList", cmpDictService.getCmpDictList("os_type"));//OS类型列表
		mv.addObject("osBitNumList", cmpDictService.getCmpDictList("os_bit_num"));//位数列表
		mv.addObject("imgCodeList", cmpDictService.getCmpDictList("img_code"));//模板列表
		mv.addObject("diskTypeList", cmpDictService.getCmpDictList("disk_type"));//磁盘类型列表
		mv.addObject("diskSizeList", cmpDictService.getCmpDictList("disk_size"));//磁盘大小列表
		mv.addObject("softNameList", cmpDictService.getCmpDictList("soft_name"));//软件名称列表
		mv.addObject("softVerList", cmpDictService.getCmpDictList("soft_ver"));//软件版本列表
		mv.addObject("cmpOrder", StringUtils.isBlank(orderId)?null:cmpOrderService.getOrderDtl(orderId));//清单详细信息
		List<CmpOrder> shoppingCartList=cmpOrderService.getShoppingCartList();//购物车列表查询
		mv.addObject("shoppingCartNum", shoppingCartList!=null?shoppingCartList.size():0);//购物车列表大小
		List<CmpOrder> buyHisList=cmpOrderService.getBuyHisList();//已购历史列表查询
		mv.addObject("buyHisNum", buyHisList!=null?buyHisList.size():0);//已购历史列表大小
		mv.setViewName("appmgr/resapp_qry_input");
		return mv;
	}
	
	//套餐申请预查询
	@RequestMapping(value="/pckgAppPre")
	public ModelAndView pckgAppPre() throws Exception{
		List<CmpOrder> pckgList=cmpOrderService.getPckgList();//套餐列表查询
		ModelAndView mv = new ModelAndView();
		mv.addObject("areaCodeList", cmpDictService.getCmpDictList("area_code"));//区域列表
		mv.addObject("platTypeList", cmpDictService.getCmpDictList("plat_type"));//平台类型列表
		mv.addObject("deployTypeList", cmpDictService.getCmpDictList("deploy_type"));//部署类型列表
		mv.addObject("envCodeList", cmpDictService.getCmpDictList("env_code"));//环境列表
		mv.addObject("projectList", projectService.getProjectList());//项目列表
		mv.addObject("resTypeList", cmpDictService.getCmpDictList("res_type"));//资源类型列表
		mv.addObject("recommendTypeList", cmpDictService.getCmpDictList("recommend_type"));//推荐配置列表
		mv.addObject("cpuList", cmpDictService.getCmpDictList("cpu"));//CPU列表
		mv.addObject("memoryList", cmpDictService.getCmpDictList("memory"));//内存列表
		mv.addObject("osTypeList", cmpDictService.getCmpDictList("os_type"));//OS类型列表
		mv.addObject("osBitNumList", cmpDictService.getCmpDictList("os_bit_num"));//位数列表
		mv.addObject("imgCodeList", cmpDictService.getCmpDictList("img_code"));//模板列表
		mv.addObject("diskTypeList", cmpDictService.getCmpDictList("disk_type"));//磁盘类型列表
		mv.addObject("diskSizeList", cmpDictService.getCmpDictList("disk_size"));//磁盘大小列表
		mv.addObject("softNameList", cmpDictService.getCmpDictList("soft_name"));//软件名称列表
		mv.addObject("softVerList", cmpDictService.getCmpDictList("soft_ver"));//软件版本列表
		mv.addObject("pckgList", pckgList);//软件版本列表
		mv.setViewName("appmgr/resapp_pckg_input");
		return mv;
	}
	
	//购物车列表查询
	@RequestMapping(value="/getShoppingCartList")
	public ModelAndView getShoppingCartList() throws Exception{
		List<CmpOrder> shoppingCartList=cmpOrderService.getShoppingCartList();//购物车列表查询
		ModelAndView mv = new ModelAndView();
		mv.addObject("shoppingCartList", shoppingCartList);//软件版本列表
		mv.addObject("shoppingCartNum", shoppingCartList!=null?shoppingCartList.size():0);//软件版本列表大小
		mv.setViewName("appmgr/shoppingcart_qry_list");
		return mv;
	}
	
	//已购历史列表查询
	@RequestMapping(value="/getBuyHisList")
	public ModelAndView getBuyHisList() throws Exception{
		List<CmpOrder> buyHisList=cmpOrderService.getBuyHisList();//已购历史列表查询
		ModelAndView mv = new ModelAndView();
		mv.addObject("buyHisList", buyHisList);//已购历史列表
		mv.addObject("buyHisNum", buyHisList!=null?buyHisList.size():0);//已购历史列表大小
		mv.setViewName("appmgr/buyhis_qry_list");
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
	@RequestMapping(value="/addList", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String addList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			CmpOrder cmpOrder=getParam(request);//获取参数Bean
			String errMsg = checkParam(cmpOrder);//参数校验
			if (errMsg != null) {
				logger.error(errMsg);
			}
			
			cmpOrder.setStatus("0");//状态：0-未提交；1-已提交
			cmpOrder.setApplyUserId(getUserId());//获取登录用户
			cmpOrderService.saveCmpOrder(cmpOrder);//新增清单或套餐
			return StringUtil.getRetStr("0", "加入清单成功");
		} catch (Exception e) {
	    	logger.error("加入清单时错误："+e);
	    	return StringUtil.getRetStr("-1", "加入清单时错误："+e);
	    }
	}
	
	//加入套餐清单
	@RequestMapping(value="/addPckgList", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String addPckgList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			CmpOrder cmpOrder=getPckgParam(request);//获取套餐参数Bean
			String errMsg = checkParam(cmpOrder);//参数校验
			if (errMsg != null) {
				logger.error(errMsg);
			}
			
			cmpOrder.setStatus("0");//状态：0-未提交；1-已提交
			cmpOrder.setApplyUserId(getUserId());//获取登录用户
			cmpOrderService.addPckgList(cmpOrder);//新增套餐清单
			return StringUtil.getRetStr("0", "加入套餐清单成功");
		} catch (Exception e) {
	    	logger.error("加入套餐清单时错误："+e);
	    	return StringUtil.getRetStr("-1", "加入套餐清单时错误："+e);
	    }
	}
	
	//提交申请
	@RequestMapping(value="/appCommit", produces={"application/json;charset=UTF-8"})
    @ResponseBody
	public String appCommit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			String orderIdStr=request.getParameter("orderIdStr");//清单ID字符串
			String[] orderIds=orderIdStr.split(",");
			if(orderIds!=null && orderIds.length>0){
				String applyUserId=getUserId();//获取登录用户
				for(String orderId: orderIds){
					String appNo=cmpCommonService.getAppNo("cmp_workorder");
					Map<String, Object> variables=new HashMap<String, Object>();
					variables.put("btnName", "提交");
					String procInstId=activitiService.start(processDefinitionKey, applyUserId, appNo, variables);//流程启动
					cmpWorkOrderService.addWorkOrder(appNo, orderId, applyUserId, procInstId);//提交申请
					cmpOrderService.updateCmpOrderStatus(orderId);//更新清单状态
				}
			}
			
			return StringUtil.getRetStr("0", "提交申请成功");
		} catch (Exception e) {
	    	logger.error("提交申请时错误："+e);
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
			
			cmpOrder.setApplyUserId(getUserId());//获取登录用户
			cmpOrderService.saveCmpOrder(cmpOrder);//新增清单或套餐
			return StringUtil.getRetStr("0", "保存套餐成功");
		} catch (Exception e) {
	    	logger.error("保存套餐时错误："+e);
	    	return StringUtil.getRetStr("-1", "保存套餐时错误："+e);
	    }
	}
	
	//清空购物车
	@RequestMapping(value="/clearShoppingCart", produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String clearShoppingCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			cmpOrderService.clearShoppingCart(getUserId());//清空购物车
			return StringUtil.getRetStr("0", "清空购物车成功");
		} catch (Exception e) {
			logger.error("清空购物车时错误："+e);
			return StringUtil.getRetStr("-1", "清空购物车时错误："+e);
		}
	}
	
	//删除清单
	@RequestMapping(value="/delCmpOrder", produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String delCmpOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			String orderId=request.getParameter("orderId");//清单ID
			cmpOrderService.delCmpOrder(orderId);//删除清单
			return StringUtil.getRetStr("0", "删除清单成功");
		} catch (Exception e) {
			logger.error("删除清单时错误："+e);
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
			return StringUtil.getRetStr("0", "删除套餐成功");
		} catch (Exception e) {
			logger.error("删除套餐时错误："+e);
			return StringUtil.getRetStr("-1", "删除套餐时错误："+e);
		}
	}
	
	//获取登录用户
	private String getUserId() throws Exception{
		try{
			Session session = Jurisdiction.getSession();
			User user=(User)session.getAttribute(Const.SESSION_USER);
			return user.getUSERNAME();//获取登录用户
		} catch (Exception e) {
	    	logger.error("获取登录用户时错误："+e);
	    	return StringUtil.getRetStr("-1", "获取登录用户时错误："+e);
	    }
	}
	
	//获取参数Bean
	private CmpOrder getParam(HttpServletRequest request){
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
		cmpOrder.setStatus(request.getParameter("status"));//状态：0-待提交；1-已提交；T-套餐
		cmpOrder.setPckgName(request.getParameter("pckgName"));//套餐名称
		return cmpOrder;
	}
	
	//获取套餐参数Bean
	private CmpOrder getPckgParam(HttpServletRequest request){
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