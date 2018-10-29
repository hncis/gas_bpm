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
 * SchemaTreeModel.java
 *
 * Created on August 9, 2002, 1:54 PM
 */

package com.webdeninteractive.xbotts.Mapping.maptool;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webdeninteractive.xbotts.Mapping.compiler.*;
import javax.swing.*;
import javax.swing.tree.*;

import org.uengine.util.RecursiveLoop;
/**
 *
 * @author  bmadigan
 */
public class SchemaTreeModel implements javax.swing.tree.TreeModel {
    
	Map nodesIndexedByName = new HashMap();
		public Map getNodesIndexedByName() {
			return nodesIndexedByName;
		}
		
    /** Creates a new instance of SchemaTreeModel */
    public SchemaTreeModel(Linkable root) {
        this.root = root;
        
        RecursiveLoop loop = new RecursiveLoop(){

			public boolean logic(Object tree) {
				Linkable node = ((Linkable)tree);
				
				nodesIndexedByName.put(node.getName(), node);
				return false;
			}

			public List getChildren(Object tree) {
				return ((Linkable)tree).getChildren();
			}
        	
        };
        
        loop.run(root);
    }
    
    Linkable root;
    
    public void addTreeModelListener(javax.swing.event.TreeModelListener treeModelListener) {
        //not impl.
    }
    
    public Object getChild(Object parent, int index) {
        return ((Linkable)parent).getChild(index);
    }
    
    public int getChildCount(Object parent) {
        return ((Linkable)parent).getChildNodes( ).length;
    }
    
    public int getIndexOfChild(Object parent, Object child) {
        Linkable[] children = ((Linkable)parent).getChildNodes( );
        for(int i=0;i<children.length;i++){
            if(children[i].equals(child)) return i;
        }
        return 0;
    }
    
    public Object getRoot() {
        return this.root;
    }
    
    public boolean isLeaf(Object node) {
        if(((Linkable)node).getChildNodes().length==0) return true;
        return false;
    }
    
    public void removeTreeModelListener(javax.swing.event.TreeModelListener treeModelListener) {
    }
    
    public void valueForPathChanged(javax.swing.tree.TreePath treePath, Object newValue) {
        //not implemented
    }

}
