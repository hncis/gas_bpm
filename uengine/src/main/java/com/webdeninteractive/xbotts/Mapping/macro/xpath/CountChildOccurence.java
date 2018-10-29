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
 * CountChildOccurence.java
 *
 * Created on October 10, 2002, 11:47 AM,02/23/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.xpath;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */
public class CountChildOccurence extends AbstractExtensionFunction {
    
    /** Creates a new instance of CountChildOccurence
	 * with with the left link parameter parent and editable parameter Child Name.
	 * parent should be org.w3c.dom.Node type.
	 */
    public CountChildOccurence() {
	    super.addLinkableParameter("parent"); 
		super.addEditableParameter("Child Name", "child", String.class, "Element name to count"); 
    }
    
   /** occurs function  		
    * @node An unique org.w3c.dom.Node.
	* @name An unique Element name.
    * @return The count of the child occurence.
    */     
    public static int occurs(org.w3c.dom.Node node, String name){
        NodeList nodes = node.getChildNodes( );
        int occ=0;
        for(int i=0;i<nodes.getLength( );i++){
            if(!(nodes.item(i) instanceof Element)) continue;
            Element e = (Element)nodes.item(i);
            if(e.getTagName( ).equals(name)) occ++;
        }
        return occ;
    }
    
}
