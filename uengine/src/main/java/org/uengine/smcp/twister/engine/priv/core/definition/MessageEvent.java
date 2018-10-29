package org.uengine.smcp.twister.engine.priv.core.definition;

import java.util.Collection;

/**
 * A message event is declared on a Pick activity. Its semantic is identical * to a receive activity, the Pick is waiting for the message corresponding * to the message event until an alarm is fired. * @see org.smcp.twister.engine.priv.core.definition.Pick
 */

public interface MessageEvent {

	/**
	 * 
	 * @uml.property name="partnerLink"
	 */
	public String getPartnerLink();

	/**
	 * 
	 * @uml.property name="partnerLink"
	 */
	public void setPartnerLink(String partnerLink);

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
	 * @uml.property name="correlations"
	 */
	public Collection getCorrelations();

    public void addCorrelation(CorrelationRef correlationRef);

}
