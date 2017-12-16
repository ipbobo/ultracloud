package com.cmp.service.resourcemgt;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 存量资源绑定 业务层接口
 * 
 * @author liuweixing
 *
 */
public interface VirtualBindingService {
	
	/**
	 * 加载初始化数据
	 * @param mv
	 * @throws Exception
	 */
	public void loadInitData(ModelAndView mv, PageData pd) throws Exception;

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
}
