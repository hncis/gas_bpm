package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * The actual assignment, as part of an Assign activity which is just an * assignment group. * @see Assign
 */

public interface Assignment {

    public static final int VARIABLE_PART = 1;
    public static final int PARTNER_REFERENCE = 2;
    public static final int VARIABLE_PROPERTY = 3;
    public static final int EXPRESSION = 4;
    public static final int LITERAL = 5;

    public int getFromType();
    public void setFromType(int type);

    public int getToType();
    public void setToType(int type);

	/**
	 * 
	 * @uml.property name="fromFirstValue"
	 */
	public String getFromFirstValue();

	/**
	 * 
	 * @uml.property name="fromFirstValue"
	 */
	public void setFromFirstValue(String value);

	/**
	 * 
	 * @uml.property name="fromSecondValue"
	 */
	public String getFromSecondValue();

	/**
	 * 
	 * @uml.property name="fromSecondValue"
	 */
	public void setFromSecondValue(String value);

	/**
	 * 
	 * @uml.property name="toFirstValue"
	 */
	public String getToFirstValue();

	/**
	 * 
	 * @uml.property name="toFirstValue"
	 */
	public void setToFirstValue(String value);

	/**
	 * 
	 * @uml.property name="toSecondValue"
	 */
	public String getToSecondValue();

	/**
	 * 
	 * @uml.property name="toSecondValue"
	 */
	public void setToSecondValue(String value);

	/**
	 * 
	 * @uml.property name="fromQuery"
	 */
	public String getFromQuery();

	/**
	 * 
	 * @uml.property name="fromQuery"
	 */
	public void setFromQuery(String fromQuery);

	/**
	 * 
	 * @uml.property name="toQuery"
	 */
	public String getToQuery();

	/**
	 * 
	 * @uml.property name="toQuery"
	 */
	public void setToQuery(String toQuery);

}
