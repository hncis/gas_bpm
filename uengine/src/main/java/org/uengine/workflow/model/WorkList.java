package org.uengine.workflow.model;

import java.io.Serializable;

public class WorkList implements Serializable {

	private String initiator;
	private String endpoint;
	private String instanceId;
	private String rootInstanceId;
	private String taskId;
	private String tracingTag;
	private String title;
	private String defName;
	private String info;
	private String startDate;
	private String duplicateTaskCount;
	private String roleName;

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDefName() {
		return defName;
	}

	public void setDefName(String defName) {
		this.defName = defName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDuplicateTaskCount() {
		return duplicateTaskCount;
	}

	public void setDuplicateTaskCount(String duplicateTaskCount) {
		this.duplicateTaskCount = duplicateTaskCount;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
