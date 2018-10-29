package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.Assignment;

/**
 * Persistent implementation of the Assignment interface
 * @see org.smcp.twister.engine.priv.core.definition.Assignment
 */
public class AssignmentImpl implements Assignment {

    private Long id;
    private int fromType;
    private int toType;
    private String fromFirstValue;
    private String fromSecondValue;
    private String toFirstValue;
    private String toSecondValue;
    private String fromQuery;
    private String toQuery;

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
	 * @uml.property name="fromType"
	 */
	public int getFromType() {
		return fromType;
	}

	/**
	 * 
	 * @uml.property name="fromType"
	 */
	public void setFromType(int fromType) {
		if (fromType != VARIABLE_PART
			&& fromType != VARIABLE_PROPERTY
			&& fromType != PARTNER_REFERENCE
			&& fromType != EXPRESSION
			&& fromType != LITERAL) {
			throw new IllegalArgumentException(
				"FromType value must be either VARIABLE_PART, "
					+ "VARIABLE_PROPERTY, PARTNER_REFERENCE, EXPRESSION or LITERAL");
		}
		this.fromType = fromType;
	}

	/**
	 * 
	 * @uml.property name="toType"
	 */
	public int getToType() {
		return toType;
	}

	/**
	 * 
	 * @uml.property name="toType"
	 */
	public void setToType(int toType) {
		if (toType != VARIABLE_PART
			&& toType != VARIABLE_PROPERTY
			&& toType != PARTNER_REFERENCE) {
			throw new IllegalArgumentException(
				"ToType value must be either VARIABLE_PART, "
					+ "VARIABLE_PROPERTY or PARTNER_REFERENCE");
		}
		this.toType = toType;
	}

	/**
	 * 
	 * @uml.property name="fromFirstValue"
	 */
	public String getFromFirstValue() {
		return fromFirstValue;
	}

	/**
	 * 
	 * @uml.property name="fromFirstValue"
	 */
	public void setFromFirstValue(String fromFirstValue) {
		this.fromFirstValue = fromFirstValue;
	}

	/**
	 * 
	 * @uml.property name="fromSecondValue"
	 */
	public String getFromSecondValue() {
		return fromSecondValue;
	}

	/**
	 * 
	 * @uml.property name="fromSecondValue"
	 */
	public void setFromSecondValue(String fromSecondValue) {
		this.fromSecondValue = fromSecondValue;
	}

	/**
	 * 
	 * @uml.property name="toFirstValue"
	 */
	public String getToFirstValue() {
		return toFirstValue;
	}

	/**
	 * 
	 * @uml.property name="toFirstValue"
	 */
	public void setToFirstValue(String toFirstValue) {
		this.toFirstValue = toFirstValue;
	}

	/**
	 * 
	 * @uml.property name="toSecondValue"
	 */
	public String getToSecondValue() {
		return toSecondValue;
	}

	/**
	 * 
	 * @uml.property name="toSecondValue"
	 */
	public void setToSecondValue(String toSecondValue) {
		this.toSecondValue = toSecondValue;
	}

	/**
	 * 
	 * @uml.property name="fromQuery"
	 */
	public String getFromQuery() {
		return fromQuery;
	}

	/**
	 * 
	 * @uml.property name="fromQuery"
	 */
	public void setFromQuery(String fromQuery) {
		this.fromQuery = fromQuery;
	}

	/**
	 * 
	 * @uml.property name="toQuery"
	 */
	public String getToQuery() {
		return toQuery;
	}

	/**
	 * 
	 * @uml.property name="toQuery"
	 */
	public void setToQuery(String toQuery) {
		this.toQuery = toQuery;
	}

}
