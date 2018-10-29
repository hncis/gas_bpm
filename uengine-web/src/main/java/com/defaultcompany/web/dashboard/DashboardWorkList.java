package com.defaultcompany.web.dashboard;

import java.sql.Timestamp;

public class DashboardWorkList {

	private int instId;
	private String trcTag;
	private int taskId;

	private String title;
	private String procinstnm;
	private String status;

	private Timestamp startDate;

	public int getInstId() {
		return instId;
	}

	public void setInstId(int instId) {
		this.instId = instId;
	}

	public String getTrcTag() {
		return trcTag;
	}

	public void setTrcTag(String trcTag) {
		this.trcTag = trcTag;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProcinstnm() {
		return procinstnm;
	}

	public void setProcinstnm(String procinstnm) {
		this.procinstnm = procinstnm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

}
