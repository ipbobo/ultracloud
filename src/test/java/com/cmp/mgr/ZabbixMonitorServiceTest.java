package com.cmp.mgr;

import com.cmp.entity.HostMonitorInfo;
import com.cmp.entity.ZabbixTemplateService;
import com.cmp.service.ZabbixHostService;
import com.cmp.service.ZabbixHostgroupService;
import com.cmp.service.ZabbixMonitorService;
import com.cmp.service.ZabbixMonitorServiceImpl;
import com.cmp.util.ZabbixUtil;
import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZabbixMonitorServiceTest implements CloudArchTest {

	private ZabbixMonitorService zabbixMonitorService;

	@Before
	public void setup() throws Exception {
		zabbixMonitorService = new ZabbixMonitorServiceImpl();
		FieldUtils.writeDeclaredField(zabbixMonitorService, "zabbixHostgroupService", new ZabbixHostgroupService(), true);
		FieldUtils.writeDeclaredField(zabbixMonitorService, "zabbixHostService", new ZabbixHostService(), true);
		FieldUtils.writeDeclaredField(zabbixMonitorService, "zabbixTemplateService", new ZabbixTemplateService(), true);
	}

	@Test
	public void testRegisterToZabbix() {
		ZabbixUtil.login();

		HostMonitorInfo hostInfo = new HostMonitorInfo();
		hostInfo.setHostName("localhost");
		hostInfo.setIp("192.168.0.152");
		hostInfo.setPort("80");
		hostInfo.setVisibleName("TestVM4");
		hostInfo.setMonitorType(0);
		hostInfo.setOsName("Linux");

		zabbixMonitorService.registerToZabbix(hostInfo);
	}

	@After
	public void cleanup() {
		zabbixMonitorService = null;
	}

}
