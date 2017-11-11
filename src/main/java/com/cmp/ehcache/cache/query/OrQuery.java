package com.cmp.ehcache.cache.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cmp.ehcache.cache.ICacheKeyQuery;

import net.sf.ehcache.CacheException;

public class OrQuery implements ICacheKeyQuery {
	protected List<ICacheKeyQuery> cacheKeyQueryList;
	
	public OrQuery(){
		cacheKeyQueryList = new ArrayList<ICacheKeyQuery>();
	}
	
	public Set<Object> execute() throws Exception {
		Set<Object> valueKeys = new HashSet<Object>();
		for (ICacheKeyQuery ckq : cacheKeyQueryList) {
			Set<Object> keys = ckq.execute();
			if (keys != null) {
				valueKeys.addAll(keys);
			}
		}
		
		return valueKeys.size() == 0 ? null : valueKeys;
	}
	
	public OrQuery add(ICacheKeyQuery keyQuery) throws CacheException {
		cacheKeyQueryList.add(keyQuery);
		return this;
	}

	public OrQuery add(List<ICacheKeyQuery> keyQuerys) throws CacheException {
		for (ICacheKeyQuery keyQuery : keyQuerys) {
			cacheKeyQueryList.add(keyQuery);
		}
		
		return this;
	}
}
