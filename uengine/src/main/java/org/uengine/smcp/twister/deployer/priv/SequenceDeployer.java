package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Element;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.Sequence;

/*
<sequence standard-attributes>
standard-elements
activity+
</sequence>
*/
public class SequenceDeployer extends ActivityDeployer {
    protected void processSpecificElements(Element element, Activity activity) throws DeploymentException {
        // No Specific Elements
        // activities are procced in the AbstractDeployer
    }

    protected void processSpecificAttributes(Element element, Activity activity) throws DeploymentException {
        // No Specific Attributes
    }

    protected Class getActivityClass() {
        return Sequence.class;
    }

}
