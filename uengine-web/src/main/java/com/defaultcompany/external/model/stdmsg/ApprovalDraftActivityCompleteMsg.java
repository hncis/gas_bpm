package com.defaultcompany.external.model.stdmsg;

public class ApprovalDraftActivityCompleteMsg extends ActivityCompleteMsg {

	private static final long serialVersionUID = 1L;

	private String approvalKey;
	private String mainParam;
	private String subParam;
	private String comment;

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
