package com.cmp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.VirtualMachineService;
import com.cmp.service.resourcemgt.VirtualBindingService;
import com.cmp.service.resourcemgt.VirtualMachineSyncService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 存量资源绑定 控制层
 * 
 * @author liuweixing
 *
 */
@Controller
@RequestMapping(value = "/virtualbinding")
public class VirtualBindingController extends BaseController {

	@Resource(name = "virtualBindingService")
	private VirtualBindingService virtualBindingService;

	@Resource(name = "virtualMachineService")
	private VirtualMachineService virtualMachineService;

	@Resource(name = "virtualMachineSyncService")
	private VirtualMachineSyncService virtualMachineSyncService;

	/**
	 * 去绑定页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		virtualBindingService.loadInitData(mv, pd);
		mv.setViewName("resource/virtualbinding_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 弹窗显示未绑定虚拟机列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goListVirtualmachine")
	public ModelAndView goListVirtualmachine(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		page.setShowCount(Integer.MAX_VALUE);
		List<PageData> varList = virtualBindingService.list(page);
		mv.addObject("varList", varList);
		mv.setViewName("resource/virtualbinding_list_virtual");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 保存存量资源绑定
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/binding")
	public ModelAndView binding(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			String cluster_id = request.getParameter("cluster_id");
			String project_id = request.getParameter("project_id");
			String USERNAME = request.getParameter("USERNAME");
			String virtualmachines = request.getParameter("virtualmachines");

			if (null != virtualmachines && !"".equals(virtualmachines)) {
				String ArrayDATA_IDS[] = virtualmachines.split(",");

				List<PageData> vmSyncList = virtualBindingService.datalistByIds(ArrayDATA_IDS);
				if (null != vmSyncList) {
					for (PageData vmSyncPD : vmSyncList) {
						vmSyncPD.put("project_id", project_id);
						vmSyncPD.put("user", USERNAME);
						vmSyncPD.remove("id");
						virtualMachineSyncService.saveVM(vmSyncPD);
					}
				}
			}
			mv.addObject("retMsg", "保存成功!");
		} catch (Exception e) {
			logger.error("保存错误：" + e);
			mv.addObject("retMsg", "保存失败!");
		}
		return mv;
	}

}
