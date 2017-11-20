package com.cmp.service;

import java.util.List;

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
	
	//新增套餐清单
	public void addPckgList(CmpOrder cmpOrder) throws Exception {
		dao.save("CmpOrderMapper.addPckgList", cmpOrder);
	}
	
	//套餐列表查询
	@SuppressWarnings("unchecked")
	public List<CmpOrder> getPckgList() throws Exception {
		return (List<CmpOrder>)dao.findForList("CmpOrderMapper.getPckgList", null);
	}
	
	//删除套餐
	public void delPckg(String pckgId) throws Exception {
		dao.delete("CmpOrderMapper.delPckg", pckgId);
	}
}