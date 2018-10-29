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
 * Argument.java
 *
 * Created on September 23, 2002, 2:46 PM
 */

package com.webdeninteractive.xbotts.Mapping.macro;
import com.webdeninteractive.xbotts.Mapping.compiler.*;
/**
 *
 * @author  bmadigan
 */
public class Argument {
    
    /** Creates a new instance of Argument */
    public Argument(String name, Class type, ExtensionElement owner) {
        this.name=name;
        this.type=type;
        this.ownerExtension=owner;
    }
    String name;
    public String getName( ){
        return name;
    }
    public Class getTypeClass( ){
        return type;
    }
    Class type;
    public ExtensionElement getOwnerExtension( ){
        return ownerExtension;
    }
    
    Object value;
    /**
     * Supports user final value creation.
     */
    public void setValue(Object value){
        this.value=value;
    }
    public Object getValue( ){
        return value;
    }
    
    Linkable source;
    /**
     * Supports argument that gets its value from a Linkable source.
     */
    public void setSourceLinkable(Linkable source){
        this.source = source;
    }
    public Linkable getSourceLinkable( ){
        return source;
    }
    Function sourceFunction;
    /**
     * Supports argument that gets its value from the calling of a function on 
     * another extension element.
     */
    public void setChainedFunctionSource(Function function){
        this.sourceFunction=function;
    }
    public Function getChainedFunctionSource( ){
        return sourceFunction;
    } 
    
    ExtensionElement ownerExtension;
}
