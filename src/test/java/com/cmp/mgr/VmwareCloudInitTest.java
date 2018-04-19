package com.cmp.mgr;

import static java.util.stream.Collectors.toList;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.vmware.VMWareCloudArchManager;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.vmware.vim25.GuestDiskInfo;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.VirtualMachine;

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
		// pd.put("ip", "180.169.225.158");
		pd.put("ip", "192.168.0.250");

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

				// 同步存储
				Datastore[] store = dc.getDatastores();
				for (int j = 0; j < store.length; j++) {
					PageData storePD = new PageData();
					String storeUuid = store[i].getMOR().get_value();
					String storeId = null;
					if (null == storeId) {
						storeId = UuidUtil.get32UUID();
						storePD.put("id", storeId);
						storePD.put("name", store[j].getName());
						storePD.put("uuid", storeUuid);
						storePD.put("type", "vmware");
						storePD.put("cpf_id", cloudPD.getString("id"));
						storePD.put("version", cloudPD.getString("version"));
						storePD.put("allspace", store[j].getSummary().getCapacity() / 1024 / 1024 / 1024);
						storePD.put("freespace", store[j].getSummary().getFreeSpace() / 1024 / 1024 / 1024);
						System.out.println(store[j].getSummary().getCapacity() / 1024 / 1024 / 1024);
						System.out.println(store[j].getSummary().getFreeSpace() / 1024 / 1024 / 1024);
					}
				}
			}
		}

	}

	private void p(String str) {
		System.out.println("-------->>" + str);
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.forEach(e -> System.out.print(e));
	}
}
