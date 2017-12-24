package com.cmp.mgr.openstack;

import java.util.List;

import com.cmp.mgr.PlatformBindedCloudArchManager;
import com.cmp.mgr.bean.CloneVmRequest;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;

public class OpenstatckCloudArchManager extends PlatformBindedCloudArchManager {

	@Override
	public List<Datacenter> getDatacenters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClusterComputeResource> getClusters() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ResourcePool> getResourcePools() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HostSystem> getHostMachines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VirtualMachine> getVirtualMachines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Datastore> getDatastores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Network> getNetworks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VirtualMachine> getVmTemplates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VirtualMachineSnapshot> getVmSnapshots() {
		// TODO Auto-generated method stub
		return null;
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
