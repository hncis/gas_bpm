package com.kriss.activity;

import org.uengine.contexts.TextContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;

public class KrissEventHumanActivity extends KrissHumanActivity {
	

	
	@Override
	public void executeActivity(ProcessInstance instance) throws Exception {
		
		setApprover(instance, super.getRole().getMapping(instance));
		super.executeActivity(instance);
	}

	private KrissEventScopeActivity getEventScopeActivity() {
		Activity parent = getParentActivity();
		while (!(parent instanceof KrissEventScopeActivity)){
			parent = parent.getParentActivity();
		}
		return (KrissEventScopeActivity) parent;
	}

	protected String createApproverRoleName(){
		String roleName = role.getName();
		return roleName+"_"+getEventScopeActivity().getTracingTag();
	}
	


	RoleMapping approver;
	public RoleMapping getApprover(ProcessInstance instance) {
		RoleMapping rm;
		try {
			rm = instance.getRoleMapping(createApproverRoleName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if(rm != null)
			return rm;
		else
			return approver;
	}
	
	public void setApprover(ProcessInstance instance, RoleMapping approver) {
		this.approver = approver;
		try {
			instance.putRoleMapping(createApproverRoleName(), approver);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public Role getRole() {
		return new Role(){
			
			public String getName() {
				return getDisplayName().getText();
			}

			public TextContext getDisplayName() {
				TextContext tc = TextContext.createInstance();
				
				tc.setText(createApproverRoleName());
				return tc;
			}

			public RoleMapping getMapping(ProcessInstance inst, String tracingTag) throws Exception {
				
				return getApprover(inst);
			}
			
		};
	}

	@Override
	public RoleMapping getActualMapping(ProcessInstance instance) throws Exception {
		if(getApprover(instance)==null) return super.getActualMapping(instance);
		
		return getApprover(instance);
	}
	
	
	

}
