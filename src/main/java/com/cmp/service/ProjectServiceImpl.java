package com.cmp.service;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.ProjectUserMap;
import com.cmp.sid.CmpDict;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public void save(PageData pd) throws Exception {
		dao.save("ProjectMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("ProjectMapper.delete", pd);
		dao.delete("ProjectMapper.deleteProjectUserMap", pd);
	}

	public void edit(PageData pd) throws Exception {
		dao.update("ProjectMapper.edit", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ProjectMapper.datalistPage", page);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ProjectMapper.listAll", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ProjectMapper.findById", pd);
	}

	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("ProjectMapper.deleteAll", ArrayDATA_IDS);
		dao.delete("ProjectMapper.deleteAllProjectUserMapByProjectId", ArrayDATA_IDS);
	}
	
	/**
	 * 查询项目下的成员
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectUserMap> listProjectUserMap(PageData pd) throws Exception {
		return (List<ProjectUserMap>) dao.findForList("ProjectMapper.listProjectUserMap", pd);
	}
	
	/**批量删除项目与用户关联
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllProjectUserMap(List<BigInteger> list)throws Exception {
		dao.delete("ProjectMapper.deleteAllProjectUserMap", list);
	}
	
	/**批量插入项目与用户关联
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void insertAllProjectUserMap(List<ProjectUserMap> list) throws Exception {
		dao.batchSave("ProjectMapper.insertAllProjectUserMap", list);
	}
	
	/**按用户组,批量删除项目下的成员
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteByProjectId(String id) throws Exception {
		dao.delete("ProjectMapper.deleteByProjectId", id);
	}

	//项目列表查询
	@SuppressWarnings("unchecked")
	public List<CmpDict> getProjectList() throws Exception {
		return (List<CmpDict>) dao.findForList("ProjectMapper.getProjectList", null);
	}
}
