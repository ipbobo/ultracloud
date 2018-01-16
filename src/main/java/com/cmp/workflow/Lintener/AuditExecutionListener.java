package com.cmp.workflow.Lintener;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cmp.entity.UserGroupUserMap;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.UserGroupService;
import com.cmp.sid.CmpWorkOrder;
import com.fh.util.PageData;

/**
 * 审核执行监听器
 * 
 * @author liuweixing
 *
 */
public class AuditExecutionListener implements ExecutionListener {

	private static final long serialVersionUID = 7559229982234452018L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		Map<String, Object> map = execution.getVariables();
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
	}

}
