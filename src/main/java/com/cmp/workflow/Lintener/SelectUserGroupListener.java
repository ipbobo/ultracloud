package com.cmp.workflow.Lintener;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cmp.entity.UserGroupUserMap;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.UserGroupService;
import com.cmp.sid.CmpWorkOrder;
import com.fh.util.PageData;

/**
 * 选择用户组工作流监听器
 * 
 * @author liuweixing
 *
 */
public class SelectUserGroupListener implements TaskListener {

	private static final long serialVersionUID = 2915025625163813966L;

	@Override
	public void notify(DelegateTask delegateTask) {
		Map<String, Object> map = delegateTask.getVariables();
		//String usergroup = (String) delegateTask.getVariable("usergroup");
		WebApplicationContext webctx=ContextLoader.getCurrentWebApplicationContext();
		CmpWorkOrderService cmpWorkOrderService = (CmpWorkOrderService) webctx.getBean("cmpWorkOrderService");
		CmpWorkOrder workOrder = null;
		
		UserGroupService userGroupService = (UserGroupService) webctx.getBean("userGroupService");
		
		try {
			PageData pageData = new PageData();
			pageData.put("id", "3");
			List<UserGroupUserMap> userList = userGroupService.listUserGroupUserMap(pageData);
			if (null != userList) {
				Set<String> set = new HashSet<String>();
				for (UserGroupUserMap user : userList) {
					set.add(user.getUSERNAME());
				}
				delegateTask.addCandidateUsers(set);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
