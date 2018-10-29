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
 * IfCondition.java
 * The two operands should be parsable double Strings
 * Evaluates <<val1>> <<op>> <<val2 >>, eg, 2 > 1.
 * and return the boolean value as a String object
 */
package com.webdeninteractive.xbotts.Mapping.macro.conditional;

import com.webdeninteractive.xbotts.Mapping.macro.AbstractExtensionFunction;
import com.webdeninteractive.xbotts.Mapping.macro.SelectableParameter;
import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class IfCondition extends AbstractExtensionFunction {
	//list of operators which could be added
	//changes should be made to the evaluate method
	//to handle any new operators
	private String operators = ">,>=,<,<=,==,!=,equals,equalsIgnoreCase";
	private String delim = ",";
	
	public IfCondition( ){
    	ArrayList al = new ArrayList( );
		StringTokenizer st = new StringTokenizer( operators, delim );
		while( st.hasMoreElements( ) ){
			al.add( st.nextToken( ) );
		}
		
		super.addLinkableParameter( "val1" );
		super.addLinkableParameter( "val2" );
		super.addEditableParameter(new SelectableParameter(
			"operator", ( String )al.get( 0 ) , al, String.class, 
			"Operators", this)
		);
	}
		
	//op1 and op2 should contain a parsable double
	public static String evaluateExpression( 
	    String val1, String val2, String operator) throws NumberFormatException
	{   
		String retVal;
		boolean bool = false;
		retVal = Boolean.toString( bool );
		double operand1;
		double operand2;
		//if it about comparing string
		if ( operator.equalsIgnoreCase( "equals" ) ){
			bool = val1.equals( val2 );
			retVal = Boolean.toString( bool );
		} else if (operator.equalsIgnoreCase( "equalsIgnoreCase" ) ){
			bool = val1.equalsIgnoreCase( val2 );
			retVal = Boolean.toString( bool );
		} else {
			operand1 = Double.parseDouble( val1 );
			operand2 = Double.parseDouble( val2 );
			if ( operator.equalsIgnoreCase( ">" ) ){
				bool = ( operand1 > operand2 );
				retVal = Boolean.toString( bool );
			} else if ( operator.equalsIgnoreCase( ">=" ) ){
				bool = ( operand1 >= operand2 );
				retVal = Boolean.toString( bool );
			} else if ( operator.equalsIgnoreCase( "<" ) ){
				bool = ( operand1 < operand2 );
				retVal = Boolean.toString( bool );
			} else if ( operator.equalsIgnoreCase( "<=" ) ){
				bool = ( operand1 <= operand2 );
				retVal = Boolean.toString( bool );
			} else if ( operator.equalsIgnoreCase( "==" ) ){
				bool = ( operand1 == operand2 );
				retVal = Boolean.toString( bool );
			} else if ( operator.equalsIgnoreCase( "!=" ) ){
				bool = ( operand1 != operand2 );
				retVal = Boolean.toString( bool );
			}
		}
		return retVal;
	}
}
