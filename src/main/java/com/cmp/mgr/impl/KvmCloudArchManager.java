package com.cmp.mgr.impl;

import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;

public class KvmCloudArchManager extends PlatformBindedCloudArchManager {

	@Override
	public java.util.List<Datacenter> getDatacenters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<ClusterComputeResource> getClusters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> getHostMachines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> getVirtualMachines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<Datastore> getDatastores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<Network> getNetWorks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<Datastore> getVmTemplates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<VirtualMachineSnapshot> getVmSnapshots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> startVirtualMachine(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> stopVirtualMachine(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> rebootVirtualMachine(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> resetVirtualMachine(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> deleteVirtualMachine(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createSnapshot() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSnapshot() {
		// TODO Auto-generated method stub

	}

	@Override
	public void revertToSnapshot() {
		// TODO Auto-generated method stub

	}

}
