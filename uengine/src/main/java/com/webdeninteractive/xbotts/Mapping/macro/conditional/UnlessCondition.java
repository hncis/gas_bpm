/*
-------------------------------------------------------------------
BIE is Copyright 2001-2004 Brunswick Corp.
-------------------------------------------------------------------
Please read the legal notices (docs/legal.txt) and the license
(docs/bie_license.txt) that came with this distribution before using
this software.
-------------------------------------------------------------------
*/
/* UnlessCondition.java
 * User defined the boolean (Target) to be true or false
 * Macro will return val1 if the input boolVal equal to boolean
 * else it will return an empty string
 *
 */
package com.webdeninteractive.xbotts.Mapping.macro.conditional;

import com.webdeninteractive.xbotts.Mapping.macro.AbstractExtensionFunction;
import com.webdeninteractive.xbotts.Mapping.macro.SelectableParameter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UnlessCondition extends AbstractExtensionFunction {
	//list of operators which could be added
	//changes should be made to the evaluate method
	//to handle any new operators
	private String operators = ">,>=,<,<=,==,!=";
	private String delim = ",";

	public UnlessCondition( ){
    	ArrayList al = new ArrayList( );
		StringTokenizer st = new StringTokenizer( operators, delim );
		while( st.hasMoreElements( ) ){
			al.add( st.nextToken( ) );
		}
		
		super.addLinkableParameter( "val1" );
		super.addLinkableParameter( "val2" );
		super.addEditableParameter(new SelectableParameter(
			"operator", ( String )al.get( 0 ) , al, String.class, "Operators", this)
		);
	}
	
	public static String evaluateBoolean( 
	    String val1, String val2, String operator )
	{   
		String retVal;
		boolean bool = false;
		retVal = Boolean.toString( bool );
		double operand1;
		double operand2;
		
		if ( operator.equalsIgnoreCase( ">" ) ){
		    try {
		        operand1 = Double.parseDouble( val1 );
		        operand2 = Double.parseDouble( val2 );
		    } catch ( NumberFormatException nfe ){
			throw new NumberFormatException(
			    "Only evaluate double values operands" );
		    }
            bool = ( operand1 > operand2 );
			retVal = Boolean.toString( bool );
		}
		if ( operator.equalsIgnoreCase( ">=" ) ){
		    try {
		        operand1 = Double.parseDouble( val1 );
		        operand2 = Double.parseDouble( val2 );
		    } catch ( NumberFormatException nfe ){
			throw new NumberFormatException(
			    "Only evaluate double values operands" );
		    }
			bool = ( operand1 >= operand2 );
			retVal = Boolean.toString( bool );
		}
		if ( operator.equalsIgnoreCase( "<" ) ){
		    try {
		        operand1 = Double.parseDouble( val1 );
		        operand2 = Double.parseDouble( val2 );
		    } catch ( NumberFormatException nfe ){
			throw new NumberFormatException(
			    "Only evaluate double values operands" );
		    }
			bool = ( operand1 < operand2 );
			retVal = Boolean.toString( bool );
		}
		if ( operator.equalsIgnoreCase( "<=" ) ){
		    try {
		        operand1 = Double.parseDouble( val1 );
		        operand2 = Double.parseDouble( val2 );
		    } catch ( NumberFormatException nfe ){
			throw new NumberFormatException(
			    "Only evaluate double values operands" );
		    }
			bool = ( operand1 <= operand2 );
			retVal = Boolean.toString( bool );
		}
		//possibly a string comparison or double value comparison
		if ( operator.equalsIgnoreCase( "==" ) ){
			bool = val1.equalsIgnoreCase( val2 );
			if ( bool ){
				return Boolean.toString( true );
			} else {
		        try {
					//if the contents are not parsable as double,
					//that means it was used to compared string values
		            operand1 = Double.parseDouble( val1 );
		            operand2 = Double.parseDouble( val2 );
		        } catch ( NumberFormatException nfe ){
     		        return retVal;
				}
			    bool = ( operand1 == operand2 );
			    retVal = Boolean.toString( bool );
		   }
		}
		if ( operator.equalsIgnoreCase( "!=" ) ){
		    bool = val1.equalsIgnoreCase( val2 );
			if ( !bool ){
				return Boolean.toString( true );
			} else {
		        try {
					//if the contents are not parsable as double,
					//that means it was used to compared string values
		            operand1 = Double.parseDouble( val1 );
		            operand2 = Double.parseDouble( val2 );
		        } catch ( NumberFormatException nfe ){
     		        return retVal;
				}
			    bool = ( operand1 != operand2 );
			    retVal = Boolean.toString( bool );
		    }
		    retVal = Boolean.toString( bool );
		}
		if ( retVal.equalsIgnoreCase( "true" ) ){
			retVal = "false";
		} else {
			retVal = "true";
		}
		return retVal;
	}
}
