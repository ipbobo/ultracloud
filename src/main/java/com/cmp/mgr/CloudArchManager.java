package com.cmp.mgr;

import java.util.List;

import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.mgr.bean.CloneVmRequest;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;

public interface CloudArchManager {

	public List<Datacenter> getDatacenters();

	public List<ClusterComputeResource> getClusters();
	
	public List<ResourcePool> getResourcePools();

	public List<HostSystem> getHostMachines();

	public List<TccVirtualMachine> getVirtualMachines();

	public List<Datastore> getDatastores();

	public List<Network> getNetworks();

	public List<VirtualMachine> getVmTemplates();

	public List<VirtualMachineSnapshot> getVmSnapshots();

	public void startVirtualMachine(String name);

	public void stopVirtualMachine(String name);

	public void rebootVirtualMachine(String name);

	public void resetVirtualMachine(String name);

	public void deleteVirtualMachine(String name);
	
	public void cloneVirtualMachine(CloneVmRequest request);

	public void createSnapshot(String name, String vmName, String desc, boolean memoryFlag);

	public void deleteSnapshot(String snapshotUUID);

	public void revertToSnapshot(String snapshotUUID, String hostMachineUUID);

}
