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
	 * 计费表报列表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listBillBIData(PageData pd) throws Exception;
	
	/**
	 * 资源使用表报列表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listResourceBIData(PageData pd) throws Exception;
	
	/**
	 * 软件台帐表报列表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listSoftwareBIData(PageData pd) throws Exception;
}
