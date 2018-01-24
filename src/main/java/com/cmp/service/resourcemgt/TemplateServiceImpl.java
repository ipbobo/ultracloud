package com.cmp.service.resourcemgt;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 模板业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("templateService")
public class TemplateServiceImpl implements TemplateService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("VirtualMMapper.datalistPage", page);
	}

}
