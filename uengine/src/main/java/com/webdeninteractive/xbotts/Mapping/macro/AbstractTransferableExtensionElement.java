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
 * TransferableExtensionElement.java
 *
 * Created on September 23, 2002, 1:51 PM
 */

package com.webdeninteractive.xbotts.Mapping.macro;
import java.util.*;
import javax.swing.ImageIcon;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.*;
/**
 *
 * @author  bmadigan
 */
public abstract class AbstractTransferableExtensionElement implements ExtensionElement {
    public void setId (int id){
        this.id=id;
    }
    int id;
    public int getId( ){
        return id;
    }
    
    public void setUI(Component ui){
        this.ui=ui;
    }
    Component ui;
    public Component getUI( ){
        return ui;
    }
    /**  Returns a map of this Extension's arguments.<br>
     *  The keys are the arguments names, the values are arguments' Java types.
     *  The types are used to qualify argument values in the mapping engine.
     */
    public abstract Argument[] getArguments();
    public Argument getArgument(String name){
        Argument[] args = getArguments( );
        for(int i=0;i<args.length;i++){
            if(args[i].getName( ).equals(name))return args[i];
        }
        return null;
    }
    /** Returns a meaningful description of this ExtensionElement. <BR>
     *  it might be used as ToolTip, so it could have HTML formatting if desired.
     */
    public abstract String getDescription();
    
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
    public abstract String getElement();
    
    /** Returns a space separate list containing the names of the <br>
     *  public zero-argument evaluation method(s) to be made available<br>
     *  to the XSLT processor. Each function needs a unique name.
     */
    public abstract Function[] getFunctions();
        
    public Function getFunction(String name) {
        Function[] functions = getFunctions( );
        for(int i=0;i<functions.length;i++){
            if(functions[i].getName().equals(name)) return functions[i];
        }
        return null;
    }
    /** Returns an Icon that somehow represents this Extension.
     *
     */
    public abstract ImageIcon getIcon();
    
    /**  Since this is a Java class, must return the String <i>&quotjavaclass&quot</i>
     */
    public abstract String getLanguage();
    
    /**  Returns a name that will be used to resolve this extension in a stylesheet.
     *
     */
    public abstract String getName();
    
    /**  Should always be implemented as follows:
     *  return getClass( ).getName( );
     *
     */
    public abstract String getSource();
    
    public Object getTransferData(java.awt.datatransfer.DataFlavor dataFlavor) throws java.awt.datatransfer.UnsupportedFlavorException, java.io.IOException {
        if(dataFlavor.equals(EXTENSION_FLAVOR)) return this;
        throw new UnsupportedFlavorException(dataFlavor);
    }
    
    public java.awt.datatransfer.DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }
    
    public boolean isDataFlavorSupported(java.awt.datatransfer.DataFlavor dataFlavor) {
        return dataFlavor.equals(EXTENSION_FLAVOR);
    }

    
}
