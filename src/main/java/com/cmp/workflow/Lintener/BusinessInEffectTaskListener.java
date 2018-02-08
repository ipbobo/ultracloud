package com.cmp.workflow.Lintener;

import java.io.Serializable;
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
import com.cmp.service.UserGroupService;
import com.fh.service.system.user.UserManager;
import com.fh.util.PageData;

/**
 * 业务实施中-任务监听器
 * 
 * @author liuweixing
 *
 */
public class BusinessInEffectTaskListener implements Serializable, TaskListener {

	private static final long serialVersionUID = -4371304469035089267L;

	private FixedValue usergroup = null;

	private FixedValue role = null;

	private FixedValue username = null;

	@Override
	public void notify(DelegateTask delegateTask) {
		Set<String> set = new HashSet<String>();
		String appNo = (String) delegateTask.getVariable("appNo");
		WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

		CmpWorkOrderService cmpWorkOrderService = (CmpWorkOrderService) webctx.getBean("cmpWorkOrderService");
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("status", Constants.WORKORDER_STATUS_41);
			cmpWorkOrderService.updateWorkOrder(appNo, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 添加指定的用户组
		if (null != usergroup) {
			assignUserGroup(set, webctx);
		}

		// 添加指定的角色
		if (null != role) {
			assignRole(set, webctx);
		}

		// 添加指定的用户名
		if (null != username) {
			assignUserName(set);
		}

		if (set.size() > 0) {
			delegateTask.addCandidateUsers(set);
		}
	}

	private void assignUserGroup(Set<String> set, WebApplicationContext webctx) {
		String usegroupValue = usergroup.getExpressionText();
		if (null != usegroupValue && !"".equals(usegroupValue)) {
			String[] usergroupids = usegroupValue.split(",");
			UserGroupService userGroupService = (UserGroupService) webctx.getBean("userGroupService");
			for (int i = 0; i < usergroupids.length; i++) {
				PageData pageData = new PageData();
				pageData.put("id", usergroupids[i]);
				List<UserGroupUserMap> userList = null;
				try {
					userList = userGroupService.listUserGroupUserMap(pageData);
					if (null != userList) {
						for (UserGroupUserMap user : userList) {
							set.add(user.getUSERNAME());
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void assignRole(Set<String> set, WebApplicationContext webctx) {
		String roleValue = role.getExpressionText();
		if (null != roleValue && !"".equals(roleValue)) {
			String[] roleids = roleValue.split(",");
			UserManager userService = (UserManager) webctx.getBean("userService");
			for (int i = 0; i < roleids.length; i++) {
				PageData pageData = new PageData();
				pageData.put("ROLE_ID", roleids[i]);
				List<PageData> userList = null;
				try {
					userList = userService.listAllUserByRoldId(pageData);
					if (null != userList) {
						for (PageData userPD : userList) {
							set.add(userPD.getString("USERNAME"));
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void assignUserName(Set<String> set) {
		String usernameValue = username.getExpressionText();
		if (null != usernameValue && !"".equals(usernameValue)) {
			String[] usernames = usernameValue.split(",");
			for (int i = 0; i < usernames.length; i++) {
				set.add(usernames[i]);
			}
		}
	}

	public FixedValue getUsername() {
		return username;
	}

	public void setUsername(FixedValue username) {
		this.username = username;
	}

	public FixedValue getRole() {
		return role;
	}

	public void setRole(FixedValue role) {
		this.role = role;
	}

	public FixedValue getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(FixedValue usergroup) {
		this.usergroup = usergroup;
	}
}
