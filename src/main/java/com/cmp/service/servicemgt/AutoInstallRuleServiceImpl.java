package com.cmp.service.servicemgt;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.service.ClusterService;
import com.cmp.sid.CmpDict;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.util.PageData;
import com.hazelcast.util.StringUtil;

/**
 * 自动安装业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("autoInstallRuleService")
public class AutoInstallRuleServiceImpl implements AutoInstallRuleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dictionariesService;
	
	@Resource(name = "clusterService")
	private ClusterService clusterService;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		PageData pd2 = new PageData();
		pd2.put("id", pd.getString("cluster_id"));
		pd2 = clusterService.findById(pd2);
		pd.put("type", pd2.getString("type"));
		
		dao.save("AutoInstallRuleMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("AutoInstallRuleMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("AutoInstallRuleMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("AutoInstallRuleMapper.datalistPage", page);
		List<Dictionaries> autoInstallRuleList = dictionariesService.listGrandsonDictByBianma("autoinstall_rule");
		for(PageData pageData : list) {
			String num_rule = pageData.getString("num_rule");
			String storage_rule = pageData.getString("storage_rule");
			String ip_rule = pageData.getString("ip_rule");
			String openstack_rule = pageData.getString("openstack_rule");
			for(Dictionaries d : autoInstallRuleList) {
				if(!StringUtil.isNullOrEmpty(num_rule) && num_rule.equals(d.getBIANMA())) {
					pageData.put("num_rule_name", d.getNAME());
				} else if(!StringUtil.isNullOrEmpty(storage_rule) && storage_rule.equals(d.getBIANMA())) {
					pageData.put("storage_rule_name", d.getNAME());
				} else if(!StringUtil.isNullOrEmpty(ip_rule) && ip_rule.equals(d.getBIANMA())) {
					pageData.put("ip_rule_name", d.getNAME());
				} else if(!StringUtil.isNullOrEmpty(openstack_rule) && openstack_rule.equals(d.getBIANMA())) {
					pageData.put("openstack_rule_name", d.getNAME());
				}
			}
		}
		
		return list;
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AutoInstallRuleMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AutoInstallRuleMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("AutoInstallRuleMapper.deleteAll", ArrayDATA_IDS);
	}
	
	//环境列表查询
	@SuppressWarnings("unchecked")
	public List<CmpDict> getEnvList() throws Exception {
		return (List<CmpDict>) dao.findForList("AutoInstallRuleMapper.getEnvList", null);
	}
}