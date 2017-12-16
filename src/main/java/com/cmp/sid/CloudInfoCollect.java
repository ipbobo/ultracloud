package com.cmp.sid;

//虚拟机信息汇总
public class CloudInfoCollect {
	private int vmCount;	   	//虚拟机总数
	private int cpuTotal;		//CPU总数
	private int memoryTotal;	//内存总数
	private int diskTotal;		//磁盘总数
	
	public int getVmCount() {
		return vmCount;
	}
	public void setVmCount(int vmCount) {
		this.vmCount = vmCount;
	}
	public int getCpuTotal() {
		return cpuTotal;
	}
	public void setCpuTotal(int cpuTotal) {
		this.cpuTotal = cpuTotal;
	}
	public int getMemoryTotal() {
		return memoryTotal;
	}
	public void setMemoryTotal(int memoryTotal) {
		this.memoryTotal = memoryTotal;
	}
	public int getDiskTotal() {
		return diskTotal;
	}
	public void setDiskTotal(int diskTotal) {
		this.diskTotal = diskTotal;
	}
	
	
}
