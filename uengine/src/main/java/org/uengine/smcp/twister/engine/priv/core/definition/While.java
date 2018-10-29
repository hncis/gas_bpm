package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * The While activity allows you to indicate that an activity is to * be repeated until a certain success criteria has been met.
 */

public interface While extends StructuredActivity {

	/**
	 * 
	 * @uml.property name="condition"
	 */
	public String getCondition();

	/**
	 * 
	 * @uml.property name="condition"
	 */
	public void setCondition(String booleanExpr);

}
