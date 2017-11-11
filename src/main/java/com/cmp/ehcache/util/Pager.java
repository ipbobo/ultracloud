package com.cmp.ehcache.util;

import java.util.List;

public class Pager<T> {
	private int pageSize = 10;
	private int curPage = 1;
	private int totalcnt = 0;
	private int totalpage = 0;
	private List<T> list;

	public Pager() {
	}

	public int getFirst() {
		return (curPage - 1) * pageSize + 1;
	}

	public Pager(int pageSize, int curPage) {
		this.pageSize = pageSize;
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pagesize) {
		if (pagesize <= 0) {
			return;
		}
		this.pageSize = pagesize;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurrentpage(int curPage) {
		if (curPage <= 0) {
			return;
		}
		this.curPage = curPage;
	}

	public int getTotalcnt() {
		return totalcnt;
	}

	public void setTotalcnt(int totalcnt) {
		this.totalcnt = totalcnt;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalpage() {
		if (totalcnt == 0) {
			totalpage = 0;
		} else {
			if (totalcnt % pageSize == 0) {
				return totalpage = totalcnt / pageSize;
			}
			return totalpage = totalcnt / pageSize + 1;
		}
		
		return totalpage;
	}
}