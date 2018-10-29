package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.URLActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class URLActivityDescriptor extends HumanActivityDescriptor {

	public URLActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(URLActivity.class);
	}

}
