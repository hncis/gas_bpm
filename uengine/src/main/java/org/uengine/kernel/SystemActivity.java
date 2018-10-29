package org.uengine.kernel;



import java.util.Map;

import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.EmptyActivity;
import org.uengine.kernel.MessageListener;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ReceiveActivity;
import org.uengine.kernel.ValidationContext;
import org.uengine.processdesigner.ProcessDesigner;
import org.metaworks.FieldDescriptor;
import org.metaworks.Type;

public class SystemActivity extends DefaultActivity implements MessageListener {
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public static void metaworksCallback_changeMetadata(Type type){
		type.setName("Default Info");
	}
	
	public SystemActivity(){
		super("system call");
	}
	
	public void executeActivity(ProcessInstance instance) throws Exception{
		getProcessDefinition().addMessageListener(instance, this); //subscribes to JMS topic
	}
	
	@Override
	public ValidationContext validate(Map options) {
		ValidationContext vc = super.validate(options);

		return vc;
	}

	public boolean onMessage(ProcessInstance instance, Object payload)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}


}
