package com.cmp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.SysConfigInfo;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public void update(PageData pd) throws Exception {
		dao.update("SysConfigMapper.edit", pd);
	}

	@Override
	public SysConfigInfo getSysConfig() throws Exception {
		return (SysConfigInfo) dao.findForObject("SysConfigMapper.getConfig", null);
	}

}
