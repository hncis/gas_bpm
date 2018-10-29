/*
-------------------------------------------------------------------
BIE is Copyright 2001-2004 Brunswick Corp.
-------------------------------------------------------------------
Please read the legal notices (docs/legal.txt) and the license
(docs/bie_license.txt) that came with this distribution before using
this software.
-------------------------------------------------------------------
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
 *
 * Macro to run a regular expression on a string.  The input an result are node lists, this macro can handle multiple inputs and outputs
 */
public class RegExFunc extends AbstractStringFunction {
    
    /** Creates a new instance of StringConcatFunc
     * with the left link parameter str1 and str2.
     */
    public RegExFunc() {
        super.addLinkableParameter("str1");
        super.addEditableParameter("Regular Expression", "", String.class, "Enter regular expression");
    }
    
    
    /** concat function for nodelist.
     * @param list1 A unique node list.
     * @param list2 A unique node list.
     * @return The nodelist of the concated string for each node's value.
     */
    public org.w3c.dom.NodeList regEx(org.w3c.dom.NodeList list1, final String regex)throws Exception{
        return super.execute(list1, new CompoundStringOperation( ){
            public String[] doOp(String input){
                return regEx(input, regex);
            }
        }
        );
    }
    
    public String[] regEx(String input, String regex){
        Matcher matcher = Pattern.compile(regex).matcher(input);
        matcher.find();
        
        String[] groups = new String[matcher.groupCount( )];
        for (int i=0;i<=matcher.groupCount();i++) {
            groups[i] = matcher.group(i);
        }
        return groups;
    }
    
}
