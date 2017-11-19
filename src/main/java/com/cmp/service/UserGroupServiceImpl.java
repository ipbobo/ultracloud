/**
 * 
 */
package com.cmp.service;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.UserGroupUserMap;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 用户组业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("UsergroupMapper.save", pd);
	}

	/**
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("UsergroupMapper.delete", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UsergroupMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**批量删除用户组与用户关联
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllUsergroup(List<BigInteger> list) throws Exception {
		dao.delete("UsergroupMapper.deleteAllUsergroup", list);
	}
	
	/**批量插入用户组与用户关联
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void insertAllUsergroup(List<UserGroupUserMap> list) throws Exception {
		dao.batchSave("UsergroupMapper.insertAllUsergroup", list);
	}
	
	/**批量删除用户组与用户关联,按用户组
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteByUsergroupId(BigInteger id) throws Exception {
		dao.delete("UsergroupMapper.deleteByUsergroupId", id);
	}
	
	/**
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("UsergroupMapper.edit", pd);
	}

	/**
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UsergroupMapper.datalistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UsergroupMapper.findById", pd);
	}
	
	/**
	 * 查询用户组下的所有用户关联
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<UserGroupUserMap> listUserGroupUserMap(PageData pd) throws Exception {
		return (List<UserGroupUserMap>) dao.findForList("UsergroupMapper.listUserGroupUserMap", pd);
	}

}
