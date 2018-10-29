package org.uengine.kernel;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.uengine.processdesigner.ProcessDesigner;


/**
 * @author Jinyoung Jang
 */

public class DiscoverActivity extends DefaultActivity{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public static void metaworksCallback_changeMetadata(Type type){
		type.setName((String)ProcessDesigner.getInstance().getActivityTypeNameMap().get(DiscoverActivity.class));
	}
		
	public DiscoverActivity(){
		setName("WS search");
	}
}

