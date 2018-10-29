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
 * FIFOList.java
 *
 * Created on January 15, 2002, 12:36 PM
 */

package com.webdeninteractive.xbotts.Utility;

/**
 *  Wrapper to make arraylist behave like a fifo queue
 * @author  bmadigan
 * @version $Id: FIFOQueue.java,v 1.2 2007/12/05 02:31:25 curonide Exp $ 
 */
public class FIFOQueue extends java.util.ArrayList {

    /** Creates new FIFOList */
    public FIFOQueue () {
    }

    public Object push(Object o){
	this.add(o);
	return o;
    }
    
    public Object pull( ){
	return this.remove(0);
    }
    
    public Object peek( ){
	return this.get(0);
    }
    
    public boolean endReached( ){
	return(this.size( )<=1);
    }
}
