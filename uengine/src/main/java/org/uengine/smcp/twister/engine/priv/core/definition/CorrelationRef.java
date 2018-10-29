package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * Reference to a correlation set, used by message based activities * to reference their correlation. * @see org.smcp.twister.engine.priv.core.definition.CorrelationRef
 */

public interface CorrelationRef {

    public final static int NONE = 0;
    public final static int IN = 1;
    public final static int OUT = 2;
    public final static int OUT_IN = 3;

	/**
	 * 
	 * @uml.property name="set"
	 */
	public String getSet();

	/**
	 * 
	 * @uml.property name="set"
	 */
	public void setSet(String set);

    public boolean isInitiate();
    public void setInitiate(boolean initiate);

    public int getPattern();
    public void setPattern(int pattern);

}
