package com.cmp.mgr;

import javax.annotation.Resource;

import org.junit.Test;

import com.cmp.service.bi.BIDataGenerateService;

public class BiTaskTest extends BaseJunit4Test {

	@Resource(name = "biDataGenerateService")
	BIDataGenerateService biDataGenerateService;

	// 标明是测试方法
	@Test
	public void testVirtualBillDay() throws Exception {
		try {
			biDataGenerateService.biDataGenerateHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
