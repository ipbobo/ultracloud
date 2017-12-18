package com.cmp.mgr.impl;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.CloudArchManager;
import com.sun.tools.javac.util.List;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;

public class OpenstatckCloudArchManager implements CloudArchManager {

	@Override
	public List<Datacenter> getDatacenters(TccCloudPlatform platform) {
		return null;
	}

	@Override
	public java.util.List<ClusterComputeResource> getClusters(TccCloudPlatform platform) {
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> getHostMachines(TccCloudPlatform platform) {
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> getVirtualMachines(TccCloudPlatform platform) {
		return null;
	}

	@Override
	public java.util.List<Datastore> getDatastores(TccCloudPlatform platform) {
		return null;
	}

	@Override
	public java.util.List<Network> getNetWorks(TccCloudPlatform platform) {
		return null;
	}

	@Override
	public java.util.List<Datastore> getVmTemplates(TccCloudPlatform platform) {
		return null;
	}

	@Override
	public java.util.List<VirtualMachineSnapshot> getVmSnapshots(TccCloudPlatform platform) {
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> startVirtualMachine(TccCloudPlatform platform,
			String name) {
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> stopVirtualMachine(TccCloudPlatform platform,
			String name) {
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> rebootVirtualMachine(TccCloudPlatform platform,
			String name) {
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> resetVirtualMachine(TccCloudPlatform platform,
			String name) {
		return null;
	}

	@Override
	public java.util.List<VirtualMachine> deleteVirtualMachine(TccCloudPlatform platform,
			String name) {
		return null;
	}

	@Override
	public void createSnapshot() {
		
	}

	@Override
	public void deleteSnapshot() {
		
	}

	@Override
	public void revertToSnapshot() {
		
	}

}
