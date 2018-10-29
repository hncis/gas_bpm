package org.uengine.smcp.twister.engine.priv.core.definition;

import org.smartcomps.twister.engine.exception.EngineException;
import org.smartcomps.twister.engine.priv.core.dynamic.ExecutionContext;

import java.util.List;
import java.util.Map;

/**
 * A pick is a structured activity that allows you to block and wait for a suitable * message to arrive or for a time-out alarm to go off. When one of these triggers * occurs, the associated activity is performed and the pick completes.<br> * Each Pick activty must include at least one MessageEvent. * @see org.smcp.twister.engine.priv.core.definition.MessageEvent, AlarmEvent
 */

public interface Pick extends StructuredActivity {

    public boolean isCreateInstance();
    public void setCreateInstance(boolean createInstance);

	/**
	 * Adds a message event to this Pick activity that triggers the provided
	 * activity when fired. Message events must be added in the same order as
	 * activities have been created and registered in this container and before
	 * any alarm event.
	 * @param event the message event to wait for to fire the activity
	 * @param activity to start when the message event occurs
	 */
    public void addMessageEvent(MessageEvent event, Activity activity);

	/**
	 * Builds a Map containing activities as keys and corresponding message events
	 * as values.
	 * @return Map of activity / message event pairs
	 * 
	 * @uml.property name="activityMessageEvents"
	 */
	public Map getActivityMessageEvents();


	/**
	 * Returns the message event associated with the provided activity.
	 * @param activity associated with the wanted message event
	 * @return MessageEvent
	 */
	public MessageEvent getMessageEvent(Activity activity);

	/**
	 * Returns the list of MessageEvent in the Pick structured activity. The
	 * MessageEvent objects in this list have the same positions as their
	 * corresponding activity in the Pick container.
	 * @return
	 * 
	 * @uml.property name="messageEvents"
	 */
	public List getMessageEvents();


	/**
	 * Adds an alarm event to this Pick activity that triggers the provided
	 * activity when fired. Alarm events must be added in the same order as
	 * activities have been created and registered in this container and after
	 * any message event.
	 * @param event the alarm event to wait for to fire the activity
	 * @param activity to start when the message event occurs
	 */
    public void addAlarmEvent(AlarmEvent event, Activity activity);

	/**
	 * Builds a Map containing activities as keys and corresponding alarm events
	 * as values.
	 * @return Map of activity / alarm event pairs
	 * 
	 * @uml.property name="activityAlarmEvents"
	 */
	public Map getActivityAlarmEvents();

	/**
	 * Returns the alarm event associated with the provided activity.
	 * @param activity associated with the wanted alarm event
	 * @return AlarmEvent
	 */
	public AlarmEvent getAlarmEvent(Activity activity);

    /**
     * Pick is one of the rare structured activity that can be directly
     * executed. Actually it's only one of its activity that will be
     * executed but this execution is done in the scope of the Pick.
     * @param correlationSetName
     * @param correlation
     * @return
     * @throws EngineException
     */
    public ExecutionContext execute(String correlationSetName, Map correlation) throws EngineException;

}
