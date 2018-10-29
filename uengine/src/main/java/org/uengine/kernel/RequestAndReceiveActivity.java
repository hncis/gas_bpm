package org.uengine.kernel;


/**
 * @author Jinyoung Jang
 */

public class RequestAndReceiveActivity extends ComplexActivity{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public RequestAndReceiveActivity(){
		super();
		setName("req.~rcv.");
		setChildActivities(new Activity[]{
			new WebServiceActivity(),
			new ReceiveActivity()
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

