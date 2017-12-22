package com.cmp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.SysConfigService;
import com.cmp.sid.SysConfigInfo;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Watermark;

@Controller
@RequestMapping(value = "/sysconfig")
public class SysConfigController extends BaseController{
	
	@Resource
	private SysConfigService sysConfigService;

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
		
		String  ffile = DateUtil.getDays();
		String fileName = "";
		if (null != logo && !logo.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;	//文件上传路径
			fileName = FileUpload.fileUp(logo, filePath, this.get32UUID());			//执行上传
			pd.put("logo", ffile + "/" + fileName);									//路径
		}else{
			//不修改图
		}
		
		pd.put("sysName", sys_name);
		pd.put("companyName", company_name);
		pd.put("companyShortName", company_shortName);
		pd.put("tel", tel);
		pd.put("website", website);
		pd.put("productConsultion", product_consultion);
		pd.put("copr", copr);
			
		sysConfigService.update(pd);
		mv.addObject("result", "修改成功");
		mv.addObject("pd", pd);
		mv.setViewName("system/config/sysconfig");
		return mv;
	}
}
