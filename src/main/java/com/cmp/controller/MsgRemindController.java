/**
 * 
 */
package com.cmp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmp.activiti.bean.TaskBean;
import com.cmp.activiti.service.ActivitiService;
import com.cmp.activiti.service.MsgRemindService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.sid.CmpWorkOrder;
import com.cmp.util.Page;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

@Controller
public class MsgRemindController extends BaseController{
	
	@Resource(name="activitiService")
	private ActivitiService activitiService;
	
	@Resource(name="msgRemindService")
	private MsgRemindService msgRemindService;
	
	@Resource
	private CmpWorkOrderService cmpWorkOrderService;
	
	/**
	 * 查询个人任务
	 * @param userId
	 * @return map对象  用户任务集合
	 * @throws Exception 
	 */
	@RequestMapping(value="/main/queryTasks")
	@ResponseBody
	public Object queryPersonalTask() throws Exception {
		Map<String,Object> outmap = new HashMap<String,Object>();
		int totalTaskNo = 0;
		String errInfo = "success";
		Session session = Jurisdiction.getSession();
		User userr = (User)session.getAttribute(Const.SESSION_USERROL);
		outmap.put("user", userr);
		String userName = userr.getUSERNAME();
		
		com.fh.entity.Page page = new com.fh.entity.Page();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USERNAME", userr.getUSERNAME());
		page.setPd(pd);
		page.setCurrentPage(1);
		page.setShowCount(10000);
		List<PageData> currentdayWorkOrderList = new ArrayList<PageData>();
		currentdayWorkOrderList = cmpWorkOrderService.queryUserCurrentdayWorkorder(page);
		List<PageData> todoWorkOrderList = new ArrayList<PageData>();
		todoWorkOrderList = cmpWorkOrderService.queryUserToDoWorkorder(page);
		int toDoNum = 0;
		int dealNum = 0;
		int currentdayNum = 0;
		//根据用户类型不同，查询不同的任务
		if (currentdayWorkOrderList != null & currentdayWorkOrderList.size() >0 ) {
			currentdayNum = currentdayWorkOrderList.size();
		}
		if (todoWorkOrderList != null & todoWorkOrderList.size() >0 ) {
			toDoNum = todoWorkOrderList.size();
		} 
		
		
		
		//根据用户类型不同，查询不同的任务
//		if (userr.getRole().getTYPE().equals(Role.TYPE_APPLICANT)) {
//			//获取退回任务数
//			int returnedTaskNum = msgRemindService.countReceiveTask(userName);
//			outmap.put("returnedTaskNum", returnedTaskNum);
//			//获取处理中任务数
//			int unfinishTaskNum = msgRemindService.countUserUnfinishTask(userName, userr.getRole().getTYPE()) - returnedTaskNum;
//			outmap.put("unfinishTaskNum", unfinishTaskNum);
//			//获取工单完成任务数
//			int finishedTaskNum = msgRemindService.countUserfinishTask(userName, userr.getRole().getTYPE());
//			outmap.put("finishedTaskNum", finishedTaskNum);
//			totalTaskNo = unfinishTaskNum + finishedTaskNum;
//		}
//		if (userr.getRole().getTYPE().equals(Role.TYPE_AUDIT)) {
//			//获取待审批任务数
//			int dealingTaskNum = msgRemindService.countReceiveTask(userName);
//			outmap.put("dealingTaskNum", dealingTaskNum);
//			totalTaskNo = dealingTaskNum;
//		}
//		
//		if (userr.getRole().getTYPE().equals(Role.TYPE_EXECUTOR)) {
//			//获取待处理任务数
//			int dealingTaskNum = msgRemindService.countReceiveTask(userName);
//			outmap.put("dealingTaskNum", dealingTaskNum);
//			//获取处理中任务
//			int unfinishTaskNum = msgRemindService.countUserUnfinishTask(userName, userr.getRole().getTYPE());
//			outmap.put("unfinishTaskNum", unfinishTaskNum);
//			//获取工单完成任务数
//			int finishedTaskNum = msgRemindService.countUserfinishTask(userName, userr.getRole().getTYPE());
//			outmap.put("finishedTaskNum", finishedTaskNum);
//			totalTaskNo = unfinishTaskNum + finishedTaskNum;
//		}
//		
//		if (userr.getRole().getTYPE().equals(Role.TYPE_ADMIN)) {
//			//获取待处理任务
//			//管理员无任务可获取 
//			
//		}
	
		outmap.put("toDoNum", toDoNum);
		outmap.put("currentdayNum", currentdayNum);
		outmap.put("roleType", userr.getRole().getTYPE());
		//返回查询到的任务
		return outmap;
	}
	
}
