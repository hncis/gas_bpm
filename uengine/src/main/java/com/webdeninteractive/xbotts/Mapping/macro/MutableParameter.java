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
import javax.swing.*;
import javax.swing.table.TableCellEditor;
/**
 *
 * @author  bmadigan
 */
public class MutableParameter extends Parameter {

    public MutableParameter(String name, 
    Object value, 
    Class type, 
    String description, 
    ExtensionFunction owner) {
        this(name,value,type,description, null, owner);
    }
    
    public MutableParameter(String name, 
    Object value, 
    Class type, 
    String description, 
    TableCellEditor cellEditor, 
    ExtensionFunction owner) {
        super(name, type, description, owner);
        this.value=value;
        this.cellEditor=cellEditor;
    }
    Object value;
    public void setValue( Object value ){
        this.value=value;
    }
    
    public Object getValue( ){
        return value;
    }
    
    TableCellEditor cellEditor;
    /** @deprecated */
    public TableCellEditor getCellEditorComponent( ){
        return cellEditor;
    }
    
    /** @deprecated */
    public void setCellEditorComponent(TableCellEditor cellEditor){
        this.cellEditor = cellEditor;
    }
}
