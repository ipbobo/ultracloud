package com.cmp.workflow.Lintener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.cmp.service.CmpWorkOrderService;
import com.cmp.service.UserGroupService;
import com.cmp.sid.CmpWorkOrder;
import com.fh.util.PageData;
import com.fh.util.ServiceHelper;

/**
 * 审核者任务监听器
 * @author liuweixing
 *
 */
public class AuditTaskListener implements TaskListener {

	private static final long serialVersionUID = 667530869305257754L;

	@Override
	public void notify(DelegateTask delegateTask) {
		String appNo = (String) delegateTask.getVariable("workorderId");
		CmpWorkOrderService cmpWorkOrderService = (CmpWorkOrderService) ServiceHelper.getService("cmpWorkOrderService");
		CmpWorkOrder workOrder = null;
		try {
			workOrder = cmpWorkOrderService.findByAppNo(appNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String projectCode = workOrder.getProjectCode();
		UserGroupService userGroupService = (UserGroupService) ServiceHelper.getService("userGroupService");
		PageData pageData = new PageData();
		pageData.put("id", projectCode);
		try {
			pageData = userGroupService.findById(pageData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delegateTask.setAssignee(pageData.getString("id"));

	}
}
