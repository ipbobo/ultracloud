package com.cmp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CmpWorkOrderService;
import com.cmp.sid.CmpWorkOrder;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;

@Controller
public class CmpWorkOrderController extends BaseController{
	@Resource
	private CmpWorkOrderService cmpWorkOrderService;
	@Resource(name="userService")
	private UserManager userService;
	
	@RequestMapping(value="/queryUserApplyWorkOrderPre")
	public ModelAndView querUserApplyWorkOrderPre() throws Exception{
		Session session = Jurisdiction.getSession();
		User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
		if (userr == null) {
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			userr = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
			session.setAttribute(Const.SESSION_USERROL, userr);						//存入session	
		}
		
		List<CmpWorkOrder> workOrderList = cmpWorkOrderService.selectUserWorkOrder(userr.getUSER_ID());
		
		//获取登录用户类型
		Map<String, String> qxMap = new HashMap<String, String>();
		String userType = userr.getRole().getTYPE();
		if (userType != null && (userType.equals("applicant") || userType.equals("admin")) ) {
			qxMap.put("query", "1");
		}else if (userType != null && userType.equals("audit")) {
			qxMap.put("check", "1");
		}else if (userType != null && userType.equals("executor")) {
			qxMap.put("execute", "1");
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrderList", workOrderList);
		mv.addObject("QX", qxMap); // 右侧按钮权限
		mv.setViewName("workorder/query_user_workorder");
		return mv;
	}
}
