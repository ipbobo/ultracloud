package com.cmp.service.bi;

import java.util.List;

import com.fh.util.PageData;

/**
 * 
 * @author liuweixing
 *
 */
public interface BiBillMonthService {

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;
	
	/**列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception ;
	

}
