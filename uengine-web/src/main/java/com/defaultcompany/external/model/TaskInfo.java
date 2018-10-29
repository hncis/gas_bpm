package com.defaultcompany.external.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.uengine.kernel.GlobalContext;

public class TaskInfo implements Serializable {
	
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	private String instanceId;
	private String taskId;
	private String tracingTag;
	
	private String handler; // BPM_WORKLIST - HANDLER
	private String mainParam; // BPM_WORKLIST - EXT1
	private String subParam; // BPM_WORKLIST - EXT2
	
	private String externalKey; // BPM_PROCINST - INFO

	public TaskInfo() {
		super();
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
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

	public String getExternalKey() {
		return externalKey;
	}

	public void setExternalKey(String externalKey) {
		this.externalKey = externalKey;
	}

	public String getMainParam() {
		return mainParam;
	}

	public void setMainParam(String mainParam) {
		this.mainParam = mainParam;
	}

	public String getSubParam() {
		return subParam;
	}

	public void setSubParam(String subParam) {
		this.subParam = subParam;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
