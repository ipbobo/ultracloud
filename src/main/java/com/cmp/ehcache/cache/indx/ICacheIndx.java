package com.cmp.ehcache.cache.indx;

import java.util.Map;
import java.util.Set;

import com.cmp.ehcache.cache.CacheException;
import com.cmp.ehcache.cache.ICacheSort;
import com.cmp.ehcache.cache.ICacheKeyQuery;

public interface ICacheIndx extends ICacheSort {
	public abstract void addEntity(Object entityKey, Object entity) throws CacheException;

	public abstract void removeEntity(Object entityKey, Object entity) throws CacheException;

	public abstract ICacheKeyQuery createEqQuery(Object obj) throws CacheException;

	public abstract ICacheKeyQuery createLessQuery(Object obj, boolean bool) throws CacheException;

	public abstract ICacheKeyQuery createGreatQuery(Object obj, boolean bool) throws CacheException;

	public abstract void clear() throws CacheException;

	public abstract String getIndxName();

	public abstract Map<Object, Set<Object>> getIndxMap() throws CacheException;
}
