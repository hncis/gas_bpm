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
 * Parameter.java
 *
 * Created on October 10, 2002, 10:38 AM
 */

package com.webdeninteractive.xbotts.Mapping.macro;
import com.webdeninteractive.xbotts.Mapping.compiler.Linkable;
/**
 * A parameter to an extension function call.
 * @author  bmadigan
 */
public class Parameter {

    public Parameter(String name, Class type, String description, ExtensionFunction owner) {
        this.name=name;
        this.type=type;
        this.description = description;
        this.func = owner;
    }
    public Parameter(String name, Class type, ExtensionFunction owner) {
        this(name,  type, "NA", owner);
    }
    String name;
    Class type;
    String description;
    
    public String getName( ){
        return name;
    }
    public Class getType( ){
        return type;
    }
    ExtensionFunction func;
    /**
     * Returns the function instance this parameter is passed to.
     */
    public ExtensionFunction getExtensionFunction( ){
        return func;
    }
    
    public String getDescription( ){
        return description;
    }
    
    /** Allows local reference to linkable source.
     * Bad. This is bad.
     */
    Linkable linkSource;
    public Linkable getLinkSource( ){
        return linkSource;
    }
    public void setLinkSource(Linkable l){
        this.linkSource = l;
    }
    
    ExtensionFunction extensionSource;
    public ExtensionFunction getExtensionSource( ){
        return extensionSource;
    }
    public void setExtensionSource(ExtensionFunction extension ){
        this.extensionSource = extension;
    }
}
