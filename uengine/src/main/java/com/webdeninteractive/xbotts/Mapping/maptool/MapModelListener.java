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
 * MapModelListener.java
 *
 * Created on October 10, 2002, 1:34 PM
 */

package com.webdeninteractive.xbotts.Mapping.maptool;

/**
 *
 * @author  bmadigan
 */
public interface MapModelListener {
    void extensionFunctionAdded(MapChangedEvent event);
    void extensionFunctionRemoved(MapChangedEvent event);
    void linkAdded(MapChangedEvent event);
    void linkRemoved(MapChangedEvent event);
    void contextMappingAdded(MapChangedEvent event);
    void contextMappingRemoved(MapChangedEvent event);
}
