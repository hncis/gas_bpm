package org.uengine.smcp.twister.engine.priv.core.definition;

import java.util.List;

/**
 * An assign is a basic activity used to copy data from one * variable to another or to construct or insert new data * with an expression. An assign is just a container for * Assignment objects that describe the real assignment.
 */

public interface Assign extends BasicActivity {

	/**
	 * 
	 * @uml.property name="assignments"
	 */
	public List getAssignments();

	/**
	 * 
	 * @uml.property name="assignments"
	 */
	public void setAssignments(List assignments);

    public void addAssignment(Assignment assignment);
}
