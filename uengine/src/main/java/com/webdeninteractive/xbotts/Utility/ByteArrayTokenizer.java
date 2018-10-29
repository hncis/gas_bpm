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
 * ByteArrayTokenizer.java
 *
 * Created on January 18, 2002, 10:12 AM
 */

package com.webdeninteractive.xbotts.Utility;


import java.util.*;
/**
 *
 * @author  bmadigan
 * @version $Id: ByteArrayTokenizer.java,v 1.2 2007/12/05 02:31:25 curonide Exp $ 
 */
public class ByteArrayTokenizer
{
    
    int marker = 0;
    int endMarker = 0;
    byte[] data;
    byte[] startToken,endToken;
    int tokenCount = 0;
    ArrayList list = new ArrayList ( );
    
    /** Creates new ByteArrayTokenizer */
    public ByteArrayTokenizer (byte[] data, byte[] startToken, byte[] endToken)
    throws Exception
    {
	int tokenLen = startToken.length;
	this.data = data;
	int offset=0;
	while(true)
	{
	    if((offset+tokenLen>=data.length)||(offset==-1)) break;	    
	    int end = ByteTools.indexOf (data,endToken,(offset+tokenLen));
	    byte[] tmp = null;
	    if(end==-1)
	    {
		offset=ByteTools.indexOf (data,startToken,offset);
		tmp = ByteTools.subArray (data,(offset+tokenLen),data.length);
	    }
	    else
	    {
		offset=ByteTools.indexOf (data,startToken,offset);
		tmp = ByteTools.subArray (data,offset+tokenLen,end);
	    }
	    offset = (offset+tmp.length+endToken.length);
	    ByteArray ba = new ByteArray (tmp);
	    list.add (ba);
	    tokenCount++;
	}
    }
    
    public int countTokens ( )
    {
	return list.size ();
    }
    
    public byte[] next ()
    {
	if(marker>tokenCount) return null;
	ByteArray ba = (ByteArray) list.get (marker);
	marker++;
	return ba.bytes;
    }
    
    public boolean hasMoreTokens ()
    {
	return (marker  < countTokens ());
    }
    
    public static void main (String[] args)
   throws Exception
    {
	String test = "START:this is the data:END   START:this is the next piece of data:ENDSTART:xx:ENDSTART:cc";
	byte[] data = test.getBytes ( );
	ByteArrayTokenizer bt = new ByteArrayTokenizer (data, "START:".getBytes ( ), ":END".getBytes ());
	
	while(bt.hasMoreTokens ())
	{
	    byte[] tmp = bt.next ( );
	    System.out.println(new String(tmp)+" ");
	}
	System.out.println (bt.countTokens ());
    }
}

class ByteArray
{
    public final byte[] bytes;
    public ByteArray ( byte[] bytes)
    {
	this.bytes = bytes;
    }
}