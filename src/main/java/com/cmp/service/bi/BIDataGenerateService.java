package com.cmp.service.bi;


/**
 * BI数据生成 业务层接口
 * 
 * @author liuweixing
 *
 */
public interface BIDataGenerateService {

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
}
