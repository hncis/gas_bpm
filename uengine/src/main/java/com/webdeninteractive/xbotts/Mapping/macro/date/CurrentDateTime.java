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
 * CurrentDateTime.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/20/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.date;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import java.util.*;
import java.util.Date;
import java.text.*;


/**
 *
 * @author  bmadigan, jdu
 */
public class CurrentDateTime extends AbstractExtensionFunction {
    
    /** Creates a new instance of CurrentDateTime 
	 * with editable parameter date format.
	 */
    public CurrentDateTime() {
	    super.addEditableParameter("date format", "\"yyyy-MM-dd\"", String.class, "Output date format");	
    }
  
    /** currentDate function for a date format 
	 * @param format The date format string.
	 * @return current date with the specified date format.  	
	 */
    public String currentDate(String format) throws Exception{
       Calendar c = Calendar.getInstance( );
       Date today = c.getTime( );
       SimpleDateFormat sdf = new SimpleDateFormat(format);
       return sdf.format(today);
    }
}
