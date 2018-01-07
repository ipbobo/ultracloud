package com.cmp.mgr;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.entity.tcc.TccResourcePool;
import com.cmp.mgr.vmware.VMWareCloudArchManager;
import com.fh.util.PageData;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ManagedEntity;

//public class CloudInitTest extends BaseJunit4Test {
public class CloudInitTest {

	//@Resource(name = "resourceService")
	//ResourceService resourceService;

	// 标明是测试方法
	@Test
	public void testSyncCloudDataVmWare() throws Exception {
		PageData pd = new PageData();
		pd.put("type", "vmware");
		pd.put("id", "bbb3512e75034c69ada0093265940852");
		pd.put("username", "root");
		pd.put("password", "12345678");
		pd.put("ip", "118.242.40.216");
		
		try {
			this.localSync(pd);
			//resourceService.syncCloudData(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void localSync(PageData cloudPD) throws InvalidProperty, RuntimeFault, RemoteException {
		//初始化云平台帐号
		String platformManagerType = VMWareCloudArchManager.class.getName();
		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformUser(cloudPD.getString("username"));
		platform.setCloudplatformPassword(cloudPD.getString("password"));
		platform.setCloudplatformIp(cloudPD.getString("ip"));
		platform.setPlatformManagerType(platformManagerType);

		CloudArchManagerAdapter adapter = new CloudArchManagerAdapter();
		CloudArchManager cloudArchManager = adapter.getCloudArchManagerAdaptee(platform);
		List<Datacenter> datacenterList = cloudArchManager.getDatacenters();
		for(Datacenter dc : datacenterList) {
			Folder folder = dc.getHostFolder();
			ManagedEntity[] mes = folder.getChildEntity();
			for(int i = 0; i< mes.length; i++) {
				this.p(mes[i].getName());
			}
		}
		
	}
	
	private void p(String str) {
		System.out.println("-------->>" + str);
	}
}
