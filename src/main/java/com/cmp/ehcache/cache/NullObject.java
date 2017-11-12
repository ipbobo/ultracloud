package com.cmp.ehcache.cache;

public class NullObject implements Comparable<Object> {
	private static NullObject nullObject = new NullObject();

	public int hashCode() {
		return -2147483648;
	}

	public boolean equals(Object obj) {
		return obj.getClass() == NullObject.class;
	}

	public static boolean is(Object indxValue) {
		return indxValue.getClass() == NullObject.class;
	}

	public static Object getValue(Object indxValue) {
		return indxValue == null ? nullObject : indxValue;
	}

	public int compareTo(Object o) {
		return equals(o) ? 0 : -1;
	}
}
