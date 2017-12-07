package com.cmp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.sjc.adaptee.cloud.aliyun.Instance.pojo.InstancePojo;
import com.sjc.adaptee.cloud.aliyun.Instance.request.AliYunCreateInstanceRequest;
import com.sjc.adaptee.cloud.aliyun.Instance.request.AliYunDescribeInstancesRequest;
import com.sjc.adaptee.cloud.aliyun.Instance.response.AliYunCreateInstanceResponse;
import com.sjc.adaptee.cloud.aliyun.Instance.response.AliYunDescribeInstancesResponse;
import com.sjc.adaptee.cloud.aliyun.disk.pojo.DiskPojo;
import com.sjc.adaptee.cloud.aliyun.disk.request.AliYunAttachDiskRequest;
import com.sjc.adaptee.cloud.aliyun.disk.request.AliYunCreateDiskRequest;
import com.sjc.adaptee.cloud.aliyun.disk.request.AliYunDescribeDisksRequest;
import com.sjc.adaptee.cloud.aliyun.disk.response.AliYunAttachDiskResponse;
import com.sjc.adaptee.cloud.aliyun.disk.response.AliYunCreateDiskResponse;
import com.sjc.adaptee.cloud.aliyun.disk.response.AliYunDescribeDisksResponse;
import com.sjc.adaptee.cloud.aliyun.utils.EnumUtils;
import com.sjc.adaptee.cloud.aliyun.zone.request.AliYunDescribeZonesRequest;
import com.sjc.adaptee.cloud.aliyun.zone.response.AliYunDescribeZonesResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.asyncjob.request.QueryAsyncJobResultRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.asyncjob.response.QueryAsyncJobResultResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.os.pojo.OS;
import com.sjc.adaptee.cloud.cloudstack.version43.module.os.request.OsListRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.os.response.OsListResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.serviceoffering.pojo.ServiceOfferMiddleVariable;
import com.sjc.adaptee.cloud.cloudstack.version43.module.serviceoffering.pojo.ServiceOffering;
import com.sjc.adaptee.cloud.cloudstack.version43.module.serviceoffering.request.CreateServiceOfferingRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.serviceoffering.request.ListServiceOfferingsRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.serviceoffering.response.CreateServiceOfferingResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.serviceoffering.response.ListServiceOfferingsResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.snapshot.pojo.VMSnapshot;
import com.sjc.adaptee.cloud.cloudstack.version43.module.snapshot.request.ListVMSnapshotRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.snapshot.response.ListVMSnapshotResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.template.pojo.Template;
import com.sjc.adaptee.cloud.cloudstack.version43.module.template.request.DeleteTemplateRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.pojo.VirtualMachine;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.request.DeleteVmSnapshotRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.request.DeployVirtualMachineRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.request.DestroyVirtualMachineRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.request.RebootVirtualMachineRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.request.StartVirtualMachineRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.request.StopVirtualMachineRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.response.DeleteVmSnapshotResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.response.DeployVirtualMachineResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.response.DestroyVirtualMachineResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.response.RebootVirtualMachineResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.response.StartVirtualMachineResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.virtualmachine.response.StopVirtualMachineResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.module.volume.pojo.Volume;
import com.sjc.adaptee.cloud.cloudstack.version43.module.volume.request.ListVolumesRequest;
import com.sjc.adaptee.cloud.cloudstack.version43.module.volume.response.ListVolumesResponse;
import com.sjc.adaptee.cloud.cloudstack.version43.pojo.AsyncJob;
import com.sjc.adaptee.cloud.openstack.huawei.ParamUtils;
import com.sjc.adaptee.cloud.openstack.huawei.compute.flavors.pojo.FlavorsExtraSpecsPojo;
import com.sjc.adaptee.cloud.openstack.huawei.compute.flavors.pojo.FlavorsPojo;
import com.sjc.adaptee.cloud.openstack.huawei.compute.flavors.request.FlavorsCreateRequest;
import com.sjc.adaptee.cloud.openstack.huawei.compute.flavors.request.FlavorsRequest;
import com.sjc.adaptee.cloud.openstack.huawei.compute.flavors.responese.FlavorsCreateResponse;
import com.sjc.adaptee.cloud.openstack.huawei.compute.flavors.responese.FlavorsResponse;
import com.sjc.adaptee.cloud.openstack.huawei.compute.servers.pojo.ServersPojo;
import com.sjc.adaptee.cloud.openstack.huawei.compute.servers.request.ServersAttachRequest;
import com.sjc.adaptee.cloud.openstack.huawei.compute.servers.request.ServersCreateRequest;
import com.sjc.adaptee.cloud.openstack.huawei.compute.servers.request.ServersQueryRequest;
import com.sjc.adaptee.cloud.openstack.huawei.compute.servers.request.ServersRequest;
import com.sjc.adaptee.cloud.openstack.huawei.compute.servers.responese.ServersAttachResponse;
import com.sjc.adaptee.cloud.openstack.huawei.compute.servers.responese.ServersCreateResponse;
import com.sjc.adaptee.cloud.openstack.huawei.compute.servers.responese.ServersQueryResponse;
import com.sjc.adaptee.cloud.openstack.huawei.compute.servers.responese.ServersResponse;
import com.sjc.adaptee.cloud.openstack.huawei.image.pojo.ImagePojo;
import com.sjc.adaptee.cloud.openstack.huawei.image.request.ImageRequest;
import com.sjc.adaptee.cloud.openstack.huawei.image.response.ImageResponse;
import com.sjc.adaptee.cloud.openstack.huawei.storage.pojo.StoragePojo;
import com.sjc.adaptee.cloud.openstack.huawei.storage.request.StorageCreateRequest;
import com.sjc.adaptee.cloud.openstack.huawei.storage.request.StorageListRequest;
import com.sjc.adaptee.cloud.openstack.huawei.storage.responese.StorageCreateResponse;
import com.sjc.adaptee.cloud.openstack.huawei.storage.responese.StorageListResponse;
import com.sjc.adaptee.cloud.openstack.version23.compute.flavors.pojo.FlavorPojo;
import com.sjc.adaptee.cloud.openstack.version23.compute.flavors.request.FlavorCreateRequest;
import com.sjc.adaptee.cloud.openstack.version23.compute.flavors.request.FlavorListRequest;
import com.sjc.adaptee.cloud.openstack.version23.compute.flavors.response.FlavorCreateResponse;
import com.sjc.adaptee.cloud.openstack.version23.compute.flavors.response.FlavorListResponse;
import com.sjc.adaptee.cloud.openstack.version23.compute.flavors.response.FlavorResponse;
import com.sjc.adaptee.cloud.openstack.version23.compute.servers.pojo.ServerPojo;
import com.sjc.adaptee.cloud.openstack.version23.compute.servers.pojo.ServerStatus;
import com.sjc.adaptee.cloud.openstack.version23.compute.servers.request.ServerAttachVolumeRequest;
import com.sjc.adaptee.cloud.openstack.version23.compute.servers.request.ServerBootRequest;
import com.sjc.adaptee.cloud.openstack.version23.compute.servers.request.ServerListRequest;
import com.sjc.adaptee.cloud.openstack.version23.compute.servers.request.ServerRequest;
import com.sjc.adaptee.cloud.openstack.version23.compute.servers.response.ServerAttachVolumeResponse;
import com.sjc.adaptee.cloud.openstack.version23.compute.servers.response.ServerBootResponse;
import com.sjc.adaptee.cloud.openstack.version23.compute.servers.response.ServerListResponse;
import com.sjc.adaptee.cloud.openstack.version23.compute.servers.response.ServerResponse;
import com.sjc.adaptee.cloud.openstack.version23.storage.pojo.VolumePojo;
import com.sjc.adaptee.cloud.openstack.version23.storage.pojo.VolumeStatus;
import com.sjc.adaptee.cloud.openstack.version23.storage.request.VolumeCreateRequest;
import com.sjc.adaptee.cloud.openstack.version23.storage.request.VolumeRequest;
import com.sjc.adaptee.cloud.openstack.version23.storage.response.VolumeCreateResponse;
import com.sjc.adaptee.cloud.openstack.version23.storage.response.VolumeResponse;
import com.sjc.adaptee.cloud.qingcloud.constant.AvailableCpuCore;
import com.sjc.adaptee.cloud.qingcloud.constant.AvailableMemoryMB;
import com.sjc.adaptee.cloud.qingcloud.constant.InstanceClass;
import com.sjc.adaptee.cloud.qingcloud.constant.LoginMode;
import com.sjc.adaptee.cloud.qingcloud.image.constant.ImageStatusEnum;
import com.sjc.adaptee.cloud.qingcloud.snapshot.pojo.SnapshotPojo;
import com.sjc.adaptee.cloud.qingcloud.virtualmachine.VirtualMachineStatusEnum;
import com.sjc.adaptee.cloud.qingcloud.virtualmachine.pojo.VirtualMachinePojo;
import com.sjc.adaptee.cloud.qingcloud.virtualmachine.request.VirtualMachineCreateRequest;
import com.sjc.adaptee.cloud.qingcloud.virtualmachine.request.VirtualMachineRetrieveRequest;
import com.sjc.adaptee.cloud.qingcloud.virtualmachine.response.VirtualMachineCreateResponse;
import com.sjc.adaptee.cloud.qingcloud.virtualmachine.response.VirtualMachineRetrieveResponse;
import com.sjc.adaptee.cloud.qingcloud.volume.request.VolumeAttachRequest;
import com.sjc.adaptee.cloud.qingcloud.volume.request.VolumeRetrieveRequest;
import com.sjc.adaptee.cloud.qingcloud.volume.response.VolumeAttachResponse;
import com.sjc.adaptee.cloud.qingcloud.volume.response.VolumeRetrieveResponse;
import com.sjc.adaptee.cloud.vmware.version51.module.clustercomputeresource.request.ListClusterComputeResourcesRequest;
import com.sjc.adaptee.cloud.vmware.version51.module.clustercomputeresource.response.ListClusterComputeResourcesResponse;
import com.sjc.adaptee.cloud.vmware.version51.module.datacenter.request.ListDatacentersRequest;
import com.sjc.adaptee.cloud.vmware.version51.module.datacenter.response.ListDatacentersResponse;
import com.sjc.adaptee.cloud.vmware.version51.module.snapshot.request.DeleteSnapShotByUuidRequest;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request.AddCdromRequest;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request.AddVirtualMachineDiskRequest;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request.CloneVitualMachineRequest;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request.GetVirtualMachineRequest;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request.RemoveCdromRequest;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request.VirtualMachineUpdateNetworkRequest;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response.AddCdromResponse;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response.AddVirtualMachineDiskReponse;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response.CloneVitualMachineResponse;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response.GetVirtualMachineResponse;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response.RemoveCdromResponse;
import com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response.VirtualMachineUpdateNetworkResponse;
import com.sjc.adaptee.cloud.ws.cmdb.vm.response.VMSaveResponse;
import com.sjc.cc.base.BusinessEnvironment;
import com.sjc.cc.base.LoginUserInfoHolder;
import com.sjc.cc.base.TccCloudPlatUitls;
import com.sjc.cc.base.service.AbstractBusinessService;
import com.sjc.cc.base.service.ApplicationCacheService;
import com.sjc.cc.base.util.DateUtil;
import com.sjc.cc.base.util.StringUtil;
import com.sjc.cc.base.util.StringUtils;
import com.sjc.cc.base.vo.LoginUserInfo;
import com.sjc.cc.capacity.service.CapacityMgmtService;
import com.sjc.cc.capacity.vo.ip.IpZoneEnum;
import com.sjc.cc.cloud.arch.service.SynchCMDB;
import com.sjc.cc.cloud.existResource.service.ExistResourceService;
import com.sjc.cc.entity.TCcEvn;
import com.sjc.cc.entity.TccApplyedHostResource;
import com.sjc.cc.entity.TccApplyedHostinfo;
import com.sjc.cc.entity.TccCloudDatacenter;
import com.sjc.cc.entity.TccCloudplatform;
import com.sjc.cc.entity.TccClusterConfig;
import com.sjc.cc.entity.TccConfigAssApplycase;
import com.sjc.cc.entity.TccEmployee;
import com.sjc.cc.entity.TccExpdCapacity;
import com.sjc.cc.entity.TccInstanceInfo;
import com.sjc.cc.entity.TccIpConfigInfo;
import com.sjc.cc.entity.TccOpenStackVolumeSnapshot;
import com.sjc.cc.entity.TccOrgan;
import com.sjc.cc.entity.TccOs;
import com.sjc.cc.entity.TccPhysiscResourceInfo;
import com.sjc.cc.entity.TccProjectAss;
import com.sjc.cc.entity.TccProjectInfo;
import com.sjc.cc.entity.TccResourceAccount;
import com.sjc.cc.entity.TccSnapshotCfg;
import com.sjc.cc.entity.TccSnapshotHistory;
import com.sjc.cc.entity.TccSrt;
import com.sjc.cc.entity.TccSrtApplyedhostAss;
import com.sjc.cc.entity.TccStorageAddRecord;
import com.sjc.cc.entity.TccStorageApplyCaseAss;
import com.sjc.cc.entity.TccStrategyCase;
import com.sjc.cc.entity.TccSysStoreCase;
import com.sjc.cc.entity.TccTemplateCase;
import com.sjc.cc.entity.TccVlanNetwork;
import com.sjc.cc.highnetwork.service.HignNetworkService;
import com.sjc.cc.host.service.HostService;
import com.sjc.cc.hostMonitorAlarm.api.service.HostMonitorAlarmApiService;
import com.sjc.cc.hostMonitorAlarm.bean.bo.HostMonitorBean;
import com.sjc.cc.instance.service.AliYunService;
import com.sjc.cc.instance.service.CloneVmService;
import com.sjc.cc.instance.service.OpenStackServerService;
import com.sjc.cc.instance.service.QingCloudService;
import com.sjc.cc.instance.service.VmAssistService;
import com.sjc.cc.instance.service.VmService;
import com.sjc.cc.instance.thread.DeleteTemplateThread;
import com.sjc.cc.instance.thread.VmCallable;
import com.sjc.cc.instance.vo.VmInfo;
import com.sjc.cc.network.service.NetworkService;
import com.sjc.cc.operlog.service.OperLogService;
import com.sjc.cc.pf.EnvironmentConstant;
import com.sjc.cc.pf.util.JsonResponseUtil;
import com.sjc.cc.resrcexpd.vo.ResrcExpdVO;
import com.sjc.cc.resrcrelease.service.ResrcReleaseService;
import com.sjc.cc.resrcrelease.vo.CloneResVo;
import com.sjc.cc.service.service.CloudStackService;
import com.sjc.cc.service.service.ConfigAssApplyCaseService;
import com.sjc.cc.service.service.IsoCaseService;
import com.sjc.cc.service.service.MealService;
import com.sjc.cc.service.service.StorageService;
import com.sjc.cc.service.service.StrategyCaseService;
import com.sjc.cc.service.service.TemplateService;
import com.sjc.cc.service.service.VolumeService;
import com.sjc.cc.service.util.MapUtils;
import com.sjc.cc.snapshot.category.service.SnapshotCategoryService;
import com.sjc.cc.snapshot.service.VmSnapshotService;
import com.sjc.cc.srtapply.vo.SrtManageVO;
import com.sjc.cc.util.JavaOperateSSHUtils;
import com.sjc.global.ResponseParameter;
import com.sjc.target.Target;
import com.vmware.vim25.CustomizationAdapterMapping;
import com.vmware.vim25.CustomizationFixedIp;
import com.vmware.vim25.CustomizationFixedName;
import com.vmware.vim25.CustomizationGlobalIPSettings;
import com.vmware.vim25.CustomizationGuiUnattended;
import com.vmware.vim25.CustomizationIPSettings;
import com.vmware.vim25.CustomizationIdentification;
import com.vmware.vim25.CustomizationLinuxOptions;
import com.vmware.vim25.CustomizationLinuxPrep;
import com.vmware.vim25.CustomizationNetBIOSMode;
import com.vmware.vim25.CustomizationPassword;
import com.vmware.vim25.CustomizationSpec;
import com.vmware.vim25.CustomizationSysprep;
import com.vmware.vim25.CustomizationUserData;
import com.vmware.vim25.CustomizationVirtualMachineName;
import com.vmware.vim25.CustomizationWinOptions;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.Task;

import gua.com.Utils.XmlUtils;

@SuppressWarnings("deprecation")
public class VmServiceImpl extends AbstractBusinessService implements VmService {
	@SuppressWarnings("unused")
	
	public static final String IN_PROCESS = "0";
	public static final String SUCCESS = "1";
	public static final String FAILURE = "2";
	public static final String STOPPED = "Stopped";
	public static final long MIN_STORE_FREE = 30;
	public static final int PROTOCALPORT = 7777;
	public VolumeService volumeService;
	private OperLogService operLogService;
	private VmAssistService vmAssistService;
	private StorageService storageService;
	private CloudStackService cloudStackService;
	private HostMonitorAlarmApiService zabbixApiService;
	private HostService hostService;
	private ConfigAssApplyCaseService configAssApplyCaseService;
	private CapacityMgmtService capacityMgmtService;
	private ApplicationCacheService applicationCacheService;
	private SnapshotCategoryService snapshotCategoryService;
	private HignNetworkService hignNetworkService;
	private VmSnapshotService vmSnapshotService;
	private TemplateService templateService;
	private MealService mealService;
	private IsoCaseService isoCaseService;
	private NetworkService networkService;
	
	private StrategyCaseService strategyCaseService;

	private OpenStackServerService openStackServerService;
	private QingCloudService qingcloudService;
	private AliYunService aliYunService;
	private SynchCMDB synchCMDB;
	
	private ResrcReleaseService resrcReleaseService;
	
	private ExistResourceService existResourceService;
	
	public ExistResourceService getExistResourceService() {
		return existResourceService;
	}

	public void setExistResourceService(ExistResourceService existResourceService) {
		this.existResourceService = existResourceService;
	}

	public AliYunService getAliYunService() {
		return aliYunService;
	}

	public void setAliYunService(AliYunService aliYunService) {
		this.aliYunService = aliYunService;
	}

	public SynchCMDB getSynchCMDB() {
		return synchCMDB;
	}

	public void setSynchCMDB(SynchCMDB synchCMDB) {
		this.synchCMDB = synchCMDB;
	}

	// 根据传的网络 集群 域获得相应的模板
	/**
	 * 根据cpu 内存获取计算方案
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ServiceOffering getCountCaseId(Double cpu, Double memory,
			String cloudplatformId, String clusterId) throws Throwable {
		// 查询所有的计算方案，比较是否有匹配的
		ListServiceOfferingsRequest request = new ListServiceOfferingsRequest();
		request.setCloud(cloudplatformId);// CloudplatformId
		request = (ListServiceOfferingsRequest) TccCloudPlatUitls.setRequestParam(request);
		ListServiceOfferingsResponse response = (ListServiceOfferingsResponse) TccCloudPlatUitls.getAdapte().execute(request);
		// 首先查询匹配CPU和RAM的计算方案列表
		List<ServiceOffering> serviceOfferingList = new ArrayList<ServiceOffering>();
		if (response.getListServiceOfferingsResponse().getServiceOffering() != null
				&& response.getListServiceOfferingsResponse().getServiceOffering().size() > 0) {
			for (ServiceOffering vo : response.getListServiceOfferingsResponse().getServiceOffering()) {
				if (Double.parseDouble(vo.getMemory()) == memory && Double.parseDouble(vo.getCpuNumber()) == cpu) {
					serviceOfferingList.add(vo);
				}
			}
		}

		// 查询存储列表
		DetachedCriteria criteria = DetachedCriteria.forClass(TccStorageAddRecord.class);
		criteria.add(Restrictions.eq("enableFlg", BusinessEnvironment.ENABLE_FLG));
		criteria.add(Restrictions.eq("tccCluster.id", Long.parseLong(clusterId)));
		List<TccStorageAddRecord> storageAddRecords = this.commonDao.findByCriteria(criteria);
		if (serviceOfferingList != null && serviceOfferingList.size() > 0) {
			// 先查找共享存储在查找本地存储
			ServiceOffering serviceOffering = getServiceOfferingByStoragetype(storageAddRecords, serviceOfferingList, "shared");
			if (serviceOffering == null) {
				serviceOffering = getServiceOfferingByStoragetype(storageAddRecords, serviceOfferingList, "local");
			}
			if (serviceOffering != null) {
				return serviceOffering;
			} else {
				return createServiceOffering(cpu, memory, cloudplatformId,
						storageAddRecords);
			}
		} else {
			return createServiceOffering(cpu, memory, cloudplatformId,
					storageAddRecords);
		}
	}

	/**
	 * 创建计算方案，共享存储优先
	 * 
	 * @param cpu
	 * @param memory
	 * @param cloudplatformId
	 * @param storageAddRecords
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Throwable
	 */
	private ServiceOffering createServiceOffering(Double cpu, Double memory,
			String cloudplatformId, List<TccStorageAddRecord> storageAddRecords)
			throws UnsupportedEncodingException, Throwable {
		Boolean flag = Boolean.FALSE;
		if (storageAddRecords != null && !storageAddRecords.isEmpty()) {
			for (TccStorageAddRecord storageAddRecord : storageAddRecords) {
				if ("shared".equalsIgnoreCase(storageAddRecord.getStoreType())) {
					flag = Boolean.TRUE;
					break;
				}
			}
		}
		String storageType = "local";
		if (flag) {
			storageType = "shared";
		}
		// 如果无匹配计算方案，则重新创建计算方案
		CreateServiceOfferingRequest requestCreate = new CreateServiceOfferingRequest();
		requestCreate.setCloud(cloudplatformId);
		requestCreate.setCpuspeed(cpu.intValue() + "");
		requestCreate.setCpunumber("1");
		requestCreate.setStoragetype(storageType);
		requestCreate.setMemory(memory.intValue() + "");
		String name = cpu.intValue() + "C/" + memory.intValue() / 1024 + "G"
				+ requestCreate.getStoragetype();
		requestCreate.setName(URLEncoder.encode(name, "UTF-8"));
		requestCreate.setDisplaytext(URLEncoder.encode("platecreates", "UTF-8"));
		requestCreate = (CreateServiceOfferingRequest) TccCloudPlatUitls
				.setRequestParam(requestCreate);
		CreateServiceOfferingResponse responseCreate = (CreateServiceOfferingResponse) TccCloudPlatUitls
				.getAdapte().execute(requestCreate);
		ServiceOfferMiddleVariable serviceOffering = null;
		try {
			serviceOffering = responseCreate.getCreateserviceofferingresponse();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		if (null == serviceOffering) {
			return null;
		}
		return serviceOffering.getServiceoffering();
	}

	/**
	 * 根据存储本地共享类型查询匹配的的计算方案
	 * 
	 * @param storageAddRecords
	 * @param serviceOfferings
	 * @param storagetype
	 * @return
	 */
	private ServiceOffering getServiceOfferingByStoragetype(
			List<TccStorageAddRecord> storageAddRecords,
			List<ServiceOffering> serviceOfferings, String storagetype) {
		Boolean flag = Boolean.FALSE;
		if (storageAddRecords != null && !storageAddRecords.isEmpty()) {
			for (TccStorageAddRecord storageAddRecord : storageAddRecords) {
				if (storagetype.equalsIgnoreCase(storageAddRecord.getStoreType())) {
					flag = Boolean.TRUE;
					break;
				}
			}
		}
		// 存在合适存储
		if (flag) {
			if (serviceOfferings != null && !serviceOfferings.isEmpty()) {
				for (ServiceOffering serviceOffering : serviceOfferings) {
					if (storagetype.equalsIgnoreCase(serviceOffering.getStorageType())) {
						return serviceOffering;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 根据集群id获取主机 集群就是多台物理主机组成
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TccPhysiscResourceInfo getHostBycluster(String clusterId) throws Throwable {
		TccPhysiscResourceInfo tccPhysiscResourceInfo = null;
		String hql = " from TccPhysiscResourceInfo where enableFlg=1 AND CLUSTERID = " + clusterId +" AND synch=true";
		List<TccPhysiscResourceInfo> list = this.commonDao.find(hql);
		if (null != list && !list.isEmpty()) {
			for (TccPhysiscResourceInfo obj : list) {
				if (null == tccPhysiscResourceInfo) {
					tccPhysiscResourceInfo = obj;
				} else {
					try {
						if (obj.getCpuCoreRemainCount() > tccPhysiscResourceInfo.getCpuCoreRemainCount()) {
							tccPhysiscResourceInfo = obj;
						}
					} catch (Exception e) {
						logger.warn("存在未知host:" + obj.getPhysicsName());
						e.printStackTrace();
					}
				}
			}
		}
		return tccPhysiscResourceInfo;
	}
	
	
	public TccPhysiscResourceInfo getTccPhysiscById(String id){
		if(id==null || "".equals(id)){
			return null;
		}
		TccPhysiscResourceInfo info  = (TccPhysiscResourceInfo) this.commonDao.get(TccPhysiscResourceInfo.class, Long.parseLong(id));
		return info;
	}
	
	

	public void updateIpAddress(String ipAddress, String vmName) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='创建"
						+ vmName + "虚拟机'  WHERE f.IP_ADDRESS ='" + ipAddress + "'");
		commonDao.updateByNativeSql(updateSql.toString());
	}

	public boolean validateTemplate(String plateFormId, String templateUuid) throws Exception, Throwable {
		TccCloudplatform platform = (TccCloudplatform) commonDao.get(
				TccCloudplatform.class, Long.parseLong(plateFormId));
		if(BusinessEnvironment.CLOUDPLANTFORM_CLOUDSTACK.equals(platform.getCloudplatformType())){
			List<Template> templateList = cloudStackService.getListTemplates(platform);
			for (Template template : templateList) {
				if (template.getId().equalsIgnoreCase(templateUuid)) {
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<TccStorageApplyCaseAss> getStorageApplyCaseByAssid(Long assId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TccStorageApplyCaseAss.class);
		criteria.add(Restrictions.eq("assId", assId));
		return commonDao.findByCriteria(criteria);
	}

	// 创建cloudstack虚拟机
	public synchronized Map<String, String> createCS(SrtManageVO srtManageVO,
			TccProjectInfo tccProjectInfo) throws Exception, Throwable {
		boolean attachFlag = false;
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		String currentUserId = loginUserInfo.getUserPartyId().toString();
		//当前虚拟机安装日志
		if(installLog.get(currentUserId)==null){
			installLog.put(currentUserId,new ArrayList<String>());
		}
		List<String> installLog_current = installLog.get(currentUserId);
		Map<String, String> errorMap = new HashMap<String, String>();
		String errorMsg = "";
		TccConfigAssApplycase ass = configAssApplyCaseService
				.getConfigAssApplyCase(Long.parseLong(srtManageVO.getAssId()));
		// 获取计算方案
		ServiceOffering serviceOffering = getCountCaseId(ass.getCpuSize(),
				ass.getRamSize(), srtManageVO.getCloudplatformId(), srtManageVO.getClusterId());
		
		// 获取区域
		TccCloudDatacenter dc = (TccCloudDatacenter) commonDao.get(
				TccCloudDatacenter.class, Long.parseLong(srtManageVO.getZoneId()));
		
		installLog_current.add("已选择Datacenter:" + dc.getZoneName());
		
		// 查询集群
		TccClusterConfig clusterConfig = vmAssistService.getClusterConfig(Long
						.parseLong(srtManageVO.getClusterId()));
		
		installLog_current.add("已选择Cluster:" + clusterConfig.getClusterName());
		
		// 获取主机
		TccPhysiscResourceInfo physiscResourceInfo = getHostBycluster(srtManageVO.getClusterId());//getTccPhysiscById(srtManageVO.getPhysicalId());
		
		if(physiscResourceInfo == null){
			physiscResourceInfo = getTccPhysiscById(srtManageVO.getPhysicalId());
		}
		installLog_current.add("已选择Host:" + physiscResourceInfo.getPhysicsName());
		
		// 获取模版并验证模版是否可用
		TccTemplateCase template = isoCaseService.queryTemplateByHostInfo(
				ass.getIsoId(), dc.getUuid(), physiscResourceInfo.getVirtualFlag());
		if (template == null
				|| !validateTemplate(srtManageVO.getCloudplatformId(), template.getCsTemplateOfferingId())) {
			errorMsg = "cloudstack没有这个模版！";
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		
		installLog_current.add("已选择模板:" + template.getTemplateName());
		installLog_current.add("已设置IP:" + srtManageVO.getIpAddress());
		
		// 获取网络
		TccVlanNetwork network = (TccVlanNetwork) this.commonDao.get(TccVlanNetwork.class,
				Long.parseLong(srtManageVO.getNetworkId()));
		// 设置创建云主机参数
		DeployVirtualMachineRequest deployVmReq = new DeployVirtualMachineRequest();
		deployVmReq.setServiceOfferingId(serviceOffering.getId()); // cpu 内存
																	// 从申请单读取
		deployVmReq.setTemplateId(template.getCsTemplateOfferingId());// 模版ID
																		// 资源池
																		// 镜像 模版
		deployVmReq.setHostId(physiscResourceInfo.getUuid());// 主机ID
		deployVmReq.setZoneId(dc.getUuid());// 区域ID
		deployVmReq.setName(srtManageVO.getVmName().replaceAll(" ", ""));// 虚机名称
		deployVmReq.setNetworkIds(network.getUuid());// 网络3f980d16-2311-469c-9ab2-2f3de78f896c
		deployVmReq.setIpAddress(srtManageVO.getIpAddress());// 指定ip地址
																// 172.16.60.108
		deployVmReq.setCloud(srtManageVO.getCloudplatformId());// 平台ID
		ass.setVrSubType(clusterConfig.getVtype());
		// 修改虚拟机安装状态为进行中
		ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTING);
		configAssApplyCaseService.updateConfigAssApplyCase(ass);
		ass = configAssApplyCaseService.getConfigAssApplyCase(ass.getAssId());
		// IP管理
		if (srtManageVO.getVmName() != null && !srtManageVO.getVmName().equals("")) {
			boolean flag = vmAssistService.checkDuplVmName(deployVmReq.getName());
			if (flag == false) {
				errorMsg = "输入的名字已经存在，请重新输入";
				logger.error(errorMsg);
				errorMap.put("FAILURE", errorMsg);
				return errorMap;
			}
		}
		// 调用CloudStack接口，若安装出现异常则改安装状态为失败
		AsyncJob asyncjobRespId = null;
		try {
			deployVmReq = (DeployVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(deployVmReq);
			DeployVirtualMachineResponse deployResponse = (DeployVirtualMachineResponse) TccCloudPlatUitls
					.getAdapte().execute(deployVmReq);
			asyncjobRespId = deployResponse.getDeployVirtualMachineResponse();
		} catch (Exception e) {
			ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTFAIL);// 失败状态
			configAssApplyCaseService.updateConfigAssApplyCase(ass);
			logService.saveOperLog(BusinessEnvironment.OPER_RESULT_FAILURE,
					loginUserInfo.getEmpName() + BusinessEnvironment.OPER_TYPE_ADD_VM, ass, 2);
			errorMsg = "创建虚拟机时IO异常，请联系管理员！";
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		String jobStatus = asynQueryVmStatus(asyncjobRespId.getJobId(), srtManageVO.getCloudplatformId());
		// 虚拟机成功
		if (SUCCESS.equals(jobStatus)) {
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "安装成功");
			
			// 更新同步过来IP地址的状态
			this.updateIpAddress(srtManageVO.getIpAddress(), srtManageVO.getVmName());
			// 调用CloudStack接口，获取虚拟机信息
			VirtualMachine vmVo = cloudStackService.getVmInfoCS(asyncjobRespId.getJobId(), srtManageVO.getCloudplatformId());
			// 首先检查当前虚拟机的计算方案时共享还是本地的
			String storageType = serviceOffering.getStorageType();
			// 根据申请单ID查询存储
			List<TccStorageApplyCaseAss> storageList = getStorageApplyCaseByAssid(ass.getAssId());
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "挂载数据盘开始");
			
			String volumeOfferingId = "";// 外挂卷轴UUID
			for (int i = 0; i < storageList.size(); i++) {
				TccStorageApplyCaseAss storageObj = storageList.get(i);
				// 如果是SAN存储，则自动挂载外挂存储（卷轴），否则走线下由实施人员手动安装外挂存储
				try {
					if (storageObj.getStorageSize() > 0) {
						// 把外挂存储的大小找到磁盘方案，生成卷轴挂载到虚拟机上
						TccSysStoreCase ssc = new TccSysStoreCase();
						ssc.setStoreCaseName(storageObj.getStorageSize() + "GB");
						ssc.setStoreSize(storageObj.getStorageSize().longValue());
						ssc = storageService.mergeSysStoreCase(ssc, srtManageVO.getCloudplatformId(), storageType.trim());
						volumeOfferingId = volumeService.createVolumeCS(srtManageVO.getVmName() + "D00" + i, 
								storageObj.getStorageSize().longValue() + "", 
								ssc.getDiskOfferingId(), vmVo.getZoneId().trim(), storageObj);
						// 挂载卷轴
						attachFlag = volumeService.attachVolumeCS(volumeOfferingId, 
								vmVo.getId(), srtManageVO.getCloudplatformId());
						ass.setAttachFlag(attachFlag);
						if (attachFlag) {
							storageObj.setUuid(volumeOfferingId);
							logService.saveOperLog("成功", srtManageVO.getVmName() + "挂载磁盘" + srtManageVO.getVmName()
													+ "D00" + i + BusinessEnvironment.OPER_RESULT_SUCCESS, ass, 2);
						} else {
							logService.saveOperLog("失败", srtManageVO.getVmName() + "挂载磁盘" + srtManageVO.getVmName()
													+ "D00" + i + BusinessEnvironment.OPER_RESULT_FAILURE, ass, 2);
						}
					}
				} catch (Exception e) {
					// 挂载磁盘失败
					errorMsg = "挂载磁盘时IO异常，请联系管理员！";
					logger.warn(errorMsg);
					DestroyVirtualMachineRequest request = new DestroyVirtualMachineRequest();
					request.setCloud(srtManageVO.getCloudplatformId());
					request.setId(vmVo.getId());
					request = (DestroyVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(request);
					DestroyVirtualMachineResponse response = null;
					// 虚拟机挂载磁盘失败要销毁创建成功的虚拟机
					try {
						response = (DestroyVirtualMachineResponse) TccCloudPlatUitls.getAdapte().execute(request);
					} catch (Exception ex) {
					}
					String jobStatus_tmp = asynQueryVmStatus(response .getDestroyVirtualMachineResponse().getJobId(), 
							srtManageVO.getCloudplatformId());
					if (SUCCESS.equals(jobStatus_tmp)) {
						e.printStackTrace();
						errorMap.put("FAILURE", errorMsg);
						return errorMap;
					}
				}
			}
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "挂载数据盘结束");

			// 让虚拟机自动绑定共有IP
			XmlUtils utils = new XmlUtils();
			List<String> ipList = new ArrayList<String>();
			ipList.add(vmVo.getNic().get(0).getIpAddress().trim());
			utils.addConnection("", ipList);

			// 保存服务请求主表与已分配资源代码表
			TccSrt srt = vmAssistService.getSrt(ass.getTccSrt().getSrtId());
			TccApplyedHostResource applyedHostResource = vmAssistService
					.mergeApplyedHostResource(vmVo, ass, "", volumeOfferingId);
			//设置虚拟机关联的流程编号
			if(null!=srtManageVO.getHostInfoProduceId()&&srtManageVO.getHostInfoProduceId().length()>0){
				applyedHostResource.setProduceId(srtManageVO.getHostInfoProduceId());
			}
			applyedHostResource.setCloseDttm(ass.getCloseDttm());
			applyedHostResource.setOsVersionCd(template.getOsVersionCd());
			applyedHostResource.setTemplateId(template.getTemplateCaseId().toString());
			/** add host to monitor by Destiny : begin **/
			String host = String.valueOf(applyedHostResource.getApplyResourceId());
			String visibleName = srtManageVO.getVmName();
			TccOs os = this.getOsById(applyedHostResource.getOsCd());

			try {
				if (!zabbixApiService.isAddHostToMonitorByHostName(host)) {
					
					installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "添加Zabbix监控开始");
					
					HostMonitorBean hostinfo = new HostMonitorBean();
					hostinfo.setHostName(host);
					hostinfo.setVisibleName(visibleName);
					hostinfo.setIp(srtManageVO.getIpAddress());
					hostinfo.setMonitorType(1);
					if (null != os && null != os.getOneLevelOs()) {
						hostinfo.setOsName(os.getOneLevelOs());
					} else {
						hostinfo.setOsName("");
					}
					applyedHostResource.setMonitorFlg(zabbixApiService
							.addHostToMonitor(hostinfo) ? "1" : "0");
					
					installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "添加Zabbix监控结束");
					
				} else {
					logger.info("主机[" + visibleName + "]已在zabbix中注册");
					applyedHostResource.setMonitorFlg("1");
				}
			} catch (Exception ex) {
				this.logger.error("添加监控失败！", ex);
			} catch (Throwable t) {
				this.logger.error("添加监控失败！", t);
			}
			commonDao.update(applyedHostResource);
			/** add host to monitor by Destiny : end **/
			vmAssistService.saveSrtApplyedhostAss(applyedHostResource, srt);
			applyedHostResource.setHardwareTypeCd("186");
			applyedHostResource.setClusterId(srtManageVO.getClusterId());
			ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTSUCC);
			configAssApplyCaseService.updateConfigAssApplyCase(ass);
			// 容量操作 剩余CPU、MEM减少
			capacityMgmtService.takingCpuRam(
					applyedHostResource.getTccPhysiscResourceInfo(), ass.getCpuSize(), ass.getRamSize());
			// 保存项目与已申请资源表关系
			TccProjectInfo projectInfo = tccProjectInfo;
			vmAssistService.saveProjectAss(applyedHostResource, projectInfo);
			String displayName = deployVmReq.getName();
			TccApplyedHostResource ahr = vmAssistService
					.getApplyedHostResource(applyedHostResource.getApplyResourceId());
			ahr.setHostNane(displayName);
			vmAssistService.updateApplyedHostResource(ahr);
			//保存成功日志
			this.saveLog(loginUserInfo.getUserPartyId(), loginUserInfo.getEmpName(), 
					"创建", "成功", TccConfigAssApplycase.class.toString(), ass.getAssId(), "实施者安装虚拟机"+srtManageVO.getVmName()+"成功");
			// 如果创建虚拟机成功，虚拟机信息插入到snapshotcategory表里面
			TccSnapshotCfg snapshotCfg = new TccSnapshotCfg();
			snapshotCfg.setCrtDttm(new Date());
			Set<TccApplyedHostinfo> tccApplyedHostinfos = ass.getTccApplyedHostinfos();
			for (TccApplyedHostinfo temp : tccApplyedHostinfos) {
				snapshotCfg.setCrtUserId(temp.getCrtUserId() + "");
				break;
			}
			snapshotCfg.setCrtUserId(String.valueOf(srt.getCrtUserId()));
			snapshotCfg.setShotMaxNum(0L);
			snapshotCfg.setHostId(applyedHostResource.getApplyResourceId());
			snapshotCfg.setVmName(displayName);
			snapshotCfg.setEnableFlg("1");
			snapshotCategoryService.AddSnapshotCategory(snapshotCfg);
//			logService.saveOperLog(BusinessEnvironment.OPER_RESULT_SUCCESS, "创建",ass, 2);
			errorMap.put("SUCCESS", applyedHostResource.getApplyResourceId() + "");			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "安装结束");
			//新建虚拟机的时候自动创建一个快照
			TCcEvn ccEvn = srtManageVO.getEvn();
			boolean initSnapshot = ccEvn!=null ?ccEvn.isInitSnapshot():false;
			//判断是否需要初始快照
			if(initSnapshot){
				try {
					
					boolean Status = false;
		    		String noteString = "新建虚拟机时自动创建的快照";
		    		vmSnapshotService.createVmSnapshot(host.trim(),Status,noteString.trim());
		    		//把创建的虚拟机的快照设为初始创建快照时创建
		    		//创建完快照时需要让休眠300毫秒才能查询数据
		    		Thread.sleep(300);
		    		String sql="UPDATE T_CC_SNAPSHOT_HISTORY SET SNAPSHOT_MODE = 3 WHERE VM_ID = "+host;
		    		commonDao.updateByNativeSql(sql);
					
				} catch (Exception e) {
					logger.error("新建虚拟机" + visibleName + "时，自动创建的快照失败");
				}
			}
			//把虚拟机的ID用json返回页面
			JSONObject object = new JSONObject();
			object.put("vmId", host);
			JsonResponseUtil.writeJsonArray(object);
			
			return errorMap;
		} else {// 失败
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "安装失败");
			
			logService.saveOperLog("失败", "实施者" + loginUserInfo.getEmpName()
					+ "创建虚拟机" + srtManageVO.getVmName()
					+ BusinessEnvironment.OPER_RESULT_FAILURE, ass, 2);
			logService.saveOperLog("实施者" + loginUserInfo.getEmpName() + "创建虚拟机"
					+ srtManageVO.getVmName()
					+ BusinessEnvironment.OPER_RESULT_FAILURE,
					BusinessEnvironment.OPER_TYPE_ADD_VM, ass, 2);
			ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTFAIL);// 失败状态
			configAssApplyCaseService.updateConfigAssApplyCase(ass);
			errorMsg = "新资源安装出错,请联系管理员!";
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TccCloudplatform> getAllCloudplatform() {
		String hql = " from TccCloudplatform where enableFlg=1   ";
		List<TccCloudplatform> listCloudPlatform = this.commonDao.find(hql);
		if (null != listCloudPlatform && listCloudPlatform.size() > 0) {
			return listCloudPlatform;
		}
		return null;
	}

	public Map<String, String> createVC(Long assId, IpZoneEnum ipZone,
			String clusterId, TccProjectInfo tccProjectInfo, String vmName,
			String osName, String networkname, String datastoreName,SrtManageVO srtManageVO)
			throws Exception, Throwable {
		String currentUserId = LoginUserInfoHolder.getInstance().getCurrentUser().getUserPartyId().toString();
		//当前虚拟机安装日志
		if(installLog.get(currentUserId)==null){
			installLog.put(currentUserId,new ArrayList<String>());
		}
		List<String> installLog_current = installLog.get(currentUserId);
		
		Map<String, String> errorMap = new HashMap<String, String>();
		String errorMsg = "";
		TccConfigAssApplycase ass = configAssApplyCaseService.getConfigAssApplyCase(assId);
		// 根据安装界面传来的集群表ID，选择相应的集群记录
		TccClusterConfig clusterConfig = vmAssistService.getClusterConfig(Long.parseLong(srtManageVO.getClusterId()));
		// 设置中间件名称
		ass.setMiddlewareName(applicationCacheService.getMapValue("273", ass.getMiddlewareCd()));
		CloneVitualMachineRequest clonevmRequest = new CloneVitualMachineRequest();
		clonevmRequest.setCloud(srtManageVO.getCloudplatformId());
		clonevmRequest.setName(srtManageVO.getVmName());
		
		// 获取区域
		TccCloudDatacenter dc = (TccCloudDatacenter) commonDao.get(TccCloudDatacenter.class,
						Long.parseLong(srtManageVO.getZoneId()));
		installLog_current.add("已选择Datacenter:" + dc.getZoneName());
		// ***** by zhachaoy i*****
		// 设置虚拟机克隆参数
		VirtualMachineCloneSpec virtualMachineCloneSpec = new VirtualMachineCloneSpec();

		// 配置新克隆虚拟机物理信息
		VirtualMachineRelocateSpec virtualMachineRelocateSpec = new VirtualMachineRelocateSpec();
		// 设置克隆后的虚拟机存储于那个集群
		// 查询集群
		ListClusterComputeResourcesRequest listClusterRequest = new ListClusterComputeResourcesRequest();
		listClusterRequest.setCloud(srtManageVO.getCloudplatformId());
		listClusterRequest.setName(clusterConfig.getClusterName());
		listClusterRequest = (ListClusterComputeResourcesRequest) TccCloudPlatUitls.setRequestParam(listClusterRequest);
		ListClusterComputeResourcesResponse listClusterResponse= (ListClusterComputeResourcesResponse) TccCloudPlatUitls
				.getVmwareAdapte().execute(listClusterRequest);
		ClusterComputeResource clusterResource = listClusterResponse.
				getListClusterComputeResourcesResponse().getClusterComputeResource().get(0);
		try {
			installLog_current.add("已选择Cluster:" + clusterConfig.getClusterName());
			// 设置池
			virtualMachineRelocateSpec.setPool(clusterResource.getResourcePool().getMOR());
		} catch (Exception e) {
			logger.error("未发现集群" + clusterConfig.getClusterName());
			errorMsg = "未发现集群" + clusterConfig.getClusterName();
			installLog_current.add(errorMsg);
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			errorMap.put("SUCCESS", "false");
			errorMap.put("msg", errorMsg);
			return errorMap;
		}
		
		// 获取主机
		TccPhysiscResourceInfo physiscResourceInfo =  getTccPhysiscById(srtManageVO.getPhysicalId());//getHostBycluster(srtManageVO.getClusterId());
		// 获取模版并验证模版是否可用
		TccTemplateCase template = isoCaseService.queryTemplateByHostInfo(ass.getIsoId(), 
				dc.getUuid(), physiscResourceInfo.getVirtualFlag());
		// 查询模版
		GetVirtualMachineRequest GetVirtualMachineRequestT = new GetVirtualMachineRequest();
		GetVirtualMachineRequestT.setCloud(srtManageVO.getCloudplatformId());
		if (template != null) {
			GetVirtualMachineRequestT.setVmName(template.getTemplateName());
		}
		GetVirtualMachineRequestT = (GetVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(GetVirtualMachineRequestT);
		GetVirtualMachineResponse GetVirtualMachineResponseT = (GetVirtualMachineResponse) 
				TccCloudPlatUitls.getVmwareAdapte().execute(GetVirtualMachineRequestT);
		
		installLog_current.add("已选择模板:" + template.getTemplateName());

		// 查询集群里合适的存储器
		Datastore datastoreT = null;
		// 由模板来查找存储
		for (Datastore datasotre : clusterResource.getDatastores()) {
			if(srtManageVO.getStorageName()!=null  && srtManageVO.getStorageName().equals(datasotre.getName())){
				datastoreT = datasotre;
				break;
			}
			
			if (null == datastoreT) {
				datastoreT = datasotre;
			} else {
				try {
					if (datasotre.getSummary().accessible == true
							&& "VMFS".equalsIgnoreCase(datasotre.getSummary().getType())) {
						if (datasotre.getInfo().getFreeSpace() > datastoreT.getInfo().getFreeSpace()) {
							datastoreT = datasotre;
						}
					}
				} catch (Exception e) {
					logger.warn("存在未知datastore:" + datasotre.getName());
				}
			}
		}
		if(datastoreT==null){
			logger.error("未找到相关存储");
			errorMsg = "未找到相关存储";
			installLog_current.add(errorMsg);
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			errorMap.put("SUCCESS", "false");
			errorMap.put("msg", errorMsg);
			return errorMap;
		}
		installLog_current.add("已选择存储:" + datastoreT.getName());
		
		// 剩余存储默认使用的单位是B
		double freeStore = Double.parseDouble(Long.toString(datastoreT.getInfo().getFreeSpace())) / 1024 / 1024 / 1024;
		if (MIN_STORE_FREE > freeStore) {
			errorMsg = "存储容量不足，请联系管理员！";
			installLog_current.add(errorMsg);
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			errorMap.put("SUCCESS", "false");
			errorMap.put("msg", errorMsg);
			return errorMap;
		}
		try {
			// 设置存储器
			virtualMachineRelocateSpec.setDatastore(datastoreT.getMOR());// 设置克隆,文件存放地点
		} catch (Exception e) {
			logger.error("在指定集群" + clusterConfig.getClusterName() + "中，未发现指定存储" + datastoreName);
			errorMsg = "在指定集群" + clusterConfig.getClusterName() + "中，未发现指定存储" + datastoreName;
			installLog_current.add("在指定集群" + clusterConfig.getClusterName() + "中，未发现指定存储" + datastoreName);
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			errorMap.put("SUCCESS", "false");
			errorMap.put("msg", errorMsg);
			return errorMap;
		}
		
		// 查询集群里合适的物理机
		HostSystem hostSystemT = null;
		for (HostSystem hostSystem : clusterResource.getHosts()) {
			
			if(physiscResourceInfo.getPhysicsName().equals(hostSystem.getName())){
				hostSystemT = hostSystem;
			}
			
		/*	if (null == hostSystemT) {
				for(Datastore store : hostSystem.getDatastores()){
					if(datastoreT.getMOR().getVal().equals(store.getMOR().getVal())){
						hostSystemT = hostSystem;
					}
				}
			} else {
				try {
					for(Datastore store : hostSystem.getDatastores()){
						if(datastoreT.getMOR().getVal().equals(store.getMOR().getVal())){
							if (hostSystem.getVms().length < hostSystemT.getVms().length) {
								hostSystemT = hostSystem;
							}
						}
					}
				} catch (Exception e) {
					logger.warn("存在未知host:" + hostSystem.getName());
				}
			}*/
		}
		try {
			// 设置物理机
			virtualMachineRelocateSpec.setHost(hostSystemT.getMOR());// 必须设置,否则无法克隆成功
			
			installLog_current.add("已选择Host:" + hostSystemT.getName());
			
		} catch (Exception e) {
			logger.error("在指定集群" + clusterConfig.getClusterName() + "中，未发现物理机");
			errorMsg = "在指定集群" + clusterConfig.getClusterName() + "中，未发现物理机";
			installLog_current.add(errorMsg);
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			errorMap.put("SUCCESS", "false");
			errorMap.put("msg", errorMsg);
			return errorMap;
		}
		// 配置新克隆虚拟机配置信息
		VirtualMachineConfigSpec virtualMachineConfigSpec = new VirtualMachineConfigSpec();
		// 设置虚拟机的memory
		virtualMachineConfigSpec.setMemoryMB(ass.getRamSize().longValue());
		// 设置虚拟机的cpu
		virtualMachineConfigSpec.setNumCPUs(ass.getCpuSize().intValue());
		
		virtualMachineCloneSpec.setConfig(virtualMachineConfigSpec);
		
		
		// 配置新克隆虚拟机状态信息
		virtualMachineCloneSpec.setPowerOn(true);
		virtualMachineCloneSpec.setTemplate(false);
			clonevmRequest.setVirtualMachine(GetVirtualMachineResponseT.getVitualMachine());
			clonevmRequest.setVirtualMachineCloneSpec(virtualMachineCloneSpec);
			Long ipConfigId = getIpConfigId(srtManageVO.getNetworkId(), srtManageVO.getIpAddress(),null);
			TccIpConfigInfo ipinfo = (TccIpConfigInfo) this.commonDao.get(TccIpConfigInfo.class, ipConfigId);
			if (template==null||GetVirtualMachineResponseT.getVitualMachine()==null) {
				errorMsg = "此镜像下没有可用的模版!";
				logger.error(errorMsg);
				errorMap.put("FAILURE", errorMsg);
				errorMap.put("SUCCESS", "false");
				errorMap.put("msg", errorMsg);
				return errorMap;
			}else{
				if(ipinfo != null && StringUtils.isNotBlank(ipinfo.getMemo())){
					
					
					String[] cidr = ipinfo.getMemo().split(",");
					CustomizationSpec customSpec = new CustomizationSpec();
					
					if(template.getDbosVersionCd().contains("window")||template.getMemo().contains("window"))//判断需要安装window时使用，安装linux时不使用
					{
//						CustomizationWinOptions winOptions = new CustomizationWinOptions();					
//						winOptions.setChangeSID(true);
//						winOptions.setDeleteAccounts(false);
//						customSpec.setOptions(winOptions);					
//						
//						CustomizationSysprep winPrep = new CustomizationSysprep();							
//						CustomizationGuiUnattended unattended=new CustomizationGuiUnattended();
//				        unattended.setTimeZone(4);
//				        unattended.setAutoLogon(false);
//				        unattended.setAutoLogonCount(0);
//				        winPrep.setGuiUnattended(unattended);
//
//				        CustomizationUserData userData=new CustomizationUserData();
//				        userData.setFullName("cloudrabbit.cn");
//				        userData.setOrgName("cloudrabbit.cn");
//				        CustomizationVirtualMachineName virtualMachineName=new CustomizationVirtualMachineName();
//				        userData.setComputerName(virtualMachineName);
//				        userData.setProductId("HWRFF-2FFYX-XFXP2-DYFC3-BX3B7");
//				        winPrep.setUserData(userData);
//
//				        CustomizationIdentification identification=new CustomizationIdentification();
//				        identification.setJoinWorkgroup("WORKGROUP");
//				        winPrep.setIdentification(identification);	
//				        
//						customSpec.setIdentity(winPrep);
						
						CustomizationWinOptions winOptions = new CustomizationWinOptions();					
						winOptions.setChangeSID(true);
						winOptions.setDeleteAccounts(false);
						customSpec.setOptions(winOptions);					
						
						CustomizationSysprep winPrep = new CustomizationSysprep();							
						CustomizationGuiUnattended unattended=new CustomizationGuiUnattended();
				        unattended.setTimeZone(4);
				        unattended.setAutoLogon(false);
				        unattended.setAutoLogonCount(0);
				        
				        CustomizationPassword customizationpassword = new CustomizationPassword();
				        customizationpassword.setPlainText(true);
				        customizationpassword.setValue("Cloud@123456");
				        unattended.setPassword(customizationpassword);
				        
				        winPrep.setGuiUnattended(unattended);

				        CustomizationUserData userData=new CustomizationUserData();
				        userData.setFullName("micro");
				        userData.setOrgName("micro");
				        CustomizationVirtualMachineName virtualMachineName=new CustomizationVirtualMachineName();
				        userData.setComputerName(virtualMachineName);
				        userData.setProductId("");
				        winPrep.setUserData(userData);

				        CustomizationIdentification identification=new CustomizationIdentification();
				        identification.setJoinWorkgroup("WORKGROUP");
				        winPrep.setIdentification(identification);	
				        
						customSpec.setIdentity(winPrep);
						
					}else{
						CustomizationLinuxOptions linuxOptions = new CustomizationLinuxOptions();
						customSpec.setOptions(linuxOptions);
						CustomizationLinuxPrep linuxPrep = new CustomizationLinuxPrep();
						linuxPrep.setDomain("default");
						CustomizationFixedName fixedName = new CustomizationFixedName();
						fixedName.setName(vmName);// hostname
						linuxPrep.setHostName(fixedName);					
						customSpec.setIdentity(linuxPrep);
					}
					CustomizationFixedIp fixedIp = new CustomizationFixedIp();
					fixedIp.setIpAddress(ipinfo.getIpAddress());// ipaddress
					
					installLog_current.add("已设置IP:" + ipinfo.getIpAddress());
					
					CustomizationIPSettings adapter = new CustomizationIPSettings();
					adapter.setIp(fixedIp);
					if(cidr.length >= 3 && StringUtils.isNotBlank(cidr[1])){
						if(StringUtil.checkNetworkInfoFormat(cidr[1])){
							adapter.setGateway(new String[] { cidr[1] });
						}
					}
					if(cidr.length >= 3 && StringUtils.isNotBlank(cidr[0])){
						if(StringUtil.checkNetworkInfoFormat(cidr[0])){
							adapter.setSubnetMask(cidr[0]);
						}
					}
					CustomizationAdapterMapping adapterMap = new CustomizationAdapterMapping();
					
					adapter.setNetBIOS(CustomizationNetBIOSMode.disableNetBIOS);
					
					adapterMap.setAdapter(adapter);
					CustomizationAdapterMapping[] nicSettingMap = new CustomizationAdapterMapping[] { adapterMap };
					customSpec.setNicSettingMap(nicSettingMap);
					CustomizationGlobalIPSettings gIP = new CustomizationGlobalIPSettings();
					if(cidr.length >= 3 && StringUtils.isNotBlank(cidr[2])){
						if(StringUtil.checkNetworkInfoFormat(cidr[2])){
							gIP.setDnsServerList(new String[] { cidr[2] });
						}else{
							gIP.setDnsServerList(new String[] { "114.114.114.114" });
						}
					}
//					VirtualDeviceConfigSpec[] specs = new VirtualDeviceConfigSpec[] {nicSpec};
//					virtualMachineConfigSpec.setDeviceChange(specs);
					
					
					customSpec.setGlobalIPSettings(gIP);
					
					virtualMachineCloneSpec.setCustomization(customSpec);
				}
			}
		
		
		virtualMachineCloneSpec.setLocation(virtualMachineRelocateSpec);
		
		// 查询数据中心
		ListDatacentersRequest ListDatacentersRequestT = new ListDatacentersRequest();
		ListDatacentersRequestT.setCloud(srtManageVO.getCloudplatformId());
		ListDatacentersRequestT.setName(clusterConfig.getDcname());
		ListDatacentersRequestT = (ListDatacentersRequest) TccCloudPlatUitls.setRequestParam(ListDatacentersRequestT);
		ListDatacentersResponse ListDatacentersResponseT = (ListDatacentersResponse) 
				TccCloudPlatUitls.getVmwareAdapte().execute(ListDatacentersRequestT);

		clonevmRequest.setDatacenter(ListDatacentersResponseT.getListDatacentersResponse().getDatacenter().get(0));
		clonevmRequest = (CloneVitualMachineRequest) TccCloudPlatUitls.setRequestParam(clonevmRequest);
		installLog_current.add("虚拟机:" + vmName + "安装中...");
		
		CloneVitualMachineResponse cloneVmResponse = null;
		
		cloneVmResponse = (CloneVitualMachineResponse) 
				TccCloudPlatUitls.getVmwareAdapte().execute(clonevmRequest);
		
		if (cloneVmResponse.isResult()) {
			installLog_current.add("虚拟机:" + vmName + "安装成功");
			//修改虚拟机网络标签
			TccVlanNetwork network = (TccVlanNetwork) commonDao.get(TccVlanNetwork.class, ipinfo.getNetworkId());
			if(network != null){
				String[] networkTags =network.getNetworkTag().split("_&");
				String networkTag = networkTags[1];
				installLog_current.add("虚拟机:" + vmName + "正在重新配置网络标签"+"["+networkTag+"]");
				VirtualMachineUpdateNetworkRequest updateVmNetworkRequest = new VirtualMachineUpdateNetworkRequest();
				updateVmNetworkRequest.setCloud(srtManageVO.getCloudplatformId());
				updateVmNetworkRequest.setName(vmName);
				updateVmNetworkRequest.setAdaptername("Network adapter");
				updateVmNetworkRequest.setNetname(networkTag);
				updateVmNetworkRequest = (VirtualMachineUpdateNetworkRequest)TccCloudPlatUitls.setRequestParam(updateVmNetworkRequest);
				VirtualMachineUpdateNetworkResponse updateVmNetworkResponse = (VirtualMachineUpdateNetworkResponse)TccCloudPlatUitls.getVmwareAdapte().execute(updateVmNetworkRequest);
				if(updateVmNetworkResponse!=null && ResponseParameter.SUCCESS.equals(updateVmNetworkResponse.getSuccess())){
					installLog_current.add("虚拟机:" + vmName + "重新配置成功");
				}else{
					installLog_current.add("虚拟机:" + vmName + "重新配置网络标签失败，请至后台手动修改");
				}
			}
			
			
			installLog_current.add("虚拟机:" + vmName + "安装成功");
			
			// 虚拟机创建成功，给他加的外挂存储
			try {
				List<TccStorageApplyCaseAss> storageList=getStorageApplyCaseByAssid(ass.getAssId());
				
				installLog_current.add("虚拟机:" + vmName + "挂载数据盘开始");
				
				for (TccStorageApplyCaseAss storageOB : storageList) {
					if (storageOB.getStorageSize()>0) {
						AddVirtualMachineDiskRequest addDiskRequest = new AddVirtualMachineDiskRequest();
						addDiskRequest.setCloud(srtManageVO.getCloudplatformId());
						addDiskRequest.setDiskSize(storageOB.getStorageSize().longValue()+"");//ass.getSanStorageSize()
						addDiskRequest.setName(vmName);
						addDiskRequest.setVirtualDiskType(srtManageVO.getVirtualDiskType());
						addDiskRequest = (AddVirtualMachineDiskRequest) TccCloudPlatUitls.setRequestParam(addDiskRequest);
						AddVirtualMachineDiskReponse addVirtualMachineDiskReponse = (AddVirtualMachineDiskReponse) 
								TccCloudPlatUitls.getVmwareAdapte().execute(addDiskRequest);
						if(addVirtualMachineDiskReponse != null && "true".equalsIgnoreCase(addVirtualMachineDiskReponse.getSuccess())){
							this.logger.info("VirtualMachine [" + vmName + "] attach disk successful! Disk size is " 
									+ storageOB.getStorageSize().longValue());
						}else{
							this.logger.error("VirtualMachine [" + vmName + "] attach disk failed! Disk size is " 
									+ storageOB.getStorageSize().longValue());
						}
					}
					
					installLog_current.add("虚拟机:" + vmName + "挂载数据盘结束");
					
				}
			} catch (Exception e) {
				logger.error("VirtualMachine [" + vmName + "] attach disk failed!", e);
			}
			
			GetVirtualMachineRequest getVmRequest = new GetVirtualMachineRequest();
			getVmRequest.setCloud(srtManageVO.getCloudplatformId());
			getVmRequest.setVmName(vmName);
			getVmRequest = (GetVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(getVmRequest);
			GetVirtualMachineResponse response = (GetVirtualMachineResponse) TccCloudPlatUitls.getVmwareAdapte().execute(getVmRequest);
			String vmGutestId = response.getVitualMachine().getConfig().getGuestId();
			// 该操作系统的一级操作系统
			if (vmGutestId.contains("win") || vmGutestId.contains("windows")) {
				vmGutestId = "windows";
			} else {
				vmGutestId = "linux";
			}
			ass.setOsType(vmGutestId);
			// 告诉工单虚拟机安装好了
			ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTSUCC);
			configAssApplyCaseService.updateConfigAssApplyCase(ass);
			ass = configAssApplyCaseService.getConfigAssApplyCase(ass.getAssId());
			// 保存服务请求主表与已分配资源代码表
			TccSrt srt = vmAssistService.getSrt(ass.getTccSrt().getSrtId());
			TccApplyedHostResource applyedHostResource = vmAssistService
					.mergeApplyedHostResource(response.getVitualMachine(), ass, null, null, clusterId);
			applyedHostResource.setOsVersionCd(template.getOsVersionCd());
			applyedHostResource.setCloseDttm(ass.getCloseDttm());
			applyedHostResource.setTemplateId(template.getTemplateCaseId().toString());
			applyedHostResource.setHardwareTypeCd("186");
			//设置虚拟机关联的流程编号
			if(null!=srtManageVO.getHostInfoProduceId()&&srtManageVO.getHostInfoProduceId().length()>0){
				applyedHostResource.setProduceId(srtManageVO.getHostInfoProduceId());
			}
			// 此处获取ip地址
			applyedHostResource.setIpAddress(srtManageVO.getIpAddress());
			if(ipConfigId!=null && ipConfigId!=-1){
				applyedHostResource.setTccIpConfigInfo(ipinfo);
			}
			// 修正虚拟机的账号密码信息
			if("windows".equals(ass.getOsType())){
				applyedHostResource.setHostLoginUsername("Administrator");
			}
			else{
				if(StringUtils.isNotBlank(template.getUsername())){
					applyedHostResource.setHostLoginUsername(template.getUsername());
				}
			}
			
			if("windows".equals(ass.getOsType())){
				applyedHostResource.setHostLoginPassword("Cloud@123456");
				applyedHostResource.setOriginalRootPassword("Cloud@123456");

			}else{
				if(StringUtils.isNotBlank(template.getPasswd())){
					applyedHostResource.setHostLoginPassword(template.getPasswd());
					applyedHostResource.setOriginalRootPassword(template.getPasswd());
				}
			}
			
			 /** add host to monitor by Destiny : begin **/ 
			String host = String.valueOf(applyedHostResource.getApplyResourceId());	//虚拟机ID
			String visibleName = applyedHostResource.getHostNane(); //虚拟机名称
	
			
		
			//备用,勿删
			/*try {
				//ghs 安装完成后同步虚拟机实际存储到相关资源表
				TccApplyedHostResource hostResource = (TccApplyedHostResource) 
					commonDao.get(TccApplyedHostResource.class, applyedHostResource.getApplyResourceId());
				com.vmware.vim25.mo.VirtualMachine aimVm = response.getVitualMachine();
				if (aimVm.getConfig().getHardware() != null) {
					hostResource.setCpuCoreCount(Double.valueOf(aimVm.getConfig().getHardware().getNumCPU()));
					hostResource.setRamSize(Double.valueOf(aimVm.getConfig().getHardware().getMemoryMB()));
					boolean flag = false;
					Double nasStorage = 0.0;
					if (aimVm.getConfig().getHardware().device != null) {
						for (int i = 0; i < aimVm.getConfig().getHardware().device.length; i++) {
							if (VirtualDisk.class.isInstance(aimVm.getConfig().getHardware().device[i])) {
								VirtualDisk virtualDisk = (VirtualDisk) aimVm.getConfig().getHardware().device[i];
								if (virtualDisk.getCapacityInKB() / 1024 / 1024 != 0) {
									//stccApplyedHostResource.setStorageSize((double) virtualDisk.getCapacityInKB() / 1024 / 1024);
									flag = true;
									if (flag) {
										nasStorage += virtualDisk.getCapacityInKB() / 1024 / 1024;
									}
								}
							}
						}
						hostResource.setStorageSize(nasStorage);
					}
				}
				this.commonDao.update(hostResource);
			} catch (Exception e) {
				logger.error("安装已完成,更新已有存储信息失败",e);
			}*/
			
			//保存虚拟机相关信息到合胜资源平台
			VMSaveResponse vmSaveResponse = synchCMDB.saveVM(applyedHostResource);
			if(null == vmSaveResponse || ResponseParameter.FAILED.equals(vmSaveResponse.getSuccess())){
				logger.info("------------------保存虚拟机相关信息到合胜资源平台失败");
			}
			
			commonDao.update(applyedHostResource); 
			/** add host to monitor by Destiny : end **/

			// 保存IP地址到虚拟机表里面
			vmAssistService.saveSrtApplyedhostAss(applyedHostResource, srt);
			// 更新ip的状态
			StringBuffer updateSql = new StringBuffer();
			updateSql.append("UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='创建");
			updateSql.append(vmName+ "虚拟机',DISTRIBUTE_TIME=NOW()");
			updateSql.append(" WHERE f.IP_ADDRESS ='" + srtManageVO.getIpAddress() + "'");
			// 更新ip的状态
			commonDao.updateByNativeSql(updateSql.toString());
			// 剩余CPU、MEM减少
			capacityMgmtService.takingCpuRam(applyedHostResource
					.getTccPhysiscResourceInfo(),ass.getCpuSize()*1000, ass.getRamSize()*1024);
			// 保存项目与已申请资源表关系
			TccProjectInfo projectInfo = tccProjectInfo;
			vmAssistService.saveProjectAss(applyedHostResource, projectInfo);
			LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
			this.saveResOperLog("创建", StringUtils.substringAfterLast(
	    			TccApplyedHostinfo.class.toString(), "."), ass.getAssId(),  //安装成功日志
					 loginUserInfo.getEmpName()+ " 创建vcenter虚拟机"+applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"+"成功");
			logService.saveOperLog(BusinessEnvironment.OPER_RESULT_SUCCESS,"创建", ass, 2);
			installLog_current.add("虚拟机:" + vmName + "安装结束");
			try {
				if(!zabbixApiService.isAddHostToMonitorByHostName(host)){ 
					
					installLog_current.add("虚拟机:" + vmName + "添加Zabbix监控开始");
					
				    HostMonitorBean hostinfo = new HostMonitorBean(); 
				    hostinfo.setHostName(host); 
				    hostinfo.setVisibleName(visibleName); 
				    hostinfo.setIp(applyedHostResource.getIpAddress()); 
				    hostinfo.setOsName(vmGutestId); 
				    applyedHostResource.setMonitorFlg(zabbixApiService.addHostToMonitor(hostinfo) ? "1" : "0"); 
				    
				    installLog_current.add("虚拟机:" + vmName + "添加Zabbix监控结束");
				    
				}else{ 
				    logger.info("主机["+host+"]已在zabbix中注册"); 
				    installLog_current.add("虚拟机:[" + vmName + "]已在zabbix中注册");
				    applyedHostResource.setMonitorFlg("1"); 
				}
			} catch (Exception ex) {
				this.logger.error("添加监控失败！", ex);
			} catch (Throwable t) {
				this.logger.error("添加监控失败！", t);
			}
			commonDao.update(applyedHostResource); 
			logger.info("调用vCenter接口创建虚拟机成功，虚拟机名称：" + applyedHostResource.getHostNane());
			errorMap.put("SUCCESS", applyedHostResource.getApplyResourceId() + "");
			// 如果创建虚拟机成功，虚拟机信息插入到snapshotcategory表里面
			TccSnapshotCfg snapshotCfg = new TccSnapshotCfg();
			snapshotCfg.setCrtDttm(new Date());
			Set<TccApplyedHostinfo> tccApplyedHostinfos = ass.getTccApplyedHostinfos();
			for (TccApplyedHostinfo temp : tccApplyedHostinfos) {
				snapshotCfg.setCrtUserId(temp.getCrtUserId() + "");
				break;
			}
			snapshotCfg.setCrtUserId(String.valueOf(srt.getCrtUserId()));
			snapshotCfg.setShotMaxNum(0L);
			snapshotCfg.setHostId(applyedHostResource.getApplyResourceId());
			snapshotCfg.setVmName(response.getVitualMachine().getName());
			snapshotCfg.setEnableFlg("1");
			snapshotCategoryService.AddSnapshotCategory(snapshotCfg);
			
			//用ssh执行调整
			/*
			 * root登录
				echo vm.hugetlb_shm_group  = 1002 >>/etc/sysctl.conf 
				sysctl - p
				sqlplus / as sysdba
				startup			 
			 * */
//			this.startOrStopOracleIns(srtManageVO.getIpAddress());
			// 系統添加賬戶
			
			TCcEvn ccEvn = srtManageVO.getEvn();
			boolean initSnapshot = ccEvn!=null ?ccEvn.isInitSnapshot():false;
			//判断是否需要初始快照
			if(initSnapshot){
			//新建虚拟机的时候自动创建一个快照
				try {
					installLog_current.add("虚拟机:" + vmName + "创建初始快照开始");
					boolean Status = false;
		    		String noteString = "新建虚拟机时自动创建的快照";
		    		vmSnapshotService.createVmSnapshot(host.trim(),Status,noteString.trim());
					//把创建的虚拟机的快照设为初始创建快照时创建
		    		//创建完快照时需要休眠300毫秒才能查询数据
		    		Thread.sleep(300);
		    		String sql="UPDATE T_CC_SNAPSHOT_HISTORY SET SNAPSHOT_MODE = 3 WHERE VM_ID = "+host;
		    		commonDao.updateByNativeSql(sql);
		    		installLog_current.add("虚拟机:" + vmName + "创建初始快照成功");
		    		
				} catch (Exception e) {
					logger.error("新建虚拟机" + visibleName + "时，自动创建的快照失败");
					installLog_current.add("虚拟机:" + vmName + "创建初始快照失败");
				}
			}
			//把虚拟机的ID用json返回页面
//			JSONObject object = new JSONObject();
//			object.put("vmId", host);
//			JsonResponseUtil.writeJsonArray(object);
			try {
				generateviRtualMachineRemark(srt, projectInfo, applyedHostResource);
			} catch (Exception e) {
				logger.error("新建虚拟机" + visibleName + "时，添加默认备注失败");
			}
			
			return errorMap;
		} else {
			installLog_current.add("虚拟机:" + vmName + "安装失败");
			
			ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTFAIL);// 失败状态
			configAssApplyCaseService.updateConfigAssApplyCase(ass);
			LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
			logService.saveOperLog("失败", "实施者" + loginUserInfo.getEmpName()
					+ "创建venter虚拟机" + vmName + BusinessEnvironment.OPER_RESULT_FAILURE, ass, 2);
			logger.info("调用vCenter接口创建虚拟机失败");
			errorMsg = "老资源安装出错,请联系管理员!";
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			errorMap.put("SUCCESS", "false");
			errorMap.put("msg", errorMsg);
			return errorMap;
		}
	}

	
	
	public void generateviRtualMachineRemark(TccSrt srt,TccProjectInfo projectInfo
			,TccApplyedHostResource  hostResource ) throws Exception, Throwable{
		//申请人相关信息
		TccEmployee employee = (TccEmployee) this.commonDao.get(TccEmployee.class, srt.getCrtUserId());
		StringBuffer remarkBuffer =new  StringBuffer();
		remarkBuffer.append("申请人：[");
		remarkBuffer.append(employee.getEmployeeName());
		remarkBuffer.append("]");
		remarkBuffer.append(",申请人邮箱：[");
		remarkBuffer.append(employee.getUserEmail());
		remarkBuffer.append("],申请人手机号：[");
		remarkBuffer.append(employee.getUserRelatedMobileTel());
		remarkBuffer.append("]");
		remarkBuffer.append(",申请人所在部门：[");
		remarkBuffer.append(employee.getTccOrgan().getPartyFullname());
		remarkBuffer.append("]");
		remarkBuffer.append(",申请资源所在项目：[");
		remarkBuffer.append(projectInfo.getProjectName()+"]");
		addVmRemark(hostResource.getApplyResourceId(), remarkBuffer.toString());
	}
	@Override
	public Map<String, String> createPhysical(SrtManageVO srtManageVO, TccProjectInfo tccProjectInfo) throws Throwable {
		String currentUserId = LoginUserInfoHolder.getInstance().getCurrentUser().getUserPartyId().toString();
		//当前虚拟机安装日志
		if(installLog.get(currentUserId)==null){
			installLog.put(currentUserId,new ArrayList<String>());
		}
		List<String> installLog_current = installLog.get(currentUserId);
		Map<String, String> errorMap = new HashMap<String, String>();
		String prompt = "";
		TccConfigAssApplycase ass = configAssApplyCaseService.getConfigAssApplyCase(Long.parseLong(srtManageVO.getAssId()));
		ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTSUCC);
		configAssApplyCaseService.updateConfigAssApplyCase(ass);
		ass = configAssApplyCaseService.getConfigAssApplyCase(ass.getAssId());
	
		// 保存服务请求主表与已分配资源代码表
		TccApplyedHostResource applyedHostResource = vmAssistService.mergePhysical(ass, srtManageVO);
		if(null !=srtManageVO.getOfflineinstall() && "1".equals(srtManageVO.getOfflineinstall())){
			// 获取主机
			TccPhysiscResourceInfo physiscResourceInfo = getHostBycluster(srtManageVO.getClusterId());
			// 获取区域
			TccCloudDatacenter dc = (TccCloudDatacenter) commonDao.get(TccCloudDatacenter.class,
							Long.parseLong(srtManageVO.getZoneId()));
			// 获取模版并验证模版是否可用
			TccTemplateCase template = isoCaseService.queryTemplateByHostInfo(ass.getIsoId(), 
					dc.getUuid(), physiscResourceInfo.getVirtualFlag());
			applyedHostResource.setOsVersionCd(template.getOsVersionCd());
			applyedHostResource.setTemplateId(template.getTemplateCaseId().toString());
			applyedHostResource.setZoneId(srtManageVO.getZoneId());
			// 此处获取ip地址
			 applyedHostResource.setIpAddress(srtManageVO.getIpAddress());
			  applyedHostResource.setHostNane(srtManageVO.getVmName());
	          applyedHostResource.setResourceType("1");//资源类型1、线下安装虚拟机
	      	// 更新ip的状态
				StringBuffer updateSql = new StringBuffer();
				updateSql.append("UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='创建");
				updateSql.append(srtManageVO.getVmName()+ "虚拟机',DISTRIBUTE_TIME=NOW()");
				updateSql.append(" WHERE f.IP_ADDRESS ='" + srtManageVO.getIpAddress() + "'");
				// 更新ip的状态
				commonDao.updateByNativeSql(updateSql.toString());
				prompt = "虚拟机 :"+srtManageVO.getVmName();
		}else{ //物理机
			// 此处获取ip地址
			 applyedHostResource.setIpAddress(srtManageVO.getPhysicalNetwork());
			  applyedHostResource.setHostNane(srtManageVO.getPhysicalName());
	          applyedHostResource.setResourceType("3");//资源类型2、线下安装物理机
	          applyedHostResource.setSerialNo(srtManageVO.getPhysicalSerialNum());
	          prompt = "物理机 :"+srtManageVO.getPhysicalName();
		}
		applyedHostResource.setOfflineinstall(true);
		applyedHostResource.setCloseDttm(ass.getCloseDttm());
		applyedHostResource.setHardwareTypeCd("186");
		//设置虚拟机关联的流程编号
		if(null!=srtManageVO.getHostInfoProduceId()&&srtManageVO.getHostInfoProduceId().length()>0){
			applyedHostResource.setProduceId(srtManageVO.getHostInfoProduceId());
		}
		
		commonDao.update(applyedHostResource); 
		/** add host to monitor by Destiny : end **/
		// 保存服务请求主表与已分配资源代码表
		TccSrt srt = vmAssistService.getSrt(ass.getTccSrt().getSrtId());
		// 保存IP地址到虚拟机表里面
		vmAssistService.saveSrtApplyedhostAss(applyedHostResource, srt);
		// 剩余CPU、MEM减少
		capacityMgmtService.takingCpuRam(applyedHostResource
				.getTccPhysiscResourceInfo(),ass.getCpuSize()*1000, ass.getRamSize()*1024);
		// 保存项目与已申请资源表关系
		TccProjectInfo projectInfo = tccProjectInfo;
		vmAssistService.saveProjectAss(applyedHostResource, projectInfo);
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		
		this.saveResOperLog("创建", StringUtils.substringAfterLast(
    			TccApplyedHostinfo.class.toString(), "."), ass.getAssId(),  //安装成功日志
				"实施者 " + loginUserInfo.getEmpName()+ " 线下安装"+prompt);
		logService.saveOperLog(BusinessEnvironment.OPER_RESULT_SUCCESS,"创建", ass, 2);
		
		installLog_current.add(prompt+"安装结束");
		
		logger.info("线下安装虚拟机成功，虚拟机名称：" + applyedHostResource.getHostNane());
		errorMap.put("SUCCESS", applyedHostResource.getApplyResourceId() + "");
		return errorMap;
	}
	
	
	
	
	
	
	public boolean startOrStopOracleIns(String host, String startOrStop)
			throws Exception {
		Integer port = 22;
		boolean isSuccess = false;
		//oracle用户
		String userOracle = "root";
		String passwordOracle = "password";
		String command2 = "cd /root;chmod 775 "+startOrStop+"Oracle.sh;sh "+startOrStop+"Oracle.sh";
		/**
		 *	用root用户登录修改sysctl，添加oracle用户组至内核 
		 **/
    	JSch jsch = new JSch();
    	Session session = jsch.getSession(userOracle, host, port);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setPassword(passwordOracle);
		session.connect();
		//脚本文件存放的文件夹
//		String remotePath="/home";
		//上传脚本文件
//		uploadSHFile(session,shPath,remotePath);
		
		/*******************************************执行启动脚本**************************/
		String result = "";
		try {
			result = execOracleSH(session,command2);
			isSuccess = true;
		} catch (Exception e) {
			isSuccess = false;
		}
		
		session.disconnect();
		return isSuccess;
	}
	/**
	 * 执行启动oracle脚本
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws JSchException 
	 */
	private String execOracleSH(Session session,String command) throws UnsupportedEncodingException, IOException, JSchException{
		ChannelExec openChannel_oracle1 = (ChannelExec) session.openChannel("exec");
		openChannel_oracle1.setCommand(command);
		openChannel_oracle1.setInputStream(null); 
		BufferedReader input = new BufferedReader(new InputStreamReader(openChannel_oracle1.getInputStream())); 
        openChannel_oracle1.connect(); 
        String resultStr = "";
        String line = "";
		while ((line = input.readLine()) != null) { 
			resultStr+= new String(line.getBytes("gbk"),"UTF-8")+"\r";
        } 
		//关闭SSH通道
		openChannel_oracle1.disconnect();
		System.err.println(resultStr);
		if(resultStr.contains("ORA-27125: unable to create shared memory segment")){
			logger.error(resultStr);
			throw new JSchException("启动失败，请联系管理员查看日志");
		}if(resultStr.equals("")){
			logger.error("启动Oracle失败,【可能错误原因】：1、脚本无执行权限；2、脚本路径错误");
			throw new JSchException("启动失败，请联系管理员查看日志");
		}
		return resultStr;
	}
	/**
	 * 执行启动oracle脚本
	 * 
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws JSchException
	 */
	// TODO未被使用
	/*
	 * private String execOracleSH(Session session,String command) throws
	 * UnsupportedEncodingException, IOException, JSchException{ ChannelExec
	 * openChannel_oracle1 = (ChannelExec) session.openChannel("exec");
	 * openChannel_oracle1.setCommand(command);
	 * openChannel_oracle1.setInputStream(null); BufferedReader input = new
	 * BufferedReader(new
	 * InputStreamReader(openChannel_oracle1.getInputStream()));
	 * openChannel_oracle1.connect(); String resultStr = ""; String line = "";
	 * while ((line = input.readLine()) != null) { resultStr+= new
	 * String(line.getBytes("gbk"),"UTF-8")+"\r"; } //关闭SSH通道
	 * openChannel_oracle1.disconnect(); System.err.println(resultStr);
	 * if(resultStr
	 * .contains("ORA-27125: unable to create shared memory segment")){
	 * logger.error(resultStr); throw new JSchException("启动失败，请联系管理员查看日志");
	 * }if(resultStr.equals("")){ logger.error("【可能错误原因】：1、脚本无执行权限；2、脚本路径错误");
	 * throw new JSchException("启动失败，请联系管理员查看日志"); } return resultStr; }
	 */
	/**
	 * 安装rabbitMQ
	 * 
	 * @param host
	 */
	@Override
	public boolean installRabbitMQ(String host) throws Exception {
		Integer port = 22;
		boolean isSuccess = true;
		// oracle用户
		String userOracle = "root";
		String password = "password";
		String command2 = "./erlang.sh";
		/**
		 * 安装rabbitmq
		 **/
		JSch jsch = new JSch();
		Session session = jsch.getSession(userOracle, host, port);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setPassword(password);
		session.connect();
		boolean hasInstalled = hasInstalled(host, session);
		if (hasInstalled) {
			return hasInstalled;
		}
		ChannelExec openChannel_oracle1 = (ChannelExec) session
				.openChannel("exec");
		openChannel_oracle1.setCommand(command2);
		BufferedReader input = new BufferedReader(new InputStreamReader(
				openChannel_oracle1.getInputStream()));
		openChannel_oracle1.connect();
		// String resultStr = "";
		String line = "";
		int index = 0;
		while ((line = input.readLine()) != null) {
			String tmp = new String(line.getBytes("gbk"), "UTF-8") + "";
			logger.info(tmp);
			// resultStr = tmp;//刚才代码写错了，在这里
			//System.err.println(index + "     " + tmp);// 203上面是不是没脚本;
			index++;
			if (index >= 25000) {
				isSuccess = false;
				break;
			}
		}
		// 关闭SSH通道
		openChannel_oracle1.disconnect();
		return isSuccess;
	}

	public boolean hasInstalled(String host, Session session)
			throws JSchException, IOException {
		String command = "ps -ef|grep rabbitmq|grep -v grep";
		ChannelExec openChannel_oracle1 = (ChannelExec) session.openChannel("exec");
		openChannel_oracle1.setCommand(command);
		BufferedReader input = new BufferedReader(new InputStreamReader(openChannel_oracle1.getInputStream()));
		openChannel_oracle1.connect();
		int index = 0;
		String line = "";
		while ((line = input.readLine()) != null) {
			String tmp = new String(line.getBytes("gbk"), "UTF-8") + "";
			System.out.println(tmp);
			index++;
		}
		if (index == 0) {
			return false;
		}
		return true;
	}


	/**
	 * 根据网络Id以及ip地址查询IP
	 * 
	 * @param networkId
	 * @param ipAddress
	 * @return
	 */
	private Long getIpConfigId(String networkId, String ipAddress, String networkName) {
		Long ipConfigId = -1L;
		if (StringUtils.isNotBlank(ipAddress)) {
			List<Object> params = new ArrayList<Object>();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ip.IP_CONFIG_ID AS id");
			sql.append(" FROM T_CC_IP_CONFIG_INFO ip ");
			sql.append(" INNER JOIN T_CC_VLAN_NETWORK vlan on vlan.ID = ip.NETWORK_ID ");
			sql.append(" where ip.IP_ADDRESS = ? ");
			params.add(ipAddress);
			if (StringUtils.isNotBlank(networkId)) {
				sql.append(" and vlan.ID = ? ");
				params.add(Long.parseLong(networkId));
			}
			if (StringUtils.isNotBlank(networkName)) {
				sql.append(" and vlan.NETWORK_NAME = ? ");
				params.add(networkName);
			}
			List<Map<String, Object>> resultMapList = this.commonDao.findAsMapList(sql.toString(), params.toArray());
			if (resultMapList != null && resultMapList.size() > 0) {
				ipConfigId = resultMapList.get(0).get("id") != null ? Long
						.parseLong(resultMapList.get(0).get("id").toString()) : null;
			}
		}
		return ipConfigId;
	}

	public Map<String, String> cloneVC(Long assId, Long sourceId,
			Long clusterId, TccProjectInfo tccProjectInfo, String vmName,
			String vlanId, String ipAddress) throws Exception, Throwable {
		Map<String, String> errorMsg = new HashMap<String, String>();
		TccConfigAssApplycase ass = (TccConfigAssApplycase) configAssApplyCaseService
				.getConfigAssApplyCase(assId);
		TccApplyedHostResource host = (TccApplyedHostResource) this.commonDao
				.get(TccApplyedHostResource.class, sourceId);
		if (EnvironmentConstant.DISENABLE.equals(host.getEnableFlg())) {
			errorMsg.put("SUCCESS", "false");
			errorMsg.put("msg", "服务器已被销毁，无法克隆！请刷新后操作！");
			return errorMsg;
		}
		// 修改虚拟机安装状态为进行中
		ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTING);
		configAssApplyCaseService.updateConfigAssApplyCase(ass);
		TccTemplateCase templateCase = (TccTemplateCase) this.commonDao.get(
				TccTemplateCase.class, Long.valueOf(host.getTemplateId()));

		// 根据安装界面传来的集群表ID，选择相应的集群记录 然后根据此记录，查找相应的物理主机信息 然后让虚拟机安装在此物理主机上
		TccClusterConfig clusterConfig = vmAssistService.getClusterConfig(clusterId);
		ass.setVrSubType(clusterConfig.getVtype());
		if (vmName != null && !vmName.equals("")) {
			boolean flag = vmAssistService.checkDuplVmName(vmName);
			if (flag == false) {
				String error = "输入的名字已经存在，请重新输入";
				logger.error(error);
				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", error);
				return errorMsg;
			}
		}
		CloneVitualMachineRequest cloneRequest = new CloneVitualMachineRequest();
		TccCloudplatform tcccloudplatform = this.findPlatformByClusterId(clusterConfig.getId() + "");
		cloneRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");

		try {
			cloneRequest.setName(vmName);
			// ***** by zhachaoy i*****
			// 设置虚拟机克隆参数
			VirtualMachineCloneSpec virtualMachineCloneSpec = new VirtualMachineCloneSpec();
			// 配置新克隆虚拟机物理信息
			VirtualMachineRelocateSpec virtualMachineRelocateSpec = new VirtualMachineRelocateSpec();
			// 设置克隆后的虚拟机存储于那个集群
			// 查询集群
			ListClusterComputeResourcesRequest ListClusterComputeResourcesRequestT = new ListClusterComputeResourcesRequest();
			ListClusterComputeResourcesRequestT.setCloud(tcccloudplatform
					.getCloudplatformId() + "");
			ListClusterComputeResourcesRequestT.setName(clusterConfig
					.getClusterName());
			ListClusterComputeResourcesRequestT = (ListClusterComputeResourcesRequest) TccCloudPlatUitls
					.setRequestParam(ListClusterComputeResourcesRequestT);
			ListClusterComputeResourcesResponse ListClusterComputeResourcesResponseT = 
					(ListClusterComputeResourcesResponse) TccCloudPlatUitls
					.getVmwareAdapte().execute(ListClusterComputeResourcesRequestT);
			ClusterComputeResource ClusterComputeResourceT = ListClusterComputeResourcesResponseT
					.getListClusterComputeResourcesResponse().getClusterComputeResource().get(0);
			try {
				// 设置池
				virtualMachineRelocateSpec.setPool(ClusterComputeResourceT.getResourcePool().getMOR());
			} catch (Exception e) {
				logger.error("未发现集群" + clusterConfig.getClusterName());
				errorMsg.put("FAILURE", "未发现集群" + clusterConfig.getClusterName());

				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", "未发现集群" + clusterConfig.getClusterName());
				return errorMsg;
			}

			// 获取主机
			TccPhysiscResourceInfo physiscResourceInfo = getHostBycluster(clusterId.toString());
			// 获取区域
			TccCloudDatacenter dc = (TccCloudDatacenter) commonDao.get(
					TccCloudDatacenter.class, Long.parseLong(clusterConfig.getZoneId()));
			// 获取模版并验证模版是否可用
			TccTemplateCase template = isoCaseService.queryTemplateByHostInfo(
					ass.getIsoId(), dc.getUuid(), physiscResourceInfo.getVirtualFlag());
			// 查询模版
			GetVirtualMachineRequest GetVirtualMachineRequestT = new GetVirtualMachineRequest();
			GetVirtualMachineRequestT.setCloud(dc.getCloudplatform().getCloudplatformId() + "");
			if (template != null && StringUtils.isNotBlank(template.getTemplateName())) {
				GetVirtualMachineRequestT.setVmName(template.getTemplateName());
			} else {
				// 存量资源无法找到当初创建它的模板，导致无法通过模板克隆
				errorMsg.put("FAILURE", "该云主机暂时不支持克隆，请联系管理员！");
				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", "该云主机暂时不支持克隆，请联系管理员！");
				return errorMsg;
			}

			GetVirtualMachineRequestT = (GetVirtualMachineRequest) TccCloudPlatUitls
					.setRequestParam(GetVirtualMachineRequestT);
			GetVirtualMachineResponse GetVirtualMachineResponseT = (GetVirtualMachineResponse) TccCloudPlatUitls
					.getVmwareAdapte().execute(GetVirtualMachineRequestT);

			// 查询集群里合适的存储器
			Datastore datastoreT = null;
			// 由模板来查找存储
			for (Datastore datasotre : GetVirtualMachineResponseT.getVitualMachine().getDatastores()) {
				if (null == datastoreT) {
					datastoreT = datasotre;
				} else {
					try {
						if (datasotre.getSummary().accessible == true
								&& "VMFS".equalsIgnoreCase(datasotre.getSummary().getType())) {
							if (datasotre.getInfo().getFreeSpace() > datastoreT.getInfo().getFreeSpace()) {
								datastoreT = datasotre;
							}
						}
					} catch (Exception e) {
						logger.warn("存在未知datastore:" + datasotre.getName());
					}
				}
			}
			// 剩余存储默认使用的单位是B
			double freeStore = Double.parseDouble(Long.toString(datastoreT.getInfo().getFreeSpace())) / 1024 / 1024 / 1024;
			if (MIN_STORE_FREE > freeStore) {
				String error = "存储容量不足，请联系管理员！";
				logger.error(error);
				errorMsg.put("FAILURE", error);
				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", error);
				return errorMsg;
			}
			try {
				// 设置存储器
				virtualMachineRelocateSpec.setDatastore(datastoreT.getMOR());// 设置克隆,文件存放地点
			} catch (Exception e) {
				String error = "在指定集群" + clusterConfig.getClusterName() + "中，未发现指定存储";
				logger.error(error);
				errorMsg.put("FAILURE", error);
				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", error);
				return errorMsg;
			}

			// 查询集群里合适的物理机
			HostSystem hostSystemT = null;
			for (HostSystem hostSystem : ClusterComputeResourceT.getHosts()) {
				if (null == hostSystemT) {
					for (Datastore store : hostSystem.getDatastores()) {
						if (datastoreT.getMOR().getVal().equals(store.getMOR().getVal())) {
							hostSystemT = hostSystem;
						}
					}
				} else {
					try {
						for (Datastore store : hostSystem.getDatastores()) {
							if (datastoreT.getMOR().getVal().equals(store.getMOR().getVal())) {
								if (hostSystem.getVms().length < hostSystemT.getVms().length) {
									hostSystemT = hostSystem;
								}
							}
						}
					} catch (Exception e) {
						logger.warn("存在未知host:" + hostSystem.getName());
					}
				}
			}
			try {
				// 设置物理机
				virtualMachineRelocateSpec.setHost(hostSystemT.getMOR());// 必须设置,否则无法克隆成功
			} catch (Exception e) {
				logger.error("在指定集群" + clusterConfig.getClusterName() + "中，未发现物理机");
				errorMsg.put("FAILURE", "在指定集群" + clusterConfig.getClusterName() + "中，未发现物理机");

				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", "在指定集群" + clusterConfig.getClusterName() + "中，未发现物理机");
				return errorMsg;
			}
			// 配置新克隆虚拟机配置信息
			VirtualMachineConfigSpec virtualMachineConfigSpec = new VirtualMachineConfigSpec();
			// 设置虚拟机的memory
			virtualMachineConfigSpec.setMemoryMB(ass.getRamSize().longValue());
			// 设置虚拟机的cpu
			virtualMachineConfigSpec.setNumCPUs(ass.getCpuSize().intValue());
			virtualMachineCloneSpec.setConfig(virtualMachineConfigSpec);

			// 配置新克隆虚拟机状态信息
			virtualMachineCloneSpec.setPowerOn(true);
			virtualMachineCloneSpec.setTemplate(false);
			cloneRequest.setVirtualMachineCloneSpec(virtualMachineCloneSpec);

			// 用户自定义IP
			Long ipConfigId = getIpConfigId(vlanId, ipAddress, null);
			TccIpConfigInfo ipinfo = (TccIpConfigInfo) this.commonDao.get(
					TccIpConfigInfo.class, ipConfigId);
			if (template == null || GetVirtualMachineResponseT.getVitualMachine() == null) {
				String error = "此镜像下没有可用的模版!";
				logger.error(error);
				errorMsg.put("FAILURE", error);
				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", error);
				return errorMsg;
			} else {
				if (ipinfo != null && StringUtils.isNotBlank(ipinfo.getMemo())) {
					
					
//					TccVlanNetwork network = (TccVlanNetwork) commonDao.get(TccVlanNetwork.class, ipinfo.getNetworkId());
//					String[] networkTags = network.getNetworkTag().split("_&");
//					String networkTag = networkTags[1];
//					
//					//请在虚拟机中不要配置网卡,添加网卡
//					
//					//hostSystemT.getHostVirtualNicManager().queryNetConfig("E1000");
//					String nicType = "E1000";
//					
//					 VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
//					 nicSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
//					 VirtualEthernetCard nic = null;
//					    if(nicType.equalsIgnoreCase("E1000")) {
//					        nic =  new VirtualE1000();
//					    }
//					    else if (nicType.equalsIgnoreCase("E1000e")) {
//					        nic =  new VirtualE1000e();
//					    }
//					    else if (nicType.equalsIgnoreCase("vmxnet")) {
//					        nic =  new VirtualVmxnet();
//					    }
//					    else if (nicType.equalsIgnoreCase("pcnet32") || nicType.equalsIgnoreCase("vlance")) {
//					        nic =  new VirtualPCNet32();
//					    }
//
//					    VirtualEthernetCardNetworkBackingInfo nicBacking =
//					    new VirtualEthernetCardNetworkBackingInfo();
//					    nicBacking.setDeviceName(networkTag);
//
//					    VirtualDeviceConnectInfo connectInfo = new VirtualDeviceConnectInfo();
//					    connectInfo.setConnected(true);
//					    connectInfo.setStartConnected(true);
//					    nic.setConnectable(connectInfo);
//
//					    Description info = new Description();
//					    info.setLabel(networkTag);
//					    info.setSummary(networkTag);
//					    nic.setDeviceInfo(info);
//
//					    // allowable types: "generated", "manual", "assigned"
//					    nic.setAddressType("assigned");
//					    nic.setBacking(nicBacking);
//					    //according to vsphere api, keys should be unique, but this does not appear to be enforced
//					    nic.setKey(0);
//
//					    nicSpec.setDevice(nic);

					String[] cidr = ipinfo.getMemo().split(",");
					CustomizationSpec customSpec = new CustomizationSpec();
					if(template.getDbosVersionCd().contains("window")||template.getMemo().contains("window"))//判断需要安装window时使用，安装linux时不使用
					{
//						CustomizationWinOptions winOptions = new CustomizationWinOptions();					
//						winOptions.setChangeSID(true);
//						winOptions.setDeleteAccounts(false);
//						customSpec.setOptions(winOptions);					
//						
//						CustomizationSysprep winPrep = new CustomizationSysprep();							
//						CustomizationGuiUnattended unattended=new CustomizationGuiUnattended();
//				        unattended.setTimeZone(4);
//				        unattended.setAutoLogon(false);
//				        unattended.setAutoLogonCount(0);
//				        winPrep.setGuiUnattended(unattended);
//
//				        CustomizationUserData userData=new CustomizationUserData();
//				        userData.setFullName("cloudrabbit.cn");
//				        userData.setOrgName("cloudrabbit.cn");
//				        CustomizationVirtualMachineName virtualMachineName=new CustomizationVirtualMachineName();
//				        userData.setComputerName(virtualMachineName);
//				        userData.setProductId("HWRFF-2FFYX-XFXP2-DYFC3-BX3B7");
//				        winPrep.setUserData(userData);
//
//				        CustomizationIdentification identification=new CustomizationIdentification();
//				        identification.setJoinWorkgroup("WORKGROUP");
//				        winPrep.setIdentification(identification);	
//				        
//						customSpec.setIdentity(winPrep);
						
						CustomizationWinOptions winOptions = new CustomizationWinOptions();					
						winOptions.setChangeSID(true);
						winOptions.setDeleteAccounts(false);
						customSpec.setOptions(winOptions);					
						
						CustomizationSysprep winPrep = new CustomizationSysprep();							
						CustomizationGuiUnattended unattended=new CustomizationGuiUnattended();
				        unattended.setTimeZone(4);
				        unattended.setAutoLogon(false);
				        unattended.setAutoLogonCount(0);
				        
				        CustomizationPassword customizationpassword = new CustomizationPassword();
				        customizationpassword.setPlainText(true);
				        customizationpassword.setValue("Cloud@123456");
				        unattended.setPassword(customizationpassword);
				        
				        winPrep.setGuiUnattended(unattended);

				        CustomizationUserData userData=new CustomizationUserData();
				        userData.setFullName("micro");
				        userData.setOrgName("micro");
				        CustomizationVirtualMachineName virtualMachineName=new CustomizationVirtualMachineName();
				        userData.setComputerName(virtualMachineName);
				        userData.setProductId("");
				        winPrep.setUserData(userData);

				        CustomizationIdentification identification=new CustomizationIdentification();
				        identification.setJoinWorkgroup("WORKGROUP");
				        winPrep.setIdentification(identification);	
				        
						customSpec.setIdentity(winPrep);
					}else{
						CustomizationLinuxOptions linuxOptions = new CustomizationLinuxOptions();
						customSpec.setOptions(linuxOptions);
						CustomizationLinuxPrep linuxPrep = new CustomizationLinuxPrep();
						linuxPrep.setDomain("default");
						CustomizationFixedName fixedName = new CustomizationFixedName();
						fixedName.setName(vmName);// hostname
						linuxPrep.setHostName(fixedName);					
						customSpec.setIdentity(linuxPrep);
					}
					CustomizationFixedIp fixedIp = new CustomizationFixedIp();
					fixedIp.setIpAddress(ipinfo.getIpAddress());// ipaddress
					CustomizationIPSettings adapter = new CustomizationIPSettings();
					adapter.setIp(fixedIp);
					if (cidr.length >= 3 && StringUtils.isNotBlank(cidr[1])) {
						if (StringUtil.checkNetworkInfoFormat(cidr[1])) {
							adapter.setGateway(new String[] { cidr[1] });
						}
					}
					if (cidr.length >= 3 && StringUtils.isNotBlank(cidr[0])) {
						if (StringUtil.checkNetworkInfoFormat(cidr[0])) {
							adapter.setSubnetMask(cidr[0]);
						}
					}
					CustomizationAdapterMapping adapterMap = new CustomizationAdapterMapping();
					adapterMap.setAdapter(adapter);
					CustomizationAdapterMapping[] nicSettingMap = new CustomizationAdapterMapping[] { adapterMap };
					customSpec.setNicSettingMap(nicSettingMap);
					CustomizationGlobalIPSettings gIP = new CustomizationGlobalIPSettings();
					if (cidr.length >= 3 && StringUtils.isNotBlank(cidr[2])) {
						if (StringUtil.checkNetworkInfoFormat(cidr[2])) {
							gIP.setDnsServerList(new String[] { cidr[2] });
						} else {
							gIP.setDnsServerList(new String[] { "114.114.114.114" });
						}
					}
//					VirtualDeviceConfigSpec[] specs = new VirtualDeviceConfigSpec[] {nicSpec};
//					virtualMachineConfigSpec.setDeviceChange(specs);
					customSpec.setGlobalIPSettings(gIP);
					virtualMachineCloneSpec.setCustomization(customSpec);
				}
			}

			// 由模板来查找存储
			for (Datastore datasotre : GetVirtualMachineResponseT
					.getVitualMachine().getDatastores()) {
				if (null == datastoreT) {
					datastoreT = datasotre;
				} else {
					try {
						if (datasotre.getSummary().accessible == true 
								&& "VMFS".equalsIgnoreCase(datasotre.getSummary().getType())) {
							if (datasotre.getInfo().getFreeSpace() > datastoreT.getInfo().getFreeSpace()) {
								datastoreT = datasotre;
							}
						}
					} catch (Exception e) {
						logger.warn("存在未知datastore:" + datasotre.getName());
					}
				}
			}

			try {
				// 设置存储器
				virtualMachineRelocateSpec.setDatastore(datastoreT.getMOR());// 设置克隆,文件存放地点
			} catch (Exception e) {
				String error = "在指定集群" + clusterConfig.getClusterName() + "中，未发现指定存储";
				logger.error(error);
				errorMsg.put("FAILURE", error);
				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", error);
				return errorMsg;
			}

			virtualMachineCloneSpec.setLocation(virtualMachineRelocateSpec);

			// 查询数据中心
			ListDatacentersRequest ListDatacentersRequestT = new ListDatacentersRequest();
			ListDatacentersRequestT.setCloud(tcccloudplatform.getCloudplatformId() + "");
			ListDatacentersRequestT.setName(clusterConfig.getDcname());
			ListDatacentersRequestT = (ListDatacentersRequest) TccCloudPlatUitls
					.setRequestParam(ListDatacentersRequestT);
			ListDatacentersResponse ListDatacentersResponseT = (ListDatacentersResponse) TccCloudPlatUitls
					.getVmwareAdapte().execute(ListDatacentersRequestT);

			cloneRequest.setDatacenter(ListDatacentersResponseT
					.getListDatacentersResponse().getDatacenter().get(0));
			cloneRequest.setVirtualMachine(GetVirtualMachineResponseT.getVitualMachine());
			cloneRequest = (CloneVitualMachineRequest) TccCloudPlatUitls.setRequestParam(cloneRequest);
			CloneVitualMachineResponse cloneVmResponse = (CloneVitualMachineResponse) TccCloudPlatUitls
					.getVmwareAdapte().execute(cloneRequest);
			Boolean isOK = cloneVmResponse.isResult();
			if (isOK) {
				//修改虚拟机网络标签
				TccVlanNetwork network = (TccVlanNetwork) commonDao.get(TccVlanNetwork.class, ipinfo.getNetworkId());
				if(network != null){
					String[] networkTags =network.getNetworkTag().split("_&");
					String networkTag = networkTags[1];
					logger.info("虚拟机:" + vmName + "正在重新配置网络标签"+"["+networkTag+"]");
					VirtualMachineUpdateNetworkRequest updateVmNetworkRequest = new VirtualMachineUpdateNetworkRequest();
					updateVmNetworkRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
					updateVmNetworkRequest.setName(vmName);
					updateVmNetworkRequest.setAdaptername("Network adapter");
					updateVmNetworkRequest.setNetname(networkTag);
					updateVmNetworkRequest = (VirtualMachineUpdateNetworkRequest)TccCloudPlatUitls.setRequestParam(updateVmNetworkRequest);
					VirtualMachineUpdateNetworkResponse updateVmNetworkResponse = (VirtualMachineUpdateNetworkResponse)TccCloudPlatUitls.getVmwareAdapte().execute(updateVmNetworkRequest);
					if(updateVmNetworkResponse!=null && ResponseParameter.SUCCESS.equals(updateVmNetworkResponse.getSuccess())){
						logger.info("虚拟机:" + vmName + "重新配置成功");
					}else{
						logger.info("虚拟机:" + vmName + "重新配置网络标签失败，请至后台手动修改");
					}
				}
				
				
				GetVirtualMachineRequest getVmRequest = new GetVirtualMachineRequest();
				getVmRequest.setCloud(this.findPlatformByClusterId(
						clusterConfig.getId() + "").getCloudplatformId() + "");
				getVmRequest.setVmName(vmName);
				getVmRequest = (GetVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(getVmRequest);
				GetVirtualMachineResponse getVmResponse = (GetVirtualMachineResponse) TccCloudPlatUitls
						.getVmwareAdapte().execute(getVmRequest);

				// 设置克隆操作系统
				String vmGutestId = getVmResponse.getVitualMachine().getConfig().getGuestId();
				if (vmGutestId.contains("win") || vmGutestId.contains("windows")) {
					vmGutestId = "windows";
				} else {
					vmGutestId = "linux";
				}
				ass.setOsType(vmGutestId);

				errorMsg.put("SUCCESS", "true");
				errorMsg.put("msg", "克隆虚拟机成功[" + vmName + "]");
				String volumeOfferingId = "";// 外挂卷轴UUID7
				// 如果是SAN存储，则自动挂载外挂存储（卷轴），否则走线下由实施人员手动安装外挂存储

				try {
					if (ass.getSanStorageSize() != null && !ass.getSanStorageSize().equals("0")
							&& Long.valueOf(ass.getSanStorageSize()).intValue() > 0) {
						// 把外挂存储的大小找到磁盘方案，生成卷轴挂载到虚拟机上
						TccSysStoreCase ssc = new TccSysStoreCase();
						ssc.setStoreCaseName(ass.getTccSetMealConfig().getTccPluginStoreCase().getPluginStoreCaseName());
						ssc.setStoreSize(Long.valueOf(ass.getSanStorageSize()));
						ssc.setMomo(ass.getTccSetMealConfig().getTccPluginStoreCase().getMomo());
						AddVirtualMachineDiskRequest addDiskRequest = new AddVirtualMachineDiskRequest();
						addDiskRequest.setCloud(this.findPlatformByClusterId(
								clusterConfig.getId() + "").getCloudplatformId() + "");
						addDiskRequest.setName(vmName);
						addDiskRequest.setDiskSize(String.valueOf(Integer.valueOf(ass.getSanStorageSize())));
						addDiskRequest = (AddVirtualMachineDiskRequest) TccCloudPlatUitls.setRequestParam(addDiskRequest);
						AddVirtualMachineDiskReponse addVirtualMachineDiskReponse = (AddVirtualMachineDiskReponse) 
								TccCloudPlatUitls.getVmwareAdapte().execute(addDiskRequest);
						if (addVirtualMachineDiskReponse != null
								&& "true".equalsIgnoreCase(addVirtualMachineDiskReponse.getSuccess())) {
							this.logger.info("VirtualMachine [" + vmName + "] attach disk successful! Disk size is "
									+ String.valueOf(Integer.valueOf(ass.getSanStorageSize())));
						} else {
							this.logger.error("VirtualMachine [" + vmName + "] attach disk failed! Disk size is "
									+ String.valueOf(Integer.valueOf(ass.getSanStorageSize())));
						}
					}
				} catch (Exception e) {
					logger.error("VirtualMachine [" + vmName + "] attach disk failed!", e);
				}
				
				// clone虚拟机创建成功，添加外挂存储
				try {
					List<TccStorageApplyCaseAss> storageList=getStorageApplyCaseByAssid(ass.getAssId());
					
					for (TccStorageApplyCaseAss storageOB : storageList) {
						if (storageOB.getStorageSize()>0) {
							AddVirtualMachineDiskRequest addDiskRequest = new AddVirtualMachineDiskRequest();
							addDiskRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
							addDiskRequest.setDiskSize(storageOB.getStorageSize().longValue()+"");
							addDiskRequest.setName(vmName);
							addDiskRequest = (AddVirtualMachineDiskRequest) TccCloudPlatUitls.setRequestParam(addDiskRequest);
							AddVirtualMachineDiskReponse addVirtualMachineDiskReponse = (AddVirtualMachineDiskReponse) 
									TccCloudPlatUitls.getVmwareAdapte().execute(addDiskRequest);
							if(addVirtualMachineDiskReponse != null && "true".equalsIgnoreCase(addVirtualMachineDiskReponse.getSuccess())){
								this.logger.info("VirtualMachine [" + vmName + "] attach disk successful! Disk size is " 
										+ storageOB.getStorageSize().longValue());
							}else{
								this.logger.error("VirtualMachine [" + vmName + "] attach disk failed! Disk size is " 
										+ storageOB.getStorageSize().longValue());
							}
						}
						
					}
				} catch (Exception e) {
					logger.error("VirtualMachine [" + vmName + "] attach disk failed!", e);
				}

				// 保存服务请求主表与已分配资源代码表
				TccSrt srt = vmAssistService.getSrt(ass.getTccSrt().getSrtId());
				TccApplyedHostResource applyedHostResource = vmAssistService.mergeApplyedHostResource(getVmResponse
								.getVitualMachine(), ass, "", volumeOfferingId, "", clusterConfig.getId().toString());
				applyedHostResource.setTemplateId(host.getTemplateId());
				vmAssistService.saveSrtApplyedhostAss(applyedHostResource, srt);
				applyedHostResource.setCloseDttm(srt.getSrtCloseDttm());
				ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTSUCC);
				configAssApplyCaseService.updateConfigAssApplyCase(ass);
				applyedHostResource.setIpAddress(ipAddress);
				if(ipConfigId!=null && ipConfigId!=-1 ){
					applyedHostResource.setTccIpConfigInfo(ipinfo);
				}
				// 保存IP地址到虚拟机表里面
				vmAssistService.saveApplyedHostResource(applyedHostResource);
				// 更新ip的状态
				StringBuffer updateSql = new StringBuffer();
				updateSql.append("UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='克隆" + vmName
								+ "虚拟机',DISTRIBUTE_TIME=NOW() WHERE f.IP_ADDRESS ='" + ipAddress + "'");
				// 更新ip的状态
				commonDao.updateByNativeSql(updateSql.toString());
				// 剩余CPU、MEM减少
				capacityMgmtService.takingCpuRam(applyedHostResource.getTccPhysiscResourceInfo(),
						ass.getCpuSize(), ass.getRamSize());
				// 保存项目与已申请资源表关系
				TccProjectInfo projectInfo = tccProjectInfo;
				vmAssistService.saveProjectAss(applyedHostResource, projectInfo);
				String displayName = vmName;
				TccApplyedHostResource ahr = vmAssistService
						.getApplyedHostResource(applyedHostResource.getApplyResourceId());
				ahr.setHostNane(displayName);
				
				
				if(vmGutestId.contains("win") || vmGutestId.contains("windows")){
					ahr.setHostLoginUsername("Administrator");
				}else{
					if(StringUtils.isNotBlank(templateCase.getUsername())){
						ahr.setHostLoginUsername(templateCase.getUsername());
					}
				}
				
				if(vmGutestId.contains("win") || vmGutestId.contains("windows")){
					ahr.setHostLoginPassword("Cloud@123456");
					ahr.setOriginalRootPassword("Cloud@123456");
				}else{
					if(StringUtils.isNotBlank(templateCase.getPasswd())){
						ahr.setHostLoginPassword(templateCase.getPasswd());
						ahr.setOriginalRootPassword(templateCase.getPasswd());
						
					}
				}
				
				ahr.setHardwareTypeCd("186");// 设置为小型机
				vmAssistService.updateApplyedHostResource(ahr);

				try {
					if (!zabbixApiService.isAddHostToMonitorByHostName(ahr.getApplyResourceId().toString())) {
						HostMonitorBean hostinfo = new HostMonitorBean();
						hostinfo.setHostName(ahr.getApplyResourceId().toString());
						hostinfo.setVisibleName(displayName);
						hostinfo.setIp(applyedHostResource.getIpAddress());
						hostinfo.setOsName(vmGutestId);
						applyedHostResource.setMonitorFlg(zabbixApiService.addHostToMonitor(hostinfo) ? "1" : "0");
					} else {
						logger.info("主机[" + host + "]已在zabbix中注册");
						applyedHostResource.setMonitorFlg("1");
					}
				} catch (Exception ex) {
					this.logger.error("添加监控失败！", ex);
				} catch (Throwable t) {
					this.logger.error("添加监控失败！", t);
				}
				// 如果创建虚拟机成功，虚拟机信息插入到snapshotcategory表里面
				TccSnapshotCfg snapshotCfg = new TccSnapshotCfg();
				snapshotCfg.setCrtDttm(new Date());
				Set<TccApplyedHostinfo> tccApplyedHostinfos = ass.getTccApplyedHostinfos();
				for (TccApplyedHostinfo temp : tccApplyedHostinfos) {
					snapshotCfg.setCrtUserId(temp.getCrtUserId() + "");
					break;
				}
				snapshotCfg.setCrtUserId(String.valueOf(srt.getCrtUserId()));
				snapshotCfg.setShotMaxNum(0L);
				snapshotCfg.setHostId(ahr.getApplyResourceId());
				snapshotCfg.setVmName(displayName);
				snapshotCfg.setEnableFlg("1");
				snapshotCategoryService.AddSnapshotCategory(snapshotCfg);
				LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
				
				this.saveResOperLog("创建", StringUtils.substringAfterLast(
		    			TccApplyedHostinfo.class.toString(), "."), 
						applyedHostResource.getApplyResourceId(), "实施者 " 
		    			+ loginUserInfo.getEmpName()+ " 创建vcenter虚拟机成功");
				
				errorMsg.put("SUCCESS", "true");
			} else {// 失败
				LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
				logService.saveOperLog("失败", "实施者" + loginUserInfo.getEmpName() + "创建vcenter虚拟机"
						+ BusinessEnvironment.OPER_RESULT_FAILURE, ass, 2);
				ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTFAIL);// 失败状态
				configAssApplyCaseService.updateConfigAssApplyCase(ass);
				logger.error(errorMsg);
				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", "克隆虚拟机失败[" + vmName + "]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
			ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTFAIL);// 失败状态
			logService.saveOperLog("失败", "实施者" + loginUserInfo.getEmpName()
					+ "创建vcenter虚拟机" + BusinessEnvironment.OPER_RESULT_FAILURE, ass, 2);
			configAssApplyCaseService.updateConfigAssApplyCase(ass);
			logger.error(e);
			errorMsg.put("SUCCESS", "false");
			errorMsg.put("msg", "克隆虚拟机失败[" + vmName + "]" + e.getMessage());
		}
		return errorMsg;
	}

	@Override
	public boolean destroyVC(Long applyResourceId) throws Exception, Throwable {
		TccApplyedHostResource applyedHostResource = vmAssistService
				.getApplyedHostResource(applyResourceId);
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		String loginUserName = "【资源到期自动回收器】";
		if(loginUserInfo != null){
			loginUserName = loginUserInfo.getEmpName();
		}
		GetVirtualMachineRequest getVmRequest = new GetVirtualMachineRequest();
		TccCloudplatform tcccloudplatform = this.findPlatformByVmId(applyResourceId + "");
		getVmRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
		getVmRequest.setVmName(applyedHostResource.getHostNane());
		getVmRequest = (GetVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(getVmRequest);
		GetVirtualMachineResponse getVmResponse = (GetVirtualMachineResponse) TccCloudPlatUitls
				.getVmwareAdapte().execute(getVmRequest);
		try {
			com.vmware.vim25.mo.VirtualMachine vm = getVmResponse.getVitualMachine();
			if(vm == null){
				// 虚拟机不存在则认为销毁成功
				logger.info("虚机:" + getVmRequest.getVmName() + "不存在");
				return true;
			}
			VirtualMachinePowerState vmPowerState = vm.getRuntime().getPowerState();
			// 查询当前vCenter的虚拟机是否为开机状态,若是则把它关掉,再销毁.若关机失败,直接返回销毁失败.
			if (vmPowerState == VirtualMachinePowerState.poweredOn) {
				boolean flag = stopVC(applyResourceId);
				if (!flag)
					return false;
			}
		} catch (Exception e) {
			logger.error("查询虚机:" + getVmRequest.getVmName() + "出错", e);
			return false;
		}
		com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request
				.DestroyVirtualMachineRequest destroyVmRequest = new com.sjc.adaptee
				.cloud.vmware.version51.module.virtualmachine.request.DestroyVirtualMachineRequest();
		
		destroyVmRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
		destroyVmRequest.setName(applyedHostResource.getHostNane());
		destroyVmRequest = (com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine
				.request.DestroyVirtualMachineRequest) TccCloudPlatUitls
				.setRequestParam(destroyVmRequest);
		com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response
				.DestroyVirtualMachineResponse destroyVmResponse = (com.sjc.adaptee.cloud.vmware
				.version51.module.virtualmachine.response.DestroyVirtualMachineResponse) TccCloudPlatUitls
				.getVmwareAdapte().execute(destroyVmRequest);
		boolean isOK = false;
		if (destroyVmResponse.isResult()) {
			isOK = true;
			boolean falg = true;
			if (!falg) {
				logger.warn("释放IP地址失败！");
			}
			logger.info("VC销毁虚拟机成功");
			// 使监控记录无效
			// 更改状态，使此虚拟机记录无效
			applyedHostResource.setEnableFlg(BusinessEnvironment.DISABLE_FLG);
			applyedHostResource.setRunStatusCd(BusinessEnvironment.VM_RUN_STATUS_DESTROYED);// 已销毁
			/** delete host from zabbix by Destiny * */
			try {
				boolean issuccess = zabbixApiService.deleteHost(applyedHostResource.getIpAddress());
				if (!issuccess) {
					logService.saveOperLog(BusinessEnvironment.OPER_RESULT_FAILURE,
							loginUserName + "删除监控虚拟机失败", applyedHostResource, 2);
					this.logger.error(applyedHostResource.getIpAddress() + "删除监控虚拟机是失败！");
				} else {
					// 删除监控成功
					applyedHostResource.setMonitorFlg(BusinessEnvironment.DISABLE_FLG);
				}
			} catch (Exception ex) {
				logService.saveOperLog(BusinessEnvironment.OPER_RESULT_FAILURE,
						loginUserName + "删除监控虚拟机失败", applyedHostResource, 2);
				this.logger.error(applyedHostResource.getIpAddress() + "删除监控虚拟机是失败！", ex);
			} catch (Throwable t) {
				logService.saveOperLog(BusinessEnvironment.OPER_RESULT_FAILURE,
						loginUserName + "删除监控虚拟机失败", applyedHostResource, 2);
				this.logger.error(applyedHostResource.getIpAddress() + "删除监控虚拟机是失败！", t);
			}

			/** end delete host from zabbix * */
			vmAssistService.updateApplyedHostResource(applyedHostResource);
			try {
				this.saveResOperLog("删除", StringUtils.substringAfterLast(
						TccPhysiscResourceInfo.class.toString(), "."), applyedHostResource.getApplyResourceId(), "销毁成功");
			} catch (NullPointerException e) {
			}
			
		} else {
			isOK = false;
			logger.info("VC销毁虚拟机失败");
			logService.saveOperLog("失败", "实施者" + loginUserName + "销毁vcenter" 
					+ applyedHostResource.getHostNane() + BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
		}
		return isOK;
	}

	@Override
	public boolean rebootCS(Long applyResourceId) throws Exception, Throwable {
		TccApplyedHostResource applyedHostResource = vmAssistService
				.getApplyedHostResource(applyResourceId);
		RebootVirtualMachineRequest rebootRequest = new RebootVirtualMachineRequest();
		TccCloudplatform tcccloudplatform = this.findPlatformByVmId(applyResourceId + "");
		rebootRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
		rebootRequest.setId(applyedHostResource.getUuid());
		rebootRequest = (RebootVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(rebootRequest);
		RebootVirtualMachineResponse rebootResponse = (RebootVirtualMachineResponse) TccCloudPlatUitls
				.getAdapte().execute(rebootRequest);
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		logger.info("正在重启虚拟机");
		String jobStatus = asynQueryVmStatus(rebootResponse.getRebootVirtualMachineResponse().getJobId(), this
				.findPlatformByVmId(applyResourceId.toString()).getCloudplatformId().toString());
		if (SUCCESS.equals(jobStatus)) { // 成功
			LoginUserInfo loginUserInfos = LoginUserInfoHolder.getInstance().getCurrentUser();
			logService.saveOperLog("成功",  loginUserInfos.getEmpName()
					+ "重启cloudstack虚拟机" + applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"
					+ BusinessEnvironment.OPER_RESULT_SUCCESS, applyedHostResource, 2);

			logger.info("重启虚拟机成功");
			return true;
		} else { // 失败
			logService.saveOperLog("失败",  loginUserInfo.getEmpName()
					+ "重启cloudstack虚拟机" + applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"
					+ BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
			logger.info("重启虚拟机失败");
			return false;
		}
	}

	@Override
	public boolean rebootVC(Long applyResourceId) throws Exception, Throwable {
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		TccApplyedHostResource applyedHostResource = vmAssistService.getApplyedHostResource(applyResourceId);
		com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request.
			RebootVirtualMachineRequest rebootRequest = new com.sjc.adaptee.cloud.
			vmware.version51.module.virtualmachine.request.RebootVirtualMachineRequest();
		
		TccCloudplatform tcccloudplatform = this.findPlatformByVmId(applyResourceId + "");
		rebootRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
		rebootRequest.setName(applyedHostResource.getHostNane());
		rebootRequest = (com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request
				.RebootVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(rebootRequest);
		
		com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response
			.RebootVirtualMachineResponse rebootResponse = (com.sjc.adaptee.cloud.vmware
			.version51.module.virtualmachine.response.RebootVirtualMachineResponse) 
			TccCloudPlatUitls.getVmwareAdapte().execute(rebootRequest);
		
		/*logService.saveOperLog(BusinessEnvironment.OPER_RESULT_IN_PROCESS, loginUserInfo.getEmpName()
						+ BusinessEnvironment.OPER_TYPE_REBOOT_VM + applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")", applyedHostResource, 2);*/
		logger.info("正在重启虚拟机");
		boolean isOK = rebootResponse.isResult();
		// 系统重启10秒钟 以后争取让系统完全启动成功
		Thread.sleep(10 * 1000);
		if (isOK) {// 成功
			logService.saveOperLog("成功", loginUserInfo.getEmpName()
					+ "重启vcenter虚拟机" + applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"
					+ BusinessEnvironment.OPER_RESULT_SUCCESS, applyedHostResource, 2);

			logger.info("重启虚拟机成功");
			return true;
		} else {
			logService.saveOperLog("失败",loginUserInfo.getEmpName()
					+ "重启vcenter虚拟机" + applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"
					+ BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
			logger.info("重启虚拟机失败");
			return false;
		}
	}

	/**
	 * 异步查询虚拟机状态
	 * 
	 * @param jobid
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String asynQueryVmStatus(String jobid, String platformId)throws InterruptedException, ExecutionException {
		VmCallable task = new VmCallable(jobid, platformId);
		ExecutorService es = Executors.newFixedThreadPool(1);
		Future<String> future = es.submit(task);
		String jobStatus = future.get();
		es.shutdownNow();
		return jobStatus;
	}

	public boolean startCS(Long applyResourceId) throws Exception, Throwable {
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		TccApplyedHostResource applyedHostResource = vmAssistService.getApplyedHostResource(applyResourceId);
		StartVirtualMachineRequest startVmRequest = new StartVirtualMachineRequest();
		TccCloudplatform tcccloudplatform = this.findPlatformByVmId(applyResourceId + "");
		startVmRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
		startVmRequest.setId(applyedHostResource.getUuid());
		startVmRequest.setHostId(applyedHostResource.getTccPhysiscResourceInfo().getUuid());
		startVmRequest = (StartVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(startVmRequest);
		StartVirtualMachineResponse startVmResponse = (StartVirtualMachineResponse) TccCloudPlatUitls
				.getAdapte().execute(startVmRequest);
		String jobStatus = asynQueryVmStatus(startVmResponse.getStartVirtualMachineResponse().getJobId(), 
				this.findPlatformByVmId(applyResourceId.toString()).getCloudplatformId().toString());
		if (SUCCESS.equals(jobStatus)) {// 成功
			logService.saveOperLog("成功", loginUserInfo.getEmpName()
					+ BusinessEnvironment.OPER_TYPE_START_VM, applyedHostResource, 2);
			applyedHostResource.setRunStatusCd(BusinessEnvironment.VM_RUN_STATUS_RUNNING);
			vmAssistService.updateApplyedHostResource(applyedHostResource);
			logger.info("启动虚拟机状态成功");
			return true;
		} else {// 失败
			logService.saveOperLog("失败",  loginUserInfo.getEmpName()
					+ "启动cloudstack虚拟机" + applyedHostResource.getHostNane()
					+ BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
			logger.info("启动虚拟机状态失败");
			return false;
		}
	}

	public boolean startVC(Long applyResourceId) throws Exception, Throwable {
		TccApplyedHostResource applyedHostResource = vmAssistService
				.getApplyedHostResource(applyResourceId);
		com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request
			.StartVirtualMachineRequest startVmRequest = new com.sjc.adaptee
			.cloud.vmware.version51.module.virtualmachine.request.StartVirtualMachineRequest();
		
		TccCloudplatform tcccloudplatform = this.findPlatformByVmId(applyResourceId + "");
		startVmRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
		startVmRequest.setName(applyedHostResource.getHostNane());
		startVmRequest = (com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request
				.StartVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(startVmRequest);
		
		com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response
			.StartVirtualMachineResponse startVmResponse = (com.sjc.adaptee.cloud
			.vmware.version51.module.virtualmachine.response.StartVirtualMachineResponse) 
			TccCloudPlatUitls.getVmwareAdapte().execute(startVmRequest);
		
		Boolean isOK = startVmResponse.isResult();
		// 让系统启动10秒 争取让系统完全正常启动
		Thread.sleep(10 * 1000);
		if (isOK) {
			logger.info("VC启动虚拟机成功");
			applyedHostResource.setRunStatusCd(BusinessEnvironment.VM_RUN_STATUS_RUNNING);
			vmAssistService.updateApplyedHostResource(applyedHostResource);
			LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
			logService.saveOperLog("成功",  loginUserInfo.getEmpName()
					+ "启动vcenter虚拟机" + applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"
					+ BusinessEnvironment.OPER_RESULT_SUCCESS, applyedHostResource, 2);
			return true;
		} else {
			logger.info("VC启动虚拟机失败");
			LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance()
					.getCurrentUser();
			logService.saveOperLog("失败",  loginUserInfo.getEmpName()+"("+applyedHostResource.getIpAddress()+")"
					+ "启动vcenter虚拟机" + applyedHostResource.getHostNane()
					+ BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
			return false;
		}
	}

	public boolean stopCS(Long applyResourceId) throws Exception, Throwable {
		TccApplyedHostResource applyedHostResource = vmAssistService
				.getApplyedHostResource(applyResourceId);
		StopVirtualMachineRequest stopVmRequest = new StopVirtualMachineRequest();
		TccCloudplatform tcccloudplatform = this.findPlatformByVmId(applyResourceId + "");
		stopVmRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
		stopVmRequest.setId(applyedHostResource.getUuid());
		stopVmRequest = (StopVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(stopVmRequest);
		StopVirtualMachineResponse stopVmResponse = (StopVirtualMachineResponse) TccCloudPlatUitls
				.getAdapte().execute(stopVmRequest);
		stopVmResponse.getStopVirtualMachineResponse();
		String jobStatus = asynQueryVmStatus(stopVmResponse.getStopVirtualMachineResponse().getJobId(), 
				this.findPlatformByVmId(applyResourceId.toString()).getCloudplatformId().toString());
		if (SUCCESS.equals(jobStatus)) {// 成功
			applyedHostResource.setRunStatusCd(BusinessEnvironment.VM_RUN_STATUS_STOP);
			vmAssistService.updateApplyedHostResource(applyedHostResource);
			LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
			logService.saveOperLog("成功",  loginUserInfo.getEmpName()
					+ "停止cloudstack虚拟机" + applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"
					+ BusinessEnvironment.OPER_RESULT_SUCCESS, applyedHostResource, 2);
			logger.info("定时调用：停止虚拟机，状态成功");
			return true;
		} else { // 失败
			LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
			logService.saveOperLog(BusinessEnvironment.OPER_TYPE_STOP_VM, loginUserInfo.getEmpName() + "停止cloudstack虚拟机"
					+ applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"
					+ BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
			logService.saveOperLog("失败",loginUserInfo.getEmpName()
					+ "停止cloudstack虚拟机" + applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"
					+ BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
			logger.info("定时调用：停止虚拟机，状态失败");
			return false;
		}
	}

	@Override
	public boolean stopVC(Long applyResourceId) throws Exception, Throwable {
		TccApplyedHostResource applyedHostResource = vmAssistService.getApplyedHostResource(applyResourceId);
		com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request
			.StopVirtualMachineRequest stopVmRequest = new com.sjc.adaptee.cloud
			.vmware.version51.module.virtualmachine.request.StopVirtualMachineRequest();
		
		TccCloudplatform tcccloudplatform = this.findPlatformByVmId(applyResourceId + "");
		stopVmRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
		stopVmRequest.setName(applyedHostResource.getHostNane());
		stopVmRequest = (com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request
				.StopVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(stopVmRequest);
		
		com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response
			.StopVirtualMachineResponse stopVmResponse = (com.sjc.adaptee.cloud
			.vmware.version51.module.virtualmachine.response.StopVirtualMachineResponse) 
			TccCloudPlatUitls.getVmwareAdapte().execute(stopVmRequest);
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		loginUserInfo = determineLoinuser(loginUserInfo);
		Boolean isOK = stopVmResponse.isResult();
		if (isOK) {
			applyedHostResource.setRunStatusCd(BusinessEnvironment.VM_RUN_STATUS_STOP);
			vmAssistService.updateApplyedHostResource(applyedHostResource);
			
			
				logService.saveOperLog("成功", loginUserInfo.getEmpName()
				+ "停止vcenter虚拟机" + applyedHostResource.getHostNane()
				+ BusinessEnvironment.OPER_RESULT_SUCCESS, applyedHostResource, 2);
			return true;
		} else {
				logService.saveOperLog("失败", loginUserInfo.getEmpName()
				+ "停止vcenter虚拟机" + applyedHostResource.getHostNane()
				+ BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
			return false;
		}
	}

	@Override
	public boolean stopVC(Long applyResourceId, String srt) throws Exception, Throwable {
		TccApplyedHostResource applyedHostResource = vmAssistService.getApplyedHostResource(applyResourceId);
		com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request
			.StopVirtualMachineRequest stopVmRequest = new com.sjc.adaptee.cloud
			.vmware.version51.module.virtualmachine.request.StopVirtualMachineRequest();
		
		TccCloudplatform tcccloudplatform = this.findPlatformByVmId(applyResourceId + "");
		stopVmRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
		stopVmRequest.setName(applyedHostResource.getHostNane());
		stopVmRequest = (com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.request
				.StopVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(stopVmRequest);
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		String operater = "【资源到期自动关机】";
		if(loginUserInfo!=null) operater = loginUserInfo.getEmpName();
		com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response
			.StopVirtualMachineResponse stopVmResponse = new com.sjc.adaptee.cloud
			.vmware.version51.module.virtualmachine.response.StopVirtualMachineResponse();
		
		try {
			stopVmResponse = (com.sjc.adaptee.cloud.vmware.version51.module.virtualmachine.response
					.StopVirtualMachineResponse) TccCloudPlatUitls.getVmwareAdapte().execute(stopVmRequest);
		} catch (Exception e) {
			return false;
		}
		Boolean isOK = stopVmResponse.isResult();
		if (isOK) {
			applyedHostResource.setRunStatusCd(BusinessEnvironment.VM_RUN_STATUS_STOP);
			vmAssistService.updateApplyedHostResource(applyedHostResource);
			logService.saveOperLog("成功", operater
					+ "停止vcenter虚拟机" + applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"
					+ BusinessEnvironment.OPER_RESULT_SUCCESS, applyedHostResource, 2);
			return true;
		} else {
			logService.saveOperLog("失败", operater
					+ "停止vcenter虚拟机" + applyedHostResource.getHostNane()+"("+applyedHostResource.getIpAddress()+")"
					+ BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
			return false;
		}
	}

	/**
	 * CloudStack中迁移虚拟机
	 */
	@Override
	public boolean moveVmWareCS(Long applyResourceId, String hostId)
			throws Exception {
		return false;
	}

	@Override
	public boolean moveVmWareVC(Long applyResourceId, String hostName)
			throws Exception {
		return false;
	}

	@SuppressWarnings("rawtypes")
	public Double getDiskSize(Long srtId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT SUM(AA.STORAGE_SIZE) FROM (");
		sqlBuffer.append("SELECT HOS.STORAGE_SIZE,0 FROM T_CC_APPLYED_HOST_RESOURCE HOS WHERE HOS.APPLY_RESOURCE_ID IN(");
		sqlBuffer.append("SELECT ASS.APPLY_RESOURCE_ID FROM T_CC_SRT SRT LEFT JOIN TCC_SRT_APPLYEDHOST_ASS ASS ");
		sqlBuffer.append("ON SRT.SRT_ID=ASS.SRT_ID WHERE SRT.SRT_ID=" + srtId + ")");
		sqlBuffer.append("UNION select ST.STORAGE_SIZE,1 from T_CC_STORAGE_APPLYCASE_ASS ST where ST.SRT_ID=" + srtId + ") AA");
		List listResult = this.commonDao.findColsBySqlQuery(sqlBuffer.toString());
		if (listResult.size() > 0) {
			Object obj = listResult.get(0);
			return Double.parseDouble(obj.toString());
		}
		return 0.0;
	}

	/**
	 * 常规资源扩容
	 */
	@SuppressWarnings("unchecked")
	public boolean expandVm(Long srtApplyedhostAssId) throws Exception, Throwable {
		TccSrtApplyedhostAss srtApplyedhostAss = vmAssistService.getSrtApplyedhostAss(srtApplyedhostAssId);
		TccSrt srt = srtApplyedhostAss.getTccSrt();
		TccExpdCapacity expdCapacity = vmAssistService.getExpdCapacity(srt.getSrtId());
		TccApplyedHostinfo applyedHostinfo = srtApplyedhostAss.getTccApplyedHostinfo();
		TccApplyedHostResource applyedHostResource = vmAssistService
				.getApplyedHostResource(applyedHostinfo.getApplyResourceId());
		applyedHostResource.setProcessStatus(BusinessEnvironment.RESOURCE_TYPE_INSTALL); // 安装中
		vmAssistService.updateApplyedHostResource(applyedHostResource);
		applyedHostResource = vmAssistService.getApplyedHostResource(applyedHostResource.getApplyResourceId());
		boolean flag = false;
		
		String VirtualType = applyedHostResource.getVirtualType();
		// 调用扩容接口
		if (BusinessEnvironment.VIRTUAL_TYPE_VC.equals(VirtualType)) { // 01.虚拟化类型为VC，调用VC的接口
			flag = capacityMgmtService.upgradeVmVC(srt.getSrtId());
		} else if (BusinessEnvironment.VIRTUAL_TYPE_CS.equals(VirtualType)) { // 02.虚拟化类型为CS，调用CS的接口
			boolean deletesnapshotFlag = deletVmSnapshotByvmId(
					String.valueOf(applyedHostResource.getApplyResourceId()), applyedHostResource.getUuid());
			if (deletesnapshotFlag == false) {	//CloudStack必须要删除快照才能扩容
				return false;
			}
			flag = capacityMgmtService.upgradeVmCS(srt.getSrtId());// 调用扩容接口
		} else if (BusinessEnvironment.VIRTUAL_TYPE_OPENSTACK.equals(VirtualType)) { // 05.虚拟化类型为OS，调用OS的接口
			flag = capacityMgmtService.upgradeVmOS(srt.getSrtId());// 调用扩容接口
		} else if (BusinessEnvironment.VIRTUAL_TYPE_HUAWEIOPENSTACK.equals(VirtualType)) { // 06.虚拟化类型为华为OS，调用OS的接口
			flag = capacityMgmtService.upgradeVmHuaWeiOS(srt.getSrtId());// 调用扩容接口
		} else if (BusinessEnvironment.VIRTUAL_TYPE_QINGCLOUD.equals(VirtualType)) { // 06.虚拟化类型为华为OS，调用OS的接口
			flag = capacityMgmtService.upgradeVmQc(srt.getSrtId());// 调用扩容接口
		} else if (BusinessEnvironment.VIRTUAL_TYPE_ALIYUN.equals(VirtualType)) { // 06.虚拟化类型为华为OS，调用OS的接口
			flag = capacityMgmtService.upgradeVmAliYun(srt.getSrtId());// 调用扩容接口
			//TODO
		} else if (BusinessEnvironment.VIRTUAL_TYPE_TENCENT.equals(VirtualType)) { // 06.虚拟化类型为华为OS，调用OS的接口
			//TODO
		}

		if (!flag) {
			applyedHostResource.setProcessStatus(BusinessEnvironment.RESOURCE_TYPE_FAIL); // 安装失败
			vmAssistService.updateApplyedHostResource(applyedHostResource);
			return flag;
		}
		
		// 新生成一条已分配记录
		TccApplyedHostResource newApplyedHostResource = new TccApplyedHostResource();
		BeanUtils.copyProperties(newApplyedHostResource, applyedHostResource);
		newApplyedHostResource.setCpuCoreCount(expdCapacity.getCpuCoreCount());
		newApplyedHostResource.setRamSize(expdCapacity.getRamSize());
		if (null != expdCapacity.getStoreSize()) {
			Double diskSize = getDiskSize(srt.getSrtId());
			newApplyedHostResource.setStorageSize(diskSize);
		} else if (null != expdCapacity.getNasStoreSize()) {
			Double diskSize = getDiskSize(srt.getSrtId());
			newApplyedHostResource.setStorageSize(diskSize);
		}
		newApplyedHostResource.setOrginResourceID(applyedHostResource.getOrginResourceID());
		newApplyedHostResource.setRunStatusCd(BusinessEnvironment.VM_RUN_STATUS_RUNNING);
		// 新资源主键设置为空
		newApplyedHostResource.setApplyResourceId(null);
		newApplyedHostResource.setLastuptDttm(null); // 最后修改时间为空
		newApplyedHostResource.setProcessStatus(BusinessEnvironment.RESOURCE_TYPE_SUCCESS); // 修改操作状态为空
		// 新资源
		newApplyedHostResource = vmAssistService.mergeApplyedHostResource(newApplyedHostResource);
		
		//维护快照和虚机的关系
		if(!BusinessEnvironment.VIRTUAL_TYPE_CS.equals(VirtualType)){	//CS的快照会在扩容前删除
			Long resourceId = applyedHostResource.getApplyResourceId();
			String hql = "FROM TccSnapshotHistory SH WHERE SH.enableFlg = '1' AND SH.vmId = ?";
			List<TccSnapshotHistory> snapshotList = this.commonDao.find(hql, resourceId);
			if(null != snapshotList && snapshotList.size() > 0){
				for(TccSnapshotHistory snapshot : snapshotList){
					snapshot.setVmId(newApplyedHostResource.getApplyResourceId());
					this.commonDao.update(snapshot);
				}
			}
		}
		
		// 修改原已分配记录状态
		applyedHostResource.setRunStatusCd(BusinessEnvironment.RESOURCE_RUNTYPE_EXPDEND);// 扩容完成
		applyedHostResource.setProcessStatus(BusinessEnvironment.RESOURCE_TYPE_SUCCESS);// 安装完成
		applyedHostResource.setEnableFlg(EnvironmentConstant.DISENABLE); // 设置为无效
		applyedHostResource.setQuart_enable(BusinessEnvironment.QUAR_ENABLE_FALSE);
		vmAssistService.updateApplyedHostResource(applyedHostResource);
		// 修改服务请求主表与已分配资源代码表
		srtApplyedhostAss.setTccApplyedHostinfo(newApplyedHostResource);
		vmAssistService.updateSrtApplyedhostAss(srtApplyedhostAss);
		// 新的项目和资源关系
		List<TccProjectAss> newProjectAssList = new ArrayList<TccProjectAss>();
		TccProjectAss newTccProjectAss = new TccProjectAss();
		// 批量修改项目与已申请资源表关系
		TccProjectAss projectAss = new TccProjectAss();
		projectAss.setTccApplyedHostinfo(applyedHostResource);
		// ***** by zhachaoy *****
		DetachedCriteria criteria = DetachedCriteria.forClass(TccProjectAss.class);
		if (null != projectAss && null != projectAss.getProjectAssId()) {
			criteria.add(Restrictions.eq("projectAssId", projectAss.getProjectAssId()));
		}
		if (null != projectAss && null != projectAss.getTccProjectInfo()) {
			criteria.add(Restrictions.eq("tccProjectInfo", projectAss.getTccProjectInfo()));
		}
		if (null != projectAss && null != projectAss.getTccApplyedHostinfo()) {
			criteria.add(Restrictions.eq("tccApplyedHostinfo", projectAss.getTccApplyedHostinfo()));
		}
		// 数据有效性为空或者等于1的
		criteria.add(Restrictions.or(Restrictions.eq("enableFlg", EnvironmentConstant.ENABLE), Restrictions.isNull("enableFlg")));
		List<TccProjectAss> projectAssList = commonDao.findByCriteria(criteria);
		// ##### by zhachaoy #####
		for (TccProjectAss pa : projectAssList) {
			// 拷贝关系表
			BeanUtils.copyProperties(newTccProjectAss, pa);
			newTccProjectAss.setProjectAssId(null);
			newTccProjectAss.setTccInstanceInfo(null);
			newTccProjectAss.setTccApplyedHostinfo(newApplyedHostResource);
			newProjectAssList.add(newTccProjectAss);
			pa.setEnableFlg(EnvironmentConstant.DISENABLE);
		}
		vmAssistService.updateProjectAssList(projectAssList);
		// 保存新的项目和资源关系
		this.commonDao.saveOrUpdateAll(newProjectAssList);
		// 修改新的虚拟机与快照策略的关系
		TccStrategyCase strategy = this.strategyCaseService.queryStrategyByCloudhostId(applyedHostResource.getApplyResourceId());
		if (strategy != null) {
			if (strategy.getStrategyValue2() != null
					&& strategy.getStrategyValue2().intValue() == applyedHostResource.getApplyResourceId().intValue()) {
				strategy.setStrategyValue2(newApplyedHostResource.getApplyResourceId());
				this.commonDao.update(strategy);
			}
		}
		
		//迁移账号信息
		String updateSql = "update T_CC_RESOURCE_ACCOUNT set APPLY_RESOURCE_ID = ? where APPLY_RESOURCE_ID = ?";
		commonDao.updateByNativeSql(updateSql, Lists.newArrayList((Object)
				newApplyedHostResource.getApplyResourceId(),applyedHostResource.getApplyResourceId()));
		//迁移历史追溯日志记录
		updateSql = "update T_CC_OPERLOG set OPER_OBJECT_ID = ? where OPER_OBJ_TYPE = ? and OPER_OBJECT_ID = ?";
		commonDao.updateByNativeSql(updateSql, Lists.newArrayList((Object)
				newApplyedHostResource.getApplyResourceId(),StringUtils.substringAfterLast(
    			TccApplyedHostinfo.class.toString(), "."),applyedHostResource.getApplyResourceId()));
		//维护已安装软件与扩容后虚拟机的关系
		updateSql = "update T_CC_SOFT_WARE sw set sw.VM_ID = ? where VM_ID = ?";
		commonDao.updateByNativeSql(updateSql,Lists.newArrayList((Object)
				newApplyedHostResource.getApplyResourceId(),applyedHostResource.getApplyResourceId()));
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		this.saveResOperLog("扩容", StringUtils.substringAfterLast(TccApplyedHostinfo.class.toString(), "."), 
				newApplyedHostResource.getApplyResourceId(), "用户" + loginUserInfo.getEmpName() + "扩容虚拟机：" 
				+ newApplyedHostResource.getHostNane()+"("+newApplyedHostResource.getIpAddress()+"),配置:"+newApplyedHostResource.getCpuCoreCount()+"C/"+newApplyedHostResource.getRamSize()/1024+"G" + BusinessEnvironment.OPER_RESULT_SUCCESS);
		try {
			// 虚拟机扩容完成后，需要重新注册监控
			HostMonitorBean hostinfo = new HostMonitorBean();
			hostinfo.setHostName(newApplyedHostResource.getApplyResourceId().toString());
			hostinfo.setVisibleName(newApplyedHostResource.getHostNane());
			hostinfo.setIp(applyedHostResource.getIpAddress());
			TccOs os = this.getOsById(applyedHostResource.getOsCd());
			if (null != os && null != os.getOneLevelOs()) {
				hostinfo.setOsName(os.getOneLevelOs());
			} else {
				hostinfo.setOsName("");
			}
			applyedHostResource.setMonitorFlg(zabbixApiService.updateHost(hostinfo) ? "1" : "0");
		}catch (Throwable t) {
			this.logger.error("扩容完成虚拟机后重新注册监控出错", t);
		}
		return true;
	}

	@Override
	public boolean expandMinicomputer(Long srtApplyedhostAssId) throws Exception, Throwable {

		TccSrtApplyedhostAss srtApplyedhostAss = vmAssistService.getSrtApplyedhostAss(srtApplyedhostAssId);
		TccSrt srt = srtApplyedhostAss.getTccSrt();
		TccExpdCapacity expdCapacity = vmAssistService.getExpdCapacity(srt.getSrtId());
		TccApplyedHostinfo applyedHostinfo = srtApplyedhostAss.getTccApplyedHostinfo();
		TccApplyedHostResource applyedHostResource = vmAssistService
				.getApplyedHostResource(applyedHostinfo.getApplyResourceId());
		applyedHostResource.setProcessStatus(BusinessEnvironment.RESOURCE_TYPE_SUCCESS); // 安装成功
		vmAssistService.updateApplyedHostResource(applyedHostResource);
		applyedHostResource = vmAssistService
				.getApplyedHostResource(applyedHostResource.getApplyResourceId());
		// 新生成一条已分配记录
		TccApplyedHostResource newApplyedHostResource = new TccApplyedHostResource();
		BeanUtils.copyProperties(newApplyedHostResource, applyedHostResource);
		// 如果扩容的内存/CPU比原来小机分区的要大，那么修改为大的配置；如果小，那么则不修改小机分区的配置
		if (expdCapacity.getCpuCoreCount() > newApplyedHostResource.getCpuCoreCount()) {
			newApplyedHostResource.setCpuCoreCount(expdCapacity.getCpuCoreCount());
		}
		if (expdCapacity.getRamSize() > newApplyedHostResource.getRamSize()) {
			newApplyedHostResource.setRamSize(expdCapacity.getRamSize());
		}
		if (null != expdCapacity.getStoreSize()) {
			newApplyedHostResource.setStorageSize(newApplyedHostResource
					.getStorageSize() == null ? 0 : newApplyedHostResource
					.getStorageSize() + expdCapacity.getStoreSize());
		} else if (null != expdCapacity.getNasStoreSize()) {
			newApplyedHostResource.setNasStorageSize(newApplyedHostResource
					.getNasStorageSize() == null ? 0 : newApplyedHostResource
					.getNasStorageSize() + expdCapacity.getNasStoreSize());
		}
		newApplyedHostResource.setOrginResourceID(applyedHostResource.getOrginResourceID());
		if (applyedHostResource.getRunStatusCd().equals(
				BusinessEnvironment.RESOURCE_RUNTYPE_RUNCAPACITY)) {
			newApplyedHostResource.setRunStatusCd(BusinessEnvironment.VM_RUN_STATUS_RUNNING);
		} else if (applyedHostResource.getRunStatusCd().equals(
				BusinessEnvironment.RESOURCE_RUNTYPE_STOPCAPACITY)) {
			newApplyedHostResource.setRunStatusCd(BusinessEnvironment.VM_RUN_STATUS_STOP);
		}
		// 新资源主键设置为空
		newApplyedHostResource.setApplyResourceId(null);
		newApplyedHostResource.setLastuptDttm(null); // 最后修改时间为空
		newApplyedHostResource.setProcessStatus(null); // 修改操作状态为空
		// 新资源
		newApplyedHostResource = vmAssistService.mergeApplyedHostResource(newApplyedHostResource);
		// 修改原已分配记录状态
		applyedHostResource.setRunStatusCd(BusinessEnvironment.RESOURCE_RUNTYPE_EXPDEND);// 扩容完成
		applyedHostResource.setProcessStatus(BusinessEnvironment.RESOURCE_TYPE_SUCCESS);// 安装完成
		applyedHostResource.setEnableFlg(EnvironmentConstant.DISENABLE); // 设置为无效
		vmAssistService.updateApplyedHostResource(applyedHostResource);
		// 修改服务请求主表与已分配资源代码表
		srtApplyedhostAss.setTccApplyedHostinfo(newApplyedHostResource);
		vmAssistService.updateSrtApplyedhostAss(srtApplyedhostAss);
		// 新的项目和资源关系
		List<TccProjectAss> newProjectAssList = new ArrayList<TccProjectAss>();
		TccProjectAss newTccProjectAss = new TccProjectAss();
		// 批量修改项目与已申请资源表关系
		TccProjectAss projectAss = new TccProjectAss();
		projectAss.setTccApplyedHostinfo(applyedHostResource);
		List<TccProjectAss> projectAssList = vmAssistService.getProjectAssList(projectAss);
		for (TccProjectAss pa : projectAssList) {
			// 拷贝关系表
			BeanUtils.copyProperties(newTccProjectAss, pa);
			newTccProjectAss.setProjectAssId(null);
			newTccProjectAss.setTccInstanceInfo(null);
			newTccProjectAss.setEnableFlg(EnvironmentConstant.DISENABLE);
			newProjectAssList.add(newTccProjectAss);
			// 重新赋值已分配资源
			pa.setTccApplyedHostinfo(newApplyedHostResource);
		}
		vmAssistService.updateProjectAssList(projectAssList);
		// 保存新的项目和资源关系
		this.commonDao.saveOrUpdateAll(newProjectAssList);
		// 如果是压测工单，那么保存至中件间关系表，为压测实施表查询做准备
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		logService.saveOperLog(BusinessEnvironment.OPER_RESULT_SUCCESS, loginUserInfo.getEmpName()
						+ BusinessEnvironment.OPER_TYPE_ADD_VM + newApplyedHostResource.getHostNane(),
				newApplyedHostResource, 2);
		return true;
	}

	@Override
	public TccInstanceInfo expandInstance(ResrcExpdVO resrcExpdVO) throws Exception {
		return null;
	}

	@Override
	public VmInfo getVmInfo(Long applyResourceId) throws Exception {
		logger.info("pass by");
		return getVmInfo(new VmInfo(), applyResourceId);
	}

	/**
	 * 从数据库中获得虚拟机信息
	 * 
	 * @param applyResourceId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private VmInfo getVmInfo(VmInfo vmInfo, Long applyResourceId) throws Exception {
		logger.debug("填充虚拟机对象,对象ID为:" + applyResourceId);
		TccApplyedHostResource applyHostResource = vmAssistService.getApplyedHostResource(applyResourceId);
		TccProjectInfo projectInfo = vmAssistService.getProjectInfo(applyHostResource);
		TccOrgan tccOrgan = null;
		TccEmployee employee = null;
		if (null != projectInfo) {
			tccOrgan = vmAssistService.getOrgan(projectInfo.getProjectId());
			employee = vmAssistService.getEmployeeByProjectId(projectInfo.getProjectId());
		}
		Map<String, String> optionMap = applicationCacheService.getCacheList("100");
		Map<String, Object> usageMap = hostService.getUsageById(String
				.valueOf(applyHostResource.getApplyResourceId()));
		if (usageMap != null) {
			logger.debug("填充虚拟机CPU使用率:" + (String) usageMap.get("cpuUsage"));
			logger.debug("填充虚拟机MEM使用率:" + (String) usageMap.get("memUsage"));
			vmInfo.setAssignedCpu((String) usageMap.get("cpuUsage"));
			vmInfo.setAssignedMemory((String) usageMap.get("memUsage"));
		}
		vmInfo.setVmId(applyHostResource.getUuid());
		vmInfo.setVmName(applyHostResource.getHostNane());
		vmInfo.setVmip(applyHostResource.getIpAddress());
		vmInfo.setApplyResourceId(applyHostResource.getApplyResourceId().toString());
		if (applyHostResource.getRunStatusCd().equalsIgnoreCase("01"))
			vmInfo.setRunStatus("运行"); // 虚拟机状态
		else if (applyHostResource.getRunStatusCd().equalsIgnoreCase("02"))
			vmInfo.setRunStatus("停止"); // 虚拟机状态
		else if (applyHostResource.getRunStatusCd().equalsIgnoreCase("03"))
			vmInfo.setRunStatus("运行状态，扩容工单处理中"); // 虚拟机状态
		else if (applyHostResource.getRunStatusCd().equalsIgnoreCase("04"))
			vmInfo.setRunStatus("运行状态,释放工单处理中中"); // 虚拟机状态
		else if (applyHostResource.getRunStatusCd().equalsIgnoreCase("05"))
			vmInfo.setRunStatus("停止状态,扩容工单处理中中"); // 虚拟机状态
		else if (applyHostResource.getRunStatusCd().equalsIgnoreCase("06"))
			vmInfo.setRunStatus("停止状态,释放工单处理中中"); // 虚拟机状态
		else if (applyHostResource.getRunStatusCd().equalsIgnoreCase("07"))
			vmInfo.setRunStatus("已销毁"); // 虚拟机状态
		else if (applyHostResource.getRunStatusCd().equalsIgnoreCase("08"))
			vmInfo.setRunStatus("扩容完成"); // 虚拟机状态
		else if (applyHostResource.getRunStatusCd().equalsIgnoreCase("09"))
			vmInfo.setRunStatus("运行状态,迁移工单处理中中"); // 虚拟机状态
		else if (applyHostResource.getRunStatusCd().equalsIgnoreCase("10"))
			vmInfo.setRunStatus("停止状态,迁移工单处理中中"); // 虚拟机状态

		vmInfo.setVirtualType(applyHostResource.getVirtualType());
		vmInfo.setSystemType(MapUtils.get(optionMap, applyHostResource.getOsVersionCd()));
		vmInfo.setCpuTotal(String.valueOf(applyHostResource.getCpuCoreCount()));// 总CPU
		vmInfo.setMemoryTotal(String.valueOf(applyHostResource.getRamSize() / 1024));// 剩余内存
		vmInfo.setHostId(applyHostResource.getTccPhysiscResourceInfo()!=null?applyHostResource.getTccPhysiscResourceInfo().getResourceId() + "":"");
		vmInfo.setHostName(applyHostResource.getTccPhysiscResourceInfo()!=null?applyHostResource.getTccPhysiscResourceInfo().getPhysicsName() + "":"");
		vmInfo.setHostIp(applyHostResource.getTccPhysiscResourceInfo()!=null?applyHostResource.getTccPhysiscResourceInfo().getPhysicsIp() + "":"");
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" SELECT DISTINCT d.zone_name");
		stringBuffer.append(" FROM T_CC_APPLYED_HOST_RESOURCE t");
		stringBuffer.append(" INNER JOIN T_CC_PHYSISC_RESOURCE_INFO  p ON t.resource_Id= p.resource_id");
		stringBuffer.append(" INNER JOIN T_CC_CLOUD_DATACENTER d ON d.zone_id=p.zoneid");
		stringBuffer.append(" WHERE t.APPLY_RESOURCE_ID='" + vmInfo.getApplyResourceId() + "'");
		List<Object> listPod = (List<Object>) this.commonDao.findBySql(stringBuffer.toString(), null, -1, -1);
		if (listPod != null) {
			vmInfo.setPods(String.valueOf(listPod.get(0)));
		}
		if (applyHostResource.getTccPhysiscResourceInfo() != null
				&& applyHostResource.getTccPhysiscResourceInfo().getTccCluster() != null) {
			vmInfo.setClusterId(applyHostResource.getTccPhysiscResourceInfo().getTccCluster().getId() + ""); // 获取集群ID
			vmInfo.setClusters(applyHostResource.getTccPhysiscResourceInfo()
					.getTccCluster().getClusterName()); // 获取集群名称,用以找到相同集群下的所有vCenter主机，为迁移作准备
		} else {
			vmInfo.setClusterId(null);
			vmInfo.setClusters(null);
		}
		vmInfo.setRemarks(applyHostResource.getMemo());
		if (null != tccOrgan) { // 部门
			vmInfo.setDepartment(tccOrgan.getPartyFullname());
		}
		if (null != employee) { // 用户
			vmInfo.setMangment(employee.getAccountNo());
		}
		String HardwareTypeCd = applyHostResource.getTccPhysiscResourceInfo().getHardwareTypeCd();
		String DeviceTypeName = applicationCacheService.getMapValue("168", HardwareTypeCd);// 从缓存中取得代码所对应的硬件类型名称
		vmInfo.setDeviceType(DeviceTypeName);
		if (null != projectInfo) {
			vmInfo.setProject(projectInfo.getProjectName());
			vmInfo.setAppType(projectInfo.getDataFlag());
			vmInfo.setApplicationTime(projectInfo.getProjecgBeginDttm() == null ? "" : projectInfo.getProjecgBeginDttm()
				+ "-" + (projectInfo.getProjecgEndDttm() == null ? "" : projectInfo.getProjecgEndDttm()));
		}
		vmInfo.setBuildTime(DateUtil.dateToString(applyHostResource.getCrtDttm(), DateUtil.DEFAULT_FORMAT));
		logger.debug("填充虚拟机信息完毕");
		return vmInfo;
	}

	/**
	 * cloudstack克隆虚拟机
	 */
	@SuppressWarnings("unchecked")
	public synchronized Map<String, String> CloneVm(Long assId,
			Long copyHostId, String clusterId, TccProjectInfo tccProjectInfo,
			String vmName, String vlanUuId, String ipAddress) throws Exception, Throwable {
		TccConfigAssApplycase ass = (TccConfigAssApplycase) configAssApplyCaseService
				.getConfigAssApplyCase(assId);
		TccApplyedHostinfo host = (TccApplyedHostinfo) this.commonDao.get(TccApplyedHostinfo.class, copyHostId);
		// 修改虚拟机安装状态为进行中
		ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTING);
		configAssApplyCaseService.updateConfigAssApplyCase(ass);
		TccCloudplatform tcccloudplatform = this.findPlatformByVmId(host.getApplyResourceId() + "");
		// 根据安装界面传来的集群表ID，选择相应的集群记录 然后根据此记录，
		TccClusterConfig clusterConfig = vmAssistService.getClusterConfig(Long.parseLong(clusterId));
		try {
			List<Map<String, Object>> vlanList = (List<Map<String, Object>>) this.commonDao
					.querySqlByJdbcTpl("select a.ID as vlanId from  T_CC_VLAN_NETWORK a "
									+ "join T_CC_PROJECT_INFO b on a.SYS_ID=b.PROJECT_ID where  a.SYS_ID="
									+ tccProjectInfo.getProjectId() + " and a.UUID='" + vlanUuId + "' ",
							new ArrayList<Object>(), null);
			if (vlanList != null && vlanList.size() > 0) {
				Map<String, Object> rs = (Map<String, Object>) vlanList.get(0);
				ass.setVlanId(Long.valueOf(String.valueOf(rs.get("vlanId"))));
			}
		} catch (Exception ex) {
			logger.error("创建虚拟机查找VLANID失败" + ex);
			String error = "创建虚拟机查找VLANID失败";
			Map<String, String> errorMsg = new HashMap<String, String>();
			errorMsg.put("SUCCESS", "false");
			errorMsg.put("msg", error);
			return errorMsg;
		}
		ass.setVrSubType(clusterConfig.getVtype());

		Map<String, String> errorMsg = new HashMap<String, String>();
		if (vmName != null && !vmName.equals("")) {
			boolean flag = vmAssistService.checkDuplVmName(vmName);
			if (flag == false) {
				String error = "输入的名字已经存在，请重新输入";
				logger.error(error);
				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", error);
				return errorMsg;
			}
		}
		try {
			TccCloudDatacenter dc = (TccCloudDatacenter) commonDao.get(TccCloudDatacenter.class,
					Long.valueOf(clusterConfig.getZoneId()));
			String jobId = CloneVmService.CloneVm(host.getUuid(), vmName, null,
					vlanUuId, this.findPlatformByClusterId(clusterId).getCloudplatformId().toString(), dc, ipAddress);
			String jobStatus = asynQueryVmStatus(jobId, this
					.findPlatformByClusterId(clusterId).getCloudplatformId().toString());

			if (SUCCESS.equals(jobStatus)) {
				errorMsg.put("SUCCESS", "true");
				errorMsg.put("msg", "克隆虚拟机成功[" + vmName + "]");
				VirtualMachine vmVo = cloudStackService.getVmInfoCS(jobId, this
						.findPlatformByClusterId(clusterId).getCloudplatformId().toString());

				String serviceOfferingId = vmVo.getServiceOfferingId();
				ListServiceOfferingsRequest cmd1 = new ListServiceOfferingsRequest();
				cmd1.setCloud(this.findPlatformByClusterId(clusterId).getCloudplatformId() + "");
				cmd1.setId(serviceOfferingId);
				cmd1.setIsSystem("false");
				cmd1 = (ListServiceOfferingsRequest) TccCloudPlatUitls.setRequestParam(cmd1);
				String ip = vmVo.getNic().get(0).getIpAddress();
				StringBuffer updateSql = new StringBuffer();
				updateSql.append("UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='克隆"
								+ vmName + "虚拟机' WHERE f.IP_ADDRESS ='" + ip + "'");
				commonDao.updateByNativeSql(updateSql.toString());
				String volumeOfferingId = "";
				// 添加监控
				HostMonitorBean hostinfo = new HostMonitorBean();
				hostinfo.setHostName(vmVo.getNic().get(0).getIpAddress());
				hostinfo.setIp(vmVo.getNic().get(0).getIpAddress());
				OsListRequest osRequest = new OsListRequest();
				osRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
				osRequest = (OsListRequest) TccCloudPlatUitls.setRequestParam(osRequest);
				OsListResponse osListResponse = (OsListResponse) TccCloudPlatUitls.getAdapte().execute(osRequest);
				String osDescription = "";
				if (osListResponse.getListostypesresponse().getCount() > 0) {
					for (OS osType : osListResponse.getListostypesresponse().getOstype()) {
						if (osType.equals(vmVo.getGroupId())) {
							osDescription = osType.getDescription();
						}
					}
				}
				if (osDescription.contains("windows") || osDescription.contains("windows")) {
					hostinfo.setOsName("windows");
				} else {
					hostinfo.setOsName("linux");
				}
				// linux,windows
				hostinfo.setPort("10050");
				hostinfo.setStatus(0);//
				hostinfo.setMonitorType(1);
				try {
					zabbixApiService.addHostToMonitor(hostinfo);
				} catch (Exception ex) {
					this.logger.error("添加监控失败！", ex);
				} catch (Throwable t) {
					this.logger.error("添加监控失败！", t);
				}
				// 设置操作系统类型
				ass.setOsType(hostinfo.getOsName());
				// 删除创建的模板(虚拟机创建成功以后要删除模板)
				new DeleteTemplateThread(vmVo.getTemplateId(), vmVo.getZoneId(), 
						this.findPlatformByClusterId(clusterId).getCloudplatformId().toString()).start();
				// 保存服务请求主表与已分配资源代码表
				TccSrt srt = vmAssistService.getSrt(ass.getTccSrt().getSrtId());
				TccApplyedHostResource applyedHostResource = vmAssistService
						.mergeApplyedHostResource(vmVo, ass, "", volumeOfferingId);
				vmAssistService.saveSrtApplyedhostAss(applyedHostResource, srt);
				applyedHostResource.setCloseDttm(srt.getSrtCloseDttm());
				applyedHostResource.setHardwareTypeCd("186");
				ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTSUCC);
				configAssApplyCaseService.updateConfigAssApplyCase(ass);
				// 剩余CPU、MEM减少
				capacityMgmtService.takingCpuRam(applyedHostResource.getTccPhysiscResourceInfo(), 
						ass.getCpuSize(), ass.getRamSize());
				// 保存项目与已申请资源表关系
				TccProjectInfo projectInfo = tccProjectInfo;
				vmAssistService.saveProjectAss(applyedHostResource, projectInfo);
				// 把已生成的虚拟机名称命名方式，修改为：IP_项目名称_开发环境类型，如：10.10.11.13_产险电销系统_开发测试
				String displayName = vmName;
				TccApplyedHostResource ahr = vmAssistService.getApplyedHostResource(applyedHostResource.getApplyResourceId());
				ahr.setHostNane(displayName);
				// 新修改的添加模板信息
				ahr.setTemplateId(host.getTemplateId());
				// 新修改的添加集群信息
				ahr.setClusterId(clusterId);
				vmAssistService.updateApplyedHostResource(ahr);
				// 如果创建虚拟机成功，虚拟机信息插入到snapshotcategory表里面
				TccSnapshotCfg snapshotCfg = new TccSnapshotCfg();
				snapshotCfg.setCrtDttm(new Date());
				Set<TccApplyedHostinfo> tccApplyedHostinfos = ass.getTccApplyedHostinfos();
				for (TccApplyedHostinfo temp : tccApplyedHostinfos) {
					snapshotCfg.setCrtUserId(temp.getCrtUserId() + "");
					break;
				}
				snapshotCfg.setCrtUserId(String.valueOf(srt.getCrtUserId()));
				snapshotCfg.setShotMaxNum(0L);
				snapshotCfg.setHostId(ahr.getApplyResourceId());
				snapshotCfg.setVmName(displayName);
				snapshotCfg.setEnableFlg("1");
				snapshotCategoryService.AddSnapshotCategory(snapshotCfg);
				LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
				logService.saveOperLog("成功", "实施者" + loginUserInfo.getEmpName()
						+ "克隆cloudstack虚拟机" + vmName
						+ BusinessEnvironment.OPER_RESULT_SUCCESS, ass, 2);
				errorMsg.put("SUCCESS", "true");
			} else {// 失败
				LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance()
						.getCurrentUser();
				logService.saveOperLog("失败", "实施者" + loginUserInfo.getEmpName()
						+ "克隆cloudstack虚拟机" + vmName
						+ BusinessEnvironment.OPER_RESULT_FAILURE, ass, 2);
				ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTFAIL);// 失败状态
				configAssApplyCaseService.updateConfigAssApplyCase(ass);
				logger.error(errorMsg);
				errorMsg.put("SUCCESS", "false");
				errorMsg.put("msg", "克隆虚拟机失败[" + vmName + "]" + errorMsg);
			}
		} catch (Exception e) {
			LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
			logService.saveOperLog("失败", "实施者" + loginUserInfo.getEmpName()
					+ "克隆cloudstack虚拟机" + vmName
					+ BusinessEnvironment.OPER_RESULT_FAILURE, ass, 2);
			ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTFAIL);// 失败状态
			configAssApplyCaseService.updateConfigAssApplyCase(ass);
			e.printStackTrace();
			logger.error(e);
			errorMsg.put("SUCCESS", "false");
			errorMsg.put("msg", "克隆虚拟机失败[" + vmName + "]" + e.getMessage());
		}
		return errorMsg;
	}

	class RemoveCloneTemplate implements Runnable {
		private String templateId;
		private String zoneId;

		RemoveCloneTemplate(String templateId) {
			this.templateId = templateId;
		}

		public void run() {
			try {
				DeleteTemplateRequest deleteTemplateRequest = new DeleteTemplateRequest();
				deleteTemplateRequest.setCloud(Target.CLOUDSTACK);
				deleteTemplateRequest.setId(templateId);
				deleteTemplateRequest.setZoneid(zoneId);
				deleteTemplateRequest = (DeleteTemplateRequest) TccCloudPlatUitls
						.setRequestParam(deleteTemplateRequest);
				TccCloudPlatUitls.getAdapte().execute(deleteTemplateRequest);
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		public String getTemplateId() {
			return templateId;
		}

		public void setTemplateId(String templateId) {
			this.templateId = templateId;
		}

		public String getZoneId() {
			return zoneId;
		}

		public void setZoneId(String zoneId) {
			this.zoneId = zoneId;
		}

	}

	public CloudStackService getCloudStackService() {
		return cloudStackService;
	}

	public void setCloudStackService(CloudStackService cloudStackService) {
		this.cloudStackService = cloudStackService;
	}

	public ConfigAssApplyCaseService getConfigAssApplyCaseService() {
		return configAssApplyCaseService;
	}

	public void setConfigAssApplyCaseService(
			ConfigAssApplyCaseService configAssApplyCaseService) {
		this.configAssApplyCaseService = configAssApplyCaseService;
	}

	public CapacityMgmtService getCapacityMgmtService() {
		return capacityMgmtService;
	}

	public void setCapacityMgmtService(CapacityMgmtService capacityMgmtService) {
		this.capacityMgmtService = capacityMgmtService;
	}

	public VmAssistService getVmAssistService() {
		return vmAssistService;
	}

	public void setVmAssistService(VmAssistService vmAssistService) {
		this.vmAssistService = vmAssistService;
	}

	public StorageService getStorageService() {
		return storageService;
	}

	public void setStorageService(StorageService storageService) {
		this.storageService = storageService;
	}

	public VolumeService getVolumeService() {
		return volumeService;
	}

	public void setVolumeService(VolumeService volumeService) {
		this.volumeService = volumeService;
	}

	public ApplicationCacheService getApplicationCacheService() {
		return applicationCacheService;
	}

	public void setApplicationCacheService(
			ApplicationCacheService applicationCacheService) {
		this.applicationCacheService = applicationCacheService;
	}

	public HostService getHostService() {
		return hostService;
	}

	public void setHostService(HostService hostService) {
		this.hostService = hostService;
	}

	public SnapshotCategoryService getSnapshotCategoryService() {
		return snapshotCategoryService;
	}

	public void setSnapshotCategoryService(
			SnapshotCategoryService snapshotCategoryService) {
		this.snapshotCategoryService = snapshotCategoryService;
	}

	public HignNetworkService getHignNetworkService() {
		return hignNetworkService;
	}

	public void setHignNetworkService(HignNetworkService hignNetworkService) {
		this.hignNetworkService = hignNetworkService;
	}

	public VmSnapshotService getVmSnapshotService() {
		return vmSnapshotService;
	}

	public void setVmSnapshotService(VmSnapshotService vmSnapshotService) {
		this.vmSnapshotService = vmSnapshotService;
	}

	public OperLogService getOperLogService() {
		return operLogService;
	}

	public void setOperLogService(OperLogService operLogService) {
		this.operLogService = operLogService;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public MealService getMealService() {
		return mealService;
	}

	public void setMealService(MealService mealService) {
		this.mealService = mealService;
	}

	@Override
	public Map<String, String> getVmPlatformInfo(Long vmId) {
		StringBuilder sql = new StringBuilder("SELECT PLATFORM.CLOUDPLATFORM_IP ip, PLATFORM.CLOUDPLATFORM_USER username, ");
		sql.append(" PLATFORM.CLOUDPLATFORM_PASSWORD password, PLATFORM.CLOUDPLATFORM_KEY platformKey ");
		sql.append(" FROM T_CC_APPLYED_HOSTINFO HOST ");
		sql.append(" LEFT JOIN T_CC_CLOUD_DATACENTER DATACENTER ON HOST.ZONEID = DATACENTER.ZONE_ID ");
		sql.append(" LEFT JOIN T_CC_CLOUDPLATFORM PLATFORM ON DATACENTER.CLOUDPLATFORM_ID = PLATFORM.CLOUDPLATFORM_ID ");
		sql.append(" WHERE HOST.APPLY_RESOURCE_ID = ? ");
		List<Map<String, Object>> platInfoList = commonDao.findAsMapList(sql.toString(), new Object[] { vmId });
		if (platInfoList != null && platInfoList.size() > 0) {
			Map<String, String> map = new HashMap<String, String>();
			for (Entry<String, Object> entry : platInfoList.get(0).entrySet()) {
				map.put(entry.getKey(), (String) entry.getValue());
			}
			return map;
		}
		return null;
	}

	@Override
	public Map<String, String> createOpenStack(SrtManageVO srtManageVO,
			TccProjectInfo tccProjectInfo) throws Exception, Throwable {
		Map<String, String> errorMap = new HashMap<String, String>();
		String errorMsg = "";
		String cloudplatformId = srtManageVO.getCloudplatformId();
		if (StringUtils.isBlank(cloudplatformId)) {
			errorMsg = "平台id为空，无法继续操作，请检查数据是否正确。"; 
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}

		if (StringUtils.isBlank(srtManageVO.getVmName())) {
			errorMsg = "虚拟机名称不能为空，请输入虚拟机名称。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}

		String vmName = srtManageVO.getVmName().replaceAll(" ", "");
		openstackNameCheck(cloudplatformId, vmName, errorMap);
		if (errorMap.size() > 0)
			return errorMap;
		String networkId = srtManageVO.getNetworkId();
		String networkUuid = null;
		if (StringUtils.isBlank(networkId)) {
			errorMsg = "网络设置不正确，请检查网络数据是否正确。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		TccVlanNetwork network = networkService.findById(Long.valueOf(networkId));
		if (network == null) {
			errorMsg = "没有找到正确的网络，不能继续操作，请检查网络数据是否正确。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		networkUuid = network.getUuid();
		if (StringUtils.isBlank(networkUuid)) {
			errorMsg = "没有找到正确的网络，不能继续操作，请检查网络数据是否正确。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		String applyedHostResourceId = excuteCreateOpenStack(srtManageVO, tccProjectInfo, networkUuid);
		if (applyedHostResourceId == null) {
			errorMsg = "创建资源失败。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		errorMap.put("SUCCESS", applyedHostResourceId);
		//保存日志
		try{
			TccConfigAssApplycase ass = (TccConfigAssApplycase) this.commonDao.get(TccConfigAssApplycase.class, Long.valueOf(srtManageVO.getConfigAssApplycaseId()));
			logService.saveOperLog("成功", "创建", ass, 2);
		}
		catch(Exception e){}
		//新建虚拟机的时候自动创建一个快照
		String host = String.valueOf(applyedHostResourceId);	//虚拟机ID
		TCcEvn ccEvn = srtManageVO.getEvn();
		boolean initSnapshot = ccEvn!=null ?ccEvn.isInitSnapshot():false;
		//判断是否需要初始快照
		if(initSnapshot){
			try {
				boolean Status = false;
	    		String noteString = "新建虚拟机时自动创建的快照";
	    		vmSnapshotService.createVmSnapshot(host.trim(),Status,noteString.trim());
	    		//把创建的虚拟机的快照设为初始创建快照时创建
	    		//创建完快照时需要让休眠300毫秒才能查询数据
	    		Thread.sleep(300);
	    		String sql="UPDATE T_CC_SNAPSHOT_HISTORY SET SNAPSHOT_MODE = 3 WHERE VM_ID = "+host;
	    		commonDao.updateByNativeSql(sql);
				
			} catch (Exception e) {
				logger.error("新建虚拟机时，自动创建的快照失败");
			}
		}
		//把虚拟机的ID用json返回页面
	
		JSONObject object = new JSONObject();
		object.put("vmId", host);
		JsonResponseUtil.writeJsonArray(object);
		return errorMap;
	}
	/**
	 * 创建青云虚拟机
	 */
	@Override
	public Map<String, String> createQingCloud(SrtManageVO srtManageVO,
			TccProjectInfo tccProjectInfo) throws Exception, Throwable {
		Map<String, String> errorMap = new HashMap<String, String>();
		String errorMsg = "";
		String cloudplatformId = srtManageVO.getCloudplatformId();
		if (StringUtils.isBlank(cloudplatformId)) {
			errorMsg = "平台id为空，无法继续操作，请检查数据是否正确。"; 
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}

		if (StringUtils.isBlank(srtManageVO.getVmName())) {
			errorMsg = "虚拟机名称不能为空，请输入虚拟机名称。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}

		String vmName = srtManageVO.getVmName().replaceAll(" ", "");
		if (errorMap.size() > 0)
			return errorMap;
		String applyedHostResourceId = excuteCreateQingCloud(srtManageVO, tccProjectInfo);
		if (applyedHostResourceId == null) {
			errorMsg = "创建资源失败。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		errorMap.put("SUCCESS", applyedHostResourceId);
		//保存日志
		try{
			TccConfigAssApplycase ass = (TccConfigAssApplycase) this.commonDao.get(TccConfigAssApplycase.class, Long.valueOf(srtManageVO.getConfigAssApplycaseId()));
			logService.saveOperLog("成功", "创建", ass, 2);
		}
		catch(Exception e){}
		//新建虚拟机的时候自动创建一个快照
		String host = String.valueOf(applyedHostResourceId);	//虚拟机ID
		TCcEvn ccEvn = srtManageVO.getEvn();
		boolean initSnapshot = ccEvn!=null ?ccEvn.isInitSnapshot():false;
		//判断是否需要初始快照
		if(initSnapshot){
			try {
				boolean Status = false;
	    		String noteString = "新建虚拟机时自动创建的快照";
	    		vmSnapshotService.createVmSnapshot(host.trim(),Status,noteString.trim());
	    		//把创建的虚拟机的快照设为初始创建快照时创建
	    		//创建完快照时需要让休眠300毫秒才能查询数据
	    		Thread.sleep(300);
	    		String sql="UPDATE T_CC_SNAPSHOT_HISTORY SET SNAPSHOT_MODE = 3 WHERE VM_ID = "+host;
	    		commonDao.updateByNativeSql(sql);
				
			} catch (Exception e) {
				logger.error("新建虚拟机时，自动创建的快照失败");
			}
		}
		//把虚拟机的ID用json返回页面
	
		JSONObject object = new JSONObject();
		object.put("vmId", host);
		JsonResponseUtil.writeJsonArray(object);
		return errorMap;
	}
	@Override
	public Map<String, String> createHuaWeiOpenStack(SrtManageVO srtManageVO,
			TccProjectInfo tccProjectInfo) throws Exception, Throwable { 
		Map<String, String> errorMap = new HashMap<String, String>();
		String errorMsg = "";
		String cloudplatformId = srtManageVO.getCloudplatformId();
		if (StringUtils.isBlank(cloudplatformId)) {
			errorMsg = "平台id为空，无法继续操作，请检查数据是否正确。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}

		if (StringUtils.isBlank(srtManageVO.getVmName())) {
			errorMsg = "虚拟机名称不能为空，请输入虚拟机名称。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}

		String vmName = srtManageVO.getVmName().replaceAll(" ", "");
		huaWeiOpenstackNameCheck(cloudplatformId, vmName, errorMap);
		if (errorMap.size() > 0)
			return errorMap;
		String networkId = srtManageVO.getNetworkId();
		String networkUuid = null;
		if (StringUtils.isBlank(networkId)) {
			errorMsg = "网络设置不正确，请检查网络数据是否正确。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		TccVlanNetwork network = networkService.findById(Long.valueOf(networkId));
		if (network == null) {
			errorMsg = "没有找到正确的网络，不能继续操作，请检查网络数据是否正确。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		networkUuid = network.getUuid();
		if (StringUtils.isBlank(networkUuid)) {
			errorMsg = "没有找到正确的网络，不能继续操作，请检查网络数据是否正确。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		String applyedHostResourceId = excuteCreateHuaWeiOpenStack(srtManageVO, tccProjectInfo, networkUuid);
		if (applyedHostResourceId == null) {
			errorMsg = "创建资源失败。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		
		errorMap.put("SUCCESS", applyedHostResourceId);
		//新建虚拟机的时候自动创建一个快照
		String host = String.valueOf(applyedHostResourceId);	//虚拟机ID
		//保存安装日志
		try{
			TccConfigAssApplycase ass = (TccConfigAssApplycase) this.commonDao.get(TccConfigAssApplycase.class, Long.valueOf(srtManageVO.getConfigAssApplycaseId()));
			logService.saveOperLog("成功", "创建", ass, 2);
		}
		catch(Exception e){}
		TCcEvn ccEvn = srtManageVO.getEvn();
		boolean initSnapshot = ccEvn!=null ?ccEvn.isInitSnapshot():false;
		//判断是否需要初始快照
		if(initSnapshot){
			try {
				
				boolean Status = false;
	    		String noteString = "新建虚拟机时自动创建的快照";
	    		vmSnapshotService.createVmSnapshot(host.trim(),Status,noteString.trim());
	    		//把创建的虚拟机的快照设为初始创建快照时创建
	    		//创建完快照时需要让休眠300毫秒才能查询数据
	    		Thread.sleep(300);
	    		String sql="UPDATE T_CC_SNAPSHOT_HISTORY SET SNAPSHOT_MODE = 3 WHERE VM_ID = "+host;
	    		commonDao.updateByNativeSql(sql);
			} catch (Exception e) {
				logger.error("新建虚拟机时，自动创建的快照失败");
			}
			
		}
		//把虚拟机的ID用json返回页面
	
		JSONObject object = new JSONObject();
		object.put("vmId", host);
		JsonResponseUtil.writeJsonArray(object);
		return errorMap;
	}

	@Override
	public Map<String, String> openstackNameCheck(String cloudplatformId,
			String serverName, Map<String, String> errorMap) {
		String errorMsg = "";
		boolean flag = vmAssistService.checkDuplVmName(serverName);
		if (flag == false) {
			errorMsg = "输入的名字已经存在，请重新输入";
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		ServerListRequest request = new ServerListRequest();
		request.setCloud(cloudplatformId);
		request = (ServerListRequest) TccCloudPlatUitls
				.setRequestParam(request);
		ServerListResponse response = new ServerListResponse();
		try {
			response = (ServerListResponse) TccCloudPlatUitls.getOpenStackAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("openstack虚拟机查询出错。" + e.getMessage());
			return errorMap;
		}
		if (response == null || response.getServerList() == null
				|| response.getServerList().size() == 0) {
			logger.error("没有找到对应的虚拟机。");
			return errorMap;
		}
		for (ServerResponse res : response.getServerList()) {
			if (res == null || res.getServerPojo() == null
					|| ServerStatus.ACTIVE.equals(res.getServerPojo().getStatus()) == false)
				continue;

			ServerPojo pojo = res.getServerPojo();
			if (serverName.equals(pojo.getName()) == false)
				continue;

			errorMsg = "输入的名字已经存在，请重新输入";
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		return errorMap;
	}

	@Override
	public Map<String, String> huaWeiOpenstackNameCheck(String cloudplatformId,
			String serverName, Map<String, String> errorMap) {
		String errorMsg = "";
		boolean flag = vmAssistService.checkDuplVmName(serverName);
		if (flag == false) {
			errorMsg = "输入的名字已经存在，请重新输入";
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		ServersRequest serverListRequest = new ServersRequest();
		serverListRequest.setCloud(String.valueOf(cloudplatformId));
		serverListRequest = (ServersRequest) TccCloudPlatUitls.setRequestParam(serverListRequest);
		ServersResponse serverListResponse = new ServersResponse();
		try {
			serverListResponse = (ServersResponse) TccCloudPlatUitls
					.getHuaWeiOpenStackAdapte().execute(serverListRequest);
		} catch (Exception e) {
			logger.error("Exception 获取openstack实例出错。" + e.getMessage());
			return errorMap;
		} catch (Throwable t) {
			logger.error("Throwable 获取openstack实例出错。" + t.getMessage());
			return errorMap;
		}
		if (serverListResponse != null
				&& ResponseParameter.SUCCESSSTATUS.equalsIgnoreCase(serverListResponse.getStatus())) {
			for (ServersPojo res : serverListResponse.getServersList()) {
				if (res == null || serverName.equals(res.getName()) == false)
					continue;

				errorMsg = "输入的名字已经存在，请重新输入";
				logger.error(errorMsg);
				errorMap.put("FAILURE", errorMsg);
				return errorMap;
			}
		}

		return errorMap;
	}
	
	@Override
	public Map<String, String> aliYunNameCheck(String cloudplatformId,
			String serverName, Map<String, String> errorMap,String zoneId) {
		String errorMsg = "";
		boolean flag = vmAssistService.checkDuplVmName(serverName);
		if (flag == false) {
			errorMsg = "输入的名字已经存在，请重新输入";
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		AliYunDescribeInstancesRequest request = new AliYunDescribeInstancesRequest();
		request.setCloud(cloudplatformId);
		request.setInstanceName(serverName);
		request.setIoOptimized(true);
		request.setRegionId(zoneId);
		request = (AliYunDescribeInstancesRequest) TccCloudPlatUitls.setRequestParam(request);
		AliYunDescribeInstancesResponse response = new AliYunDescribeInstancesResponse();
		try {
			response = (AliYunDescribeInstancesResponse) TccCloudPlatUitls.getAliYunAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("aliyun虚拟机查询出错。" + e.getMessage());
			return errorMap;
		}
		if (response == null || response.getInstances() == null
				|| response.getInstances().size() == 0) {
			logger.error("没有找到对应的虚拟机。");
			return errorMap;
		}
		for (InstancePojo pojo : response.getInstances()) {
			
			if (serverName.equals(pojo.getInstanceName()) == false){
				continue;
			}

			errorMsg = "输入的名字已经存在，请重新输入";
			logger.error(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		return errorMap;
	}

	/**
	 * Description:执行创建
	 * 
	 * @param srtManageVO
	 * @param tccProjectInfo
	 * @param networkUuid
	 * @return
	 * @throws Exception
	 * @throws Throwable
	 * @return String
	 */
	private String excuteCreateOpenStack(SrtManageVO srtManageVO,
			TccProjectInfo tccProjectInfo, String networkUuid) throws Exception, Throwable {
		String cloudplatformId = srtManageVO.getCloudplatformId();
		String serverName = srtManageVO.getVmName();
		String zoneId = srtManageVO.getZoneId();
		String clusterId = srtManageVO.getClusterId();
		// 取得云主机类型
		List<FlavorResponse> flavorList = getFlavorList(cloudplatformId);
		TccConfigAssApplycase ass = configAssApplyCaseService
				.getConfigAssApplyCase(Long.parseLong(srtManageVO.getAssId()));

		// 获取可用模版
		// 根据数据中心Id查询UUID
		TccCloudDatacenter datacenter = (TccCloudDatacenter) this.commonDao
				.get(TccCloudDatacenter.class, Long.parseLong(zoneId));
		String currentUserId = LoginUserInfoHolder.getInstance().getCurrentUser().getUserPartyId().toString();
		//当前虚拟机安装日志
		if(installLog.get(currentUserId)==null){
			installLog.put(currentUserId,new ArrayList<String>());
		}
		List<String> installLog_current = installLog.get(currentUserId);
		installLog_current.add("已选择Zone:" + datacenter.getZoneName());
		
		// 根据集群获取虚拟化类型
		String virtualType = "";
		DetachedCriteria criteria = DetachedCriteria.forClass(TccClusterConfig.class);
		criteria.add(Restrictions.eq("id", Long.parseLong(clusterId)));
		criteria.add(Restrictions.eq("zoneId", zoneId));
		@SuppressWarnings("unchecked")
		List<TccClusterConfig> clusters = commonDao.findByCriteria(criteria);
		if (clusters != null && clusters.size() > 0) {
			virtualType = clusters.get(0).getVtype();
			
			installLog_current.add("已选择Cluster:" + clusters.get(0).getClusterName());
			
		}
		TccTemplateCase template = isoCaseService.queryTemplateByHostInfo(
				ass.getIsoId(), datacenter.getUuid(), virtualType);
		if (template == null || template.getCsTemplateOfferingId() == null) {
			logger.error("没有找到对应的可用模板。");
			return null;
		}
		
		installLog_current.add("已选择模板:" + template.getTemplateName());
		
		// 取得镜像id
		String imageId = template.getCsTemplateOfferingId();

		// 根据申请单ID查询存储
		List<TccStorageApplyCaseAss> storageList = getStorageApplyCaseByAssid(ass.getAssId());
	/*	if (storageList.size() == 0)
			return null;*/

		String flavorId = getFlavorId(flavorList, ass);
		if (StringUtils.isBlank(flavorId)) {
			flavorId = createFlavor(cloudplatformId, serverName, 
					ass.getRamSize().intValue(), ass.getCpuSize().intValue(), null);
		}
		ServerPojo serverPojo = openStackBoot(cloudplatformId, serverName, flavorId, imageId, networkUuid);
		if (serverPojo == null || serverPojo.getAddresses() == null
				|| serverPojo.getAddresses().getAddressesList() == null
				|| serverPojo.getAddresses().getAddressesList().size() == 0) {
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "创建失败");
			
			return null;
		}
		
		installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "创建成功;");
		
		String volumeId = null;
		if (storageList != null && !storageList.isEmpty()) {
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "挂载数据盘开始");
			
			// 虚拟机挂载磁盘
			for (TccStorageApplyCaseAss applyCaseAss : storageList) {
				// 创建磁盘
				volumeId = createVolume(cloudplatformId, applyCaseAss.getStorageSize().intValue(), serverName);
				if (StringUtils.isBlank(volumeId)) {
					continue;
				}
				attachVolume(cloudplatformId, serverPojo.getId(), volumeId, serverPojo.getName());
				applyCaseAss.setUuid(volumeId);
				// 更新数据库中磁盘uuid
				commonDao.update(applyCaseAss);
			}
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "挂载数据盘结束");
			
		}
		/*if (StringUtils.isBlank(volumeId))
			return null;*/
		String applyedHostResourceId = saveOrUpdateApplyedHostResource(
				serverPojo, tccProjectInfo, ass.getAssId(), clusterId, zoneId,
				imageId, networkUuid,srtManageVO.getHostInfoProduceId(), template.getTemplateCaseId());
		
		installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "安装结束");
		
		return applyedHostResourceId;
	}

	/**
	 * Description:执行创建青云虚拟机
	 * 
	 * @param srtManageVO
	 * @param tccProjectInfo
	 * @param networkUuid
	 * @return
	 * @throws Exception
	 * @throws Throwable
	 * @return String
	 */
	private String excuteCreateQingCloud(SrtManageVO srtManageVO,
			TccProjectInfo tccProjectInfo) throws Exception, Throwable {
		String cloudplatformId = srtManageVO.getCloudplatformId();
		String serverName = srtManageVO.getVmName();
		String zoneId = srtManageVO.getZoneId();
		String clusterId = srtManageVO.getClusterId();
		TccConfigAssApplycase ass = configAssApplyCaseService
				.getConfigAssApplyCase(Long.parseLong(srtManageVO.getAssId()));
		
		// 获取可用模版
		// 根据数据中心Id查询UUID
		TccCloudDatacenter datacenter = (TccCloudDatacenter) this.commonDao
				.get(TccCloudDatacenter.class, Long.parseLong(zoneId));
		String currentUserId = LoginUserInfoHolder.getInstance().getCurrentUser().getUserPartyId().toString();
		//当前虚拟机安装日志
		if(installLog.get(currentUserId)==null){
			installLog.put(currentUserId,new ArrayList<String>());
		}
		List<String> installLog_current = installLog.get(currentUserId);
		installLog_current.add("已选择Zone:" + datacenter.getZoneName());
		
		// 根据集群获取虚拟化类型
		String virtualType = datacenter.getHypervisorType();
		TccTemplateCase template = isoCaseService.queryTemplateByHostInfo(
				ass.getIsoId(), datacenter.getUuid(), virtualType);
		if (template == null || template.getCsTemplateOfferingId() == null) {
			logger.error("没有找到对应的可用模板。");
			installLog_current.add("没有找到对应的可用模板");
			return null;
		}
		installLog_current.add("已选择模板:" + template.getTemplateName());
		// 取得镜像id
		String imageId = template.getCsTemplateOfferingId();
		//创建虚拟机
		VirtualMachinePojo virtualMachinePojo = qingCloudBoot(cloudplatformId, serverName, imageId, null, ass.getCpuSize().intValue(), ass.getRamSize().intValue(), srtManageVO.getInstanceType(),datacenter.getUuid());
		// 根据申请单ID查询存储
		List<TccStorageApplyCaseAss> storageList = getStorageApplyCaseByAssid(ass.getAssId());
		if (virtualMachinePojo == null) {
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "创建失败");
			return null;
		}
		
		installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "创建成功;");
		
		String volumeId = null;
		if (storageList != null && !storageList.isEmpty()) {
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "挂载数据盘开始");
			
			// 虚拟机挂载磁盘
			for (TccStorageApplyCaseAss applyCaseAss : storageList) {
				// 创建磁盘
				volumeId = createQingCloudVolume(cloudplatformId, applyCaseAss.getStorageSize().intValue(), serverName,datacenter.getUuid());
				if (StringUtils.isBlank(volumeId)) {
					continue;
				}
				attachQingcloudVolume(cloudplatformId, virtualMachinePojo.getVmId(), volumeId, virtualMachinePojo.getVmName(),datacenter.getUuid());
				applyCaseAss.setUuid(volumeId);
				// 更新数据库中磁盘uuid
				commonDao.update(applyCaseAss);
			}
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "挂载数据盘结束");
			
		}
		String applyedHostResourceId = saveOrUpdateQingcloudApplyedHostResource(
				virtualMachinePojo, tccProjectInfo, ass.getAssId(), clusterId, zoneId,
				imageId, null,srtManageVO.getHostInfoProduceId(), template.getTemplateCaseId());
		
		installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "安装结束");
		
		return applyedHostResourceId;
	}
	
	/**
	 * Description:执行创建阿里云虚拟机
	 * 
	 * @param srtManageVO
	 * @param tccProjectInfo
	 * @param networkUuid
	 * @return
	 * @throws Exception
	 * @throws Throwable
	 * @return String
	 */
	@SuppressWarnings("unused")
	private String excuteCreateAliYun(SrtManageVO srtManageVO,
			TccProjectInfo tccProjectInfo) throws Exception, Throwable {
		String cloudplatformId = srtManageVO.getCloudplatformId();
		String serverName = srtManageVO.getVmName();
		String zoneId = srtManageVO.getZoneId();
		String clusterId = srtManageVO.getClusterId();
		TccConfigAssApplycase ass = configAssApplyCaseService
				.getConfigAssApplyCase(Long.parseLong(srtManageVO.getAssId()));
		
		// 获取可用模版
		// 根据数据中心Id查询UUID
		TccCloudDatacenter datacenter = (TccCloudDatacenter) this.commonDao
				.get(TccCloudDatacenter.class, Long.parseLong(zoneId));
		String uuid = datacenter.getUuid();
		String currentUserId = LoginUserInfoHolder.getInstance().getCurrentUser().getUserPartyId().toString();
		//当前虚拟机安装日志
		if(installLog.get(currentUserId)==null){
			installLog.put(currentUserId,new ArrayList<String>());
		}
		List<String> installLog_current = installLog.get(currentUserId);
		installLog_current.add("已选择Zone:" + datacenter.getZoneName());
		
		// 根据集群获取虚拟化类型
		String virtualType = datacenter.getHypervisorType();
		TccTemplateCase template = isoCaseService.queryTemplateByHostInfo(
				ass.getIsoId(), uuid, virtualType);
		if (template == null || template.getCsTemplateOfferingId() == null) {
			logger.error("没有找到对应的可用模板。");
			installLog_current.add("没有找到对应的可用模板");
			return null;
		}
		installLog_current.add("已选择模板:" + template.getTemplateName());
		// 取得镜像id
		String imageId = template.getCsTemplateOfferingId();
		//创建虚拟机
		InstancePojo instancePojo = aliYunBoot(cloudplatformId, serverName, imageId, null, ass.getCpuSize().intValue(), ass.getRamSize().intValue(), srtManageVO.getInstanceType(),datacenter.getUuid());
		
		// 根据申请单ID查询存储
		List<TccStorageApplyCaseAss> storageList = getStorageApplyCaseByAssid(ass.getAssId());
		if (instancePojo == null) {
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "创建失败");
			return null;
		}
		
		installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "创建成功;");
		
		String volumeId = null;
		if (storageList != null && !storageList.isEmpty()) {
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "挂载数据盘开始");
			
			// 虚拟机挂载磁盘
			for (TccStorageApplyCaseAss applyCaseAss : storageList) {
				// 创建磁盘
				volumeId = createAliYunVolume(cloudplatformId, applyCaseAss.getStorageSize().intValue(), serverName,uuid,zoneId);
				if (StringUtils.isBlank(volumeId)) {
					continue;
				}
				attachAliYunVolume(cloudplatformId, instancePojo.getInstanceId(), volumeId, instancePojo.getInstanceName(),uuid);
				applyCaseAss.setUuid(volumeId);
				// 更新数据库中磁盘uuid
				commonDao.update(applyCaseAss);
			}
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "挂载数据盘结束");
			
		}
		String applyedHostResourceId = saveOrUpdateAliYunApplyedHostResource(
				instancePojo, tccProjectInfo, ass.getAssId(), clusterId, zoneId,
				imageId, null,srtManageVO.getHostInfoProduceId(), template.getTemplateCaseId());
		
		//启动虚拟机
		aliYunService.start(Long.valueOf(applyedHostResourceId));
		
		installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "安装结束");
		
		return applyedHostResourceId;
	}
	
	/**
	 * 创建华为虚拟机 2016年6月2日19:46:00
	 * 
	 * @param srtManageVO
	 * @param tccProjectInfo
	 * @param networkUuid
	 * @return
	 * @throws Exception
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	private String excuteCreateHuaWeiOpenStack(SrtManageVO srtManageVO,
			TccProjectInfo tccProjectInfo, String networkUuid) throws Exception, Throwable {
		String cloudplatformId = srtManageVO.getCloudplatformId();
		String serverName = srtManageVO.getVmName();
		String zoneId = srtManageVO.getZoneId();
		String clusterId = srtManageVO.getClusterId();
		// 取得云主机类型
		List<FlavorsPojo> flavorList = getHuaWeiFlavorList(cloudplatformId);
		TccConfigAssApplycase ass = configAssApplyCaseService
				.getConfigAssApplyCase(Long.parseLong(srtManageVO.getAssId()));
		// 获取可用模版
		// 根据数据中心Id查询UUID
		TccCloudDatacenter datacenter = (TccCloudDatacenter) this.commonDao
				.get(TccCloudDatacenter.class, Long.parseLong(zoneId));
		
		String currentUserId = LoginUserInfoHolder.getInstance().getCurrentUser().getUserPartyId().toString();
		//当前虚拟机安装日志
		if(installLog.get(currentUserId)==null){
			installLog.put(currentUserId,new ArrayList<String>());
		}
		List<String> installLog_current = installLog.get(currentUserId);
		installLog_current.add("已选择Zone:" + datacenter.getZoneName());
		
		// 根据集群获取虚拟化类型
		String virtualType = "";
		DetachedCriteria criteria = DetachedCriteria.forClass(TccClusterConfig.class);
		criteria.add(Restrictions.eq("id", Long.parseLong(clusterId)));
		criteria.add(Restrictions.eq("zoneId", zoneId));
		List<TccClusterConfig> clusters = commonDao.findByCriteria(criteria);
		if (clusters != null && clusters.size() > 0) {
			virtualType = clusters.get(0).getVtype();
			
			installLog_current.add("已选择Cluster:" + clusters.get(0).getClusterName());
			
		}
		TccTemplateCase template = isoCaseService.queryTemplateByHostInfo(
				ass.getIsoId(), datacenter.getUuid(), virtualType);
		if (template == null || template.getCsTemplateOfferingId() == null) {
			logger.error("没有找到对应的可用模板。");
			return null;
		}
		
		installLog_current.add("已选择模板:" + template.getTemplateName());
		
		// 取得镜像id
		String imageId = template.getCsTemplateOfferingId();

		// 根据申请单ID查询存储
		List<TccStorageApplyCaseAss> storageList = getStorageApplyCaseByAssid(ass.getAssId());
//		if (storageList.size() == 0)
//			return null;
		
		String resourceType = template.getResourceType();	//资源类型:0:物理机	1:虚拟机
		
		//获取计算方案需要的最小磁盘大小
		Long disk = this.getMinDisk(cloudplatformId, imageId);
		
		//查询计算方法是否存在,不存在,创建
		Double cpu = ass.getCpuSize();
		Double ram = ass.getRamSize();
		String diskType = srtManageVO.getSanStorage();	//本地	共享
		String flavorId = getHuaWeiFlavorId(flavorList, cpu, ram, disk, resourceType, diskType, cloudplatformId);
		if (StringUtils.isBlank(flavorId)) {
			flavorId = createHuaWeiFlavor(cloudplatformId, serverName, ass.getRamSize().intValue(), 
					ass.getCpuSize().intValue(), disk, template.getResourceType(), diskType);
		}
		ServersPojo serverPojo = huaWeiOpenStackBoot(cloudplatformId,
				serverName, flavorId, imageId, networkUuid, datacenter.getZoneName());
		if (serverPojo == null || serverPojo.getAddresses() == null
				|| serverPojo.getAddresses().size() == 0) {
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "安装失败");
			
			return null;
		}
		
		installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "安装成功");

		String volumeId = null;
		if (storageList != null && !storageList.isEmpty()) {
			
			installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "挂载数据盘开始");
			
			// 虚拟机挂载磁盘
			for (TccStorageApplyCaseAss applyCaseAss : storageList) {
				// 创建磁盘
				volumeId = createHuaWeiVolume(cloudplatformId, applyCaseAss.getStorageSize().intValue(), 
						serverName, datacenter.getZoneName(), resourceType);
				
				if(StringUtils.isNotBlank(volumeId)){
					//解决物理机出现挂载失败情况，失败重复挂载三次
					String attachVolumeId = volumeId;
					for(int i=1;i<=3;i++){
						logger.info("尝试第"+i+"次挂载...");
						volumeId = attachHuaWeiVolume(cloudplatformId, serverPojo.getId(), attachVolumeId, serverPojo.getName());
						if(StringUtils.isNotBlank(volumeId)){
							break;
						}
					}
				}
				
				installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "挂载数据盘结束");
				
				//只要有一块磁盘创建或挂载失败 ，整体返回失败
				if (StringUtils.isBlank(volumeId)) {
					break;
				}
				
				applyCaseAss.setUuid(volumeId);
				// 更新数据库中磁盘uuid
				commonDao.update(applyCaseAss);
			}
		}
//		if (StringUtils.isBlank(volumeId))
//			return null;
		String applyedHostResourceId = saveOrUpdateHuaWeiApplyedHostResource(serverPojo, 
				tccProjectInfo, ass.getAssId(), clusterId, zoneId, imageId, networkUuid, resourceType);
		
		installLog_current.add("虚拟机:" + srtManageVO.getVmName() + "安装结束");
		
		return applyedHostResourceId;
	}
	
	/**
	 * 获取计算方案所需要的最小磁盘大小
	 * @param platformId
	 * @param imageId
	 * @return
	 */
	private Long getMinDisk(String platformId, String imageId){
		ImageRequest request = new ImageRequest();
		request.setCloud(platformId);
		request.setImageId(imageId);
		request = (ImageRequest) TccCloudPlatUitls.setRequestParam(request);
		ImageResponse response = null;
		try {
			response = (ImageResponse) TccCloudPlatUitls.getHuaWeiOpenStackAdapte().execute(request);
			if(null == response || ResponseParameter.FAILED.equals(response.getSuccess()) || null == response.getImage()){
				logger.info("----------------查询镜像失败!");
				return null;
			}
		} catch (Throwable e) {
			logger.info("------------获取镜像失败", e);
		}
		
		ImagePojo image = response.getImage();
		Long disk = 0L;
		if(null != request && null != request.getHost() && request.getHost().contains("identity")){	//华为OpenStack			
			if(null != image.getMin_disk()){
				disk = Long.parseLong(image.getMin_disk());
			}
		}else if(null != request && null != request.getHost() && request.getHost().contains("ustack")){	//ustack
			if(null != image.getMin_disk()){
				disk = Long.parseLong(image.getMin_disk());
			}
			if(null != image.getSize() && Long.parseLong(image.getSize()) > disk){
				disk = Long.parseLong(image.getSize()) / 1024 / 1024 / 1024;
			}
		}else{																						//PowerVC
			StorageListRequest listRequest = new StorageListRequest();
			listRequest.setCloud(platformId);
			listRequest = (StorageListRequest) TccCloudPlatUitls.setRequestParam(listRequest);
			StorageListResponse listResponse = new StorageListResponse();
			try {
				listResponse = (StorageListResponse) TccCloudPlatUitls.getHuaWeiOpenStackAdapte().execute(listRequest);
				if(null == listResponse || null == listResponse.getStoragePojoList()
						|| ResponseParameter.FAILED.equals(listResponse.getSuccess())){
					logger.info("----------------获取存储列表失败!");
					return null;
				}
			} catch (Throwable e) {
				logger.info("----------------获取存储列表失败", e);
			}
			List<StoragePojo> storageList = listResponse.getStoragePojoList();
			for(StoragePojo storage : storageList){
				if(null == storage.getMetadata()){
					continue;
				}
				if(image.getId().equals(storage.getMetadata().get("image_id"))){
					disk = storage.getSize();
					break;
				}
			}
		}
		return disk;
	}
	
	@Override
	public String saveOrUpdateApplyedHostResource(ServerPojo serverPojo,
			TccProjectInfo tccProjectInfo, Long assId, String clusterId,
			String zoneId, String imageId, String networkUuid,String hostInfoProduceId,
			long... templateid) throws Exception, Throwable {
		TccConfigAssApplycase ass = configAssApplyCaseService.getConfigAssApplyCase(assId);
		TccSrt srt = vmAssistService.getSrt(ass.getTccSrt().getSrtId());
		String ipAddress = serverPojo.getAddresses().getAddressesList().get(0).getAddr();
		TccApplyedHostResource applyedHostResource = vmAssistService
				.mergeOpenStackApplyedHostResource(serverPojo, ass, ipAddress, zoneId, imageId);
		applyedHostResource.setHardwareTypeCd("186");
		applyedHostResource.setClusterId(clusterId);
		if(null!=hostInfoProduceId&&hostInfoProduceId.length()>0){
			applyedHostResource.setProduceId(hostInfoProduceId);
		}
		if (templateid != null) {
			applyedHostResource.setTemplateId(templateid[0] + "");
		}

		ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTSUCC);
		applyedHostResource.setNetworkUuid(networkUuid);

		configAssApplyCaseService.updateConfigAssApplyCase(ass);
		vmAssistService.saveSrtApplyedhostAss(applyedHostResource, srt);
		vmAssistService.saveProjectAss(applyedHostResource, tccProjectInfo);

		String host = String.valueOf(applyedHostResource.getApplyResourceId()); // 虚拟机ID
		String visibleName = applyedHostResource.getHostNane(); // 虚拟机名称
		TccOs os = this.getOsById(applyedHostResource.getOsCd());
		// 将虚拟机注册到Zabbix中
		try {
			if (!zabbixApiService.isAddHostToMonitorByHostName(host)) {
				HostMonitorBean hostinfo = new HostMonitorBean();
				hostinfo.setHostName(host);
				hostinfo.setVisibleName(visibleName);
				hostinfo.setIp(applyedHostResource.getIpAddress());
				if (null != os && null != os.getOneLevelOs()) {
					hostinfo.setOsName(os.getOneLevelOs());
				} else {
					hostinfo.setOsName("");
				}
				applyedHostResource.setMonitorFlg(zabbixApiService
						.addHostToMonitor(hostinfo) ? "1" : "0");
			} else {
				logger.info("主机[" + host + "]已在zabbix中注册");
				applyedHostResource.setMonitorFlg("1");
			}
		} catch (Exception ex) {
			this.logger.error("添加监控失败！", ex);
		} catch (Throwable t) {
			this.logger.error("添加监控失败！", t);
		}
		commonDao.update(applyedHostResource);
		// 更新ip的状态
		if (ipAddress != null) {
			String sql = "UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='创建"
					+ serverPojo.getInstanceName()
					+ "虚拟机',DISTRIBUTE_TIME=NOW() WHERE f.IP_ADDRESS ='" + ipAddress + "'";
			commonDao.updateByNativeSql(sql);
		}
		String applyedHostResourceId = applyedHostResource.getApplyResourceId()
				.toString();
		return applyedHostResourceId;
	}
	@Override
	public String saveOrUpdateQingcloudApplyedHostResource(VirtualMachinePojo serverPojo,
			TccProjectInfo tccProjectInfo, Long assId, String clusterId,
			String zoneId, String imageId, String networkUuid,String hostInfoProduceId,
			long... templateid) throws Exception, Throwable {
		TccConfigAssApplycase ass = configAssApplyCaseService.getConfigAssApplyCase(assId);
		TccSrt srt = vmAssistService.getSrt(ass.getTccSrt().getSrtId());
		String ipAddress = null;
		if(serverPojo.getVxnets()!=null&&!serverPojo.getVxnets().isEmpty()){
			ipAddress = serverPojo.getVxnets().get(0).getPrivateIp();
		}
		TccApplyedHostResource applyedHostResource = vmAssistService
				.mergeQingcloudApplyedHostResource(serverPojo, ass, ipAddress, zoneId, imageId);
		applyedHostResource.setHardwareTypeCd("186");
		applyedHostResource.setClusterId(clusterId);
		if(null!=hostInfoProduceId&&hostInfoProduceId.length()>0){
			applyedHostResource.setProduceId(hostInfoProduceId);
		}
		if (templateid != null) {
			applyedHostResource.setTemplateId(templateid[0] + "");
		}

		ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTSUCC);
		applyedHostResource.setNetworkUuid(networkUuid);

		configAssApplyCaseService.updateConfigAssApplyCase(ass);
		vmAssistService.saveSrtApplyedhostAss(applyedHostResource, srt);
		vmAssistService.saveProjectAss(applyedHostResource, tccProjectInfo);

		String host = String.valueOf(applyedHostResource.getApplyResourceId()); // 虚拟机ID
		String visibleName = applyedHostResource.getHostNane(); // 虚拟机名称
		TccOs os = this.getOsById(applyedHostResource.getOsCd());
		// 将虚拟机注册到Zabbix中
		try {
			if (!zabbixApiService.isAddHostToMonitorByHostName(host)) {
				HostMonitorBean hostinfo = new HostMonitorBean();
				hostinfo.setHostName(host);
				hostinfo.setVisibleName(visibleName);
				hostinfo.setIp(applyedHostResource.getIpAddress());
				if (null != os && null != os.getOneLevelOs()) {
					hostinfo.setOsName(os.getOneLevelOs());
				} else {
					hostinfo.setOsName("");
				}
				applyedHostResource.setMonitorFlg(zabbixApiService
						.addHostToMonitor(hostinfo) ? "1" : "0");
			} else {
				logger.info("主机[" + host + "]已在zabbix中注册");
				applyedHostResource.setMonitorFlg("1");
			}
		} catch (Exception ex) {
			this.logger.error("添加监控失败！", ex);
		} catch (Throwable t) {
			this.logger.error("添加监控失败！", t);
		}
		commonDao.update(applyedHostResource);
		// 更新ip的状态
		if (ipAddress != null) {
			String sql = "UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='创建"
					+ serverPojo.getVmName()
					+ "虚拟机',DISTRIBUTE_TIME=NOW() WHERE f.IP_ADDRESS ='" + ipAddress + "'";
			commonDao.updateByNativeSql(sql);
		}
		String applyedHostResourceId = applyedHostResource.getApplyResourceId()
				.toString();
		return applyedHostResourceId;
	}
	@Override
	public String saveOrUpdateHuaWeiApplyedHostClone(ServersPojo serverPojo,
			TccApplyedHostinfo host, String imageId, TccProjectInfo tccProjectInfo,
			LoginUserInfo loginUserInfo) throws Exception, Throwable {
		String hql = "FROM TccVlanNetwork WHERE enableFlg = '1' AND uuid = ?";
		//获取网络信息
		@SuppressWarnings("unchecked")
		List<TccVlanNetwork> networkList = this.commonDao.find(hql, host.getNetworkUuid());
		if (null == networkList || networkList.size() <= 0 || networkList.get(0) == null) {
			return null;
		}
		Map<String, List<Object>> addrMap = serverPojo.getAddresses();
		List<Object> addrList = addrMap.get(networkList.get(0).getNetworkName());
		if (null == addrList || addrList.size() <= 0) {
			return null;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> addr = (Map<String, Object>) addrList.get(0);

		String ipAddress = (String) addr.get("addr");
		TccApplyedHostResource applyedHostResource = vmAssistService
				.mergeHuaWeiOpenStackApplyedHostResourceClone(serverPojo, ipAddress, host, imageId,loginUserInfo);
		applyedHostResource.setHardwareTypeCd("186");
		applyedHostResource.setClusterId(host.getClusterId());
		applyedHostResource.setNetworkUuid(host.getNetworkUuid());
		//项目与申请资源表
		vmAssistService.saveProjectAss(applyedHostResource, tccProjectInfo);
		//..
		String hostId = String.valueOf(applyedHostResource.getApplyResourceId()); // 虚拟机ID
		String visibleName = applyedHostResource.getHostNane(); // 虚拟机名称
		TccOs os = this.getOsById(applyedHostResource.getOsCd());
		// 将虚拟机注册到Zabbix中
				try {
					if (!zabbixApiService.isAddHostToMonitorByHostName(hostId)) {
						HostMonitorBean hostinfo = new HostMonitorBean();
						hostinfo.setHostName(hostId);
						hostinfo.setVisibleName(visibleName);
						hostinfo.setIp(applyedHostResource.getIpAddress());
						if (null != os && null != os.getOneLevelOs()) {
							hostinfo.setOsName(os.getOneLevelOs());
						} else {
							hostinfo.setOsName("");
						}
						applyedHostResource.setMonitorFlg(zabbixApiService
								.addHostToMonitor(hostinfo) ? "1" : "0");
					} else {
						logger.info("主机[" + host + "]已在zabbix中注册");
						applyedHostResource.setMonitorFlg("1");
					}
				} catch (Exception ex) {
					this.logger.error("添加监控失败！", ex);
				} catch (Throwable t) {
					this.logger.error("添加监控失败！", t);
				}
				commonDao.update(applyedHostResource);
				// 更新ip的状态
				if (ipAddress != null) {
					String sql = "UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='创建"
							+ serverPojo.getInstance_name() + "虚拟机' WHERE f.IP_ADDRESS ='" + ipAddress + "'";
					commonDao.updateByNativeSql(sql);
				}
				String applyedHostResourceId = applyedHostResource.getApplyResourceId().toString();
		return applyedHostResourceId;
	}
	@Override
	public String saveOrUpdateHuaWeiApplyedHostResource(ServersPojo serverPojo,
			TccProjectInfo tccProjectInfo, Long assId, String clusterId,
			String zoneId, String imageId, String networkUuid, String resourceType)
			throws Exception, Throwable {
		TccConfigAssApplycase ass = configAssApplyCaseService.getConfigAssApplyCase(assId);
		TccSrt srt = vmAssistService.getSrt(ass.getTccSrt().getSrtId());

		String hql = "FROM TccVlanNetwork WHERE enableFlg = '1' AND uuid = ?";
		@SuppressWarnings("unchecked")
		List<TccVlanNetwork> networkList = this.commonDao.find(hql, networkUuid);
		if (null == networkList || networkList.size() <= 0
				|| networkList.get(0) == null) {
			logger.error("获取["+networkUuid+"] networkList 为空!");
			return null;
		}

		Map<String, List<Object>> addrMap = serverPojo.getAddresses();
		List<Object> addrList = addrMap.get(networkList.get(0).getNetworkName());
		if (null == addrList || addrList.size() <= 0) {
			logger.error("获取["+networkList.get(0).getNetworkName()+"] addrList 为空!");
			return null;
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> addr = (Map<String, Object>) addrList.get(0);

		String ipAddress = (String) addr.get("addr");
		TccApplyedHostResource applyedHostResource = vmAssistService
				.mergeHuaWeiOpenStackApplyedHostResource(serverPojo, ass, ipAddress, zoneId, imageId);
		applyedHostResource.setHardwareTypeCd("186");
		applyedHostResource.setClusterId(clusterId);
		ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTSUCC);
		applyedHostResource.setNetworkUuid(networkUuid);
		applyedHostResource.setResourceType(resourceType);	//0:物理机	1:虚拟机

		configAssApplyCaseService.updateConfigAssApplyCase(ass);
		vmAssistService.saveSrtApplyedhostAss(applyedHostResource, srt);
		vmAssistService.saveProjectAss(applyedHostResource, tccProjectInfo);

		String host = String.valueOf(applyedHostResource.getApplyResourceId()); // 虚拟机ID
		String visibleName = applyedHostResource.getHostNane(); // 虚拟机名称
		TccOs os = this.getOsById(applyedHostResource.getOsCd());
		// 将虚拟机注册到Zabbix中
		try {
			if (!zabbixApiService.isAddHostToMonitorByHostName(host)) {
				HostMonitorBean hostinfo = new HostMonitorBean();
				hostinfo.setHostName(host);
				hostinfo.setVisibleName(visibleName);
				hostinfo.setIp(applyedHostResource.getIpAddress());
				if (null != os && null != os.getOneLevelOs()) {
					hostinfo.setOsName(os.getOneLevelOs());
				} else {
					hostinfo.setOsName("");
				}
				applyedHostResource.setMonitorFlg(zabbixApiService
						.addHostToMonitor(hostinfo) ? "1" : "0");
			} else {
				logger.info("主机[" + host + "]已在zabbix中注册");
				applyedHostResource.setMonitorFlg("1");
			}
		} catch (Exception ex) {
			this.logger.error("添加监控失败！", ex);
		} catch (Throwable t) {
			this.logger.error("添加监控失败！", t);
		}
		commonDao.update(applyedHostResource);
		// 更新ip的状态
		if (ipAddress != null) {
			String sql = "UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='创建"
					+ serverPojo.getInstance_name() + "虚拟机' WHERE f.IP_ADDRESS ='" + ipAddress + "'";
			commonDao.updateByNativeSql(sql);
		}
		String applyedHostResourceId = applyedHostResource.getApplyResourceId().toString();
		return applyedHostResourceId;
	}

	/**
	 * 获取系统
	 * 
	 * @param OsId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TccOs getOsById(String OsId) {
		String hql = " FROM TccOs TO WHERE TO.osId = ?";
		List<TccOs> osList = this.commonDao.find(hql, Long.parseLong(OsId));
		TccOs tccOs = null;
		if (null != osList) {
			tccOs = osList.get(0);
		}
		return tccOs;
	}

	/**
	 * 挂载青云磁盘
	 * @param cloudplatformId
	 * @param serverId
	 * @param volumeId
	 * @param serverName
	 * @param zoneUuid
	 * @return
	 */
	@Override
	public String attachQingcloudVolume(String cloudplatformId, String serverId,
			String volumeId, String serverName,String zoneUuid) {
		VolumeAttachRequest request = new VolumeAttachRequest();
		request.setCloud(cloudplatformId);
		request = (VolumeAttachRequest) TccCloudPlatUitls.setRequestParam(request);
		VolumeAttachResponse response = new VolumeAttachResponse();
		try {
			request.setVmId(serverId);
			request.setZoneId(zoneUuid);
			request.getIds().add(volumeId);
			response = (VolumeAttachResponse) TccCloudPlatUitls.getQingCloudAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("qingcloud虚拟机挂载磁盘出错。" + e.getMessage());
		}

		if (response == null) {
			logger.error("qingcloud虚拟机挂载磁盘出错。");
			return null;
		}
		if(ResponseParameter.FAILED.equals(response.getSuccess())){
			logger.error("qingcloud虚拟机挂载磁盘出错。"+response.getError());
			return null;
		}
		boolean statusIsNull = true;
		do {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.info(e);
			}
			com.sjc.adaptee.cloud.qingcloud.volume.pojo.VolumePojo volumePojo = getQingcloudVolumeById(cloudplatformId, volumeId,zoneUuid);
			if ("in-use".equals(volumePojo.getStatus())) {
				statusIsNull = false;
			}
		} while (statusIsNull);

		logger.info("qingcloud虚拟机挂载磁盘成功。");
		return volumeId;
	}
	/**
	 * Description:挂载磁盘 Date:2015年8月27日 下午4:22:00
	 * 
	 * @param cloudplatformId
	 * @param serverId
	 * @param volumeId
	 * @param serverName
	 * @return
	 * @return String
	 */
	public String attachVolume(String cloudplatformId, String serverId,
			String volumeId, String serverName) {
		ServerAttachVolumeRequest request = new ServerAttachVolumeRequest();
		request.setCloud(cloudplatformId);
		request = (ServerAttachVolumeRequest) TccCloudPlatUitls.setRequestParam(request);
		ServerAttachVolumeResponse response = new ServerAttachVolumeResponse();
		try {
			request.setServerId(serverId);
			request.setVolumeId(volumeId);
			response = (ServerAttachVolumeResponse) TccCloudPlatUitls.getOpenStackAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("openstack虚拟机挂载磁盘出错。" + e.getMessage());
		}

		if (response == null || response.getVolumeAttachmentPojo() == null
				|| ResponseParameter.FAILED.equals(response.getSuccess())) {
			logger.error("openstack虚拟机挂载磁盘出错。");
			return null;
		}
			boolean statusIsNull = true;
			do {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					logger.info(e);
				}
				VolumePojo volumePojo = getVolumeById(cloudplatformId, volumeId);
				if (VolumeStatus.IN_USE.equals(volumePojo.getStatus())) {
					statusIsNull = false;
				}
			} while (statusIsNull);

			 logger.info("openstack虚拟机挂载磁盘成功。");
		return volumeId;
	}

	/**
	 * Description:挂载磁盘
	 * 
	 * @param cloudplatformId
	 * @param serverId
	 * @param volumeId
	 * @param serverName
	 * @return
	 * @return String
	 */
	public String attachHuaWeiVolume(String cloudplatformId, String serverId,
			String volumeId, String serverName) {
		ServersAttachRequest request = new ServersAttachRequest();
		request.setCloud(cloudplatformId);
		request = (ServersAttachRequest) TccCloudPlatUitls.setRequestParam(request);
		ServersAttachResponse response = new ServersAttachResponse();
		try {
			request.setServerId(serverId);
			request.setVolumeId(volumeId);
			response = (ServersAttachResponse) TccCloudPlatUitls.getHuaWeiOpenStackAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("华为openstack虚拟机挂载磁盘出错。" + e.getMessage());
		}

		if (response == null
				|| ResponseParameter.FAILED.equals(response.getSuccess())) {
			logger.error("华为openstack虚拟机挂载磁盘出错。");
			return null;
		}

		logger.info("华为openstack虚拟机挂载磁盘成功。");
		return volumeId;
	}

	@Override
	public String createVolume(String cloudplatformId, Integer storageSize, String serverName) {
		VolumeCreateRequest request = new VolumeCreateRequest();
		request.setCloud(cloudplatformId);
		request = (VolumeCreateRequest) TccCloudPlatUitls.setRequestParam(request);
		VolumeCreateResponse response = new VolumeCreateResponse();
		try {
			request.setSize(storageSize);
			String volumeName = serverName + "_volume" + "_" + new Random().nextInt(10000);
			request.setName(volumeName);
			request.setDescription("create " + volumeName + " through UI");
			response = (VolumeCreateResponse) TccCloudPlatUitls.getOpenStackAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("创建openstack磁盘出错。" + e.getMessage());
			return null;
		}
		String volumeId = null;
		if (response == null || response.getVolumePojo() == null)
			return null;
		VolumePojo volumePojo = response.getVolumePojo();
		if (volumePojo.getStatus() == null || VolumeStatus.AVAILABLE.equals(volumePojo.getStatus()) == false) {
			boolean statusIsNull = true;
			do {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					logger.info(e);
				}
				volumePojo = getVolumeById(cloudplatformId, volumePojo.getId());
				if (VolumeStatus.AVAILABLE.equals(volumePojo.getStatus())) {
					statusIsNull = false;
				}
			} while (statusIsNull);
		}
		volumeId = response.getVolumePojo().getId();
		logger.info("创建openstack磁盘成功。");

		return volumeId;
	}
	@Override
	public String createQingCloudVolume(String cloudplatformId, Integer storageSize, String serverName,String zoneUuid) {
		com.sjc.adaptee.cloud.qingcloud.volume.request.VolumeCreateRequest request = new com.sjc.adaptee.cloud.qingcloud.volume.request.VolumeCreateRequest();
		request.setCloud(cloudplatformId);
		request = (com.sjc.adaptee.cloud.qingcloud.volume.request.VolumeCreateRequest) TccCloudPlatUitls.setRequestParam(request);
		com.sjc.adaptee.cloud.qingcloud.volume.response.VolumeCreateResponse response = new com.sjc.adaptee.cloud.qingcloud.volume.response.VolumeCreateResponse();
		try {
			request.setZoneId(zoneUuid);
			request.setSize(storageSize);
			String volumeName = serverName + "_volume" + "_" + new Random().nextInt(10000);
			request.setName(volumeName);
			response = (com.sjc.adaptee.cloud.qingcloud.volume.response.VolumeCreateResponse) TccCloudPlatUitls.getQingCloudAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("创建qingcloud磁盘出错。" + e.getMessage());
			return null;
		}
		if (response == null || response.getVolumeIds()==null){
			return null;
		}
		if(ResponseParameter.FAILED.equals(response.getSuccess())){
			logger.error("创建qingcloud磁盘出错。" + response.getError());
			return null;
		}
		String volumeId = response.getVolumeIds().get(0);
		//查询磁盘状态是否可用
		boolean isActive = false;
		int tryTimes = 1;
		com.sjc.adaptee.cloud.qingcloud.volume.pojo.VolumePojo volume  = null;
		do{
			volume = getQingcloudVolumeById(cloudplatformId, volumeId, zoneUuid);
			//由于状态是通用的，所以用镜像状态也可以比对
			isActive = ImageStatusEnum.AVAILABLE.getValue().equals(volume.getStatus());
			if(!isActive){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					logger.error(e);;
				}
			}
			tryTimes++;
		}while(!isActive&&tryTimes<=10);
		logger.info("创建qingcloud磁盘成功。");
		return volumeId;
	}
	@Override
	public String createHuaWeiVolume(String cloudplatformId, Integer storageSize, 
			String serverName, String zoneName, String resourceType) {
		StorageCreateRequest request = new StorageCreateRequest();
		request.setCloud(cloudplatformId);
		request = (StorageCreateRequest) TccCloudPlatUitls.setRequestParam(request);
		StorageCreateResponse response = new StorageCreateResponse();
		try {
			request.setSize(storageSize.toString());
			request.setResourceType(resourceType);
			request.setZoneName(zoneName);
			String volumeName = serverName + "_volume" + "_" + new Random().nextInt(10000);
			request.setName(volumeName);
			request.setDescription("create " + volumeName + " through UI");
			response = (StorageCreateResponse) TccCloudPlatUitls.getHuaWeiOpenStackAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("创建华为openstack磁盘出错。" + e.getMessage());
			return null;
		}
		if (response == null || response.getStoragePojo() == null)
			return null;
		StoragePojo volumePojo = response.getStoragePojo();
		if (volumePojo.getStatus() == null
				|| !ParamUtils.AVAILABLE.equals(volumePojo.getStatus())) {
			return null;
		}

		String volumeId = volumePojo.getId();
		logger.info("创建华为openstack磁盘成功。");

		return volumeId;
	}

	@Override
	public VolumePojo getVolumeById(String cloudplatformId, String volumeId) {
		VolumeRequest request = new VolumeRequest();
		request.setCloud(cloudplatformId);
		request = (VolumeRequest) TccCloudPlatUitls.setRequestParam(request);
		VolumeResponse response = new VolumeResponse();
		try {
			request.setId(volumeId);
			response = (VolumeResponse) TccCloudPlatUitls.getOpenStackAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("获取openstack磁盘出错。" + e.getMessage());
		}
		if (response == null || response.getVolumePojo() == null)
			return null;
		logger.error("获取openstack磁盘成功。");
		return response.getVolumePojo();
	}
	@Override
	public com.sjc.adaptee.cloud.qingcloud.volume.pojo.VolumePojo getQingcloudVolumeById(String cloudplatformId, String volumeId,String zoneUuid) {
		VolumeRetrieveRequest request = new VolumeRetrieveRequest();
		request.setCloud(cloudplatformId);
		request.setZoneId(zoneUuid);
		request = (VolumeRetrieveRequest) TccCloudPlatUitls.setRequestParam(request);
		VolumeRetrieveResponse response = new VolumeRetrieveResponse();
		try {
			request.getIds().add(volumeId);
			response = (VolumeRetrieveResponse) TccCloudPlatUitls.getQingCloudAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("获取qingcloud磁盘出错。" + e.getMessage());
		}
		if (response == null || response.getVolumes().isEmpty())
			return null;
		logger.error("获取qingcloud磁盘成功。");
		return response.getVolumes().get(0);
	}
	/**
	 * Description:取得云主机类型id Date:2015年8月26日 上午11:04:47
	 * 
	 * @param flavorList
	 * @param ass
	 * @param applyCaseAss
	 * @return
	 * @throws Exception
	 * @throws Throwable
	 * @return String
	 */
	public String getFlavorId(List<FlavorResponse> flavorList,
			TccConfigAssApplycase ass) throws Exception, Throwable {
		String flavorId = null;
		for (FlavorResponse flavorResponse : flavorList) {
			FlavorPojo flavorPojo = flavorResponse.getFlavorPojo();
			if (flavorPojo == null)
				continue;

			// 比对云主机类型是否相同，找到相同的直接跳出
			if (ass.getCpuSize().doubleValue() == Double.valueOf(
					flavorPojo.getVcpus()).doubleValue()
					&& ass.getRamSize().doubleValue() == Double.valueOf(flavorPojo.getRam()).doubleValue()) {
				flavorId = flavorPojo.getId();
				break;
			}
		}
		return flavorId;
	}

	@Override
	public String getHuaWeiFlavorId(List<FlavorsPojo> flavorList, Double cpu, Double ram, 
			Long disk, String resourceType, String diskType, String platformId){
		FlavorListRequest request = new FlavorListRequest();
		request.setCloud(platformId);
		request = (FlavorListRequest) TccCloudPlatUitls.setRequestParam(request);
		String flavorId = null;
		if("本地".equals(diskType)){		//本地磁盘
			for (FlavorsPojo flavorPojo : flavorList) {
				if (flavorPojo == null){				
					continue;
				}
				
				// 比对云主机类型是否相同，找到相同的直接跳出
				if("0".equals(resourceType)){	//物理机
					FlavorsExtraSpecsPojo extraPojo = flavorPojo.getFlavorsExtraSpecsPojo();
					if(null != extraPojo){	//物理机规格中一定会有该扩展字段
						if(cpu == Double.valueOf(flavorPojo.getVcpus()).doubleValue()
								&& ram == Double.valueOf(flavorPojo.getRam()).doubleValue()
								&& flavorPojo.getDisk() >= disk
								&& ParamUtils.IRONIC.equalsIgnoreCase(extraPojo.getHypervisor_type())){
							flavorId = flavorPojo.getId();
							break;
						}
					}
				}else{							//虚拟机
					FlavorsExtraSpecsPojo extraPojo = flavorPojo.getFlavorsExtraSpecsPojo();
					if(null != extraPojo){	//虚拟机规格中可能有该扩展字段
						if(cpu == Double.valueOf(flavorPojo.getVcpus()).doubleValue()
								&& ram == Double.valueOf(flavorPojo.getRam()).doubleValue()
								&& flavorPojo.getDisk() >= disk
								&& !ParamUtils.IRONIC.equalsIgnoreCase(extraPojo.getHypervisor_type())){
							flavorId = flavorPojo.getId();
							break;
						}
					}else{
						if(cpu == Double.valueOf(flavorPojo.getVcpus()).doubleValue()
								&& ram == Double.valueOf(flavorPojo.getRam()).doubleValue()
								&& flavorPojo.getDisk() >= disk){
							flavorId = flavorPojo.getId();
							break;
						}
					}
				}
			}
		}else{							//共享磁盘
			for (FlavorsPojo flavorPojo : flavorList) {
				if (flavorPojo == null){				
					continue;
				}
				
				// 比对云主机类型是否相同，找到相同的直接跳出
				if("0".equals(resourceType)){	//物理机
					FlavorsExtraSpecsPojo extraPojo = flavorPojo.getFlavorsExtraSpecsPojo();
					if(null != extraPojo){	//物理机规格中一定会有该扩展字段
						if(cpu == Double.valueOf(flavorPojo.getVcpus()).doubleValue()
								&& ram == Double.valueOf(flavorPojo.getRam()).doubleValue()
								&& flavorPojo.getDisk() >= disk
								&& ParamUtils.IRONIC.equalsIgnoreCase(extraPojo.getHypervisor_type())){
							flavorId = flavorPojo.getId();
							break;
						}
					}
				}else{							//虚拟机
					if(request.getHost().contains("ustack")){
							if(cpu == Double.valueOf(flavorPojo.getVcpus()).doubleValue()
									&& ram == Double.valueOf(flavorPojo.getRam()).doubleValue()
									&& flavorPojo.getDisk() >= disk){
								flavorId = flavorPojo.getId();
								break;
							}
					}else{
						FlavorsExtraSpecsPojo extraPojo = flavorPojo.getFlavorsExtraSpecsPojo();
						if(null != extraPojo && "Volume".equalsIgnoreCase(extraPojo.getExtBootType())){	//虚拟机规格中可能有该扩展字段
							String diskStr = extraPojo.getRoot_disk();	//共享磁盘的大小
							Long diskSize = 0L;
							if(StringUtils.isNotBlank(diskStr)){							
								diskSize = Long.parseLong(diskStr);
							}
							if(cpu == Double.valueOf(flavorPojo.getVcpus()).doubleValue()
									&& ram == Double.valueOf(flavorPojo.getRam()).doubleValue()
									&& diskSize >= disk
									&& !ParamUtils.IRONIC.equalsIgnoreCase(extraPojo.getHypervisor_type())){
								flavorId = flavorPojo.getId();
								break;
							}
						}
					}
				}
			}
		}
		return flavorId;
	}

	/**
	 * Description:取得云主机类型 Date:2015年8月20日 下午5:02:47
	 * 
	 * @param cloudplatformId
	 * @return
	 * @return List<FlavorResponse>
	 */
	public List<FlavorResponse> getFlavorList(String cloudplatformId) {
		FlavorListRequest flavorListRequest = new FlavorListRequest();
		flavorListRequest.setCloud(cloudplatformId);
		flavorListRequest = (FlavorListRequest) TccCloudPlatUitls.setRequestParam(flavorListRequest);
		FlavorListResponse flavorListResponse = new FlavorListResponse();
		try {
			flavorListResponse = (FlavorListResponse) TccCloudPlatUitls
					.getOpenStackAdapte().execute(flavorListRequest);
		} catch (Throwable e) {
			logger.info("获取openstack云主机类型出错。" + e.getMessage());
		}
		List<FlavorResponse> flavorList = flavorListResponse.getFlavorList();
		return flavorList;
	}

	/**
	 * Description:取得云主机类型
	 * 
	 * @param cloudplatformId
	 * @return
	 * @return List<FlavorsPojo>
	 */
	public List<FlavorsPojo> getHuaWeiFlavorList(String cloudplatformId) {
		FlavorsRequest flavorListRequest = new FlavorsRequest();
		flavorListRequest.setCloud(cloudplatformId);
		flavorListRequest = (FlavorsRequest) TccCloudPlatUitls.setRequestParam(flavorListRequest);
		FlavorsResponse flavorListResponse = new FlavorsResponse();
		try {
			flavorListResponse = (FlavorsResponse) TccCloudPlatUitls
					.getHuaWeiOpenStackAdapte().execute(flavorListRequest);
		} catch (Throwable e) {
			logger.info("获取openstack云主机类型出错。" + e.getMessage());
		}
		List<FlavorsPojo> flavorList = flavorListResponse.getFlavorsPojoList();
		return flavorList;
	}

	@Override
	public ServerPojo openStackBoot(String cloudplatformId, String serverName,
			String flavorId, String imageId, String networkId) throws Exception {
		ServerBootRequest request = new ServerBootRequest();
		request.setCloud(cloudplatformId);
		request = (ServerBootRequest) TccCloudPlatUitls.setRequestParam(request);
		ServerBootResponse response = new ServerBootResponse();
		try {
			request.setName(serverName);
			request.setFlavorId(flavorId);
			request.setImageId(imageId);
			request.setNetwordId(networkId);
			response = (ServerBootResponse) TccCloudPlatUitls.getOpenStackAdapte().execute(request);
		} catch (Throwable e) {
			logger.info("创建openstack虚拟机出错。" + e.getMessage());
		}

		if (response == null || response.getServerPojo() == null)
			return null;

		ServerPojo serverPojo = response.getServerPojo();
		String adminPass = serverPojo.getAdminPass();
		ServerStatus serverPojoStatue = serverPojo.getStatus();
		if (ServerStatus.ACTIVE.equals(serverPojoStatue) == false) {
			boolean notActive = true;
			do {
				Thread.sleep(2000);
				serverPojo = getServerPojo(cloudplatformId, serverPojo.getId());
				if (serverPojo == null)
					return null;

				serverPojoStatue = serverPojo.getStatus();
				if (ServerStatus.ACTIVE.equals(serverPojoStatue) || ServerStatus.ERROR.equals(serverPojoStatue)){					
					notActive = false;
				}
			} while (notActive);
		}
		serverPojo.setAdminPass(adminPass);
		return serverPojo;
	}
	@Override
	public VirtualMachinePojo qingCloudBoot(String cloudplatformId, String serverName,
			String imageId, String networkId,int cpu,int ramMb,String instanceClass,String zoneUuid) throws Throwable {
		VirtualMachineCreateRequest request = new VirtualMachineCreateRequest();
		request.setCloud(cloudplatformId);
		request = (VirtualMachineCreateRequest) TccCloudPlatUitls.setRequestParam(request);
		VirtualMachineCreateResponse response = new VirtualMachineCreateResponse();
		try {
			request.setVmName(serverName);
			request.setZone(zoneUuid);
			request.setImageId(imageId);
			request.setLoginMode(LoginMode.PASSWD);
			request.setLoginPasswd("1qaz@WSX");
			request.setCpu(QingcloudServiceImpl.translateCpu(cpu));
			request.setMemory(QingcloudServiceImpl.translateRam(ramMb));
			request.setInstanceClass("1".equals(instanceClass)?InstanceClass.HIGHPERFORMANCE:InstanceClass.NORMALPERFORMANCE);
			response = (VirtualMachineCreateResponse) TccCloudPlatUitls.getQingCloudAdapte().execute(request);
		} catch (Throwable e) {
			logger.info("创建qingcloud虚拟机出错。" + e.getMessage());
		}

		if (response == null || response.getVmIds() == null){
			return null;
		}
		if(!ResponseParameter.SUCCESS.equals(response.getSuccess())){
			logger.info("创建qingcloud虚拟机出错。" + response.getError());
		}
		boolean isActive = false;
		int tryTimes = 1;
		VirtualMachinePojo virtualMachine = null;
		do{
			VirtualMachineRetrieveRequest retrieveRequest = new VirtualMachineRetrieveRequest();
			retrieveRequest.setCloud(cloudplatformId);
			retrieveRequest = (VirtualMachineRetrieveRequest)TccCloudPlatUitls.setRequestParam(retrieveRequest);
			retrieveRequest.setVmId(response.getVmIds());
			retrieveRequest.setZone(zoneUuid);
			VirtualMachineRetrieveResponse retrieveResponse = (VirtualMachineRetrieveResponse) TccCloudPlatUitls.getQingCloudAdapte().execute(retrieveRequest);
			if(retrieveResponse == null || retrieveResponse.getVirtualMachine()==null){
				return null;
			}
			virtualMachine = retrieveResponse.getVirtualMachine().get(0);
			isActive = VirtualMachineStatusEnum.RUNNING.getValue().equals(virtualMachine.getStatus());
			tryTimes++;
			if(!isActive){
				Thread.sleep(2000);
			}
		}while(!isActive&&tryTimes<=10);
		return virtualMachine;
	}
	@Override
	public ServersPojo huaWeiOpenStackBoot(String cloudplatformId, String serverName, 
			String flavorId, String imageId, String networkId, String zoneName)throws Exception {
		this.logger.info("-----------------创建虚拟机的参数:");
		this.logger.info("-----------------资源池:" + cloudplatformId);
		this.logger.info("-----------------虚拟机名称:" + serverName);
		this.logger.info("-----------------计算方案:" + flavorId);
		this.logger.info("-----------------镜像:" + imageId);
		this.logger.info("-----------------网络:" + networkId);
		this.logger.info("-----------------区域:" + zoneName);
		List<String> networks = new ArrayList<String>();
		networks.add(networkId);
		ServersCreateRequest request = new ServersCreateRequest();
		request.setCloud(cloudplatformId);
		request = (ServersCreateRequest) TccCloudPlatUitls.setRequestParam(request);
		ServersCreateResponse response = new ServersCreateResponse();
		try {
			request.setZoneName(zoneName);
			request.setName(serverName);
			request.setFlavorId(flavorId);
			request.setImageId(imageId);
			request.setNetworks(networks);
			response = (ServersCreateResponse) TccCloudPlatUitls.getHuaWeiOpenStackAdapte().execute(request);
			if (response != null && !ResponseParameter.SUCCESSSTATUS.equalsIgnoreCase(response.getStatus())) {
				logger.info("创建华为openstack虚拟机出错,response状态:" + response.getStatus());
			}
		} catch (Throwable e) {
			logger.info("创建华为openstack虚拟机出错。" + e.getMessage());
		}

		if (response == null || response.getServersPojo() == null
				|| !ResponseParameter.SUCCESSSTATUS.equalsIgnoreCase(response.getStatus()))
			return null;

		if (null == response.getServersPojo().getId()) {
			return null;
		}
		ServersQueryRequest serversQueryRequest = new ServersQueryRequest();
		serversQueryRequest.setCloud(cloudplatformId);
		serversQueryRequest.setServerId(response.getServersPojo().getId());
		serversQueryRequest = (ServersQueryRequest) TccCloudPlatUitls.setRequestParam(serversQueryRequest);
		ServersQueryResponse serversQueryResponse = null;
		try {
			serversQueryResponse = (ServersQueryResponse) TccCloudPlatUitls
					.getHuaWeiOpenStackAdapte().execute(serversQueryRequest);
		} catch (Throwable e) {
			logger.info("------------------虚拟机查询失败.", e);
		}
		if (null == serversQueryResponse || ResponseParameter.FAILED.equals(serversQueryResponse.getSuccess())) {
			logger.info("------------------虚拟机查询失败.");
			return null;
		}
		ServersPojo serverPojo = serversQueryResponse.getServersPojo();
		String adminPass = response.getServersPojo().getAdminPass();
		serverPojo.setAdminPass(adminPass);
		return serverPojo;
	}

	/**
	 * Description:取得虚拟机 Date:2015年8月26日 下午2:48:54
	 * 
	 * @param cloudplatformId
	 * @param serverId
	 * @return
	 * @return ServerPojo
	 */
	private ServerPojo getServerPojo(String cloudplatformId, String serverId) {
		ServerRequest request = new ServerRequest();
		request.setCloud(cloudplatformId);
		request = (ServerRequest) TccCloudPlatUitls.setRequestParam(request);
		ServerResponse response = new ServerResponse();
		try {
			request.setId(serverId);
			response = (ServerResponse) TccCloudPlatUitls.getOpenStackAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("获取openstack虚拟机出错。" + e.getMessage());
		}
		if (response == null || response.getServerPojo() == null)
			return null;
		return response.getServerPojo();
	}

	/**
	 * Description:创建云主机 Date:2015年8月20日 下午4:35:29
	 * 
	 * @param cloudplatformId
	 * @param name
	 * @param ram
	 * @param vcpus
	 * @return
	 * @return String
	 */
	@Override
	public String createFlavor(String cloudplatformId, String name, Integer ram, Integer vcpus, Integer storage) {
		FlavorCreateRequest request = new FlavorCreateRequest();
		request.setCloud(cloudplatformId);
		request = (FlavorCreateRequest) TccCloudPlatUitls.setRequestParam(request);
		FlavorCreateResponse response = new FlavorCreateResponse();
		try {
			request.setName(name);
			request.setRam(ram);
			request.setVcpus(vcpus);
			if(null != storage){				
				 request.setDisk(storage);
			}else{
				request.setDisk(0);				
			}
			response = (FlavorCreateResponse) TccCloudPlatUitls
					.getOpenStackAdapte().execute(request);
		} catch (Throwable e) {
			logger.info("创建openstack云主机出错。" + e.getMessage());
		}
		if (response == null || response.getFlavorPojo() == null) {
			return null;
		}
		return response.getFlavorPojo().getId();
	}

	/**
	 * Description:创建云主机 Date:2016年6月2日19:55:27
	 * 
	 * @param cloudplatformId
	 * @param name
	 * @param ram
	 * @param vcpus
	 * @param resourceType 资源类型0:物理机	1:虚拟机
	 * @return String
	 */
	@Override
	public String createHuaWeiFlavor(String cloudplatformId, String name, 
			Integer ram, Integer vcpus, Long disk, String resourceType, String diskType) {
		FlavorsCreateRequest request = new FlavorsCreateRequest();
		request.setCloud(cloudplatformId);
		if(null == diskType || !"本地".equals(diskType)){			
			request.setDiskType(ParamUtils.SHAREDISK);
		}else{			
			request.setDiskType(ParamUtils.LOCALDISK);
		}
		request = (FlavorsCreateRequest) TccCloudPlatUitls.setRequestParam(request);
		FlavorsCreateResponse response = new FlavorsCreateResponse();
		FlavorsPojo flavorsPojo = new FlavorsPojo();
		try {
			request.setName(vcpus + "C" + ram/1024 + "G_" + UUID.randomUUID());
			if("0".equals(resourceType)){	//物理机
				FlavorsExtraSpecsPojo extraPojo = new FlavorsExtraSpecsPojo();
				extraPojo.setHypervisor_type(ParamUtils.IRONIC);	//该字段用来标识是物理机
				request.setFlavorsExtraSpecsPojo(extraPojo);
				request.setName(vcpus + "C" + ram/1024 + "G_Ironic_" + UUID.randomUUID());
			}
			request.setRam((long) ram);
			request.setVcpus(vcpus);
			if(null == disk){				
				request.setDisk(0L);
			}else{
				request.setDisk(disk);				
			}
			response = (FlavorsCreateResponse) TccCloudPlatUitls.getHuaWeiOpenStackAdapte().execute(request);
		} catch (Throwable e) {
			logger.info("创建openstack云主机出错。" + e.getMessage());
		}
		if (response != null && ResponseParameter.SUCCESSSTATUS.equals(response.getStatus())) {
			flavorsPojo = response.getFlavorsPojo();
		} else {
			return null;
		}
		return flavorsPojo.getId();
	}

	public HostMonitorAlarmApiService getZabbixApiService() {
		return zabbixApiService;
	}

	public void setZabbixApiService(HostMonitorAlarmApiService zabbixApiService) {
		this.zabbixApiService = zabbixApiService;
	}

	public IsoCaseService getIsoCaseService() {
		return isoCaseService;
	}

	public void setIsoCaseService(IsoCaseService isoCaseService) {
		this.isoCaseService = isoCaseService;
	}

	public boolean destroyCS(Long applyResourceId) throws Exception, Throwable {
		TccApplyedHostResource applyedHostResource = vmAssistService
				.getApplyedHostResource(applyResourceId);
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		logService.saveOperLog("成功", loginUserInfo.getEmpName()
				+ BusinessEnvironment.OPER_TYPE_DESTROY_VM
				+ BusinessEnvironment.OPER_RESULT_IN_PROCESS, applyedHostResource, 2);
		boolean isOK = false;
		// 删除快照
		boolean delSnapshotFalg = deletVmSnapshotByvmId(
				String.valueOf(applyResourceId), applyedHostResource.getUuid());
		if (delSnapshotFalg) {
			Long zoneId = Long.valueOf(applyedHostResource.getZoneId());
			TccCloudDatacenter zone = (TccCloudDatacenter) commonDao.get(
					TccCloudDatacenter.class, zoneId);
			String platformId = StringUtil.convertToString(zone.getCloudplatform().getCloudplatformId());
			// 卸载云磁盘
			List<String> volumeIdList = detachCSVmVolumesByVirtualMachineId(
					platformId, applyedHostResource.getUuid());
			if (volumeIdList != null) {
				// 删除磁盘
				Boolean delVolumesFlag = delCSVmVolumesByVirtualMachineId(platformId, volumeIdList);
				if (!delVolumesFlag) {
					logger.warn("删除卷轴失败！卷轴UUID：" + applyedHostResource.getStorageSize() + "GB ");
					logService.saveOperLog("失败", "实施者" + loginUserInfo.getEmpName() + "删除卷"
									+ applyedHostResource.getStorageSize() + "GB " 
									+ BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
					return Boolean.FALSE;
				} else {
					// 执行销毁虚拟机
					isOK = executeDestoryCsVm(applyedHostResource, loginUserInfo, isOK, platformId);
				}
			} else {
				// 卸载磁盘卷失败
				logger.warn("卸载卷轴失败！卷轴UUID：" + applyedHostResource.getStorageSize() + "GB ");
				logService.saveOperLog("失败", "实施者" + loginUserInfo.getEmpName()
						+ "卸载卷" + applyedHostResource.getStorageSize() + "GB "
						+ BusinessEnvironment.OPER_RESULT_FAILURE, applyedHostResource, 2);
				return Boolean.FALSE;
			}
		}
		return isOK;
	}

	/**
	 * 执行销毁虚拟机方法
	 * 
	 * @param applyResourceId
	 *            虚拟机Id
	 * @param applyedHostResource
	 *            虚拟机对象
	 * @param loginUserInfo
	 *            当前登录对象
	 * @param isOK
	 *            销毁操作成功与否标志
	 * @param platformId
	 * @return
	 * @throws Throwable
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws Exception
	 */
	private boolean executeDestoryCsVm(
			TccApplyedHostResource applyedHostResource,
			LoginUserInfo loginUserInfo, boolean isOK, String platformId)
			throws Throwable, InterruptedException, ExecutionException,
			Exception {
		DestroyVirtualMachineRequest destroyVmRequest = new DestroyVirtualMachineRequest();
		destroyVmRequest.setCloud(platformId);
		destroyVmRequest.setId(applyedHostResource.getUuid());
		destroyVmRequest = (DestroyVirtualMachineRequest) TccCloudPlatUitls
				.setRequestParam(destroyVmRequest);
		DestroyVirtualMachineResponse destroyVmResponse = (DestroyVirtualMachineResponse) TccCloudPlatUitls
				.getAdapte().execute(destroyVmRequest);
		AsyncJob asyncJob = destroyVmResponse.getDestroyVirtualMachineResponse();
		String result = asynQueryVmStatus(asyncJob.getJobId(),
				this.findPlatformByVmId(applyedHostResource.getApplyResourceId().toString())
						.getCloudplatformId().toString());
		if (VmServiceImpl.SUCCESS.equalsIgnoreCase(result)) {
			isOK = true;
			if (isOK) {
				boolean falg = capacityMgmtService
						.releaseCpuMemIp(applyedHostResource);
				if (!falg) {
					logger.warn("释放IP地址失败！");
				}
				// 使监控记录无效
				vmAssistService.removeTccSysDict(applyedHostResource.getIpAddress());
				// 更改状态，使此虚拟机记录无效
				applyedHostResource.setEnableFlg(BusinessEnvironment.DISABLE_FLG);
				applyedHostResource.setRunStatusCd(BusinessEnvironment.VM_RUN_STATUS_DESTROYED);// 已销毁
				/** delete host from zabbix by Destiny **/
				try {
					boolean issuccess = zabbixApiService.deleteHost(applyedHostResource.getIpAddress());
					if (!issuccess) {
						logService.saveOperLog(BusinessEnvironment.OPER_RESULT_FAILURE,
								loginUserInfo.getEmpName() + "删除监控虚拟机失败", applyedHostResource, 2);
						this.logger.error(applyedHostResource.getIpAddress() + "删除监控虚拟机是失败！");
					} else {
						// 删除监控成功
						applyedHostResource.setMonitorFlg(BusinessEnvironment.DISABLE_FLG);
					}
				} catch (Exception ex) {
					logService.saveOperLog(
							BusinessEnvironment.OPER_RESULT_FAILURE,
							loginUserInfo.getEmpName() + "删除监控虚拟机失败",
							applyedHostResource, 2);
					this.logger.error(applyedHostResource.getIpAddress() + "删除监控虚拟机是失败！", ex);
				}

				/** end delete host from zabbix **/
				vmAssistService.updateApplyedHostResource(applyedHostResource);
				logService.saveOperLog(BusinessEnvironment.OPER_RESULT_SUCCESS,
						loginUserInfo.getEmpName() + BusinessEnvironment.OPER_TYPE_DESTROY_VM,
						applyedHostResource, 2);
			} else {
				isOK = false;
				logService.saveOperLog(BusinessEnvironment.OPER_RESULT_FAILURE, loginUserInfo.getEmpName()
								+ BusinessEnvironment.OPER_TYPE_DESTROY_VM, applyedHostResource, 2);
			}
			// 删除虚拟机的快照
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(applyedHostResource.getApplyResourceId()));
			vmSnapshotService.deleteVmSnapshot(list);
		}
		return isOK;
	}

	/**
	 * 根据云磁盘Id删除云磁盘
	 * 
	 * @param cloudPlatfromId
	 *            资源平台Id
	 * @param volumeIdList
	 *            云磁盘Id集合
	 * @return
	 */
	private Boolean delCSVmVolumesByVirtualMachineId(String cloudPlatfromId, List<String> volumeIdList) {
		Boolean flag = Boolean.TRUE;
		try {
			if (volumeIdList != null && !volumeIdList.isEmpty()) {
				// 删除失败计数器累加
				Integer failCount = 0;
				for (String volumeId : volumeIdList) {
					Boolean deleteVolumeFlag = volumeService.deleteVolumeCS(volumeId, cloudPlatfromId);
					if (!deleteVolumeFlag) {
						failCount++;
					}
				}
				if (failCount >= 1) {
					flag = Boolean.FALSE;
				}
			}
		} catch (Exception e) {
			flag = Boolean.FALSE;
			logger.error("Delete CS Volumes Exception ,errorMsg : " + e.getMessage(), e);
		} catch (Throwable t) {
			flag = Boolean.FALSE;
			logger.error("Delete CS Volumes Throwable ,errorMsg : " + t.getMessage(), t);
		}
		return flag;
	}

	/**
	 * 根据虚拟机Id 删除该虚拟机下所有快照，并删除快照表内记录
	 * 
	 * @param cloudPlatfromId
	 *            资源平台Id
	 * @param vmId
	 *            虚拟机Id
	 * @param virtualMachineId
	 *            虚拟机UUID
	 * @return
	 * @throws Throwable
	 */
	private Boolean delCSVmSnapshotByVirtualMachineId(String cloudPlatfromId,
			Long vmId, String virtualMachineId) throws Throwable {
		Boolean flag = Boolean.TRUE;
		try {
			if (StringUtils.isNotBlank(virtualMachineId)) {
				// 根据虚拟机Id，查询当前该虚拟机下所有快照列表
				ListVMSnapshotRequest listVMSnapshotRequest = new ListVMSnapshotRequest();
				listVMSnapshotRequest.setCloud(cloudPlatfromId);
				listVMSnapshotRequest.setVirtualMachineId(virtualMachineId);
				listVMSnapshotRequest = (ListVMSnapshotRequest) TccCloudPlatUitls
						.setRequestParam(listVMSnapshotRequest);
				ListVMSnapshotResponse listVMSnapshotResponse = (ListVMSnapshotResponse) TccCloudPlatUitls
						.getAdapte().execute(listVMSnapshotRequest);
				List<VMSnapshot> vmsnapshotList = listVMSnapshotResponse
						.getListVMSnapshotResponse().getVmSnapshot();
				if (vmsnapshotList != null && !vmsnapshotList.isEmpty()) {
					// 删除虚拟机下快照判断值,如果报错变成false
					// 最后如果成功删完虚拟机下所有快照返回true，物理删除本地该虚拟机下所有快照
					Boolean isDelAllVmSnapshot = Boolean.TRUE;
					for (VMSnapshot vmSnapshot : vmsnapshotList) {
						if (vmSnapshot != null) {
							DeleteVmSnapshotRequest deleteVmSnapshotRequest = new DeleteVmSnapshotRequest();
							deleteVmSnapshotRequest.setVmsnapshotid(vmSnapshot.getId());
							deleteVmSnapshotRequest.setCloud(cloudPlatfromId);
							deleteVmSnapshotRequest = (DeleteVmSnapshotRequest) TccCloudPlatUitls
									.setRequestParam(deleteVmSnapshotRequest);
							DeleteVmSnapshotResponse response = (DeleteVmSnapshotResponse) TccCloudPlatUitls
									.getAdapte().execute(deleteVmSnapshotRequest);
							AsyncJob asyncJob = response.getDeletevmsnapshotresponse();
							QueryAsyncJobResultRequest asyncRequest = new QueryAsyncJobResultRequest();
							// 查询job执行删除成功，改为true
							Boolean executeResult = Boolean.FALSE;
							while (true) {
								asyncRequest.setCloud(cloudPlatfromId);
								asyncRequest.setJobId(asyncJob.getJobId());
								asyncRequest = (QueryAsyncJobResultRequest) TccCloudPlatUitls
										.setRequestParam(asyncRequest);
								QueryAsyncJobResultResponse asyncJobResultResponse = (QueryAsyncJobResultResponse) TccCloudPlatUitls
										.getAdapte().execute(asyncRequest);
								if (asyncJobResultResponse.getQueryAsyncJobResultResponse()
										.getJobStatus().equalsIgnoreCase("1")) {
									executeResult = Boolean.TRUE;
									break;
								} else if (asyncJobResultResponse.getQueryAsyncJobResultResponse()
										.getJobStatus().equalsIgnoreCase("2")) {
									isDelAllVmSnapshot = Boolean.FALSE;
									break;
								}
								Thread.sleep(1000 * 2);
							}
							// 成功删除快照，同步删除本地该虚拟对应快照
							if (executeResult) {
								this.commonDao.executeUpdateWithNativeSQL("UPDATE T_CC_SNAPSHOT_HISTORY SET "
												+ " ENABLE_FLG = '0' WHERE SNAPSHOT_UUID='" + vmSnapshot.getId() + "'");
							}
						}
					}
					// 成功删除所有快照,删除本地库下该虚拟机下所有快照
					if (isDelAllVmSnapshot) {
						String sql = "UPDATE T_CC_SNAPSHOT_HISTORY SET ENABLE_FLG = '0' WHERE VM_ID=" + vmId;
						this.commonDao.executeUpdateWithNativeSQL(sql);
					}
				} else {
					String sql = "UPDATE T_CC_SNAPSHOT_HISTORY SET ENABLE_FLG = '0' WHERE VM_ID=" + vmId;
					// 快照可能由于手动在资源平台删除，故查不到快照也把历史快照做逻辑删除
					this.commonDao.executeUpdateWithNativeSQL(sql);
				}
			}
		} catch (Exception e) {
			flag = Boolean.FALSE;
			logger.error("Delete CS VmSnapshot Exception by VirtualMachineId is "
							+ virtualMachineId + ",errorMsg : " + e.getMessage(), e);
			throw new Exception(e);
		} catch (Throwable t) {
			flag = Boolean.FALSE;
			logger.error("Delete CS VmSnapshot Throwable by VirtualMachineId is "
							+ virtualMachineId + ",errorMsg : " + t.getMessage(), t);
			throw new Throwable(t);
		}
		return flag;
	}

	/**
	 * 卸载虚拟机上挂载磁盘
	 * 
	 * @param cloudPlatfromId
	 * @param virtualMachineId
	 * @return 返回磁盘卷Id
	 */
	private List<String> detachCSVmVolumesByVirtualMachineId(
			String cloudPlatfromId, String virtualMachineId) {
		List<String> volumeIdList = new ArrayList<String>();
		try {
			if (StringUtils.isNotBlank(virtualMachineId)) {
				ListVolumesRequest listVolumesRequest = new ListVolumesRequest();
				listVolumesRequest.setCloud(cloudPlatfromId);
				listVolumesRequest.setVirtualMachineId(virtualMachineId);
				listVolumesRequest = (ListVolumesRequest) TccCloudPlatUitls
						.setRequestParam(listVolumesRequest);
				ListVolumesResponse listVolumesResponse = (ListVolumesResponse) TccCloudPlatUitls
						.getAdapte().execute(listVolumesRequest);
				List<Volume> volumes = listVolumesResponse.getListVolumesResponse().getVolume();
				if (volumes != null && !volumes.isEmpty()) {
					for (Volume volume : volumes) {
						if (volume != null) {
							Boolean detachVolumeFlag = volumeService.detachVolumeCS(null, volume.getId(), virtualMachineId);
							if (detachVolumeFlag) {
								volumeIdList.add(volume.getId());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			volumeIdList = null;
			logger.error("Query&Detach CS Volumes Exception by virtualMachineId is "
							+ virtualMachineId + ",errorMsg : " + e.getMessage(), e);
		} catch (Throwable t) {
			volumeIdList = null;
			logger.error("Query&Detach CS Volumes Throwable by virtualMachineId is "
							+ virtualMachineId + ",errorMsg : " + t.getMessage(), t);
		}
		return volumeIdList;
	}

	/**
	 * 根据虚拟机的ID查询快照，有快照就删除快照，没有返回true
	 */
	@SuppressWarnings("unchecked")
	public boolean deletVmSnapshotByvmId(String vmId, String vmUuid) {
		boolean result = true;
		LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
		String sql = "select tsh.snapshot_uuid from t_cc_snapshot_history tsh where  tsh.enable_flg=1 and  tsh.vm_id=? ";
		List<Object> list = this.commonDao.findBySql(sql, new Object[] { vmId }, -1, -1);
		// 根据虚拟机ID查询快照，若有快照删除快照
		if (null != list && list.size() > 0) {
			List<String> snapshotUUId = new ArrayList<String>();
			for (Object object : list) {
				if (null != object) {
					snapshotUUId.add(object.toString());
				}
			}
			try {
				TccApplyedHostResource applyedHostResource = (TccApplyedHostResource) commonDao
						.get(TccApplyedHostResource.class, Long.valueOf(vmId));
				TccCloudDatacenter dc = (TccCloudDatacenter) commonDao.get(TccCloudDatacenter.class,
						Long.valueOf(applyedHostResource.getZoneId()));
				TccCloudplatform tcccloudplatform = dc.getCloudplatform();
				if (BusinessEnvironment.CLOUDPLANTFORM_VCENTER.equals(tcccloudplatform.getCloudplatformType())) {
					for (String snapshotUuid : snapshotUUId) {
						DeleteSnapShotByUuidRequest deleteSnapshotRequest = new DeleteSnapShotByUuidRequest();
						deleteSnapshotRequest.setCloud(String.valueOf(tcccloudplatform
										.getCloudplatformId()));
						deleteSnapshotRequest.setSnapShotuuid(snapshotUuid);
						deleteSnapshotRequest = (DeleteSnapShotByUuidRequest) TccCloudPlatUitls
								.setRequestParam(deleteSnapshotRequest);
						TccCloudPlatUitls.getVmwareAdapte().execute(deleteSnapshotRequest);
						sql = "delete from T_CC_SNAPSHOT_HISTORY  where snapshot_uuid='" + snapshotUuid.trim() + "'";
						this.commonDao.executeUpdateWithNativeSQL(sql);
					}
				} else if (BusinessEnvironment.CLOUDPLANTFORM_CLOUDSTACK.equals(tcccloudplatform.getCloudplatformType())) {
					result = delCSVmSnapshotByVirtualMachineId(String.valueOf(tcccloudplatform.getCloudplatformId()),
							Long.parseLong(vmId), vmUuid);
				} else if (BusinessEnvironment.CLOUDPLANTFORM_OPENSTACK.equals(tcccloudplatform.getCloudplatformType())) {
					// os删除快照
					for (String snapshotUuid : snapshotUUId) {
						deleteOpenStackSnapshot(tcccloudplatform.getCloudplatformId() + "", snapshotUuid);
					}
				} else if (BusinessEnvironment.CLOUDPLANTFORM_QINGCLOUD.equals(tcccloudplatform.getCloudplatformType())) {
					// 青云删除快照
					TccCloudDatacenter zone = (TccCloudDatacenter) commonDao.get(TccCloudDatacenter.class, applyedHostResource.getZoneId());
					deleteQingCloudSnapshot(tcccloudplatform.getCloudplatformId() + "", snapshotUUId,zone.getUuid());
				} else if (BusinessEnvironment.CLOUDPLANTFORM_ALIYUN.equals(tcccloudplatform.getCloudplatformType())) {
					//TODO 阿里云
				} else if (BusinessEnvironment.CLOUDPLANTFORM_TENCENT.equals(tcccloudplatform.getCloudplatformType())) {
					//TODO 腾讯云
				}

				logService.saveOperLog(BusinessEnvironment.OPER_RESULT_SUCCESS,
						loginUserInfo.getEmpName() + "删除虚拟机快照成功", loginUserInfo, 2);
			} catch (Exception e) {
				logger.error("删除快照失败", e);
				try {
					logService.saveOperLog(
							BusinessEnvironment.OPER_RESULT_FAILURE,
							loginUserInfo.getEmpName() + "删除虚拟机快照失败", loginUserInfo, 2);
				} catch (Exception e1) {
					logger.error("删除快照失败", e1);
				}
				result = false;
			} catch (Throwable e) {
				logger.error("删除快照失败", e);
				result = false;
			}
		}
		return result;
	}

	/**
	 * 
	 * Description:删除os快照 Date:2015年9月18日 下午1:49:31
	 * 
	 * @param cloudplatformId
	 * @param serverSnapshotId
	 * @return void
	 */
	private void deleteOpenStackSnapshot(String cloudplatformId, String serverSnapshotId) {
		// 查询磁盘快照
		List<TccOpenStackVolumeSnapshot> volumeSnapshotList = openStackServerService
				.getVolumeSnapshotByServerSnapshotId(serverSnapshotId);
		// 删除快照镜像
		openStackServerService.deleteSnapshotImage(cloudplatformId, serverSnapshotId);
		openStackServerService.checkServerSnapshotDeleteSuccess(cloudplatformId, serverSnapshotId);
		// 删除磁盘快照
		for (TccOpenStackVolumeSnapshot volumeSnapshot : volumeSnapshotList) {
			String volumeSnapshotId = volumeSnapshot.getVolumeSnapshotUuid();
			openStackServerService.deleteVolumeSnapshot(cloudplatformId, volumeSnapshotId);
			openStackServerService.checkVolumeSnapshotDeleteSuccess(cloudplatformId, volumeSnapshotId);
			openStackServerService.deleteLocalVolumeSnapshot(volumeSnapshotId);
		}
	}
	/**
	 * 
	 * Description:删除青云快照 
	 * 
	 * @param cloudplatformId
	 * @param serverSnapshotId
	 * @return void
	 */
	private void deleteQingCloudSnapshot(String cloudplatformId, List<String> serverSnapshotIds,String zoneUuid) throws Exception{
		// 查询磁盘快照	TODO
		// 删除快照镜像
		try {
			qingcloudService.deleteSnapshot(serverSnapshotIds,cloudplatformId, zoneUuid);
		} catch (Exception e) {
			throw new RuntimeException("删除青云快照失败，"+e.getMessage());
		}
		// 删除磁盘快照	TODO
	}
	public boolean checkupConfig(String assId, String zoneId, String clusterId, String cloudplatformId) {
		TccConfigAssApplycase ass = configAssApplyCaseService.getConfigAssApplyCase(Long.parseLong(assId));
		try {
			// 获取主机
			TccPhysiscResourceInfo physiscResourceInfo = getHostBycluster(clusterId);
			// 获取区域
			TccCloudDatacenter dc = (TccCloudDatacenter) commonDao.get(TccCloudDatacenter.class, Long.parseLong(zoneId));
			// 获取模版并验证模版是否可用
			TccTemplateCase template = isoCaseService.queryTemplateByHostInfo(
					ass.getIsoId(), dc.getUuid(), physiscResourceInfo.getVirtualFlag());
			if (template == null
					|| !validateTemplate(cloudplatformId, template.getCsTemplateOfferingId())) {
				return false;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean addVCenterISO(String vmId, String isoPath) throws Exception {
		Boolean flag = false;
		if(null == vmId){
			flag = true;
			return flag;
		}
		TccCloudplatform platform = this.findPlatformByVmId(vmId);
		String cloudplatformId = platform.getCloudplatformId().toString();
		TccApplyedHostinfo resource = (TccApplyedHostinfo) this.commonDao.get(TccApplyedHostinfo.class, Long.parseLong(vmId));
		String vmName = resource.getHostNane();
		AddCdromRequest request = new AddCdromRequest();
		request.setCloud(cloudplatformId);
		request.setIsoPath(isoPath);
		request.setName(vmName);
		request = (AddCdromRequest) TccCloudPlatUitls.setRequestParam(request);
		AddCdromResponse response = new AddCdromResponse();
		try {
			response = (AddCdromResponse) TccCloudPlatUitls.getVmwareAdapte().execute(request);
		} catch (Throwable e) {
			logger.info("--------------虚拟机挂载cdrom失败!", e);
			flag = true;
			return flag;
		}
		if(null == response || ResponseParameter.FAILED.equals(response.getSuccess())){			
			flag = true;
			return flag;
		}
		return flag;
	}
	
	@Override
	public boolean removeVCenterISO(String vmId) throws Exception {
		Boolean flag = false;
		if(null == vmId){
			flag = true;
			return flag;
		}
		TccCloudplatform platform = this.findPlatformByVmId(vmId); 
		String cloudplatformId = platform.getCloudplatformId().toString();
		TccApplyedHostinfo resource = (TccApplyedHostinfo) this.commonDao.get(TccApplyedHostinfo.class, Long.parseLong(vmId));
		String vmName = resource.getHostNane();
		RemoveCdromRequest request = new RemoveCdromRequest();
		request.setCloud(cloudplatformId);
		request.setName(vmName);
		request = (RemoveCdromRequest) TccCloudPlatUitls.setRequestParam(request);
		RemoveCdromResponse response = new RemoveCdromResponse();
		try {
			response = (RemoveCdromResponse) TccCloudPlatUitls.getVmwareAdapte().execute(request);
		} catch (Throwable e) {
			logger.info("--------------虚拟机卸载cdrom失败!", e);
			flag = true;
			return flag;
		}
		if(null == response || ResponseParameter.FAILED.equals(response.getSuccess())){			
			flag = true;
			return flag;
		}
		return flag;
	}
	
	/**
	 * gjh  新增
	 * 封装云主机账户信息
	 * @return
	 */
	public TccResourceAccount cloudHostingAccount(SrtManageVO srtManageVO,TccApplyedHostinfo applyedHostinfo){
		 TccConfigAssApplycase applycase =  (TccConfigAssApplycase) this.commonDao
					.get(TccConfigAssApplycase.class, Long.parseLong(srtManageVO.getAssId()));
		TccResourceAccount resourceAccount = new TccResourceAccount();
		try {
			resourceAccount.setAccount(StringUtils.isNotBlank(applycase.getOsLoginName()) 
					? URLDecoder.decode(applycase.getOsLoginName(), "UTF-8"):null);
			resourceAccount.setPassword(StringUtils.isNotBlank(applycase.getOsLoginPassword()) 
					? URLDecoder.decode(applycase.getOsLoginPassword(), "UTF-8"):null);
			resourceAccount.setRoute(StringUtils.isNotBlank(applycase.getOsLoginPath()) 
					? URLDecoder.decode(applycase.getOsLoginPath(), "UTF-8"):null);
			resourceAccount.setTccApplyedHostinfo(applyedHostinfo);
		} catch (UnsupportedEncodingException e) {
			logger.error( "Add fail decode error：" + e.getMessage());
		}
		
		if(applycase.getOsloginDate()!=null){
			resourceAccount.setExpirationTime(DateUtil.stringToDate(new SimpleDateFormat("yyyy-MM-dd").
					format(applycase.getOsloginDate()), DateUtil.DATEFORMAT_DAY));
		}
		try{
	    	LoginUserInfo loginUserInfo = LoginUserInfoHolder.getInstance().getCurrentUser();
				resourceAccount.setEnableFlg(BusinessEnvironment.ENABLE_FLG);
				resourceAccount.setLockFlg(BusinessEnvironment.ENABLE_FLG);
				resourceAccount.setCrtDttm(new Date());
				resourceAccount.setCrtUserId(loginUserInfo.getUserPartyId());
				resourceAccount.setLastuptDttm(new Date());
				resourceAccount.setLastuptUserId(loginUserInfo.getUserPartyId());
		}catch(Exception e){
			logger.error("encapsulateResourceAccountBean error：" + e.getMessage());
		}
		
		return resourceAccount;
	}
	
	//查看安装脚本是否存在
	public boolean getFileExists(String host,String user,String password,String filePath){
		StringBuilder command = new StringBuilder();
		command.append("ls ");
		command.append(filePath);
		boolean flag  =false;
		int port = 22;
		Map<String, Object> result = JavaOperateSSHUtils.excuteLinusCommand(user, password, host, port, command.toString());
		if(Boolean.parseBoolean(result.get(JavaOperateSSHUtils.KEY_FLAG).toString())){
			String res = result.get(JavaOperateSSHUtils.KEY_RESULT).toString();
			logger.info("执行查询，执行结果正常，返回信息："+res);
			System.out.println("正常：");
			System.out.println(res);
			if(res.toUpperCase().contains("NO SUCH FILE")){
				
				flag =  true;
			}
		}else{
			String res = result.get(JavaOperateSSHUtils.KEY_ERROR_MSG).toString();
			logger.error("执行查询，执行结果异常，返回信息："+res);
		}
		
		return flag;
	}
	
	
	/**
	 * 下载脚本
	 * @param host
	 * @param user
	 * @param password
	 * @param filePath  下载
	 * @param toPath 保存目录
	 * @return
	 */
	public boolean downloadScript(String host,String user,String password,String DownPath,String toPath){
		StringBuilder command = new StringBuilder();
		command.append("scp -r").append(" ");
		command.append(DownPath).append(" ");
		command.append(toPath);
		boolean flag  =false;
		int port = 22;
		Map<String, Object> result = 
				JavaOperateSSHUtils.excuteLinusCommand(user, password, host, port, command.toString());
		if(Boolean.parseBoolean(result.get(JavaOperateSSHUtils.KEY_FLAG).toString())){
			String res = result.get(JavaOperateSSHUtils.KEY_RESULT).toString();
			logger.info("执行脚本下载，执行结果正常，返回信息："+res);
			System.out.println("正常：");
			System.out.println(res);
			flag = true;
		}else{
			String res = result.get(JavaOperateSSHUtils.KEY_ERROR_MSG).toString();
			logger.error("执行脚本下载，执行结果异常，返回信息："+res);
		}
		
		return flag;
	}
	
	public NetworkService getNetworkService() {
		return networkService;
	}

	public void setNetworkService(NetworkService networkService) {
		this.networkService = networkService;
	}

	public OpenStackServerService getOpenStackServerService() {
		return openStackServerService;
	}

	public void setOpenStackServerService(
			OpenStackServerService openStackServerService) {
		this.openStackServerService = openStackServerService;
	}

	public StrategyCaseService getStrategyCaseService() {
		return strategyCaseService;
	}

	public void setStrategyCaseService(StrategyCaseService strategyCaseService) {
		this.strategyCaseService = strategyCaseService;
	}

	public ResrcReleaseService getResrcReleaseService() {
		return resrcReleaseService;
	}

	public void setResrcReleaseService(ResrcReleaseService resrcReleaseService) {
		this.resrcReleaseService = resrcReleaseService;
	}

	@Override
	public Map<String, String> cloudScaleoutVcenter(Long vmId, String vmName,
			LoginUserInfo loginUserInfo,String ipAddress) throws Exception, Throwable {
		Map<String, String> errorMsg = new HashMap<String, String>();
		TccApplyedHostResource host = (TccApplyedHostResource) this.commonDao
				.get(TccApplyedHostResource.class, vmId);
		if (EnvironmentConstant.DISENABLE.equals(host.getEnableFlg())) {
			errorMsg.put("FAILURE", ResponseParameter.FAILED);
			errorMsg.put("msg", "服务器已被销毁，无法克隆！请刷新后操作！");
			return errorMsg;
		}
		//根据vmId获取相关信息
		CloneResVo cloneResVo = this.findVmInfoByVmId(StringUtil.convertToString(vmId));
		TccTemplateCase templateCase = (TccTemplateCase) this.commonDao.get(
				TccTemplateCase.class, Long.valueOf(host.getTemplateId()));

		// 根据安装界面传来的集群表ID，选择相应的集群记录 然后根据此记录，查找相应的物理主机信息 然后让虚拟机安装在此物理主机上
		Long clusterId = StringUtil.StringToLong(cloneResVo.getClusterId());
		TccClusterConfig clusterConfig = vmAssistService.getClusterConfig(clusterId);
		CloneVitualMachineRequest cloneRequest = new CloneVitualMachineRequest();
		TccCloudplatform tcccloudplatform = this.findPlatformByClusterId(clusterConfig.getId() + "");
		cloneRequest.setCloud(tcccloudplatform.getCloudplatformId() + "");
		
		try {
			cloneRequest.setName(vmName);
			// ***** by zhachaoy i*****
			// 设置虚拟机克隆参数
			VirtualMachineCloneSpec virtualMachineCloneSpec = new VirtualMachineCloneSpec();
			// 配置新克隆虚拟机物理信息
			VirtualMachineRelocateSpec virtualMachineRelocateSpec = new VirtualMachineRelocateSpec();
			// 设置克隆后的虚拟机存储于那个集群
			// 查询集群
			ListClusterComputeResourcesRequest ListClusterComputeResourcesRequestT = new ListClusterComputeResourcesRequest();
			ListClusterComputeResourcesRequestT.setCloud(tcccloudplatform.getCloudplatformId() + "");
			ListClusterComputeResourcesRequestT.setName(clusterConfig.getClusterName());
			ListClusterComputeResourcesRequestT = (ListClusterComputeResourcesRequest) TccCloudPlatUitls
					.setRequestParam(ListClusterComputeResourcesRequestT);
			ListClusterComputeResourcesResponse ListClusterComputeResourcesResponseT = 
					(ListClusterComputeResourcesResponse) TccCloudPlatUitls.getVmwareAdapte().execute(
							ListClusterComputeResourcesRequestT);
			ClusterComputeResource ClusterComputeResourceT = ListClusterComputeResourcesResponseT
					.getListClusterComputeResourcesResponse().getClusterComputeResource().get(0);
			try {
				// 设置池
				virtualMachineRelocateSpec.setPool(ClusterComputeResourceT.getResourcePool().getMOR());
			} catch (Exception e) {
				logger.error("未发现集群" + clusterConfig.getClusterName());
				errorMsg.put("FAILURE", ResponseParameter.FAILED);
				errorMsg.put("msg", "未发现集群" + clusterConfig.getClusterName());
				return errorMsg;
			}

			// 获取主机
			TccPhysiscResourceInfo physiscResourceInfo = getHostBycluster(clusterId.toString());
			// 获取区域
			TccCloudDatacenter dc = (TccCloudDatacenter) commonDao.get(
					TccCloudDatacenter.class, Long.parseLong(clusterConfig.getZoneId()));
			
			// 获取模版并验证模版是否可用
			TccTemplateCase template = isoCaseService.queryTemplateByHostInfo(
					cloneResVo.getIsoId(), dc.getUuid(), physiscResourceInfo.getVirtualFlag());
			// 查询模版
			GetVirtualMachineRequest GetVirtualMachineRequestT = new GetVirtualMachineRequest();
			GetVirtualMachineRequestT.setCloud(dc.getCloudplatform().getCloudplatformId() + "");
			if (template != null && StringUtils.isNotBlank(template.getTemplateName())) {
				GetVirtualMachineRequestT.setVmName(template.getTemplateName());
			} else {
				// 存量资源无法找到当初创建它的模板，导致无法通过模板克隆
				logger.error("存量资源无法找到当初创建它的模板，导致无法通过模板克隆!");
				errorMsg.put("FAILURE", ResponseParameter.FAILED);
				errorMsg.put("msg", "没有模板，该云主机暂时不支持克隆，请联系管理员！");
				return errorMsg;
			}

			GetVirtualMachineRequestT = (GetVirtualMachineRequest) TccCloudPlatUitls
					.setRequestParam(GetVirtualMachineRequestT);
			GetVirtualMachineResponse GetVirtualMachineResponseT = (GetVirtualMachineResponse) TccCloudPlatUitls
					.getVmwareAdapte().execute(GetVirtualMachineRequestT);

			// 查询集群里合适的存储器
			Datastore datastoreT = null;
			// 由模板来查找存储
			for (Datastore datasotre : GetVirtualMachineResponseT.getVitualMachine().getDatastores()) {
				if (null == datastoreT) {
					datastoreT = datasotre;
				} else {
					try {
						if (datasotre.getSummary().accessible == true
								&& "VMFS".equalsIgnoreCase(datasotre.getSummary().getType())) {
							if (datasotre.getInfo().getFreeSpace() > datastoreT.getInfo().getFreeSpace()) {
								datastoreT = datasotre;
							}
						}
					} catch (Exception e) {
						logger.warn("存在未知datastore:" + datasotre.getName());
					}
				}
			}
			// 剩余存储默认使用的单位是B
			double freeStore = Double.parseDouble(Long.toString(datastoreT.getInfo().getFreeSpace())) / 1024 / 1024 / 1024;
			if (MIN_STORE_FREE > freeStore) {
				String error = "存储容量不足，请联系管理员！";
				logger.error(error);
				errorMsg.put("FAILURE", ResponseParameter.FAILED);
				errorMsg.put("msg", error);
				return errorMsg;
			}
			try {
				// 设置存储器
				virtualMachineRelocateSpec.setDatastore(datastoreT.getMOR());// 设置克隆,文件存放地点
			} catch (Exception e) {
				String error = "在指定集群" + clusterConfig.getClusterName() + "中，未发现指定存储";
				logger.error(error);
				errorMsg.put("FAILURE", ResponseParameter.FAILED);
				errorMsg.put("msg", error);
				return errorMsg;
			}

			// 查询集群里合适的物理机
			HostSystem hostSystemT = null;
			for (HostSystem hostSystem : ClusterComputeResourceT.getHosts()) {
				if (null == hostSystemT) {
					for (Datastore store : hostSystem.getDatastores()) {
						if (datastoreT.getMOR().getVal().equals(store.getMOR().getVal())) {
							hostSystemT = hostSystem;
						}
					}
				} else {
					try {
						for (Datastore store : hostSystem.getDatastores()) {
							if (datastoreT.getMOR().getVal().equals(store.getMOR().getVal())) {
								if (hostSystem.getVms().length < hostSystemT.getVms().length) {
									hostSystemT = hostSystem;
								}
							}
						}
					} catch (Exception e) {
						logger.warn("存在未知host:" + hostSystem.getName());
					}
				}
			}

			try {
				// 设置物理机
				virtualMachineRelocateSpec.setHost(hostSystemT.getMOR());// 必须设置,否则无法克隆成功
			} catch (Exception e) {
				logger.error("在指定集群" + clusterConfig.getClusterName() + "中，未发现物理机");
				errorMsg.put("FAILURE", ResponseParameter.FAILED);
				errorMsg.put("msg", "在指定集群" + clusterConfig.getClusterName() + "中，未发现物理机");
				return errorMsg;
			}
			// 配置新克隆虚拟机配置信息
			VirtualMachineConfigSpec virtualMachineConfigSpec = new VirtualMachineConfigSpec();
			// 设置虚拟机的memory
			virtualMachineConfigSpec.setMemoryMB(cloneResVo.getRamSize().longValue());
			// 设置虚拟机的cpu
			virtualMachineConfigSpec.setNumCPUs(cloneResVo.getCpuSize().intValue());
			virtualMachineCloneSpec.setConfig(virtualMachineConfigSpec);

			// 配置新克隆虚拟机状态信息
			virtualMachineCloneSpec.setPowerOn(true);
			virtualMachineCloneSpec.setTemplate(false);
			cloneRequest.setVirtualMachineCloneSpec(virtualMachineCloneSpec);

			// 用户自定义IP
			Long ipConfigId = this.getIpConfigIdByIpAddress(ipAddress);
//			Long ipConfigId = getIpConfigId(vlanId, ipAddress, null);
			TccIpConfigInfo ipinfo = (TccIpConfigInfo) this.commonDao.get(TccIpConfigInfo.class, ipConfigId);
			if (template == null || GetVirtualMachineResponseT.getVitualMachine() == null) {
				String error = "此镜像下没有可用的模版!";
				logger.error(error);
				errorMsg.put("FAILURE", ResponseParameter.FAILED);
				errorMsg.put("msg", error);
				return errorMsg;
			} else {
				if (ipinfo != null && StringUtils.isNotBlank(ipinfo.getMemo())) {
					String[] cidr = ipinfo.getMemo().split(",");
					CustomizationSpec customSpec = new CustomizationSpec();
					if(template.getDbosVersionCd().contains("window")||template.getMemo().contains("window"))//判断需要安装window时使用，安装linux时不使用
					{
						CustomizationWinOptions winOptions = new CustomizationWinOptions();					
						winOptions.setChangeSID(true);
						winOptions.setDeleteAccounts(false);
						customSpec.setOptions(winOptions);					
						
						CustomizationSysprep winPrep = new CustomizationSysprep();							
						CustomizationGuiUnattended unattended=new CustomizationGuiUnattended();
				        unattended.setTimeZone(4);
				        unattended.setAutoLogon(false);
				        unattended.setAutoLogonCount(0);       
				        
				        CustomizationPassword customizationpassword = new CustomizationPassword();
				        customizationpassword.setPlainText(true);
				        customizationpassword.setValue("Cloud@123456");
				        unattended.setPassword(customizationpassword);
				        
				        winPrep.setGuiUnattended(unattended);

				        CustomizationUserData userData=new CustomizationUserData();
				        userData.setFullName("micro");
				        userData.setOrgName("micro");
				        CustomizationVirtualMachineName virtualMachineName=new CustomizationVirtualMachineName();
				        userData.setComputerName(virtualMachineName);
				        userData.setProductId("");
				        winPrep.setUserData(userData);

				        CustomizationIdentification identification=new CustomizationIdentification();
				        identification.setJoinWorkgroup("WORKGROUP");
				        winPrep.setIdentification(identification);	
				        
						customSpec.setIdentity(winPrep);
					}else{
						CustomizationLinuxOptions linuxOptions = new CustomizationLinuxOptions();
						customSpec.setOptions(linuxOptions);
						CustomizationLinuxPrep linuxPrep = new CustomizationLinuxPrep();
						linuxPrep.setDomain("default");
						CustomizationFixedName fixedName = new CustomizationFixedName();
						fixedName.setName(vmName);// hostname
						linuxPrep.setHostName(fixedName);					
						customSpec.setIdentity(linuxPrep);
					}
					CustomizationFixedIp fixedIp = new CustomizationFixedIp();
					fixedIp.setIpAddress(ipinfo.getIpAddress());// ipaddress
					CustomizationIPSettings adapter = new CustomizationIPSettings();
					adapter.setIp(fixedIp);
					if (cidr.length >= 3 && StringUtils.isNotBlank(cidr[1])) {
						if (StringUtil.checkNetworkInfoFormat(cidr[1])) {
							adapter.setGateway(new String[] { cidr[1] });
						}
					}
					if (cidr.length >= 3 && StringUtils.isNotBlank(cidr[0])) {
						if (StringUtil.checkNetworkInfoFormat(cidr[0])) {
							adapter.setSubnetMask(cidr[0]);
						}
					}
					CustomizationAdapterMapping adapterMap = new CustomizationAdapterMapping();
					adapterMap.setAdapter(adapter);
					CustomizationAdapterMapping[] nicSettingMap = new CustomizationAdapterMapping[] { adapterMap };
					customSpec.setNicSettingMap(nicSettingMap);
					CustomizationGlobalIPSettings gIP = new CustomizationGlobalIPSettings();
					if (cidr.length >= 3 && StringUtils.isNotBlank(cidr[2])) {
						if (StringUtil.checkNetworkInfoFormat(cidr[2])) {
							gIP.setDnsServerList(new String[] { cidr[2] });
						} else {
							gIP.setDnsServerList(new String[] { "114.114.114.114" });
						}
					}
//					VirtualDeviceConfigSpec[] specs = new VirtualDeviceConfigSpec[] {nicSpec};
//					virtualMachineConfigSpec.setDeviceChange(specs);
					customSpec.setGlobalIPSettings(gIP);
					virtualMachineCloneSpec.setCustomization(customSpec);
				}
			}

			// 由模板来查找存储
			for (Datastore datasotre : GetVirtualMachineResponseT.getVitualMachine().getDatastores()) {
				if (null == datastoreT) {
					datastoreT = datasotre;
				} else {
					try {
						if (datasotre.getSummary().accessible == true
								&& "VMFS".equalsIgnoreCase(datasotre.getSummary().getType())) {
							if (datasotre.getInfo().getFreeSpace() > datastoreT.getInfo().getFreeSpace()) {
								datastoreT = datasotre;
							}
						}
					} catch (Exception e) {
						logger.warn("存在未知datastore:" + datasotre.getName());
					}
				}
			}

			try {
				// 设置存储器
				virtualMachineRelocateSpec.setDatastore(datastoreT.getMOR());// 设置克隆,文件存放地点
			} catch (Exception e) {
				String error = "在指定集群" + clusterConfig.getClusterName() + "中，未发现指定存储";
				logger.error(error);
				errorMsg.put("FAILURE", ResponseParameter.FAILED);
				errorMsg.put("msg", error);
				return errorMsg;
			}

			virtualMachineCloneSpec.setLocation(virtualMachineRelocateSpec);

			// 查询数据中心
			ListDatacentersRequest ListDatacentersRequestT = new ListDatacentersRequest();
			ListDatacentersRequestT.setCloud(tcccloudplatform.getCloudplatformId() + "");
			ListDatacentersRequestT.setName(clusterConfig.getDcname());
			ListDatacentersRequestT = (ListDatacentersRequest) TccCloudPlatUitls
					.setRequestParam(ListDatacentersRequestT);
			ListDatacentersResponse ListDatacentersResponseT = (ListDatacentersResponse) TccCloudPlatUitls
					.getVmwareAdapte().execute(ListDatacentersRequestT);

			cloneRequest.setDatacenter(ListDatacentersResponseT.getListDatacentersResponse().getDatacenter().get(0));

			cloneRequest.setVirtualMachine(GetVirtualMachineResponseT.getVitualMachine());
			cloneRequest = (CloneVitualMachineRequest) TccCloudPlatUitls.setRequestParam(cloneRequest);
			CloneVitualMachineResponse cloneVmResponse = (CloneVitualMachineResponse) TccCloudPlatUitls
					.getVmwareAdapte().execute(cloneRequest);
			Boolean isOK = cloneVmResponse.isResult();
			if (isOK) {
				//修改虚拟机网络标签
				TccVlanNetwork network = (TccVlanNetwork) commonDao.get(TccVlanNetwork.class, ipinfo.getNetworkId());
				if(network != null){
					String[] networkTags =network.getNetworkTag().split("_&");
					String networkTag = networkTags[1];
					VirtualMachineUpdateNetworkRequest updateVmNetworkRequest = new VirtualMachineUpdateNetworkRequest();
					updateVmNetworkRequest.setCloud(String.valueOf(tcccloudplatform.getCloudplatformId()));
					updateVmNetworkRequest.setName(vmName);
					updateVmNetworkRequest.setAdaptername("Network adapter");
					updateVmNetworkRequest.setNetname(networkTag);
					updateVmNetworkRequest = (VirtualMachineUpdateNetworkRequest)TccCloudPlatUitls.setRequestParam(updateVmNetworkRequest);
					VirtualMachineUpdateNetworkResponse updateVmNetworkResponse = (VirtualMachineUpdateNetworkResponse)TccCloudPlatUitls.getVmwareAdapte().execute(updateVmNetworkRequest);
					if(updateVmNetworkResponse!=null && ResponseParameter.SUCCESS.equals(updateVmNetworkResponse.getSuccess())){
						logger.info("虚拟机:" + vmName + "重新配置成功");
					}else{
						logger.info("虚拟机:" + vmName + "重新配置网络标签失败，请至后台手动修改");
					}
				}
				GetVirtualMachineRequest getVmRequest = new GetVirtualMachineRequest();
				getVmRequest.setCloud(this.findPlatformByClusterId(clusterConfig.getId() + "").getCloudplatformId() + "");
				getVmRequest.setVmName(vmName);
				getVmRequest = (GetVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(getVmRequest);
				GetVirtualMachineResponse getVmResponse = (GetVirtualMachineResponse) TccCloudPlatUitls
						.getVmwareAdapte().execute(getVmRequest);

				// 设置克隆操作系统
				String vmGutestId = getVmResponse.getVitualMachine().getConfig().getGuestId();
				if (vmGutestId.contains("win")
						|| vmGutestId.contains("windows")) {
					vmGutestId = "windows";
				} else {
					vmGutestId = "linux";
				}
				String volumeOfferingId = "";// 外挂卷轴UUID7
				// 如果是SAN存储，则自动挂载外挂存储（卷轴），否则走线下由实施人员手动安装外挂存储
				
				try {
					if (cloneResVo.getStorageSize() != null
							&& !cloneResVo.getStorageSize().equals("0")
							&& cloneResVo.getStorageSize().intValue() > 0) {
						AddVirtualMachineDiskRequest addDiskRequest = new AddVirtualMachineDiskRequest();
						addDiskRequest.setCloud(this.findPlatformByClusterId(
								clusterConfig.getId() + "").getCloudplatformId() + "");
						addDiskRequest.setName(vmName);
						addDiskRequest.setDiskSize(StringUtil.convertToString(cloneResVo.getStorageSize().intValue()));
						addDiskRequest = (AddVirtualMachineDiskRequest) TccCloudPlatUitls
								.setRequestParam(addDiskRequest);
						AddVirtualMachineDiskReponse addVirtualMachineDiskReponse = (AddVirtualMachineDiskReponse) TccCloudPlatUitls
								.getVmwareAdapte().execute(addDiskRequest);
						if (addVirtualMachineDiskReponse != null
								&& "true".equalsIgnoreCase(addVirtualMachineDiskReponse.getSuccess())) {
							this.logger.info("VirtualMachine [" + vmName
									+ "] attach disk successful! Disk size is "
									+ String.valueOf((cloneResVo.getStorageSize().intValue())));
						} else {
							this.logger.error("VirtualMachine [" + vmName
									+ "] attach disk failed! Disk size is "
									+ String.valueOf((cloneResVo.getStorageSize().intValue())));
						}
					}
				} catch (Exception e) {
					logger.error("VirtualMachine [" + vmName + "] attach disk failed!", e);
				}

				// 保存服务请求主表与已分配资源代码表
				TccApplyedHostResource applyedHostResource = vmAssistService
						.mergeApplyedHostResourceForScaleout(getVmResponse.getVitualMachine(),
								volumeOfferingId,"",clusterConfig.getId().toString(),loginUserInfo,cloneResVo);
				applyedHostResource.setTemplateId(host.getTemplateId());
				applyedHostResource.setIpAddress(ipAddress);
				applyedHostResource.setHostLoginUsername(host.getHostLoginUsername());
				applyedHostResource.setHostLoginPassword(host.getHostLoginPassword());
				// 保存IP地址到虚拟机表里面
				vmAssistService.saveApplyedHostResource(applyedHostResource);
				// 更新ip的状态
				StringBuffer updateSql = new StringBuffer();
				updateSql.append("UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='克隆" + vmName
								+ "虚拟机',DISTRIBUTE_TIME=NOW() WHERE f.IP_ADDRESS ='" + ipAddress + "'");
				// 更新ip的状态
				commonDao.updateByNativeSql(updateSql.toString());
				// 剩余CPU、MEM减少
				capacityMgmtService.takingCpuRam(applyedHostResource.getTccPhysiscResourceInfo(),
						cloneResVo.getCpuSize(), cloneResVo.getRamSize());
				
				//保存工单
				Long srtId = this.existResourceService.saveTccSrt(StringUtil
						.convertToString(loginUserInfo.getUserPartyId()),cloneResVo.getEvnCode(), cloneResVo.getProjectId(), loginUserInfo, true);
				//保存配置表
				Long assId = this.existResourceService.saveTccConfigAssApplyCase(srtId,
						applyedHostResource.getApplyResourceId(),
						StringUtil.StringToLong(host.getTemplateId()), cloneResVo.getProjectId());
				boolean result = this.existResourceService.updateHostInfo(applyedHostResource.getApplyResourceId(), assId,
						cloneResVo.getEvnCode(), StringUtil.StringToLong(host.getTemplateId()),
						loginUserInfo.getUserPartyId());
				boolean saveActivity = this.existResourceService.saveActivitiTask(
						srtId,cloneResVo.getEvnCode(), loginUserInfo.getUserPartyId(),true);
				//保存与项目的关系
				boolean saveProject = this.existResourceService.saveVmToProject(
						Long.valueOf(cloneResVo.getProjectId()), applyedHostResource.getApplyResourceId());
				
				//保存存储与工单关系表
				boolean saveStorageAss = this.existResourceService.saveTccStorageApplyCaseAss(assId,srtId,applyedHostResource.getApplyResourceId(),loginUserInfo.getUserPartyId());
				
				boolean saveApplyResource = this.existResourceService.saveApplyResource(
						srtId,applyedHostResource.getApplyResourceId());
				
				TccApplyedHostResource ahr = vmAssistService
						.getApplyedHostResource(applyedHostResource.getApplyResourceId());
				
				if(vmGutestId.contains("win") || vmGutestId.contains("windows")){
					ahr.setHostLoginUsername("Administrator");
				}else{
					if(StringUtils.isNotBlank(templateCase.getUsername())){
						ahr.setHostLoginUsername(templateCase.getUsername());
					}
				}
				
				if(vmGutestId.contains("win") || vmGutestId.contains("windows")){
					ahr.setHostLoginPassword("Cloud@123456");
					ahr.setOriginalRootPassword("Cloud@123456");
				}else{
					if(StringUtils.isNotBlank(templateCase.getPasswd())){
						ahr.setHostLoginPassword(templateCase.getPasswd());
						ahr.setOriginalRootPassword(templateCase.getPasswd());
						
					}
				}
				
				ahr.setHardwareTypeCd("186");// 设置为小型机
				vmAssistService.updateApplyedHostResource(ahr);
				TccApplyedHostinfo logForhostInfo = (TccApplyedHostinfo) this.commonDao
						.load(TccApplyedHostinfo.class, applyedHostResource.getApplyResourceId());
				if (result && saveActivity && saveProject && saveApplyResource) {
					
					/** add host to monitor by Destiny : begin **/
					String hostId = String.valueOf(applyedHostResource.getApplyResourceId());
					String visibleName = vmName;
					TccOs os = this.getOsById(applyedHostResource.getOsCd());

					try {
						if (!zabbixApiService.isAddHostToMonitorByHostName(hostId)) {
						
							String currentUserId = loginUserInfo.getUserPartyId().toString();
							//当前虚拟机安装日志
							if(installLog.get(currentUserId)==null){
								installLog.put(currentUserId,new ArrayList<String>());
							}
							List<String> installLog_current = installLog.get(currentUserId);
							
							installLog_current.add("虚拟机:" + vmName + "添加Zabbix监控开始");
							
							HostMonitorBean hostinfo = new HostMonitorBean();
							hostinfo.setHostName(hostId);
							hostinfo.setVisibleName(visibleName);
							hostinfo.setIp(applyedHostResource.getIpAddress());
							hostinfo.setMonitorType(1);
							if (null != os && null != os.getOneLevelOs()) {
								hostinfo.setOsName(os.getOneLevelOs());
							} else {
								hostinfo.setOsName("");
							}
							applyedHostResource.setMonitorFlg(zabbixApiService
									.addHostToMonitor(hostinfo) ? "1" : "0");
							
							installLog_current.add("虚拟机:" + vmName + "添加Zabbix监控结束");
							
						} else {
							logger.info("主机[" + visibleName + "]已在zabbix中注册");
							applyedHostResource.setMonitorFlg("1");
						}
					} catch (Exception ex) {
						this.logger.error("添加监控失败！", ex);
					} catch (Throwable t) {
						this.logger.error("添加监控失败！", t);
					}
					commonDao.update(applyedHostResource);
					/** add host to monitor by Destiny : end **/
					
					// 加入日志
			        logService.saveOperLog("成功", "用户" + loginUserInfo.getEmpName()
			                + "vcenter横向扩容:" + logForhostInfo.getHostNane() 
			                + BusinessEnvironment.OPER_RESULT_SUCCESS, logForhostInfo, 1);
			        errorMsg.put("SUCCESS", ResponseParameter.SUCCESS);
					return errorMsg;
				} else {
					// 加入日志
			        logService.saveOperLog("失败", "用户" + loginUserInfo.getEmpName()
			                + "vcenter横向扩容:" + logForhostInfo.getHostNane() 
			                + BusinessEnvironment.OPER_RESULT_FAILURE, logForhostInfo, 1);
			        errorMsg.put("FAILURE", ResponseParameter.FAILED);
					return errorMsg;
				}
			} else {// 失败
				logService.saveOperLog("失败", "实施者" + loginUserInfo.getEmpName()
						+ "创建vcenter虚拟机" + BusinessEnvironment.OPER_RESULT_FAILURE, null, 2);
				logger.error(errorMsg);
				errorMsg.put("FAILURE", ResponseParameter.FAILED);
				errorMsg.put("msg", "克隆虚拟机失败[" + vmName + "]");
				return errorMsg;
			}
		} catch (Exception e) {
			logService.saveOperLog("失败", "实施者" + loginUserInfo.getEmpName()
					+ "创建vcenter虚拟机" + BusinessEnvironment.OPER_RESULT_FAILURE, null, 2);
			logger.error(e);
			errorMsg.put("FAILURE", ResponseParameter.FAILED);
			errorMsg.put("msg", "克隆虚拟机失败[" + vmName + "]" + e.getMessage());
			return errorMsg;
		}
		
	}

	@SuppressWarnings("rawtypes")
	private Long getIpConfigIdByIpAddress(String ipAddress) {
		Long ipConfigId = 0l;
		StringBuilder hql = new StringBuilder("from TccIpConfigInfo i where i.ipAddress = ? and enableFlg = '1'");
		List list = this.commonDao.find(hql.toString(), new Object[]{ipAddress});
		if(null != list && list.size() > 0){
			TccIpConfigInfo ipConfigInfo = (TccIpConfigInfo) list.get(0);
			ipConfigId = ipConfigInfo.getIpConfigId();
		}
		return ipConfigId;
	}

	private CloneResVo findVmInfoByVmId(String vmId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT H.APPLY_RESOURCE_ID,R.RAM_SIZE,R.STORAGE_SIZE,");
		sql.append(" T.ISO_ID,R.CPU_CORE_COUNT,CLU.ID CLUSTER_ID,PA.PROJECT_ID,SRT.USEAGE_ENV ENV_CODE,H.CLOSE_DATE ");
		sql.append(" FROM T_CC_APPLYED_HOSTINFO H");
		sql.append(" LEFT JOIN T_CC_APPLYED_HOST_RESOURCE R ON R.APPLY_RESOURCE_ID = H.APPLY_RESOURCE_ID");
		sql.append(" LEFT JOIN T_CC_TEMPLATE_CASE T ON T.TEMPLATE_CASE_ID = H.TEMPLATE_ID");
		sql.append(" LEFT JOIN T_CC_CLUSTER_CONFIG CLU ON CLU.ID = H.CLUSTERID");
		sql.append(" LEFT JOIN T_CC_PROJECT_ASS PA ON PA.APPLY_RESOURCE_ID = H.APPLY_RESOURCE_ID");
		sql.append(" LEFT JOIN T_CC_PROJECT_INFO PRO ON PRO.PROJECT_ID = PA.PROJECT_ID");
		sql.append(" LEFT JOIN T_CC_CONFIG_ASS_APPLYCASE C_ASS ON C_ASS.ASS_ID = H.ASS_ID");
		sql.append(" LEFT JOIN T_CC_SRT SRT ON SRT.SRT_ID = C_ASS.SRT_ID");
		sql.append(" WHERE H.APPLY_RESOURCE_ID = ?");
		List<Map<String,Object>> mapList = this.commonDao.findAsMapList(sql.toString(), new Object[]{vmId});
		CloneResVo vo = new CloneResVo();
		if(null != mapList && mapList.size() > 0){
			Map<String, Object> map = mapList.get(0);
			vo.setSourceVmId(StringUtil.convertToString(map.get("APPLY_RESOURCE_ID")));
			vo.setRamSize(StringUtil.StringToDouble(map.get("RAM_SIZE")));
			vo.setStorageSize(StringUtil.StringToDouble(map.get("STORAGE_SIZE")));
			vo.setIsoId(StringUtil.StringToLong(map.get("ISO_ID")));
			vo.setCpuSize(StringUtil.StringToDouble(map.get("CPU_CORE_COUNT")));
			vo.setClusterId(StringUtil.convertToString(map.get("CLUSTER_ID")));
			vo.setProjectId(StringUtil.convertToString(map.get("PROJECT_ID")));
			vo.setEvnCode(StringUtil.convertToString(map.get("ENV_CODE")));
			String closeDateStr = StringUtil.convertToString(map.get("CLOSE_DATE"));
			if(closeDateStr!=null && !"".equals(closeDateStr)){
				vo.setCloseDate(DateUtil.stringToDate(closeDateStr, DateUtil.DAY_FORMAT));
			}
		}
		return vo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public synchronized TccIpConfigInfo findIpByVlanId(String vlanId) {
		TccIpConfigInfo ipInfo = new TccIpConfigInfo();
		if(!StringUtils.isNotBlank(vlanId)){
			return ipInfo;
		}
		StringBuilder hql = new StringBuilder("from TccIpConfigInfo i where i.usedFlag = '0' "
				+ " and i.networkId = ? and (i.remark = '' or i.remark is null)");
		List list = this.commonDao.find(hql.toString(), new Object[]{StringUtil.StringToLong(vlanId)});
		if(null != list && list.size() > 0){
			ipInfo = (TccIpConfigInfo) list.get(0);
		}
		//IP预占用
		this.ipAddressPreOccupied(vlanId, ipInfo.getIpAddress());
		return ipInfo;
	}

	@Override
	public void ipAddressPreOccupied(String networkId, String ipAddress) {
		//TccVlanNetwork network = (TccVlanNetwork) this.commonDao.get(TccVlanNetwork.class, Long.parseLong(networkId));
		TccIpConfigInfo ipConfigInfo = this.findIpConfigInfoByNetworkIdAndIpAddress(networkId, ipAddress);
		String remark = "横向扩容IP占用";
		boolean succeed = false;
		if(("0".equalsIgnoreCase(ipConfigInfo.getUsedFlag())) || 
				"1".equalsIgnoreCase(ipConfigInfo.getUsedFlag()) && remark.equalsIgnoreCase(ipConfigInfo.getRemark())){
			String sql = "UPDATE T_CC_IP_CONFIG_INFO SET USED_FLAG = '1',REMARK = '" 
				+ remark + "' WHERE NETWORK_ID='"+networkId
				+ "' AND  IP_ADDRESS='"+ipAddress+"' and USED_FLAG ='0'";
			if(this.commonDao.updateByNativeSql(sql) == 1){
				succeed = true;
			}
		}
		if(!succeed){
			throw new RuntimeException("所选IP["+ipAddress+"]已被占用，请选择其他IP地址");
		}
	}

	private TccIpConfigInfo findIpConfigInfoByNetworkIdAndIpAddress(
			String networkId, String ipAddress) {
		try {
			if(StringUtils.isNotBlank(networkId) && StringUtils.isNotBlank(ipAddress)){
				TccVlanNetwork network = (TccVlanNetwork) this.commonDao.get(TccVlanNetwork.class, Long.parseLong(networkId));
				StringBuilder sqlBase = new StringBuilder();
				sqlBase.append("SELECT ipConfig.IP_CONFIG_ID AS id, ipConfig.USED_FLAG AS usedFlag, ");
				sqlBase.append("ipConfig.REMARK AS remark, ipConfig.IP_ADDRESS AS ipAddress ");
				sqlBase.append("FROM T_CC_IP_CONFIG_INFO ipConfig ");
				sqlBase.append("WHERE 1=1 ");
				/*sqlBase.append("AND ipConfig.`NAME` = ? ");*/
				sqlBase.append("AND ipConfig.IP_ADDRESS = ? ");
				StringBuilder sql = new StringBuilder(sqlBase).append("AND ipConfig.NETWORK_ID = ? ");
				List<Map<String, Object>> resultList = this.commonDao.findAsMapList(sql.toString(), 
						new Object[]{/*network.getNetworkName(),*/ ipAddress, network.getId()});
				if(resultList == null || (resultList != null && resultList.isEmpty())){
					resultList = this.commonDao.findAsMapList(sqlBase.toString(), new Object[]{network.getNetworkName(), ipAddress});
				}
				if(resultList != null && resultList.size() > 0){
					Map<String, Object> result = resultList.get(0);
					Long ipConfigId =  result.get("id") != null ? Long.parseLong(result.get("id").toString()) : -1L;
					String usedFlag = result.get("usedFlag") != null ? result.get("usedFlag").toString() : null;
					String remark = result.get("remark") != null ? result.get("remark").toString() : null;
					String resultIpAddress = result.get("ipAddress") != null ? result.get("ipAddress").toString() : null;
					TccIpConfigInfo ipConfigInfo = new TccIpConfigInfo();
					ipConfigInfo.setIpConfigId(ipConfigId);
					ipConfigInfo.setUsedFlag(usedFlag);
					ipConfigInfo.setRemark(remark);
					ipConfigInfo.setIpAddress(resultIpAddress);
					return ipConfigInfo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new TccIpConfigInfo();
	}

	@Override
	public void relieveIpAddressPreOccupied(String networkId, String ipAddress) {
		if(StringUtils.isNotBlank(networkId) && StringUtils.isNotBlank(ipAddress)){
			TccVlanNetwork network = (TccVlanNetwork) this.commonDao.get(TccVlanNetwork.class, Long.parseLong(networkId));
			String sql = "UPDATE T_CC_IP_CONFIG_INFO SET USED_FLAG = '0',REMARK='' WHERE `NAME`='"
					+network.getNetworkName()+"' AND  IP_ADDRESS='"+ipAddress+"' AND REMARK = '横向扩容IP占用'";
			this.commonDao.executeBySql(sql);
		}
	}

	@Override
	public boolean expandVm(TccExpdCapacity capacity, Long hostresouceId,List<TccStorageApplyCaseAss> storageList) throws Exception, Throwable {

		//当前云主机资源
		TccApplyedHostinfo applyedHostinfo = (TccApplyedHostinfo) this.commonDao
												.get(TccApplyedHostinfo.class, hostresouceId);
		//this.commonDao.flush();
		TccApplyedHostResource applyedHostResource = vmAssistService
				.getApplyedHostResource(applyedHostinfo.getApplyResourceId());
		applyedHostResource.setProcessStatus(BusinessEnvironment.RESOURCE_TYPE_INSTALL); // 安装中
		vmAssistService.updateApplyedHostResource(applyedHostResource);
		applyedHostResource = vmAssistService.getApplyedHostResource(applyedHostResource.getApplyResourceId());
		boolean flag = false;
		
		String VirtualType = applyedHostResource.getVirtualType();
		// 调用扩容接口
		if (BusinessEnvironment.VIRTUAL_TYPE_VC.equals(VirtualType)) { // 01.虚拟化类型为VC，调用VC的接口
			flag = capacityMgmtService.upgradeVmVC(capacity,applyedHostinfo,storageList);
		} else if (BusinessEnvironment.VIRTUAL_TYPE_CS.equals(VirtualType)) { // 02.虚拟化类型为CS，调用CS的接口
			boolean deletesnapshotFlag = deletVmSnapshotByvmId(
					String.valueOf(applyedHostResource.getApplyResourceId()), applyedHostResource.getUuid());
			if (deletesnapshotFlag == false) {	//CloudStack必须要删除快照才能扩容
				return false;
			}
			flag = capacityMgmtService.upgradeVmCS(capacity,applyedHostinfo,storageList);// 调用扩容接口
		} else if (BusinessEnvironment.VIRTUAL_TYPE_OPENSTACK.equals(VirtualType)) { // 05.虚拟化类型为OS，调用OS的接口
			flag = capacityMgmtService.upgradeVmOS(capacity,applyedHostinfo,storageList);// 调用扩容接口
		} else if (BusinessEnvironment.VIRTUAL_TYPE_HUAWEIOPENSTACK.equals(VirtualType)) { // 06.虚拟化类型为华为OS，调用OS的接口
			flag = capacityMgmtService.upgradeVmHuaWeiOS(capacity,applyedHostinfo,storageList);// 调用扩容接口
		}

		if (!flag) {
			applyedHostResource.setProcessStatus(BusinessEnvironment.RESOURCE_TYPE_FAIL); // 安装失败
			vmAssistService.updateApplyedHostResource(applyedHostResource);
			return flag;
		}
		

		
		//维护快照和虚机的关系
		if(!BusinessEnvironment.VIRTUAL_TYPE_CS.equals(VirtualType)){	//CS的快照会在扩容前删除
			Long resourceId = applyedHostResource.getApplyResourceId();
			String hql = "FROM TccSnapshotHistory SH WHERE SH.enableFlg = '1' AND SH.vmId = ?";
			List<TccSnapshotHistory> snapshotList = this.commonDao.find(hql, resourceId);
			if(null != snapshotList && snapshotList.size() > 0){
				for(TccSnapshotHistory snapshot : snapshotList){
					snapshot.setVmId(applyedHostResource.getApplyResourceId());
					this.commonDao.update(snapshot);
				}
			}
		}
		
		//扩容虚拟机设置登录名密码
		applyedHostResource.setHostLoginUsername(applyedHostinfo.getHostLoginUsername());
		applyedHostResource.setOriginalRootPassword(applyedHostinfo.getOriginalRootPassword());
		applyedHostResource.setHostLoginPassword(applyedHostinfo.getHostLoginPassword());
		// 修改原已分配记录状态
		applyedHostResource.setRunStatusCd(BusinessEnvironment.RESOURCE_RUNTYPE_RUN);// 扩容完成
		applyedHostResource.setProcessStatus(BusinessEnvironment.RESOURCE_TYPE_SUCCESS);// 安装完成
		applyedHostResource.setEnableFlg(EnvironmentConstant.ENABLE); // 设置为无效
		applyedHostResource.setQuart_enable(BusinessEnvironment.QUAR_ENABLE_TRUE);
		applyedHostResource.setRamSize(capacity.getRamSize());
		applyedHostResource.setCpuCoreCount(capacity.getCpuCoreCount());
		vmAssistService.updateApplyedHostResource(applyedHostResource);
		
		
		//设置已经优化
		 this.commonDao.bulkUpdate("update TVmSurveryInfo set optimizeFlg = ? where tccApplyedHostinfo.applyResourceId = ? ",
				new Object[]{capacity.getProposalType(),hostresouceId});//更新优化状态
		
//		
//		// 新的项目和资源关系
//		List<TccProjectAss> newProjectAssList = new ArrayList<TccProjectAss>();
//		TccProjectAss newTccProjectAss = new TccProjectAss();
//		// 批量修改项目与已申请资源表关系
//		TccProjectAss projectAss = new TccProjectAss();
//		projectAss.setTccApplyedHostinfo(applyedHostResource);
//		// ***** by zhachaoy *****
//		DetachedCriteria criteria = DetachedCriteria.forClass(TccProjectAss.class);
//		if (null != projectAss && null != projectAss.getProjectAssId()) {
//			criteria.add(Restrictions.eq("projectAssId", projectAss.getProjectAssId()));
//		}
//		if (null != projectAss && null != projectAss.getTccProjectInfo()) {
//			criteria.add(Restrictions.eq("tccProjectInfo", projectAss.getTccProjectInfo()));
//		}
//		if (null != projectAss && null != projectAss.getTccApplyedHostinfo()) {
//			criteria.add(Restrictions.eq("tccApplyedHostinfo", projectAss.getTccApplyedHostinfo()));
//		}
//		// 数据有效性为空或者等于1的
//		criteria.add(Restrictions.or(Restrictions.eq("enableFlg", EnvironmentConstant.ENABLE), Restrictions.isNull("enableFlg")));
//		List<TccProjectAss> projectAssList = commonDao.findByCriteria(criteria);
//		// ##### by zhachaoy #####
//		for (TccProjectAss pa : projectAssList) {
//			// 拷贝关系表
//			BeanUtils.copyProperties(newTccProjectAss, pa);
//			newTccProjectAss.setProjectAssId(null);
//			newTccProjectAss.setTccInstanceInfo(null);
//			newTccProjectAss.setTccApplyedHostinfo(applyedHostResource);
//			newProjectAssList.add(newTccProjectAss);
//			pa.setEnableFlg(EnvironmentConstant.DISENABLE);
//		}
//		vmAssistService.updateProjectAssList(projectAssList);
//		// 保存新的项目和资源关系
//		this.commonDao.saveOrUpdateAll(newProjectAssList);
		return true;
	
	}
	
	
	//判断用户是否为空，如果为空设置默认操作自动回收
	public LoginUserInfo determineLoinuser(LoginUserInfo userInfo){
			if(userInfo == null){
				userInfo = new LoginUserInfo();
				userInfo.setEmpName("资源到期自动回收");
			}else{
				userInfo.setEmpName("实施者"+userInfo.getEmpName());
			}
		return userInfo;
	}

	public QingCloudService getQingcloudService() {
		return qingcloudService;
	}

	public void setQingcloudService(QingCloudService qingcloudService) {
		this.qingcloudService = qingcloudService;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<TccPhysiscResourceInfo> getPhysicalBycluster(String clusterId) throws Throwable {
		String hql = " from TccPhysiscResourceInfo where enableFlg=1 AND CLUSTERID = " + clusterId +" AND synch=true";
		List<TccPhysiscResourceInfo> list = this.commonDao.find(hql);
		return list;
	
	}
	
	@Override
	public void addVmRemark(Long vmId, String remark) throws Exception, Throwable {
		TccApplyedHostinfo vmDb = (TccApplyedHostinfo) commonDao.get(TccApplyedHostinfo.class, vmId);
		String virtualType = vmDb.getVirtualType();
		TccCloudplatform platform = findPlatformByClusterId(vmDb.getClusterId());
		// 调用添加备注接口
		if (BusinessEnvironment.VIRTUAL_TYPE_VC.equals(virtualType)) { // 01.虚拟化类型为VC，调用VC的接口
			addVcRemark(remark, vmDb.getHostNane(), platform.getCloudplatformId().toString());
		} else if (BusinessEnvironment.VIRTUAL_TYPE_CS.equals(virtualType)) { // 02.虚拟化类型为CS，调用CS的接口
			//TODO
		} else if (BusinessEnvironment.VIRTUAL_TYPE_OPENSTACK.equals(virtualType)) { // 05.虚拟化类型为OS，调用OS的接口
			//TODO
		} else if (BusinessEnvironment.VIRTUAL_TYPE_HUAWEIOPENSTACK.equals(virtualType)) { // 06.虚拟化类型为华为OS，调用OS的接口
			//TODO
		} else if (BusinessEnvironment.VIRTUAL_TYPE_QINGCLOUD.equals(virtualType)) { // 07.虚拟化类型为华为OS，调用OS的接口
			//TODO
		} else if (BusinessEnvironment.VIRTUAL_TYPE_ALIYUN.equals(virtualType)) { // 08.虚拟化类型为华为OS，调用OS的接口
			//TODO
		} else if (BusinessEnvironment.VIRTUAL_TYPE_TENCENT.equals(virtualType)) { // 09.虚拟化类型为华为OS，调用OS的接口
			//TODO
		}
		vmDb.setRemark(remark);
		commonDao.update(vmDb);
	}

	@Override
	public void addVcRemark(String remark, String vmName,String cloudPaltformId) throws Exception, Throwable {
		// 查询虚拟机
		GetVirtualMachineRequest GetVirtualMachineRequestT = new GetVirtualMachineRequest();
		GetVirtualMachineRequestT.setCloud(cloudPaltformId);
		if (StringUtils.isNotBlank(vmName)) {
			GetVirtualMachineRequestT.setVmName(vmName);
		}
		GetVirtualMachineRequestT = (GetVirtualMachineRequest) TccCloudPlatUitls.setRequestParam(GetVirtualMachineRequestT);
		GetVirtualMachineResponse GetVirtualMachineResponseT = (GetVirtualMachineResponse) 
				TccCloudPlatUitls.getVmwareAdapte().execute(GetVirtualMachineRequestT);
		com.vmware.vim25.mo.VirtualMachine vm = GetVirtualMachineResponseT.getVitualMachine();
		VirtualMachineConfigSpec vmConfig = new VirtualMachineConfigSpec();
		vmConfig.setAnnotation(remark);
		Task reconfigTask = vm.reconfigVM_Task(vmConfig);
		reconfigTask.waitForTask();
	}
	
	@Override
	public Map<String, String> createAliYun(SrtManageVO srtManageVO, TccProjectInfo tccProjectInfo)
			throws Exception, Throwable {
		Map<String, String> errorMap = new HashMap<String, String>();
		String errorMsg = "";
		String cloudplatformId = srtManageVO.getCloudplatformId();
		if (StringUtils.isBlank(cloudplatformId)) {
			errorMsg = "平台id为空，无法继续操作，请检查数据是否正确。"; 
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}

		if (StringUtils.isBlank(srtManageVO.getVmName())) {
			errorMsg = "虚拟机名称不能为空，请输入虚拟机名称。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}

		String vmName = srtManageVO.getVmName().replaceAll(" ", "");
		// 根据数据中心Id查询UUID
				TccCloudDatacenter datacenter = (TccCloudDatacenter) this.commonDao
						.get(TccCloudDatacenter.class, Long.parseLong(srtManageVO.getZoneId()));
		aliYunNameCheck(cloudplatformId, vmName, errorMap,datacenter.getUuid());
		if (errorMap.size() > 0)
			return errorMap;
		String applyedHostResourceId = excuteCreateAliYun(srtManageVO, tccProjectInfo);
		if (applyedHostResourceId == null) {
			errorMsg = "创建资源失败。";
			logger.info(errorMsg);
			errorMap.put("FAILURE", errorMsg);
			return errorMap;
		}
		errorMap.put("SUCCESS", applyedHostResourceId);
		//保存日志
		try{
			TccConfigAssApplycase ass = (TccConfigAssApplycase) this.commonDao.get(TccConfigAssApplycase.class, Long.valueOf(srtManageVO.getConfigAssApplycaseId()));
			logService.saveOperLog("成功", "创建", ass, 2);
		}
		catch(Exception e){}
		//新建虚拟机的时候自动创建一个快照
		String host = String.valueOf(applyedHostResourceId);	//虚拟机ID
		TCcEvn ccEvn = srtManageVO.getEvn();
		boolean initSnapshot = ccEvn!=null ?ccEvn.isInitSnapshot():false;
		//判断是否需要初始快照
		if(initSnapshot){
			try {
				boolean Status = false;
//	    		String noteString = "新建虚拟机时自动创建的快照";
//	    		vmSnapshotService.createVmSnapshot(host.trim(),Status,noteString.trim());
	    		//把创建的虚拟机的快照设为初始创建快照时创建
	    		//创建完快照时需要让休眠300毫秒才能查询数据
	    		Thread.sleep(300);
	    		String sql="UPDATE T_CC_SNAPSHOT_HISTORY SET SNAPSHOT_MODE = 3 WHERE VM_ID = "+host;
	    		commonDao.updateByNativeSql(sql);
				
			} catch (Exception e) {
				logger.error("新建虚拟机时，自动创建的快照失败");
			}
		}
		//把虚拟机的ID用json返回页面
	
		JSONObject object = new JSONObject();
		object.put("vmId", host);
		JsonResponseUtil.writeJsonArray(object);
		return errorMap;
	}

	@Override
	public InstancePojo aliYunBoot(String cloudplatformId, String serverName, String imageId, String networkId,
			int cpu, int ramMb, String instanceType, String zoneUuid) throws Exception, Throwable {
		AliYunCreateInstanceRequest request = new AliYunCreateInstanceRequest();
		request.setCloud(cloudplatformId);
		request = (AliYunCreateInstanceRequest) TccCloudPlatUitls.setRequestParam(request);
		AliYunCreateInstanceResponse response = new AliYunCreateInstanceResponse();
		try {
			request.setInstanceName(serverName);
			request.setRegionId(zoneUuid);
			request.setImageId(imageId);
//			request.setLoginMode(LoginMode.PASSWD);
//			request.setLoginPasswd("1qaz@WSX");
//			request.setCpu(QingcloudServiceImpl.translateCpu(cpu));
//			request.setMemory(QingcloudServiceImpl.translateRam(ramMb));
//			request.setInstanceClass("1".equals(instanceClass)?InstanceClass.HIGHPERFORMANCE:InstanceClass.NORMALPERFORMANCE);
			request.setInstanceType("ecs.n1.small");
			response = (AliYunCreateInstanceResponse) TccCloudPlatUitls.getAliYunAdapte().execute(request);
		} catch (Throwable e) {
			logger.info("创建aliyun虚拟机出错。" + e.getMessage());
		}

		if (response == null || response.getInstanceId() == null){
			return null;
		}
		if(!ResponseParameter.SUCCESS.equals(response.getSuccess())){
			logger.info("创建aliyun虚拟机出错。" + response.getError());
		}
		boolean isActive = false;
		int tryTimes = 1;
		InstancePojo instancePojo = null;
		do{
			AliYunDescribeInstancesRequest retrieveRequest = new AliYunDescribeInstancesRequest();
			retrieveRequest.setCloud(cloudplatformId);
			retrieveRequest = (AliYunDescribeInstancesRequest)TccCloudPlatUitls.setRequestParam(retrieveRequest);
			JSONArray array = new JSONArray();
			array.add(response.getInstanceId());
			retrieveRequest.setInstanceIds(array.toJSONString());
			retrieveRequest.setRegionId(zoneUuid);
			retrieveRequest.setIoOptimized(true);
			AliYunDescribeInstancesResponse retrieveResponse = (AliYunDescribeInstancesResponse) TccCloudPlatUitls.getAliYunAdapte().execute(retrieveRequest);
			if(retrieveResponse == null || retrieveResponse.getInstances()==null){
				return null;
			}
			instancePojo = retrieveResponse.getInstances().get(0);
			isActive = EnumUtils.Status.RUNNING.equals(instancePojo.getStatus());
			tryTimes++;
			if(!isActive){
				Thread.sleep(2000);
			}
		}while(!isActive&&tryTimes<=10);
		return instancePojo;
	}

	@Override
	public String createAliYunVolume(String cloudplatformId, Integer storageSize, String serverName, String zoneUuid,String zoneId) {
		AliYunCreateDiskRequest request = new AliYunCreateDiskRequest();
		request.setCloud(cloudplatformId);
		request = (AliYunCreateDiskRequest) TccCloudPlatUitls.setRequestParam(request);
		AliYunCreateDiskResponse response = new AliYunCreateDiskResponse();
		try {
			
			zoneId = getZoneId(cloudplatformId, zoneUuid);
			request.setRegionId(zoneUuid);
			request.setZoneId("cn-shanghai-b");
			request.setSize(storageSize+40);
			request.setDiskCategory("cloud_efficiency");
			String volumeName = serverName + "_disk_" + new Random().nextInt(10000);
			request.setDiskName(volumeName);
			response = (AliYunCreateDiskResponse) TccCloudPlatUitls.getAliYunAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("创建aliyun磁盘出错。" + e.getMessage());
			return null;
		}
		if (response == null || response.getDiskId()==null){
			return null;
		}
		if(ResponseParameter.FAILED.equals(response.getSuccess())){
			logger.error("创建aliyun磁盘出错。" + response.getError());
			return null;
		}
		String diskId = response.getDiskId();
		//查询磁盘状态是否可用
		boolean isActive = false;
		int tryTimes = 1;
		DiskPojo diskPojo  = null;
		do{
			diskPojo = getAliYunVolumeById(cloudplatformId, diskId, zoneUuid);
			//由于状态是通用的，所以用镜像状态也可以比对
			isActive = ImageStatusEnum.AVAILABLE.getValue().equals(diskPojo.getStatus());
			if(!isActive){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					logger.error(e);;
				}
			}
			tryTimes++;
		}while(!isActive&&tryTimes<=10);
		logger.info("创建aliyun磁盘成功。");
		return diskId;
	}
	
	private String getZoneId(String cloudplatformId, String zoneUuid){
		AliYunDescribeZonesRequest request = new AliYunDescribeZonesRequest();
		request.setCloud(cloudplatformId);
		request = (AliYunDescribeZonesRequest) TccCloudPlatUitls.setRequestParam(request);
		AliYunDescribeZonesResponse response = new AliYunDescribeZonesResponse();
		try {
			request.setRegionId(zoneUuid);
			response = (AliYunDescribeZonesResponse) TccCloudPlatUitls.getAliYunAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("获取可用区出错。" + e.getMessage());
			return null;
		}
		if (response == null || !"true".equals(response.getSuccess())){
			return null;
		}
		if(ResponseParameter.FAILED.equals(response.getSuccess())){
			logger.error("获取可用区出错。" + response.getError());
			return null;
		}
		return response.getZones().get(0).getZoneId();
	}

	@Override
	public DiskPojo getAliYunVolumeById(String cloudplatformId, String volumeId, String zoneUuid) {
		AliYunDescribeDisksRequest request = new AliYunDescribeDisksRequest();
		request.setCloud(cloudplatformId);
//		request.setZoneId(zoneUuid);
		request.setRegionId(zoneUuid);
		request = (AliYunDescribeDisksRequest) TccCloudPlatUitls.setRequestParam(request);
		AliYunDescribeDisksResponse response = new AliYunDescribeDisksResponse();
		try {
			JSONArray array = new JSONArray();
	        array.add(volumeId);
	        request.setDiskIds(array.toJSONString());
			response = (AliYunDescribeDisksResponse) TccCloudPlatUitls.getAliYunAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("获取aliyun磁盘出错。" + e.getMessage());
		}
		if (response == null || response.getDisks().isEmpty())
			return null;
		logger.error("获取aliyun磁盘成功。");
		return response.getDisks().get(0);
	}

	@Override
	public String saveOrUpdateAliYunApplyedHostResource(InstancePojo serverPojo, TccProjectInfo tccProjectInfo,
			Long assId, String clusterId, String zoneId, String imageId, String networkUuid, String hostInfoProduceId,
			long... templateid) throws Exception, Throwable {
		
		TccConfigAssApplycase ass = configAssApplyCaseService.getConfigAssApplyCase(assId);
		TccSrt srt = vmAssistService.getSrt(ass.getTccSrt().getSrtId());
		String ipAddress = null;
		if(serverPojo.getVpcAttributes().getPrivateIpAddress()!=null&&!serverPojo.getVpcAttributes().getPrivateIpAddress().isEmpty()){
			ipAddress = serverPojo.getVpcAttributes().getPrivateIpAddress().get(0);
		}
		TccApplyedHostResource applyedHostResource = vmAssistService
				.mergeAliYunApplyedHostResource(serverPojo, ass, ipAddress, zoneId, imageId);
		applyedHostResource.setHardwareTypeCd("186");
		applyedHostResource.setClusterId(clusterId);
		if(null!=hostInfoProduceId&&hostInfoProduceId.length()>0){
			applyedHostResource.setProduceId(hostInfoProduceId);
		}
		if (templateid != null) {
			applyedHostResource.setTemplateId(templateid[0] + "");
		}

		ass.setSetupStats(BusinessEnvironment.SETUP_STATS_INSTSUCC);
		applyedHostResource.setNetworkUuid(networkUuid);

		configAssApplyCaseService.updateConfigAssApplyCase(ass);
		vmAssistService.saveSrtApplyedhostAss(applyedHostResource, srt);
		vmAssistService.saveProjectAss(applyedHostResource, tccProjectInfo);

		String host = String.valueOf(applyedHostResource.getApplyResourceId()); // 虚拟机ID
		String visibleName = applyedHostResource.getHostNane(); // 虚拟机名称
		TccOs os = this.getOsById(applyedHostResource.getOsCd());
		// 将虚拟机注册到Zabbix中
		try {
			if (!zabbixApiService.isAddHostToMonitorByHostName(host)) {
				HostMonitorBean hostinfo = new HostMonitorBean();
				hostinfo.setHostName(host);
				hostinfo.setVisibleName(visibleName);
				hostinfo.setIp(applyedHostResource.getIpAddress());
				if (null != os && null != os.getOneLevelOs()) {
					hostinfo.setOsName(os.getOneLevelOs());
				} else {
					hostinfo.setOsName("");
				}
				applyedHostResource.setMonitorFlg(zabbixApiService
						.addHostToMonitor(hostinfo) ? "1" : "0");
			} else {
				logger.info("主机[" + host + "]已在zabbix中注册");
				applyedHostResource.setMonitorFlg("1");
			}
		} catch (Exception ex) {
			this.logger.error("添加监控失败！", ex);
		} catch (Throwable t) {
			this.logger.error("添加监控失败！", t);
		}
		commonDao.update(applyedHostResource);
		// 更新ip的状态
		if (ipAddress != null) {
			String sql = "UPDATE T_CC_IP_CONFIG_INFO f SET f.USED_FLAG='1',REMARK='创建"
					+ serverPojo.getInstanceName()
					+ "虚拟机',DISTRIBUTE_TIME=NOW() WHERE f.IP_ADDRESS ='" + ipAddress + "'";
			commonDao.updateByNativeSql(sql);
		}
		String applyedHostResourceId = applyedHostResource.getApplyResourceId()
				.toString();
		return applyedHostResourceId;
	}

	@Override
	public String attachAliYunVolume(String cloudplatformId, String serverId, String volumeId, String serverName,
			String zoneUuid) {
		AliYunAttachDiskRequest request = new AliYunAttachDiskRequest();
		request.setCloud(cloudplatformId);
		request = (AliYunAttachDiskRequest) TccCloudPlatUitls.setRequestParam(request);
		AliYunAttachDiskResponse response = new AliYunAttachDiskResponse();
		try {
			request.setInstanceId(serverId);
			request.setRegionId(zoneUuid);
			request.setDiskId(volumeId);
			response = (AliYunAttachDiskResponse) TccCloudPlatUitls.getAliYunAdapte().execute(request);
		} catch (Throwable e) {
			logger.error("aliyun虚拟机挂载磁盘出错。" + e.getMessage());
		}

		if (response == null) {
			logger.error("aliyun虚拟机挂载磁盘出错。");
			return null;
		}
		if(ResponseParameter.FAILED.equals(response.getSuccess())){
			logger.error("aliyun虚拟机挂载磁盘出错。"+response.getError());
			return null;
		}
		boolean statusIsNull = true;
		do {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.info(e);
			}
			DiskPojo diskPojo = getAliYunVolumeById(cloudplatformId, volumeId,zoneUuid);
			if(diskPojo!=null){
				if ("IN_USE".equalsIgnoreCase(diskPojo.getStatus())) {
					statusIsNull = false;
				}
			}
		} while (statusIsNull);

		logger.info("aliyun虚拟机挂载磁盘成功。");
		return volumeId;
	}
	
}
