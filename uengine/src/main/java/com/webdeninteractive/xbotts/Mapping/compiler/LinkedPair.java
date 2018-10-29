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
 * LinkedPair.java
 *
 * Created on October 14, 2002, 3:02 PM
 */

package com.webdeninteractive.xbotts.Mapping.compiler;

/**
 *
 * @author  bmadigan
 */
public class LinkedPair {
    
    /** Creates a new instance of LinkedPair */
    public LinkedPair(String id, Object source, Object target) {
        this.source = source;
        this.target = target;
        this.id=id;
    }
    Object source, target;
    String id;
    public Object getSource( ){
        return source;
    }
    public Object getTarget( ){
        return target;
    }
    public String getId( ){
        return id;
    }
    public String toString(){
        return "LinkedPair: id["+id+"] ["+source+","+target+"]";
    }
}
