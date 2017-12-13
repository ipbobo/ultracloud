package com.cmp.service.servicemgt;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.CmpDict;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.util.PageData;

/**
 * 虚拟机命名规则业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("virtualNameRuleService")
public class VirtualNameRuleServiceImpl implements VirtualNameRuleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dictionariesService;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("VirtualNameRuleMapper.save", pd);
		if("1".equals(pd.getString("isDefault"))) {
			dao.update("VirtualNameRuleMapper.udpateDefaultData", pd);
		}
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("VirtualNameRuleMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("VirtualNameRuleMapper.edit", pd);
		if("1".equals(pd.getString("isDefault"))) {
			dao.update("VirtualNameRuleMapper.udpateDefaultData", pd);
		}
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("VirtualNameRuleMapper.datalistPage", page);
		List<Dictionaries> prefix_dictionariesList = dictionariesService.listSubDictByBianma("virtualnamerule_prefix");
		List<Dictionaries> suffix_dictionariesList = dictionariesService.listSubDictByBianma("virtualnamerule_suffix");
		for(PageData pageData : list) {
			String prefix = pageData.getString("prefix");
			String suffix = pageData.getString("suffix");
			for(Dictionaries d : prefix_dictionariesList) {
				if(prefix.equals(d.getBIANMA())) {
					pageData.put("prefix_name", d.getNAME());
				} 
			}
			for(Dictionaries d : suffix_dictionariesList) {
				if(suffix.equals(d.getBIANMA())) {
					pageData.put("suffix_name", d.getNAME());
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
		return (List<PageData>) dao.findForList("VirtualNameRuleMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("VirtualNameRuleMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("VirtualNameRuleMapper.deleteAll", ArrayDATA_IDS);
	}
	
	//环境列表查询
	@SuppressWarnings("unchecked")
	public List<CmpDict> getEnvList() throws Exception {
		return (List<CmpDict>) dao.findForList("VirtualNameRuleMapper.getEnvList", null);
	}
}