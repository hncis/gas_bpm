package org.uengine.bpmutil.vo;

import java.io.Serializable;
import java.util.List;

public class ProcessRole implements Serializable {

	private String roleName;
	private List<ProcessRoleId> roleIds;
	/**
	 * @return the rolName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param rolName the rolName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the roleIds
	 */
	public List<ProcessRoleId> getRoleIds() {
		return roleIds;
	}
	/**
	 * @param roleIds the roleIds to set
	 */
	public void setRoleIds(List<ProcessRoleId> roleIds) {
		this.roleIds = roleIds;
	}

}
