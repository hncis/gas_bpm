package org.uengine.smcp.twister.engine.priv.core.definition;

import org.smartcomps.twister.engine.exception.EngineException;
import org.smartcomps.twister.engine.priv.core.dynamic.ExecutionContext;

import java.util.Map;

/**
 * Supertye for all basic activity meaning an activity that doesn't
 * contain any other activity. It's therefore a terminal activity
 * and not a container that triggers an action from the workflow
 * engine.
 */
public interface BasicActivity extends Activity {

    public ExecutionContext execute(String correlationSetName, Map correlation) throws EngineException;
    
}
