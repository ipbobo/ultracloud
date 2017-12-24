package com.cmp.service.bi;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

/**
 * 
 * @author liuweixing
 *
 */
@Service("biBillMonthService")
public class BiBillMonthServiceImpl implements BiBillMonthService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("BiBillMonthMapper.delete", pd);
	}
	
	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("BiBillMonthMapper.save", pd);
	}
}
