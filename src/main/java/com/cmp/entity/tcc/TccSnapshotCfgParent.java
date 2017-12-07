package com.cmp.entity.tcc;

public class TccSnapshotCfgParent extends TccSnapshotCfg {

	private static final long serialVersionUID = -2684199891124668409L;

	private String	ip;
	private String	enviroment;
	private String	appSystem;
	private String	serverType;
	private String	applyedPeople;

	private String exe_time_cycle;

	/* 是否启用动态任务执行定时快照策略 */
	private String quart_enable;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEnviroment() {
		return enviroment;
	}

	public void setEnviroment(String enviroment) {
		this.enviroment = enviroment;
	}

	public String getAppSystem() {
		return appSystem;
	}

	public void setAppSystem(String appSystem) {
		this.appSystem = appSystem;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getApplyedPeople() {
		return applyedPeople;
	}

	public void setApplyedPeople(String applyedPeople) {
		this.applyedPeople = applyedPeople;
	}

	public String getLeadPeople() {
		return leadPeople;
	}

	public void setLeadPeople(String leadPeople) {
		this.leadPeople = leadPeople;
	}

	/**
	 * @return the exe_time_cycle
	 */
	public String getExe_time_cycle() {
		return exe_time_cycle;
	}

	/**
	 * @param exe_time_cycle the exe_time_cycle to set
	 */
	public void setExe_time_cycle(String exe_time_cycle) {
		this.exe_time_cycle = exe_time_cycle;
	}

	/**
	 * @return the quart_enable
	 */
	public String getQuart_enable() {
		return quart_enable;
	}

	/**
	 * @param quart_enable the quart_enable to set
	 */
	public void setQuart_enable(String quart_enable) {
		this.quart_enable = quart_enable;
	}

	private String leadPeople;
}
