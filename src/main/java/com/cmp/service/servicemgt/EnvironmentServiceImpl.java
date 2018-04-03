package com.cmp.service.servicemgt;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.CmpDict;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 环境业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("environmentService")
public class EnvironmentServiceImpl implements EnvironmentService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "areaEnvironmentService")
	private AreaEnvironmentService areaEnvironmentService;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("EnvironmentMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("EnvironmentMapper.delete", pd);
		PageData delPD = new PageData();
		delPD.put("environment_id", pd.getString("id"));
		areaEnvironmentService.delete(delPD);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("EnvironmentMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("EnvironmentMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("EnvironmentMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("EnvironmentMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("EnvironmentMapper.deleteAll", ArrayDATA_IDS);
		for (String id : ArrayDATA_IDS) {
			PageData delPD = new PageData();
			delPD.put("environment_id", id);
			this.areaEnvironmentService.delete(delPD);
		}
	}

	/**
	 * 已选环境
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllInById(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("EnvironmentMapper.listAllInById", pd);
	}

	// 环境列表查询
	@SuppressWarnings("unchecked")
	public List<CmpDict> getEnvList(String areaCodeId) throws Exception {
		PageData pd=new PageData("areaCodeId", areaCodeId);
		return (List<CmpDict>) dao.findForList("EnvironmentMapper.getEnvList", pd);
	}
}