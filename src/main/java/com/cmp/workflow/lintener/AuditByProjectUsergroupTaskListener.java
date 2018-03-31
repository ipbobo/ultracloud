package com.cmp.workflow.lintener;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cmp.common.Constants;
import com.cmp.entity.UserGroupUserMap;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.ProjectService;
import com.cmp.service.UserGroupService;
import com.cmp.sid.CmpWorkOrder;
import com.fh.util.PageData;

/**
 * 根据项目用户组的审核-任务监听器
 * 
 * @author liuweixing
 *
 */
public class AuditByProjectUsergroupTaskListener implements Serializable, TaskListener {

	private static final long serialVersionUID = -7726817106433701217L;
	
	private FixedValue status = null;

	@Override
	public void notify(DelegateTask delegateTask) {
		String appNo = (String) delegateTask.getVariable("appNo");
		
		WebApplicationContext webctx=ContextLoader.getCurrentWebApplicationContext();
		CmpWorkOrderService cmpWorkOrderService = (CmpWorkOrderService) webctx.getBean("cmpWorkOrderService");
		
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("status", status.getExpressionText());
			cmpWorkOrderService.updateWorkOrder(appNo, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CmpWorkOrder workOrder = null;
		try {
			workOrder = cmpWorkOrderService.findByAppNo(appNo);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		String projectCode = workOrder.getProjectCode();
		ProjectService projectService = (ProjectService) webctx.getBean("projectService");
		PageData pageData = new PageData();
		pageData.put("id", projectCode);
		PageData projectPD = null;
		try {
			projectPD = projectService.findById(pageData);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		UserGroupService userGroupService = (UserGroupService) webctx.getBean("userGroupService");
		pageData.put("id", (BigInteger)projectPD.get("usergroup_id")+"");
		
		try {
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

	public FixedValue getStatus() {
		return status;
	}

	public void setStatus(FixedValue status) {
		this.status = status;
	}
}
