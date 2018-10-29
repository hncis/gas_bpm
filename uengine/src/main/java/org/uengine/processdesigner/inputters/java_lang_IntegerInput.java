/*
 * Created on 2004-04-11
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.uengine.processdesigner.inputters;

import org.metaworks.inputter.NumberInput;

/**
 * @author Jinyoung Jang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class java_lang_IntegerInput extends NumberInput{

	public java_lang_IntegerInput(){
		super();
	}
	
	public boolean isEligibleType(Class type) {
		return Number.class.isAssignableFrom(type);
	}

}
