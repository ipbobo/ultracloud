package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
public class CmpLogService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//日志列表分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getLogPageList(Page page) throws Exception{
		return (List<PageData>)dao.findForList("CmpLogMapper.getLogPageList", page);
	}
}