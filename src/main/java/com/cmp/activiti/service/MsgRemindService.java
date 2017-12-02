package com.cmp.activiti.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.activiti.bean.TaskBean;
import com.cmp.util.Page;
import com.fh.entity.system.Role;

/**
 * 消息提醒服务类
 *
 */
@Service
public class MsgRemindService {
	
	public static final int MAX_NUM_PER_PAGE = 5000;   //消息提醒每页最大查询数
	
	@Resource(name="activitiService")
	private ActivitiService activitiService;
	
	/**
	 * 查询用户未完成的任务数
	 * @param userId
	 * @return
	 */
	public int countUserUnfinishTask(String userName, String roleType) {
		if (roleType != null && Role.TYPE_APPLICANT.equals(roleType)) {
			return activitiService.countByUserHistoricProcess(userName, false);
		} else if (roleType != null && Role.TYPE_EXECUTOR.equals(roleType)) {
			return activitiService.countByUserActInst(userName, false);
		} else {
			return 0;
		}
	}
	
	/**
	 * 查询用户完成的任务数
	 * @param userName
	 * @return
	 */
	public int countUserfinishTask(String userName, String roleType) {
		if (roleType != null && Role.TYPE_APPLICANT.equals(roleType)) {
			return activitiService.countByUserHistoricProcess(userName, true);
		} else if (roleType != null && Role.TYPE_EXECUTOR.equals(roleType)) {
			return activitiService.countByUserActInst(userName, true);
		} else {
			return 0;
		}
	}
	
	
	/**
	 * 查询到达的任务数
	 */
	public int countReceiveTask(String userName) {
		Page<TaskBean> page = activitiService.getPersonalTaskList(userName, 1, MAX_NUM_PER_PAGE);
		return page.getTotalCount();
	}
	
}
