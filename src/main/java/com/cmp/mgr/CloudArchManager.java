package com.cmp.mgr;

import java.util.List;

import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;

public interface CloudArchManager {

	public List<Datacenter> getDatacenters();

	public List<ClusterComputeResource> getClusters();

	public List<HostSystem> getHostMachines();

	public List<VirtualMachine> getVirtualMachines();

	public List<Datastore> getDatastores();

	public List<Network> getNetworks();

	public List<VirtualMachine> getVmTemplates();

	public List<VirtualMachineSnapshot> getVmSnapshots();

	public List<VirtualMachine> startVirtualMachine(String name);

	public List<VirtualMachine> stopVirtualMachine(String name);

	public List<VirtualMachine> rebootVirtualMachine(String name);

	public List<VirtualMachine> resetVirtualMachine(String name);

	public List<VirtualMachine> deleteVirtualMachine(String name);

	public void createSnapshot();

	public void deleteSnapshot();

	public void revertToSnapshot();

}
