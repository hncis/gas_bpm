package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.Property;
import org.uengine.smcp.twister.engine.priv.core.definition.PropertyAlias;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Persistent implementation of the Property interface.
 * @see org.smcp.twister.engine.priv.core.definition.Property
 * @hibernate.class table="PROPERTY"
 */
public class PropertyImpl implements Property {

    private Long id;
    private String name;
    private String type;

	/**
	 * 
	 * @uml.property name="aliases"
	 * @uml.associationEnd 
	 * @uml.property name="aliases" multiplicity="(0 -1)" elementType="org.uengine.smcp.twister.engine.priv.core.definition.PropertyAlias"
	 */
	private Collection aliases = new HashSet();

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
	 * @hibernate.property length="50" not-null="true"
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
	 * @hibernate.property length="200" not-null="true"
	 * 
	 * @uml.property name="type"
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @uml.property name="type"
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @uml.property name="aliases"
	 */
	public Collection getAliases() {
		return aliases;
	}


    public void addAlias(PropertyAlias alias) {
        this.aliases.add(alias);
    }

	/**
	 * 
	 * @uml.property name="aliases"
	 */
	public void setAliases(Collection aliases) {
		if (aliases instanceof Set) {
			this.aliases = aliases;
		} else {
			this.aliases = new HashSet(aliases);
		}
	}

}
