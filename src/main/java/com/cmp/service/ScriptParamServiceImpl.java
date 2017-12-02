package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

/**
 * 脚本参数业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("scriptParamService")
public class ScriptParamServiceImpl implements ScriptParamService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("ScriptParamMapper.save", pd);
	}

	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("ScriptParamMapper.delete", pd);
	}

	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("ScriptParamMapper.edit", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ScriptParamMapper.listAll", pd);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ScriptParamMapper.findById", pd);
	}

	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("ScriptParamMapper.deleteAll", ArrayDATA_IDS);
	}

}
