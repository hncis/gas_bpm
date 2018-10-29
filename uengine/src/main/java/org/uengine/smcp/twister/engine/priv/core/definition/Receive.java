package org.uengine.smcp.twister.engine.priv.core.definition;

import java.util.Collection;

/**
 * A receive activity waits until a matching message is received by * the engine.
 */

public interface Receive extends BasicActivity {

    public boolean isReply();
    public void setReply(boolean reply);

	/**
	 * 
	 * @uml.property name="partner"
	 */
	public String getPartner();

	/**
	 * 
	 * @uml.property name="partner"
	 */
	public void setPartner(String partner);

	/**
	 * 
	 * @uml.property name="portType"
	 */
	public String getPortType();

	/**
	 * 
	 * @uml.property name="portType"
	 */
	public void setPortType(String portType);

	/**
	 * 
	 * @uml.property name="operation"
	 */
	public String getOperation();

	/**
	 * 
	 * @uml.property name="operation"
	 */
	public void setOperation(String operation);

	/**
	 * 
	 * @uml.property name="variable"
	 */
	public String getVariable();

	/**
	 * 
	 * @uml.property name="variable"
	 */
	public void setVariable(String variable);


    public boolean isCreateInstance();
    public void setCreateInstance(boolean createInstance);

	/**
	 * 
	 * @uml.property name="correlations"
	 */
	public Collection getCorrelations();

    public void addCorrelation(CorrelationRef correlationRef);

}
