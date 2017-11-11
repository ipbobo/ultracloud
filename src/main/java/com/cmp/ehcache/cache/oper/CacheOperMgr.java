package com.cmp.ehcache.cache.oper;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.cmp.ehcache.cache.CacheConfig;
import com.cmp.ehcache.cache.CacheException;

public class CacheOperMgr{
	private Map<String, CacheOper> cacheMap = new HashMap<String, CacheOper>();
	private Map<Class<?>, String> entityCacheMap = new HashMap<Class<?>, String>();
	private CacheOper defaultCacheOper;

	public void init() throws CacheException {
	}
	
	public void addCacheOper(CacheOper cacheOper, boolean defaultCache) {
		cacheMap.put(cacheOper.getCacheId(), cacheOper);
		if (defaultCache){
			defaultCacheOper = cacheOper;
		}
	}

	public void addEntityCacheMap(Class<?> entityClass, String cacheOperId) throws CacheException {
		if (cacheOperId != null && cacheMap.get(cacheOperId) == null) {
			throw new CacheException("无" + cacheOperId + "缓存," + entityClass.getName());
		}
		
		entityCacheMap.put(entityClass, cacheOperId);
	}

	public String getId(Class<?> entityClass) {
		return getCacheOper(entityClass).getCacheId();
	}

	private CacheOper getCacheOper(Class<?> entityClass) {
		String cacheOperId = (String) entityCacheMap.get(entityClass);
		CacheOper cacheOper = cacheMap.get(cacheOperId);
		if(cacheOper!=null){
			return cacheOper;
		}
		
		return defaultCacheOper;
	}

	public long clear(Class<?> entityClass) throws CacheException {
		return getCacheOper(entityClass).clear(entityClass);
	}

	public long count(Class<?> entityClass) throws CacheException {
		return getCacheOper(entityClass).count(entityClass);
	}

	public Object getEntityKey(Object id, Class<?> entityClass) throws CacheException {
		return entityClass.getSimpleName() + "_" + id;
	}

	public Object getEntity(Class<?> entityClass, Object key) throws CacheException {
		return getCacheOper(entityClass).getEntity(entityClass, key);
	}

	public long hits(Class<?> entityClass) throws CacheException {
		return getCacheOper(entityClass).hits(entityClass);
	}

	public void putEntity(Object entityKey, Object entity) throws CacheException {
		getCacheOper(entity.getClass()).putEntity(entityKey, entity);
	}

	public boolean removeEntity(Class<?> entityClass, Object entityKey) throws CacheException {
		return getCacheOper(entityClass).removeEntity(entityClass, entityKey);
	}

	public long size(Class<?> entityClass) throws CacheException {
		return getCacheOper(entityClass).size(entityClass);
	}

	public CacheOper getDefaultCacheOper() {
		return defaultCacheOper;
	}

	public List<?> getList(Class<?> entityClass) throws CacheException {
		return getCacheOper(entityClass).getList(entityClass);
	}
	
	public Object getEntityZip(Class<?> clazz, Object key) throws CacheException {
		Object entity = getEntity(clazz, key);
		return CacheConfig.isZip(clazz)?unzip(entity):entity;
	}
	
	public List<?> getEntityZipList(Class<?> clazz) throws CacheException {
		List<?> entityList = getList(clazz);
		if (!CacheConfig.isZip(clazz)) {
			return entityList;
		}

		List<Object> zipList = new ArrayList<Object>();
		for (Iterator<?> it = entityList.iterator(); it.hasNext();) {
			zipList.add(unzip(it.next()));
		}
		
		return zipList;
	}
	
	public Object unzip(Object entity) throws CacheException {
		try {
			if (entity == null){
				return null;
			}
			
			GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream((byte[]) entity));
			ObjectInputStream input = new ObjectInputStream(gzip);
			return input.readObject();
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}
}
