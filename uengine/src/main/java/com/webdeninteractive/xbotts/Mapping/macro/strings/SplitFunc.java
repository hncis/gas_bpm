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
import com.webdeninteractive.xbotts.Utility.*;

import javax.xml.parsers.*;
import org.w3c.dom.traversal.*;

import java.util.regex.*;
/**
 *
 * @author  jdepons
 * Splits a string based on a regualar expression.  The macro uses node lists as input and output, so it can handle multiple inputs and outputs.
 */
public class SplitFunc extends AbstractStringFunction {
    
    /** Creates a new instance of StringConcatFunc
     * with the left link parameter str1 and str2.
     */
    public SplitFunc() {
        super.addLinkableParameter("str1");
        super.addEditableParameter("Regular Expression", "", String.class, "Enter a regular expression to split on");
        
    }
    
    /** concat function for nodelist.
     * @param list1 A unique node list.
     * @param list2 A unique node list.
     * @return The nodelist of the concated string for each node's value.
     */
    public org.w3c.dom.NodeList split(org.w3c.dom.NodeList list1, final String regex)throws Exception{                                        
        return super.execute(list1, new CompoundStringOperation(){
                public String[] doOp(String input){
                    return split(input, regex);
                }
            }
        );
     }
    
    public String[] split(String input,String regex){
        return input.split(regex);
    }
    
     
    
}
