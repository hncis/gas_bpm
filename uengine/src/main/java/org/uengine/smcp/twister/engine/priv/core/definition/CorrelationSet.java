package org.uengine.smcp.twister.engine.priv.core.definition;

import java.util.Collection;

/**
 * A correlation set is a set of named properties used to add contextual * information to a message. The goal of a correlation set is to give * a stateful meaning to message exchange (for example customer ids, order * number...).
 */

public interface CorrelationSet {

	/**
	 * 
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * 
	 * @uml.property name="properties"
	 */
	public Collection getProperties();

}
