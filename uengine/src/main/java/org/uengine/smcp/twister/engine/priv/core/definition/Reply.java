package org.uengine.smcp.twister.engine.priv.core.definition;

import java.util.Collection;

/**
 * The Reply activity allows the business process to send a message in * reply to a message that was received through a Receive. The combination * of a receive and a reply forms a request-response operation on the WSDL * portType for the process.
 */

public interface Reply extends BasicActivity {

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

	/**
	 * 
	 * @uml.property name="faultName"
	 */
	public String getFaultName();

	/**
	 * 
	 * @uml.property name="faultName"
	 */
	public void setFaultName(String faultName);

	/**
	 * 
	 * @uml.property name="correlations"
	 */
	public Collection getCorrelations();

	/**
	 * 
	 * @uml.property name="correlations"
	 */
	public void setCorrelations(Collection correlations);

}
