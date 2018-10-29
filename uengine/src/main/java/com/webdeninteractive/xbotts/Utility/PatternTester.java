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
 * PatternTester.java
 *
 * Created on May 22, 2002, 11:45 AM
 */

package com.webdeninteractive.xbotts.Utility;
import java.util.regex.*;
import java.nio.CharBuffer;
import java.io.*;
/**
 *
 * @author  bmadigan
 * @version $Id: PatternTester.java,v 1.2 2007/12/05 02:31:25 curonide Exp $ 
 */
public class PatternTester {

    /** Creates new PatternTester */
    public PatternTester () {
    }

    public static void main(String args[]) throws Exception{
	File datafile = new File ( "C:/Documents and Settings/bmadigan/cvsroot/mapBuilder/data/edifact.orders.test" );
	char[] message = new char[1024];
	//read the message.
	Reader in = new FileReader(datafile);
	in.read(message);
	Pattern pattern = Pattern.compile("\\+");
	Matcher m = pattern.matcher (CharBuffer.wrap (message));
	//found the segment identifier pattern
	if(m.find ( )){
	    System.out.println(new String(message, m.start( ), m.start()+30));
	}else{
	    System.out.println("unexpected EOF, aborting");
	}
    }
}
