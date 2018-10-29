package org.uengine.smcp.twister.engine.priv.core.definition;

import java.util.Set;

/**
 * Root class for all activities, an activity is the most basic * part of a process defining what should be done.
 */

public interface Activity {

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
	 * @uml.property name="joinCondition"
	 */
	public String getJoinCondition();

	/**
	 * 
	 * @uml.property name="joinCondition"
	 */
	public void setJoinCondition(String expr);

	/**
	 * 
	 * @uml.property name="sourceLinks"
	 */
	public Set getSourceLinks();

	/**
	 * 
	 * @uml.property name="sourceLinks"
	 */
	public void setSourceLinks(Set sources);

	/**
	 * 
	 * @uml.property name="targetLinks"
	 */
	public Set getTargetLinks();

	/**
	 * 
	 * @uml.property name="targetLinks"
	 */
	public void setTargetLinks(Set targets);

	/**
	 * An Activity is contained either in a TwisterProcess of in a StructuredActivity,
	 * therefore this method will return the StructuredActivity containing this activity
	 * only if it's not the root activity. For the root activity it will return null (the
	 * root activity being the activity directly in the process).
	 * @return StructuredActivity the container of this activity, null if this activity is root
	 * 
	 * @uml.property name="container"
	 * @uml.associationEnd 
	 * @uml.property name="container" multiplicity="(0 1)"
	 */
	public StructuredActivity getContainer();

	/**
	 * An Activity is contained either in a TwisterProcess of in a StructuredActivity,
	 * therefore this method will return the TwisterProcess containing this activity
	 * only if it is the root activity. For other activities it will return null (the
	 * root activity being the activity directly in the process).
	 * @return TwisterProcess the process of this activity, null for all activity that are not root
	 * 
	 * @uml.property name="process"
	 * @uml.associationEnd 
	 * @uml.property name="process" multiplicity="(0 1)" inverse="activity:org.uengine.smcp.twister.engine.priv.core.definition.TwisterProcess"
	 */
	public TwisterProcess getProcess();

    
    /**
     * This method browse the activity containment hierarchy to fetch the process
     * this activity is included in.
     * @return TwisterProcess
     */
    public TwisterProcess fetchProcess();
}
