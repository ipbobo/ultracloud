package com.cmp.mgr;

import javax.annotation.Resource;

import org.junit.Test;

import com.cmp.service.bi.BIDatacenterService;

public class BiTaskTest extends BaseJunit4Test {

	@Resource(name = "biDatacenterService")
	BIDatacenterService biDatacenterService;

	// 标明是测试方法
	@Test
	public void testVirtualBillDay() throws Exception {
		try {
			biDatacenterService.biDataGenerateHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
