package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * An alarm event is declared on a Pick activity to define a deadline on * the waiting of the Pick. When a deadline defined by an alarm event is * reached the corresponding activity is started.<br> * An alarm event is defined by a an expression that can either define * a duration or a deadline, a duration is defined from the starting time * of the Pick container. * @see org.smcp.twister.engine.priv.core.definition.Pick
 */

public interface AlarmEvent {

    public static final int DURATION_EXPR = 1;
    public static final int DEADLINE_EXPR = 2;

	/**
	 * 
	 * @uml.property name="timeExpression"
	 */
	public String getTimeExpression();

    public int getType();

    public void setTimeExpression(String timeExpression, int type);
}
