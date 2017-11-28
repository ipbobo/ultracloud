package com.cmp.workflow.entity;

import java.io.Serializable;

public class ProcessInstanceVo  implements Serializable {
	
	private static final long serialVersionUID = -5615413562887473753L;

	private String id;
	
	private String processInstanceId;
	
	private String processDefinitionId;
	
	private TaskVo task;

	public ProcessInstanceVo() {
		super();
	}

	public ProcessInstanceVo(String id, String processInstanceId, String processDefinitionId) {
		super();
		this.id = id;
		this.processInstanceId = processInstanceId;
		this.processDefinitionId = processDefinitionId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public TaskVo getTask() {
		return task;
	}

	public void setTask(TaskVo task) {
		this.task = task;
	}

}
