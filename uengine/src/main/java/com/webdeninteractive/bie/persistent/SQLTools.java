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
 * SQLTools.java
 *
 * Created on March 7, 2003, 4:27 PM
 */

package com.webdeninteractive.bie.persistent;

/**
 * Utility class for sql queries
 * @author  jd
 */
public class SQLTools {
        
    /** escape ' from a string for insertion into a database*/
    public static String escape(String s) {
        String retvalue = s;
        if ( s.indexOf("'") != -1 ) {
            StringBuffer hold = new StringBuffer();
            char c;
            for ( int i = 0; i < s.length(); i++ ) {
                if ( (c=s.charAt(i)) == '\'' )
                    hold.append("''");
                else
                    hold.append(c);
            }
            retvalue = hold.toString();
        }
        return retvalue;
    }
    
}
