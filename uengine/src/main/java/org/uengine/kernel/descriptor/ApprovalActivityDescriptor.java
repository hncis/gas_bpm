package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;
import org.metaworks.*;

/**
 * @author Jinyoung Jang
 */

public class ApprovalActivityDescriptor extends ActivityDescriptor{

	public ApprovalActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);

		setAttributeIgnoresError("Approver", 	"hidden", true);

		setFieldDisplayNames(HumanActivity.class);
	}

}