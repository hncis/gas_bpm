package org.uengine.kernel.descriptor;

import org.uengine.kernel.DocumentActivity;
import org.uengine.processdesigner.*;
import org.metaworks.*;

/**
 * @author Jinyoung Jang
 */

public class DocumentActivityDescriptor extends HumanActivityDescriptor{

	public DocumentActivityDescriptor() throws Exception{
		super();		
	}
	
	public void setActivityClass(Class actCls) throws Exception {
		super.setActivityClass(actCls);
		setFieldDisplayNames("activitytypes." + actCls.getName().toLowerCase());

		//Disabled
		setAttributeIgnoresError("Tool", 	"disabled", true);
		//Hidden
		setAttributeIgnoresError("Input", 	"hidden", true);
		setAttributeIgnoresError("Parameters", 	"hidden", true);
		
		setFieldDisplayNames(DocumentActivity.class);
	}

}