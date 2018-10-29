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
 * StringConcatFunc.java
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
public class StringConcatFunc extends AbstractStringFunction {
    
    /** Creates a new instance of StringConcatFunc
     * with the left link parameter str1 and str2.
     */
    public StringConcatFunc() {
        super.addLinkableParameter("str1");
        super.addLinkableParameter("str2");
    }
    
    
    /** concat function for nodelist.
     * @param list1 A unique node list.
     * @param list2 A unique node list.
     * @return The nodelist of the concated string for each node's value.
     */
    public org.w3c.dom.NodeList concat(org.w3c.dom.NodeList list1, org.w3c.dom.NodeList list2)throws Exception{
        return super.execute(list1, list2, op);
    }
    
    public String concat(String s1, String s2){
        return op.doOp(s1, s2);
    }
    
    ComplexStringOperation op = new ComplexStringOperation(){
        public String doOp(String s1, String s2){
            return s1+s2;
        }
    };
}
