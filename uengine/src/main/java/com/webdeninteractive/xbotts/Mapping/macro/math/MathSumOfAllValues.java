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
 * MathSumOfAllValues.java
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
 * @author  bmadigan, jdu
 */
public class MathSumOfAllValues extends AbstractMathFunction {
    
    /** Creates a new instance of MathSumOfAllValues
     * with the left link parameter nodeList
     * The nodeList should be org.w3c.dom.NodeList.
     */
    public MathSumOfAllValues() {
        super.addLinkableParameter("nodeList");
    }
    
    /** sum function
     * @param nodes A unique NodeList
     * @return The sum of the numbers in the nodes.
     */
    public String sum(org.w3c.dom.NodeList nodes){
        BigDecimal sum = new BigDecimal(0);
        
        for(int i=0;i<nodes.getLength( );i++){
            BigDecimal bd = getBigDecimalValue(nodes.item(i));
           
            sum = sum.add(bd);
        }
        return sum.toString( );
    }
}
