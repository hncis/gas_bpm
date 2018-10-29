package org.uengine.smcp.twister.engine.priv.core.definition;

import java.util.Collection;

public interface Property {

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
	 * @uml.property name="type"
	 */
	public String getType();

	/**
	 * 
	 * @uml.property name="type"
	 */
	public void setType(String type);

	/**
	 * 
	 * @uml.property name="aliases"
	 */
	public Collection getAliases();

    public void addAlias(PropertyAlias alias);

	/**
	 * 
	 * @uml.property name="aliases"
	 */
	public void setAliases(Collection aliases);

}
