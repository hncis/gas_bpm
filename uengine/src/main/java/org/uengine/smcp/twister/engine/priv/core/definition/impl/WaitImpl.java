package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.Wait;

/**
 * Persistent implementation of the Wait interface.
 * @see org.smcp.twister.engine.priv.core.definition.Wait
 * @hibernate.class table="WAIT"
 * @hibernate.subclass discriminator-value="WAIT"
 */
public class WaitImpl extends BasicActivityImpl implements Wait {

    private String time;
    private boolean duration = false;

	/**
	 * @hibernate.property length="100"
	 * 
	 * @uml.property name="time"
	 */
	public String getTime() {
		return time;
	}

	/**
	 * 
	 * @uml.property name="time"
	 */
	public void setTime(String time) {
		this.time = time;
	}


    /**
     * @hibernate.property
     */
    public boolean isDuration() {
        return duration;
    }

	/**
	 * 
	 * @uml.property name="duration"
	 */
	public void setDuration(boolean period) {
		this.duration = period;
	}

}
