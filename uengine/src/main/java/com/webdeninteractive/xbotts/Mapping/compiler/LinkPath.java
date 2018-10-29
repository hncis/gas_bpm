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
import java.util.LinkedList;
import java.util.StringTokenizer;
/** Contains an XPath path represented as a linked list.
 *  XPath paths are backslash seperated strings representing each nested level,
 *  terminated by an @attribute or #text atomic element.
 * @author  bmadigan
 */
public class LinkPath extends java.util.LinkedList {
    Linkable atom;
    public LinkPath(Linkable atom){
        this.atom = atom;
        this.atomType=atom.getType( );
        this.atomName=atom.getName( );
        this.addFirst(atom);
        //walk tree to root, add nodes root first;
        Linkable next = (Linkable)atom.getOwner( );
        if(null!=next) {
            this.addFirst(next);
            Linkable tmp;
            while((tmp = next.getOwner())!=null){
                next=tmp;
                this.addFirst(next);
            }
        }
    }
    String atomType;
    String atomName;
    
    public String getAtomType( ){
        return atomType;
    }
    public void setAtomType(String t)throws IllegalArgumentException{
        if(!t.equals("attribute")||t.equals("text")){
            throw new IllegalArgumentException(
            "setAtomType( ) expects 'attribute' or 'text'. Value "+t
            +" not allowed.");
        }
        this.atomType=t;
    }
    public String getAtomName( ){
        return atomName;
    }
    public void setAtomName(String name){
        this.atomName=name;
    }
    
    public String toString( ){
        String path = PathUtils.getPath(atom);
        System.out.println("path: "+path);
        return path;
        /*
        StringBuffer sb = new StringBuffer( );
        if(this.size()==1) return "/";
        for(int i=0;i<this.size( );i++){
            Linkable elem = (Linkable)this.get(i);
            if(elem.equals(this.getLast())){
                if(elem.getNodeType().equals(Linkable.ATTR_NODE)){
                    sb.append("/@"+elem.getName( ));
                }else{
                  sb.append("/"+elem.getName( ));
                }
            }else if(elem.equals(this.getFirst( ))){
                //root, do nothing
            }else{
                sb.append("/"+elem.getName( ));
            }
        }
        return sb.toString( );
         */
    }
}
