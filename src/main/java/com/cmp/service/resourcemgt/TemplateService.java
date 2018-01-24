package com.cmp.service.resourcemgt;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 模板 业务层接口
 * 
 * @author liuweixing
 *
 */
public interface TemplateService {

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;
}
