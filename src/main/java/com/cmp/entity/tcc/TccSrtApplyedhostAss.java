package com.cmp.entity.tcc;

public class TccSrtApplyedhostAss implements java.io.Serializable {

	private static final long serialVersionUID = 7161591625465058603L;

	private Long assId;

	private TccSrt tccSrt;

	private TccApplyedHostinfo tccApplyedHostinfo;

	private TccSrt oldTccStr;

	public Long getAssId() {
		return this.assId;
	}

	public void setAssId(Long assId) {
		this.assId = assId;
	}

	public TccSrt getTccSrt() {
		return this.tccSrt;
	}

	public void setTccSrt(TccSrt tccSrt) {
		this.tccSrt = tccSrt;
	}

	public TccApplyedHostinfo getTccApplyedHostinfo() {
		return this.tccApplyedHostinfo;
	}

	public void setTccApplyedHostinfo(TccApplyedHostinfo tccApplyedHostinfo) {
		this.tccApplyedHostinfo = tccApplyedHostinfo;
	}

	public TccSrt getOldTccStr() {
		return oldTccStr;
	}

	public void setOldTccStr(TccSrt oldTccStr) {
		this.oldTccStr = oldTccStr;
	}

}
