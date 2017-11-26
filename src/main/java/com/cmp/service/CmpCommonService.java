package com.cmp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;

@Service
public class CmpCommonService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//获取申请编号
	public String getAppNo(String seqName) throws Exception {
		return (String)dao.findForObject("CmpCommonMapper.getAppNo", seqName);
	}
}