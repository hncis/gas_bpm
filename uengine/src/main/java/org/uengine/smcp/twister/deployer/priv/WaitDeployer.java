package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Element;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.Wait;

/**
 * <wait (for="duration-expr" | until="deadline-expr") standard-attributes>
 * standard-elements
 * </wait>
 */
public class WaitDeployer extends ActivityDeployer {

    protected void processSpecificElements(Element element, Activity activity) throws DeploymentException {
        // No Specific Elements
    }

    protected void processSpecificAttributes(Element element, Activity activity) throws DeploymentException {
        String forDurationExpression = element.valueOf("@for");
        String untilDeadlineExpression = element.valueOf("@until");
        Wait wait = (Wait) activity;
        if (forDurationExpression != null) {
            wait.setDuration(true);
            wait.setTime(forDurationExpression);
        } else if (untilDeadlineExpression != null) {
            wait.setDuration(false);
            wait.setTime(untilDeadlineExpression);
        }
    }

    protected Class getActivityClass() {
        return Wait.class;
    }
}
