package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.JMSQueueActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class JMSQueueActivityDescriptor extends ActivityDescriptor {

	public JMSQueueActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(JMSQueueActivity.class);
	}

}
