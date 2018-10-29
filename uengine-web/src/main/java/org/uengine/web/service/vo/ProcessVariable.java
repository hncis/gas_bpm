package org.uengine.web.service.vo;

import java.io.Serializable;
import java.util.List;

public class ProcessVariable implements Serializable {

	private String varName;
	/**
	 * @return the varName
	 */
	public String getVarName() {
		return varName;
	}
	/**
	 * @param varName the varName to set
	 */
	public void setVarName(String varName) {
		this.varName = varName;
	}
	/**
	 * @return the varValues
	 */
	public List<ProcessVariableValue> getVarValues() {
		return varValues;
	}
	/**
	 * @param varValues the varValues to set
	 */
	public void setVarValues(List<ProcessVariableValue> varValues) {
		this.varValues = varValues;
	}
	private List<ProcessVariableValue> varValues;

}
