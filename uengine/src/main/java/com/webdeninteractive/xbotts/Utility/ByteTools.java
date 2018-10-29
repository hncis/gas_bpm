/*
-------------------------------------------------------------------
BIE is Copyright 2001-2004 Brunswick Corp.
-------------------------------------------------------------------
Please read the legal notices (docs/legal.txt) and the license
(docs/bie_license.txt) that came with this distribution before using
this software.
-------------------------------------------------------------------
*/
package com.webdeninteractive.xbotts.Utility;
import java.io.*;

public class ByteTools
{
    public static void main (String args[])throws Exception
    {
	File f = new File ("./testDataFile.dat");
	DataInputStream fis = new DataInputStream (new FileInputStream (f));
	int length = (new Long (f.length ( ))).intValue ();
	byte[] data = new byte[length];
	fis.readFully (data);
	String token = "'xxxx2'";
	byte[] tokenBytes = token.getBytes ();
	int offset = 0;
	long start,end;
	int result = -1;
	start = System.currentTimeMillis ();
	for(int i=0;i<10000;i++)
	{
	    result = ByteTools.indexOf (data,tokenBytes,12);
	}
	end = System.currentTimeMillis ();
	System.out.println ("pattern found at:"+result);
	System.out.println ("pattern String: "+ new String(subArray(data,result,data.length)));
	System.out.println (end-start);
	
	byte[] test = "This|string|hasxxx|lots|of|tokens.|The|most|common|token|is|a|pipe".getBytes();
	int startI = 5;
	int endI = ByteTools.indexOf(test,"|".getBytes(),startI);
	System.out.println("x length"+"x".getBytes().length);
	System.out.println(startI+" "+endI);
	
	//byte[] res = subArray(test, startI, endI);
	//System.out.println("resulting subArray: "+new String(res) );
    }
    
    public static byte[] subArray(byte[] data, int start, int end) throws Exception{
	if(end <= start){ throw new Exception("Sub Array has length >= 0");}
	byte[] result = new byte[end-start];
	for(int i=0; i<end-start;i++){
	    result[i]=data[start+i];
	}
	return result;
    }
    
    
    public static int indexOf (byte[] data, byte[] tokenBytes, int offset)
    {
	//byte last = tokenBytes[tokenBytes.length-1];
	int tLen = tokenBytes.length;
	if(tLen<1) return 0;
	if(tLen == 1) return indexOf(data, tokenBytes[0], offset);
	boolean match = false;
	int pos = offset; //data offset loop pointer
	int count =0; //loop count
	while(match==false)
	{
	    count++;
	    if(pos>=data.length) return -1;
	    
	    if(data[pos]==tokenBytes[tLen-1])
	    {
		if((pos-tLen)>=-1){
		    if(data[pos-tLen+1] == tokenBytes[0])
		    {
			int c=tLen-1;
			for(int j=pos-1;j>pos-tLen;j--)
			{
			    c--;
			    if(data[j]!=tokenBytes[c])
			    {
				match=false;
				break;
			    }else
			    {
				match=true;
			    }
			}
		    }
		}
	    }
	    pos++;
	}
	return pos - tLen;
    }
    
    public static int indexOf (byte[] data, byte[] tokenBytes){
	return indexOf(data,tokenBytes,0);
    }
    
    public static int indexOf(byte[] data, byte token){
	return indexOf(data,token,0);
    }
    
    public static int indexOf(byte[] data, byte token, int offset){
	for(int i=offset;i<data.length;i++){
	    if(data[i]==token) return i;
	}
	return -1;
    }
    
    public static String toString (byte b)
    {
	byte[] byt = new byte[1];
	byt[0]=b;
	String bt = new String (byt);
	return bt;
    }
}


