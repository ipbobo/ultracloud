package com.cmp.service.bi;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

/**
 * 
 * @author liuweixing
 *
 */
@Service("biSoftwareBillService")
public class BiSoftwareBillServiceImpl implements BiSoftwareBillService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("BiSoftwareBillMapper.delete", pd);
	}

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("BiSoftwareBillMapper.save", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listVSoftwareBill(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("BiSoftwareBillMapper.listVSoftwareBill", pd);
	}
	
	/**
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllSoftwareBIData(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("BiSoftwareBillMapper.listAllSoftwareBIData", pd);
	}
}
