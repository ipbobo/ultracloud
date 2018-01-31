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
	public int add(VirtualMachine vm) throws Exception {
		return (Integer)dao.save("VirtualMachineMapper.save", vm);
	}

	@Override
	public VirtualMachine findById(String id) throws Exception {
		return (VirtualMachine)dao.findForObject("VirtualMachineMapper.findById", id);
	}

	@Override
	public List<VirtualMachine> findByUser(String user) throws Exception {
		return (List)dao.findForList("VirtualMachineMapper.findByUser", user);
	}

	@Override
	public List<VirtualMachine> findByAppNo(String appNo) throws Exception {
		return (List)dao.findForList("VirtualMachineMapper.findByAppNo", appNo);
	}
	
	@Override
	public int countByProject(String projectId) throws Exception {
		return (int)dao.findForObject("VirtualMachineMapper.countByProject", projectId);
	}

	@Override
	public List<VirtualMachine> findAll() throws Exception {
		return (List)dao.findForList("VirtualMachineMapper.findAll", null);
	}

	@Override
	public List<VirtualMachine> findCurrentDay(String dateNum) throws Exception {
		return (List)dao.findForList("VirtualMachineMapper.findCurrentDay", dateNum);
	}
	
	
}
