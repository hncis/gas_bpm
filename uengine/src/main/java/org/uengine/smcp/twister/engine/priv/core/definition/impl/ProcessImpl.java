package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.common.persistence.XMLSessionException;
import org.smartcomps.twister.common.persistence.CreationException;
import org.smartcomps.twister.engine.exception.EngineException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.CorrelationSet;
import org.uengine.smcp.twister.engine.priv.core.definition.Variable;
import org.uengine.smcp.twister.engine.priv.core.definition.Property;
import org.uengine.smcp.twister.engine.priv.core.definition.TwisterProcess;
import org.smartcomps.twister.engine.priv.core.dynamic.ProcessInstance;
import org.smartcomps.twister.engine.priv.core.dynamic.ProcessInstanceFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Persistent implementation of the TwisterProcess interface.
 * @see org.smcp.twister.engine.priv.core.definition.TwisterProcess
 */
public class ProcessImpl implements TwisterProcess {

    private Long id;
    private String name;
    private String namespace;

	/**
	 * 
	 * @uml.property name="activity"
	 * @uml.associationEnd 
	 * @uml.property name="activity" multiplicity="(0 1)"
	 */
	private ActivityImpl activity;

	/**
	 * 
	 * @uml.property name="instances"
	 * @uml.associationEnd 
	 * @uml.property name="instances" multiplicity="(0 -1)" elementType="org.smartcomps.twister.engine.priv.core.dynamic.ProcessInstance"
	 */
	private Collection instances = new HashSet();

	/**
	 * 
	 * @uml.property name="correlationSets"
	 * @uml.associationEnd 
	 * @uml.property name="correlationSets" multiplicity="(0 -1)" elementType="org.uengine.smcp.twister.engine.priv.core.definition.CorrelationSet"
	 */
	private Collection correlationSets = new HashSet();

	/**
	 * 
	 * @uml.property name="properties"
	 * @uml.associationEnd 
	 * @uml.property name="properties" multiplicity="(0 -1)" elementType="org.uengine.smcp.twister.engine.priv.core.definition.Property"
	 */
	private Collection properties = new HashSet();

	/**
	 * 
	 * @uml.property name="variables"
	 * @uml.associationEnd 
	 * @uml.property name="variables" multiplicity="(0 -1)" elementType="org.uengine.smcp.twister.engine.priv.core.definition.Variable"
	 */
	private Collection variables = new HashSet();

/**
 * 
 * @uml.property name="piFactory"
 * @uml.associationEnd 
 * @uml.property name="piFactory" multiplicity="(1 1)"
 */
//    private Collection partnerLinks = new HashSet();

private ProcessInstanceFactory piFactory = new ProcessInstanceFactory();

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
	 * 
	 * @uml.property name="namespace"
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * 
	 * @uml.property name="namespace"
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = (ActivityImpl)activity;
    }

	/**
	 * 
	 * @uml.property name="instances"
	 */
	public Collection getInstances() {
		return this.instances;
	}

	/**
	 * 
	 * @uml.property name="instances"
	 */
	public void setInstances(Collection instances) {
		this.instances = instances;
	}


    public void addInstance(ProcessInstance context) {
        this.instances.add(context);
    }

	/**
	 * 
	 * @uml.property name="correlationSets"
	 */
	public Collection getCorrelationSets() {
		return correlationSets;
	}


    public CorrelationSet getCorrelationSet(String setName) {
        CorrelationSet result = null;
        for (Iterator corIter = correlationSets.iterator(); corIter.hasNext();) {
            CorrelationSet correlationSet = (CorrelationSet) corIter.next();
            if (correlationSet.getName().equals(setName)) {
                result = correlationSet;
            }
        }
        return result;
    }

	/**
	 * 
	 * @uml.property name="correlationSets"
	 */
	public void setCorrelationSets(Collection correlationSets) {
		this.correlationSets = correlationSets;
	}


    public void addCorrelationSet(CorrelationSet set) {
        this.correlationSets.add(set);
    }

	/**
	 * 
	 * @uml.property name="properties"
	 */
	public Collection getProperties() {
		return properties;
	}


    public Property getProperty(String propName) {
        Property result = null;
        for (Iterator propIter = properties.iterator(); propIter.hasNext();) {
            Property property = (Property) propIter.next();
            if (property.getName().equals(propName)) {
                result = property;
            }
        }
        return result;
    }

	/**
	 * 
	 * @uml.property name="properties"
	 */
	public void setProperties(Collection properties) {
		this.properties = properties;
	}


    public void addProperty(Property prop) {
        this.properties.add(prop);
    }

    public ProcessInstance execute(String correlationSetName, Map correlation) throws EngineException {
        return null;
    }

    protected ProcessInstanceFactory getProcessInstanceFactory() {
        if (this.piFactory == null) {
            piFactory = new ProcessInstanceFactory();
        }
        return piFactory;
    }

	/**
	 * 
	 * @uml.property name="variables"
	 */
	public Collection getVariables() {
		return variables;
	}

	/**
	 * 
	 * @uml.property name="variables"
	 */
	public void setVariables(Collection variables) {
		this.variables = variables;
	}

	
	public void addVariable(Variable variable){
		getVariables().add(variable);
	}
	
	public Variable getVariable(String name){
		return null;//getVariables().
	}
}
