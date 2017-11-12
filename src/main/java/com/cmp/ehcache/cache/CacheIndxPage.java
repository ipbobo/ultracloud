package com.cmp.ehcache.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CacheIndxPage {
	public static <T> List<T> page(Collection<T> values, int first, int max) {
		if (first < 1) {
			first = 1;
		}
		if (max < 1) {
			return null;
		}
		int from = first - 1 > values.size() ? values.size() : first - 1;
		int to = first + max - 1 > values.size() ? values.size() : first + max - 1;
		List<T> list = new ArrayList<T>(values.size());
		list.addAll(values);
		return list.subList(from, to);
	}

	public static <T> List<T> pageByPager(Collection<T> values, int page, int pageSize) {
		int first = page * pageSize - pageSize;
		first = 1 == page ? first : first + 1;
		return page(values, first, pageSize);
	}
}
