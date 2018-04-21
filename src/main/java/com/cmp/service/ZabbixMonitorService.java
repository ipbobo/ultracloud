package com.cmp.service;

import com.cmp.entity.HostMonitorInfo;

public interface ZabbixMonitorService {

	public void registerToZabbix(HostMonitorInfo hostInfo);

}
