package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.FTPActivity;
import org.uengine.kernel.JMSQueueActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class FTPActivityDescriptor extends ActivityDescriptor {

	public FTPActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(FTPActivity.class);
	}

}
