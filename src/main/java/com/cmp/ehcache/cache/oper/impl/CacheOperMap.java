package com.cmp.ehcache.cache.oper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cmp.ehcache.cache.CacheException;
import com.cmp.ehcache.cache.oper.CacheOper;

public class CacheOperMap extends CacheOper {
	private Map<Object, Object> cacheMap;

	public CacheOperMap() {
		cacheMap = new ConcurrentHashMap<Object, Object>();
	}

	public void init() {
	}
	
	public Object getEntity(Class<?> clazz, Object key) {
		return cacheMap.get(key);
	}

	public void putEntity(Object key, Object value) {
		cacheMap.put(key, value);
	}

	public boolean removeEntity(Class<?> clazz, Object key) {
		return cacheMap.remove(key) != null;
	}

	public long size(Class<?> clazz) {
		return 0L;
	}

	public long count(Class<?> clazz) throws CacheException {
		return cacheMap.size();
	}

	public long hits(Class<?> clazz) throws CacheException {
		return 0L;
	}

	public long clear(Class<?> clazz) throws CacheException {
		return 0L;
	}

	public List<?> getList(Class<?> clazz) throws CacheException {
		List<Object> list = new ArrayList<Object>();
		list.addAll(cacheMap.values());
		return list;
	}
}
