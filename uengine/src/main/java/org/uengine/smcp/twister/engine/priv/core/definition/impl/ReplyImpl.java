package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.Reply;

import java.util.Collection;
import java.util.HashSet;

/**
 * Persistent implementation of the Reply interface.
 * @see org.smcp.twister.engine.priv.core.definition.Reply
 */
public class ReplyImpl extends BasicActivityImpl implements Reply {

    private String partner;
    private String portType;
    private String operation;
    private String variable;
    private String faultName;

    private Collection correlations = new HashSet();

	/**
	 * 
	 * @uml.property name="partner"
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * 
	 * @uml.property name="partner"
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}

	/**
	 * 
	 * @uml.property name="portType"
	 */
	public String getPortType() {
		return portType;
	}

	/**
	 * 
	 * @uml.property name="portType"
	 */
	public void setPortType(String portType) {
		this.portType = portType;
	}

	/**
	 * 
	 * @uml.property name="operation"
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * 
	 * @uml.property name="operation"
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * 
	 * @uml.property name="variable"
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * 
	 * @uml.property name="variable"
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}

	/**
	 * 
	 * @uml.property name="faultName"
	 */
	public String getFaultName() {
		return faultName;
	}

	/**
	 * 
	 * @uml.property name="faultName"
	 */
	public void setFaultName(String faultName) {
		this.faultName = faultName;
	}

	/**
	 * 
	 * @uml.property name="correlations"
	 */
	public Collection getCorrelations() {
		return correlations;
	}

	/**
	 * 
	 * @uml.property name="correlations"
	 */
	public void setCorrelations(Collection correlations) {
		this.correlations = correlations;
	}

}
