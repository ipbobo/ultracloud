package com.cmp.util;

import com.zabbix.util.FormatData;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PutMethod;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.util.Properties;

public class ZabbixUtil {

	private static Logger logger = LoggerFactory.getLogger(ZabbixUtil.class);

	public static String API_URL;

	private static String USERNAME;

	private static String PASSWORD;

	static {
		try {
			Properties properties = new Properties();
			properties.load(new FileReader(ZabbixUtil.class.getResource("/").getPath() + "/" + "env.properties"));

			API_URL = properties.getProperty("zabbix.url");
			USERNAME = properties.getProperty("zabbix.user");
			PASSWORD = properties.getProperty("zabbix.password");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void login() {
		try {
			HttpClient client = new HttpClient();
			PutMethod putMethod = new PutMethod(API_URL);
			putMethod.setRequestHeader("Content-Type", "application/json-rpc");
			String jsonrpc = loginJson(USERNAME, PASSWORD);

			JSONObject jsonObj = new JSONObject(jsonrpc);
			putMethod.setRequestBody(FormatData.fromString(jsonObj.toString()));
			client.executeMethod(putMethod);

			String loginResponse = putMethod.getResponseBodyAsString();
			JSONObject obj = new JSONObject(loginResponse);

			String sessionId = "";
			if (obj.has("result")) {
				sessionId = obj.getString("result");
				FormatData.auth = sessionId;
				logger.info("zabbix api sessionId: {}", sessionId);
			} else if (obj.has("error")) {
				sessionId = obj.getJSONObject("error").getString("data");
				logger.error("Zabbix error: {}", sessionId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static String loginJson(String loginName, String password) {
		JSONStringer js = new JSONStringer();
		try {
			js.object();
			js.key("jsonrpc").value("2.0");
			js.key("method").value("user.login");
			js.key("id").value(1);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user", loginName);
			jsonObject.put("password", password);
			js.key("params").value(jsonObject);
			js.endObject();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return js.toString();
	}

}
