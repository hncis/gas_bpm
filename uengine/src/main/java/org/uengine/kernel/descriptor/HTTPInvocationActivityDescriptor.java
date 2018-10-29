package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.HTTPInvocationActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class HTTPInvocationActivityDescriptor extends ActivityDescriptor {

	public HTTPInvocationActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(HTTPInvocationActivity.class);
	}

}
