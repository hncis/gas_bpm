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
 * XMLTransform.java
 *
 * Created on May 20, 2002, 1:27 PM
 */

package com.webdeninteractive.xbotts.Utility;
import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 *
 * @author  bmadigan
 * @version $Id: XMLTransform.java,v 1.2 2007/12/05 02:31:25 curonide Exp $ 
 */
public class XMLTransform
{
    
    /** Creates new XMLTransform */
    public XMLTransform ()
    {
    }
    
    public static void main (String args[])throws Exception
    {
	
	if(args.length<3)
	{
	    System.err.println ("Usage: XMLTransform <src file> <dest file> <xsl template>\n");
	    return;
	}
	xsl (args[0],args[1],args[2]);
    }
    
    public static void xsl (String inFilename, String outFilename, String xslFilename)
    throws Exception
    {
	try
	{
	    // Create transformer factory
	    TransformerFactory factory = TransformerFactory.newInstance ();
	    
	    // Use the factory to create a template containing the xsl file
	    Templates template = factory.newTemplates (new StreamSource (
	    new FileInputStream (xslFilename)));
	    
	    // Use the template to create a transformer
	    Transformer xformer = template.newTransformer ();
	    
	    // Prepare the input and output files
	    Source source = new StreamSource (new FileInputStream (inFilename));
	    Result result = new StreamResult (new FileOutputStream (outFilename));
	    
	    // Apply the xsl file to the source file and write the result to the output file
	    xformer.transform (source, result);
	    // An error occurred in the XSL file
	} catch (TransformerException e)
	{
	    // An error occurred while applying the XSL file
	    // Get location of error in input file
	    e.printStackTrace ( );
	}
    }
    

}
