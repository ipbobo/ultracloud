package com.cmp.service;

import com.cmp.util.ZabbixUtil;
import com.google.gson.Gson;
import com.zabbix.api.domain.base.HostGroup;
import com.zabbix.api.domain.hostgroup.*;
import com.zabbix.util.FormatData;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HostgroupService {

	private String apiUrl = "http://192.168.0.150/zabbix/api_jsonrpc.php";

	public Object hostGroupCreate(HostGroupCreateRequest hostGroupCreate) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			System.out.println(js.toJson(hostGroupCreate));
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupCreate)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public Object hostGroupDelete(HostGroupDeleteRequest hostGroupDelete) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			System.out.println(js.toJson(hostGroupDelete));
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupDelete)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public Object hostGroupExists(HostGroupExistsRequest hostGroupExists) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			System.out.println(js.toJson(hostGroupExists));
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupExists)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			} else if (rs.has("error")) {
				result = rs.get("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object hostGroupGetobjects(HostGroupGetobjectsRequest hostGroupGetobjects) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			System.out.println(js.toJson(hostGroupGetobjects));
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupGetobjects)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			} else if (rs.has("error")) {
				result = rs.get("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object hostGroupGet(HostGroupGetRequest hostGroupGet) {
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupGet)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();

			rs = new JSONObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public List<HostGroup> getHostGroupBean(HostGroupGetRequest hostGroupGet) {
		JSONObject result = (JSONObject) hostGroupGet(hostGroupGet);
		List<HostGroup> hostGroups = null;
		if (result.has("result")) {
			try {
				JSONArray array = result.getJSONArray("result");
				if ((array != null) && (array.length() > 0)) {
					hostGroups = new ArrayList();
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						HostGroup group = new HostGroup();
						group.setGroupid(object.getString("groupid"));
						group.setInternal(object.getInt("internal"));
						group.setName(object.getString("name"));
						hostGroups.add(group);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return hostGroups;
		}

		if (result.has("error")) {
			return null;
		}

		return hostGroups;
	}

	public Object hostGroupIsreadable(HostGroupIsreadableRequest hostGroupIsreadable) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			System.out.println(js.toJson(hostGroupIsreadable));
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupIsreadable)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			} else if (rs.has("error")) {
				result = rs.get("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object hostGroupIswritable(HostGroupIswritableRequest hostGroupIswritable) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			System.out.println(js.toJson(hostGroupIswritable));
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupIswritable)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			} else if (rs.has("error")) {
				result = rs.get("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object hostGroupMassadd(HostGroupMassaddRequest hostGroupMassadd) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			System.out.println(js.toJson(hostGroupMassadd));
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupMassadd)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public Object hostGroupMassremove(HostGroupMassremoveRequest hostGroupMassremove) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			System.out.println(js.toJson(hostGroupMassremove));
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupMassremove)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			} else if (rs.has("error")) {
				result = rs.get("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object hostGroupMassupdate(HostGroupMassupdateRequest hostGroupMassupdate) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			System.out.println(js.toJson(hostGroupMassupdate));
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupMassupdate)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public Object hostGroupUpdate(HostGroupUpdateRequest hostGroupUpdate) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(ZabbixUtil.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			System.out.println(js.toJson(hostGroupUpdate));
			putMethod.setRequestBody(FormatData.fromString(js.toJson(hostGroupUpdate)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

}
