package com.cmp.sid;

//仪表盘-负载情况
public class CmpCdLoad implements Comparable<CmpCdLoad> {
	private String resType;//资源类型：cpu-CPU、mem-内存、store-磁盘
	private String hostId;//主机ID
	private String hostName;//主机名	
	private String hostIp;//IP
	private float cpuLoad=0;//CPU负载
	private float memLoad=0;//内存负载
	private float storeLoad=0;//磁盘负载
	private float cpuNum=0;//CPU数量
	private float memNum=0;//内存数量
	private float storeNum=0;//磁盘数量

	@Override
	public int compareTo(CmpCdLoad obj) {
		if("cpu".equals(resType)){//CPU
			if (this.cpuLoad > obj.cpuLoad) {
				return -1;
			} else if (this.cpuLoad < obj.cpuLoad) {
				return 1;
			}
		}else if("mem".equals(resType)){//内存
			if (this.memLoad > obj.memLoad) {
				return -1;
			} else if (this.memLoad < obj.memLoad) {
				return 1;
			}
		}else if("store".equals(resType)){//磁盘
			if (this.storeLoad > obj.storeLoad) {
				return -1;
			} else if (this.storeLoad < obj.storeLoad) {
				return 1;
			}
		}
		
		return 0;
	}
	
	//获取最大负载
	public float getMaxLoad() {
		float maxLoad=cpuLoad;
		if(memLoad>maxLoad){
			maxLoad=memLoad;
		}
		if(storeLoad>maxLoad){
			maxLoad=storeLoad;
		}
		
		return maxLoad;
	}
	
	//使用率
	public String getUseRate() {
		if("cpu".equals(resType)){//CPU
			return cpuLoad*100+"%";
		}else if("mem".equals(resType)){//内存
			return memLoad*100+"%";
		}else if("store".equals(resType)){//磁盘
			return storeLoad*100+"%";
		}
		
		return null;
	}
	
	//使用量
	public String getUseNum() {
		if("cpu".equals(resType)){//CPU
			return cpuLoad*cpuNum+"核";
		}else if("mem".equals(resType)){//内存
			return memLoad*memNum+"GB";
		}else if("store".equals(resType)){//磁盘
			return storeLoad*storeNum+"GB";
		}
		
		return null;
	}
	
	//分配量
	public String getAllotNum() {
		if("cpu".equals(resType)){//CPU
			return cpuNum+"核";
		}else if("mem".equals(resType)){//内存
			return memNum+"GB";
		}else if("store".equals(resType)){//磁盘
			return storeNum+"GB";
		}
		
		return null;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public float getCpuLoad() {
		return cpuLoad;
	}

	public void setCpuLoad(float cpuLoad) {
		this.cpuLoad = cpuLoad;
	}

	public float getMemLoad() {
		return memLoad;
	}

	public void setMemLoad(float memLoad) {
		this.memLoad = memLoad;
	}

	public float getStoreLoad() {
		return storeLoad;
	}

	public void setStoreLoad(float storeLoad) {
		this.storeLoad = storeLoad;
	}

	public float getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(float cpuNum) {
		this.cpuNum = cpuNum;
	}

	public float getMemNum() {
		return memNum;
	}

	public void setMemNum(float memNum) {
		this.memNum = memNum;
	}

	public float getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(float storeNum) {
		this.storeNum = storeNum;
	}
}