package com.cmp.service;

import java.util.List;

import com.cmp.entity.Medium;
import com.cmp.sid.CmpDict;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 介质业务层接口
 * 
 * @version
 */
public interface MediumService {

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

	/**
	 * 查询出所有符合条件的列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Medium> listAllMediumByPId(PageData pd) throws Exception;
	
	/**
	 * 查询所有软件类型
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllSoftType(PageData pd) throws Exception;

	//软件列表查询
	public List<CmpDict> getSoftList() throws Exception;
	
	//软件参数列表查询
	public List<CmpDict> getSoftParamList(String softCode) throws Exception;
}