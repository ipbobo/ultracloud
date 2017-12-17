package com.cmp.service.resourcemgt;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 宿主机管理业务层接口
 * 
 * @author liuweixing
 *
 */
public interface HostmachineService {

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
	 * KVM列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listKVM(Page page) throws Exception;

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
	
	/**
	 * 查询虚拟机列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listVirtual(Page page) throws Exception;

}
