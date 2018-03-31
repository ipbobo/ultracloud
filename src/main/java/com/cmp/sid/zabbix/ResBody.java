package com.cmp.sid.zabbix;

public class ResBody {
	private String jsonrpc;
	private ResBean[] result;
	private ResError error;
	private String id;

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public ResBean[] getResult() {
		return result;
	}

	public void setResult(ResBean[] result) {
		this.result = result;
	}

	public ResError getError() {
		return error;
	}

	public void setError(ResError error) {
		this.error = error;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}