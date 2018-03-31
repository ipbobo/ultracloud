package com.cmp.mgr;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.mgr.kvm.KvmCloudArchManager;
import com.cmp.mgr.vmware.VMWareCloudArchManager;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.vmware.vim25.CustomFieldValue;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;

//public class CloudInitTest extends BaseJunit4Test {
public class KVMCloudInitTest {

	// @Resource(name = "resourceService")
	// ResourceService resourceService;

	// 标明是测试方法
	@Test
	public void testSyncCloudDataVmWare() throws Exception {
		PageData pd = new PageData();
		pd.put("type", "kvm");
		pd.put("username", "root");
		pd.put("password", "123456");
		pd.put("ip", "192.168.0.126");
		pd.put("port", "16509");

		try {
			this.localSync(pd);
			// resourceService.syncCloudData(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void localSync(PageData pd) {
		String platformManagerType = KvmCloudArchManager.class.getName();
		
		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformUser(pd.getString("username"));
		platform.setCloudplatformPassword(pd.getString("password"));
		platform.setCloudplatformIp(pd.getString("ip"));
		platform.setCloudplatformPort(pd.getString("port"));
		platform.setPlatformManagerType(platformManagerType);

		CloudArchManagerAdapter adapter = new CloudArchManagerAdapter();
		CloudArchManager cloudArchManager = adapter.getCloudArchManagerAdaptee(platform);
		
		List<TccVirtualMachine> vmList = cloudArchManager.getVirtualMachines();
	
		System.out.println("-------->>");
	}

	private void p(String str) {
		System.out.println("-------->>" + str);
	}
}
