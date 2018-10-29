package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Element;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.Empty;

/*
<empty standard-attributes>
standard-elements
</empty>
*/
public class EmptyDeployer extends ActivityDeployer {

    protected void processSpecificElements(Element element, Activity activity) throws DeploymentException {
        // No Specific Elements
    }

    protected void processSpecificAttributes(Element element, Activity activity) throws DeploymentException {
        // No Specific Attributes
    }

    protected Class getActivityClass() {
        return Empty.class;
    }
}
