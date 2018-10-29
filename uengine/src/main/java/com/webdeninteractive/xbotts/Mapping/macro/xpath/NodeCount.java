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
 * NodeCount.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/23/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.xpath;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.NodeList;
/**
 *
 * @author  bmadigan, jdu
 */
public class NodeCount extends AbstractExtensionFunction {
    
    /** Creates a new instance of NodeCount
	 * with with the left link parameter node.
	 * node should be org.w3c.dom.NodeList type.
	 */
    public NodeCount() {
	     super.addLinkableParameter("node");  
    }
    
   /** count function  		
    * @nodes An unique org.w3c.dom.NodeList.
    * @return The number of nodes in the NodeList nodes.
    */ 
    public static int count(org.w3c.dom.NodeList nodes){
        return nodes.getLength( );
    }
    
}
