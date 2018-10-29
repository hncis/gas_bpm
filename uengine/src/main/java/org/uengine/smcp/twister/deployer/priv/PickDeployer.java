package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.common.util.StringUtil;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.*;
import org.smartcomps.twister.common.util.StringUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
<pick createInstance="yes|no"? standard-attributes>
standard-elements
<onMessage partnerLink="ncname" portType="qname"
operation="ncname" variable="ncname"?>+
<correlations>?
<correlation set="ncname" initiate="yes|no"?>+
</correlations>
activity
</onMessage>
<onAlarm (for="duration-expr" | until="deadline-expr")>*
activity
</onAlarm>
</pick>
*/

public class PickDeployer extends ActivityDeployer {

    protected void processSpecificElements(final Element element, Activity activity) throws DeploymentException {
        Pick pick = (Pick) activity;
        for (Iterator i = element.elementIterator("onMessage"); i.hasNext();) {
            Element onMessageElt = (Element) i.next();
            String partnerLink = onMessageElt.valueOf("@partnerLink");
            String portType = onMessageElt.valueOf("@portType");
            String operation = onMessageElt.valueOf("@operation");
            String variable = onMessageElt.valueOf("@variable");
            Element correlationsElt = onMessageElt.element("correlations");
            Set correlationList = getCorrelationRefList(correlationsElt);
            Element activityElement = getActivityElement(onMessageElt);
            ActivityDeployer ad = ActivityDeployerFactory.getActivityDeployer(activityElement.getName());
            Activity act = ad.deploy(activityElement, pick);
            try {
                ActivityFactory.addMessageEvent(pick, act, partnerLink, portType, operation, variable, correlationList);
            } catch (DBSessionException e) {
                throw new DeploymentException(e);
            }
        }
        for (Iterator i = element.elementIterator("onAlarm"); i.hasNext();) {
            Element onAlarmElt = (Element) i.next();
            String forDurationExpression = onAlarmElt.valueOf("@for");
            String untilDeadlineExpression = onAlarmElt.valueOf("@until");
            String timeExpression = null;
            int expressionType = 0;
            if (forDurationExpression != null) {
                timeExpression = forDurationExpression;
                expressionType = AlarmEvent.DURATION_EXPR;
            } else if (untilDeadlineExpression != null) {
                timeExpression = untilDeadlineExpression;
                expressionType = AlarmEvent.DEADLINE_EXPR;
            }
            Element activityElement = getActivityElement(onAlarmElt);
            ActivityDeployer ad = ActivityDeployerFactory.getActivityDeployer(activityElement.getName());
            Activity act = ad.deploy(activityElement, pick);
            try {
                ActivityFactory.addAlarmEvent(pick, act, timeExpression, expressionType);
            } catch (DBSessionException e) {
                throw new DeploymentException(e);
            }
        }
        processChildren = false;
    }

    private Set getCorrelationRefList(Element correlationsElt) throws DeploymentException {
        Set correlationList = new HashSet();
        if (correlationsElt != null) {
            Iterator correlations = correlationsElt.elementIterator("correlation");
            while (correlations.hasNext()) {
                Element correlation = (Element) correlations.next();
                Attribute setAtt = correlation.attribute("set");
                Attribute initiateAtt = correlation.attribute("initiate");
                String set = setAtt != null ? setAtt.getValue() : "";
                boolean initiate = initiateAtt != null ? StringUtil.booleanValue(initiateAtt.getValue()) : false;
                CorrelationRef correlationRef = null;
                try {
                    correlationRef = ActivityFactory.createCorrelationRef(set, initiate, CorrelationRef.IN);
                } catch (DBSessionException e) {
                    throw new DeploymentException(e);
                }
                correlationList.add(correlationRef);
            }
        }
        return correlationList;
    }

    protected void processSpecificAttributes(Element element, Activity activity) throws DeploymentException {
        Pick pick = (Pick) activity;
        Attribute createInstance = element.attribute("createInstance");
        if (createInstance != null) {
            log.debug("createInstance=" + createInstance.getValue());
            pick.setCreateInstance(StringUtil.booleanValue(createInstance.getValue()));
        }
    }

    protected Class getActivityClass() {
        return Pick.class;
    }
}
