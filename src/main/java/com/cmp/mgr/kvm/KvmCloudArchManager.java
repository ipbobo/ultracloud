package com.cmp.mgr.kvm;

import java.util.ArrayList;
import java.util.List;

import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;

import com.cmp.entity.tcc.TccCloudPlatform;
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
import com.cmp.mgr.bean.CreateVolumeRequest;
import com.cmp.mgr.libvirt.LibvirtConnect;
import com.vmware.vim25.mo.Datacenter;

public class KvmCloudArchManager extends PlatformBindedCloudArchManager {

	private KvmConverters converters = new KvmConverters();

	public KvmCloudArchManager() {
	}

	public KvmCloudArchManager(TccCloudPlatform platform) {
		setPlatform(platform);
	}

	@Override
	public List<Datacenter> getDatacenters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccDatacenter> getDatacenter() {
		return null;
	}

	@Override
	public List<TccCluster> getClusters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccResourcePool> getResourcePools() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccHostMachine> getHostMachines() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccVirtualMachine> getVirtualMachines() {
		List<TccVirtualMachine> ls = new ArrayList<>();

		Connect conn = null;
		try {
			conn = getLibvirtConnect();
			int[] idArr = conn.listDomains();
			for (int id : idArr) {
				Domain domain = conn.domainLookupByID(id);
				ls.add(converters.toVirtualMachine().apply(domain));
				domain.free();
			}

			String[] definedNames = conn.listDefinedDomains();
			for (String name : definedNames) {
				Domain domain = conn.domainLookupByName(name);
				converters.toVirtualMachine().apply(domain);
				domain.free();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			release(conn);
		}

		return ls;
	}
	
	@Override
	public TccVirtualMachine geVirtualMachineByName(String name) {
		return null;
	}

	private Connect getLibvirtConnect() throws LibvirtException {
		return new LibvirtConnect("qemu+tcp://" + platform.getCloudplatformIp() + "/system", false);
	}

	private void release(Connect conn) {
		try {
			conn.close();
		} catch (Throwable e) {
			// ignore
		} finally {
			conn = null;
		}
	}

	@Override
	public List<TccDatastore> getDatastores() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccNetwork> getNetworks() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccVirtualMachine> getVmTemplates() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccVmSnapshot> getVmSnapshots() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void createVirtualMachine(CreateVmRequest request) {
		// TODO
	}

	@Override
	public void startVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			if (dom.isActive() == 0) {
				dom.resume();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void stopVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			if (dom.isActive() == 1) {
				dom.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void rebootVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			if (dom.isActive() == 1) {
				dom.reboot(0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void resetVirtualMachine(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			dom.destroy();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void cloneVirtualMachine(CloneVmRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void createVmSnapshot(String name, String vmName, String desc, boolean memoryFlag) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteSnapshot(String snapshotUUID) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void revertToSnapshot(String snapshotUUID, String hostMachineUUID) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void createVolume(CreateVolumeRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void createVolumeSnapshot(String volumeId, String name, String desc) {
		throw new UnsupportedOperationException();
	}

}
