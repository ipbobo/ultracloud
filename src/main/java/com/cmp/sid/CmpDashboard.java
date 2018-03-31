package com.cmp.sid;

//仪表盘
public class CmpDashboard {
	private int cpuUseNum=0;//CPU已分配
	private int cpuTotalNum=0;//CPU总量
	private int memUseNum=0;//内存已分配
	private int memTotalNum=0;//内存总量
	private int storeUseNum=0;//存储已分配
	private int storeTotalNum=0;//存储总量
	private int loadLittleNum=0;//轻度负载
	private int loadMiddleNum=0;//中度负载
	private int loadHeightNum=0;//高度负载
	private int loadStopNum=0;//停机负载
	private int runRunnigNum=0;//运行
	private int runHangupNum=0;//挂起
	private int runCloseNum=0;//关机

	public int getCpuUseNum() {
		return cpuUseNum;
	}

	public void setCpuUseNum(int cpuUseNum) {
		this.cpuUseNum = cpuUseNum;
	}
	
	public void addCpuUseNum(int cpuUseNum) {
		this.cpuUseNum += cpuUseNum;
	}

	public int getCpuTotalNum() {
		return cpuTotalNum;
	}

	public void setCpuTotalNum(int cpuTotalNum) {
		this.cpuTotalNum = cpuTotalNum;
	}
	
	public void addCpuTotalNum(int cpuTotalNum) {
		this.cpuTotalNum += cpuTotalNum;
	}
	
	public int getMemUseNum() {
		return memUseNum;
	}

	public void setMemUseNum(int memUseNum) {
		this.memUseNum = memUseNum;
	}
	
	public void addMemUseNum(int memUseNum) {
		this.memUseNum += memUseNum;
	}

	public int getMemTotalNum() {
		return memTotalNum;
	}

	public void setMemTotalNum(int memTotalNum) {
		this.memTotalNum = memTotalNum;
	}
	
	public void addMemTotalNum(int memTotalNum) {
		this.memTotalNum += memTotalNum;
	}
	
	public int getStoreUseNum() {
		return storeUseNum;
	}

	public void setStoreUseNum(int storeUseNum) {
		this.storeUseNum = storeUseNum;
	}
	
	public void addStoreUseNum(int storeUseNum) {
		this.storeUseNum += storeUseNum;
	}
	
	public int getStoreTotalNum() {
		return storeTotalNum;
	}

	public void setStoreTotalNum(int storeTotalNum) {
		this.storeTotalNum = storeTotalNum;
	}
	
	public void addStoreTotalNum(int storeTotalNum) {
		this.storeTotalNum += storeTotalNum;
	}

	public int getLoadLittleNum() {
		return loadLittleNum;
	}

	public void setLoadLittleNum(int loadLittleNum) {
		this.loadLittleNum = loadLittleNum;
	}
	
	public void addLoadLittleNum(int loadLittleNum) {
		this.loadLittleNum += loadLittleNum;
	}

	public int getLoadMiddleNum() {
		return loadMiddleNum;
	}

	public void setLoadMiddleNum(int loadMiddleNum) {
		this.loadMiddleNum = loadMiddleNum;
	}
	
	public void addLoadMiddleNum(int loadMiddleNum) {
		this.loadMiddleNum += loadMiddleNum;
	}

	public int getLoadHeightNum() {
		return loadHeightNum;
	}

	public void setLoadHeightNum(int loadHeightNum) {
		this.loadHeightNum = loadHeightNum;
	}
	
	public void addLoadHeightNum(int loadHeightNum) {
		this.loadHeightNum += loadHeightNum;
	}

	public int getLoadStopNum() {
		return loadStopNum;
	}

	public void setLoadStopNum(int loadStopNum) {
		this.loadStopNum = loadStopNum;
	}
	
	public void addLoadStopNum(int loadStopNum) {
		this.loadStopNum += loadStopNum;
	}

	//负载总数
	public int getLoadTotalNum() {
		return loadLittleNum+loadMiddleNum+loadHeightNum+loadStopNum;
	}

	public int getRunRunnigNum() {
		return runRunnigNum;
	}

	public void setRunRunnigNum(int runRunnigNum) {
		this.runRunnigNum = runRunnigNum;
	}
	
	public void addRunRunnigNum(int runRunnigNum) {
		this.runRunnigNum += runRunnigNum;
	}
	
	public int getRunHangupNum() {
		return runHangupNum;
	}
	
	public void setRunHangupNum(int runHangupNum) {
		this.runHangupNum = runHangupNum;
	}
	
	public void addRunHangupNum(int runHangupNum) {
		this.runHangupNum += runHangupNum;
	}

	public int getRunCloseNum() {
		return runCloseNum;
	}
	
	public void setRunCloseNum(int runCloseNum) {
		this.runCloseNum = runCloseNum;
	}
	
	public void addRunCloseNum(int runCloseNum) {
		this.runCloseNum += runCloseNum;
	}
	
	//运行总数
	public int getRunTotalNum() {
		return runRunnigNum+runHangupNum+runCloseNum;
	}
}