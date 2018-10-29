package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.EMailActivity;
import org.uengine.processdesigner.*;
import org.metaworks.inputter.*;
import org.uengine.processdesigner.inputters.*;
import org.uengine.ui.XMLValueInput;
import org.metaworks.*;

/**
 * @author Jinyoung Jang
 */

public class EMailActivityDescriptor extends ActivityDescriptor{

	public EMailActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		FieldDescriptor fd = getFieldDescriptor("ToRole");
		fd.setInputter(new RoleInput(pd));
		
		fd = getFieldDescriptor("Contents");
		TextAreaInput contentsInput = new TextAreaInput();
		contentsInput.setCols(50);
		contentsInput.setRows(20);
		fd.setInputter(contentsInput);
		
		//fd = getFieldDescriptor("PortType");
		//if(fd!=null)
		//	fd.setAttribute("disabled", new Boolean(true));
			
		//fd = getFieldDescriptor("OperationName");
		//if(fd!=null)
		//	fd.setAttribute("disabled", new Boolean(true));
			
		//fd = getFieldDescriptor("Parameters");
		//if(fd!=null)
		//	fd.setAttribute("disabled", new Boolean(true));
		
		//Disabled
		setAttributeIgnoresError("PortType", 	"disabled", true);
		setAttributeIgnoresError("OperationName", 	"disabled", true);
		setAttributeIgnoresError("Parameters", 	"disabled", true);
		
		
		fd = getFieldDescriptor("TemplateFilePath");
		if(fd!=null)
			fd.setInputter(new XMLValueInput("/admin/processmanager/emailTemplateListXML.jsp"));


		setFieldDisplayNames(EMailActivity.class);
	}
	
}