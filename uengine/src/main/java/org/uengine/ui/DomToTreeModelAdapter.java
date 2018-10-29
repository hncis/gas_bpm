package org.uengine.ui;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.swing.tree.*;
import javax.swing.event.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.*;

import org.uengine.processdesigner.ProcessDesigner;

public class DomToTreeModelAdapter implements javax.swing.tree.TreeModel{

	Document document;
	String documentURL;
	boolean partialLoading = false;
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	
	public DomToTreeModelAdapter(Document document, boolean partialLoading){
		this.document = document;
	}

	public DomToTreeModelAdapter(String documentURL, boolean partialLoading){
		this.partialLoading = partialLoading;
		this.documentURL = documentURL;
		
//		System.getProperties().setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.crimson.jaxp.DocumentBuilderFactoryImpl");

//		factory =
//		DocumentBuilderFactory.newInstance();
//		factory.setIgnoringElementContentWhitespace(true);
		
		try {
			SAXBuilder builder = new SAXBuilder();
			builder.setValidation(false);
			builder.setExpandEntities(false);
			builder.setIgnoringElementContentWhitespace(true);

				document = builder.build(ProcessDesigner.getClientProxy().openStream(documentURL));
			
//			builder = factory.newDocumentBuilder();
//			document = builder.parse(ProcessDesigner.getClientProxy().openStream(documentURL));
		} catch (JDOMException e) {
			throw new RuntimeException(e);
			//e.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException(e);
			//e.printStackTrace();
		} catch (Exception e) {
			throw new RuntimeException(e);
			//e.printStackTrace();
		}			
	}
	
	public Object  getRoot() {
	    return new AdapterNode(document.getRootElement());
	}
	
	public boolean isLeaf(Object aNode) {
		AdapterNode node = (AdapterNode) aNode;
		//return !node.domNode.getNodeName().equals("tree");
		
/*		boolean isLeaf = false;
		
		String nodeName = node.domNode.getNodeName(); 
		if(!nodeName.equals("tree") && nodeName.equals("")) isLeaf = true;
*/		
		if(partialLoading)
			return false;
			
		if (node.childCount() > 0) return false;
	    	return true;
	    	
//	    return isLeaf;
	}
	
	public int getChildCount(Object parent) {
	    AdapterNode node = (AdapterNode) parent;
	    int cnt = node.childCount();
	    
	    if(partialLoading && cnt==0){
	    	/*boolean isLeaf = false;
	    	Attr attrIsLeaf = (Attr)node.domNode.getAttributes().getNamedItem("isLeaf");
	    	
	    	if(attrIsLeaf!=null){
	    		try{
					isLeaf = ((Attr)node.domNode.getAttributes().getNamedItem("isLeaf")).getValue().equals("true");
	    		}catch(Exception e){
	    		}
	    	}
	    	
	    	if(!isLeaf){*/
				attachDocument(node);
				cnt = node.childCount();
				
				/*if(cnt==0){
					((Attr)node.domNode.getAttributes().getNamedItem("isLeaf")).setValue("true");
				}
	    	}*/
	    }
	    
	    return cnt;
	}
	
