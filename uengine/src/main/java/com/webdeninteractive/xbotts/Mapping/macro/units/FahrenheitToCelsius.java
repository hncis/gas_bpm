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
 * FahrenheitToCelsius.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/23/2004
 */
package com.webdeninteractive.xbotts.Mapping.macro.units;

import com.webdeninteractive.xbotts.Mapping.macro.*;
import org.w3c.dom.*;
import java.text.DecimalFormat;

/**
 *
 * @author jtsmith, jdu
 */


public class FahrenheitToCelsius extends AbstractExtensionFunction {
    
    /** Creates a new instance of FahrenheitToCelsius
     * with the left link parameter fahrenheit
     */
    public FahrenheitToCelsius() {
        super.addLinkableParameter("fahrenheit");
        super.addEditableParameter("decimalPlaces", "1", String.class, "How many decimal places to round to.");
    }
    
    /** celsius Temperature conversion function.  For back comparable.
     * @param list A unique node list.
     * @return The nodelist of a new number converted to celsius for each node's value.
     */
    public org.w3c.dom.NodeList celsius(org.w3c.dom.NodeList list) throws Exception{
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list.getLength( );i++){
            if(owner==null){
                owner = list.item(i).getOwnerDocument();
                elem = owner.createElement("tmp");
            }
            String aValue = getStringValue(list.item(i));
            if(aValue == null || aValue.equals("")){
                elem.appendChild(owner.createTextNode(""));
            }else{
                elem.appendChild(owner.createTextNode(Double.toString( (Double.parseDouble(aValue)-32)*5/9d)));
            }
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    }
    
    
    /** celsius Temperature conversion function
     * @param list A unique node list of numbers in fahrenheit.
     * @param decimalPlaces A unique number for how many decimal places to keep.
     * @return The nodelist of a new number converted to celsius for each node.
     */
    public org.w3c.dom.NodeList celsius(org.w3c.dom.NodeList list, double decimalPlaces) throws Exception{
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list.getLength( );i++){
            if(owner==null){
                owner = list.item(i).getOwnerDocument();
                elem = owner.createElement("tmp");
            }
            String aValue = getStringValue(list.item(i));
            if(aValue == null || aValue.equals("")){
                elem.appendChild(owner.createTextNode(""));
            }else{
                double f = (Double.parseDouble(aValue)-32)*5/9d;
                elem.appendChild(owner.createTextNode(roundToPlaces(f, decimalPlaces )));
            }
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    }

    
}

