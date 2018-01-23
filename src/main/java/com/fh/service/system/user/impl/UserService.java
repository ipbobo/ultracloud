package com.fh.service.system.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 系统用户
 * 
 * @author 修改时间：
 */
@Service("userService")
public class UserService implements UserManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 登录判断
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByNameAndPwd(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.getUserInfo", pd);
	}

	/**
	 * 更新登录时间
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd) throws Exception {
		dao.update("UserMapper.updateLastLogin", pd);
	}

	public User getCurrrentUserAndRole() throws Exception {
		Session session = Jurisdiction.getSession();
		User userr = (User) session.getAttribute(Const.SESSION_USERROL);
		if (userr == null) {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (user != null) {
				userr = getUserAndRoleById(user.getUSER_ID());
				session.setAttribute(Const.SESSION_USERROL, userr);
			}
		}

		return userr;
	}

	/**
	 * 通过用户ID获取用户信息和角色信息
	 * 
	 * @param USER_ID
	 * @return
	 * @throws Exception
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception {
		return (User) dao.findForObject("UserMapper.getUserAndRoleById", USER_ID);
	}

	/**
	 * 通过USERNAEME获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUsername", pd);
	}

	/**
	 * 通过USERNAEME获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsernameForUpdate(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUsernameForUpdate", pd);
	}

	/**
	 * 列出某角色下的所有用户
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listAllUserByRoldId", pd);

	}

	/**
	 * 保存用户IP
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd) throws Exception {
		dao.update("UserMapper.saveIP", pd);
	}

	/**
	 * 用户列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUsers(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.userlistPage", page);
	}

	/**
	 * 用户列表(弹窗选择用)
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUsersBystaff(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.userBystafflistPage", page);
	}

	/**
	 * 通过邮箱获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUE", pd);
	}

	/**
	 * 通过编号获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUN(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUN", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findById", pd);
	}

	/**
	 * 保存用户
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd) throws Exception {
		dao.save("UserMapper.saveU", pd);
	}

	/**
	 * 保存用户(新)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("UserMapper.save", pd);
	}

	/**
	 * 修改用户
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd) throws Exception {
		dao.update("UserMapper.editU", pd);
	}

	/**
	 * 修改用户(新)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("UserMapper.edit", pd);
	}

	/**
	 * 删除用户
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd) throws Exception {
		dao.delete("UserMapper.deleteU", pd);
	}

	/**
	 * 批量删除用户
	 * 
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS) throws Exception {
		dao.delete("UserMapper.deleteAllU", USER_IDS);
	}

	/**
	 * 用户列表(全部)
	 * 
	 * @param USER_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listAllUser", pd);
	}

	/**
	 * 获取总数
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData getUserCount(String value) throws Exception {
		return (PageData) dao.findForObject("UserMapper.getUserCount", value);
	}

	/**
	 * 列出用户组下的所有用户
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<User> listUserBypd(PageData pd) throws Exception {
		return (List<User>) dao.findForList("UserMapper.listUserBypd", pd);
	}

	/**
	 * 列出已加入用户组的所有用户
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllInUserByUserGroupId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listAllInUserByUserGroupId", pd);
	}

	/**
	 * 列出未加入用户组的同角色的所有用户
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllOutUserByPdId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listAllOutUserByPdId", pd);
	}

	/**
	 * 列出已加入该项目的所有用户
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllInProjectByProjectId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listAllInProjectByProjectId", pd);
	}

	/**
	 * 列出未加入该项目的所有用户
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllOutProjectByPdId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listAllOutProjectByPdId", pd);
	}

}
