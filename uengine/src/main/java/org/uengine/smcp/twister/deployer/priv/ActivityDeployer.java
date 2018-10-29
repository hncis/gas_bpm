package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.common.util.logger.Logger;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.*;

import java.util.Iterator;
import java.util.List;

/**
 * parent class of specific activity deployer
 */

public abstract class ActivityDeployer {

	/**
	 * 
	 * @uml.property name="log"
	 * @uml.associationEnd 
	 * @uml.property name="log" multiplicity="(1 1)"
	 */
	protected Logger log = Logger.getLogger(getClass());


    // this flag indicates if the deploy loop should deploy the
    // children elements or not
    protected boolean processChildren = true;

    protected ActivityDeployer() {
    }

    /**
     * The token "activity" can be any of the following:
     * ?<receive>
     * ?<reply>
     * ?<invoke>
     * ?<assign>
     * ?<throw>
     * ?<terminate>
     * ?<wait>
     * ?<empty>
     * ?<sequence>
     * ?<switch>
     * ?<while>
     * ?<pick>
     * ?<flow>
     * ?<scope>
     * ?<compensate>
     * 
     * @param pe 
     * @return 
     */
    public static Element getActivityElement(final Element pe) {
        Element res = null;
        Element tmp = null;
        res = ((res != null) || (tmp = pe.element("assign")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("compensate")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("empty")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("flow")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("invoke")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("pick")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("receive")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("reply")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("sequence")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("scope")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("switch")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("terminate")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("throw")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("wait")) == null) ? res : tmp;
        res = ((res != null) || (tmp = pe.element("while")) == null) ? res : tmp;
        return res;
    }


    /**
     * @param element        
     * @param twisterProcess 
     * @return 
     * @throws DeploymentException 
     */
    final public Activity deploy(Element element, TwisterProcess twisterProcess) throws DeploymentException {
        Activity activity = createActivity(element, twisterProcess);
        processChildren(activity, element);
        return activity;
    }

    /**
     * @param element        
     * @param parentActivity 
     * @return 
     * @throws DeploymentException 
     */
    final public Activity deploy(Element element, StructuredActivity parentActivity) throws DeploymentException {
        Activity activity = createActivity(element, parentActivity);
        processChildren(activity, element);
        return activity;
    }

    private void processChildren(Activity activity, Element element) throws DeploymentException {
        if ((activity instanceof StructuredActivity) && (processChildren == true)) {
            List elements = element.elements();
            int eltsSize = elements.size();
            for (int i = 0; i < eltsSize; i++) {
                Element e = (Element) elements.get(i);
                log.info("<" + e.getName() + ">");
                ActivityDeployer ad = null;
                try {
                    ad = ActivityDeployerFactory.getActivityDeployer(e.getName());
                } catch (DeploymentException ex) {
                    throw new DeploymentException(ex);
                }
                Activity returnedActivity = ad.deploy(e, (StructuredActivity) activity);
                // Setting a flag on a receive if it's immediately followed by a Reply
                if ("receive".equals(e.getName())) {
                    if (i < (eltsSize - 1)) {
                        Element next = (Element) elements.get(i + 1);
                        if ("reply".equals(next.getName())) {
                            if ("sequence".equals(element.getName())) {
                                ((Receive) returnedActivity).setReply(true);
                            }
                        }
                    }
                }

                log.info("</" + e.getName() + ">");
            }
        }
    }

    /**
     * Standard Attributes for Each Activity
     * <p/>
     * name="ncname"?
     * joinCondition="bool-expr"?
     * suppressJoinFailure="yes|no"?>
     * 
     * @param element  
     * @param activity 
     */
    final protected void processStandardAttributes(Element element, Activity activity) {
        Attribute name = element.attribute("name");
        if (name != null) {
            log.debug("name=" + name);
            activity.setName(name.getValue());
        }

        Attribute joinCondition = element.attribute("joinCondition");
        if (joinCondition != null) {
            log.debug("joinCondition=" + joinCondition);
            activity.setJoinCondition(joinCondition.getValue());
        }

    }

    /**
     * Standard Elements for Each Activity
     * <p/>
     * <source linkName="ncname" transitionCondition="bool-expr"?/>*
     * <target linkName="ncname"/>*
     * 
     * @param element  
     * @param activity 
     */
    final protected void processStandardElements(Element element, Activity activity) {
        Iterator sourceElements = element.elementIterator("source");
        processSource(sourceElements, activity);

        Iterator targetElements = element.elementIterator("target");
        processTarget(targetElements, activity);
    }


    private void processSource(Iterator sourceElements, Activity activity) {
        while (sourceElements.hasNext()) {
            Element element = (Element) sourceElements.next();
            log.info("not yet managed element : " + element);
        }
    }

    private void processTarget(Iterator targetElements, Activity activity) {
        while (targetElements.hasNext()) {
            Element element = (Element) targetElements.next();
            log.info("not yet managed element : " + element);
        }
    }

    protected int getCorrelationPattern(String patternAsString) {
        int res = CorrelationRef.NONE;
        if ("in".equalsIgnoreCase(patternAsString)) {
            res = CorrelationRef.IN;
        } else if ("out".equalsIgnoreCase(patternAsString)) {
            res = CorrelationRef.OUT;
        } else if ("out-in".equalsIgnoreCase(patternAsString)) {
            res = CorrelationRef.OUT_IN;
        }
        return res;
    }

    /**
     * @param element        
     * @param twisterProcess 
     * @return 
     * @throws DeploymentException 
     */
    final protected Activity createActivity(Element element, TwisterProcess twisterProcess) throws DeploymentException {
        Activity activity = null;
//        log.debug("Creating " + StringUtil.getClassName(getActivityClass().getName()) + " ...");
        try {
            activity = ActivityFactory.createActivity(getActivityClass(), twisterProcess);
        } catch (DBSessionException e) {
            throw new DeploymentException(e);
        }
        processElements(element, activity);
        return activity;
    }

    /**
     * @param element        
     * @param parentActivity 
     * @return 
     * @throws DeploymentException 
     */
    final protected Activity createActivity(Element element, StructuredActivity parentActivity) throws DeploymentException {
        Activity activity = null;
//        log.debug("Creating " + StringUtil.getClassName(getActivityClass().getName()) + " ...");
        try {
            activity = ActivityFactory.createActivity(getActivityClass(), parentActivity);
        } catch (DBSessionException e) {
            throw new DeploymentException(e);
        }

        processElements(element, activity);

        return activity;
    }

    private void processElements(Element element, Activity activity) throws DeploymentException {
        processStandardAttributes(element, activity);
        processSpecificAttributes(element, activity);
        processStandardElements(element, activity);
        processSpecificElements(element, activity);
    }

    protected abstract void processSpecificElements(Element element, Activity activity) throws DeploymentException;

    protected abstract void processSpecificAttributes(Element element, Activity activity) throws DeploymentException;

	/**
	 * 
	 * @uml.property name="activityClass"
	 */
	protected abstract Class getActivityClass();

    protected String truncNamespace(String string) {
        int index = string.lastIndexOf(":");
        return string.substring(index + 1);
    }

}
