package org.uengine.ui.flowchart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ProcessDefinitionInfo implements Serializable {

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	/*
	 * Process Instance Info
	 */
	private String taskId;
	
	/*
	 * Process Definition Info
	 */
	private String name;
	private String tracingTag;
	private String type;
	private String status;
	private String roleName;
	private String duration;
	private String description;
	private String subDefId;
	private String[] condition;
	private ArrayList<ProcessDefinitionInfo> children;

	/*
	 * Process Instance Info
	 */
	private String endpoint;
	private String subInstId;
	private Date startedDate;
	private Date dueDate;
	private Date finishedDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTracingTag() {
		return tracingTag;
	}

	public void setTracingTag(String tracingTag) {
		this.tracingTag = tracingTag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubInstId() {
		return subInstId;
	}

	public void setSubInstId(String subInstId) {
		this.subInstId = subInstId;
	}

	public String getSubDefId() {
		return subDefId;
	}

	public void setSubDefId(String subDefId) {
		this.subDefId = subDefId;
	}

	public String[] getCondition() {
		return condition;
	}

	public void setCondition(String[] condition) {
		this.condition = condition;
	}

	public ArrayList<ProcessDefinitionInfo> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ProcessDefinitionInfo> children) {
		this.children = children;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getFinishedDate() {
		return finishedDate;
	}

	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
