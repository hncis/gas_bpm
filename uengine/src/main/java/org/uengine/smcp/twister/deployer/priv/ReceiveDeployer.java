package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.common.util.StringUtil;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.ActivityFactory;
import org.uengine.smcp.twister.engine.priv.core.definition.CorrelationRef;
import org.uengine.smcp.twister.engine.priv.core.definition.Receive;

import java.util.Iterator;

/*
 * <receive partnerLink="ncname" portType="qname" operation="ncname"
 *     variable="ncname"? createInstance="yes|no"?
 *     standard-attributes>
 *     standard-elements
 *     <correlations>?
 *         <correlation set="ncname" initiate="yes|no"?>+
 *     </correlations>
 * </receive>
 *
 */

public class ReceiveDeployer extends ActivityDeployer {

    protected Class getActivityClass() {
        return Receive.class;
    }

    protected void processSpecificElements(Element element, Activity activity) throws DeploymentException {
        Element correlationsElement = element.element("correlations");
        try {
            if (correlationsElement != null) {
                processCorrelations(correlationsElement, (Receive) activity);
            }
        } catch (DBSessionException e) {
            throw new DeploymentException(e);
        }
    }

    protected void processSpecificAttributes(Element element, Activity activity) throws DeploymentException {
        Receive receive = (Receive) activity;
        Attribute partnerLink = element.attribute("partnerLink");
        if (partnerLink != null) {
            log.debug("partnerLink=" + partnerLink.getValue());
            receive.setPartner(partnerLink.getValue());
        }

        Attribute portType = element.attribute("portType");
        if (portType != null) {
            String portTypeStr = portType.getValue();
            int sepIndex = portTypeStr.indexOf(TwisterDeployerImpl.NS_SEPARATOR);
            if (sepIndex > 0) {
                portTypeStr = portTypeStr.substring(sepIndex + 1, portTypeStr.length());
            }
            log.debug("portType=" + portTypeStr);
            receive.setPortType(portTypeStr);
        }

        Attribute operation = element.attribute("operation");
        if (operation != null) {
            log.debug("operation=" + operation.getValue());
            receive.setOperation(operation.getValue());
        }

        Attribute variable = element.attribute("variable");
        if (variable != null) {
            log.debug("variable=" + variable.getValue());
            receive.setVariable(variable.getValue());
        }

        Attribute createInstance = element.attribute("createInstance");
        if (createInstance != null) {
            boolean bool = StringUtil.booleanValue(createInstance.getValue());
            log.debug("createInstance=" + bool);
            receive.setCreateInstance(bool);
        }

    }

    private void processCorrelations(Element correlationsElement, Receive receive) throws DBSessionException {
//        log.debug(correlationsElement);
        Iterator correlations = correlationsElement.elementIterator("correlation");
        while (correlations.hasNext()) {
            Element correlation = (Element) correlations.next();
            Attribute setAtt = correlation.attribute("set");
            Attribute initiateAtt = correlation.attribute("initiate");
            Attribute patternAtt = correlation.attribute("pattern");
            String set = setAtt != null ? setAtt.getValue() : "";
            boolean initiate = initiateAtt != null ? StringUtil.booleanValue(initiateAtt.getValue()) : false;
            int pattern = patternAtt != null ? getCorrelationPattern(patternAtt.getValue()) : CorrelationRef.NONE;
            CorrelationRef correlationRef = ActivityFactory.addCorrelationRef(receive, set, initiate, pattern);
            log.debug(correlationRef);
        }
    }
}
