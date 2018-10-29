package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.CorrelationRef;
import org.uengine.smcp.twister.engine.priv.core.definition.Reply;

import java.util.Iterator;

/**
 *
 * <reply partnerLink="ncname" portType="qname" operation="ncname"
 *     variable="ncname"? faultName="qname"?
 *     standard-attributes>
 *     standard-elements
 *     <correlations>?
 *         <correlation set="ncname" initiate="yes|no"?>+
 *     </correlations>
 * </reply>
 */

public class ReplyDeployer extends ActivityDeployer {

    protected Class getActivityClass() {
        return Reply.class;
    }

    protected void processSpecificAttributes(Element element, Activity activity) throws DeploymentException {
        Reply reply = (Reply) activity;
        Attribute partnerLink = element.attribute("partnerLink");
        if (partnerLink != null) {
            log.debug("partnerLink=" + partnerLink);
            reply.setPartner(partnerLink.getValue());
        }

        Attribute portType = element.attribute("portType");
        if (portType != null) {
            String portTypeStr = portType.getValue();
            int sepIndex = portTypeStr.indexOf(TwisterDeployerImpl.NS_SEPARATOR);
            if (sepIndex > 0) {
                portTypeStr = portTypeStr.substring(sepIndex + 1, portTypeStr.length());
            }
            log.debug("portType=" + portTypeStr);
            reply.setPortType(portTypeStr);
        }

        Attribute operation = element.attribute("operation");
        if (operation != null) {
            log.debug("operation=" + operation);
            reply.setOperation(operation.getValue());
        }

        Attribute variable = element.attribute("variable");
        if (variable != null) {
            log.debug("variable=" + variable);
            reply.setVariable(variable.getValue());
        }

        Attribute faultName = element.attribute("faultName");
        if (faultName != null) {
            log.debug("faultName=" + faultName);
            reply.setFaultName(faultName.getValue());
        }

    }

    protected void processSpecificElements(Element element, Activity activity) throws DeploymentException {
        Element correlationsElement = element.element("correlations");
        try {
            processCorrelations(correlationsElement, (Reply) activity);
        } catch (DBSessionException e) {
            throw new DeploymentException(e);
        }
    }

    private void processCorrelations(Element correlationsElement, Reply reply) throws DBSessionException {
        log.debug(correlationsElement);
        Iterator correlations = correlationsElement.elementIterator("correlation");
        while (correlations.hasNext()) {
            Element correlation = (Element) correlations.next();
            Attribute setAtt = correlation.attribute("set");
            String set = setAtt != null ? setAtt.getValue() : "";
            Attribute initiateAtt = correlation.attribute("initiate");
            boolean initiate = initiateAtt != null ? (initiateAtt.getValue().equals("yes") ? true : false) : false;
            Attribute patternAtt = correlation.attribute("pattern");
            int pattern = patternAtt != null ? getCorrelationPattern(patternAtt.getValue()) : CorrelationRef.NONE;
            // todo
//            CorrelationRef correlationRef = ActivityFactory.addCorrelationRef(receive, set, initiate, pattern);
//            log.debug(correlationRef);
        }
    }
}
