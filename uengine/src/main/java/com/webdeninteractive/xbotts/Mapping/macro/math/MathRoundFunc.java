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
 * MathRoundFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, Modified 02/19/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.math;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import java.text.DecimalFormat;
import org.w3c.dom.*;
import java.math.BigDecimal;
/**
 *
 * @author  bmadigan, rewrite jdu
 */

public class MathRoundFunc extends AbstractMathFunction {
    /** Creates a new instance of MathRoundFunc
     * with the left link parameter num1 and a editable parameter decimalPlaces.
     * The num1 and decimalPlaces should be numbers.
     */
    public MathRoundFunc() {
        super.addLinkableParameter("nuber");
        super.addEditableParameter("scale", "0", String.class, "Scale.");
    }
    
    /** round function for nodelist. Round each node value to a specific precision determined by decimalPlaces.
     * @param list A unique node list.
     * @param decimalPlaces A unique number for how many decimal places to keep.
     * @return The nodelist of the round result for each node.
     */
    public org.w3c.dom.NodeList round(org.w3c.dom.NodeList list, final int scale)throws Exception{
       return super.execute(list, new MathOperation( ){
            public java.math.BigDecimal doOp(java.math.BigDecimal x){
                return x.setScale(scale);
            }
        });
    }
    
    
    public String max(String int1, String scale)throws Exception{
        BigDecimal d1 = super.getBigDecimalValue(int1);
        return d1.setScale(Integer.parseInt(scale)).toString();
    }
}




