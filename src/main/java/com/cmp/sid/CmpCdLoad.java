package com.cmp.sid;

//仪表盘-负载情况
public class CmpCdLoad {
	private float cpuLoad=0;//CPU负载
	private float memLoad=0;//内存负载
	private float storeLoad=0;//磁盘负载

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
}