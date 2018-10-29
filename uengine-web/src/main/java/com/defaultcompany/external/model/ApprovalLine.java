package com.defaultcompany.external.model;

public class ApprovalLine {
	
	private String approvalKey;
	private int instanceId;
	private int version;
	private String lineStatus;
	
	public ApprovalLine() {
		super();
	}

	public ApprovalLine(String approvalKey, int instanceId, int version, String lineStatus) {
		super();
		this.approvalKey = approvalKey;
		this.instanceId = instanceId;
		this.version = version;
		this.lineStatus = lineStatus;
	}

	public String getApprovalKey() {
		return approvalKey;
	}

	public void setApprovalKey(String approvalKey) {
		this.approvalKey = approvalKey;
	}

	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}

}
