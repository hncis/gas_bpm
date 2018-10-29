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
 * StringSubstringFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/20/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.strings;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */
public class StringSubstringFunc extends AbstractStringFunction {
    
    /** Creates a new instance of StringSubstringFunc
     * with the left link parameter string and editable parameter start and end
     */
    public StringSubstringFunc() {
        super.addLinkableParameter("string");
        super.addEditableParameter("start", "0", String.class, "The start position of the substring");
        super.addEditableParameter("end", "0", String.class, "The end position of the substring");
    }
    
    
    
    /** substring function for nodelist.
     * @param list A unique node list.
     * @param start The startting index.
     * @param end The end index.
     * @return The nodelist of a new string that is a substring of the string for each node's value.
     */
    public org.w3c.dom.NodeList substring(org.w3c.dom.NodeList list, final int start, final int end){
        return super.execute(list, new StringOperation( ){
            public String doOp(String s){
                return substring(s, start,end);
            }
        });
    }
    
    public String substring(String s, int start, int end){
        if(s==null||s.equals("")) return s;
        if(start<0) start = 0;
        if(start>s.length( )-1) start = s.length( )-1;
        if(end<0) end = 0;
        if(end>s.length( )-1) end = s.length( )-1;
        if(start>=end) return "";
        return s.substring(start, end);
    }
    
}
