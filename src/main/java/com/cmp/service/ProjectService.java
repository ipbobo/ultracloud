package com.cmp.service;

import java.util.List;

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

}
