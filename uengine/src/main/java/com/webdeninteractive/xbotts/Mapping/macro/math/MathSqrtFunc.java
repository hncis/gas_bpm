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
 * MathSqrtFunc.java
 *
 * Created on October 10, 2002, 11:47 AM, rewrite 02/20/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.math;
import  com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan, jdu
 */
public class MathSqrtFunc extends AbstractMathFunction {
    
    /** Creates a new instance of MathSqrtFunc 
	 * with the left link parameter num1
	 * The num1 should be number.
	 */
    public MathSqrtFunc() {
	     super.addLinkableParameter("num1");
    }
    
		
   /** squareRoot function for nodelist.
    * @param list A unique node list.
    * @return The nodelist of the squareRoot for each node.  		    
    */ 
     public org.w3c.dom.NodeList squareRoot(org.w3c.dom.NodeList list)throws Exception{
         return super.execute(list, new MathOperation( ){
            public java.math.BigDecimal doOp(java.math.BigDecimal x){
                //this is cheating. Bypasses any real work it would take to handle BIG numbers
                return new java.math.BigDecimal(Math.sqrt(x.doubleValue( )));
            }
        });
     }
     
     
    public String squareRoot(String s)throws Exception{
        return new java.math.BigDecimal(Math.sqrt(Double.parseDouble(s))).toString( );
    }
}
