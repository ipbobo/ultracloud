package com.cmp.sid;

//仪表盘
public class CmpDashboard {
	private long cpuUseNum=0;//CPU已分配
	private long cpuTotalNum=0;//CPU总量
	private long memUseNum=0;//内存已分配
	private long memTotalNum=0;//内存总量
	private long storeUseNum=0;//存储已分配
	private long storeTotalNum=0;//存储总量
	private long loadLittleNum=0;//轻度负载
	private long loadMiddleNum=0;//中度负载
	private long loadHeightNum=0;//高度负载
	private long loadStopNum=0;//停机负载
	private long runRunnigNum=0;//运行
	private long runHangupNum=0;//挂起
	private long runCloseNum=0;//关机

	public long getCpuUseNum() {
		return cpuUseNum;
	}

	public void setCpuUseNum(long cpuUseNum) {
		this.cpuUseNum = cpuUseNum;
	}
	
	public void addCpuUseNum(long cpuUseNum) {
		this.cpuUseNum += cpuUseNum;
	}

	public long getCpuTotalNum() {
		return cpuTotalNum;
	}

	public void setCpuTotalNum(long cpuTotalNum) {
		this.cpuTotalNum = cpuTotalNum;
	}
	
	public void addCpuTotalNum(long cpuTotalNum) {
		this.cpuTotalNum += cpuTotalNum;
	}
	
	public long getMemUseNum() {
		return memUseNum;
	}

	public void setMemUseNum(long memUseNum) {
		this.memUseNum = memUseNum;
	}
	
	public void addMemUseNum(long memUseNum) {
		this.memUseNum += memUseNum;
	}

	public long getMemTotalNum() {
		return memTotalNum;
	}

	public void setMemTotalNum(long memTotalNum) {
		this.memTotalNum = memTotalNum;
	}
	
	public void addMemTotalNum(long memTotalNum) {
		this.memTotalNum += memTotalNum;
	}
	
	public long getStoreUseNum() {
		return storeUseNum;
	}

	public void setStoreUseNum(long storeUseNum) {
		this.storeUseNum = storeUseNum;
	}
	
	public void addStoreUseNum(long storeUseNum) {
		this.storeUseNum += storeUseNum;
	}
	
	public long getStoreTotalNum() {
		return storeTotalNum;
	}

	public void setStoreTotalNum(long storeTotalNum) {
		this.storeTotalNum = storeTotalNum;
	}
	
	public void addStoreTotalNum(long storeTotalNum) {
		this.storeTotalNum += storeTotalNum;
	}

	public long getLoadLittleNum() {
		return loadLittleNum;
	}

	public void setLoadLittleNum(long loadLittleNum) {
		this.loadLittleNum = loadLittleNum;
	}
	
	public void addLoadLittleNum(long loadLittleNum) {
		this.loadLittleNum += loadLittleNum;
	}

	public long getLoadMiddleNum() {
		return loadMiddleNum;
	}

	public void setLoadMiddleNum(long loadMiddleNum) {
		this.loadMiddleNum = loadMiddleNum;
	}
	
	public void addLoadMiddleNum(long loadMiddleNum) {
		this.loadMiddleNum += loadMiddleNum;
	}

	public long getLoadHeightNum() {
		return loadHeightNum;
	}

	public void setLoadHeightNum(long loadHeightNum) {
		this.loadHeightNum = loadHeightNum;
	}
	
	public void addLoadHeightNum(long loadHeightNum) {
		this.loadHeightNum += loadHeightNum;
	}

	public long getLoadStopNum() {
		return loadStopNum;
	}

	public void setLoadStopNum(long loadStopNum) {
		this.loadStopNum = loadStopNum;
	}
	
	public void addLoadStopNum(long loadStopNum) {
		this.loadStopNum += loadStopNum;
	}

	//负载总数
	public long getLoadTotalNum() {
		return loadLittleNum+loadMiddleNum+loadHeightNum+loadStopNum;
	}

	public long getRunRunnigNum() {
		return runRunnigNum;
	}

	public void setRunRunnigNum(long runRunnigNum) {
		this.runRunnigNum = runRunnigNum;
	}
	
	public void addRunRunnigNum(long runRunnigNum) {
		this.runRunnigNum += runRunnigNum;
	}
	
	public long getRunHangupNum() {
		return runHangupNum;
	}
	
	public void setRunHangupNum(long runHangupNum) {
		this.runHangupNum = runHangupNum;
	}
	
	public void addRunHangupNum(long runHangupNum) {
		this.runHangupNum += runHangupNum;
	}

	public long getRunCloseNum() {
		return runCloseNum;
	}
	
	public void setRunCloseNum(long runCloseNum) {
		this.runCloseNum = runCloseNum;
	}
	
	public void addRunCloseNum(long runCloseNum) {
		this.runCloseNum += runCloseNum;
	}
	
	//运行总数
	public long getRunTotalNum() {
		return runRunnigNum+runHangupNum+runCloseNum;
	}
}