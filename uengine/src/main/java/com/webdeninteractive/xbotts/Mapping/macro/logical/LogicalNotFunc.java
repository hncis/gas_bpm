/*
-------------------------------------------------------------------
BIE is Copyright 2001-2004 Brunswick Corp.
-------------------------------------------------------------------
Please read the legal notices (docs/legal.txt) and the license
(docs/bie_license.txt) that came with this distribution before using
this software.
-------------------------------------------------------------------
*/
/*
 * LogicalNotFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/20/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.logical;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */
public class LogicalNotFunc extends AbstractExtensionFunction {
    
    /** Creates a new instance of LogicalNotFunc 
	 * with the left link parameter boolean1 
	 */
    public LogicalNotFunc() {
	     super.addLinkableParameter("boolean1");  
    }
   
  	
  /** logicalNot function for nodelist.
   * @param list A unique node list.
   * @return The nodelist of the logical not of the boolean value for each node.  		    
   */ 
    public org.w3c.dom.NodeList logicalNot(org.w3c.dom.NodeList list){
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list.getLength( );i++){		   
            if(owner==null){
                owner = list.item(i).getOwnerDocument();				
                elem = owner.createElement("tmp");
            }           
            String xValue = getStringValue(list.item(i));	
			Boolean b1 = new Boolean(xValue);
            elem.appendChild(owner.createTextNode(Boolean.toString(!b1.booleanValue())));
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    } 
	  

}
