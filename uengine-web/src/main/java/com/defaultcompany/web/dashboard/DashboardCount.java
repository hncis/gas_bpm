package com.defaultcompany.web.dashboard;

public class DashboardCount {

	private int newWork;
	private int savedWork;
	private int completedWork;
	private int runningProcess;
	private int completedProcess;

	public int getNewWork() {
		return newWork;
	}

	public void setNewWork(int newWork) {
		this.newWork = newWork;
	}

	public int getSavedWork() {
		return savedWork;
	}

	public void setSavedWork(int savedWork) {
		this.savedWork = savedWork;
	}

	public int getCompletedWork() {
		return completedWork;
	}

	public void setCompletedWork(int completedWork) {
		this.completedWork = completedWork;
	}

	public int getRunningProcess() {
		return runningProcess;
	}

	public void setRunningProcess(int runningProcess) {
		this.runningProcess = runningProcess;
	}

	public int getCompletedProcess() {
		return completedProcess;
	}

	public void setCompletedProcess(int completedProcess) {
		this.completedProcess = completedProcess;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("DashboardCount [");
		sb.append("newWork=").append(newWork).append(", ");
		sb.append("savedWork=").append(savedWork).append(", ");
		sb.append("completedWork=").append(completedWork).append(", ");
		sb.append("runningProcess=").append(runningProcess).append(", ");
		sb.append("completedProcess=").append(completedProcess);
		sb.append("]");
		
		return sb.toString();
	}

	public String toXML() {
		StringBuffer sb = new StringBuffer();

		sb.append("<DashboardCount>").append("\r\n");
		sb.append("  <newWork>").append(newWork).append("</newWork>").append("\r\n");
		sb.append("  <savedWork>").append(savedWork).append("</savedWork>").append("\r\n");
		sb.append("  <completedWork>").append(completedWork).append("</completedWork>").append("\r\n");
		sb.append("  <runningProcess>").append(runningProcess).append("</runningProcess>").append("\r\n");
		sb.append("  <completedProcess>").append(completedProcess).append("</completedProcess>").append("\r\n");
		sb.append("</DashboardCount>");

		return sb.toString();
	}

	public String toJSON() {
		StringBuffer sb = new StringBuffer();

		sb.append("{").append("\r\n");
		sb.append("  \"newWork\" : ").append(newWork).append(",").append("\r\n");
		sb.append("  \"savedWork\" : ").append(savedWork).append(",").append("\r\n");
		sb.append("  \"completedWork\" : ").append(completedWork).append(",").append("\r\n");
		sb.append("  \"runningProcess\" : ").append(runningProcess).append(",").append("\r\n");
		sb.append("  \"completedProcess\" : ").append(completedProcess).append("\r\n");
		sb.append("}");

		return sb.toString();
	}
	
}
