package com.kriss.activity.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.descriptor.ActivityDescriptor;
import org.uengine.processdesigner.ProcessDesigner;

import com.kriss.activity.KrissApprovalLineActivity;
import com.kriss.activity.KrissHumanActivity;

public class KrissApprovalLineActivityDescriptor extends ActivityDescriptor {

	public KrissApprovalLineActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
		
		
		setFieldDisplayNames(KrissApprovalLineActivity.class);
	}

}
