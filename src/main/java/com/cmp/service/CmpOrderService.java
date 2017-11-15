package com.cmp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.CmpOrder;
import com.fh.dao.DaoSupport;

@Service
public class CmpOrderService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//新增清单
	public void saveCmpOrder(CmpOrder cmpOrder) throws Exception {
		dao.save("CmpOrderMapper.saveCmpOrder", cmpOrder);
	}
}