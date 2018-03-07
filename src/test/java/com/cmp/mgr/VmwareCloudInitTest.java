package com.cmp.mgr;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
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
public class VmwareCloudInitTest {

	// @Resource(name = "resourceService")
	// ResourceService resourceService;

	// 标明是测试方法
	@Test
	public void testSyncCloudDataVmWare() throws Exception {
		PageData pd = new PageData();
		pd.put("type", "vmware");
		pd.put("id", "bbb3512e75034c69ada0093265940852");
		pd.put("username", "administrator@vsphere.local");
		pd.put("password", "123.comM");
		pd.put("ip", "180.169.225.158");

		try {
			this.localSync(pd);
			// resourceService.syncCloudData(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void localSync(PageData cloudPD) throws InvalidProperty, RuntimeFault, RemoteException {
		// 初始化云平台帐号
		String platformManagerType = VMWareCloudArchManager.class.getName();
		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformUser(cloudPD.getString("username"));
		platform.setCloudplatformPassword(cloudPD.getString("password"));
		platform.setCloudplatformIp(cloudPD.getString("ip"));
		platform.setPlatformManagerType(platformManagerType);

		CloudArchManagerAdapter adapter = new CloudArchManagerAdapter();
		CloudArchManager cloudArchManager = adapter.getCloudArchManagerAdaptee(platform);
		List<Datacenter> datacenterList = cloudArchManager.getDatacenters();
		for (Datacenter dc : datacenterList) {
			Folder folder = dc.getHostFolder();
			ManagedEntity[] mes = folder.getChildEntity();
			for (int i = 0; i < mes.length; i++) {
				this.p(mes[i].getName());

				// 同步网络
				Network[] network = dc.getNetworks();
				for (int j = 0; j < network.length; j++) {
					PageData networkPD = new PageData();
					String networkUuid = network[j].getMOR().get_value();
					String networkId = networkUuid;

					networkId = UuidUtil.get32UUID();
					networkPD.put("id", networkId);
					networkPD.put("name", network[j].getName());
					networkPD.put("uuid", networkUuid);
					networkPD.put("type", "vmware");
					networkPD.put("cpf_id", cloudPD.getString("id"));
					networkPD.put("datacenter_id", dc.getMOR().get_value());
					networkPD.put("version", cloudPD.getString("version"));
				}

			}
		}

	
	}

	private void p(String str) {
		System.out.println("-------->>" + str);
	}
}
