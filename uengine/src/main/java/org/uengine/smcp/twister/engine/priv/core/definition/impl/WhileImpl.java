package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.While;

/**
 * A persistent implementation of the While interface.
 * @see org.smcp.twister.engine.priv.core.definition.While
 */
public class WhileImpl extends StructuredActivityImpl implements While {

    private String condition;

	/**
	 * 
	 * @uml.property name="condition"
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * 
	 * @uml.property name="condition"
	 */
	public void setCondition(String booleanExpr) {
		this.condition = booleanExpr;
	}

    public void addActivity(Activity activity) {
        if (getActivitySet().size() > 0) {
            throw new UnsupportedOperationException("This while already contains an activity, no additional " +
                    "activities can be added, while id : " + getId());
        }
        super.addActivity(activity);
    }
}
