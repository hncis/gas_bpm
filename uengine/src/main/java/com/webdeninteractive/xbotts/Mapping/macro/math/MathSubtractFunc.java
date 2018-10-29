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
 * MathSubtractFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, rewrite 02/20/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.math;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
import java.math.BigDecimal;
/**
 *
 * @author  bmadigan,jdu
 */
public class MathSubtractFunc extends AbstractMathFunction {
    
    /** Creates a new instance of MathSubtractFunc
     * with the left link parameter num1 and num2.
     * The num1 and num2 should be numbers.
     */
    public MathSubtractFunc() {
        super.addLinkableParameter("num1");
        super.addLinkableParameter("num2");
    }
    
    
    /** subtract function for nodelist.
     * @param list1 A unique node list.
     * @param list2 A unique node list.
     * @return The nodelist of the subtraction result for each node.
     */
    public org.w3c.dom.NodeList subtract(org.w3c.dom.NodeList list1, org.w3c.dom.NodeList list2)throws Exception{
        return super.execute(list1, list2, new MathOperation( ){
            public java.math.BigDecimal doOp(java.math.BigDecimal x, java.math.BigDecimal y){
                return x.subtract(y);
            }
        });
    }
    
    public String subtract(String int1, String int2)throws Exception{
        BigDecimal d1 = super.getBigDecimalValue(int1);
        BigDecimal d2 = super.getBigDecimalValue(int2);
        System.out.println(d1.toString( )+"-"+d2.toString()+"="+d1.subtract(d2).toString ());
        return d1.subtract(d2).toString( );
    }
    
}
