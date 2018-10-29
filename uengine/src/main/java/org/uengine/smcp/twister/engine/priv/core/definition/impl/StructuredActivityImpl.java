package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.StructuredActivity;
import org.smartcomps.twister.engine.priv.core.dynamic.StructuredEC;

import java.util.*;

/**
 * Persistent implementation of the StructuredActivity interface.
 * @see org.smcp.twister.engine.priv.core.definition.StructuredActivity
 */
public abstract class StructuredActivityImpl extends ActivityImpl implements StructuredActivity {

	/**
	 * 
	 * @uml.property name="activitySet"
	 * @uml.associationEnd 
	 * @uml.property name="activitySet" multiplicity="(0 -1)" inverse="activityContainer:org.uengine.smcp.twister.engine.priv.core.definition.impl.ActivityImpl"
	 */
	private Set activitySet = new HashSet();

	/**
	 * 
	 * @uml.property name="executionContexts"
	 * @uml.associationEnd 
	 * @uml.property name="executionContexts" multiplicity="(0 -1)" elementType="org.smartcomps.twister.engine.priv.core.dynamic.StructuredEC"
	 */
	private Collection executionContexts = new HashSet();


    public List getActivities() {
		// Making sure the set is ordered before using it to create the List.
		SortedSet sortedSet = new TreeSet(activitySet);
        return new ArrayList(sortedSet);
    }

    public void setActivities(List activities) {
        this.activitySet = new HashSet(activities);
    }

	/**
	 * 
	 * @uml.property name="activitySet"
	 */
	public Set getActivitySet() {
		return activitySet;
	}

	/**
	 * Do not use this method, its doesn't maintain activity indexes properly, use
	 * the addActivity method instead.
	 * @param activitySet
	 * 
	 * @uml.property name="activitySet"
	 */
	public void setActivitySet(Set activitySet) {
		this.activitySet = activitySet;
	}


    public void addActivity(Activity activity) {
        ActivityImpl impl = (ActivityImpl) activity;
        impl.setIndex(nextActivityIndex());
        getActivitySet().add(activity);
        impl.setContainer(this);
    }

	/**
	 * 
	 * @uml.property name="executionContexts"
	 */
	public Collection getExecutionContexts() {
		return executionContexts;
	}

	/**
	 * 
	 * @uml.property name="executionContexts"
	 */
	public void setExecutionContexts(Collection executionContexts) {
		if (!(executionContexts instanceof Set)) {
			this.executionContexts = new HashSet(executionContexts);
		} else {
			this.executionContexts = executionContexts;
		}
	}

	public void addExecutionContext(StructuredEC context) {
		this.executionContexts.add(context);
	}

    private int nextActivityIndex() {
        int index = 0;
        for (Iterator actIterator = getActivitySet().iterator(); actIterator.hasNext();) {
            ActivityImpl activity = (ActivityImpl) actIterator.next();
            if (activity.getIndex() > index)
                index = activity.getIndex();
        }
        return index + 1;
    }


}
