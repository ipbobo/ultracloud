package com.cmp.controller;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.DocumentService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DelAllFile;
import com.fh.util.FileDownload;
import com.fh.util.FileUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;

/**
 * 文档管理
 */
@Controller
@RequestMapping(value = "/document")
public class DocumentController extends BaseController {

	String menuUrl = "document/list.do"; // 菜单地址(权限用)
	
	@Resource(name = "documentService")
	private DocumentService documentService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Document");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("user_id", Jurisdiction.getUsername()); // 上传者
		pd.put("filesize", FileUtil.getFilesize(PathUtil.getClasspath() + Const.FILEPATHFILEOA + pd.getString("url"))); // 文件大小
		documentService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Document");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = documentService.findById(pd);
		documentService.delete(pd);
		DelAllFile.delFolder(PathUtil.getClasspath() + Const.FILEPATHFILEOA + pd.getString("url")); // 删除文件
		out.write("success");
		out.close();
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Document");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String item = Jurisdiction.getDEPARTMENT_IDS();
		if ("0".equals(item) || "无权".equals(item)) {
			pd.put("item", ""); // 根据部门ID过滤
		} else {
			pd.put("item", item.replaceFirst("\\(", "\\('" + Jurisdiction.getDEPARTMENT_ID() + "',"));
		}
		page.setPd(pd);
		List<PageData> varList = documentService.list(page); // 列出Document列表
		List<PageData> nvarList = new ArrayList<PageData>();
		for (int i = 0; i < varList.size(); i++) {
			PageData npd = new PageData();
			String url = varList.get(i).getString("url");
			String Extension_name = url.substring(20, url.length());// 文件拓展名
			String fileType = "file";
			int zindex1 = "java,php,jsp,html,css,txt,asp".indexOf(Extension_name);
			if (zindex1 != -1) {
				fileType = "wenben"; // 文本类型
			}
			int zindex2 = "jpg,gif,bmp,png".indexOf(Extension_name);
			if (zindex2 != -1) {
				fileType = "tupian"; // 图片文件类型
			}
			int zindex3 = "rar,zip,rar5".indexOf(Extension_name);
			if (zindex3 != -1) {
				fileType = "yasuo"; // 压缩文件类型
			}
			int zindex4 = "doc,docx".indexOf(Extension_name);
			if (zindex4 != -1) {
				fileType = "doc"; // doc文件类型
			}
			int zindex5 = "xls,xlsx".indexOf(Extension_name);
			if (zindex5 != -1) {
				fileType = "xls"; // xls文件类型
			}
			int zindex6 = "ppt,pptx".indexOf(Extension_name);
			if (zindex6 != -1) {
				fileType = "ppt"; // ppt文件类型
			}
			int zindex7 = "pdf".indexOf(Extension_name);
			if (zindex7 != -1) {
				fileType = "pdf"; // ppt文件类型
			}
			npd.put("fileType", fileType); // 用于文件图标
			npd.put("id", ((BigInteger)varList.get(i).get("id")).intValue()); // 唯一ID
			npd.put("name", varList.get(i).getString("name")); // 文件名
			npd.put("url", url); // 文件名+扩展名
			npd.put("gmt_create", varList.get(i).get("gmt_create")); // 创建时间
			npd.put("gmt_modified", varList.get(i).get("gmt_modified")); // 更新时间
			npd.put("user_id", varList.get(i).getString("user_id")); // 用户名
			//npd.put("DEPARTMENT_ID", varList.get(i).getString("DEPARTMENT_ID"));// 机构级别
			npd.put("filesize", varList.get(i).getString("filesize")); // 文件大小
			npd.put("detail", varList.get(i).getString("detail")); // 备注
			nvarList.add(npd);
		}
		mv.setViewName("automation/document_list");
		mv.addObject("varList", nvarList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("automation/document_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去预览pdf文件页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goViewPdf")
	public ModelAndView goViewPdf() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = documentService.findById(pd);
		mv.setViewName("automation/document_view_pdf");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去预览txt,java,php,等文本文件页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goViewTxt")
	public ModelAndView goViewTxt() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String encoding = pd.getString("encoding");
		pd = documentService.findById(pd);
		String code = Tools.readTxtFileAll(Const.FILEPATHFILEOA + pd.getString("url"), encoding);
		pd.put("code", code);
		mv.setViewName("automation/document_view_txt");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Document");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			PageData fpd = new PageData();
			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				fpd.put("id", ArrayDATA_IDS[i]);
				fpd = documentService.findById(fpd);
				DelAllFile.delFolder(PathUtil.getClasspath() + Const.FILEPATHFILEOA + fpd.getString("url")); // 删除物理文件
			}
			documentService.deleteAll(ArrayDATA_IDS); // 删除数据库记录
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 下载
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/download")
	public void downExcel(HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = documentService.findById(pd);
		String fileName = pd.getString("url");
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILEOA + fileName,
				pd.getString("name") + fileName.substring(19, fileName.length()));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
