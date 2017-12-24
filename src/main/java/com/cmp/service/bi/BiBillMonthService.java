package com.cmp.service.bi;

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
}
