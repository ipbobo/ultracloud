/**
 * 
 */
package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("UsergroupMapper.save", pd);
	}

	/**
	 * 删除
	 */
	@Override
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
	
	/**
	 * 修改
	 */
	@Override
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

}
