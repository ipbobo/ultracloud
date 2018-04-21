package com.cmp.mgr;

import com.cmp.entity.HostMonitorInfo;
import com.cmp.entity.TemplateService;
import com.cmp.service.CloudMonitorService;
import com.cmp.service.CloudMonitorServiceImpl;
import com.cmp.service.HostService;
import com.cmp.service.HostgroupService;
import com.cmp.util.ZabbixUtil;
import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CloudMonitorServiceTest implements CloudArchTest {

	private CloudMonitorService cloudMonitorService;

	@Before
	public void setup() throws Exception {
		cloudMonitorService = new CloudMonitorServiceImpl();
		FieldUtils.writeDeclaredField(cloudMonitorService, "hostgroupService", new HostgroupService(), true);
		FieldUtils.writeDeclaredField(cloudMonitorService, "hostService", new HostService(), true);
		FieldUtils.writeDeclaredField(cloudMonitorService, "templateService", new TemplateService(), true);
	}

	@Test
	public void testRegisterToZabbix() {
		ZabbixUtil.login();

		HostMonitorInfo hostInfo = new HostMonitorInfo();
		hostInfo.setHostName("localhost");
		hostInfo.setIp("192.168.0.152");
		hostInfo.setPort("");
		hostInfo.setVisibleName("TestVM4");
		hostInfo.setMonitorType(0);
		hostInfo.setOsName("linux");

		cloudMonitorService.registerToZabbix(hostInfo);
	}

	@After
	public void cleanup() {
		cloudMonitorService = null;
	}

}
