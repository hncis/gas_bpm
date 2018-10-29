package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * A property alias defines a globally named property as an alias for * a location in a part of a message.
 */

public interface PropertyAlias {

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

	/**
	 * 
	 * @uml.property name="part"
	 */
	public String getPart();

	/**
	 * 
	 * @uml.property name="part"
	 */
	public void setPart(String part);

	/**
	 * 
	 * @uml.property name="query"
	 */
	public String getQuery();

	/**
	 * 
	 * @uml.property name="query"
	 */
	public void setQuery(String query);

}
