package org.uengine.processdesigner.inputters;

import org.metaworks.inputter.TextInput;
import org.uengine.contexts.TextContext;

public class org_uengine_contexts_TextContextInput extends TextInput{

	TextContext value;
	
	public Object getValue() {
		if(value==null)
			value = TextContext.createInstance();
		
		value.setText((String)super.getValue());
		
		return value;
	}

	public void setValue(Object obj) {
		value = (TextContext)obj;
		if(value==null)
			value = TextContext.createInstance();
		
		super.setValue(value.getText());
	}

	public boolean isEligibleType(Class type) {
		return TextContext.class.isAssignableFrom(type);
	}

}
