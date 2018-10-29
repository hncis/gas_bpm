package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.RoleAssignActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class RoleAssignActivityDescriptor extends ActivityDescriptor{

	public RoleAssignActivityDescriptor() throws Exception{
		super();
	}
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
		setFieldDisplayNames(RoleAssignActivity.class);
	}

}
