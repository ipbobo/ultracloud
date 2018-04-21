package com.cmp.mgr.openstack;

import com.cmp.entity.tcc.*;
import com.cmp.mgr.PlatformBindedCloudArchManager;
import com.cmp.mgr.bean.CloneVmRequest;
import com.cmp.mgr.bean.CloneVmResponse;
import com.cmp.mgr.bean.CreateVmRequest;
import com.cmp.mgr.bean.CreateVolumeRequest;
import com.vmware.vim25.mo.Datacenter;
import org.apache.commons.collections4.CollectionUtils;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.model.compute.RebootType;
import org.openstack4j.model.storage.block.VolumeSnapshot;
import org.openstack4j.openstack.OSFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class OpenstatckCloudArchManager extends PlatformBindedCloudArchManager {

	private OpenstackConverters converters = new OpenstackConverters();

	private Optional<OSClientV2> getOSClient() {
		OSClientV2 os = null;
		try {
			os = OSFactory.builderV2().endpoint(platform.getCloudplatformIp())
					.credentials(platform.getCloudplatformUser(),
							platform.getCloudplatformPassword())
					.tenantName(platform.getTenantName()).authenticate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return Optional.ofNullable(os);
	}

	@Override
	public List<Datacenter> getDatacenters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccDatacenter> getDatacenter() {
		throw new UnsupportedOperationException();
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
		// @formatter:off
		return getOSClient()
				.map(c -> c.compute().servers().list())
				.filter(CollectionUtils::isNotEmpty)
				.map(ls ->
					ls.stream()
					  .map(converters.toVirtualMachine())
					  .collect(toList()))
				.orElse(Collections.emptyList());
		// @formatter:on
	}

	@Override
	public TccVirtualMachine getVirtualMachineByName(String name) {
		return null;
	}

	@Override
	public List<TccDatastore> getDatastores() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccNetwork> getNetworks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TccVirtualMachine> getVmTemplates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TccVmSnapshot> getVmSnapshots(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createVirtualMachine(CreateVmRequest request) {
		// TODO Auto-generated method stub
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
		getOSClient().ifPresent(client -> {
			client.compute().servers().reboot(name, RebootType.SOFT);
		});
	}

	@Override
	public void suspendVirtualMachine(String name) {
		getOSClient().ifPresent(client -> {
			// client.compute().servers().suspend(name, RebootType.SOFT);
		});
	}

	@Override
	public void resumeVirtualMachine(String name) {
		getOSClient().ifPresent(client -> {
			// client.compute().servers().resume(name, RebootType.SOFT);
		});
	}

	@Override
	public void resetVirtualMachine(String name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroyVirtualMachine(String name) {
		getOSClient().ifPresent(client -> {
			client.compute().servers().delete(name);
		});
	}

	@Override
	public CloneVmResponse cloneVirtualMachine(CloneVmRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createVmSnapshot(String name, String vmName, String desc, boolean memoryFlag) {
		getOSClient().ifPresent(client -> {
			client.compute().servers().createSnapshot(vmName, name);
		});
	}

	@Override
	public void deleteSnapshot(String snapshotUUID) {
		getOSClient().ifPresent(client -> {
			client.images().delete(snapshotUUID);
		});
	}

	@Override
	public void revertToSnapshot(String snapshotUUID, String hostMachineUUID) {
		// TODO Auto-generated method stub
	}

	@Override
	public void createVolume(CreateVolumeRequest request) {
		String serverId = request.getVmUUID();
		String volumeId = request.getVolumeId();
		String device = request.getDevice();

		getOSClient().ifPresent(client -> {
			client.compute().servers().attachVolume(serverId, volumeId, device);
		});
	}

	@Override
	public void createVolumeSnapshot(String volumeId, String name, String desc) {
		getOSClient().ifPresent(client -> {
			VolumeSnapshot volumeSnapshot = Builders.volumeSnapshot()
					.volume(volumeId).name(name).description(desc).build();

			client.blockStorage().snapshots().create(volumeSnapshot);
		});
	}

	@Override
	public TccCapability getCapability() {
		return null;
	}

	@Override public String acquireTicket(String vmName) {
		throw new UnsupportedOperationException();
	}
}
