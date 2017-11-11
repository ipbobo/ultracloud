package com.cmp.ehcache.cache.query;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.cmp.ehcache.cache.ICacheKeyQuery;

public class LikeQuery implements ICacheKeyQuery {
	private Map<Object, Set<Object>> indxMap;
	private Object indxValue;

	public LikeQuery(Map<Object, Set<Object>> indxMap, Object indxValue) {
		this.indxMap = indxMap;
		this.indxValue = indxValue;
	}

	public Set<Object> execute() throws Exception {
		Set<Object> resultSet = new HashSet<Object>();
		for (Iterator<Object> it = indxMap.keySet().iterator(); it.hasNext();) {
			Object value = it.next();
			if (value.toString().contains(indxValue.toString())) {
				resultSet.addAll(indxMap.get(value));
			}
		}
		
		return resultSet;
	}
}
