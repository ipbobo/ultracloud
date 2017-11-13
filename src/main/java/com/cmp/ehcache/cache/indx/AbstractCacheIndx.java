package com.cmp.ehcache.cache.indx;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.cmp.ehcache.cache.CacheException;
import com.cmp.ehcache.util.StringUtil;

public abstract class AbstractCacheIndx implements ICacheIndx {
	protected Map<Object, Set<Object>> indxMap = new ConcurrentHashMap<Object, Set<Object>>();
	protected String indxName;

	public AbstractCacheIndx(String indxName) {
		this.indxName = indxName;
	}

	public synchronized void addEntity(Object entityKey, Object entity) throws CacheException {
		Object indxValue = getIndxValue(entity, indxName);
		Set<Object> entityKeySet = indxMap.get(indxValue);
		if (entityKeySet == null) {
			entityKeySet = new ConcurrentSkipListSet<Object>();
			indxMap.put(indxValue, entityKeySet);
		}
		
		entityKeySet.add(entityKey);
	}

	public synchronized void removeEntity(Object entityKey, Object entity) throws CacheException {
		Object indxValue = getIndxValue(entity, indxName);
		Set<Object> entityKeys = indxMap.get(indxValue);
		if (entityKeys != null) {
			entityKeys.remove(entityKey);
			if (entityKeys.size() == 0)
				indxMap.remove(indxValue);
		}
	}

	public void clear() throws CacheException {
		indxMap.clear();
	}

	public String getIndxName() {
		return indxName;
	}
	
	public Map<Object, Set<Object>> getIndxMap() throws CacheException{
		return indxMap;
	}
	
	public Object getIndxValue(Object entity, String indxName) throws CacheException {
		if(entity.getClass().getName().equals(indxName)){//类级别索引
			return indxName;
		}
		
		return StringUtil.getEntityValue(entity, indxName);//非类级别索引
	}
}
