package com.cmp.ehcache.cache;

import java.util.List;

public class Page<E> {
	private List<E> list;
	private int count;

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String toString() {
		return "size:" + (list == null ? "null" : Integer.valueOf(list.size())) + ",count:" + count;
	}
}
