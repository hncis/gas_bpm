package org.uengine.web.service.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.uengine.kernel.GlobalContext;

public class ApprovalLineVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
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
