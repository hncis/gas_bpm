package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.CorrelationRef;
import org.uengine.smcp.twister.engine.priv.core.definition.MessageEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Persistent implementation of the MessageEvent interface.
 * @see org.smcp.twister.engine.priv.core.definition.MessageEvent
 */
public class MessageEventImpl implements MessageEvent {

    private Long id;
    private String partnerLink;
    private String portType;
    private String operation;
    private String variable;

	/**
	 * 
	 * @uml.property name="correlations"
	 * @uml.associationEnd 
	 * @uml.property name="correlations" multiplicity="(0 -1)" elementType="org.uengine.smcp.twister.engine.priv.core.definition.CorrelationRef"
	 */
	private Collection correlations = new HashSet();

	/**
	 * 
	 * @uml.property name="id"
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @uml.property name="id"
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @uml.property name="partnerLink"
	 */
	public String getPartnerLink() {
		return partnerLink;
	}

	/**
	 * 
	 * @uml.property name="partnerLink"
	 */
	public void setPartnerLink(String partnerLink) {
		this.partnerLink = partnerLink;
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
