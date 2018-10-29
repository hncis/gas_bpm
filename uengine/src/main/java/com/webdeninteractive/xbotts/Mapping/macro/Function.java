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
 * Function.java
 *
 * Created on September 23, 2002, 2:46 PM
 */

package com.webdeninteractive.xbotts.Mapping.macro;
import com.webdeninteractive.xbotts.Mapping.compiler.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
/**
 *
 * @author  bmadigan
 */
public class Function implements java.awt.datatransfer.Transferable{
    
    /** Creates a new instance of Function */
    public Function(String name, Class returnType, ExtensionElement owner) {
        this.name=name;
        this.returnType=returnType;
        this.ownerExtension=owner;
    }
    String name;
    public String getName( ){
        return name;
    }
    public Class getReturnType( ){
        return returnType;
    }
    Class returnType;
    public ExtensionElement getOwnerExtension( ){
        return ownerExtension;
    }
        Linkable target;
    /**
     * Supports argument that gets its value from a Linkable source.
     */
    public void setTargetLinkable(Linkable target){
        this.target = target;
    }
    public Linkable getTargetLinkable( ){
        return target;
    }
    
    public Object getTransferData(java.awt.datatransfer.DataFlavor dataFlavor) 
    throws java.awt.datatransfer.UnsupportedFlavorException, java.io.IOException{
        if(isDataFlavorSupported(dataFlavor)){
            return this;
        }
        throw new UnsupportedFlavorException(dataFlavor);
    }
    
    public java.awt.datatransfer.DataFlavor[] getTransferDataFlavors(){
        return flavors;
    }
    
    public boolean isDataFlavorSupported(java.awt.datatransfer.DataFlavor dataFlavor){
        return dataFlavor.equals(FUNCTION_FLAVOR);
    }
    
    final public static DataFlavor FUNCTION_FLAVOR =
    new DataFlavor(Function.class, "Function");
    static DataFlavor flavors[] = {FUNCTION_FLAVOR};
    
    ExtensionElement ownerExtension;
}
