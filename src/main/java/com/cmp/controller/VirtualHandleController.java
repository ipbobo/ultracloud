package com.cmp.controller;

import java.io.PrintWriter;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmp.service.resourcemgt.VirtualBindingService;
import com.fh.controller.base.BaseController;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 对虚拟机的相关操作 控制层
 * 
 * @author liuweixing
 *
 */
@Controller
@RequestMapping(value = "/virtualhandle")
public class VirtualHandleController extends BaseController {

	@Resource(name = "virtualBindingService")
	private VirtualBindingService virtualBindingService;
	
	/**
	 * 开机
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/startup")
	public void startup(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "开机");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//Todo
		
		out.write("success");
		out.close();
	}
	
	/**
	 * 关机
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/shutdown")
	public void shutdown(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "关机");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//Todo
		
		out.write("success");
		out.close();
	}
	
	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//Todo
		
		out.write("success");
		out.close();
	}
	
	/**
	 * 重启
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/restart")
	public void restart(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "重启");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//Todo
		
		out.write("success");
		out.close();
	}
	
	/**
	 * 挂起
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/hangup")
	public void hangup(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "挂起");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//Todo
		
		out.write("success");
		out.close();
	}
	
	/**
	 * 恢复
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/recover")
	public void recover(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "恢复");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//Todo
		
		out.write("success");
		out.close();
	}
}
