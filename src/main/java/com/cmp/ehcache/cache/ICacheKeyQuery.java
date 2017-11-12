package com.cmp.ehcache.cache;

import java.util.Set;

public interface ICacheKeyQuery {
	public abstract Set<Object> execute() throws Exception;
}
