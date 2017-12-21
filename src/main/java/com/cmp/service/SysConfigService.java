package com.cmp.service;

import com.cmp.sid.SysConfigInfo;
import com.fh.util.PageData;

public interface SysConfigService {
	public void update(PageData pd) throws Exception;
	public SysConfigInfo getSysConfig() throws Exception;
}
