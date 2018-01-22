package com.cmp.mgr;

import java.util.List;
import java.util.function.Supplier;

import com.cmp.entity.tcc.TccCluster;
import com.cmp.entity.tcc.TccDatacenter;
import com.cmp.entity.tcc.TccDatastore;
import com.cmp.entity.tcc.TccHostMachine;
import com.cmp.entity.tcc.TccNetwork;
import com.cmp.entity.tcc.TccResourcePool;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.entity.tcc.TccVmSnapshot;
import com.cmp.mgr.bean.CloneVmRequest;
import com.cmp.mgr.bean.CreateVmRequest;
import com.cmp.mgr.bean.CreateVolumeRequest;
import com.vmware.vim25.mo.Datacenter;

public interface CloudArchManager {

	default Supplier<RuntimeException> error(String msg) {
		return () -> new RuntimeException(msg);
	}

	public List<Datacenter> getDatacenters();

	public List<TccDatacenter> getDatacenter();

	public List<TccCluster> getClusters();

	public List<TccResourcePool> getResourcePools();

	public List<TccHostMachine> getHostMachines();

	public List<TccVirtualMachine> getVirtualMachines();
	
	public TccVirtualMachine getVirtualMachineByName(String name);

	public List<TccDatastore> getDatastores();

	public List<TccNetwork> getNetworks();

	public List<TccVirtualMachine> getVmTemplates();

	public List<TccVmSnapshot> getVmSnapshots();

	public void createVirtualMachine(CreateVmRequest request);

	public void startVirtualMachine(String name);

	public void stopVirtualMachine(String name);

	public void rebootVirtualMachine(String name);
	
	public void suspendVirtualMachine(String name);
	
	public void resumeVirtualMachine(String name);

	public void resetVirtualMachine(String name);

	public void destroyVirtualMachine(String name);

	public void cloneVirtualMachine(CloneVmRequest request);

	public void createVmSnapshot(String name, String vmName, String desc, boolean memoryFlag);

	public void deleteSnapshot(String snapshotUUID);

	public void revertToSnapshot(String snapshotUUID, String hostMachineUUID);

	public void createVolume(CreateVolumeRequest request);

	public void createVolumeSnapshot(String volumeId, String name, String desc);

}
