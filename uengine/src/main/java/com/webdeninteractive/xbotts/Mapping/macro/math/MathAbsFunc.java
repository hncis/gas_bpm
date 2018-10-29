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
 * MathAbsFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, Modified 02/19/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.math;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
/**
 *
 * @author bmadigan, jdu
 */
public class MathAbsFunc extends AbstractMathFunction {
    
    /** Creates a new instance of MathAbsFunc
     * with the left link parameter number.
     * number should be a number
     */
    public MathAbsFunc() {
        super.addLinkableParameter("number");
    }
    
    /** absoluteValue function for nodelist.
     * @param list A unique node list.
     * @return The nodelist of the absoluteValue for each node.
     */
    public org.w3c.dom.NodeList absoluteValue(org.w3c.dom.NodeList list)throws Exception{
         return super.execute(list, new MathOperation( ){
          public java.math.BigDecimal doOp(java.math.BigDecimal x){
              return x.abs( );
          }
       });
    }
    
    public String absoluteValue(String num){
        java.math.BigDecimal b = super.getBigDecimalValue(num);
        return b.abs().toString ();
    }
}