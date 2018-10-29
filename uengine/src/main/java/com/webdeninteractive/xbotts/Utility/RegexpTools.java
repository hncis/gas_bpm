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
 * RegexpTools.java
 *
 * Created on January 25, 2002, 2:16 PM
 */

package com.webdeninteractive.xbotts.Utility;
import org.apache.oro.text.regex.*;
/**
 *
 * @author  bmadigan
 * @version $Id: RegexpTools.java,v 1.2 2007/12/05 02:31:25 curonide Exp $ 
 */
public class RegexpTools {

    /** Creates new RegexpTools */
    public RegexpTools () {
    }
    private static PatternCompiler getCompiler(){
	return new Perl5Compiler( );
    }
    private static PatternMatcher getMatcher( ){
	return new Perl5Matcher( );
    }
    private static Perl5Compiler compiler = new Perl5Compiler( );
    private static Perl5Matcher matcher = new Perl5Matcher( );
    
    public static Pattern compilePattern(String regexp) throws Exception{
	return compilePattern(regexp, Perl5Compiler.DEFAULT_MASK);
    }

    public  static Pattern compilePattern(String regexp, int options) throws Exception{
	return compiler.compile(regexp,options);
    }
    
    public  static Pattern compilePattern(char[] regexp) throws Exception{
	return compilePattern(regexp, Perl5Compiler.DEFAULT_MASK);
    }
    
    public  static Pattern compilePattern(char[] regexp, int options)throws Exception{
	return compiler.compile(regexp,options);
    }
    
    public static  MatchResult getMatchResults(String data, Pattern pattern)throws Exception{
	if(!matcher.contains(data,pattern)) return null;
	return  matcher.getMatch( );
    }
    
    public  static MatchResult getMatchResults(char[] data, Pattern pattern)throws Exception{
	if(!matcher.contains(data,pattern)) return null;
	return  matcher.getMatch( );
    }
    
    public  static String getFirstMatch(String data, String expr)throws Exception{
	String[] sa = getMatches(expr,data);
	if(sa.length>0) return sa[0];
	return null;
    }
    
    public  static String[] getMatches(String data, String expr)throws Exception{
	Pattern p = compiler.compile(expr);
	MatchResult mr;
	if(matcher.contains(data,p)){
	    mr = matcher.getMatch();
	} else {
	    return null;
	}
	String[] results = new String[mr.groups()];
	for(int i=0;i<results.length;i++)
	{
	    results[i] = mr.group(i);
	}
	return results;
    }
}
