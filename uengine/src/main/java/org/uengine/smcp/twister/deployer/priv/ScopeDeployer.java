package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Element;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.Scope;

/*
<scope variableAccessSerializable="yes|no" standard-attributes>
standard-elements
<variables>?
... see above under <process> for syntax ...
</variables>
<correlationSets>?
... see above under <process> for syntax ...
</correlationSets>
<faultHandlers>?
... see above under <process> for syntax ...
</faultHandlers>
<compensationHandler>?
... see above under <process> for syntax ...
</compensationHandler>
<eventHandlers>?
...
</eventHandlers>
activity
</scope>
*/
public class ScopeDeployer extends ActivityDeployer {

    protected void processSpecificElements(Element element, Activity activity) throws DeploymentException {
        log.info("Not Yet Implemented");
    }

    protected void processSpecificAttributes(Element element, Activity activity) throws DeploymentException {
        log.info("Not Yet Implemented");
    }

    protected Class getActivityClass() {
        return Scope.class;
    }
}
