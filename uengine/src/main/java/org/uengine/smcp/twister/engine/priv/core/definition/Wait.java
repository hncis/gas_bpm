package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * Allows you to wait for a certain period or until a given time * has passed.
 */

public interface Wait extends BasicActivity {

	/**
	 * Gets the time expression of this Wait
	 * @return
	 * 
	 * @uml.property name="time"
	 */
	public String getTime();

	/**
	 * 
	 * @uml.property name="time"
	 */
	public void setTime(String time);

    public boolean isDuration();
    public void setDuration(boolean duration);

}
