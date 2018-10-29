package org.uengine.ui;

import org.jdom2.Element;
import org.uengine.util.XMLUtil;
//import org.w3c.dom.Node;

public class AdapterNode 
    { 
      Element domNode;
	  static final String[] typeName = {
		  "none",
		  "Element",
		  "Attr",
		  "Text",
		  "CDATA",
		  "EntityRef",
		  "Entity",
		  "ProcInstr",
		  "Comment",
		  "Document",
		  "DocType",
		  "DocFragment",
		  "Notation",
	  };


      // Construct an Adapter node from a DOM node
      public AdapterNode(Element node) {
        domNode = node;
        
/*		NodeList nl = node.getChildNodes();		
        for(int i=0; i<nl.getLength(); i++){
        	Node child = nl.item(i);
        	if(child.getNodeType()==2){
        		
        	}
        }
        
        node.getAttributes().setNamedItem(new Node)*/
      }

	  public String toString() {
		String s = "";//typeName[domNode.getNodeType()];
		//String nodeName = domNode.getNodeName();
		String nodeName = domNode.getName();
		
		try{
			if ( domNode.getAttributeValue("name") != null )
				nodeName = domNode.getAttributeValue("name");
//			Node nameAttr = domNode.getAttributes().getNamedItem("name");
//			if(nameAttr!=null){
//				nodeName = nameAttr.getNodeValue();
//			}
		}catch(Exception e){
		}
		
		
		if (! nodeName.startsWith("#")) {
		   s += nodeName;
		}
		if (domNode.getText() != null) {
		//if (domNode.getNodeValue() != null) {
//		   if (s.startsWith("ProcInstr")) 
//			  s += ", "; 
//		   else 
//			  s += ": ";
		   // Trim the value to get rid of NL's at the front
		   //String t = domNode.getNodeValue().trim();
		   String t = domNode.getText().trim();
		   int x = t.indexOf("\n");
		   if (x >= 0) t = t.substring(0, x);
		   s += t;
		}
		return s;
	  }

      /*
       * Return children, index, and count values
       */
      public int index(AdapterNode child) {
        //System.err.println("Looking for index of " + child);
        int count = childCount();
        for (int i=0; i<count; i++) {
          AdapterNode n = this.child(i);
          if (child.domNode == n.domNode) return i;
        }
        return -1; // Should never get here.
      }

      public AdapterNode child(int searchIndex) {
        //Note: JTree index is zero-based.
    	  Element node = (Element)domNode.getChildren().get(searchIndex);
    	  return new AdapterNode(node);
//        org.w3c.dom.Node node = 
//             domNode.getChildNodes().item(searchIndex);
//        return new AdapterNode(node); 
      }

      public int childCount() {
          //return domNode.getChildNodes().getLength();
    	  return domNode.getChildren().size();
      }     
      
      public String getValue(){      	
		try{
			if ( domNode.getAttributeValue("value") != null )
				return domNode.getAttributeValue("value");
//			Node valueAttr = domNode.getAttributes().getNamedItem("value");
//			if(valueAttr!=null){
//				return valueAttr.getNodeValue();
//			}
		}catch(Exception e){
		}
		      	
      	//return domNode.getNodeValue();
		return domNode.getText();
      }
}

 