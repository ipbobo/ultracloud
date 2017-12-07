package com.cmp.entity.tcc;

public class TccVipSrt implements java.io.Serializable {

	private static final long serialVersionUID = -2813821611605145942L;

	private Long id;

	private TccDevOps tccDevOps;

	private TccApplyedHostinfo tccApplyedHostinfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TccDevOps getTccDevOps() {
		return tccDevOps;
	}

	public void setTccDevOps(TccDevOps tccDevOps) {
		this.tccDevOps = tccDevOps;
	}

	public TccApplyedHostinfo getTccApplyedHostinfo() {
		return tccApplyedHostinfo;
	}

	public void setTccApplyedHostinfo(TccApplyedHostinfo tccApplyedHostinfo) {
		this.tccApplyedHostinfo = tccApplyedHostinfo;
	}

}
