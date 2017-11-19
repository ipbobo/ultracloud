package com.cmp.service;

import java.math.BigInteger;
import java.util.List;

import com.cmp.entity.UserGroupUserMap;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 用户组业务层接口
 * 
 * @author liuweixing
 *
 */
public interface UserGroupService {

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**批量删除用户组与用户关联
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllUsergroup(List<BigInteger> list)throws Exception;
	
	/**批量插入用户组与用户关联
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void insertAllUsergroup(List<UserGroupUserMap> list) throws Exception;
	
	
	/**批量删除用户组与用户关联,按用户组
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteByUsergroupId(BigInteger id) throws Exception;
	
	
	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 查询用户组下的所有用户关联
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<UserGroupUserMap> listUserGroupUserMap(PageData pd) throws Exception;

}
