package kr.co.hncis.components.activity.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.descriptor.ActivityDescriptor;
import org.uengine.processdesigner.ProcessDesigner;

import kr.co.hncis.components.activity.HncisApprovalLineActivity;

public class HncisApprovalLineActivityDescriptor extends ActivityDescriptor {

	public HncisApprovalLineActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
		
		
		setFieldDisplayNames(HncisApprovalLineActivity.class);
	}

}
