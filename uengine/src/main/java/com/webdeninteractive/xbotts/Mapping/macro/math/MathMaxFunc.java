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
 * MathMaxFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, rewrite 02/19/2004
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
public class MathMaxFunc extends AbstractMathFunction {
    
    /** Creates a new instance of MathMaxFunc
     * with the left link parameter num1 and num2.
     * The num1 and num2 should be numbers.
     */
    public MathMaxFunc() {
        super.addLinkableParameter("num1");
        super.addLinkableParameter("num2");
    }
    
    
    
    /** max function for nodelist.
     * @param list1 A unique node list.
     * @param list2 A unique node list.
     * @return The nodelist of the maximum number for each node's value.
     */
    public org.w3c.dom.NodeList max(org.w3c.dom.NodeList list1, org.w3c.dom.NodeList list2)throws Exception{
       return super.execute(list1, list2, new MathOperation( ){
          public BigDecimal doOp(BigDecimal x, BigDecimal y){
              return x.max(y);
          }
       });
    }
    
    public String max(String int1, String int2)throws Exception{
        BigDecimal d1 = super.getBigDecimalValue(int1);
        BigDecimal d2 = super.getBigDecimalValue(int2);
        return d1.max(d2).toString();
    }
}
