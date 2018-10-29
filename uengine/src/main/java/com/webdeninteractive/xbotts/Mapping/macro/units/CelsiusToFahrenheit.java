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
 * CelsiusToFahrenheit.java
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


public class CelsiusToFahrenheit extends AbstractExtensionFunction {
    
    /** Creates a new instance of CelsiusToFahrenheit
     * with the left link parameter celsius
     */
    public CelsiusToFahrenheit() {
        super.addLinkableParameter("celsius");
        super.addEditableParameter("decimalPlaces", "1", String.class, "How many decimal places to round to.");
    }
    
    /** fahrenheit Temperature conversion function.  For back comparable.
     * @param list A unique node list.
     * @return The nodelist of a new number converted to fahrenheit for each node's value.
     */
    public org.w3c.dom.NodeList fahrenheit(org.w3c.dom.NodeList list) throws Exception{
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list.getLength( );i++){
            if(owner==null){
                owner = list.item(i).getOwnerDocument();
                elem = owner.createElement("tmp");
            }
            String celsius = getStringValue(list.item(i));
            if(celsius == null || celsius.equals("")){
                elem.appendChild(owner.createTextNode(""));
            }else{
                elem.appendChild(owner.createTextNode(Double.toString((Double.parseDouble(celsius))*9/5d +32)));
            }
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    }
    
    
    /** fahrenheit Temperature conversion function
     * @param list A unique node list.
     * @param decimalPlaces A unique number for how many decimal places to keep.
     * @return The nodelist of a new number converted to fahrenheit for each node's value.
     */
    public org.w3c.dom.NodeList fahrenheit(org.w3c.dom.NodeList list, double decimalPlaces) throws Exception{
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list.getLength( );i++){
            if(owner==null){
                owner = list.item(i).getOwnerDocument();
                elem = owner.createElement("tmp");
            }
            String celsius = getStringValue(list.item(i));
            if(celsius == null || celsius.equals("")){
                elem.appendChild(owner.createTextNode(""));
            }else{
                double aValue = Double.parseDouble(celsius)*9/5d +32;
                String rounded = roundToPlaces(aValue, decimalPlaces);
                elem.appendChild(owner.createTextNode(rounded));
            }
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    }

}

