package org.uengine.workflow.model;

import java.io.Serializable;

public class NextTask implements Serializable {

	private String endpoint;
	private String instanceId;
	private String rootInstanceId;
	private String taskId;
	private String tracingTag;
	private String info;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getRootInstanceId() {
		return rootInstanceId;
	}

	public void setRootInstanceId(String rootInstanceId) {
		this.rootInstanceId = rootInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTracingTag() {
		return tracingTag;
	}

	public void setTracingTag(String tracingTag) {
		this.tracingTag = tracingTag;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
