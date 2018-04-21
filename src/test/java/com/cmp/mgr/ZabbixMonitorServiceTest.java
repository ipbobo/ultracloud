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
		ZabbixUtil.login("http://192.168.0.150/zabbix/api_jsonrpc.php", "Admin", "zabbix");

		HostMonitorInfo hostInfo = new HostMonitorInfo();
		hostInfo.setUUID("4224f272-ff27-1e83-dbca-181fb49a04e4");
		hostInfo.setIp("192.168.0.155");
		hostInfo.setPort("10050");
		hostInfo.setVisibleName("TestVM5");
		hostInfo.setMonitorType(0);
		hostInfo.setOsName("Linux");

		zabbixMonitorService.registerToZabbix(hostInfo);
	}

	@After
	public void cleanup() {
		zabbixMonitorService = null;
	}

}
