package org.uengine.smcp.twister.deployer.priv;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.smartcomps.twister.common.configuration.DeployerConfiguration;
import org.smartcomps.twister.common.persistence.CreationException;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.common.persistence.XMLDataAccess;
import org.smartcomps.twister.common.transaction.TransactionException;
import org.smartcomps.twister.common.transaction.TransactionManager;
import org.smartcomps.twister.common.util.logger.Logger;
import org.smartcomps.twister.deployer.TwisterDeployer;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.ProcessFactory;
import org.uengine.smcp.twister.engine.priv.core.definition.Property;
import org.uengine.smcp.twister.engine.priv.core.definition.TwisterProcess;
//import org.xmldb.api.base.Collection;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Implementation of the TwisterDeployer interface.
 * <process name="ncname" targetNamespace="uri"
 * queryLanguage="anyURI"?
 * expressionLanguage="anyURI"?
 * suppressJoinFailure="yes|no"?
 * enableInstanceCompensation="yes|no"?
 * abstractProcess="yes|no"?
 * xmlns="http://schemas.xmlsoap.org/ws/2003/03/business-process/">
 * <partnerLinks>?
 * <!-- Note: At least one role must be specified. -->
 * <partnerLink name="ncname" partnerLinkType="qname"
 * myRole="ncname"? partnerRole="ncname"?>+
 * </partnerLink>
 * </partnerLinks>
 * <partners>?
 * <partner name="ncname">+
 * <partnerLink name="ncname"/>+
 * </partner>
 * </partners>
 * <variables>?
 * <variable name="ncname" messageType="qname"?
 * type="qname"? element="qname"?/>+
 * </variables>
 * <correlationSets>?
 * <correlationSet name="ncname" properties="qname-list"/>+
 * </correlationSets>
 * <faultHandlers>?
 * <!-- Note: There must be at least one fault handler or default. -->
 * <catch faultName="qname"? faultVariable="ncname"?>*
 * activity
 * </catch>
 * <catchAll>?
 * activity
 * </catchAll>
 * </faultHandlers>
 * <compensationHandler>?
 * activity
 * </compensationHandler>
 * <eventHandlers>?
 * <!-- Note: There must be at least one onMessage or onAlarm handler. -->
 * <onMessage partnerLink="ncname" portType="qname"
 * operation="ncname" variable="ncname"?>
 * <correlations>?
 * <correlation set="ncname" initiate="yes|no"?>+
 * </correlations>
 * activity
 * </onMessage>
 * <onAlarm for="duration-expr"? until="deadline-expr"?>*
 * activity
 * </onAlarm>
 * </eventHandlers>
 * activity
 * </process>
 */
public class TwisterDeployerImpl implements TwisterDeployer {

	/**
	 * 
	 * @uml.property name="log"
	 * @uml.associationEnd 
	 * @uml.property name="log" multiplicity="(1 1)"
	 */
	private Logger log = Logger.getLogger(getClass());

    private final String CLASSNAME = getClass().getName();

    public static String NS_SEPARATOR = ":";
    public static String SPACE = " ";

    public void deploy(String xmlProcessDescription) throws DeploymentException {
        deploy(xmlProcessDescription, null);
    }

    public void deploy(String xmlProcess, String xmlProcessDefinition) throws DeploymentException {
        try {
            deploy(getDocument(xmlProcess), getDocument(xmlProcessDefinition));
        } catch (DocumentException e) {
            log.error("Failed to deploy a XML description from a String.", e);
            throw new DeploymentException("unable to deploy the document", e);
        }
    }

