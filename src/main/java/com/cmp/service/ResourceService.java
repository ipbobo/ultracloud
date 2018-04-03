package com.cmp.service;

import java.util.List;

import com.cmp.entity.tcc.TccVirtualMachine;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 资源管理业务层接口
 * 
 * @author liuweixing
 *
 */
public interface ResourceService {

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
	 * @param pd
	 * @throws Exception
	 */
	public List<TccVirtualMachine> syncMirroTemplate(PageData pd) throws Exception;
	
	/**
	 * 更新同步数据为选中并复制到正式表中
	 * @param hostmachineIds
	 * @param storageIds
	 * @param dcnIds
	 * @throws Exception
	 */
	public void updateSelectData(String hostmachineIds, String storageIds, String dcnIds) throws Exception;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception;

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception;

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception;

}
