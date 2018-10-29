package org.uengine.smcp.twister.engine.priv.core.definition;

import java.util.List;
import java.util.SortedMap;

/**
 * The Switch activity allows you to select exactly one branch of activity from * a set of choices.<br> * The conditions must be ordered following the same order as the activities * stored in this container; first to keep a coherence between conditions and * their activities; second because the first condition that is evaluated to true * following the ordering will have its activity executed (and not the other * ones).
 */

public interface Switch extends StructuredActivity {

    /**
     * Adds conditions to this Switch with their activities. Conditions must
     * be added in the same order as their activities have been created and
     * assigned to this container.
     * @param condition
     * @param activity
     */
    public void addCondition(String condition, Activity activity);

	/**
	 * Returns a SortedMap containing the activities as keys ordered as they
	 * have been inserted in this container and their conditions as values.
	 * @return SortedMap ordered map of Activity/Condition pairs.
	 * 
	 * @uml.property name="activityConditions"
	 */
	public SortedMap getActivityConditions();


    /**
     * Returns the condition at index.
     * @param index
     * @return int
     */
    public String getCondition(int index);

	/**
	 * 
	 * @uml.property name="conditions"
	 */
	public List getConditions();

    /**
     * Sets an Activity as the otherwise for this Switch. The otherwise activity
     * must have been added last in this container (when created).
     * @param activity
     */
    public void setOtherwise(Activity activity);

}
