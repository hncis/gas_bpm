package com.defaultcompany.external.model;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.StringUtils;

public class Approver {

	private String approvalKey;
	private int version;
	private String empCode;
	private String truthEmpCode;
	private String type;
	private String comment;
	private String status;
	private Timestamp endDate;
	private int order;
	private String taskId;
	private String tracingTag;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getApprovalKey() {
		return approvalKey;
	}

	public void setApprovalKey(String approvalKey) {
		this.approvalKey = approvalKey;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getTruthEmpCode() {
		return truthEmpCode;
	}

	public void setTruthEmpCode(String truthEmpCode) {
		this.truthEmpCode = truthEmpCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
//	public boolean isDelegated() {
//		if (!StringUtils.hasText(this.truthEmpCode) || this.empCode.equals(this.truthEmpCode)) {
//			return false;
//		}
//		return true;
//	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
