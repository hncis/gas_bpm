package org.uengine.kernel.descriptor;


import org.uengine.kernel.Activity;
import org.uengine.kernel.OfficeDocumentActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class OfficeDocumentActivityDescriptor extends HumanActivityDescriptor {

	public OfficeDocumentActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		setFieldDisplayNames(OfficeDocumentActivity.class);
	}

}
