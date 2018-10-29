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
 * MapChangedEvent.java
 *
 * Created on October 10, 2002, 1:35 PM
 */

package com.webdeninteractive.xbotts.Mapping.maptool;
import java.util.EventObject;
/** MapChangedEvent is an EventObject. When the state of the MapToolDataModel
 * changes, these events are fired off to listeners to be handled accordingly.
 * @author  bmadigan
 */
public class MapChangedEvent extends java.util.EventObject{
    
    /** Creates a new instance of MapChangedEvent */
    public MapChangedEvent(Object source) {
        super(source);
    }
}
