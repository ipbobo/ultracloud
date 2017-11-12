package com.cmp.ehcache.cache.indx.impl;

import java.util.Set;

import com.cmp.ehcache.cache.ICacheKeyQuery;
import com.cmp.ehcache.cache.indx.AbstractCacheIndx;
import com.cmp.ehcache.cache.query.DefaultEqualQuery;

public class CacheIndxDefault extends AbstractCacheIndx {
	public CacheIndxDefault(String indxName) {
		super(indxName);
	}

	public ICacheKeyQuery createEqQuery(Object indxValue) {
		return new DefaultEqualQuery(indxMap, indxValue);
	}

	public ICacheKeyQuery createGreatQuery(Object indxValue, boolean equal) {
		throw new UnsupportedOperationException("CacheIndxDefault不支持大于操作");
	}

	public ICacheKeyQuery createLessQuery(Object indxValue, boolean equal) {
		throw new UnsupportedOperationException("CacheIndxDefault不支持小于操作");
	}

	public Set<Object> sort(Set<Object> entityKeys, boolean asc) {
		throw new UnsupportedOperationException("CacheIndxDefault不支持排序操作");
	}
}
