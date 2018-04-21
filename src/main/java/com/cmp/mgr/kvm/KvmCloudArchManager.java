package com.cmp.mgr.kvm;

import com.cmp.entity.tcc.*;
import com.cmp.mgr.PlatformBindedCloudArchManager;
import com.cmp.mgr.bean.CloneVmRequest;
import com.cmp.mgr.bean.CreateVmRequest;
import com.cmp.mgr.bean.CreateVolumeRequest;
import com.cmp.mgr.libvirt.LibvirtConnect;
import com.vmware.vim25.mo.Datacenter;
import org.apache.commons.io.IOUtils;
import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.DomainSnapshot;
import org.libvirt.LibvirtException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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
				if (domain.isActive() == 1) {
					ls.add(converters.toVirtualMachine().apply(domain));
				}
				domain.free();
			}

			String[] definedNames = conn.listDefinedDomains();
			for (String name : definedNames) {
				Domain domain = conn.domainLookupByName(name);
				if (domain.isActive() == 1) {
					converters.toVirtualMachine().apply(domain);
				}
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
	public TccVirtualMachine getVirtualMachineByName(String name) {
		try {
			Domain dom = getLibvirtConnect().domainLookupByName(name);
			return converters.toVirtualMachine().apply(dom);
		} catch (LibvirtException e) {
			throw new RuntimeException(e);
		}
	}

	private Connect getLibvirtConnect() throws LibvirtException {
		return new LibvirtConnect("qemu+tcp://" + platform.getCloudplatformIp() + "/system", false);
	}

	private void release(Connect conn) {
		try {
			conn.close();
		} catch (Throwable e) {
			// ignore
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
	public void createVirtualMachine(CreateVmRequest request) {
		try {
			String xmls = IOUtils.toString(getClass().getClassLoader()
					.getResourceAsStream("kvmdef/kvm.xml"), "utf8");

			// @formatter:off
			String domainDef =
				xmls.replace("${name}", request.getVmName())
					.replace("${uuid}", UUID.randomUUID().toString())
					.replace("${memory}", String.valueOf(request.getMemSizeMB()))
					.replace("${vcpu}", String.valueOf(request.getCupCount()))
					.replace("${disk-file-name}", request.getVmName())
					.replace("${image-path}", request.getImagePath());
			// @formatter:on

			getLibvirtConnect().domainCreateXML(domainDef, 0);
		} catch (LibvirtException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void startVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			if (dom.isActive() != 1) {
				dom.create();
			}
		} catch (LibvirtException e) {
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
		} catch (LibvirtException e) {
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
		} catch (LibvirtException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void suspendVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			if (dom.isActive() == 1) {
				dom.suspend();
			}
		} catch (LibvirtException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void resumeVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			if (dom.isActive() == 1) {
				dom.resume();
			}
		} catch (LibvirtException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void resetVirtualMachine(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void destroyVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			dom.destroy();
		} catch (LibvirtException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String cloneVirtualMachine(CloneVmRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccVmSnapshot> getVmSnapshots(String uuid) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByUUID(UUID.fromString(uuid));

			Function<String, DomainSnapshot> snapshotLookupByName = name -> {
				try {
					return dom.snapshotLookupByName(name);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			};

			return Arrays.stream(dom.snapshotListNames()).map(snapshotLookupByName)
					.map(converters.toSnapshot()).collect(Collectors.toList());
		} catch (LibvirtException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createVmSnapshot(String name, String vmName, String desc, boolean memoryFlag) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			dom.snapshotCreateXML(dom.getXMLDesc(0));
		} catch (LibvirtException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteSnapshot(String snapshotUUID) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByUUID(UUID.fromString(snapshotUUID));
			dom.snapshotLookupByName(snapshotUUID).delete(0);
		} catch (LibvirtException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void revertToSnapshot(String snapshotUUID, String hostMachineUUID) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByUUID(UUID.fromString(snapshotUUID));
			DomainSnapshot snapshot = dom.snapshotLookupByName(snapshotUUID);
			dom.revertToSnapshot(snapshot);
		} catch (LibvirtException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createVolume(CreateVolumeRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void createVolumeSnapshot(String volumeId, String name, String desc) {
		throw new UnsupportedOperationException();
	}

	@Override
	public TccCapability getCapability() {
		return null;
	}

	@Override public String acquireTicket(String vmName) {
		throw new UnsupportedOperationException();
	}
}
