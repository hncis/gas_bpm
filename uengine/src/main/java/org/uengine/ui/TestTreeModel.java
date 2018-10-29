package org.uengine.ui;

import javax.swing.tree.*;
import javax.swing.event.*;

/**
 * @author Jinyoung Jang
 */

public class TestTreeModel extends Tree implements TreeModel{

	public TestTreeModel(){
		super();
		/*
		add("level0leaf");

		Tree level1tree = new Tree("tree1");
			Tree level2tree = new Tree("tree2");
			level2tree.add("level2leaf");
		level1tree.add(level2tree);
		
		add(level1tree);
		*/
	}

	public void addTreeModelListener(TreeModelListener l) {
//	          Adds a listener for the TreeModelEvent posted after the tree changes. 
		
	}
	
	public Object getChild(Object parent, int index) {
//	          Returns the child of parent at index index in the parent's child array. 

		if(parent instanceof Tree)
			return ((Tree)parent).elementAt(index);
			
		return null;
		
	}
	
	public int getChildCount(Object parent) {
//	          Returns the number of children of parent. 
		if(parent instanceof Tree)
			return ((Tree)parent).size();
			
		return 0;
	
	}
	
	public int getIndexOfChild(Object parent, Object child){
//	          Returns the index of child in parent. 

		if(parent instanceof Tree)
			return ((Tree)parent).indexOf(child);
			
		return 0;
	}
	
	public Object getRoot() {
//	          Returns the root of the tree. 
		return this;
	}
	
	public boolean isLeaf(Object node) {
//	          Returns true if node is a leaf.

		if( node instanceof Tree){
			Tree t = (Tree)node;
			if( t.size() == 0)
				return true;
		}
		
		return !(node instanceof Tree);
	}
	
	public void removeTreeModelListener(TreeModelListener l) {
//	          Removes a listener previously added with addTreeModelListener(). 
	}
	public void valueForPathChanged(TreePath path, Object newValue) {
//	          Messaged when the user has altered the value for the item identified by path to newValue. 
	}
	
}

