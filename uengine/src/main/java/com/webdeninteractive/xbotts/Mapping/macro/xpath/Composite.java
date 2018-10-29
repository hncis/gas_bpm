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
 * Composite.java
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
public class Composite extends AbstractExtensionFunction {
    
   /** Creates a new instance of Composite
	* with with the left link parameter node.
	* node should be org.w3c.dom.NodeList type.
	*/
    public Composite() {
	    super.addLinkableParameter("node");  
    }
      
   /** composite function  		
    * @nodes An unique org.w3c.dom.NodeList.
    * @return A string with all of the node value appended together.
    */ 
    public static String composite(org.w3c.dom.NodeList nodes){
        StringBuffer sb = new StringBuffer( );
        for(int i=0;i<nodes.getLength( );i++){
            Node item = nodes.item(i);
            if(item instanceof CharacterData){
                sb.append(item.getNodeValue( ));
            }
        }
        return sb.toString( );
    }
    
}
