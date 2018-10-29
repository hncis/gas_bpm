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
 * StringReplaceFunc.java
 *
 * Created on November, 2003, 11:47 AM, 02/20/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.strings;

import com.webdeninteractive.xbotts.Mapping.macro.*;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */

public class StringReplaceFunc extends AbstractStringFunction {
    
    /** Creates a new instance of StringReplaceFunc
     * with the left link parameter str1 and editable parameter
     * regex and replacement
     */
    public StringReplaceFunc(){
        super.addLinkableParameter("str1");
        super.addEditableParameter("regex", "", String.class, "A regular expression to search for");
        super.addEditableParameter("replacement", "", String.class, "The substitution string");
    }
    
    /** replaceAll function for nodelist.
     * @param list A unique node list.
     * @param regex An unique regular expresion value.
     * @param replacement An unique String value to be replaced with.
     * @return The nodelist of the replaced string for each node's value.
     */
    public org.w3c.dom.NodeList replaceAll(org.w3c.dom.NodeList list, final String regex, final String replacement){
        return super.execute(list, new StringOperation( ){
            public String doOp(String input){
                return replaceAll(input, regex, replacement);
            }
        });
    }
    
    public String replaceAll(String input, String regex, String replacement){
        return input.replaceAll(regex, replacement);
    }
    
}