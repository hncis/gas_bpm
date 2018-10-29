package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.CorrelationRef;
import org.uengine.smcp.twister.engine.priv.core.definition.Receive;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Persistent implementation of the Receive interface.
 * @see org.smcp.twister.engine.priv.core.definition.Receive
 */
public class ReceiveImpl extends BasicActivityImpl implements Receive {

    private String partner;
    private String portType;
    private String operation;
    private String variable;
    private boolean createInstance = false;
    private boolean reply = false;

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


    public boolean isCreateInstance() {
        return createInstance;
    }

	/**
	 * 
	 * @uml.property name="createInstance"
	 */
	public void setCreateInstance(boolean createInstance) {
		this.createInstance = createInstance;
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

    public boolean isReply() {
        return reply;
    }

	/**
	 * 
	 * @uml.property name="reply"
	 */
	public void setReply(boolean reply) {
		this.reply = reply;
	}

}
