package com.defaultcompany.external.model.stdmsg;

public class ProcessStartMsg extends BaseStdMsg {

	private static final long serialVersionUID = 1L;
	
	private String initiator;
	private String procAlias;
	private String instanceName;
	private String approvalKey;
	private String comment;
	private String mainParam;
	private String subParam;
	private boolean firstTaskCompleted;
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isFirstTaskCompleted() {
		return firstTaskCompleted;
	}

	public void setFirstTaskCompleted(boolean firstTaskCompleted) {
		this.firstTaskCompleted = firstTaskCompleted;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getProcAlias() {
		return procAlias;
	}

	public void setProcAlias(String procAlias) {
		this.procAlias = procAlias;
	}

	public String getApprovalKey() {
		return approvalKey;
	}

	public void setApprovalKey(String approvalKey) {
		this.approvalKey = approvalKey;
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
	
}
