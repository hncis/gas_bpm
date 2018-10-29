package org.uengine.kernel;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ReceiveActivity;


/**
 * @author Jinyoung Jang
 */

public class ReceiveAndReplyActivity extends ComplexActivity{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public ReceiveAndReplyActivity(){
		super();
		setName("receive~reply");
		setChildActivities(new Activity[]{
			new ReceiveActivity(),
			new WebServiceActivity()
		});
	}
	
/*	protected void queueActivity(final Activity act, final ActivityInstance instance) throws Exception{
		act.executeActivity(instance);		
	}	

	ProcessVariable output;
		public ProcessVariable getOutput() {
			return output;
		}
		public void setOutput(ProcessVariable variable) {
			output = variable;
		}
*/
}

