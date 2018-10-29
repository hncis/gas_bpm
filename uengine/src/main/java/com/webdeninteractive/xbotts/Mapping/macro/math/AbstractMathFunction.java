/*
 * AbstractMathFunction.java
 *
 * Created on August 3, 2004, 5:01 PM
 */

package com.webdeninteractive.xbotts.Mapping.macro.math;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import org.w3c.dom.*;
import java.math.BigDecimal;

/**
 *
 * @author  bmadigan
 */
public abstract class AbstractMathFunction extends AbstractExtensionFunction {
    
    /** Creates a new instance of AbstractMathFunction */
    public AbstractMathFunction() {
    }
    
    /** getDoubleValue function for a Node
     * @param n A unique Node
     * @return The value of the node.
     */
    public double getDoubleValue(Node n){
        if(n instanceof CharacterData){
            return getDoubleValue(n.getNodeValue( ).trim( ));
        }
        if(n==null){
            return 0;
        }
        return getDoubleValue(n.getFirstChild( ));
    }
    
    /** getDoubleValue function for a string
     * @param s A unique String
     * @return The value of the string as a double.
     */
    public double getDoubleValue(String s){
        if(s!=null && !s.equals("")){
            return Double.parseDouble(s.trim( ));
        }
        return 0;
    }
    
    public BigDecimal getBigDecimalValue(Node n){
        if(n instanceof CharacterData){
            return getBigDecimalValue(n.getNodeValue( ).trim( ));
        }
        if(n==null){
            return new BigDecimal(0);
        }
        return getBigDecimalValue(n.getFirstChild( ));
    }
    
    public java.math.BigDecimal getBigDecimalValue(String s){
        if(s==null || "".equals(s)) return new BigDecimal(0);
        return new BigDecimal(s.trim( ));
    }
    
    public org.w3c.dom.NodeList execute(org.w3c.dom.NodeList list1, org.w3c.dom.NodeList list2, MathOperation op)
    throws Exception{
        Element elem=null;
        Document owner= null;
        if(list2!=null){
            if(list1.getLength( )!=list2.getLength( )) throw new Exception("Can't operate on NodeLists of unequal length");
        }
        
        for(int i=0;i<list1.getLength( );i++){
            if(owner==null){
                owner = list1.item(i).getOwnerDocument();
                elem = owner.createElement("tmp");
            }
            BigDecimal xValue = getBigDecimalValue(list1.item(i));
            BigDecimal yValue = getBigDecimalValue(list2.item(i));
           
            elem.appendChild(owner.createTextNode(op.doOp(xValue, yValue).toString()));
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    }
    
    public org.w3c.dom.NodeList execute(org.w3c.dom.NodeList list1, MathOperation op)
    throws Exception{
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list1.getLength( );i++){
            if(owner==null){
                owner = list1.item(i).getOwnerDocument();
                elem = owner.createElement("tmp");
            }
            BigDecimal xValue = getBigDecimalValue(list1.item(i));
           
            elem.appendChild(owner.createTextNode(op.doOp(xValue).toString()));
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    }
}
