package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.Assign;
import org.uengine.smcp.twister.engine.priv.core.definition.Assignment;

import java.util.ArrayList;
import java.util.List;

/**
 * Persistent implementation of the Assign interface.
 * @see org.smcp.twister.engine.priv.core.definition.Assign
 */
public class AssignImpl extends BasicActivityImpl implements Assign {

	/**
	 * 
	 * @uml.property name="assignments"
	 * @uml.associationEnd 
	 * @uml.property name="assignments" multiplicity="(0 -1)" elementType="org.uengine.smcp.twister.engine.priv.core.definition.Assignment"
	 */
	private List assignments = new ArrayList();

	/**
	 * 
	 * @uml.property name="assignments"
	 */
	public List getAssignments() {
		return assignments;
	}

	/**
	 * 
	 * @uml.property name="assignments"
	 */
	public void setAssignments(List assignments) {
		this.assignments = assignments;
	}

    public void addAssignment(Assignment assignment) {
        getAssignments().add(assignment);
    }
}
