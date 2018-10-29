package org.uengine.kernel.descriptor;

import org.uengine.processdesigner.*;
import org.uengine.kernel.Activity;
import org.uengine.kernel.FileGenerateActivity;
import org.metaworks.*;
import org.metaworks.inputter.TextAreaInput;

/**
 * @author Jinyoung Jang
 */

public class FileGenerateActivityDescriptor extends ActivityDescriptor{

	public FileGenerateActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(final ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		FieldDescriptor fd = getFieldDescriptor("Content");
		fd.setInputter(new TextAreaInput());		

		setFieldDisplayNames(FileGenerateActivity.class);
	}
	
}