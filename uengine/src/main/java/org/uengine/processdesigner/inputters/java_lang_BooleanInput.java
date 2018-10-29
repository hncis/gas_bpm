package org.uengine.processdesigner.inputters;

import org.metaworks.inputter.RadioInput;

/**
 * @author Jinyoung Jang
 */

public class java_lang_BooleanInput extends RadioInput {
	public java_lang_BooleanInput(){
		super(
			new String[]{"Yes", "No"}, 
			new Object[]{new Boolean(true), new Boolean(false)}
		);
	}

}
