package com.cmp.service;

import java.util.List;

import com.cmp.entity.DeployedSoft;

public interface DeployedSoftService {

	public int add(DeployedSoft deployedSoft) throws Exception;
	
	public List<DeployedSoft> findByVmId(String vmId) throws Exception;
	
	public DeployedSoft findById(String id) throws Exception;
}
