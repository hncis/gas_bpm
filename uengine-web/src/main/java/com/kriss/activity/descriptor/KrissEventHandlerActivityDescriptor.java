package com.kriss.activity.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.descriptor.ActivityDescriptor;
import org.uengine.processdesigner.ProcessDesigner;

import com.kriss.activity.KrissEventHandlerActivity;


public class KrissEventHandlerActivityDescriptor extends ActivityDescriptor {

	public KrissEventHandlerActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
		
		
		setAttributeIgnoresError("EventScopeActivity", 	"hidden", true);
		setFieldDisplayNames(KrissEventHandlerActivity.class);
	}

}
