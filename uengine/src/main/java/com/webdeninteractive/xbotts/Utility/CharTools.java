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
 * CharTools.java
 *
 * Created on January 22, 2002, 2:58 PM
 */

package com.webdeninteractive.xbotts.Utility;
import org.apache.oro.text.regex.*;
/**
 *
 * @author  bmadigan
 * @version $Id: CharTools.java,v 1.2 2007/12/05 02:31:25 curonide Exp $ 
 */
public class CharTools {

    /** Creates new CharTools */
    public CharTools () {
    }
   
    public static String decode (String hex)throws Exception
    {
	Byte b= Byte.decode (hex);
	byte[] bytes = new byte[]
	{b.byteValue ( )};
	return new String (bytes);
    }
    
    public static char[] subArray(char[] data, int start, int end){
	if(end<=start) return null;
	int len = end-start;
	char[] tmp = new char[len];
	int c = 0;
	for ( int i=start;i<end;i++,c++){
	    tmp[c]=data[i];
	}
	return tmp;
    }
    
    public static boolean compare (char[] data, int start, int len, char[] token)
    {
	int c = 0;
	for(int i=start; i< len; i++,c++)
	{
	    if(data[i]!=token[c]) return false;
	}
	return true;
    }
    
    public static int indexOf(char[] data, Pattern pattern){
	return indexOf(data,pattern,0,true);
    }
    
    public static int indexOf(char[] data, Pattern pattern, int offset){
	return indexOf(data,pattern,offset,true);
    }
    
    public static int indexOf(char[] data, Pattern pattern, int offset, boolean multiline){
	if(data==null) return -1;
	char[] tmp = CharTools.subArray(data,offset,data.length);
	Perl5Matcher matcher = new Perl5Matcher( );
	matcher.setMultiline(multiline);
	if(!matcher.contains(tmp,pattern)) return -1;
	MatchResult mr = matcher.getMatch( );
	return mr.beginOffset(0)+offset;
    }
    
    public static int indexOf (char[] cbuff, char[] token, int offset)
    throws Exception
    {
	int position=0;
	int len = token.length;
	int read = 0;
	int c;
	int size = cbuff.length;
	for(int i=offset;i<size;i++){
	    if(len == 1){
		if(cbuff[i]==token[0]){
		    position = i;
		    break;
		}
	    }
	    if(cbuff[i] == token[len-1])
	    { //last char in token found in reader
		int lookBack = (i-len)+1;
		if(lookBack >= 0)	//could be negative
		{
		    if((cbuff[lookBack])==token[0])
		    { //see if first char matches
			if(compare (cbuff,lookBack,len,token)){
			    position = (i-len)+1;//possible match, scan for complete match
			    break;
			}
		    }
		}
		else{ //lookBack was behind start of buffer
		    continue;//keep looking
		}
	    }
	    read++;
	}
	if(read==size) return -1;//found no tokens
	return position;
    }
}
