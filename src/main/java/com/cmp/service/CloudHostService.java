package com.cmp.service;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface CloudHostService {

	public List<PageData> list(Page page) throws Exception;

	public void start(List<Integer> ls) throws Exception;

	public void stop(List<Integer> ls) throws Exception;

	public void reboot(List<Integer> ls) throws Exception;

	public void suspend(List<Integer> ls) throws Exception;

	public void resume(List<Integer> ls) throws Exception;

	public void destroy(List<Integer> ls) throws Exception;

}
