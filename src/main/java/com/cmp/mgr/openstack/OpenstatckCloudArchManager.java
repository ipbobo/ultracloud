package com.cmp.mgr.openstack;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.model.compute.RebootType;
import org.openstack4j.model.storage.block.VolumeSnapshot;
import org.openstack4j.openstack.OSFactory;

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
import com.vmware.vim25.mo.Datacenter;

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
	public List<TccVmSnapshot> getVmSnapshots() {
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
	public void resetVirtualMachine(String name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteVirtualMachine(String name) {
		getOSClient().ifPresent(client -> {
			client.compute().servers().delete(name);
		});
	}

	@Override
	public void cloneVirtualMachine(CloneVmRequest request) {
		// TODO Auto-generated method stub
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

}
