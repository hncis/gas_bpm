package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * A link is an element used by a Flow activity to link activities. It * specifies a source (previous activity) and a destination (next * activity).
 */

public interface Link {

	/**
	 * 
	 * @uml.property name="source"
	 * @uml.associationEnd 
	 * @uml.property name="source" multiplicity="(0 1)"
	 */
	public Activity getSource();

	/**
	 * 
	 * @uml.property name="source"
	 */
	public void setSource(Activity source);

	/**
	 * 
	 * @uml.property name="target"
	 * @uml.associationEnd 
	 * @uml.property name="target" multiplicity="(0 1)"
	 */
	public Activity getTarget();

	/**
	 * 
	 * @uml.property name="target"
	 */
	public void setTarget(Activity target);

	/**
	 * 
	 * @uml.property name="transitionCondition"
	 */
	public String getTransitionCondition();

	/**
	 * 
	 * @uml.property name="transitionCondition"
	 */
	public void setTransitionCondition(String condition);

}
