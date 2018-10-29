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
 * CountChildNodes.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/23/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.xpath;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;

/**
 *
 * @author  bmadigan, jdu
 */
public class CountChildNodes extends AbstractExtensionFunction {
    
    /** Creates a new instance of CountChildNodes
	* with with the left link parameter parent.
	* parent should be org.w3c.dom.Node type.
	 */
    public CountChildNodes() {
	     super.addLinkableParameter("parent");  
    }
   
   /** count function  		
    * @node An unique org.w3c.dom.Node.
    * @return The child node count for the given node.
    */ 
    
    public static int count(org.w3c.dom.Node node){
        return node.getChildNodes( ).getLength( );
    }
    
}
