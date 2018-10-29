package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Element;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.Switch;

import java.util.Iterator;

/*
<switch standard-attributes>
standard-elements
<case condition="bool-expr">+
activity
</case>
<otherwise>?
activity
</otherwise>
</switch>
*/
public class SwitchDeployer extends ActivityDeployer {


    protected void processSpecificElements(Element element, Activity activity) throws DeploymentException {
        Switch aSwitch = (Switch) activity;
        for (Iterator i = element.elementIterator("case"); i.hasNext();) {
            Element caseElement = (Element) i.next();
            String s = caseElement.valueOf("@condition");
            log.debug("<case " +s +">");
            Element activityElement = getActivityElement(caseElement);
            log.debug("<" + activityElement.getName() + ">");
            ActivityDeployer ad = ActivityDeployerFactory.getActivityDeployer(activityElement.getName());
            aSwitch.addCondition(s, ad.deploy(activityElement, aSwitch));
            log.debug("</" + activityElement.getName() + ">");
            log.debug("</case>");
        }
        log.debug("<otherwise>");
        Element otherwiseElement = element.element("otherwise");
        Element activityElement = getActivityElement(otherwiseElement);
        log.debug("<" + activityElement.getName() + ">");
        ActivityDeployer ad = ActivityDeployerFactory.getActivityDeployer(activityElement.getName());
        aSwitch.setOtherwise(ad.deploy(activityElement, aSwitch));
        log.debug("</" + activityElement.getName() + ">");
        log.debug("</otherwise>");
        processChildren = false;
    }

    protected void processSpecificAttributes(Element element, Activity activity) throws DeploymentException {

    }

    protected Class getActivityClass() {
        return Switch.class;
    }

}
