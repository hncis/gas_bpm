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
 * Strings.java
 *
 * Created on September 16, 2002, 10:13 AM
 */

package com.webdeninteractive.xbotts.Utility;
import java.util.*;
import java.util.regex.*;
/**
 *
 * @author  bmadigan
 */
public class Strings {
    
    /** Creates a new instance of Strings */
    public Strings() {
    }
    
    
    public static String[] split(String line, String sep, String q) throws Exception {
        
        Pattern p = Pattern.compile(q);
        ArrayList result = new ArrayList();
        int read=0;
        //outer field parse loop
        boolean done= false;
        boolean hasStringQualifier = false;
        StringBuffer sb = new StringBuffer();
        while(read<line.length()){
            while(true) { //slow
                if(read+sep.length()<=line.length()){
                    if(q.length() == 0){
                        if(!line.substring(read, read+sep.length( )).equals(sep)){
                            sb.append(line.charAt(read));
                        }
                        else{
                            break;
                        }
                    }
                    else{
                        if(line.substring(read,read+q.length()).equals(q)){
                            if (hasStringQualifier) hasStringQualifier = false;
                            else hasStringQualifier = true;
                        }
                        if(hasStringQualifier){
                            sb.append(line.charAt(read));
                        }
                        else{
                            if(!line.substring(read, read+sep.length( )).equals(sep)){
                                sb.append(line.charAt(read));
                            }
                            else{
                                break;
                            }
                        }
                    }
                    read++;
                }
                else{
                    break;
                }
            }
            result.add(sb.toString( ));
            //System.out.println(sb.toString ( ));
            read++;
            sb = new StringBuffer( );//slow
        }
        String ret[] = new String[result.size( )];
        for(int i=0;i<result.size( );i++){
            if (q.length() == 0){
                ret[i] = (String) result.get(i);
            }
            else{
                Matcher matcher = p.matcher((String) result.get(i));
                ret[i] = matcher.replaceAll("");
            }
        }
        return ret;
    }
}
