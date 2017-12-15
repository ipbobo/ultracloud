package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.VirtualMachine;
import com.fh.dao.DaoSupport;

@Service("virtualMachineService")
public class VirtualMachineServiceImpl implements VirtualMachineService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public void add(VirtualMachine vm) throws Exception {
		dao.save("VirtualMachineMapper.save", vm);
	}

	@Override
	public VirtualMachine findById(String id) throws Exception {
		return (VirtualMachine)dao.findForObject("VirtualMachineMapper.findById", id);
	}

	@Override
	public List<VirtualMachine> findByUser(String user) throws Exception {
		return (List)dao.findForList("VirtualMachineMapper.findByUser", user);
	}
}
