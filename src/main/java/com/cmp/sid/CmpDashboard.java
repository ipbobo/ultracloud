package com.cmp.sid;

//仪表盘
public class CmpDashboard {
	private String cpuTotalNum;//CPU总量
	private String cpuUseNum;//CPU已分配
	private String memTotalNum;//内存总量
	private String memUseNum;//内存已分配
	private String storeTotalNum;//存储总量
	private String storeUseNum;//存储已分配
	private String loadLittleNum;//轻度负载
	private String loadMiddleNum;//中度负载
	private String loadHeightNum;//高度负载
	private String loadStopNum;//停机负载
	private String runRunnigNum;//运行
	private String runHangupNum;//挂起
	private String runCloseNum;//关机

	public String getCpuTotalNum() {
		return cpuTotalNum;
	}

	public void setCpuTotalNum(String cpuTotalNum) {
		this.cpuTotalNum = cpuTotalNum;
	}

	public String getCpuUseNum() {
		return cpuUseNum;
	}

	public void setCpuUseNum(String cpuUseNum) {
		this.cpuUseNum = cpuUseNum;
	}

	public String getMemTotalNum() {
		return memTotalNum;
	}

	public void setMemTotalNum(String memTotalNum) {
		this.memTotalNum = memTotalNum;
	}

	public String getMemUseNum() {
		return memUseNum;
	}

	public void setMemUseNum(String memUseNum) {
		this.memUseNum = memUseNum;
	}

	public String getStoreTotalNum() {
		return storeTotalNum;
	}

	public void setStoreTotalNum(String storeTotalNum) {
		this.storeTotalNum = storeTotalNum;
	}

	public String getStoreUseNum() {
		return storeUseNum;
	}

	public void setStoreUseNum(String storeUseNum) {
		this.storeUseNum = storeUseNum;
	}

	public String getLoadLittleNum() {
		return loadLittleNum;
	}

	public void setLoadLittleNum(String loadLittleNum) {
		this.loadLittleNum = loadLittleNum;
	}

	public String getLoadMiddleNum() {
		return loadMiddleNum;
	}

	public void setLoadMiddleNum(String loadMiddleNum) {
		this.loadMiddleNum = loadMiddleNum;
	}

	public String getLoadHeightNum() {
		return loadHeightNum;
	}

	public void setLoadHeightNum(String loadHeightNum) {
		this.loadHeightNum = loadHeightNum;
	}

	public String getLoadStopNum() {
		return loadStopNum;
	}

	public void setLoadStopNum(String loadStopNum) {
		this.loadStopNum = loadStopNum;
	}

	//负载总数
	public String getLoadTotalNum() {
		return String.valueOf(Integer.parseInt(loadLittleNum)+Integer.parseInt(loadMiddleNum)+Integer.parseInt(loadHeightNum)+Integer.parseInt(loadStopNum));
	}

	public String getRunRunnigNum() {
		return runRunnigNum;
	}

	public void setRunRunnigNum(String runRunnigNum) {
		this.runRunnigNum = runRunnigNum;
	}
	
	public String getRunHangupNum() {
		return runHangupNum;
	}
	
	public void setRunHangupNum(String runHangupNum) {
		this.runHangupNum = runHangupNum;
	}

	public String getRunCloseNum() {
		return runCloseNum;
	}
	
	public void setRunCloseNum(String runCloseNum) {
		this.runCloseNum = runCloseNum;
	}
	
	//运行总数
	public String getRunTotalNum() {
		return String.valueOf(Integer.parseInt(runRunnigNum)+Integer.parseInt(runHangupNum)+Integer.parseInt(runCloseNum));
	}
}