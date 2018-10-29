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
 * MathDivideByFunc.java
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
 * @author  bmadigan, rewrite jdu
 */
public class MathDivideByFunc extends AbstractMathFunction {
    
    /** Creates a new instance of MathDivideByFunc
     * with the left link parameter value and a editable parameter divideBy.
     * The value and divideBy should be numbers.
     */
    public MathDivideByFunc() {
        super.addLinkableParameter("value");
        super.addEditableParameter("divideBy", "100", String.class, "the number to divide by");
    }
    
    
    
    /** divideBy function for nodelist.
     * @param list A unique node list.
     * @param divideBy A unique number.
     * @return The nodelist of the divided by result for each node.
     */
    public org.w3c.dom.NodeList divideBy(org.w3c.dom.NodeList list, final String divideBy)throws Exception{
        return super.execute(list, new MathOperation( ){
            public BigDecimal doOp(BigDecimal x){
                return x.divide(new BigDecimal(divideBy),BigDecimal.ROUND_HALF_EVEN);
            }
        });
    }
    
    public String divideBy(String int1, String int2)throws Exception{
        BigDecimal d1 = super.getBigDecimalValue(int1);
        BigDecimal d2 = super.getBigDecimalValue(int2);
        return d1.divide(d2, BigDecimal.ROUND_HALF_EVEN).toString( );
    }
}