package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.FormApprovalLineActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class FormApprovalLineActivityDescriptor extends ActivityDescriptor {

	public FormApprovalLineActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(FormApprovalLineActivity.class);
	}

}
