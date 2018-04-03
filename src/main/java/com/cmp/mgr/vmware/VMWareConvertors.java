package com.cmp.mgr.vmware;

import com.cmp.entity.tcc.*;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.GuestInfo;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.mo.*;

import java.util.function.Function;

public class VMWareConvertors {

	public Function<Datacenter, TccDatacenter> toDatacenter() {
		return dc -> {
			TccDatacenter tdc = new TccDatacenter();
			tdc.setHypervisorType("vmware");
			tdc.setName(dc.getName());

			return tdc;
		};
	}

	public Function<ClusterComputeResource, TccCluster> toCluster() {
		return ccr -> {
			TccCluster cluster = new TccCluster();
			cluster.setClusterName(ccr.getName());

			return cluster;
		};
	}

	public Function<ResourcePool, TccResourcePool> toResourcePool() {
		return rp -> {
			TccResourcePool trp = new TccResourcePool();
			trp.setName(rp.getName());

			return trp;
		};
	}

	public Function<HostSystem, TccHostMachine> toHostMachine() {
		return hs -> {
			TccHostMachine hm = new TccHostMachine();
			hm.setHostName(hs.getName());
			hm.setHostIp("");

			return hm;
		};
	}

	public Function<VirtualMachine, TccVirtualMachine> toVirtualMachine() {
		return vm -> {
			VirtualMachineConfigInfo config = vm.getConfig();

			TccVirtualMachine tccVm = new TccVirtualMachine();
			tccVm.setUUID(config.getUuid());
			tccVm.setName(vm.getName());

			tccVm.setVcpus(config.getHardware().getNumCPU());
			tccVm.setMemory(config.getHardware().getMemoryMB());

			GuestInfo guestInfo = vm.getGuest();
			tccVm.setIpAddress(guestInfo.getIpAddress());
			tccVm.setState(guestInfo.getGuestState());

			return tccVm;
		};
	}

	public Function<Datastore, TccDatastore> toDatastore() {
		return ds -> {
			TccDatastore tds = new TccDatastore();
			tds.setName(ds.getName());

			DatastoreSummary summary = ds.getSummary();
			tds.setFreeSpace(summary.getFreeSpace());
			tds.setCapacity(summary.getCapacity());
			tds.setMaxFileSize(ds.getInfo().getMaxFileSize());

			return tds;
		};
	}

	public Function<Network, TccNetwork> toNetwork() {
		return nw -> {
			TccNetwork tnw = new TccNetwork();
			tnw.setNetworkName(nw.getName());
			tnw.setNetworkTag(nw.getMOR().getVal());

			return tnw;
		};
	}

	public Function<VirtualMachineSnapshot, TccVmSnapshot> toVmSnapshot() {
		return vms -> {
			TccVmSnapshot snapshot = new TccVmSnapshot();
			snapshot.setName(vms.getMOR().getVal());

			return snapshot;
		};
	}

}
