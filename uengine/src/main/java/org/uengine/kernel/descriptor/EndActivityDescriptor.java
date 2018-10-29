package org.uengine.kernel.descriptor;

import org.metaworks.Dependancy;
import org.metaworks.EnablingDependancy;
import org.metaworks.FieldDescriptor;
import org.uengine.kernel.Activity;
import org.uengine.kernel.EndActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class EndActivityDescriptor extends ActivityDescriptor {

	public EndActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		
		FieldDescriptor escalationLevelFd = getFieldDescriptor("EscalationLevel");
		
		escalationLevelFd.setAttribute("dependancy", new EnablingDependancy("Escalate"){

			public boolean enableIf(Object dependencyFieldValue) {
				Boolean dependencyFieldValueBoolean = (Boolean)dependencyFieldValue;
				if(dependencyFieldValue!=null && dependencyFieldValueBoolean.booleanValue())
					return false;
				else
					return true;
			}
			
		});
		
		setFieldDisplayNames(EndActivity.class);
	}

}
