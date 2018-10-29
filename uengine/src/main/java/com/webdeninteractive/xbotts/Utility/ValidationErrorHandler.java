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
 * ValidationErrorHandler.java
 *
 * Created on July 9, 2002, 4:41 PM
 */

package com.webdeninteractive.xbotts.Utility;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
/**
 *
 * @author  bmadigan
 * @version $Id: ValidationErrorHandler.java,v 1.2 2007/12/05 02:31:25 curonide Exp $ 
 */
public class ValidationErrorHandler extends org.xml.sax.helpers.DefaultHandler {

    /** Creates new ValidationErrorHandler */
    public ValidationErrorHandler () {
    }
    
    int threshold = 10;
    
    public void error(SAXParseException  spe)throws SAXParseException{
	throw spe;
    }
    public void warning (SAXParseException  spe)throws SAXParseException{
	if(threshold-- == 0) throw spe;
    }
    public void fatalError (SAXParseException  spe)throws SAXParseException{
	throw spe;
    }
}
