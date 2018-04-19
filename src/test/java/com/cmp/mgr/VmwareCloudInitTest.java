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

				// 同步集群
				ManagedEntity[] childEntitys = dc.getHostFolder().getChildEntity();
				List<ClusterComputeResource> clusterList = Arrays.stream(childEntitys).map(ClusterComputeResource.class::cast).collect(toList());
				for (ClusterComputeResource cluster : clusterList) {
					String clusterUuid = cluster.getMOR().get_value();
					String clusterId = null;
					if (null == clusterId) {
						PageData clusterPD = new PageData();
						clusterId = UuidUtil.get32UUID();
						clusterPD.put("id", clusterId);
						clusterPD.put("name", cluster.getName());
						clusterPD.put("uuid", clusterUuid);
						clusterPD.put("type", "vmware");
						clusterPD.put("cpf_id", cloudPD.getString("id"));
						clusterPD.put("version", cloudPD.getString("version"));
					}

					// 同步宿主机
					HostSystem[] host = cluster.getHosts();
					for (int k = 0; k < host.length; k++) {
						PageData hostmachinePD = new PageData();
						String hostmachineUuid = host[i].getMOR().get_value();
						String hostmachineId = null;
						// 总cpu数
						double cpuTotal = Double.valueOf(host[k].getHardware().getCpuInfo().getNumCpuCores())
								* (host[k].getHardware().getCpuInfo().getHz() / 1000 / 1000 / 1000);
						// 剩余cpu数
						double cpuCoreRemainCount = Double.valueOf(host[k].getHardware().getCpuInfo().getNumCpuCores());
						// 内存
						double memorySize = new Double(host[k].getHardware().getMemorySize() / 1024 / 1024 / 1024);
						if (null == hostmachineId) {
							hostmachineId = UuidUtil.get32UUID();
							hostmachinePD.put("id", hostmachineId);
							hostmachinePD.put("name", host[k].getName());
							hostmachinePD.put("uuid", hostmachineUuid);
							hostmachinePD.put("type", "vmware");
							hostmachinePD.put("cpf_id", cloudPD.getString("id"));
							hostmachinePD.put("cluster_id", clusterId);
							hostmachinePD.put("version", cloudPD.getString("version"));
							hostmachinePD.put("ip", host[i].getName());
							hostmachinePD.put("cpu", cpuTotal);
							hostmachinePD.put("memory", memorySize);
						}

						// 同步虚拟机
						VirtualMachine[] virtualMachine = host[i].getVms();
						for (int j = 0; j < virtualMachine.length; j++) {
							PageData vmPD = new PageData();
							String vmUuid = virtualMachine[j].getMOR().get_value();
							BigInteger vmId = null;
							if (null == vmId) {
								vmPD.put("name", virtualMachine[j].getName());
								vmPD.put("uuid", vmUuid);
								vmPD.put("type", "vmware");
								vmPD.put("cpf_id", cloudPD.getString("id"));
								vmPD.put("cluster_id", clusterId);
								vmPD.put("hostmachine_id", hostmachineId);
								vmPD.put("version", cloudPD.getString("version"));
								vmPD.put("ip", virtualMachine[j].getGuest().getIpAddress());
								vmPD.put("cpu", virtualMachine[j].getConfig().getHardware().getNumCPU());
								vmPD.put("memory", virtualMachine[j].getConfig().getHardware().getMemoryMB() / 1024);

								long committed = virtualMachine[j].getSummary().storage.committed;
								System.out.println("----------------- committed = " + committed / 1024 / 1024 / 1024);
								long uncommitted = virtualMachine[j].getSummary().storage.uncommitted;
								System.out.println("----------------- uncommitted = " + uncommitted / 1024 / 1024 / 1024);

								VirtualDevice[] vd = virtualMachine[j].getConfig().getHardware().getDevice();
								long totalDisk = 0;
								for (int a = 0; a < vd.length; a++) {
									if (vd[a] instanceof com.vmware.vim25.VirtualDisk) {
										long total = ((com.vmware.vim25.VirtualDisk) vd[a]).getCapacityInKB();
										totalDisk += total;
									}
								}
								System.out.println("totalDisk=" + totalDisk / 1024 / 1024);

								GuestDiskInfo[] guestDiskInfo = virtualMachine[j].getGuest().getDisk();
								long freeDisk = 0;
								if (null != guestDiskInfo) {
									for (int a = 0; a < guestDiskInfo.length; a++) {
										long freeSpace = guestDiskInfo[a].getFreeSpace();
										freeDisk += freeSpace;

									}
								}

								System.out.println("freeDisk=" + freeDisk / 1025 / 1024 + "\n");

								// Datastore[] ds =
								// virtualMachine[j].getDatastores();
								// VirtualMachineSummary vs =
								// virtualMachine[j].getSummary();
								// long maxFileSize =
								// ds[0].getInfo().getMaxFileSize();
								// long capacity =
								// ds[0].getSummary().getCapacity();
								// long freeSpace =
								// ds[0].getSummary().getFreeSpace();

								vmPD.put("platform", cloudPD.get("id"));
								String statusStr = virtualMachine[j].getGuest().getGuestState();
								Integer status = null;
								if ("running".equals(statusStr)) {
									status = 0;
								} else if ("notRunning".equals(statusStr)) {
									status = 2;
								} else {
									status = 1;
								}
								vmPD.put("status", status);
							}
						}

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
		list.forEach(e->System.out.print(e));
	}
}
