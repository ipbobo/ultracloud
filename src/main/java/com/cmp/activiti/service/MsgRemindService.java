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
	
	@Resource
	ActivitiService activitiService;
	
	/**
	 * 查询用户未完成的任务数
	 * @param userId
	 * @return
	 */
	public int countUserUnfinishTask(String userId, String roleType) {
		if (roleType != null && Role.TYPE_APPLICANT.equals(roleType)) {
			return activitiService.countByUserHistoricProcess(userId, false);
		} else if (roleType != null && Role.TYPE_EXECUTOR.equals(roleType)) {
			return activitiService.countByUserActInst(userId, false);
		} else {
			return 0;
		}
	}
	
	/**
	 * 查询用户完成的任务数
	 * @param userId
	 * @return
	 */
	public int countUserfinishTask(String userId, String roleType) {
		if (roleType != null && Role.TYPE_APPLICANT.equals(roleType)) {
			return activitiService.countByUserHistoricProcess(userId, true);
		} else if (roleType != null && Role.TYPE_EXECUTOR.equals(roleType)) {
			return activitiService.countByUserActInst(userId, true);
		} else {
			return 0;
		}
	}
	
	
	/**
	 * 查询到达的任务数
	 */
	public int countReceiveTask(String userId) {
		Page<TaskBean> page = activitiService.getPersonalTaskList(userId, 1, MAX_NUM_PER_PAGE);
		return page.getTotalCount();
	}
	
}
