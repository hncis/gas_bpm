package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.FlagActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class FlagActivityDescriptor extends ActivityDescriptor {

	public FlagActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(FlagActivity.class);
	}

}
