package com.cmp.mgr;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
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
			System.out.println("dc="+dc.getName()+",");
			
			Datastore[] store = dc.getDatastores();
			for(int i = 0; i< store.length; i++) {
				System.out.println(store[i].getName());
			}
			
			Network[] network = dc.getNetworks();
			for(int i = 0; i< network.length; i++) {
				System.out.println(network[i].getName());
				
			}
		}
		
		List<ClusterComputeResource> clusterList = cloudArchManager.getClusters();
		for(ClusterComputeResource cluster : clusterList) {
			System.out.println("cluster="+cluster.getName()+",parent="+cluster.getParent().getParent().getName());
			
			HostSystem[] host = cluster.getHosts();
			for(int i = 0; i< host.length; i++) {
				System.out.println(host[i].getName()+",");
			}
		}
	}
}
