package com.cmp.mgr;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;

@Component
public class CloudArchManager {
	
	public static void main(String[] args) throws Exception {
		ServiceInstance serviceInstance = new ServiceInstance(new URL("https://118.242.40.216/sdk"),
				"administrator@vsphere.local", "123.comM", true);
		
		ManagedEntity[] managedEntities = new InventoryNavigator(
				serviceInstance.getRootFolder()).searchManagedEntities("Datacenter");
		
		if (null != managedEntities && managedEntities.length > 0) {
			for (ManagedEntity managedEntity : managedEntities) {
				Datacenter datacenter = (Datacenter) managedEntity;
				System.out.println(datacenter.getName());
				
				ManagedEntity[] childEntities = datacenter.getHostFolder().getChildEntity();
				for (ManagedEntity childEntity : childEntities) {
					System.out.println(childEntity.getMOR().getType());
				}
			}
		}
	}

}
