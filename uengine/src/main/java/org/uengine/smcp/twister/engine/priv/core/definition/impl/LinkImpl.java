package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.Link;

/**
 * Persistent implementation of the Link interface.
 * @see org.smcp.twister.engine.priv.core.Link
 * @hibernate.class table="LINK"
 */
public class LinkImpl implements Link {

    private Long id;

	/**
	 * 
	 * @uml.property name="source"
	 * @uml.associationEnd 
	 * @uml.property name="source" multiplicity="(0 1)"
	 */
	private ActivityImpl source;

	/**
	 * 
	 * @uml.property name="target"
	 * @uml.associationEnd 
	 * @uml.property name="target" multiplicity="(0 1)"
	 */
	private ActivityImpl target;

    private String transitionCondition;

	/**
	 * @hibernate.id generator-class="native" type="long"
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
     * @hibernate.many-to-one class="org.smcp.twister.engine.priv.core.definition.impl.ActivityImpl" column="SRC_ACT_ID"
     */
    public Activity getSource() {
        return source;
    }

    public void setSource(Activity source) {
        this.source = (ActivityImpl) source;
    }

    /**
     * @hibernate.many-to-one class="org.smcp.twister.engine.priv.core.definition.impl.ActivityImpl" column="TGT_ACT_ID"
     */
    public Activity getTarget() {
        return target;
    }

    public void setTarget(Activity target) {
        this.target = (ActivityImpl) target;
    }

	/**
	 * @hibernate.property column="TRANSCOND" length="300"
	 * 
	 * @uml.property name="transitionCondition"
	 */
	public String getTransitionCondition() {
		return transitionCondition;
	}

	/**
	 * 
	 * @uml.property name="transitionCondition"
	 */
	public void setTransitionCondition(String transitionCondition) {
		this.transitionCondition = transitionCondition;
	}

    
}
