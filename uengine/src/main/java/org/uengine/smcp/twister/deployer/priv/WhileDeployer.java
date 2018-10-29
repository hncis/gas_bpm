package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.While;

/**
 * <while condition="bool-expr" standard-attributes>
 * standard-elements
 * activity
 * </while>
 */
public class WhileDeployer extends ActivityDeployer {

    protected Class getActivityClass() {
        return While.class;
    }

    protected void processSpecificAttributes(Element element, Activity activity) {
        While awhile = (While) activity;
        Attribute condition = element.attribute("condition");
        if (condition != null) {
            log.debug("condition=" + condition.getValue());
            awhile.setCondition(condition.getValue());
        }
    }

    protected void processSpecificElements(Element element, Activity activity) throws DeploymentException {
//        for (Iterator i = element.elementIterator(); i.hasNext();) {
//            Element elem = (Element) i.next();
//            ActivityDeployer ad = ActivityDeployerFactory.getActivityDeployer(elem.getName());
//            ad.deploy(elem, (StructuredActivity) activity);
//        }
    }

}
