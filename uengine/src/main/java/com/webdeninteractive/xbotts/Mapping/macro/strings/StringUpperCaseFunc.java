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
 * StringUpperCaseFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/23/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.strings;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */
public class StringUpperCaseFunc extends AbstractStringFunction {
    
    /** Creates a new instance of StringUpperCaseFunc
     * with the left link parameter str1
     */
    public StringUpperCaseFunc() {
        super.addLinkableParameter("str1");
    }
    
    
    /** toUpperCase function for nodelist.
     * @param list A unique node list.
     * @return The nodelist of the upper case of the string for each node's value.
     */
    public org.w3c.dom.NodeList toUpperCase(org.w3c.dom.NodeList list){
        return super.execute(list, op);
    }
    
    public String toUpperCase(String s){
        return op.doOp(s);
    }
    
    StringOperation op = new StringOperation( ){
        public String doOp(String input){
            return input.toUpperCase();
        }
    };
}
