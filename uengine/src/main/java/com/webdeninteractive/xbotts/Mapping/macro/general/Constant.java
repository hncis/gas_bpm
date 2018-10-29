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
 * Constant.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/20/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.general;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
/**
 *
 * @author  bmadigan, jdu
 */
public class Constant extends AbstractExtensionFunction {
    
    /** Creates a new instance of Constant 
	 * with the editable parameter value.
	 */
    public Constant() {
	    super.addEditableParameter("value", "", Object.class, "the constant's value");
    }
    
    /** getValue function
	 * @param value The value the user entered.
	 * @return The value.  		    
	 */
    public Object getValue(Object value){
        return value;
    }
}
