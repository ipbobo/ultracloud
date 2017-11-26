package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("snapshotService")
public class SnapshotServiceImpl implements SnapshotService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public void save(PageData pd) throws Exception {
		dao.save("SnapshotMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("SnapshotMapper.delete", pd);
	}

	public void edit(PageData pd) throws Exception {
		dao.update("SnapshotMapper.edit", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("SnapshotMapper.datalistPage", page);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("SnapshotMapper.listAll", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("SnapshotMapper.findById", pd);
	}

	public PageData findFhsmsCount(String USERNAME) throws Exception {
		return (PageData) dao.findForObject("SnapshotMapper.findFhsmsCount", USERNAME);
	}

	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("SnapshotMapper.deleteAll", ArrayDATA_IDS);
	}

}
