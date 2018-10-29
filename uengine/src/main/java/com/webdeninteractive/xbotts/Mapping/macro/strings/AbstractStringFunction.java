/*
 * AbstractStringFunction.java
 *
 * Created on August 11, 2004, 11:23 AM
 */

package com.webdeninteractive.xbotts.Mapping.macro.strings;
import com.webdeninteractive.xbotts.Mapping.macro.AbstractExtensionFunction;
import org.w3c.dom.*;
import javax.xml.parsers.*;
/**
 *
 * @author  bmadigan
 */
public abstract class AbstractStringFunction extends AbstractExtensionFunction{
   
    public org.w3c.dom.NodeList execute(org.w3c.dom.NodeList list1, StringOperation op){
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list1.getLength( );i++){
            if(owner==null){
                owner = list1.item(i).getOwnerDocument();
                elem = owner.createElement("tmp");
            }
            String s = getStringValue(list1.item(i));
            elem.appendChild(owner.createTextNode(op.doOp(s)));
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    }
    
    public org.w3c.dom.NodeList execute(org.w3c.dom.NodeList list1, org.w3c.dom.NodeList list2, ComplexStringOperation op)
    throws Exception
    {
        Element elem=null;
        Document owner= null;
        
        if(list2!=null){
            if(list1.getLength( )!=list2.getLength( )) throw new Exception("Can't operate on NodeLists of unequal length");
        }
        for(int i=0;i<list1.getLength( );i++){
            if(owner==null){
                owner = list1.item(i).getOwnerDocument();
                elem = owner.createElement("tmp");
            }
            String s1 = getStringValue(list1.item(i));
            String s2 = getStringValue(list2.item(i));
            
            elem.appendChild(owner.createTextNode(op.doOp(s1, s2)));
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    }
    
    public org.w3c.dom.NodeList execute(org.w3c.dom.NodeList list1, CompoundStringOperation operation)throws Exception{                                        
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder( );
        doc = db.newDocument();        
                                                                                
        Element root = doc.createElement("root");
        doc.appendChild(root);
        
        Element groupLable = null;
        
        for(int j=0;j<list1.getLength( );j++){
                        
            String str = getStringValue(list1.item(j));
          
            String[] strings = operation.doOp(str);

            for (int k=0;k<strings.length;k++) {
                groupLable = doc.createElement("group" + k);
                root.appendChild(groupLable);
                groupLable.appendChild(doc.createTextNode(strings[k]));               
            }   
        }
        
        if(root==null) return null;
        return root.getChildNodes();                                        
        
       
     }
}
