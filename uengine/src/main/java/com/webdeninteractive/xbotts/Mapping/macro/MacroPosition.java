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
 * MacroPosition.java
 *
 * Created on May 9, 2003, 2:53 PM
 */

package com.webdeninteractive.xbotts.Mapping.macro;

/**
 *
 * @author  bmadigan
 */
public interface MacroPosition {
    /*Methods implemented by AbstractExtensionFunction.*/
    void setX(int x);
    void setY(int y);
    int getX( );
    int getY( );
}
