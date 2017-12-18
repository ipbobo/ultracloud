package com.cmp.mgr;

import java.util.List;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;

public interface CloudArchManager {

	public List<Datacenter> getDatacenters(TccCloudPlatform platform);

	public List<ClusterComputeResource> getClusters(TccCloudPlatform platform);

	public List<VirtualMachine> getHostMachines(TccCloudPlatform platform);

	public List<VirtualMachine> getVirtualMachines(TccCloudPlatform platform);

	public List<Datastore> getDatastores(TccCloudPlatform platform);

	public List<Network> getNetWorks(TccCloudPlatform platform);

	public List<Datastore> getVmTemplates(TccCloudPlatform platform);

	public List<VirtualMachineSnapshot> getVmSnapshots(TccCloudPlatform platform);

	public List<VirtualMachine> startVirtualMachine(TccCloudPlatform platform, String name);

	public List<VirtualMachine> stopVirtualMachine(TccCloudPlatform platform, String name);

	public List<VirtualMachine> rebootVirtualMachine(TccCloudPlatform platform, String name);

	public List<VirtualMachine> resetVirtualMachine(TccCloudPlatform platform, String name);

	public List<VirtualMachine> deleteVirtualMachine(TccCloudPlatform platform, String name);

	public void createSnapshot();

	public void deleteSnapshot();

	public void revertToSnapshot();

}
