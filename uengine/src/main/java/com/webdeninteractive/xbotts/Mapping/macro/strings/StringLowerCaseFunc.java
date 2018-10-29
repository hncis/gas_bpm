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
 * StringLowerCaseFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/20/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.strings;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */
public class StringLowerCaseFunc extends AbstractStringFunction {
    
    /** Creates a new instance of StringLowerCaseFunc
	 * with the left link parameter str1.
	 */
    public StringLowerCaseFunc() {
	     super.addLinkableParameter("str1"); 
    }
 
   
    /** toLowerCase function for nodelist.
     * @param list A unique node list.
     * @return The nodelist of the lower case of the string for each node's value.  		    
     */ 
    public org.w3c.dom.NodeList toLowerCase(org.w3c.dom.NodeList list){
	return super.execute(list, op);
    }
    
    public String toLowerCase(String input){
        return op.doOp(input);
    }
	  
    StringOperation op = new StringOperation(){
        public String doOp(String input){
            return input.toLowerCase();
        }
    };
	
}
