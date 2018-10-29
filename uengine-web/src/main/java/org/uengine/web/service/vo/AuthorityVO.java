package org.uengine.web.service.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.uengine.kernel.GlobalContext;

public class AuthorityVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
	String processCode;
	String bizKey;
	String statusCode;
	String userId;
	
	boolean approve;
	boolean save; 	
	boolean delegate;
	boolean collect;
	boolean reject;
	boolean complete;
	boolean delete;
	
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getBizKey() {
		return bizKey;
	}
	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isApprove() {
		return approve;
	}
	public void setApprove(boolean approve) {
		this.approve = approve;
	}
	public boolean isSave() {
		return save;
	}
	public void setSave(boolean save) {
		this.save = save;
	}
	public boolean isDelegate() {
		return delegate;
	}
	public void setDelegate(boolean delegate) {
		this.delegate = delegate;
	}
	public boolean isCollect() {
		return collect;
	}
	public void setCollect(boolean collect) {
		this.collect = collect;
	}
	public boolean isReject() {
		return reject;
	}
	public void setReject(boolean reject) {
		this.reject = reject;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}

}
