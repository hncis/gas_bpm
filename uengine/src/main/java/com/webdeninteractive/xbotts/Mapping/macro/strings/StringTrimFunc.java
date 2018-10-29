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
 * StringTrimFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/23/2004
 */
package com.webdeninteractive.xbotts.Mapping.macro.strings;

import com.webdeninteractive.xbotts.Mapping.macro.*;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */

public class StringTrimFunc extends AbstractStringFunction {

    /** Creates a new instance of StringTrimFunc
	 * with the left link parameter string str1 
	 */
   public StringTrimFunc(){
      super.addLinkableParameter("str1");
   }

  
    /** trimString function for nodelist.
     * @param list A unique node list.
     * @return The nodelist of a copy of the string in a node with leading and trailing white space removed for each node's value.  		    
     */ 
    public org.w3c.dom.NodeList trimString(org.w3c.dom.NodeList list){
	return super.execute(list, op);    
    }
    
    public String trimString(String s){
        return op.doOp(s);
    }

    
    StringOperation op = new StringOperation(){
        public String doOp(String input){
            return input.trim( );
        }
    };
}