    public void deploy(InputStream xmlProcessDescription) throws DeploymentException {
        String methodName = "deploy";
        log.entering(CLASSNAME, methodName, xmlProcessDescription);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(xmlProcessDescription));
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            deploy(getDocument(buffer.toString()), null);
        } catch (DocumentException e) {
            log.error("Failed to deploy a XML description from a stream.", e);
            throw new DeploymentException("unable to deploy the document", e);
        } catch (IOException e) {
            log.error("Failed to deploy a XML description from a stream.", e);
            throw new DeploymentException("unable to deploy the document", e);
        }
        log.exiting(CLASSNAME, methodName);
    }

    public void deploy(URL xmlProcessDescription) throws DeploymentException {
        String methodName = "deploy";
        log.entering(CLASSNAME, methodName, xmlProcessDescription);
        try {
            deploy(getDocument(xmlProcessDescription), null);
        } catch (Exception e) {
            log.error("Failed to deploy a XML description from an URL.", e);
            throw new DeploymentException("unable to deploy the document", e);
        }
        log.exiting(CLASSNAME, methodName);
    }

    public void deploy(File xmlProcessDescription) throws DeploymentException {
        String methodName = "deploy";
        log.entering(CLASSNAME, methodName, xmlProcessDescription);
        try {
            deploy(getDocument(xmlProcessDescription.toURL()), null);
        } catch (Exception e) {
            log.error("Failed to deploy a XML description from a File.", e);
            throw new DeploymentException("unable to deploy the document", e);
        }
        log.exiting(CLASSNAME, methodName);
    }

    private void deploy(Document processDoc, Document definitionDoc) throws DeploymentException {
        String methodName = "deploy";
        log.entering(CLASSNAME, methodName, processDoc);

        try {
            log.debug("begin transaction");
            TransactionManager.beginTransaction();

            Element processElement = processDoc.getRootElement();
            log.debug("<process>");
            TwisterProcess tp = deployProcessElement(processElement);
            Document defDoc = definitionDoc;
            if (definitionDoc == null) {
                defDoc = getProcessDefDoc(processElement);
            }
            deployDefinitions(defDoc, tp);
            deployVariables(processElement.element("variables"), tp);
            deployCorrelationSets(processElement.element("correlationSets"), tp);

            // todo implements 'partnerLinks' elements
            // todo implements 'partners' elements
            // todo implements 'faultHandlers' elements
            // todo implements 'compensationHandlers' elements
            // todo implements 'eventHandlers' elements

            deployActivity(processElement, tp);
            log.debug("</process> ");

            saveProcess(processDoc, defDoc, tp.getNamespace()+tp.getName());

            log.debug("commit transaction");
            TransactionManager.commitTransaction();
        } catch (TransactionException e) {
            try {
                TransactionManager.rollbackTransaction();
            } catch (TransactionException e1) {
                throw new DeploymentException("Could not rollback transaction.", e);
            }
            throw new DeploymentException(e);
        }

        log.exiting(CLASSNAME, methodName);
    }

    private void saveProcess(Document processDoc, Document defDoc, String processName) throws DeploymentException {
/*        try {
            Collection processColl = XMLDataAccess.getCollection("/process");
            Collection processDefColl = XMLDataAccess.getCollection("/process/def");
            if (processColl == null) {
                Collection rootColl = XMLDataAccess.getRootCollection();
                processColl = XMLDataAccess.createCollection(rootColl, "process");
            }
            if (processDefColl == null) {
                processDefColl = XMLDataAccess.createCollection(processColl, "def");
            }
            XMLDataAccess.insertDocument(processColl, "" + processName.hashCode(), processDoc);
            XMLDataAccess.insertDocument(processDefColl, "" + processName.hashCode(), defDoc);
        } catch (Exception e) {
            throw new DeploymentException("Cuold not save the process description and web services " +
                    "definitions in DB.", e);
        }*/
    }

    /**
     * todo actualy, it processes only properties defined in one file.
     * todo Deploy also if there are more than one definition file.
     */
    private Document getProcessDefDoc(Element processElement) throws DeploymentException {
        List list = processElement.declaredNamespaces();
        Document doc = null;
        for (int i = 0; i < list.size(); i++) {
            Namespace ns = (Namespace) list.get(i);
            String nsURI = ns.getURI();
            List uncheckedDefSchema = DeployerConfiguration.getUncheckedDefSchema();
            if (!uncheckedDefSchema.contains(nsURI)) {
                URL nsURL = null;
                String urlLocalMapping = getUrlLocalMapping(nsURI);
                nsURL = getClass().getClassLoader().getResource(urlLocalMapping);
                try {
                    doc = getDocument(nsURL);
                } catch (Exception e) {
                    throw new DeploymentException(e);
                }
            }
        }
        return doc;
    }

    private void deployDefinitions(Document doc, TwisterProcess tp) throws DeploymentException {
        Element rootElement = doc.getRootElement();
        Iterator propertyAlias = rootElement.elementIterator("propertyAlias");
        Map addedProperty = new HashMap();
        try {
            while (propertyAlias.hasNext()) {
                Element e = (Element) propertyAlias.next();
                String propertyName = e.valueOf("@propertyName");

                Namespace defaultNS = doc.getRootElement().getNamespace();
                XPath xpathSelector = null;
                if (defaultNS != null) {
                    xpathSelector = DocumentHelper.createXPath("//*/defaultNS:property[@name=\"" + propertyName + "\"]");
                    HashMap nsMap = new HashMap(1);
                    nsMap.put("defaultNS", defaultNS.getURI());
                    xpathSelector.setNamespaceURIs(nsMap);
                } else {
                    xpathSelector = DocumentHelper.createXPath("//*/property[@name=\"" + propertyName + "\"]");
                }
                Node propNode = xpathSelector.selectSingleNode(doc);
//                Node propNode = doc.selectSingleNode("//*/property[@name=\"" + propertyName + "\"]");

                Property prop = (Property) addedProperty.get(propertyName);
                if (propNode != null && prop == null) {
                    prop = ProcessFactory.addProperty(tp,
                            propertyName, propNode.valueOf("@type"));
                    addedProperty.put(propertyName, prop);
                }
                if (prop != null) {
                    ProcessFactory.addPropertyAlias(prop, e.valueOf("@messageType"),
                            e.valueOf("@part"), e.valueOf("@query"));
                } else {
                    log.error("A propertyAlias is defined without property : " + propertyName);
                    throw new DeploymentException("a propertyAlias is defined without property : " + propertyName);
                }
            }
            Iterator properties = rootElement.elementIterator("property");
            while (properties.hasNext()) {
                Element e = (Element) properties.next();
                String name = e.valueOf("@name");
                if (addedProperty.containsKey(name) == false) {
                    ProcessFactory.addProperty(tp, name, e.valueOf("@type"));
                }
            }
        } catch (Exception e) {
            throw new DeploymentException(e);
        }
    }

    private String getUrlLocalMapping(String nsURI) {
        String property = null;
        property = (String) DeployerConfiguration.getProcessDefMapping().get(nsURI);
        if (property == null) {
            property = nsURI;
        }
        return property;
    }

    private void deployActivity(Element processElement, TwisterProcess tp) throws DeploymentException, TransactionException {
        String methodName = "deployActivity";
        log.entering(CLASSNAME, methodName);
        Element activityElement = ActivityDeployer.getActivityElement(processElement);
        if (activityElement != null) {
            log.debug("<" + activityElement.getName() + ">");
            ActivityDeployer ad = ActivityDeployerFactory.getActivityDeployer(activityElement.getName());
            try {
                ad.deploy(activityElement, tp);
                log.debug("</" + activityElement.getName() + ">");
            } catch (DeploymentException e) {
                TransactionManager.rollbackTransaction();
                log.error("Transation Rolled Back due to " + e.getMessage());
                throw new DeploymentException(e);
            }
        }
        log.exiting(CLASSNAME, methodName);
    }

    /**
     * Deploy the proces element.
     * 
     * @param processElement the process DOM element
     * @return the TwisterProcess corresponding the given process
     * @throws TransactionException 
     * @throws DeploymentException  
     */
    private TwisterProcess deployProcessElement(Element processElement) throws TransactionException, DeploymentException {
        String methodName = "deployProcessElement";
        log.entering(CLASSNAME, methodName, processElement);
        String name = processElement.valueOf("@name");
        String targetNamespace = processElement.valueOf("@targetNamespace");
//        String queryLanguage = processElement.valueOf("@queryLanguage");
//        String expressionLanguage = processElement.valueOf("@expressionLanguage");
//        String suppressJoinFailure = processElement.valueOf("@suppressJoinFailure");
//        String enableInstanceCompensation = processElement.valueOf("@enableInstanceCompensation");
//        String abstractProcess = processElement.valueOf("@abstractProcess");
//        String xmlns = processElement.valueOf("@xmlns");
        TwisterProcess tp = null;
        try {
            tp = ProcessFactory.createProcess(name, targetNamespace);
        } catch (DBSessionException e) {
            TransactionManager.rollbackTransaction();
            throw new DeploymentException(e);
        } catch (CreationException e) {
            TransactionManager.rollbackTransaction();
            throw new DeploymentException(e);
        }
        log.exiting(CLASSNAME, methodName, tp);
        return tp;
    }


    /**
     * Deployment of the Variables elements
     * <p/>
     * <variables>?
     * <variable name="ncname" messageType="qname"?
     * type="qname"? element="qname"?/>+
     * </variables>
     * 
     * @param element the Variables DOM element.
     * @param tp      the parent process
     */
    private void deployVariables(Element element, TwisterProcess tp) {
        if (element != null) {
            for (Iterator it = element.elementIterator("variable"); it.hasNext();) {
                log.debug("<variable>");
                Element variable = (Element) it.next();
                String name = variable.valueOf("@name");
                String messageType = variable.valueOf("@messageType");
                String type = variable.valueOf("@type");
                String elmt = variable.valueOf("@element");
                log.debug("name = " + name);
                log.debug("messageType = " + messageType);
                log.debug("type = " + type);
                log.debug("element = " + elmt);
                log.debug("</variable>");
            }
        }
    }

    /**
     * Deployment of the CorrelationSets element
     * <p/>
     * <correlationSets>?
     * <correlationSet name="ncname" properties="qname-list"/>+
     * </correlationSets>
     * 
     * @param element the CorrelationSets DOM element.
     * @param tp      the parent process
     * @throws DeploymentException 
     */
    private void deployCorrelationSets(Element element, TwisterProcess tp) throws DeploymentException {
        if (element != null) {
            for (Iterator it = element.elementIterator("correlationSet"); it.hasNext();) {
                log.debug("<correlationSet>");
                Element variable = (Element) it.next();
                String name = variable.valueOf("@name");
                String properties = variable.valueOf("@properties");
                log.debug("name = " + name);
                log.debug("messageType = " + properties);
                log.debug("</correlationSet>");
                try {
                    ProcessFactory.addCorrelation(tp, name, truncNamespace(properties));
                } catch (Exception e) {
                    throw new DeploymentException(e);
                }
            }
        }
    }

    private String truncNamespace(String nsProps) {
        StringBuffer properties = new StringBuffer();
        for (StringTokenizer nsTokenizer = new StringTokenizer(nsProps); nsTokenizer.hasMoreTokens();) {
            String nsProp = nsTokenizer.nextToken();
            int index = nsProp.lastIndexOf(NS_SEPARATOR);
            properties.append(nsProp.substring(index + 1));
            if (nsTokenizer.hasMoreTokens()) {
                properties.append(SPACE);
            }
        }
        return properties.toString();
    }

    private Document getDocument(String xmlProcessDescription) throws DocumentException {
        if (xmlProcessDescription == null) {
            return null;
        }
        return DocumentHelper.parseText(xmlProcessDescription);
    }

    private Document getDocument(URL xmlProcessDescription) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(xmlProcessDescription);

        return document;
    }


}
