package com.cmp.service;

import com.cmp.entity.HostMonitorInfo;

public interface CloudMonitorService {

	public void registerToZabbix(HostMonitorInfo hostInfo);

}
