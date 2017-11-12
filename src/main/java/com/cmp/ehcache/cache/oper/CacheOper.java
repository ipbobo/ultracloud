package com.cmp.ehcache.cache.oper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmp.ehcache.cache.CacheException;

public abstract class CacheOper {
	public static final Logger logger = LoggerFactory.getLogger(CacheOper.class);
	private String cacheId;
	private Map<String, String> params;
	
	public CacheOper() {
		params = new HashMap<String, String>();
	}
	
	public abstract void init() throws CacheException;

	public abstract Object getEntity(Class<?> clazz, Object key) throws CacheException;

	public abstract void putEntity(Object key, Object entity) throws CacheException;

	public abstract boolean removeEntity(Class<?> clazz, Object key) throws CacheException;

	public abstract long size(Class<?> clazz) throws CacheException;

	public abstract long count(Class<?> clazz) throws CacheException;

	public abstract long hits(Class<?> clazz) throws CacheException;

	public abstract long clear(Class<?> clazz) throws CacheException;

	public abstract List<?> getList(Class<?> clazz) throws CacheException;
	
	public void addParam(String name, String value) {
		params.put(name, value);
	}

	public String getParam(String name) {
		return (String) params.get(name);
	}

	public String getCacheId() {
		return cacheId;
	}

	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}
}
