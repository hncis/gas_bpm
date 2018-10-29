package com.defaultcompany.external.model.stdmsg;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.defaultcompany.external.model.TaskInfo;

public class ReturnMsg {
	
	private String status;
	private List<TaskInfo> taskInfoList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TaskInfo> getTaskInfoList() {
		return taskInfoList;
	}

	public void setTaskInfoList(List<TaskInfo> taskInfoList) {
		this.taskInfoList = taskInfoList;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
