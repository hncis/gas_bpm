package org.uengine.kernel.descriptor;

import org.metaworks.FieldDescriptor;
import org.metaworks.inputter.TextAreaInput;
import org.metaworks.inputter.TextInput;
import org.uengine.kernel.Activity;
import org.uengine.kernel.FileGenerateActivity;
import org.uengine.processdesigner.ProcessDesigner;

public class FileGenerationActivityDescriptor extends ActivityDescriptor{

	public FileGenerationActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(final ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		FieldDescriptor fd = getFieldDescriptor("Contents");
		fd.setInputter(new TextAreaInput());		

		setFieldDisplayNames(FileGenerateActivity.class);
	}

}
