package org.uengine.smcp.twister.engine.priv.core.definition;

import java.util.Collection;

/**
 * The invoke activity allows the business process to inoke a one-way or * request-response operation on a portType offered by a partner.
 */

public interface Invoke extends BasicActivity {

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
	 * @uml.property name="inputVariable"
	 */
	public String getInputVariable();

	/**
	 * 
	 * @uml.property name="inputVariable"
	 */
	public void setInputVariable(String variable);

	/**
	 * 
	 * @uml.property name="outputVariable"
	 */
	public String getOutputVariable();

	/**
	 * 
	 * @uml.property name="outputVariable"
	 */
	public void setOutputVariable(String variable);

	/**
	 * 
	 * @uml.property name="correlations"
	 */
	public Collection getCorrelations();

    public void addCorrelation(CorrelationRef correlationRef);
    
}
