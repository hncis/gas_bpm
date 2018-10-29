package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.TelnetNormalActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class TelnetNormalActivityDescriptor extends HumanActivityDescriptor {

	public TelnetNormalActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(TelnetNormalActivity.class);
	}

}
