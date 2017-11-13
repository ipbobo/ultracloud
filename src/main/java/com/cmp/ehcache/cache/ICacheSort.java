package com.cmp.ehcache.cache;

import java.util.Set;

public interface ICacheSort {
	public abstract Set<Object> sort(Set<Object> paramSet, boolean paramBoolean) throws CacheException;
}
