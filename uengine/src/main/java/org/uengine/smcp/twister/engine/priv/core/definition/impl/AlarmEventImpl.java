package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.AlarmEvent;

public class AlarmEventImpl implements AlarmEvent {

    private Long id;
    private String timeExpression;
    private int type;

	/**
	 * 
	 * @uml.property name="id"
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @uml.property name="id"
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @uml.property name="timeExpression"
	 */
	public String getTimeExpression() {
		return timeExpression;
	}

	/**
	 * 
	 * @uml.property name="timeExpression"
	 */
	public void setTimeExpression(String timeExpression) {
		this.timeExpression = timeExpression;
	}

	/**
	 * 
	 * @uml.property name="type"
	 */
	public int getType() {
		return type;
	}

	/**
	 * 
	 * @uml.property name="type"
	 */
	public void setType(int type) {
		if (type != DURATION_EXPR && type != DEADLINE_EXPR) {
			throw new IllegalArgumentException("Type value must be either DURATION_EXPR or DEADLINE_EXPR.");
		}
		this.type = type;
	}

    public void setTimeExpression(String timeExpression, int type) {
        setTimeExpression(timeExpression);
        setType(type);
    }
}
