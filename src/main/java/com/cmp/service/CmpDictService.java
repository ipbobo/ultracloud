package com.cmp.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cmp.ehcache.AbstractDao;
import com.cmp.sid.CmpDict;

@Service
public class CmpDictService extends AbstractDao<CmpDict, Long> {
	private static Logger logger = Logger.getLogger(CmpDictService.class);

	//数据字典列表查询
	public List<CmpDict> getCmpDictList(String dictType) {
		try {
			List<CmpDict> cmpDictList=getCacheQuery().eq("dictType", dictType).getList();
			Collections.sort(cmpDictList);//排序
			return cmpDictList;
		} catch (Exception e) {
			logger.error("数据字典列表查询时错误："+e);
			return null;
		}
	}
	
	//数据字典列表查询
	public Map<String, String> getCmpDictMap(String dictType) {
		try {
			Map<String, String> dictMap=new LinkedHashMap<String, String>();
			List<CmpDict> cmpDictList=getCacheQuery().eq("dictType", dictType).getList();
			if(cmpDictList!=null && !cmpDictList.isEmpty()){
				for(CmpDict cmpDict: cmpDictList){
					dictMap.put(cmpDict.getDictCode(), cmpDict.getDictValue());
				}
			}
			
			return dictMap;
		} catch (Exception e) {
			logger.error("数据字典列表查询时错误："+e);
			return null;
		}
	}
	
	//获取数据字典
	public CmpDict getCmpDict(String dictType, String dictCode) {
		try {
			return getCacheQuery().eq("dictType", dictType).eq("dictCode", dictCode).getUniqueResult();
		} catch (Exception e) {
			logger.error("获取数据字典时错误："+e);
			return null;
		}
	}
	
	//获取数据字典名称
	public String getDictValue(String dictType, String dictCode) {
		CmpDict cmpDict=getCmpDict(dictType, dictCode);
		if(cmpDict!=null){
			return cmpDict.getDictValue();
		}
		
		return null;
	}

	//重新加载缓存
	public void reloadCache() {
		try {
			super.reloadCache();//重新加载缓存
		} catch (Exception e) {
			logger.error("重新加载缓存时错误："+e);
		}
	}
	
	@Override
	public String getMybatisQryFunc() {
		return "CmpDictMapper.getCmpDictList";
	}
}