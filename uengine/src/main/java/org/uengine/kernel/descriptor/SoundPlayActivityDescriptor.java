package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.SoundPlayActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class SoundPlayActivityDescriptor extends ActivityDescriptor {

	public SoundPlayActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(SoundPlayActivity.class);
	}

}
