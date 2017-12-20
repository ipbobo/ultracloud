package com.cmp.mgr;

import static java.util.stream.Collectors.toList;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;

public class CloudInitTest {
	@Test
	public void testGetAdaptee() throws InvalidProperty, RuntimeFault, RemoteException {
		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformUser("administrator@vsphere.local");
		platform.setCloudplatformPassword("123.comM");
		platform.setCloudplatformIp("118.242.40.216");
		platform.setPlatformManagerType("com.cmp.mgr.impl.VMWareCloudArchManager");

		CloudArchManagerAdapter adapter = new CloudArchManagerAdapter();
		CloudArchManager cloudArchManager = adapter.getCloudArchManagerAdaptee(platform);
		List<Datacenter> datacenterList = cloudArchManager.getDatacenters();
		
		for(Datacenter dc : datacenterList) {
			System.out.println("datacenter name="+dc.getName()+",uuid="+dc.getMOR().get_value());
			
			ManagedEntity[] childEntitys = dc.getHostFolder().getChildEntity();
			List<ClusterComputeResource> clusterList = Arrays.stream(childEntitys).map(ClusterComputeResource.class::cast).collect(toList());
			for(ClusterComputeResource cluster : clusterList) {
				System.out.println("--cluster name="+cluster.getName()+",uuid="+cluster.getMOR().get_value());
				
				HostSystem[] host = cluster.getHosts();
				for(int i = 0; i< host.length; i++) {
					String osVersionCdName = host[i].getSummary().getConfig().getProduct().getName()+host[i].getSummary().getConfig().getProduct().getVersion();
					String hardwareTypeCd = host[i].getHardware().getSystemInfo().getVendor() + " " + host[i].getHardware().getSystemInfo().getModel();
					double cpuTotal = Double.valueOf(host[i].getHardware().getCpuInfo().getNumCpuCores()) * (host[i].getHardware().getCpuInfo().getHz() / 1000 / 1000 / 1000);
					double cpuCoreRemainCount = Double.valueOf(host[i].getHardware().getCpuInfo().getNumCpuCores());
					System.out.println("----host name="+host[i].getName()+",uuid="+host[i].getMOR().get_value()+","+cpuCoreRemainCount);
				}
				
			}
			
			Datastore[] store = dc.getDatastores();
			for(int i = 0; i< store.length; i++) {
				double maxFileSize = store[i].getInfo().getMaxFileSize()/1024/1024/1024;
				double freeSpace = store[i].getInfo().getFreeSpace()/1024/1024/1024;
				System.out.println("--store name="+store[i].getName()+",uuid="+store[i].getMOR().get_value()+",maxFileSize="+maxFileSize+",freeSpace="+freeSpace);
			}
			
			Network[] network = dc.getNetworks();
			for(int i = 0; i< network.length; i++) {
				System.out.println("--network name="+network[i].getName()+",uuid="+network[i].getMOR().get_value());
			}
		}
	}
}
