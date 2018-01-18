package com.cmp.workflow.Lintener;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;

/**
 * 指定用户-任务监听器
 * 
 * @author liuweixing
 *
 */
public class AssignUserListener implements Serializable, TaskListener {

	private static final long serialVersionUID = 2915025625163813966L;

	private FixedValue username;

	@Override
	public void notify(DelegateTask delegateTask) {
		Set<String> set = new HashSet<String>();

		// 添加指定的用户名
		if (null != username) {
			assignUserName(set);
		}

		if (set.size() > 0) {
			delegateTask.addCandidateUsers(set);
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
}
