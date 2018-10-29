package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.CorrelationSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

public class CorrelationSetImpl implements CorrelationSet {

    private long id;
    private String name;
    private String propertiesString;

	/**
	 * 
	 * @uml.property name="id"
	 */
	public long getId() {
		return id;
	}

	/**
	 * 
	 * @uml.property name="id"
	 */
	public void setId(long id) {
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
	 * @uml.property name="propertiesString"
	 */
	public String getPropertiesString() {
		return propertiesString;
	}

	/**
	 * 
	 * @uml.property name="propertiesString"
	 */
	public void setPropertiesString(String propertiesString) {
		this.propertiesString = propertiesString;
	}

    public Collection getProperties() {
        ArrayList result = new ArrayList();
        for (StringTokenizer propTokenizer = new StringTokenizer(propertiesString); propTokenizer.hasMoreTokens();) {
            String prop = propTokenizer.nextToken();
            result.add(prop);
        }

        return result;
    }
}
