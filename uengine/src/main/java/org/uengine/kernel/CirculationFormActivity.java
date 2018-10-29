package org.uengine.kernel;

import org.metaworks.Type;
import org.uengine.kernel.FormActivity;

public class CirculationFormActivity extends FormActivity {
	public CirculationFormActivity() {
		setName("CirculationFormActivity");
	}
	
	public void executeActivity(ProcessInstance instance) throws Exception{
		super.executeActivity(instance);
		fireComplete(instance);
	}
}
