package com.cmp.entity.tcc;

import java.io.Serializable;

public class MenuPO implements Comparable<MenuPO>, Serializable {

	private static final long serialVersionUID = -8831599618624072136L;

	private Long privilegeId;// 权限名称

	private String privilegeName;// 权限url

	private String privilegeUrl;

	private Long pid;

	private Long porder;

	public Long getPorder() {
		return porder;
	}

	public void setPorder(Long porder) {
		this.porder = porder;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegeUrl() {
		return privilegeUrl;
	}

	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Override
	public int compareTo(MenuPO menuPO) {

		if (this.porder.longValue() > menuPO.porder.longValue()) {
			return 1;
		} else {
			return -1;
		}
	}

}
