package com.cmp.service.bi;

import java.util.List;

import com.fh.util.PageData;

/**
 * 
 * @author liuweixing
 *
 */
public interface BiSoftwareBillService {

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
	
	/**
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listVSoftwareBill(PageData pd) throws Exception;
	
	/**
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllSoftwareBIData(PageData pd) throws Exception;
}
