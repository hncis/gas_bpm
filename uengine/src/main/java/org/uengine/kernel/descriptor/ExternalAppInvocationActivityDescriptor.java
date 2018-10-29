package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.ExternalAppInvocationActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class ExternalAppInvocationActivityDescriptor extends ActivityDescriptor {

	public ExternalAppInvocationActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(ExternalAppInvocationActivity.class);
	}

}
