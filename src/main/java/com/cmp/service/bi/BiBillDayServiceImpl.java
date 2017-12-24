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
@Service("biBillDayService")
public class BiBillDayServiceImpl implements BiBillDayService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listVBiVirtualBill(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("BiBillDayMapper.listVBiVirtualBill", pd);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("BiBillDayMapper.save", pd);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BiBillDayMapper.listAll", pd);
	}
	
	/**列表
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listBillGroupByVirtualId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("BiBillDayMapper.listBillGroupByVirtualId", pd);
	}
	
	/**资源使用列表
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listResourceGroupByVirtualId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("BiBillDayMapper.listResourceGroupByVirtualId", pd);
	}

}
