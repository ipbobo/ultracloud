package com.cmp.service.resourcemgt;

import java.util.List;

import com.cmp.sid.CmpDict;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 云平台  业务层接口
 * 
 * @author liuweixing
 *
 */
public interface CloudplatformService {

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd, boolean isSyncTable) throws Exception;

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd, boolean isSyncTable) throws Exception;

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd, boolean isSyncTable) throws Exception;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page, boolean isSyncTable) throws Exception;

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd, boolean isSyncTable) throws Exception;

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd, boolean isSyncTable) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS, boolean isSyncTable) throws Exception;

	//平台类型列表查询
	public List<CmpDict> getPlatTypeList(String areaCodeId, String envCodeId) throws Exception;
}
