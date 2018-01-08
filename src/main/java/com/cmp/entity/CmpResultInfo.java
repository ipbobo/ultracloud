package com.cmp.entity;

public class CmpResultInfo {
	
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	private String resultCode;
	private String resultMsg;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}
