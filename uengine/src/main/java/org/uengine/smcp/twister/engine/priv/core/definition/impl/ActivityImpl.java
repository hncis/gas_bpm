package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.engine.exception.EngineException;
import org.smartcomps.twister.engine.exception.ProcessStructuralException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.StructuredActivity;
import org.uengine.smcp.twister.engine.priv.core.definition.TwisterProcess;
import org.smartcomps.twister.engine.priv.core.dynamic.ExecutionContext;
import org.smartcomps.twister.engine.priv.core.dynamic.ExecutionContextFactory;
import org.smartcomps.twister.engine.priv.core.dynamic.ProcessInstance;
import org.smartcomps.twister.engine.priv.core.dynamic.StructuredEC;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Persistent implementation of the Activity interface.
 * @see org.smcp.twister.engine.priv.core.definition.Activity
 * @hibernate.class table="ACTIVITY" discriminator-value="ACT"
 * @hibernate.discriminator column="DISCRIM"
 */
public abstract class ActivityImpl implements Activity, Comparable {

    private Long id;
    private String name;
    private String joinCondition;
    private Set sourceLinks = new HashSet();
    private Set targetLinks = new HashSet();

	/**
	 * 
	 * @uml.property name="activityContainer"
	 * @uml.associationEnd 
	 * @uml.property name="activityContainer" multiplicity="(0 1)" inverse="activitySet:org.uengine.smcp.twister.engine.priv.core.definition.impl.StructuredActivityImpl"
	 */
	private StructuredActivityImpl activityContainer;

	/**
	 * 
	 * @uml.property name="processSet"
	 * @uml.associationEnd 
	 * @uml.property name="processSet" multiplicity="(0 -1)" elementType="org.uengine.smcp.twister.engine.priv.core.definition.TwisterProcess"
	 */
	private Set processSet = new HashSet();


//  This is a purely technical field to indicate the position of the activity within
//  its container. It should be managed by Hibernate in the future but right now
//  there's no other choice than this.
    private int index;

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
	 * @hibernate.property length="50"
	 * 
	 * @uml.property name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @uml.property name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property length="300"
	 * 
	 * @uml.property name="joinCondition"
	 */
	public String getJoinCondition() {
		return joinCondition;
	}

	/**
	 * 
	 * @uml.property name="joinCondition"
	 */
	public void setJoinCondition(String joinCondition) {
		this.joinCondition = joinCondition;
	}

	/**
	 * @hibernate.set inverse="true" role="sourceLinks"
	 * @hibernate.collection-key column="TGT_ACT_ID"
	 * @hibernate.collection-one-to-many class="org.smcp.twister.engine.priv.core.definition.impl.LinkImpl"
	 * 
	 * @uml.property name="sourceLinks"
	 */
	public Set getSourceLinks() {
		return sourceLinks;
	}

	/**
	 * 
	 * @uml.property name="sourceLinks"
	 */
	public void setSourceLinks(Set sourceLinks) {
		this.sourceLinks = sourceLinks;
	}

	/**
	 * @hibernate.set inverse="true" role="targetLinks"
	 * @hibernate.collection-key column="SRC_ACT_ID"
	 * @hibernate.collection-one-to-many class="org.smcp.twister.engine.priv.core.definition.impl.LinkImpl"
	 * 
	 * @uml.property name="targetLinks"
	 */
	public Set getTargetLinks() {
		return targetLinks;
	}

	/**
	 * 
	 * @uml.property name="targetLinks"
	 */
	public void setTargetLinks(Set targetLinks) {
		this.targetLinks = targetLinks;
	}


    public StructuredActivity getContainer() {
        return activityContainer;
    }

    public void setContainer(StructuredActivity stucturedActivity) {
        this.activityContainer = (StructuredActivityImpl) stucturedActivity;
    }

    public TwisterProcess getProcess() {
        if (processSet == null || processSet.size() != 1) return null;
        return (TwisterProcess) processSet.iterator().next();
    }

    public void setProcess(TwisterProcess process) {
        HashSet processSet = new HashSet(1);
        processSet.add(process);
        this.processSet = processSet;
    }

    public TwisterProcess fetchProcess() {
        if (getContainer() == null) {
            return getProcess();
        } else {
            StructuredActivity container = this.getContainer();
            while (container.getContainer() != null) container = container.getContainer();
            return container.getProcess();
        }
    }

	/**
	 * TECHNICAL, DO NOT USE
	 * @hibernate.many-to-one class="org.smcp.twister.engine.priv.core.definition.impl.ActivityImpl" column="SRC_ACT_ID"
	 * 
	 * @uml.property name="activityContainer"
	 */
	public StructuredActivityImpl getActivityContainer() {
		return activityContainer;
	}

	/**
	 * TECHNICAL, DO NOT USE
	 * 
	 * @uml.property name="activityContainer"
	 */
	public void setActivityContainer(StructuredActivityImpl activityContainer) {
		this.activityContainer = activityContainer;
	}

	/**
	 * TECHNICAL, DO NOT USE
	 * Only one activity in a processSet really has a processSet, therefore
	 * this method will often return null ; use the getContainer method
	 * instead, that always returns the right container (the real activity
	 * container or the processSet if it's a root activity).
	 * @hibernate.set inverse="true" role="processSet"
	 * @hibernate.collection-key column="ACTIVITY_ID"
	 * @hibernate.collection-one-to-many class="org.smcp.twister.engine.priv.core.definition.impl.ProcessImpl"
	 * 
	 * @uml.property name="processSet"
	 */
	public Set getProcessSet() {
		return processSet;
	}

	/**
	 * TECHNICAL, DO NOT USE
	 * 
	 * @uml.property name="processSet"
	 */
	public void setProcessSet(Set processSet) {
		this.processSet = processSet;
	}

	/**
	 * TECHNICAL, DO NOT USE
	 * 
	 * @uml.property name="index"
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * TECHNICAL, DO NOT USE
	 * 
	 * @uml.property name="index"
	 */
	public void setIndex(int index) {
		this.index = index;
	}

    protected ExecutionContext createContextTree(String correlationSetName, Map correlation) throws EngineException {
/*        ExecutionContext context = null;
        if (getContainer() != null) {
            StructuredEC parentContext = (StructuredEC)
                    ((StructuredActivityImpl)getContainer()).createContextTree(correlationSetName, correlation);
            try {
                context = ExecutionContextFactory.createExecutionContext(this, parentContext);
            } catch (DBSessionException e) {
                throw new EngineException(e);
            }
        } else if (getProcess() != null) {
            ProcessInstance parentContext = ((ProcessImpl)getProcess()).execute(correlationSetName, correlation);
            try {
                context = ExecutionContextFactory.createExecutionContext(this, parentContext);
            } catch (DBSessionException e) {
                throw new EngineException(e);
            }
        } else {
            throw new ProcessStructuralException("The activity " + getId() +
                    " has no process nor container, this shouldn't be possible");
        }
*/
        return null;
    }

	public int compareTo(Object o) {
		if (o instanceof ActivityImpl) {
			if (((ActivityImpl)o).getIndex() > this.getIndex()) return -1;
			else if (((ActivityImpl)o).getIndex() == this.getIndex()) return 0;
			else return 1;
		}
		return 0;
	}
}
