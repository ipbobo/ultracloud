package com.cmp.sid;

public class CmpLog {
	private String id;//ID
	private String userName;//操作者
	private String type;//操作类型：1-新增；2-修改；3-删除
	private String optObject;//操作对象
	private String optStatus;//操作状态：0-成功；-1-失败
	private String ip;//操作者ip
	private String detail;//操作详细描述
	private String gmtCreate;//创建时间
	private String gmtModified;//修改时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOptObject() {
		return optObject;
	}

	public void setOptObject(String optObject) {
		this.optObject = optObject;
	}

	public String getOptStatus() {
		return optStatus;
	}

	public void setOptStatus(String optStatus) {
		this.optStatus = optStatus;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}
}