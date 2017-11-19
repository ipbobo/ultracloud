package com.cmp.service;

import java.math.BigInteger;
import java.util.List;

import com.cmp.entity.ProjectUserMap;
import com.cmp.entity.UserGroupUserMap;
import com.fh.entity.Page;
import com.fh.util.PageData;

public interface ProjectService {

	public void save(PageData pd) throws Exception;

	public void delete(PageData pd) throws Exception;

	public void edit(PageData pd) throws Exception;

	public List<PageData> list(Page page) throws Exception;

	public List<PageData> listAll(PageData pd) throws Exception;

	public PageData findById(PageData pd) throws Exception;

	public void deleteAll(String[] ArrayDATA_IDS) throws Exception;

	/**
	 * 查询项目下的成员
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<ProjectUserMap> listProjectUserMap(PageData pd) throws Exception;
	
	/**批量删除项目与用户关联
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllProjectUserMap(List<BigInteger> list)throws Exception;
	
	/**批量插入项目与用户关联
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void insertAllProjectUserMap(List<ProjectUserMap> list) throws Exception;
	
	/**按用户组,批量删除项目下的成员
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteByProjectId(String id) throws Exception;
}
