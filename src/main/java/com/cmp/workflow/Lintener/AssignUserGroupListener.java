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

import com.cmp.entity.UserGroupUserMap;
import com.cmp.service.UserGroupService;
import com.fh.util.PageData;

/**
 * 指定用户-任务监听器
 * 
 * @author liuweixing
 *
 */
public class AssignUserGroupListener implements Serializable, TaskListener {

	private static final long serialVersionUID = 2915025625163813966L;

	private FixedValue usergroup;

	@Override
	public void notify(DelegateTask delegateTask) {
		Set<String> set = new HashSet<String>();
		WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

		// 添加指定的用户组
		if (null != usergroup) {
			assignUserGroup(set, webctx);
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

	public FixedValue getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(FixedValue usergroup) {
		this.usergroup = usergroup;
	}
}
