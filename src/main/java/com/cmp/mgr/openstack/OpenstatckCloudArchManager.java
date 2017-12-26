package com.cmp.mgr.openstack;

import java.util.List;

import com.cmp.entity.tcc.TccCluster;
import com.cmp.entity.tcc.TccDatacenter;
import com.cmp.entity.tcc.TccDatastore;
import com.cmp.entity.tcc.TccHostMachine;
import com.cmp.entity.tcc.TccNetwork;
import com.cmp.entity.tcc.TccResourcePool;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.entity.tcc.TccVmSnapshot;
import com.cmp.mgr.PlatformBindedCloudArchManager;
import com.cmp.mgr.bean.CloneVmRequest;
import com.cmp.mgr.bean.CreateVmRequest;
import com.vmware.vim25.mo.Datacenter;

public class OpenstatckCloudArchManager extends PlatformBindedCloudArchManager {

	@Override
	public List<Datacenter> getDatacenters() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<TccDatacenter> getDatacenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TccCluster> getClusters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TccResourcePool> getResourcePools() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TccHostMachine> getHostMachines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TccVirtualMachine> getVirtualMachines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TccDatastore> getDatastores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TccNetwork> getNetworks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TccVirtualMachine> getVmTemplates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TccVmSnapshot> getVmSnapshots() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void createVirtualMachine(CreateVmRequest request) {
		// TODO Auto-generated method stub
	}

	@Override
	public void startVirtualMachine(String name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void stopVirtualMachine(String name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void rebootVirtualMachine(String name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void resetVirtualMachine(String name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteVirtualMachine(String name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void cloneVirtualMachine(CloneVmRequest request) {
		// TODO Auto-generated method stub
	}

	@Override
	public void createSnapshot(String name, String vmName, String desc, boolean memoryFlag) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteSnapshot(String snapshotUUID) {
		// TODO Auto-generated method stub
	}

	@Override
	public void revertToSnapshot(String snapshotUUID, String hostMachineUUID) {
		// TODO Auto-generated method stub
	}

}
