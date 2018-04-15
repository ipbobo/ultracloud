package com.cmp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.MediumService;
import com.cmp.service.ProjectService;
import com.cmp.service.bi.BIDatacenterService;
import com.cmp.service.bi.BiBillDayService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 报表中心 控制层
 * 
 * @author liuweixing
 *
 */
@Controller
@RequestMapping(value = "/bidatacenter")
public class BIDatacenterController extends BaseController {

	@Resource(name = "biDatacenterService")
	private BIDatacenterService biDatacenterService;

	@Resource(name = "departmentService")
	private DepartmentManager departmentService;

	@Resource(name = "projectService")
	private ProjectService projectService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "mediumService")
	private MediumService mediumService;

	@Resource(name = "biBillDayService")
	private BiBillDayService biBillDayService;

	@Resource(name = "dictionariesService")
	private DictionariesService dictionariesService;

	/**
	 * 计费报表列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listBillDay")
	public ModelAndView listBillDay(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表BillDay");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		// 查询项目列表
		List<PageData> projectList = projectService.listAll(pd);
		mv.addObject("projectList", projectList);

		// 查询部门树
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0", zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));

		// 查询用户列表
		List<PageData> userList = userService.listAllUser(pd);
		mv.addObject("userList", userList);

		List<PageData> varList = biDatacenterService.listBillBIData(pd);
		mv.addObject("varList", varList);

		// 计算总额
		if (null != varList) {
			BigDecimal totalSum = new BigDecimal(0);
			for (PageData biPD : varList) {
				totalSum = totalSum.add((BigDecimal) biPD.get("account"));
			}
			pd.put("totalSum", totalSum);
		}

		mv.setViewName("bi/bill_list");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 导出计费报表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportBillExcel")
	public ModelAndView exportBillExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			
			String DEPARTMENT_ID = pd.getString("DEPARTMENT_ID"); // 部门
			String project_id = pd.getString("project_id"); // 项目
			String USERNAME = pd.getString("USERNAME"); // 申请人
			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (DEPARTMENT_ID != null && !"".equals(DEPARTMENT_ID)) {
				pd.put("DEPARTMENT_ID", DEPARTMENT_ID);
			}
			if (project_id != null && !"".equals(project_id)) {
				pd.put("project_id", project_id);
			}
			if (USERNAME != null && !"".equals(USERNAME)) {
				pd.put("USERNAME", USERNAME);
			}
			if (startTime != null && !"".equals(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (endTime != null && !"".equals(endTime)) {
				pd.put("endTime", endTime + " 00:00:00");
			}
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("序号"); // 1
			titles.add("部门"); // 2
			titles.add("项目"); // 3
			titles.add("虚拟机名称"); // 4
			titles.add("虚拟机配置(cpu/内存/磁盘)"); // 5
			titles.add("平台"); // 6
			titles.add("申请人"); // 7
			titles.add("计费"); // 8
			dataMap.put("titles", titles);
			
			List<PageData> billList = biDatacenterService.listBillBIData(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for (int i = 0; i < billList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", (i + 1)+""); // 1
				vpd.put("var2", billList.get(i).getString("DEPARTMENT_NAME")); // 2
				vpd.put("var3", billList.get(i).getString("project_name")); // 3
				vpd.put("var4", billList.get(i).getString("vm_name")); // 4
				vpd.put("var5", (Integer)billList.get(i).get("cpu")+"C/"+(Long)billList.get(i).get("memory")+"G/"+(Long)billList.get(i).get("datadisk")+"G"); // 5
				vpd.put("var6", billList.get(i).getString("type")); // 6
				vpd.put("var7", billList.get(i).getString("user")); // 7
				vpd.put("var8", (BigDecimal)billList.get(i).get("account")+"元"); // 8
				varList.add(vpd);
			}
			PageData totalPD = new PageData();
			totalPD.put("var7", "总计"); // 7
			// 计算总额
			if (null != billList) {
				BigDecimal totalSum = new BigDecimal(0);
				for (PageData biPD : billList) {
					totalSum = totalSum.add((BigDecimal) biPD.get("account"));
				}
				totalPD.put("var8", totalSum+"元");
			}
			varList.add(totalPD);
			
			dataMap.put("varList", varList);
			
			
			ObjectExcelView erv = new ObjectExcelView(); // 执行excel操作
			mv = new ModelAndView(erv, dataMap);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 资源使用报表列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listResource")
	public ModelAndView listResource(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表ResourceBI");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		// 查询项目列表
		List<PageData> projectList = projectService.listAll(pd);
		mv.addObject("projectList", projectList);

		// 查询部门树
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0", zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));

		// 查询用户列表
		List<PageData> userList = userService.listAllUser(pd);
		mv.addObject("userList", userList);

		String date = pd.getString("date");
		if (null == date) {
			pd.put("date", DateUtil.getDay());
		}
		List<PageData> varList = biDatacenterService.listResourceBIData(pd);
		mv.addObject("varList", varList);

		// 计算资源总配置
		if (null != varList) {
			int cpu = 0;
			long memory = 0;
			long datadisk = 0;
			for (PageData biPD : varList) {
				cpu += (Integer) biPD.get("cpu");
				memory += (Long) biPD.get("memory");
				datadisk += (null == biPD.get("datadisk")) ? 0l : (Long) biPD.get("datadisk");
			}
			pd.put("totalConfig", cpu + "G/" + memory / 1024 + "G/" + datadisk + "G");
		}

		mv.setViewName("bi/resource_list");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 软件台帐报表列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listSoftware")
	public ModelAndView listSoftware(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Software");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		// 查询项目列表
		List<PageData> projectList = projectService.listAll(pd);
		mv.addObject("projectList", projectList);

		// 查询部门树
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0", zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));

		// 查询用户列表
		List<PageData> userList = userService.listAllUser(pd);
		mv.addObject("userList", userList);

		// 查询软件类型列表
		List<PageData> softTypeList = mediumService.listAllSoftType(pd);
		mv.addObject("softTypeList", softTypeList);

		String date = pd.getString("date");
		if (null == date) {
			pd.put("date", DateUtil.getDay());
		}
		List<PageData> varList = biDatacenterService.listSoftwareBIData(pd);
		mv.setViewName("bi/software_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 云主机资产台帐报表列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listVirtual")
	public ModelAndView listVirtual(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Virtual");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		// 查询项目列表
		List<PageData> projectList = projectService.listAll(pd);
		mv.addObject("projectList", projectList);

		// 查询部门树
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0", zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));

		// 查询用户列表
		List<PageData> userList = userService.listAllUser(pd);
		mv.addObject("userList", userList);

		// 查询虚拟机状态列表
		List<Dictionaries> dictionariesList = dictionariesService.listSubDictByBianma("virtual_status");
		mv.addObject("dictionariesList", dictionariesList);

		List<PageData> varList = biBillDayService.listVBiVirtualBill(pd);
		mv.setViewName("bi/virtual_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

}
