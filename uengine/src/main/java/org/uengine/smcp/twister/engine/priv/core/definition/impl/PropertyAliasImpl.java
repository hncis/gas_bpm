package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.PropertyAlias;

/**
 * Persistent implementation of the Property interface.
 */
public class PropertyAliasImpl implements PropertyAlias{

    private Long id;
    private String messageType;
    private String part;
    private String query;

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
	 * @uml.property name="messageType"
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * 
	 * @uml.property name="messageType"
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * 
	 * @uml.property name="part"
	 */
	public String getPart() {
		return part;
	}

	/**
	 * 
	 * @uml.property name="part"
	 */
	public void setPart(String part) {
		this.part = part;
	}

	/**
	 * 
	 * @uml.property name="query"
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * 
	 * @uml.property name="query"
	 */
	public void setQuery(String query) {
		this.query = query;
	}

}
