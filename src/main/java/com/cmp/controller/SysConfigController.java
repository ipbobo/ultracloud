package com.cmp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CmpDictService;
import com.cmp.service.CmpLogService;
import com.cmp.service.SysConfigService;
import com.cmp.sid.SysConfigInfo;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value = "/sysconfig")
public class SysConfigController extends BaseController{
	
	@Resource
	private SysConfigService sysConfigService;
	
	@Resource
	private CmpDictService cmpDictService;
	
	@Resource
	private CmpLogService cmpLogService;
	

	@RequestMapping(value = "/updatePre")
	public ModelAndView updatePre(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		SysConfigInfo sysconfig = sysConfigService.getSysConfig();
		if (sysconfig != null) {
			pd.put("sysName", sysconfig.getSysName());
			pd.put("companyName", sysconfig.getCompanyName());
			pd.put("logo", sysconfig.getLogo());
			pd.put("companyShortName", sysconfig.getCompanyShortName());
			pd.put("tel", sysconfig.getTel());
			pd.put("website", sysconfig.getWebsite());
			pd.put("productConsultion", sysconfig.getProductConsultion());
			pd.put("copr", sysconfig.getCopr());
		}
		
		mv.setViewName("system/config/sysconfig");
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView update(HttpServletRequest request,
			@RequestParam(value="logo",required=false) MultipartFile logo,
			@RequestParam(value="sys_name",required=false) String sys_name,
			@RequestParam(value="company_name",required=false) String company_name,
			@RequestParam(value="company_shortName",required=false) String company_shortName,
			@RequestParam(value="tel",required=false) String tel,
			@RequestParam(value="website",required=false) String website,
			@RequestParam(value="product_consultion",required=false) String product_consultion,
			@RequestParam(value="copr",required=false) String copr) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		
		SysConfigInfo initConfig = SysConfigInfo.getInstance();
		String newlogoPath = initConfig.getLogo();;
		if (null != logo && !logo.isEmpty()) {
			String filePath = PathUtil.getClasspath() + "static/login";	//文件上传路径
			String upfileName = logo.getOriginalFilename();
			newlogoPath = filePath + "/" + upfileName;
			FileUpload.fileUp(logo, filePath, upfileName);			//执行上传
			pd.put("logo", newlogoPath);									//路径
		}else{
			//不修改图
			pd.put("logo", initConfig.getLogo());	
		}
		pd.put("sysName", sys_name);
		pd.put("companyName", company_name);
		pd.put("companyShortName", company_shortName);
		pd.put("tel", tel);
		pd.put("website", website);
		pd.put("productConsultion", product_consultion);
		pd.put("copr", copr);
		sysConfigService.update(pd);
		
		initConfig.setCompanyName(company_name);
		initConfig.setCompanyShortName(company_shortName);
		initConfig.setCopr(copr);
		initConfig.setLogo(newlogoPath);
		initConfig.setProductConsultion(product_consultion);
		initConfig.setSysName(sys_name);
		initConfig.setTel(tel);
		initConfig.setWebsite(website);

		cmpLogService.addCmpLog("1", "修改系统设置", "修改系统设置", "0", StringUtil.getClientIp(request));
		mv.addObject("result", "修改成功");
		mv.addObject("pd", pd);
		mv.setViewName("system/config/sysconfig");
		return mv;
	}
	
	
	
	
	@RequestMapping(value = "/getSysConfig")
	@ResponseBody
	public Object getSysConfig() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String resultInfo = "";
		if (SysConfigInfo.getInstance().getSysName() != null && SysConfigInfo.getInstance().getSysName().length() >0) {
			map.put("sysConfigInfo", SysConfigInfo.getInstance());
		}else {
			//第一次访问，初始化
			SysConfigInfo  sysConfigInfo = sysConfigService.getSysConfig();
			SysConfigInfo initConfig = SysConfigInfo.getInstance();
			initConfig.setCompanyName(sysConfigInfo.getCompanyName());
			initConfig.setCompanyShortName(sysConfigInfo.getCompanyShortName());
			initConfig.setCopr(sysConfigInfo.getCopr());
			initConfig.setLogo(sysConfigInfo.getLogo());
			initConfig.setProductConsultion(sysConfigInfo.getProductConsultion());
			initConfig.setSysName(sysConfigInfo.getSysName());
			initConfig.setTel(sysConfigInfo.getTel());
			initConfig.setWebsite(sysConfigInfo.getWebsite());
			map.put("sysConfigInfo", initConfig);
		}
		
		resultInfo = "success";
		map.put("result", resultInfo);	
		return map;
	}
}
