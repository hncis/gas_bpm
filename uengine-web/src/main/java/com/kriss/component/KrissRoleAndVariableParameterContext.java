package com.kriss.component;

import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.Role;

public class KrissRoleAndVariableParameterContext extends ParameterContext {
	
	Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
