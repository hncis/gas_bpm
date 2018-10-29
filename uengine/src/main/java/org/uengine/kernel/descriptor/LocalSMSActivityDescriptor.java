package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.LocalSMSActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class LocalSMSActivityDescriptor extends ActivityDescriptor {

	public LocalSMSActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(LocalSMSActivity.class);
	}

}
