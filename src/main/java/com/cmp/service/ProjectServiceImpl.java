package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
	}

}
