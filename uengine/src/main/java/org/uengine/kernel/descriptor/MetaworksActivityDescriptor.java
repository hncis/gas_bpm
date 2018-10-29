package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.MetaworksActivity;
import org.uengine.processdesigner.*;
import org.metaworks.*;

/**
 * @author Jinyoung Jang
 */

public class MetaworksActivityDescriptor extends HumanActivityDescriptor{

	public MetaworksActivityDescriptor() throws Exception{
		super();		
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, null);
			
		//FieldDescriptor fd = getFieldDescriptor("Tool");
		//fd.setAttribute("hidden", new Boolean(true));
		
		//fd = getFieldDescriptor("Input");
		//fd.setAttribute("hidden", new Boolean(true));
		
		//fd = getFieldDescriptor("Parameters");
		//fd.setAttribute("hidden", new Boolean(true));
		
		setAttributeIgnoresError("Tool", 	"hidden", true);
		setAttributeIgnoresError("Input", 	"hidden", true);
		setAttributeIgnoresError("Parameters", 	"hidden", true);

		setFieldDisplayNames(MetaworksActivity.class);
	}
	
}