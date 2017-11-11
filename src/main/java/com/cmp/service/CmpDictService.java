package com.cmp.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cmp.ehcache.AbstractDao;
import com.cmp.sid.CmpDict;

@Service
public class CmpDictService extends AbstractDao<CmpDict, Long> {
	private static Logger logger = Logger.getLogger(CmpDictService.class);

	public List<CmpDict> getCmpDictList(String dictType) {
		try {
			return getCacheQuery().eq("dictType", dictType).getList();
		} catch (Exception e) {
			logger.error("获取数据字典列表时错误："+e);
			return null;
		}
	}
	
	public CmpDict getCmpDict(String dictType, String dictCode) {
		try {
			return getCacheQuery().eq("dictType", dictType).eq("dictCode", dictCode).getUniqueResult();
		} catch (Exception e) {
			logger.error("获取数据字典时错误："+e);
			return null;
		}
	}

	@Override
	public String getMybatisQryFunc() {
		return "CmpDictMapper.getCmpDictList";
	}
}