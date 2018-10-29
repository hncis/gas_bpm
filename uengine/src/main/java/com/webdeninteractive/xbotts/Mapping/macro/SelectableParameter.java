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
 * EditableParameter.java
 *
 * Created on October 25, 2002, 2:30 PM
 */

package com.webdeninteractive.xbotts.Mapping.macro;
import java.util.List;
/**
 *
 * @author  bmadigan
 */
public class SelectableParameter extends MutableParameter {

    public SelectableParameter(String name, Object value, List values, Class type, String description, ExtensionFunction owner) {
        super(name,value, type, description, owner);
        this.values=values;
    }
    List values;
    
    public void setValues(List values){
        this.values=values;
    }
    public List getValues( ){
        return values;
    }
}
