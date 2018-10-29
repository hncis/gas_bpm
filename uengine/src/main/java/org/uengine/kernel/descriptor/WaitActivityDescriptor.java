package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.WaitActivity;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;
import org.metaworks.*;

/**
 * @author SeungHyeon Kim
 */

public class WaitActivityDescriptor extends ActivityDescriptor{
	
	public WaitActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		setFieldDisplayNames(WaitActivity.class);
	}
	
}