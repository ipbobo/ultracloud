package com.cmp.service;

import java.util.List;

import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.mgr.CloudArchManager;
import com.fh.util.PageData;

/**
 * 资源管理业务层接口
 * 
 * @author liuweixing
 *
 */
public interface ResourceService {

	/**
	 * 连接云平台
	 * 
	 * @param cloudPD
	 * @return
	 */
	public CloudArchManager initCloud(PageData cloudPD);

	/**
	 * 同步云平台数据
	 * 
	 * @param type
	 * @param ip
	 * @param useranme
	 * @param password
	 * @throws Exception
	 */
	public void syncCloudData(PageData pd) throws Exception;

	/**
	 * 同步镜像模板
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<TccVirtualMachine> syncMirroTemplate(PageData pd) throws Exception;

	/**
	 * 更新同步数据为选中并复制到正式表中
	 * 
	 * @param hostmachineIds
	 * @param storageIds
	 * @param dcnIds
	 * @throws Exception
	 */
	public void updateSelectData(String hostmachineIds, String storageIds, String dcnIds) throws Exception;

	/**
	 * 删除虚拟机时进行资源释放
	 * 
	 * @param vmId
	 * @throws Exception
	 */
	public void releaseResource(String type, String[] vmId) throws Exception;

}
