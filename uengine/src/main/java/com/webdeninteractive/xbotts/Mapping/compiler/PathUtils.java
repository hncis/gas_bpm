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
 * LinkPath.java
 *
 * Created on August 1, 2002, 1:16 PM
 */

package com.webdeninteractive.xbotts.Mapping.compiler;
import java.util.*;
/** Contains an XPath path represented as a linked list.
 *  XPath paths are backslash seperated strings representing each nested level,
 *  terminated by an @attribute or #text nodeic element.
 * @author  bmadigan
 */
public class PathUtils{
    
    public static String getPath(Linkable node){
        if(node.getName( ).equals("/")) return node.getName( );
        //walk from node to root, creating a path
        LinkedList list = new LinkedList( );
        list.addFirst(node);
        //walk tree to root, add nodes root first;
        Linkable o = (Linkable)node.getOwner( );
        if(null!=o) {
            list.addFirst(o);
            Linkable tmp;
            while((tmp = o.getOwner())!=null){
                o=tmp;
                list.addFirst(o);
            }
        }
        StringBuffer sb = new StringBuffer( );
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            Linkable current = (Linkable)iter.next( );
            Linkable owner = current.getOwner( );
            if(owner==null){
                //sb.append(current.getName( ));
                continue;
            }
            
            Iterator siblings = owner.getChildren( ).iterator( );
            int index = 0;
            while(siblings.hasNext( )){
                Linkable sibling = (Linkable) siblings.next( );
                if(sibling.getName( ).equals(current.getName( ))){
                    if(!sibling.equals(current)){ //add index to path
                        index++;
                    }else{
                        break;
                    }
                }
            }
            sb.append("/");
            if(current.getChildren( ).size( )==0){
                if(current.getNodeType( ).equals(Linkable.ATTR_NODE)){
                    sb.append("@");
                }
            }
            if(index>0){
                sb.append(current.getName( )).append("[").append(index).append("]");
            }else{
                sb.append(current.getName( ));
            }
        }
        return sb.toString( );
    }
    
    public static Linkable find(String path, Linkable root){
        //search for a node in a tree.
       // System.out.println("searching for: "+path);
        StringTokenizer tok = new StringTokenizer(path, "/");
        Linkable current = root;
        while(tok.hasMoreTokens( )){
            String next = tok.nextToken( );
            if(next.startsWith("@")){//strip attributes (may accur in path)
                next=next.substring((next.indexOf("@")+1), next.length( ));
            }
            if(next.equals("/")) continue;
            current = current.getChild(next);
        }
       // System.out.println("found: "+current.getName( ));
        return current;
    }
}
