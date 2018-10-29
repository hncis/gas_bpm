package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.CorrelationRef;
import org.uengine.smcp.twister.engine.priv.core.definition.Invoke;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Persistent implementation of the Invoke interface.
 * @see org.smcp.twister.engine.priv.core.definition.Invoke
 */
public class InvokeImpl extends BasicActivityImpl implements Invoke {

    private String partner;
    private String portType;
    private String operation;
    private String inputVariable;
    private String outputVariable;

	/**
	 * 
	 * @uml.property name="correlations"
	 * @uml.associationEnd 
	 * @uml.property name="correlations" multiplicity="(0 -1)" elementType="org.uengine.smcp.twister.engine.priv.core.definition.CorrelationRef"
	 */
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
	 * @uml.property name="inputVariable"
	 */
	public String getInputVariable() {
		return inputVariable;
	}

	/**
	 * 
	 * @uml.property name="inputVariable"
	 */
	public void setInputVariable(String inputVariable) {
		this.inputVariable = inputVariable;
	}

	/**
	 * 
	 * @uml.property name="outputVariable"
	 */
	public String getOutputVariable() {
		return outputVariable;
	}

	/**
	 * 
	 * @uml.property name="outputVariable"
	 */
	public void setOutputVariable(String outputVariable) {
		this.outputVariable = outputVariable;
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
		if (correlations instanceof Set) {
			this.correlations = correlations;
		} else {
			this.correlations = new HashSet(correlations);
		}
	}

    public void addCorrelation(CorrelationRef correlationRef) {
        correlations.add(correlationRef);
    }
}
