package com.cmp.service;

import com.cmp.entity.HostMonitorInfo;
import com.cmp.entity.TemplateService;
import com.zabbix.api.domain.base.Host;
import com.zabbix.api.domain.base.HostGroup;
import com.zabbix.api.domain.base.HostInterface;
import com.zabbix.api.domain.base.Template;
import com.zabbix.api.domain.hostgroup.HostGroupCreateRequest;
import com.zabbix.api.domain.hostgroup.HostGroupExistsRequest;
import com.zabbix.api.domain.hostgroup.HostGroupGetRequest;
import com.zabbix.api.domain.template.TemplateGetRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CloudMonitorServiceImpl implements CloudMonitorService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private @Autowired HostgroupService hostgroupService;

	private @Autowired HostService hostService;

	private @Autowired TemplateService templateService;

	public void registerToZabbix(HostMonitorInfo hostInfo) {
		String groupid = "";
		String templateid = "";
		String groupname = hostInfo.getOsName();
		boolean result = hostGroupExists(groupname);

		if (result) {
			List<String> names = new ArrayList<>();
			names.add(groupname);
			List<HostGroup> hostgroups = getHostGroup(names);
			groupid = hostgroups.get(0).getGroupid();
		} else {
			String createresult = hostGroupCreate(groupname);
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(createresult);
			if (isNum.matches()) {
				groupid = createresult;
			} else {
				logger.error("hostGroupCreate出错了" + createresult);
			}
		}
		if (!"".equals(groupid)) {
			String osname = hostInfo.getOsName();
			if ("windows".equalsIgnoreCase(osname)) {
				List<Template> templates = getTemplateByName("Template OS Windows");
				if (templates != null && templates.size() > 0) {
					templateid = templates.get(0).getTemplateid();
				}
			} else if ("linux".equalsIgnoreCase(osname)) {
				List<Template> templates = getTemplateByName("Template OS Linux");
				if (templates != null && templates.size() > 0) {
					templateid = templates.get(0).getTemplateid();
				}
			}
			Host host = new Host();
			HostGroup group = new HostGroup();
			group.setGroupid(groupid);
			HostInterface hostInterface = new HostInterface();
			Template template = new Template();
			template.setTemplateid(templateid);
			host.setName(hostInfo.getVisibleName());
			host.setHost(hostInfo.getHostName());
			host.setStatus(0);
			hostInterface.setType(1); // 1 - agent; 2 - SNMP; 3 - IPMI 4 -
			// JMX.
			hostInterface.setIp(hostInfo.getIp());
			hostInterface.setDns("");
			hostInterface.setUseip(1); // 0 - connect using host DNS name 1
			// - connect using host IP address.
			hostInterface.setMain(1); // 0 - not default; 1 - default.
			hostInterface.setPort(hostInfo.getPort()); // agent监控默认端口
			String rs = hostService.createHost(host, hostInterface, group, template);
		}
	}

	private List<HostGroup> getHostGroup(List<String> groupnames) {
		HostGroupGetRequest hostGroupGet = new HostGroupGetRequest();
		hostGroupGet.getParams().setOutput("extend");
		List<String> names = new ArrayList<>();
		if (groupnames != null && groupnames.size() > 0) {
			for (String name : groupnames) {
				names.add(name);
			}
		}
		hostGroupGet.getParams().getFilter().setName(names);
		return hostgroupService.getHostGroupBean(hostGroupGet);
	}

	private List<Template> getTemplateByName(String templatename) {
		TemplateGetRequest get = new TemplateGetRequest();
		get.getParams().setOutput("extend");
		List<String> hosts = new ArrayList<>();
		hosts.add(templatename);
		get.getParams().getFilter().setHost(hosts);
		return templateService.getTemplateToBean(get);
	}

	private boolean hostGroupExists(String hostgroupname) {
		HostGroupExistsRequest hostGroupExists = new HostGroupExistsRequest();
		hostGroupExists.getParams().getName().add(hostgroupname);
		return (boolean) hostgroupService.hostGroupExists(hostGroupExists);
	}

	private String hostGroupCreate(String hostgroupname) {
		String resultmessage = "";
		HostGroupCreateRequest hostGroupCreate = new HostGroupCreateRequest();
		HostGroup hostGroup = new HostGroup();
		hostGroup.setName(hostgroupname);
		hostGroupCreate.getParams().add(hostGroup);
		JSONObject rs = (JSONObject) hostgroupService
				.hostGroupCreate(hostGroupCreate);
		if (rs.has("result")) {
			try {
				JSONObject result = (JSONObject) rs.get("result");
				JSONArray ids = (JSONArray) result.get("groupids");
				resultmessage = (String) ids.get(0);
			} catch (Exception e) {
				logger.error("HostMonitorServiceImpl.hostGroupCreate出错了，错误信息为:" + e.getMessage());
			}
		} else if (rs.has("error")) {
			try {
				JSONObject result = (JSONObject) rs.get("error");
				resultmessage = (String) result.get("data");
				return resultmessage;
			} catch (Exception e) {
				logger.error("HostMonitorServiceImpl.hostGroupCreate出错了，错误信息为:" + e.getMessage());
			}
		}
		return resultmessage;
	}

}
