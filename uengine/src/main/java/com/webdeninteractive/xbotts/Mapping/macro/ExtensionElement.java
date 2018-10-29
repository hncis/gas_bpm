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
 * ExtensionElement.java
 *
 * Created on September 19, 2002, 1:26 PM
 */

package com.webdeninteractive.xbotts.Mapping.macro;
import java.util.*;
import javax.swing.ImageIcon;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.*;
/** Interface that all 'Macro' candidates must implement.<BR>
 *  Methods defined in this interface are used by the Mapping engine to create <BR>
 *  Macro components and add them to a map for evaluation at runtime.<BR>
 *  The method called to run the macro is resolved through reflection in the<BR>
 *  XSLT Processor engine (in this case, Xalan), so any method signatures for <BR>
 *  eval functions are omitted from this interface.
 * @author  bmadigan
 */
public interface ExtensionElement extends java.awt.datatransfer.Transferable{
    public void setId(int id);
    public int getId( );
    public void setUI(Component ui);
    public Component getUI( );
    /**
     *  Returns a map of this Extension's arguments.<br>
     *  The keys are the arguments names, the values are arguments' Java types.
     *  The types are used to qualify argument values in the mapping engine.
     */
    public Argument[] getArguments( );
    public Argument getArgument(String name);
    /**
     *  Returns a name that will be used to resolve this extension in a stylesheet.
     *
     */
    public String getName( );
    /** Returns a space separated list containing the names of the <br>
     *  element method(s) defined here. Element methods must follow<br>
     *  the prototype: <br>
     *  <i>
     *  Type element(org.apache.xalan.extensions.XSLProcessorContext,<br>
     *        org.apache.xalan.templates.ElemExtensionCall extensionElement)</i>
     *  Each element method needs a unique name,
     *  or there will be problems. Better to only have one Element method
     *  per Extension.
     */
    public String getElement( );
    /** Returns a space separate list containing the names of the <br>
     *  public zero-argument evaluation method(s) to be made available<br>
     *  to the XSLT processor. Each function needs a unique name.
     */
    public Function[] getFunctions( );
    public Function getFunction(String name);
    /**
     *  Since this is a Java class, must return the String <i>&quotjavaclass&quot</i>
     */
    public String getLanguage( );
    /**
     *  Should always be implemented as follows:
     *  return getClass( ).getName( );
     *
     */
    public String getSource( );
    /** Returns a meaningful description of this ExtensionElement. <BR>
     *  it might be used as ToolTip, so it could have HTML formatting if desired.
     */
    public String getDescription( );
    
    /** Returns an Icon that somehow represents this Extension.
     *  
     */
    public ImageIcon getIcon( );
    
    public Object getTransferData(java.awt.datatransfer.DataFlavor dataFlavor) throws java.awt.datatransfer.UnsupportedFlavorException, java.io.IOException;
    
    public java.awt.datatransfer.DataFlavor[] getTransferDataFlavors();
    
    public boolean isDataFlavorSupported(java.awt.datatransfer.DataFlavor dataFlavor);
    
    final public static DataFlavor EXTENSION_FLAVOR =
    new DataFlavor(ExtensionElement.class, "ExtensionElement");
    static DataFlavor flavors[] = {EXTENSION_FLAVOR};
    
}
