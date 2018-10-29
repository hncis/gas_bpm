package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.CorrelationRef;


public class CorrelationRefImpl implements CorrelationRef {

    private Long id;
    private String set;
    private boolean initiate = false;
    private int pattern;

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
	 * @uml.property name="set"
	 */
	public String getSet() {
		return set;
	}

	/**
	 * 
	 * @uml.property name="set"
	 */
	public void setSet(String set) {
		this.set = set;
	}


    public boolean isInitiate() {
        return initiate;
    }

	/**
	 * 
	 * @uml.property name="initiate"
	 */
	public void setInitiate(boolean initiate) {
		this.initiate = initiate;
	}

	/**
	 * 
	 * @uml.property name="pattern"
	 */
	public int getPattern() {
		return pattern;
	}

	/**
	 * 
	 * @uml.property name="pattern"
	 */
	public void setPattern(int pattern) {
		if (pattern != NONE
			&& pattern != IN
			&& pattern != OUT
			&& pattern != OUT_IN) {
			throw new IllegalArgumentException("Pattern type must be either NONE, IN, OUT, or OUT_IN.");
		}
		this.pattern = pattern;
	}

}
