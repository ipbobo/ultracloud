package com.cmp.ehcache.cache.query;

import java.util.Map;
import java.util.Set;

import com.cmp.ehcache.cache.ICacheKeyQuery;
import com.cmp.ehcache.cache.NullObject;

public class DefaultEqualQuery implements ICacheKeyQuery {
	private Object indxValue;
	private Map<Object, Set<Object>> indxMap;

	public DefaultEqualQuery(Map<Object, Set<Object>> indxMap, Object indxValue) {
		this.indxValue = NullObject.getValue(indxValue);
		this.indxMap = indxMap;
	}

	public Set<Object> execute() throws Exception {
		return indxMap.get(indxValue);
	}
}
