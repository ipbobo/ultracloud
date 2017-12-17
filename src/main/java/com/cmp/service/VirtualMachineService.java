package com.cmp.service;

import java.util.List;

import com.cmp.sid.VirtualMachine;

public interface VirtualMachineService {
	
	public int add(VirtualMachine vm) throws Exception;
	
	public VirtualMachine findById(String id) throws Exception;

	public List<VirtualMachine> findByUser(String user) throws Exception;
	
	public List<VirtualMachine> findByAppNo(String appNo) throws Exception;

}
