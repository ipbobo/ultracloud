package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("cloudDiskService")
public class CloudDiskServiceImpl implements CloudDiskService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public void save(PageData pd) throws Exception {
		dao.save("CloudDiskMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("CloudDiskMapper.delete", pd);
	}

	public void edit(PageData pd) throws Exception {
		dao.update("CloudDiskMapper.edit", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CloudDiskMapper.datalistPage", page);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CloudDiskMapper.listAll", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CloudDiskMapper.findById", pd);
	}

	public PageData findFhsmsCount(String USERNAME) throws Exception {
		return (PageData) dao.findForObject("CloudDiskMapper.findFhsmsCount", USERNAME);
	}

	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("CloudDiskMapper.deleteAll", ArrayDATA_IDS);
	}

}
