package com.cmp.controller;

import com.cmp.service.CloudHostService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping(value = "/cloudhost")
public class CloudHostController extends BaseController {

	private static final String SUCCESS = "SUCCESS";

	private static final String FAILURE = "FAILURE";

	String menuUrl = "cloudhost/list.do";

	@Resource(name = "cloudHostService")
	private CloudHostService cloudHostService;

	@Resource(name = "userService")
	private UserService userService;

	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		String keywords = pd.getString("keywords");
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		User userr = userService.getCurrrentUserAndRole();
		//读取session中的用户信息(含角色信息)
		if (userr == null) {
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user == null) {
				mv.setViewName("system/index/login");
				return mv;
			}
			userr = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
			session.setAttribute(Const.SESSION_USERROL, userr);						//存入session
		}

		pd.put("userType", userr.getRole().getTYPE());
		pd.put("FROM_USERNAME", Jurisdiction.getUsername());
		page.setPd(pd);
		List<PageData> varList = cloudHostService.list(page);
		mv.setViewName("console/cloudhost/cloud_host");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);

		if (userr != null) {
			mv.addObject("QX", "audit".equalsIgnoreCase(userr.getRole().getTYPE()) ? 0 : 1);
			mv.addObject("BQX", "executor".equalsIgnoreCase(userr.getRole().getTYPE()) ? 0 : 1);
		} else {
			mv.addObject("QX", 0);
		}

		return mv;
	}

	@RequestMapping(value = "/start")
	public ResponseEntity<String> start(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.start(ls);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}

		return ok(SUCCESS);
	}

	@RequestMapping(value = "/stop")
	public ResponseEntity<String> stop(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.stop(ls);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}

		return ok(SUCCESS);
	}

	@RequestMapping(value = "/reboot")
	public ResponseEntity<String> reboot(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.reboot(ls);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}

		return ok(SUCCESS);
	}

	@RequestMapping(value = "/suspend")
	public ResponseEntity<String> suspend(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.suspend(ls);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}

		return ok(SUCCESS);
	}

	@RequestMapping(value = "/resume")
	public ResponseEntity<String> resume(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.resume(ls);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}

		return ok(SUCCESS);
	}

	@RequestMapping(value = "/destroy")
	public ResponseEntity<String> destroy(@RequestParam("ls[]") List<Integer> ls) {
		try {
			cloudHostService.destroy(ls);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}

		return ok(SUCCESS);
	}

	@RequestMapping(value = "/clone")
	public ResponseEntity<String> clone(Integer id) {
		try {
			cloudHostService.clone(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}

		return ok(SUCCESS);
	}

	@RequestMapping(value = "/snapshot")
	public ResponseEntity<String> snapshot(Integer id) {
		try {
			cloudHostService.snapshot(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ok(FAILURE);
		}

		return ok(SUCCESS);
	}

	@RequestMapping(value = "/console")
	public ModelAndView console(String host, Integer id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("console/cloudhost/console");

		User userr = userService.getCurrrentUserAndRole();
		if (userr != null) {
			mv.addObject("QX", "audit".equalsIgnoreCase(userr.getRole().getTYPE()) ? 0 : 1);
			mv.addObject("BQX", "executor".equalsIgnoreCase(userr.getRole().getTYPE()) ? 0 : 1);
		} else {
			mv.addObject("QX", 0);
		}
		mv.addObject("Ticket", cloudHostService.acquireTicket(id));
		mv.addObject("Host", host);

		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
