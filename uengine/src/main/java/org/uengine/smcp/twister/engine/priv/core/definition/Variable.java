package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * Variables provide the means for holding messages data that constitute the * state of a business process.
 */

public interface Variable {

	/**
	 * 
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * 
	 * @uml.property name="name"
	 */
	public void setName(String name);

	/**
	 * 
	 * @uml.property name="messageType"
	 */
	public String getMessageType();

	/**
	 * 
	 * @uml.property name="messageType"
	 */
	public void setMessageType(String messageType);

}
