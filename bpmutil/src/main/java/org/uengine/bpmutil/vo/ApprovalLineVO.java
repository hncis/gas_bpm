package org.uengine.bpmutil.vo;

import java.io.Serializable;

public class ApprovalLineVO implements Serializable {
	
	/**
	 * 
	 */
	
	String title, roles;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
