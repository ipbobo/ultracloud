package com.cmp.ehcache.cache;

public class CacheException extends Exception {
	private static final long serialVersionUID = 1L;

	public CacheException() {
	}

	public CacheException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CacheException(String arg0) {
		super(arg0);
	}

	public CacheException(Throwable arg0) {
		super(arg0);
	}

	public static void asserts(boolean bool, String msg) throws CacheException {
		if (!bool){
			throw new CacheException(msg);
		}
	}
}
