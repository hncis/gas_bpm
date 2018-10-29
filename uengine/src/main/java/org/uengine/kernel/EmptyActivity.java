package org.uengine.kernel;

import org.metaworks.Type;
import org.uengine.processdesigner.ProcessDesigner;



/**
 * @author Jinyoung Jang
 */

public class EmptyActivity extends DefaultActivity{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public static void metaworksCallback_changeMetadata(Type type){
		type.setName((String)ProcessDesigner.getInstance().getActivityTypeNameMap().get(EmptyActivity.class));
	}
	
	public EmptyActivity(){
		super("Skip");
	}
	
	public void executeActivity(ProcessInstance instance) throws Exception{
		fireComplete(instance);
	}
}

