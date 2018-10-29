package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.BackActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class BackActivityDescriptor extends ActivityDescriptor {

	public BackActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(BackActivity.class);
	}

}
