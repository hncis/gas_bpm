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
 * PrimitiveHashMap.java
 *
 * Created on January 29, 2002, 9:56 AM
 */

package com.webdeninteractive.xbotts.Utility;
import java.util.HashMap;
/** Stores hashtable of String=primitive pairs
 *
 * @author  bmadigan
 * @version $Id: IntHashMap.java,v 1.2 2007/12/05 02:31:25 curonide Exp $ 
 */
public class IntHashMap extends java.util.HashMap {

    /** Creates new IntHashMap */
    public IntHashMap () {
    }
    
    public int put(String name, int value){
	Integer i = new Integer(value);
	super.put(name, i);
	return value;
    }
    
    public int get(String name){
	Integer i = (Integer) super.get(name);
	return i.intValue();
    }
}
