package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.DeployedSoft;
import com.fh.dao.DaoSupport;


@Service("deployedSoftService")
public class DeployedSoftServiceImpl implements DeployedSoftService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public int add(DeployedSoft deployedSoft) throws Exception {
		return (Integer)dao.save("DeployedSoftMapper.save", deployedSoft);
	}

	@Override
	public List<DeployedSoft> findByVmId(String vmId) throws Exception {
		return (List<DeployedSoft>)dao.findForList("DeployedSoftMapper.findByVmId", vmId);
	}

}
