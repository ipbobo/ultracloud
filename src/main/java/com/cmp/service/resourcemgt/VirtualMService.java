package com.cmp.service.resourcemgt;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 虚拟机 业务层接口
 * 
 * @author liuweixing
 *
 */
public interface VirtualMService {

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;
}
