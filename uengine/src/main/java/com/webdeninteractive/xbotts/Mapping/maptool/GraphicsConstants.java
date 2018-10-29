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
 * GraphicsConstants.java
 *
 * Created on December 12, 2003, 11:50 AM
 */

package com.webdeninteractive.xbotts.Mapping.maptool;
import com.webdeninteractive.xbotts.Mapping.compiler.Linkable;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author  bmadigan
 */
public class GraphicsConstants {
    
    /** Creates a new instance of GraphicsConstants */
    public GraphicsConstants() {
    }
    
    public static final BasicStroke NORMAL_STROKE = new BasicStroke(0.5f);
    public static final BasicStroke SELECTED_STROKE = new BasicStroke(2.0f);
    public static final Color DEFAULT_LINK_COLOR = Color.black;
    public static final Color ATOMIC_LINK_COLOR = Color.gray;
    public static final Color CONTEXT_LINK_COLOR = Color.blue;
    
    public static Color getLinkColor(int linkType){
        if(linkType == Linkable.CONTEXT_LINK) return CONTEXT_LINK_COLOR;
        if(linkType == Linkable.ATOMIC_LINK) return ATOMIC_LINK_COLOR;
        return DEFAULT_LINK_COLOR;
    }
}
