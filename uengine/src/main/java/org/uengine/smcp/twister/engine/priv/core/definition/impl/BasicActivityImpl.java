package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.smartcomps.twister.engine.exception.EngineException;
import org.uengine.smcp.twister.engine.priv.core.definition.BasicActivity;
import org.smartcomps.twister.engine.priv.core.dynamic.ExecutionContext;

import java.util.Map;

/**
 * Persistent implementation of the BasicActivity interface.
 * @see org.smcp.twister.engine.priv.core.definition.BasicActivity
 * @hibernate.class table="ACT_BASIC"
 * @hibernate.subclass discriminator-value="BACT"
 */
public abstract class BasicActivityImpl extends ActivityImpl implements BasicActivity {

    public ExecutionContext execute(String correlationSetName, Map correlation) throws EngineException {
/*		ExecutionContext ec = createContextTree(correlationSetName, correlation);
		ec.execute();
		return ec;*/
		
		return null;
	}

}
