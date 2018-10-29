package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.SQLActivity;
import org.uengine.processdesigner.*;
import org.metaworks.*;
import org.metaworks.inputter.MultipleInput;
import org.metaworks.inputter.TextAreaInput;

/**
 * @author Jinyoung Jang
 */

public class SQLActivityDescriptor extends ActivityDescriptor{

	public SQLActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(final ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		FieldDescriptor fd = getFieldDescriptor("SqlStmt");
		
		TextAreaInput textAreaInput = new TextAreaInput();
		textAreaInput.setCols(45);
		textAreaInput.setRows(15);		
		fd.setInputter(textAreaInput);
		setFieldDisplayNames(SQLActivity.class);
		
		((MultipleInput) getFieldDescriptor("Parameters").getInputter()).getTable().removeFieldDescriptor("Type");
		((MultipleInput) getFieldDescriptor("SelectMappings").getInputter()).getTable().removeFieldDescriptor("Type");
	}

}