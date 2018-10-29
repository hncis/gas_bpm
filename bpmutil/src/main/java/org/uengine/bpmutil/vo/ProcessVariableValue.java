package org.uengine.bpmutil.vo;

import java.io.Serializable;

public class ProcessVariableValue implements Serializable {

	private Object varValue;

	/**
	 * @return the varValue
	 */
	public Object getVarValue() {
		return varValue;
	}

	/**
	 * @param varValue the varValue to set
	 */
	public void setVarValue(Object varValue) {
		this.varValue = varValue;
	}

}
