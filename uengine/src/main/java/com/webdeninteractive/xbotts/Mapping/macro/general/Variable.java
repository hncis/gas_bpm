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
 * Variable.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/27/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.general;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */
public class Variable extends AbstractExtensionFunction {
    
    /** Creates a new instance of Variable
	 * with a left side linkable parameter value.
	 */
    public Variable() {
	    super.addLinkableParameter("value");
    }
   
	
	/** getValue function for nodelist.
     * @param list A unique node list.
     * @return The nodelist of the value for each node.  		    
     */ 
    public org.w3c.dom.NodeList getValue(org.w3c.dom.NodeList list){
	   
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list.getLength( );i++){		   
            if(owner==null){
                owner = list.item(i).getOwnerDocument();				
                elem = owner.createElement("tmp");
            }           
            String xValue = getStringValue(list.item(i));			
            elem.appendChild(owner.createTextNode(xValue));
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    } 
	  
	
}
