package com.cmp.service;

import com.cmp.sid.VirtualMachine;

public interface VirtualMachineService {
	
	public void add(VirtualMachine vm) throws Exception;
	
	public VirtualMachine findById(String id) throws Exception;

}
