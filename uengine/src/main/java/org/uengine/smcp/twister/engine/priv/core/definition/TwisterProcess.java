package org.uengine.smcp.twister.engine.priv.core.definition;

import java.util.Collection;

/**
 * The business process is the root container for all activities. It's therefore * modeled as an activity container. But it cannot be used as a standard container * as it doesn't support the common attributes and constructs of standard * activities. Trying to use methods inherited from Activity on a TwisterProcess will * return null or empty values. * @see CorrelationSet * @see Property
 */

public interface TwisterProcess {

	/**
	 * 
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * 
	 * @uml.property name="namespace"
	 */
	public String getNamespace();

	/**
	 * 
	 * @uml.property name="activity"
	 * @uml.associationEnd 
	 * @uml.property name="activity" multiplicity="(0 1)" inverse="process:org.uengine.smcp.twister.engine.priv.core.definition.Activity"
	 */
	public Activity getActivity();

	/**
	 * 
	 * @uml.property name="instances"
	 */
	public Collection getInstances();

	/**
	 * 
	 * @uml.property name="correlationSets"
	 */
	public Collection getCorrelationSets();

    public CorrelationSet getCorrelationSet(String setName);

	/**
	 * 
	 * @uml.property name="properties"
	 */
	public Collection getProperties();

    public Property getProperty(String propName);

	/**
	 * 
	 * @uml.property name="variables"
	 */
	public Collection getVariables();

    public Variable getVariable(String name);
}
