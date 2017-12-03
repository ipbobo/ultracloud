package com.cmp.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.CmpOpServe;
import com.fh.dao.DaoSupport;

@Service
public class CmpOpServeService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name="cmpDictService")
	private CmpDictService cmpDictService;

	public void saveCmpOpServe(CmpOpServe cmpOpServe) throws Exception {
		dao.save("CmpOpServeMapper.saveCmpOpServe", cmpOpServe);
	}
	
	
	public CmpOpServe findByOrderNo(String orderNo) throws Exception {
		return (CmpOpServe)dao.findForObject("CmpOpServeMapper.findByOrderNo", orderNo);
	}
	
	public CmpOpServe encase(CmpOpServe opServe) {
		Map serviceTypeMap = cmpDictService.getCmpDictMap("service_type");
		opServe.setServiceTypeName((String)serviceTypeMap.get(opServe.getServiceType()));
		Map operTypeMap = cmpDictService.getCmpDictMap("oper_type_" + opServe.getServiceType());
		opServe.setOperTypeName((String)operTypeMap.get(opServe.getOperType()));
		if (opServe.getStatus() == null || opServe.getStatus().equals("0")) {
			opServe.setOperName("未操作");
		}else {
			opServe.setOperName("操作完成");
		}
		return opServe;
	}
}