package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.smartcomps.twister.engine.exception.EngineException;
import org.smartcomps.twister.engine.exception.ProcessStructuralException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.AlarmEvent;
import org.uengine.smcp.twister.engine.priv.core.definition.MessageEvent;
import org.uengine.smcp.twister.engine.priv.core.definition.Pick;
import org.smartcomps.twister.engine.priv.core.dynamic.ExecutionContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Persistent implementation of the Pick interface.
 * 
 * @see org.smcp.twister.engine.priv.core.definition.Pick
 */
public class PickImpl extends StructuredActivityImpl implements Pick {

    private boolean createInstance;

	/**
	 * 
	 * @uml.property name="messageEvents"
	 * @uml.associationEnd 
	 * @uml.property name="messageEvents" multiplicity="(0 -1)" elementType="org.uengine.smcp.twister.engine.priv.core.definition.MessageEvent"
	 */
	private List messageEvents = new ArrayList();

	/**
	 * 
	 * @uml.property name="alarmEvents"
	 * @uml.associationEnd 
	 * @uml.property name="alarmEvents" multiplicity="(0 -1)" elementType="org.uengine.smcp.twister.engine.priv.core.definition.AlarmEvent"
	 */
	private List alarmEvents = new ArrayList();


    public boolean isCreateInstance() {
        return createInstance;
    }

	/**
	 * 
	 * @uml.property name="createInstance"
	 */
	public void setCreateInstance(boolean createInstance) {
		this.createInstance = createInstance;
	}

	/**
	 * 
	 * @uml.property name="messageEvents"
	 */
	public List getMessageEvents() {
		return messageEvents;
	}

	/**
	 * 
	 * @uml.property name="messageEvents"
	 */
	public void setMessageEvents(List messageEvents) {
		this.messageEvents = messageEvents;
	}


    public void addMessageEvent(MessageEvent event, Activity activity) {
        int actPos = getActivities().indexOf(activity);

        if (actPos < 0)
            throw new ProcessStructuralException("Activity has not been attributed to " +
                    "this Pick container at creation time.");
        if (actPos != messageEvents.size())
            throw new ProcessStructuralException("MessageEvent must be" +
                    "added in the same order as activities have been created and therefore assigned to this" +
                    "Pick container, please check the ActivityFactory.createActivity method.");

        getMessageEvents().add(event);
    }

    public Map getActivityMessageEvents() {
        // First activities in the activities List are associated with MessageEvent objects.
        Map result = new HashMap(messageEvents.size());

        for (int m = 0; m < getMessageEvents().size(); m++) {
            try {
                Activity activity = (Activity) getActivities().get(m);

                if (messageEvents.get(m) == null) {
                    throw new ProcessStructuralException("An activity in this Pick container is not associated with " +
                            "any MessageEvent, please check that all activities created in this " +
                            "container is properly associated with an event.");
                }
                result.put(activity, messageEvents.get(m));
            } catch (IndexOutOfBoundsException iobe) {
                throw new ProcessStructuralException("There are more Activity objects in this Pick container " +
                        "than MessageEvent, activities and container have been wrongly created or associated", iobe);
            }
        }
        return result;
    }

    public MessageEvent getMessageEvent(Activity activity) {
        int actPos = getActivities().indexOf(activity);

        if (actPos < 0)
            throw new ProcessStructuralException("Activity has not been attributed to " +
                    "this Pick container at creation time.");

        return (MessageEvent) messageEvents.get(actPos);
    }

	/**
	 * 
	 * @uml.property name="alarmEvents"
	 */
	public List getAlarmEvents() {
		return alarmEvents;
	}

	/**
	 * 
	 * @uml.property name="alarmEvents"
	 */
	public void setAlarmEvents(List alarmEvents) {
		this.alarmEvents = alarmEvents;
	}

    public void addAlarmEvent(AlarmEvent event, Activity activity) {
        int actPos = getActivities().indexOf(activity);

        if (actPos < 0)
            throw new ProcessStructuralException("Activity has not been attributed to " +
                    "this Pick container at creation time.");
        if (actPos != (alarmEvents.size() + messageEvents.size()))
            throw new ProcessStructuralException("AlarmEvent must be" +
                    "added in the same order as activities have been created and therefore assigned to this" +
                    "Pick container, please check the ActivityFactory.createActivity method.");

        getAlarmEvents().add(event);
    }

    public Map getActivityAlarmEvents() {
        // Activities after those associated to MessageEvent objects are associated with
        // AlarmEvent objects.
        Map result = new HashMap(alarmEvents.size());

        for (int m = 0; m < alarmEvents.size(); m++) {
            try {
                Activity activity = (Activity) getActivities().get(m + messageEvents.size());

                if (alarmEvents.get(m) == null) {
                    throw new ProcessStructuralException("An activity in this Pick container is not associated " +
                            "with any AlarmEvent, please check that all activities created in this container " +
                            "is properly associated with an event.");
                }
                result.put(activity, alarmEvents.get(m));
            } catch (IndexOutOfBoundsException iobe) {
                throw new ProcessStructuralException("There are more Activity objects in this Pick container " +
                        "than AlarmEvent, activities and container have been wrongly created or associated", iobe);
            }
        }
        return result;
    }

    public AlarmEvent getAlarmEvent(Activity activity) {
        int actPos = getActivities().indexOf(activity);

        if (actPos < 0)
            throw new ProcessStructuralException("Activity has not been attributed to " +
                    "this Pick container at creation time.");
        if (actPos - messageEvents.size() < 0)
            throw new ProcessStructuralException("There are more " +
                    "message events than activities in this Pick container.");

        return (AlarmEvent) alarmEvents.get(actPos - messageEvents.size());
    }

    public ExecutionContext execute(String correlationSetName, Map correlation) throws EngineException {
        return null;
    }

}
