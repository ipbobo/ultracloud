package com.cmp.service.resourcemgt;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 虚拟机 业务层接口
 * 
 * @author liuweixing
 *
 */
public interface VirtualMService {

	public List<PageData> list(Page page) throws Exception;

	public List<PageData> vmList(Page page) throws Exception;

	public void createVm(PageData pd) throws Exception;

	public void startVm(List<Integer> ls) throws Exception;

	public void stopVm(List<Integer> ls) throws Exception;

	public void rebootVm(List<Integer> ls) throws Exception;

	public void suspendVm(List<Integer> ls) throws Exception;

	public void resumeVm(List<Integer> ls) throws Exception;

	public void destroyVm(List<Integer> ls) throws Exception;

}
