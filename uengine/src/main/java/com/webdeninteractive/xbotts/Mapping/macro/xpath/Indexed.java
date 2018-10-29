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
 * Indexed.java
 *
 * Created on October 10, 2003, 11:47 AM,  02/23/2004 
 */

package com.webdeninteractive.xbotts.Mapping.macro.xpath;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
import java.util.ArrayList;

/**
 *
 * @author  bmadigan, jdu
 */
public class Indexed extends AbstractExtensionFunction {
    
	/** Creates a new instance of Indexed
	 * with the left link parameter node and an editable parameter Item index.
	 * The node should be org.w3c.dom.NodeList. 
	 */
    public Indexed() {
        super.addEditableParameter("Item index", "0", String.class, "The index of the node to map");
        super.addLinkableParameter("node");
    }
    
	
    
	/** item function
	 * @nodes An unique org.w3c.dom.NodeList.
	 * @index An unique number.
	 * @return The value for the index.  		    
	 */
    public static String item(org.w3c.dom.NodeList nodes, int index)throws Exception{
        if(index>=nodes.getLength( )) return "";
        if(index<0) throw new Exception("Index out of range: "+index);
        //get elements
        ArrayList elements = new ArrayList ();
        for(int i=0;i<nodes.getLength( );i++){
            Node n = nodes.item(i);
            if(n instanceof Element){
                elements.add(n);
            }
        }
        if(index>elements.size()) return "";
        Element selected = (Element) elements.get(index);
        Node text = selected.getFirstChild( );
        if(text instanceof CharacterData) return text.getNodeValue( );
        return "";
    }
    
}
