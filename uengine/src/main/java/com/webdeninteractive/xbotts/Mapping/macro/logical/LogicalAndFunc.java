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
 * LogicalAndFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/27/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.logical;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */
public class LogicalAndFunc extends AbstractExtensionFunction {
    
    /** Creates a new instance of LogicalAndFunc
	 * with the left link parameter boolean1 and boolean2.
	 */
    public LogicalAndFunc() {
	     super.addLinkableParameter("boolean1"); 
		 super.addLinkableParameter("boolean2"); 
    }
    
     
  /** logicalAnd function for nodelist.
   * @param list1 A unique node list.
   * @param list2 A unique node list.
   * @return The nodelist of the logical and of the two boolean value for each node.  		    
   */ 
    public org.w3c.dom.NodeList logicalAnd(org.w3c.dom.NodeList list1, org.w3c.dom.NodeList list2)throws Exception{
        Element elem=null;
        Document owner= null;
        if(list1.getLength( )!=list2.getLength( )) throw new Exception("Attempted to logicalAnd NodeList of unequal lengths");
        for(int i=0;i<list1.getLength( );i++){	
		    System.out.println("i"+i);	   
            if(owner==null){
                owner = list1.item(i).getOwnerDocument();				
                elem = owner.createElement("tmp");
            }           
            String xValue = getStringValue(list1.item(i));			
            String yValue = getStringValue(list2.item(i));		
			Boolean b1 = new Boolean(xValue);
            Boolean b2 = new Boolean(yValue);	
			elem.appendChild(owner.createTextNode( Boolean.toString(b1.booleanValue()&&b2.booleanValue())));
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    } 
	  

}
