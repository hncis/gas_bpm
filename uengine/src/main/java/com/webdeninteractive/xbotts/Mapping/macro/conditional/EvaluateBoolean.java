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
 * EvaluateBoolean.java
 * evaluate if given boolean is true or false,
 * returns the parameters for the respective boolean values
 * ie, if ( true ) {
 *         return parameterTrue; 
 *     } else {
 *         return parameterFalse;
 *     }
 *
 */
package com.webdeninteractive.xbotts.Mapping.macro.conditional;

import com.webdeninteractive.xbotts.Mapping.macro.AbstractExtensionFunction;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class EvaluateBoolean extends AbstractExtensionFunction {
	
	public EvaluateBoolean( ){
		super.addLinkableParameter( "boolean" );
		super.addLinkableParameter( "val1" );
		super.addLinkableParameter( "val2" );
	}
	
	public static String evaluateBoolean( String booleanValue,
	                                      String parameterTrue, 
										  String parameterFalse )
	{   
		boolean bool = new Boolean( booleanValue ).booleanValue( );
		if ( bool ) {
	        return parameterTrue;
		} else {
			return parameterFalse;
		}
	}
}
