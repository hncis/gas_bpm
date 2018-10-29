package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.common.util.StringUtil;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.ActivityFactory;
import org.uengine.smcp.twister.engine.priv.core.definition.CorrelationRef;
import org.uengine.smcp.twister.engine.priv.core.definition.Invoke;
import org.smartcomps.twister.common.util.StringUtil;

import java.util.Iterator;

/*
 * <invoke partnerLink="ncname" portType="qname" operation="ncname"
 * inputVariable="ncname"? outputVariable="ncname"?
 * standard-attributes>
 * standard-elements
 * <correlations>?
 * <correlation set="ncname" initiate="yes|no"?
 * pattern="in|out|out-in"/>+
 * </correlations>
 * <catch faultName="qname" faultVariable="ncname"?>*
 * activity
 * </catch>
 * <catchAll>?
 * activity
 * </catchAll>
 * <compensationHandler>?
 * activity
 * </compensationHandler>
 * </invoke>
 *
 */
public class InvokeDeployer extends ActivityDeployer {

    protected Class getActivityClass() {
        return Invoke.class;
    }

    protected void processSpecificElements(Element element, Activity activity) throws DeploymentException {
        Element correlationsElement = element.element("correlations");
        try {
            if (correlationsElement != null) {
                processCorrelations(correlationsElement, (Invoke) activity);
            }
        } catch (DBSessionException e) {
            throw new DeploymentException(e);
        }

        Iterator catchElements = element.elementIterator("catch");
        processCatchs(catchElements, (Invoke) activity);

        Element catchAll = element.element("catchAll");
        processCatchAll(catchAll, (Invoke) activity);

        Element compensationHandler = element.element("compensationHandler");
        processCompensationHandler(compensationHandler, (Invoke) activity);
    }

    protected void processSpecificAttributes(Element element, Activity activity) throws DeploymentException {
        Invoke invoke = (Invoke) activity;
        Attribute partnerLink = element.attribute("partnerLink");
        if (partnerLink != null) {
            log.debug("partnerLink=" + partnerLink);
            invoke.setPartner(partnerLink.getValue());
        }

        Attribute portType = element.attribute("portType");
        if (portType != null) {
            String portTypeStr = portType.getValue();
            int sepIndex = portTypeStr.indexOf(TwisterDeployerImpl.NS_SEPARATOR);
            if (sepIndex > 0) {
                portTypeStr = portTypeStr.substring(sepIndex + 1, portTypeStr.length());
            }
            log.debug("portType=" + portTypeStr);
            invoke.setPortType(portTypeStr);
        }

        Attribute operation = element.attribute("operation");
        if (operation != null) {
            log.debug("operation=" + operation);
            invoke.setOperation(operation.getValue());
        }

        Attribute inputVariable = element.attribute("inputVariable");
        if (inputVariable != null) {
            log.debug("inputVariable=" + inputVariable);
            invoke.setInputVariable(inputVariable.getValue());
        }

        Attribute outputVariable = element.attribute("outputVariable");
        if (outputVariable != null) {
            log.debug("outputVariable=" + outputVariable);
            invoke.setOutputVariable(outputVariable.getValue());
        }
    }

    private void processCorrelations(Element correlationsElement, Invoke invoke) throws DBSessionException {
        log.debug(correlationsElement);
        Iterator correlations = correlationsElement.elementIterator("correlation");
        while (correlations.hasNext()) {
            Element correlation = (Element) correlations.next();
            Attribute setAtt = correlation.attribute("set");
            Attribute initiateAtt = correlation.attribute("initiate");
            Attribute patternAtt = correlation.attribute("pattern");
            String set = setAtt != null ? setAtt.getValue() : "";
            boolean initiate = initiateAtt != null ? StringUtil.booleanValue(initiateAtt.getValue()) : false;
            int pattern = patternAtt != null ? getCorrelationPattern(patternAtt.getValue()) : CorrelationRef.NONE;
            CorrelationRef correlationRef = ActivityFactory.addCorrelationRef(invoke, set, initiate, pattern);
            log.debug(correlationRef);
        }
    }

    private void processCatchs(Iterator catchElements, Invoke invoke) {
        while (catchElements.hasNext()) {
            Element element = (Element) catchElements.next();
            log.info("not yet managed element : " + element);
        }
    }

    private void processCatchAll(Element catchAllElement, Invoke invoke) {
        log.info("not yet managed element : " + catchAllElement);
    }

    private void processCompensationHandler(Element compensationHandlerElement, Invoke invoke) {
        log.info("not yet managed element : " + compensationHandlerElement);
    }
}
