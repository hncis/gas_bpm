package com.defaultcompany.web.dashboard;

import java.sql.Timestamp;

public class DashboardProcessList {
	private int instId;
	private String instName;
	private String defname;
	private String status;
	private Timestamp startDate;

	public int getInstId() {
		return instId;
	}

	public void setInstId(int instId) {
		this.instId = instId;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getDefname() {
		return defname;
	}

	public void setDefname(String defname) {
		this.defname = defname;
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
