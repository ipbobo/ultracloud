package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("cloudHostService")
public class CloudHostServiceImpl implements CloudHostService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public void save(PageData pd) throws Exception {
		dao.save("CloudHostMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("CloudHostMapper.delete", pd);
	}

	public void edit(PageData pd) throws Exception {
		dao.update("CloudHostMapper.edit", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CloudHostMapper.datalistPage", page);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CloudHostMapper.listAll", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CloudHostMapper.findById", pd);
	}

	public PageData findFhsmsCount(String USERNAME) throws Exception {
		return (PageData) dao.findForObject("CloudHostMapper.findFhsmsCount", USERNAME);
	}

	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("CloudHostMapper.deleteAll", ArrayDATA_IDS);
	}

}
