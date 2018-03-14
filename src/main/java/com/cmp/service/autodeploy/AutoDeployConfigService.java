package com.cmp.service.autodeploy;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 自动化部署业务层接口
 * 
 * @author liuweixing
 *
 */
public interface AutoDeployConfigService {

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
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception;
	
	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

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
	 * 列出所有已加入的节点列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listAllInNodeById(PageData pd) throws Exception;

	/**
	 * 列出所有未加入的节点列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listAllOutNodeById(PageData pd) throws Exception;

	/**
	 * 配置节点列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllConfigNode(PageData pd) throws Exception;

	/**
	 * 批量删除配置节点
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllConfigNode(String config_id) throws Exception;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveConfigNode(PageData pd) throws Exception;
	
	/**
	 * 配置部署节点
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void bind(String id, String nodeIds[]) throws Exception;
	
	/**
	 * 查询全部
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findAll() throws Exception;

}
