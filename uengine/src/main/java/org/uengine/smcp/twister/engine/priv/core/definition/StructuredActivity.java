package org.uengine.smcp.twister.engine.priv.core.definition;

import org.smartcomps.twister.engine.priv.core.dynamic.StructuredEC;

import java.util.Collection;
import java.util.List;


public interface StructuredActivity extends Activity {

	/**
	 * Returns activities in this container. Activities are attributed to a container
	 * at creation time by passing the container in the ActivityFactory.createActivity
	 * method.
	 * @return List and ordered list of activities.
	 * 
	 * @uml.property name="activities"
	 */
	public List getActivities();

	public void addActivity(Activity activity);

	/**
	 * 
	 * @uml.property name="executionContexts"
	 */
	public Collection getExecutionContexts();

	public void addExecutionContext(StructuredEC context);
}
