package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.smartcomps.twister.engine.exception.ProcessStructuralException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.Switch;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Persistent implementation of the Switch interface.
 * @see org.smcp.twister.engine.priv.core.definition.Switch
 */
public class SwitchImpl extends StructuredActivityImpl implements Switch {

	/**
	 * 
	 * @uml.property name="conditions"
	 * @uml.associationEnd 
	 * @uml.property name="conditions" multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private List conditions = new ArrayList();


	public void addCondition(String condition, Activity activity) {
		int actPos = getActivities().indexOf(activity);

		if (actPos < 0) throw new ProcessStructuralException("Activity has not been attributed to " +
				"this Pick container at creation time.");
		if (actPos != conditions.size()) throw new ProcessStructuralException("Conditions must be " +
				"added in the same order as activities have been added in this container when they " +
				"have been created, please check the ActivityFactory.createActivity method.");

		conditions.add(condition);
	}

	/**
	 * 
	 * @uml.property name="conditions"
	 */
	public List getConditions() {
		return conditions;
	}


	public SortedMap getActivityConditions() {
        TreeMap orderedMap = new TreeMap();
        for (int m = 0; m < getActivities().size(); m++) {
            Activity activity = (Activity) getActivities().get(m);
            if (m < conditions.size()) {
                orderedMap.put(activity, conditions.get(m));
            } else {
                orderedMap.put(activity, "");
            }
        }
		return orderedMap;
	}

	/**
	 * 
	 * @uml.property name="conditions"
	 */
	public void setConditions(List conditions) {
		this.conditions = conditions;
	}

	public String getCondition(int index) {
		return (String) conditions.get(index);
	}

    public void setOtherwise(Activity activity) {
        Activity last = (Activity) getActivities().get(getActivities().size() - 1);
        if (!last.equals(activity)) {
            throw new ProcessStructuralException("The otherwise activity must have been created and " +
                    "included in this container in the last position.");
        }
    }
}
