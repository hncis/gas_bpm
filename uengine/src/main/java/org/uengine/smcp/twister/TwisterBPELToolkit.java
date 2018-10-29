package org.uengine.smcp.twister;

// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TwisterDeployerImpl.java

import java.io.*;
import java.net.URL;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.smartcomps.twister.common.configuration.DeployerConfiguration;
import org.smartcomps.twister.common.persistence.*;
import org.smartcomps.twister.common.transaction.TransactionException;
import org.smartcomps.twister.common.transaction.TransactionManager;
import org.smartcomps.twister.common.util.logger.Logger;
import org.smartcomps.twister.deployer.TwisterDeployer;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.uengine.smcp.twister.engine.priv.core.definition.*;
import org.uengine.smcp.twister.deployer.priv.*;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.*;

// Referenced classes of package org.smartcomps.twister.deployer.priv:
//			  ActivityDeployer, ActivityDeployerFactory

public class TwisterBPELToolkit
	//implements TwisterDeployer
	
{
	
	static boolean bConfLoaded = false;
	
	public TwisterBPELToolkit()
	{
		log = Logger.getLogger(getClass());
				
		if(!bConfLoaded){
			try{
				bConfLoaded = true;
				org.smartcomps.twister.common.configuration.XMLConfigurationReader.loadConfiguration();
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
	}

	public void deploy(String xmlProcessDescription)
		throws DeploymentException
	{
		deploy(xmlProcessDescription, ((String) (null)));
	}

	public void deploy(String xmlProcess, String xmlProcessDefinition)
		throws DeploymentException
	{
		try
		{
			deploy(getDocument(xmlProcess), getDocument(xmlProcessDefinition));
		}
		catch(DocumentException e)
		{
			log.error("Failed to deploy a XML description from a String.", e);
			throw new DeploymentException("unable to deploy the document", e);
		}
	}

	public void deploy(InputStream xmlProcessDescription)
		throws DeploymentException
	{
		String methodName = "deploy";
		log.entering(CLASSNAME, methodName, xmlProcessDescription);
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(xmlProcessDescription));
			StringBuffer buffer = new StringBuffer();
			for(String str = null; (str = reader.readLine()) != null;)
				buffer.append(str);

			deploy(getDocument(buffer.toString()), ((Document) (null)));
		}
		catch(DocumentException e)
		{
			log.error("Failed to deploy a XML description from a stream.", e);
			throw new DeploymentException("unable to deploy the document", e);
		}
		catch(IOException e)
		{
			log.error("Failed to deploy a XML description from a stream.", e);
			throw new DeploymentException("unable to deploy the document", e);
		}
		log.exiting(CLASSNAME, methodName);
	}

	public void deploy(URL xmlProcessDescription)
		throws DeploymentException
	{
		String methodName = "deploy";
		log.entering(CLASSNAME, methodName, xmlProcessDescription);
		try
		{
			deploy(getDocument(xmlProcessDescription), ((Document) (null)));
		}
		catch(Exception e)
		{
			log.error("Failed to deploy a XML description from an URL.", e);
			throw new DeploymentException("unable to deploy the document", e);
		}
		log.exiting(CLASSNAME, methodName);
	}

	public void deploy(File xmlProcessDescription, File defFile)
		throws DeploymentException
	{
		String methodName = "deploy";
		log.entering(CLASSNAME, methodName, xmlProcessDescription);
		try
		{
			deploy(getDocument(xmlProcessDescription.toURL()), getDocument(defFile.toURL()));
		}
		catch(Exception e)
		{
			log.error("Failed to deploy a XML description from a File.", e);
			throw new DeploymentException("unable to deploy the document", e);
		}
		log.exiting(CLASSNAME, methodName);
	}

	private void deploy(Document processDoc, Document definitionDoc)
		throws DeploymentException
	{
		String methodName = "deploy";
		log.entering(CLASSNAME, methodName, processDoc);
		try
		{
			log.debug("begin transaction");
//			TransactionManager.beginTransaction();
			Element processElement = processDoc.getRootElement();
			log.debug("<process>");
			TwisterProcess tp = deployProcessElement(processElement);
			Document defDoc = definitionDoc;
			if(definitionDoc == null)
				defDoc = getProcessDefDoc(processElement);
			deployDefinitions(defDoc, tp);
			deployVariables(processElement.element("variables"), tp);
			deployCorrelationSets(processElement.element("correlationSets"), tp);
			deployActivity(processElement, tp);
			log.debug("</process> ");
			//saveProcess(processDoc, defDoc, tp.getNamespace() + tp.getName());
			
//System.out.println("xml-------> "  + processDoc.asXML());
			log.debug("commit transaction");
//			TransactionManager.commitTransaction();
		}
		catch(TransactionException e)
		{
			throw new DeploymentException(e);
		}
		log.exiting(CLASSNAME, methodName);
	}	
	
	private TwisterProcess read(Document processDoc, Document definitionDoc)
		throws Exception
	{
		String methodName = "deploy";
		log.entering(CLASSNAME, methodName, processDoc);

			log.debug("begin transaction");
//			TransactionManager.beginTransaction();
			Element processElement = processDoc.getRootElement();
			log.debug("<process>");
			TwisterProcess tp = deployProcessElement(processElement);
			Document defDoc = definitionDoc;
			if(definitionDoc == null)
				defDoc = getProcessDefDoc(processElement);
			deployDefinitions(defDoc, tp);
			deployVariables(processElement.element("variables"), tp);
			deployCorrelationSets(processElement.element("correlationSets"), tp);
			deployActivity(processElement, tp);
			log.debug("</process> ");
			//saveProcess(processDoc, defDoc, tp.getNamespace() + tp.getName());
			
			return tp;			
	}
	
	public TwisterProcess read(InputStream processIS, InputStream defIS) throws Exception		
	{
		StringBuffer processSB = new StringBuffer();
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(processIS));
			for(String str = null; (str = reader.readLine()) != null;)
				processSB.append(str);
			reader.close();
		}
		StringBuffer defSB = new StringBuffer();
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(defIS));
			for(String str = null; (str = reader.readLine()) != null;)
				defSB.append(str);
			reader.close();
		}

		return read(getDocument(processSB.toString()), getDocument(defSB.toString()));
	}


	private void saveProcess(Document processDoc, Document defDoc, String processName)
		throws DeploymentException
	{
/*		{
			org.xmldb.api.base.Collection processColl = XMLDataAccess.getCollection("/process");
			org.xmldb.api.base.Collection processDefColl = XMLDataAccess.getCollection("/process/def");
			if(processColl == null)
			{
				org.xmldb.api.base.Collection rootColl = XMLDataAccess.getRootCollection();
				processColl = XMLDataAccess.createCollection(rootColl, "process");
			}
			if(processDefColl == null)
				processDefColl = XMLDataAccess.createCollection(processColl, "def");
			XMLDataAccess.insertDocument(processColl, "" + processName.hashCode(), processDoc);
			XMLDataAccess.insertDocument(processDefColl, "" + processName.hashCode(), defDoc);
		}
		catch(Exception e)
		{
			throw new DeploymentException("Cuold not save the process description and web services definitions in DB.", e);
		}*/
	}

	private Document getProcessDefDoc(Element processElement)
		throws DeploymentException
	{
		List list = processElement.declaredNamespaces();
		Document doc = null;
		for(int i = 0; i < list.size(); i++)
		{
			Namespace ns = (Namespace)list.get(i);
			String nsURI = ns.getURI();
			List uncheckedDefSchema = DeployerConfiguration.getUncheckedDefSchema();
			if(!uncheckedDefSchema.contains(nsURI))
			{
				URL nsURL = null;
				String urlLocalMapping = getUrlLocalMapping(nsURI);
				nsURL = getClass().getClassLoader().getResource(urlLocalMapping);
				try
				{
					doc = getDocument(nsURL);
				}
				catch(Exception e)
				{
					throw new DeploymentException(e);
				}
			}
		}

		return doc;
	}

	private void deployDefinitions(Document doc, TwisterProcess tp)
		throws DeploymentException
	{
		Element rootElement = doc.getRootElement();
		Iterator propertyAlias = rootElement.elementIterator("propertyAlias");
		Map addedProperty = new HashMap();
		try
		{
			while(propertyAlias.hasNext()) 
			{
				Element e = (Element)propertyAlias.next();
				String propertyName = e.valueOf("@propertyName");
				Namespace defaultNS = doc.getRootElement().getNamespace();
				XPath xpathSelector = null;
				if(defaultNS != null)
				{
					xpathSelector = DocumentHelper.createXPath("//*/defaultNS:property[@name=\"" + propertyName + "\"]");
					HashMap nsMap = new HashMap(1);
					nsMap.put("defaultNS", defaultNS.getURI());
					xpathSelector.setNamespaceURIs(nsMap);
				} else
				{
					xpathSelector = DocumentHelper.createXPath("//*/property[@name=\"" + propertyName + "\"]");
				}
				Node propNode = xpathSelector.selectSingleNode(doc);
				Property prop = (Property)addedProperty.get(propertyName);
				if(propNode != null && prop == null)
				{
					//prop = ProcessFactory.addProperty(tp, propertyName, propNode.valueOf("@type"));
					prop = addPropertyToProcess(tp, propertyName, propNode.valueOf("@type"));
					//
					
					addedProperty.put(propertyName, prop);
				}
				if(prop != null)
				{
					//ProcessFactory.addPropertyAlias(prop, e.valueOf("@messageType"), e.valueOf("@part"), e.valueOf("@query"));
					PropertyAliasImpl alias = new PropertyAliasImpl();
					alias.setMessageType( e.valueOf("@messageType"));
					alias.setPart(e.valueOf("@part"));
					alias.setQuery(e.valueOf("@query"));
					prop.addAlias(alias);
					//
				} else
				{
					log.error("A propertyAlias is defined without property : " + propertyName);
					throw new DeploymentException("a propertyAlias is defined without property : " + propertyName);
				}
			}
			for(Iterator properties = rootElement.elementIterator("property"); properties.hasNext();)
			{
				Element e = (Element)properties.next();
				String name = e.valueOf("@name");
				if(!addedProperty.containsKey(name))
					//ProcessFactory.addProperty(tp, name, e.valueOf("@type"));
					addPropertyToProcess(tp, name, e.valueOf("@type"));
			}

		}
		catch(Exception e)
		{
			throw new DeploymentException(e);
		}
	}

	private Property addPropertyToProcess(TwisterProcess tp, String name, String type){
		PropertyImpl newProp = new PropertyImpl();
		newProp.setName(name);
		newProp.setType(type);					
		((ProcessImpl)tp).addProperty(newProp);
		
		return newProp;
	}

	private String getUrlLocalMapping(String nsURI)
	{
		String property = null;
		property = (String)DeployerConfiguration.getProcessDefMapping().get(nsURI);
		if(property == null)
			property = nsURI;
		return property;
	}

	private void deployActivity(Element processElement, TwisterProcess tp)
		throws DeploymentException, TransactionException
	{
		String methodName = "deployActivity";
		log.entering(CLASSNAME, methodName);
		Element activityElement = ActivityDeployer.getActivityElement(processElement);
		if(activityElement != null)
		{
			log.debug("<" + activityElement.getName() + ">");
			ActivityDeployer ad = ActivityDeployerFactory.getActivityDeployer(activityElement.getName());
			try
			{
				ad.deploy(activityElement, tp);
				log.debug("</" + activityElement.getName() + ">");
			}
			catch(DeploymentException e)
			{
//				TransactionManager.rollbackTransaction();
				log.error("Transation Rolled Back due to " + e.getMessage());
				throw new DeploymentException(e);
			}
		}
		log.exiting(CLASSNAME, methodName);
	}

	private TwisterProcess deployProcessElement(Element processElement)
		throws TransactionException, DeploymentException
	{
		String methodName = "deployProcessElement";
		log.entering(CLASSNAME, methodName, processElement);
		String name = processElement.valueOf("@name");
		String targetNamespace = processElement.valueOf("@targetNamespace");
		TwisterProcess tp = null;
		try
		{
			//tp = ProcessFactory.createProcess(name, targetNamespace);
			ProcessImpl process = new ProcessImpl();
			process.setName(name);
		 	process.setNamespace(targetNamespace);
		 	
		 	tp = process;
		 	//
		}
		catch(Exception e)
		{
			throw new DeploymentException(e);
		}
		log.exiting(CLASSNAME, methodName, tp);
		return tp;
	}

	private void deployVariables(Element element, TwisterProcess tp)
	{
		if(element != null)
		{
			for(Iterator it = element.elementIterator("variable"); it.hasNext(); log.debug("</variable>"))
			{
				log.debug("<variable>");
				Element variable = (Element)it.next();
				String name = variable.valueOf("@name");
				String messageType = variable.valueOf("@messageType");
				String type = variable.valueOf("@type");
				String elmt = variable.valueOf("@element");
				log.debug("name = " + name);
				log.debug("messageType = " + messageType);
				log.debug("type = " + type);
				log.debug("element = " + elmt);

				VariableImpl var = new VariableImpl();
				var.setName(name);
				var.setMessageType(type);
				((ProcessImpl)tp).addVariable(var);
			}
		}
	}

	private void deployCorrelationSets(Element element, TwisterProcess tp)
		throws DeploymentException
	{
		if(element != null)
		{
			for(Iterator it = element.elementIterator("correlationSet"); it.hasNext();)
			{
				log.debug("<correlationSet>");
				Element variable = (Element)it.next();
				String name = variable.valueOf("@name");
				String properties = variable.valueOf("@properties");
				log.debug("name = " + name);
				log.debug("messageType = " + properties);
				log.debug("</correlationSet>");
				try
				{
					//ProcessFactory.addCorrelation(tp, name, truncNamespace(properties));
					CorrelationSetImpl correlationSet = new CorrelationSetImpl();
					correlationSet.setName(name);
					correlationSet.setPropertiesString(truncNamespace(properties));
					((ProcessImpl)tp).addCorrelationSet(correlationSet);
					//

				}
				catch(Exception e)
				{
					throw new DeploymentException(e);
				}
			}

		}
	}

	private String truncNamespace(String nsProps)
	{
		StringBuffer properties = new StringBuffer();
		for(StringTokenizer nsTokenizer = new StringTokenizer(nsProps); nsTokenizer.hasMoreTokens();)
		{
			String nsProp = nsTokenizer.nextToken();
			int index = nsProp.lastIndexOf(NS_SEPARATOR);
			properties.append(nsProp.substring(index + 1));
			if(nsTokenizer.hasMoreTokens())
				properties.append(SPACE);
		}

		return properties.toString();
	}

	private Document getDocument(String xmlProcessDescription)
		throws DocumentException
	{
		if(xmlProcessDescription == null)
			return null;
		else
			return DocumentHelper.parseText(xmlProcessDescription);
	}

	private Document getDocument(URL xmlProcessDescription)
		throws DocumentException
	{
		SAXReader reader = new SAXReader();
		Document document = reader.read(xmlProcessDescription);
		return document;
	}

	/**
	 * 
	 * @uml.property name="log"
	 * @uml.associationEnd 
	 * @uml.property name="log" multiplicity="(1 1)"
	 */
	private Logger log;

	private final String CLASSNAME = getClass().getName();
	public static String NS_SEPARATOR = ":";
	public static String SPACE = " ";

}
