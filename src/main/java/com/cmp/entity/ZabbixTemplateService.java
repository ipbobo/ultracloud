package com.cmp.entity;

import com.google.gson.Gson;
import com.zabbix.api.domain.base.Template;
import com.zabbix.api.domain.template.*;
import com.zabbix.util.FormatData;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZabbixTemplateService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${zabbix.url}")
	private String apiUrl;

	public Object create(TemplateCreateRequest create) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(create);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			rs = new JSONObject(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public Object delete(TemplateDeleteRequest delete) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(delete);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			rs = new JSONObject(response);
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

	public Object exists(TemplateExistsRequest exists) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(exists);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			rs = new JSONObject(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public Object getobjects(TemplateGetobjectsRequest getobjects) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(getobjects);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			rs = new JSONObject(response);
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

	public Object get(TemplateGetRequest get) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(get);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			System.out.println("json response:" + response);
			rs = new JSONObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public List<Template> getTemplateToBean(TemplateGetRequest get) {
		List<Template> templates = null;
		JSONObject result = (JSONObject) get(get);
		if (result.has("result")) {
			try {
				JSONArray array = result.getJSONArray("result");
				if ((array == null) || (array.length() <= 0)) {
					return templates;
				}

				templates = new ArrayList();
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					Template template = new Template();
					template.setTemplateid(jsonObject.getString("templateid"));
					template.setHost(jsonObject.getString("host"));
					template.setName(jsonObject.getString("name"));
					templates.add(template);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (result.has("error")) {
			return null;
		}
		return templates;
	}

	public Object isreadable(TemplateIsreadableRequest isreadable) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(isreadable);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			rs = new JSONObject(response);
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

	public Object iswritable(TemplateIswritableRequest iswritable) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(iswritable);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			rs = new JSONObject(response);
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

	public Object massadd(TemplateMassaddRequest massadd) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(massadd);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			rs = new JSONObject(response);
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

	public Object massremove(TemplateMassremoveRequest massremove) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(massremove);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			rs = new JSONObject(response);
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

	public Object massupdate(TemplateMassupdateRequest massupdate) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(massupdate);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			rs = new JSONObject(response);
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

	public Object update(TemplateUpdateRequest update) {
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(apiUrl);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs = null;
		try {
			String json = js.toJson(update);
			logger.info("json request:" + json);
			putMethod.setRequestBody(FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			logger.info("json response:" + response);
			rs = new JSONObject(response);
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

}
