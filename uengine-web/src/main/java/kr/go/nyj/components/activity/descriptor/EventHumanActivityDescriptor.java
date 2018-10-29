package kr.go.nyj.components.activity.descriptor;

import org.metaworks.FieldDescriptor;
import org.uengine.kernel.Activity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.descriptor.ActivityDescriptor;
import org.uengine.processdesigner.ProcessDesigner;

public class EventHumanActivityDescriptor extends ActivityDescriptor {

	public EventHumanActivityDescriptor() throws Exception {
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		
		FieldDescriptor fd = getFieldDescriptor("EventSubProcessActivity");
		fd.setAttribute("hidden", new Boolean(true));
		setFieldDisplayNames(HumanActivity.class);
	}
}
