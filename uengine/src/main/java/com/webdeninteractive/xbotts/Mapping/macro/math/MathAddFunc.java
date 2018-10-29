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
 * MathAddFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, modified 02/19/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.math;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
import java.math.BigDecimal;
/**
 *
 * @author  bmadigan, jdu
 */
public class MathAddFunc extends AbstractMathFunction {
    
    /** Creates a new instance of MathAddFunc
     * with the left link parameter num1 and num2.
     * The num1 and num2 should be numbers.
     */
    
    public MathAddFunc() {
        super.addLinkableParameter("num1");
        super.addLinkableParameter("num2");
    }
    
    
    
    /** add function for nodelist.
     * @param list1 A unique node list of numbers.
     * @param list2 A unique node list of numbers.
     * @return The nodelist of the addition result for each node.
     */
    public org.w3c.dom.NodeList add(org.w3c.dom.NodeList list1, org.w3c.dom.NodeList list2)throws Exception{
        return super.execute(list1, list2, new MathOperation( ){
            public java.math.BigDecimal doOp(java.math.BigDecimal x, java.math.BigDecimal y){
                return x.add(y);
            }
        });
    }
    
    public String add(String int1, String int2)throws Exception{
        BigDecimal d1 = super.getBigDecimalValue(int1);
        BigDecimal d2 = super.getBigDecimalValue(int2);
        return d1.add(d2).toString( );
    }
    
}
