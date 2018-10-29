package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.StatusReportActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class StatusReportActivityDescriptor extends ActivityDescriptor {

	public StatusReportActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(StatusReportActivity.class);
	}

}
