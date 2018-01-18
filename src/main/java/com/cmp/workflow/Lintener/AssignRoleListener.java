package com.cmp.workflow.Lintener;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fh.service.system.user.UserManager;
import com.fh.util.PageData;

/**
 * 指定用户-任务监听器
 * 
 * @author liuweixing
 *
 */
public class AssignRoleListener implements Serializable, TaskListener {

	private static final long serialVersionUID = 2915025625163813966L;

	private FixedValue role;

	@Override
	public void notify(DelegateTask delegateTask) {
		Set<String> set = new HashSet<String>();
		WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

		// 添加指定的角色
		if (null != role) {
			assignRole(set, webctx);
		}

		if (set.size() > 0) {
			delegateTask.addCandidateUsers(set);
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

	public FixedValue getRole() {
		return role;
	}

	public void setRole(FixedValue role) {
		this.role = role;
	}
}
