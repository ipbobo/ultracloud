package com.cmp.service.bi;

import java.util.List;

import com.fh.util.PageData;

/**
 * BI数据生成 业务层接口
 * 
 * @author liuweixing
 *
 */
public interface BIDatacenterService {

	/**
	 * BI数据生成
	 * 
	 * @param type
	 * @param ip
	 * @param useranme
	 * @param password
	 * @throws Exception
	 */
	public void biDataGenerateHandler() throws Exception;

	/**
	 * 按天查询虚拟机费用列表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllBillDay(PageData pd) throws Exception;
	
	/**
	 * 按月查询虚拟机费用列表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllBillMonth(PageData pd) throws Exception;
	
	/**
	 * 按天查询虚拟机费用列表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listBillGroupByVirtualId(PageData pd) throws Exception;
}
