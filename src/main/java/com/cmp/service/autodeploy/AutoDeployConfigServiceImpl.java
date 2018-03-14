package com.cmp.service.autodeploy;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 自动化部署业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("autoDeployConfigService")
public class AutoDeployConfigServiceImpl implements AutoDeployConfigService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("AutoDeployConfigMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("AutoDeployConfigMapper.delete", pd);
	}
	
	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("AutoDeployConfigMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AutoDeployConfigMapper.findById", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("AutoDeployConfigMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("AutoDeployConfigMapper.datalistPage", page);
	}
	
	/**
	 * 列出所有已加入的节点列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllInNodeById(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AutoDeployConfigMapper.listAllInNodeById", pd);
	}
	
	/**
	 * 列出所有未加入的节点列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllOutNodeById(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AutoDeployConfigMapper.listAllOutNodeById", pd);
	}

	/**
	 * 配置节点列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllConfigNode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AutoDeployConfigMapper.listAllConfigNode", pd);
	}
	
	/**
	 * 批量删除配置节点
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllConfigNode(String config_id) throws Exception {
		dao.delete("AutoDeployConfigMapper.deleteAllConfigNode", config_id);
	}

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveConfigNode(PageData pd) throws Exception {
		dao.save("AutoDeployConfigMapper.saveConfigNode", pd);
	}
	
	/**
	 * 配置部署节点
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void bind(String id, String [] nodeIds) throws Exception {
		this.deleteAllConfigNode(id);
		int i = 1;
		for(String nodeId : nodeIds) {
			PageData pd = new PageData();
			pd.put("config_id", id);
			pd.put("node_id", nodeId);
			pd.put("ordernum", i++);
			this.saveConfigNode(pd);
		}
	}
	
	@Override
	public List<PageData> findAll() throws Exception {
		return (List<PageData>)dao.findForList("AutoDeployConfigMapper.findAll", null);
	}

}
