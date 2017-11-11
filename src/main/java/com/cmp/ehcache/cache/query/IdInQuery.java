package com.cmp.ehcache.cache.query;

import java.util.List;
import java.util.Set;

import com.cmp.ehcache.cache.ICacheKeyQuery;
import com.cmp.ehcache.util.StringUtil;

public class IdInQuery implements ICacheKeyQuery {
	private Set<Object> idList;

	public IdInQuery(List<?> joinList, String idProperty) throws Exception {
		idList = StringUtil.getPropertyValueList(idProperty, joinList);
	}

	public Set<Object> execute() throws Exception {
		return idList;
	}
}
