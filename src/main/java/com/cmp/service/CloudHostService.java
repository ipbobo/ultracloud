package com.cmp.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface CloudHostService {

	public List<PageData> list(Page page) throws Exception;

	public void start(List<Integer> ls) throws Exception;

	public void stop(List<Integer> ls) throws Exception;

	public void reboot(List<Integer> ls) throws Exception;

	public void suspend(List<Integer> ls) throws Exception;

	public void resume(List<Integer> ls) throws Exception;

	public void destroy(List<Integer> ls) throws Exception;

	public void clone(Integer id) throws Exception;

	public void snapshot(Integer ls) throws Exception;

	public String acquireTicket(Integer id) throws Exception;

}
