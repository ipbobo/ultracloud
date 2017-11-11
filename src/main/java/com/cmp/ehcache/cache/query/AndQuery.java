package com.cmp.ehcache.cache.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cmp.ehcache.cache.ICacheKeyQuery;

import net.sf.ehcache.CacheException;

public class AndQuery implements ICacheKeyQuery {
	protected List<ICacheKeyQuery> cacheKeyQueryList;
	
	public AndQuery(){
		cacheKeyQueryList = new ArrayList<ICacheKeyQuery>();
	}
	
	public Set<Object> execute() throws Exception {
		Set<Object> resultSet = ((ICacheKeyQuery) cacheKeyQueryList.get(0)).execute();
		for (int i = 1; i < cacheKeyQueryList.size(); i++) {
			Set<Object> keys = ((ICacheKeyQuery) cacheKeyQueryList.get(i)).execute();
			if(keys!=null){
				resultSet = intersection(resultSet, keys);
			}
		}

		return resultSet;
	}

	private Set<Object> intersection(Set<Object> keys1, Set<Object> keys2) {
		Set<Object> keySet = new HashSet<Object>();
		if ((keys1 != null) && (keys2 != null)) {
			for (Iterator<Object> it = keys1.iterator(); it.hasNext();) {
				Object key = it.next();
				if (keys2.contains(key)) {
					keySet.add(key);
				}
			}
		}
		
		return keySet;
	}
	
	public AndQuery add(ICacheKeyQuery keyQuery) throws CacheException {
		cacheKeyQueryList.add(keyQuery);
		return this;
	}

	public AndQuery add(List<ICacheKeyQuery> keyQuerys) throws CacheException {
		for (ICacheKeyQuery keyQuery : keyQuerys) {
			cacheKeyQueryList.add(keyQuery);
		}
		
		return this;
	}
}
