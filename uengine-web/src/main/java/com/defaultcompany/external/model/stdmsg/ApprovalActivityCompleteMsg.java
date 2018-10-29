package com.defaultcompany.external.model.stdmsg;

public class ApprovalActivityCompleteMsg extends ActivityCompleteMsg {

	private static final long serialVersionUID = 1L;

	private String approvalKey;
	private String approvalStatus;
	private String comment;

	public String getApprovalKey() {
		return approvalKey;
	}

	public void setApprovalKey(String approvalKey) {
		this.approvalKey = approvalKey;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
