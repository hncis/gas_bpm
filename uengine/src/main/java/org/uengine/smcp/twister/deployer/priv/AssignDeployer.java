package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.Activity;
import org.uengine.smcp.twister.engine.priv.core.definition.ActivityFactory;
import org.uengine.smcp.twister.engine.priv.core.definition.Assign;
import org.uengine.smcp.twister.engine.priv.core.definition.Assignment;

import java.util.Iterator;

/*
<assign standard-attributes>
standard-elements
<copy>+
from-spec
to-spec
</copy>
</assign>

where from-spec :
<from variable="ncname" part="ncname"? query="queryString"?/>
<from partnerLink="ncname" endpointReference="myRole|partnerRole"/>
<from variable="ncname" property="qname"/>
<from expression="general-expr"/>
<from> ... literal value ... </from>

and to-spec :
<to variable="ncname" part="ncname"? query="queryString"?/>
<to partnerLink="ncname"/>
<to variable="ncname" property="qname"/>
*/
public class AssignDeployer extends ActivityDeployer {

    protected Class getActivityClass() {
        return Assign.class;
    }


    protected void processSpecificAttributes(Element element, Activity activity) throws DeploymentException {
        // No Specific Attributes
    }

    protected void processSpecificElements(Element element, Activity activity) throws DeploymentException {
        for (Iterator copy = element.elementIterator(); copy.hasNext();) {
            Element copyElement = (Element) copy.next();
//            log.debug("processing the copyElement : " + copyElement);

            Element fromElement = copyElement.element("from");
            Element toElement = copyElement.element("to");

            if (fromElement == null || toElement == null) {
                throw new DeploymentException("The copy tag in an assigment MUST contains BOTH the FROM and the TO attributes !");
            }

            // processing the FROM type
            Attribute fromFirstAtt = null;
            Attribute fromSecondAtt = null;
            Attribute fromThirdAtt = null;

            int fromType = 0;
            String fromFirstValue = "";
            String fromSecondValue = "";
            String fromThirdValue = "";
            /*
                <from variable="ncname" part="ncname"? query="queryString"?/>
            */
            log.debug("<copy>");
            fromFirstAtt = fromElement.attribute("variable");
            fromSecondAtt = fromElement.attribute("part");
            fromThirdAtt = fromElement.attribute("query");
            if ((fromFirstAtt != null && fromElement.attributeCount() == 1)
                    || (fromSecondAtt != null || fromThirdAtt != null)) {
                fromType = Assignment.VARIABLE_PART;
                fromFirstValue = fromFirstAtt != null ? fromFirstAtt.getValue() : "";
                fromSecondValue = fromSecondAtt != null ? fromSecondAtt.getValue() : "";
                fromThirdValue = fromThirdAtt != null ? fromThirdAtt.getValue() : "";
                log.debug("     <from" +
                        (fromFirstAtt != null ? " variable=" + fromFirstValue : "") +
                        (fromSecondAtt != null ? " part=" + fromSecondValue : "") +
                        (fromThirdAtt != null ? " query=" + fromThirdValue : "") +
                        "/>");
            } else
            /*
                <from expression="general-expr"/>
            */ if ((fromFirstAtt = fromElement.attribute("expression")) != null) {
                fromType = Assignment.EXPRESSION;
                fromFirstValue = fromFirstAtt.getValue();
                log.debug("     <from" +
                        (fromFirstAtt != null ? " expression=" + fromFirstValue : "") +
                        "/>");
            } else
            /* 
                <from partnerLink="ncname" endpointReference="myRole|partnerRole"/>
            */ if ((fromFirstAtt = fromElement.attribute("partnerLink")) != null &&
                    (fromSecondAtt = fromElement.attribute("endpointReference")) != null) {
                fromType = Assignment.PARTNER_REFERENCE;
                fromFirstValue = fromFirstAtt.getValue();
                fromSecondValue = fromSecondAtt.getValue();
                log.debug("     <from" +
                        (fromFirstAtt != null ? " partnerLink=" + fromFirstValue : "") +
                        (fromSecondAtt != null ? " endpointReference=" + fromSecondValue : "") +
                        "/>");
            } else
            /*
                <from variable="ncname" property="qname"/>
            */ if ((fromFirstAtt = fromElement.attribute("variable")) != null &&
                    (fromSecondAtt = fromElement.attribute("property")) != null) {
                fromType = Assignment.VARIABLE_PROPERTY;
                fromFirstValue = fromFirstAtt != null ? fromFirstAtt.getValue() : "";
                fromSecondValue = fromSecondAtt != null ? truncNamespace(fromSecondAtt.getValue()) : "";
                log.debug("     <from" +
                        (fromFirstAtt != null ? " variable=" + fromFirstValue : "") +
                        (fromSecondAtt != null ? " property=" + fromSecondValue : "") +
                        "/>");
            } else
            /*
                <from> ... literal value ... </from>
            */ if (fromElement.attributeCount() == 0) {
                fromType = Assignment.LITERAL;
                fromFirstValue = fromElement.getText();
                log.debug("     <from>" + fromFirstValue + "</from>");
            } else {
                throw new DeploymentException("The FROM attribute is invalid !");
            }

            // processing the TO type
            int toType = 0;
            String toFirstValue = "";
            String toSecondValue = "";
            String toThirdValue = "";

            Attribute toFirstAtt = null;
            Attribute toSecondAtt = null;
            Attribute toThirdAtt = null;

            /*
                <to variable="ncname" part="ncname"? query="queryString"?/>
            */
            toFirstAtt = toElement.attribute("variable");
            toSecondAtt = toElement.attribute("part");
            toThirdAtt = toElement.attribute("query");
            if ((toFirstAtt != null && toElement.attributeCount() == 1)
                    || (toSecondAtt != null || toThirdAtt != null)) {
                toType = Assignment.VARIABLE_PART;
                toFirstValue = toFirstAtt != null ? toFirstAtt.getValue() : "";
                toSecondValue = toSecondAtt != null ? toSecondAtt.getValue() : "";
                toThirdValue = toThirdAtt != null ? toThirdAtt.getValue() : "";
                log.debug("     <to" +
                        (toFirstAtt != null ? " variable=" + toFirstValue : "") +
                        (toSecondAtt != null ? " part=" + toSecondValue : "") +
                        (toThirdAtt != null ? " query=" + toThirdValue : "") +
                        "/>");
            } else
            /*
                <to partnerLink="ncname" endpointReference="myRole|partnerRole"/>
            */ if ((toFirstAtt = toElement.attribute("partnerLink")) != null &&
                    (toSecondAtt = toElement.attribute("endpointReference")) != null) {
                toType = Assignment.PARTNER_REFERENCE;
                toFirstValue = toFirstAtt.getValue();
                toSecondValue = toSecondAtt.getValue();
                log.debug("     <to" +
                        (toFirstAtt != null ? " partnerLink=" + toFirstValue : "") +
                        (toSecondAtt != null ? " endpointReference=" + toSecondValue : "") +
                        "/>");
            } else
            /*
                <to variable="ncname" property="qname"/>
            */ if ((toFirstAtt = toElement.attribute("variable")) != null &&
                    (toSecondAtt = toElement.attribute("property")) != null) {
                toType = Assignment.VARIABLE_PROPERTY;
                toFirstValue = toFirstAtt != null ? toFirstAtt.getValue() : "";
                toSecondValue = toSecondAtt != null ? truncNamespace(toSecondAtt.getValue()): "";
                log.debug("     <to" +
                        (toFirstAtt != null ? " variable=" + toFirstValue : "") +
                        (toSecondAtt != null ? " property=" + toSecondValue : "") +
                        "/>");
            } else {
                throw new DeploymentException("The TO attribute is invalid !");
            }

            Assignment assignment = null;
            try {
                assignment = ActivityFactory.addAssignment((Assign) activity, fromType, toType);
            } catch (DBSessionException e) {
                throw new DeploymentException(e);
            }

            assignment.setFromFirstValue(fromFirstValue);
            assignment.setFromSecondValue(fromSecondValue);
            assignment.setFromQuery(fromThirdValue);
            assignment.setToFirstValue(toFirstValue);
            assignment.setToSecondValue(toSecondValue);
            assignment.setToQuery(toThirdValue);
            log.debug("</copy>");
        }
    }

}
    