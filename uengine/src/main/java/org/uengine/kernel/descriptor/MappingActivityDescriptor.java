package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.MappingActivity;
import org.uengine.kernel.StatusReportActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class MappingActivityDescriptor extends ActivityDescriptor {

	public MappingActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(MappingActivity.class);
	}

}
