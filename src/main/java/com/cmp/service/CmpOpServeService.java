package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.CmpOpServe;
import com.fh.dao.DaoSupport;

@Service
public class CmpOpServeService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;


	public void saveCmpOpServe(CmpOpServe cmpOpServe) throws Exception {
		dao.save("CmpOpServeMapper.saveCmpOpServe", cmpOpServe);
	}
	
}