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
 * MacroFunction.java
 *
 * Created on October 10, 2002, 10:41 AM
 */

package com.webdeninteractive.xbotts.Mapping.macro;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import javax.swing.ImageIcon;
import java.awt.Component;
/**
 *
 * @author  bmadigan
 */
public interface ExtensionFunction extends java.awt.datatransfer.Transferable, MacroUI, MacroPosition, MacroIdentity {
    /**
     * The macro function's name.
     *
     */
    String getName( );
    /**
     * Gets an array of parameters which take link inputs. (link parameters)
     */
    Parameter[] getParameters( );
    /**
     * Gets an array of user editable parameters.
     *
     */
    public MutableParameter[] getMutableParameters( );
    /**
     * Gets a named Parameter.
     *
     */
    Parameter getParameter(String name);
    /**
     * Gets the return data type of the macro's method.
     */
    Class getReturnType( );
    /**
     * Gets an icon that represents this extension function.
     *
     */
    ImageIcon getIcon( );
    /**
     * Returns the implementing language.
     * for java, should be 'javaclass'
     */
    String getLanguage( );
    
    /**
     * In java macros, returns getClass( ).getName( );
     */
    String getSource( );
    /** Returns a meaningful description of this ExtensionElement. <BR>
     *  it might be used as ToolTip, so it could have HTML formatting if desired.
     */
    String getDescription( );
    
    
    /** Determines if this function can be instantiated or if methods are static.
     *  Most functions are static unless they need to retain information across multiple
     *  calls.
     */
    public boolean isStatic( );
    
    final public static DataFlavor EXTENSION_FUNCTION_FLAVOR =
    new DataFlavor(ExtensionFunction.class, "ExtensionFunction");
    static DataFlavor flavors[] = {EXTENSION_FUNCTION_FLAVOR};
}
