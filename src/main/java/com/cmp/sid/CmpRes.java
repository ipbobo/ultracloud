package com.cmp.sid;

//资源信息
public class CmpRes {
	private String bizviewType;//业务视图总览类型
	private String bizviewTypeName;//业务视图总览类型名称
	private String subBizviewType;//子业务视图总览类型
	private String subBizviewTypeName;//子业务视图总览类型名称
	private String cpuTotalNum;//CPU总量
	private String cpuUseNum;//CPU已分配
	private String cpuAppNum;//CPU申请中
	private String cpuRestNum;//CPU剩余
	private String memTotalNum;//内存总量
	private String memUseNum;//内存已分配
	private String memAppNum;//内存申请中
	private String memRestNum;//内存剩余
	private String storeTotalNum;//存储总量
	private String storeUseNum;//存储已分配
	private String storeAppNum;//存储申请中
	private String storeRestNum;//存储剩余
	private String storeAssignNum;//存储已分配
	private String loadLittleNum;//轻度负载
	private String loadMiddleNum;//中度负载
	private String loadHeightNum;//高度负载
	private String loadStopNum;//停机负载
	private String runRunnigNum;//运行
	private String runHangupNum;//挂起
	private String runCloseNum;//关机

	public String getBizviewType() {
		return bizviewType;
	}

	public void setBizviewType(String bizviewType) {
		this.bizviewType = bizviewType;
	}

	public String getBizviewTypeName() {
		return bizviewTypeName;
	}

	public void setBizviewTypeName(String bizviewTypeName) {
		this.bizviewTypeName = bizviewTypeName;
	}

	public String getSubBizviewType() {
		return subBizviewType;
	}

	public void setSubBizviewType(String subBizviewType) {
		this.subBizviewType = subBizviewType;
	}

	public String getSubBizviewTypeName() {
		return subBizviewTypeName;
	}

	public void setSubBizviewTypeName(String subBizviewTypeName) {
		this.subBizviewTypeName = subBizviewTypeName;
	}

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

	public String getCpuAppNum() {
		return cpuAppNum;
	}

	public void setCpuAppNum(String cpuAppNum) {
		this.cpuAppNum = cpuAppNum;
	}

	public String getCpuRestNum() {
		return cpuRestNum;
	}

	public void setCpuRestNum(String cpuRestNum) {
		this.cpuRestNum = cpuRestNum;
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

	public String getMemAppNum() {
		return memAppNum;
	}

	public void setMemAppNum(String memAppNum) {
		this.memAppNum = memAppNum;
	}

	public String getMemRestNum() {
		return memRestNum;
	}

	public void setMemRestNum(String memRestNum) {
		this.memRestNum = memRestNum;
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

	public String getStoreAppNum() {
		return storeAppNum;
	}

	public void setStoreAppNum(String storeAppNum) {
		this.storeAppNum = storeAppNum;
	}

	public String getStoreRestNum() {
		return storeRestNum;
	}

	public void setStoreRestNum(String storeRestNum) {
		this.storeRestNum = storeRestNum;
	}

	public String getStoreAssignNum() {
		return storeAssignNum;
	}

	public void setStoreAssignNum(String storeAssignNum) {
		this.storeAssignNum = storeAssignNum;
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