package com.cmp.ehcache.cache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.cmp.ehcache.cache.indx.ICacheIndx;

//索引管理,实体管理器更新实体的同时会把实体交给索引管理器 索引管理器把实体交给该实体对应的所有索引，每个索引负责更新自己
public class CacheIndxMgr {
	private Map<Class<?>, Map<String, ICacheIndx>> allCacheIndxMap = new TreeMap<Class<?>, Map<String, ICacheIndx>>(
		new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return ((Class<?>) o1).getName().compareTo(((Class<?>) o2).getName());
			}
		}
	);

	public void registerEntity(Class<?> entityClass) {
		allCacheIndxMap.put(entityClass, new HashMap<String, ICacheIndx>());
	}

	public void regsiterCacheIndx(Class<?> entityClass, ICacheIndx cacheIndx) throws CacheException {
		Map<String, ICacheIndx> cacheIndxMap = allCacheIndxMap.get(entityClass);
		CacheException.asserts(cacheIndxMap != null, "未注册实体" + entityClass.getName());
		cacheIndxMap.put(cacheIndx.getIndxName(), cacheIndx);
	}

	public Map<String, ICacheIndx> getCacheIndxMap(Class<?> entityClass) {
		return allCacheIndxMap.get(entityClass);
	}
	
	public Set<Class<?>> getEntityClasses() {
		return allCacheIndxMap.keySet();
	}

	public Map<Class<?>, Map<String, ICacheIndx>> getAllCacheIndxMap() {
		return allCacheIndxMap;
	}

	public ICacheIndx getCacheIndx(Class<?> entityClass, String indxName) throws CacheException {
		Map<String, ICacheIndx> cacheIndxMap=allCacheIndxMap.get(entityClass);
		CacheException.asserts(cacheIndxMap != null, "未注册实体" + entityClass.getName());
		return cacheIndxMap.get(indxName);
	}

	public void addEntity(final Object entityKey, final Object entity) throws CacheException {
		Map<String, ICacheIndx> cacheIndxMap = allCacheIndxMap.get(entity.getClass());
		CacheException.asserts(cacheIndxMap != null, "未注册实体" + entity.getClass().getName());
		for (ICacheIndx cacheIndx : cacheIndxMap.values()) {
			cacheIndx.addEntity(entityKey, entity);
		}
	}

	public void removeEntity(final Object entityKey, final Object entity) throws CacheException {
		Map<String, ICacheIndx> cacheIndxMap = allCacheIndxMap.get(entity.getClass());
		CacheException.asserts(cacheIndxMap != null, "未注册实体" + entity.getClass().getName());
		for (ICacheIndx cacheIndx : cacheIndxMap.values()) {
			cacheIndx.removeEntity(entityKey, entity);
		}
	}

	public void clear(Class<?> entityClass) throws CacheException {
		Map<String, ICacheIndx> cacheIndxMap = allCacheIndxMap.get(entityClass);
		CacheException.asserts(cacheIndxMap != null, "未注册实体" + entityClass.getName());
		for (ICacheIndx cacheIndx : cacheIndxMap.values()) {
			cacheIndx.clear();
		}
	}
}
