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
 * SimpleFilter.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/27/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.general;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import com.webdeninteractive.bie.persistent.OIDGenerator;
import javax.swing.ImageIcon;
import java.util.*;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */
public class SimpleFilter extends AbstractExtensionFunction {
    
    /** Creates a new instance of StringConcatFunc */
    public SimpleFilter() {
        super.addLinkableParameter("input");
        super.addEditableParameter("source", "", String.class, "Source value - filter will apply to any data that matches this value.");
        super.addEditableParameter("target", "", String.class, "Target value - the desired value.");
    }
    
   
  /** apply function for nodelist.
   * @param list A unique node list. 
   * @param source An unique String value.
   * @param target An unique String value to be replaced with.
   * @return The nodelist of the input string if the input string not equals to the source string, the target string if the input string equals to the source string for each node's value.  		    
   */ 
    public org.w3c.dom.NodeList apply(org.w3c.dom.NodeList list, String source, String target){
	   
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list.getLength( );i++){		   
            if(owner==null){
                owner = list.item(i).getOwnerDocument();				
                elem = owner.createElement("tmp");
            }           
            String xValue = getStringValue(list.item(i));	
			if(xValue.equals(source)){        		
                elem.appendChild(owner.createTextNode(target));
			}else{
			    elem.appendChild(owner.createTextNode(xValue));
			}
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    } 
	
}
