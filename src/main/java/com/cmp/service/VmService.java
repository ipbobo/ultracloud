package com.cmp.service;

public interface VmService {
//	/**
//	 * 获得所有的平台
//	 * 
//	 * @return
//	 */
//	public List<TccCloudplatform> getAllCloudplatform();
//
//	/**
//	 * 工单模块调用新建vCenter的虚拟机的接口
//	 * 
//	 * @param assId 套餐配置表与资源申请表关系代码表ID
//	 * @param ipZone IP
//	 * @param clusterId TccClusterConfig主键ID
//	 * @return 错误信息
//	 * @throws Exception
//	 */
//	public Map<String, String> createVC(Long assId, IpZoneEnum ipZone,
//			String clusterId, TccProjectInfo tccProjectInfo, String vmName,
//			String osName, String networkId, String datastoreName, SrtManageVO srtManageVO)
//			throws Exception, Throwable;
//
//	/**
//	 * 销毁CloudStack的虚拟机接口调用
//	 * 
//	 * @param applyResourceId
//	 * @throws Exception
//	 */
//	public boolean destroyCS(Long applyResourceId) throws Exception, Throwable;
//
//	/**
//	 * Description:取得云主机类型id
//	 * 
//	 * @param flavorList
//	 * @param cpu
//	 * @param ram
//	 * @param disk
//	 * @param resourceType
//	 * @param diskType
//	 * @throws Exception
//	 * @throws Throwable
//	 * @return String
//	 */
//	public String getHuaWeiFlavorId(List<FlavorsPojo> flavorList, Double cpu, Double ram,
//			Long disk, String resourceType, String diskType, String platformId);
//
//	/**
//	 * 销毁vCenter的虚拟机接口调用
//	 * 
//	 * @param applyResourceId
//	 * @throws Exception
//	 */
//	public boolean destroyVC(Long applyResourceId) throws Exception, Throwable;
//
//	/**
//	 * 启动CloudStack的虚拟机接口调用
//	 * 
//	 * @param applyResourceId
//	 * @throws Exception
//	 */
//	public boolean startCS(Long applyResourceId) throws Exception, Throwable;
//
//	/**
//	 * 启动vCenter的虚拟机接口调用
//	 * 
//	 * @param applyResourceId
//	 * @throws Exception
//	 */
//	public boolean startVC(Long applyResourceId) throws Exception, Throwable;
//
//	/**
//	 * 停止CloudStack的虚拟机接口调用
//	 * 
//	 * @param applyResourceId
//	 * @throws Exception
//	 */
//	public boolean stopCS(Long applyResourceId) throws Exception, Throwable;
//
//	/**
//	 * 停止vCenter的虚拟机接口调用
//	 * 
//	 * @param applyResourceId
//	 * @throws Exception
//	 */
//	public boolean stopVC(Long applyResourceId) throws Exception, Throwable;
//
//	public boolean stopVC(Long applyResourceId, String str) throws Exception, Throwable;
//
//	/**
//	 * 主机管理模块调用重启CloudStack的虚拟机接口
//	 * 
//	 * @param applyResourceId
//	 * @return 重启成功为true，重启失败为false
//	 * @throws Exception
//	 */
//	public boolean rebootCS(Long applyResourceId) throws Exception, Throwable;
//
//	/**
//	 * 主机管理模块调用重启vCenter的虚拟机接口
//	 * 
//	 * @param applyResourceId
//	 * @return 重启成功为true，重启失败为false
//	 * @throws Exception
//	 */
//	public boolean rebootVC(Long applyResourceId) throws Exception, Throwable;
//
//	/**
//	 * CloudStack中迁移虚拟机
//	 * 
//	 * @param applyResourceId
//	 * @param hostId
//	 * @return true为迁移成功
//	 * @throws Exception
//	 */
//	public boolean moveVmWareCS(Long applyResourceId, String hostId)
//			throws Exception;
//
//	/**
//	 * vm中迁移虚拟机
//	 * 
//	 * @param applyResourceId
//	 * @param hostName 迁移到的主机名称
//	 * @return true为迁移成功
//	 * @throws Exception
//	 */
//	public boolean moveVmWareVC(Long applyResourceId, String hostName)
//			throws Exception;
//
//	/**
//	 * 扩容虚拟机
//	 * 
//	 * @param srtApplyedhostAssId 服务请求主表与已分配资源代码表ID
//	 * @throws Exception
//	 */
//	public boolean expandVm(Long srtApplyedhostAssId) throws Exception,
//			Throwable;
//
//	/**
//	 * 扩容小型机
//	 * 
//	 * @param srtApplyedhostAssId 服务请求主表与已分配资源代码表ID
//	 * @throws Exception
//	 */
//	public boolean expandMinicomputer(Long srtApplyedhostAssId)
//			throws Exception, Throwable;
//
//	/**
//	 * 
//	 * Description: 扩容实例 Date:Jul 17, 2013 4:05:04 PM
//	 * 
//	 * @param resrcExpdVO
//	 * @return
//	 * @throws Exception
//	 * @return TccInstanceInfo
//	 */
//	public TccInstanceInfo expandInstance(ResrcExpdVO resrcExpdVO)
//			throws Exception;
//
//	/**
//	 * <p>
//	 * 获取虚拟机部分信息（除了已分配的cpu、内存与存储）
//	 * </p>
//	 * 
//	 * @param applyResourceId 已分配应用系统申请资源基本信息表ID
//	 * @return
//	 * @throws Exception
//	 */
//	public VmInfo getVmInfo(Long applyResourceId) throws Exception;
//
//	/**
//	 * 工单模块调用新建CloudStack的克隆虚拟机接口
//	 * 
//	 * @param assId 套餐配置表与资源申请表关系代码表ID
//	 * @param copyHostId 被克隆的虚拟机ID
//	 * @param clusterId TccClusterConfig主键ID
//	 * @return 错误信息
//	 * @throws Exception
//	 */
//	public Map<String, String> CloneVm(Long assId, Long copyHostId,
//			String clusterId, TccProjectInfo tccProjectInfo, String vmName,
//			String vlanId, String ipAddress) throws Exception, Throwable;
//
//	/**
//	 * 克隆vcenter接口
//	 * 
//	 * @param assId
//	 * @param sourceId
//	 * @param clusterId
//	 * @param tccProjectInfo
//	 * @param vmName
//	 * @return
//	 * @throws Exception
//	 */
//	public Map<String, String> cloneVC(Long assId, Long sourceId,
//			Long clusterId, TccProjectInfo tccProjectInfo, String vmName,
//			String networkname, String ipAddress) throws Exception, Throwable;
//
//	/**
//	 * 根虚拟机id获取虚拟机所在的平台的 ip, username, password
//	 * 
//	 * @param vmId 虚拟机id
//	 * @return map: {"ip": "ip", "username": "username", "password": "password",
//	 *         "platformKey": "key"}
//	 */
//	public Map<String, String> getVmPlatformInfo(Long vmId);
//
//	/**
//	 * 
//	 * Description:创建openstack Date:2015年8月19日 下午5:56:43
//	 * 
//	 * @param srtManageVO
//	 * @param tccProjectInfo
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable
//	 * @return Map<String,String>
//	 */
//	Map<String, String> createOpenStack(SrtManageVO srtManageVO,
//			TccProjectInfo tccProjectInfo) throws Exception, Throwable;
//
//	/**
//	 * 
//	 * Description:创建华为openstack 2016年6月2日18:20:22
//	 * 
//	 * @param srtManageVO
//	 * @param tccProjectInfo
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable
//	 * @return Map<String,String>
//	 */
//	Map<String, String> createHuaWeiOpenStack(SrtManageVO srtManageVO,
//			TccProjectInfo tccProjectInfo) throws Exception, Throwable;
//
//	/**
//	 * 查询所有的云主机类型
//	 * 
//	 * @param cloudplatformId
//	 * @return
//	 */
//	List<FlavorResponse> getFlavorList(String cloudplatformId);
//
//	/**
//	 * 查询所有的华为OpenStack云主机类型
//	 * 
//	 * @param cloudplatformId
//	 * @return
//	 */
//	List<FlavorsPojo> getHuaWeiFlavorList(String cloudplatformId);
//
//	/**
//	 * 创建云主机类型
//	 * 
//	 * @param cloudplatformId
//	 * @param name
//	 * @param ram
//	 * @param vcpus
//	 * @param disk
//	 * @param storage
//	 * @return
//	 */
//	String createFlavor(String cloudplatformId, String name, Integer ram, Integer vcpus,
//			Integer storage);
//
//	/**
//	 * 创建华为云主机类型
//	 * 
//	 * @param cloudplatformId
//	 * @param name
//	 * @param ram
//	 * @param vcpus
//	 * @param disk
//	 * @param diskType 本地 共享
//	 * @param 资源类型0:物理机 1:虚拟机
//	 * @return
//	 */
//	String createHuaWeiFlavor(String cloudplatformId, String name,
//			Integer ram, Integer vcpus, Long disk, String resourceType, String diskType);
//
//	/**
//	 * 查询openstack云主机类型
//	 * 
//	 * @param flavorList
//	 * @param ass
//	 * @param applyCaseAss
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable
//	 */
//	String getFlavorId(List<FlavorResponse> flavorList,
//			TccConfigAssApplycase ass) throws Exception, Throwable;
//
//	/**
//	 * openstack挂载存储
//	 * 
//	 * @param cloudplatformId
//	 * @param serverId
//	 * @param volumeId
//	 * @param serverName
//	 * @return
//	 */
//	String attachVolume(String cloudplatformId, String serverId, String volumeId,
//			String serverName);
//
//	/**
//	 * 华为openstack挂载存储
//	 * 
//	 * @param cloudplatformId
//	 * @param serverId
//	 * @param volumeId
//	 * @param serverName
//	 * @return
//	 */
//	String attachHuaWeiVolume(String cloudplatformId, String serverId, String volumeId,
//			String serverName);
//
//	/**
//	 * 
//	 * Description:保存或更新 Date:2015年9月1日 下午2:55:45
//	 * 
//	 * @param serverPojo
//	 * @param tccProjectInfo
//	 * @param assId
//	 * @param clusterId
//	 * @param cloudplatformId
//	 * @return
//	 */
//	public boolean checkupConfig(String assId, String zoneId, String clusterId,
//			String cloudplatformId);
//
//	/**
//	 * 
//	 * Description:保存或更新 Date:2015年9月1日 下午2:55:45
//	 * 
//	 * @param serverPojo
//	 * @param tccProjectInfo
//	 * @param assId
//	 * @param clusterId
//	 * @param zoneId
//	 * @param imageId
//	 * @param networkUuid
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable
//	 * @return String
//	 */
//	String saveOrUpdateApplyedHostResource(ServerPojo serverPojo,
//			TccProjectInfo tccProjectInfo, Long assId, String clusterId,
//			String zoneId, String imageId, String networkUuid, String hostInfoProduceId,
//			long[] templateido)
//			throws Exception, Throwable;
//
//	/**
//	 * Description:保存或更新华为云主机信息:Date:2016年6月3日20:10:09
//	 * 
//	 * @param serverPojo
//	 * @param tccProjectInfo
//	 * @param assId
//	 * @param clusterId
//	 * @param zoneId
//	 * @param imageId
//	 * @param networkUuid
//	 * @param resourceType
//	 * @param poolId
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable
//	 */
//	String saveOrUpdateHuaWeiApplyedHostResource(ServersPojo serverPojo,
//			TccProjectInfo tccProjectInfo, Long assId, String clusterId, String zoneId,
//			String imageId, String networkUuid, String resourceType) throws Exception, Throwable;
//
//	/**
//	 * Description:保存或更新华为云主机信息:Date:2016年8月12日14:10:09
//	 * 
//	 * @param serverPojo
//	 * @param host
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable
//	 */
//	String saveOrUpdateHuaWeiApplyedHostClone(ServersPojo serverPojo, TccApplyedHostinfo host,
//			String imageId, TccProjectInfo tccProjectInfo, LoginUserInfo loginUserInfo)
//			throws Exception, Throwable;
//
//	/**
//	 * 
//	 * Description:创建openstack虚拟机 Date:2015年9月1日 下午2:54:43
//	 * 
//	 * @param cloudplatformId
//	 * @param serverName
//	 * @param flavorId
//	 * @param imageId
//	 * @param networkId
//	 * @return
//	 * @throws Exception
//	 * @return ServerPojo
//	 */
//	ServerPojo openStackBoot(String cloudplatformId, String serverName,
//			String flavorId, String imageId, String networkId) throws Exception;
//
//	/**
//	 * 
//	 * Description:创建华为openstack虚拟机 Date:2016年6月2日20:24:06
//	 * 
//	 * @param cloudplatformId
//	 * @param serverName
//	 * @param flavorId
//	 * @param imageId
//	 * @param networkId
//	 * @param zoneName
//	 * @return
//	 * @throws Exception
//	 * @return ServerPojo
//	 */
//	ServersPojo huaWeiOpenStackBoot(String cloudplatformId, String serverName,
//			String flavorId, String imageId, String networkId, String zoneName) throws Exception;
//
//	/**
//	 * 
//	 * Description:创建磁盘 Date:2015年9月17日 上午9:35:36
//	 * 
//	 * @param cloudplatformId
//	 * @param storageSize
//	 * @param serverName
//	 * @return
//	 * @return String
//	 */
//	String createVolume(String cloudplatformId, Integer storageSize, String serverName);
//
//	/**
//	 * 
//	 * Description:创建华为磁盘
//	 * 
//	 * @param cloudplatformId
//	 * @param storageSize
//	 * @param serverName
//	 * @param zoneName
//	 * @param resourceType 0:物理机 1:虚拟机
//	 * @return
//	 * @return String
//	 */
//	String createHuaWeiVolume(String cloudplatformId, Integer storageSize,
//			String serverName, String zoneName, String resourceType);
//
//	/**
//	 * 
//	 * Description:根据磁盘id获取磁盘 Date:2015年9月17日 下午3:43:59
//	 * 
//	 * @param cloudplatformId
//	 * @param volumeId
//	 * @return
//	 * @return VolumePojo
//	 */
//	VolumePojo getVolumeById(String cloudplatformId, String volumeId);
//
//	Map<String, String> openstackNameCheck(String cloudplatformId, String serverName,
//			Map<String, String> errorMap);
//
//	/**
//	 * 云主机名重复检测
//	 * 
//	 * @param cloudplatformId
//	 * @param serverName
//	 * @param errorMap
//	 * @return
//	 */
//	Map<String, String> huaWeiOpenstackNameCheck(String cloudplatformId, String serverName,
//			Map<String, String> errorMap);
//
//	/**
//	 * 启动oracle实例
//	 * 
//	 * @param host
//	 */
//	public boolean startOrStopOracleIns(String host, String startOrStop) throws Exception;
//
//	/**
//	 * 安装rabbitMQ
//	 * 
//	 * @param host
//	 */
//	public boolean installRabbitMQ(String host) throws Exception;
//
//	/**
//	 * VCenter虚拟机挂载ISO文件
//	 * 
//	 * @param vmId 虚拟机id
//	 * @param isoPath ISO文件所在路径
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean addVCenterISO(String vmId, String isoPath) throws Exception;
//
//	/**
//	 * VCenter虚拟机卸载ISO文件
//	 * 
//	 * @param vmId 虚拟机id
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean removeVCenterISO(String vmId) throws Exception;
//
//	/**
//	 * vcenter横向扩容
//	 * 
//	 * @param vmId
//	 * @param vmName
//	 * @param loginUserInfo
//	 * @return
//	 */
//	public Map<String, String> cloudScaleoutVcenter(Long vmId, String vmName,
//			LoginUserInfo loginUserInfo, String ipAddress) throws Exception, Throwable;
//
//	/**
//	 * 根据网络ID查询合适的IP 并且预占用这个IP
//	 * 
//	 * @param vlanId
//	 * @return
//	 */
//	public TccIpConfigInfo findIpByVlanId(String vlanId);
//
//	/**
//	 * IP预占用
//	 * 
//	 * @param vlanId
//	 * @param ipAddress
//	 */
//	public void ipAddressPreOccupied(String vlanId, String ipAddress);
//
//	/**
//	 * 解除IP预占用
//	 * 
//	 * @param vlanId
//	 * @param ipAddress
//	 */
//	public void relieveIpAddressPreOccupied(String vlanId, String ipAddress);
//
//	/**
//	 * 扩容虚拟机
//	 * 
//	 * @param srtApplyedhostAssId 服务请求主表与已分配资源代码表ID
//	 * @throws Exception
//	 */
//	public boolean expandVm(TccExpdCapacity capacity, Long hostresouce,
//			List<TccStorageApplyCaseAss> storageList) throws Exception,
//			Throwable;
//
//	/**
//	 * 根据虚拟机的ID查询快照，有快照就删除快照，没有返回true
//	 */
//
//	public boolean deletVmSnapshotByvmId(String vmId, String vmUuid);
//
//	/**
//	 * 
//	 * @Description (线下安装物理机)
//	 * @param srtManageVO
//	 * @param tccProjectInfo
//	 * @return
//	 */
//	public Map<String, String> createPhysical(SrtManageVO srtManageVO,
//			TccProjectInfo tccProjectInfo) throws Throwable;
//
//	/**
//	 * 
//	 * @Description (根据集群id查询物理机)
//	 * @param clusterId 集群id
//	 * @return
//	 * @throws Throwable
//	 */
//	public List<TccPhysiscResourceInfo> getPhysicalBycluster(String clusterId) throws Throwable;
//
//	/**
//	 * 创建青云虚拟机
//	 * 
//	 * @param srtManageVO
//	 * @param tccProjectInfo
//	 * @return
//	 * @throws Throwable
//	 * @throws Exception
//	 */
//	public Map<String, String> createQingCloud(SrtManageVO srtManageVO,
//			TccProjectInfo tccProjectInfo) throws Exception, Throwable;
//
//	/**
//	 * 执行创建动作
//	 * 
//	 * @param cloudplatformId
//	 * @param serverName 虚拟机名
//	 * @param imageId 镜像ID
//	 * @param networkId 网络ID
//	 * @param cpu cpu数量
//	 * @param ramMb 内存大小，单位Mb
//	 * @param instanceType 性能类型，0：性能型 1：超高性能型
//	 * @param zoneUuid 区域ID
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable
//	 */
//	public VirtualMachinePojo qingCloudBoot(String cloudplatformId, String serverName,
//			String imageId,
//			String networkId, int cpu, int ramMb, String instanceType, String zoneUuid)
//			throws Exception, Throwable;
//
//	/**
//	 * 创建青云磁盘
//	 * 
//	 * @param cloudplatformId
//	 * @param storageSize
//	 * @param serverName
//	 * @param zoneUuid 区域ID
//	 * @return
//	 */
//	public String createQingCloudVolume(String cloudplatformId, Integer storageSize,
//			String serverName, String zoneUuid);
//
//	/**
//	 * 查询qingcloud磁盘
//	 * 
//	 * @param cloudplatformId
//	 * @param volumeId
//	 * @param zoneUuid
//	 * @return
//	 */
//	public com.sjc.adaptee.cloud.qingcloud.volume.pojo.VolumePojo getQingcloudVolumeById(
//			String cloudplatformId, String volumeId, String zoneUuid);
//
//	/**
//	 * 创建青云虚拟机完成后保存虚拟机信息
//	 * 
//	 * @param serverPojo
//	 * @param tccProjectInfo
//	 * @param assId
//	 * @param clusterId
//	 * @param zoneId
//	 * @param imageId
//	 * @param networkUuid
//	 * @param hostInfoProduceId
//	 * @param templateid
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable
//	 */
//	public String saveOrUpdateQingcloudApplyedHostResource(VirtualMachinePojo serverPojo,
//			TccProjectInfo tccProjectInfo, Long assId,
//			String clusterId, String zoneId, String imageId, String networkUuid,
//			String hostInfoProduceId,
//			long[] templateid) throws Exception, Throwable;
//
//	/**
//	 * 挂载青云磁盘
//	 * 
//	 * @param cloudplatformId
//	 * @param serverId
//	 * @param volumeId
//	 * @param serverName
//	 * @param zoneUuid
//	 * @return
//	 */
//	public String attachQingcloudVolume(String cloudplatformId, String serverId, String volumeId,
//			String serverName,
//			String zoneUuid);
//
//	/**
//	 * 创建阿里云虚拟机
//	 * 
//	 * @param srtManageVO
//	 * @param tccProjectInfo
//	 * @return
//	 * @throws Throwable
//	 * @throws Exception
//	 */
//	public Map<String, String> createAliYun(SrtManageVO srtManageVO, TccProjectInfo tccProjectInfo)
//			throws Exception, Throwable;
//
//	/**
//	 * 执行创建动作
//	 * 
//	 * @param cloudplatformId
//	 * @param serverName 虚拟机名
//	 * @param imageId 镜像ID
//	 * @param networkId 网络ID
//	 * @param cpu cpu数量
//	 * @param ramMb 内存大小，单位Mb
//	 * @param instanceType 性能类型，0：性能型 1：超高性能型
//	 * @param zoneUuid 区域ID
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable
//	 */
//	public InstancePojo aliYunBoot(String cloudplatformId, String serverName, String imageId,
//			String networkId, int cpu, int ramMb, String instanceType, String zoneUuid)
//			throws Exception, Throwable;
//
//	/**
//	 * 创建阿里云磁盘
//	 * 
//	 * @param cloudplatformId
//	 * @param storageSize
//	 * @param serverName
//	 * @param zoneUuid 区域ID
//	 * @return
//	 */
//	public String createAliYunVolume(String cloudplatformId, Integer storageSize, String serverName,
//			String zoneUuid, String zoneId);
//
//	/**
//	 * 查询阿里云磁盘
//	 * 
//	 * @param cloudplatformId
//	 * @param volumeId
//	 * @param zoneUuid
//	 * @return
//	 */
//	public DiskPojo getAliYunVolumeById(String cloudplatformId, String volumeId, String zoneUuid);
//
//	/**
//	 * 创建青云虚拟机完成后保存虚拟机信息
//	 * 
//	 * @param serverPojo
//	 * @param tccProjectInfo
//	 * @param assId
//	 * @param clusterId
//	 * @param zoneId
//	 * @param imageId
//	 * @param networkUuid
//	 * @param hostInfoProduceId
//	 * @param templateid
//	 * @return
//	 * @throws Exception
//	 * @throws Throwable
//	 */
//	public String saveOrUpdateAliYunApplyedHostResource(InstancePojo serverPojo,
//			TccProjectInfo tccProjectInfo, Long assId,
//			String clusterId, String zoneId, String imageId, String networkUuid,
//			String hostInfoProduceId,
//			long[] templateid) throws Exception, Throwable;
//
//	/**
//	 * 挂载青云磁盘
//	 * 
//	 * @param cloudplatformId
//	 * @param serverId
//	 * @param volumeId
//	 * @param serverName
//	 * @param zoneUuid
//	 * @return
//	 */
//	public String attachAliYunVolume(String cloudplatformId, String serverId, String volumeId,
//			String serverName,
//			String zoneUuid);
//
//	/**
//	 * 检查阿里云虚拟机名称是否重复
//	 * 
//	 * @param cloudplatformId
//	 * @param serverName
//	 * @param errorMap
//	 * @return
//	 */
//	public Map<String, String> aliYunNameCheck(String cloudplatformId,
//			String serverName, Map<String, String> errorMap, String zoneId);
//
//	/**
//	 * 添加虚拟机备注
//	 * 
//	 * @throws Throwable
//	 */
//	public void addVmRemark(Long vmId, String remark) throws Exception, Throwable;
//
//	/**
//	 * 添加vc虚拟机备注
//	 */
//	public void addVcRemark(String remark, String vmName, String cloudPaltformId)
//			throws Exception, Throwable;
}