	protected void attachDocument(AdapterNode parent){
		try {
			SAXBuilder builder = new SAXBuilder();
			builder.setValidation(false);
			builder.setExpandEntities(false);
			builder.setIgnoringElementContentWhitespace(true);

			//document = builder.build(ProcessDesigner.getClientProxy().openStream(documentURL));
			Document documentToAttach = builder.build((new java.net.URL(documentURL + "parent=" + parent.getValue())).openStream());
			Element rootElement = documentToAttach.getRootElement();
			List childs = rootElement.getChildren();
			if ( childs != null && childs.size() > 0 ) {
				for(Iterator iter=childs.iterator(); iter.hasNext(); ) {
					Element child = (Element)iter.next();
					Element childToAppend = new Element(child.getName());
					childToAppend.setAttribute("name", child.getAttributeValue("name"));
					childToAppend.setAttribute("value", child.getAttributeValue("value"));
					parent.domNode.addContent(childToAppend);
//					child.
				}
			}
			//NodeList childs = documentToAttach.getCh().item(0).getChildNodes();
			
//			builder = factory.newDocumentBuilder();
//			document = builder.parse(ProcessDesigner.getClientProxy().openStream(documentURL));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
//		try {
//			Document documentToAttach = builder.parse((new java.net.URL(documentURL + "parent=" + parent.getValue())).openStream());			
//			NodeList childs = documentToAttach.getChildNodes().item(0).getChildNodes();
//			if(childs!=null && childs.getLength()>0){
//				for(int i=0; i<childs.getLength(); i++){
//					Node child = childs.item(i);
//					Node childToAppend = document.createElement(child.getNodeName());
//					
//					Attr attrValue = document.createAttribute("value");
//					Attr attrName = document.createAttribute("name");
//					attrValue.setValue(((Attr)child.getAttributes().getNamedItem("value")).getValue());
//					attrName.setValue(((Attr)child.getAttributes().getNamedItem("name")).getValue());
//					
//					childToAppend.getAttributes().setNamedItem(attrValue);
//					childToAppend.getAttributes().setNamedItem(attrName);
//										
//					parent.domNode.appendChild(childToAppend);
//				}
//			}else{
//				
//			}
//		} catch (SAXParseException spe) {
//		   // Error generated by the parser
//		   System.out.println("\n** Parsing error"
//			  + ", line " + spe.getLineNumber()
//			  + ", uri " + spe.getSystemId());
//		   System.out.println("   " + spe.getMessage() );
//
//		   // Use the contained exception, if any
//		   Exception  x = spe;
//		   if (spe.getException() != null)
//			   x = spe.getException();
//		   x.printStackTrace();
// 
//		} catch (SAXException sxe) {
//		   // Error generated during parsing)
//		   Exception  x = sxe;
//		   if (sxe.getException() != null)
//			   x = sxe.getException();
//		   x.printStackTrace();
//
//		} catch (IOException ioe) {
//		   // I/O error
//		   ioe.printStackTrace();
//		}		
	}
	
	public Object getChild(Object parent, int index) {
	    AdapterNode node = (AdapterNode) parent;
	    return node.child(index);
	}
	
	public int getIndexOfChild(Object parent, Object child) {
	    AdapterNode node = (AdapterNode) parent;
	    return node.index((AdapterNode) child);
	}
	
	public void valueForPathChanged(TreePath path, Object newValue) {
	}
	
	private Vector listenerList = new Vector();
	public void addTreeModelListener(TreeModelListener listener) {
	    if ( listener != null 
	    && ! listenerList.contains( listener ) ) {
	       listenerList.addElement( listener );
	    }
	}
	
	public void removeTreeModelListener(TreeModelListener listener) {
		if ( listener != null ) {
	    	listenerList.removeElement( listener );
		}
	}
	
	public void fireTreeNodesChanged( TreeModelEvent e ) {
	    Enumeration listeners = listenerList.elements();
	    while ( listeners.hasMoreElements() ) {
	      TreeModelListener listener = 
	        (TreeModelListener) listeners.nextElement();
	      listener.treeNodesChanged( e );
	    }
	}
	 
	public void fireTreeNodesInserted( TreeModelEvent e ) {
	    Enumeration listeners = listenerList.elements();
	    while ( listeners.hasMoreElements() ) {
	       TreeModelListener listener =
	         (TreeModelListener) listeners.nextElement();
	       listener.treeNodesInserted( e );
	    }
	}
	   
	public void fireTreeNodesRemoved( TreeModelEvent e ) {
	    Enumeration listeners = listenerList.elements();
	    while ( listeners.hasMoreElements() ) {
	      TreeModelListener listener = 
	        (TreeModelListener) listeners.nextElement();
	      listener.treeNodesRemoved( e );
	    }
	}
	   
	public void fireTreeStructureChanged( TreeModelEvent e ) {
	    Enumeration listeners = listenerList.elements();
	    while ( listeners.hasMoreElements() ) {
	      TreeModelListener listener =
	        (TreeModelListener) listeners.nextElement();
	      listener.treeStructureChanged( e );
	    }
	}

}
