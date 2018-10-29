package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ReceiveActivity;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;
import org.metaworks.*;

/**
 * @author Jinyoung Jang
 */

public class ReceiveActivityDescriptor extends ActivityDescriptor{

	static{
		fieldOrder.insertElementAt("MessageDefinition", 0);
	}

	public ReceiveActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		FieldDescriptor fd; /*= getFieldDescriptor("Output");		
		fd.setAttribute("hidden", new Boolean(true));*/

		fd = getFieldDescriptor("Parameters");
		ProcessVariableArrayInput parameterInputter = (ProcessVariableArrayInput)fd.getInputter();
		
		//fd = getFieldDescriptor("Message");
		//fd.setAttribute("hidden", new Boolean(true));
		
		//Hidden
		setAttributeIgnoresError("Message", 	"hidden", true);
		
		fd = getFieldDescriptor("MessageDefinition");
		fd.setInputter(new MessageDefinitionInput((ProcessDefinition)pd.getProcessDefinitionDesigner().getActivity(), parameterInputter));

		setFieldDisplayNames(ReceiveActivity.class);
	}
	
}