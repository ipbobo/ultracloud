package com.cmp.service;

import java.util.List;

import com.cmp.sid.VirtualMachine;
import com.fh.util.PageData;

public interface VirtualMachineService {
	
	public int add(VirtualMachine vm) throws Exception;
	
	public VirtualMachine findById(String id) throws Exception;
	
	public VirtualMachine findByIp(String ip) throws Exception;

	public List<PageData> searchIp(PageData pd)  throws Exception;
	
	public List<VirtualMachine> findByUser(String user) throws Exception;
	
	public List<VirtualMachine> findByAppNo(String appNo) throws Exception;

	public int countByProject(String projectId) throws Exception;
	
	public List<VirtualMachine> findAll() throws Exception;
	
	public List<VirtualMachine> findCurrentDay(String dateNum) throws Exception;

}
