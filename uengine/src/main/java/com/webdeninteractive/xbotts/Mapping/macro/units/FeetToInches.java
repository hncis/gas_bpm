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
 * FeetToInches.java
 *
 * Created on February 20, 2003, 1:39 PM, 02/23/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.units;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
import java.text.DecimalFormat;
/**
 *
 * @author  mlee, jdu
 */
public class FeetToInches extends AbstractExtensionFunction {
    
    /** Creates a new instance of FeetToInches
     * with the left link parameter feet
     */
    public FeetToInches() {
        super.addLinkableParameter("feet");
    }
    
    /** getInches length conversion function
     * @param list A unique node list of numbers in feet.
     * @return The nodelist of a new number converted to inch for each node.
     */
    public org.w3c.dom.NodeList getInches(org.w3c.dom.NodeList list){
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list.getLength( );i++){
            if(owner==null){
                owner = list.item(i).getOwnerDocument();
                elem = owner.createElement("tmp");
            }
            String aValue = getStringValue(list.item(i));
            if(aValue==null||aValue.equals("")){
                elem.appendChild(owner.createTextNode("0"));
            }else{
                int inches = 0;
                int feet = 0;
                int total=0;
                aValue=aValue.replaceAll(",","");
                if(aValue.indexOf("'")!=-1){
                    feet = Integer.parseInt(aValue.substring(0, aValue.indexOf("'")).trim( ));
                    total = feet*12;
                    if(aValue.indexOf("\"")!=-1){
                        inches = Integer.parseInt(aValue.substring(aValue.indexOf("'")+1, aValue.indexOf("\"")).trim( ));
                        total = total+inches;
                    }
                    elem.appendChild(owner.createTextNode(Integer.toString(total)));
                }else if(aValue.indexOf("\"")!=-1){
                    total = Integer.parseInt(aValue.substring(0, aValue.indexOf("\"")).trim( ));
                    elem.appendChild(owner.createTextNode(Integer.toString(total)));
                }else{
                    feet=Integer.parseInt(aValue.trim( ));
                    elem.appendChild(owner.createTextNode(Integer.toString(feet*12)));
                }
            }
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    }
    
    
}