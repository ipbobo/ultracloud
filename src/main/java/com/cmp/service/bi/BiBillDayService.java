package com.cmp.service.bi;

import java.util.List;

import com.fh.util.PageData;

/**
 * 
 * @author liuweixing
 *
 */
public interface BiBillDayService {
	

	/**
	 * 查询v_bi_virtual_bill视图
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listVBiVirtualBill(PageData pd) throws Exception;
	
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
	
	/**费用报表列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listBillGroupByVirtualId(PageData pd) throws Exception ;
	
	/**资源使用列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listResourceGroupByVirtualId(PageData pd) throws Exception ;
	
}
