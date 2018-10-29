package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.LoopActivity;
import org.uengine.kernel.descriptor.ActivityDescriptor;
import org.uengine.processdesigner.ProcessDesigner;
public class LoopActivityDescriptor extends ActivityDescriptor {

	public LoopActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
		setFieldDisplayNames(LoopActivity.class);
	}

}
