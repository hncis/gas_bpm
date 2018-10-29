package org.uengine.ui;

import javax.swing.tree.*;
import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class Tree extends Vector implements TreeNode{

	Object data;
	
	Tree parent;
	
	public Tree(){
		super();
	}
	
	public Tree( Tree parent, Object data){
		super();
		this.parent = parent;
		this.data = data;
	}
	
	public String toString(){
		return data.toString();
	}

	/**
	 * 
	 * @uml.property name="data"
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 
	 * @uml.property name="data"
	 */
	public Object getData() {
		return data;
	}

	
// TreeNode Implements
	public Enumeration children(){
	     // Returns the children of the receiver as an Enumeration.
		return elements();
	}
	
	public boolean getAllowsChildren(){
	      //Returns true if the receiver allows children. 
	      return true;
	}
	public TreeNode getChildAt(int childIndex){
	      //Returns the child TreeNode at index childIndex. 
	      if( size() == 0)
	      		return null;
	      
	      return (TreeNode)get(childIndex);
	}
	public int getChildCount(){
	      //Returns the number of children TreeNodes the receiver contains. 
	      return size();
	}
	public int getIndex(TreeNode node){
	      //Returns the index of node in the receivers children. 
	      return indexOf( node);
	}
	public TreeNode getParent(){
	      //Returns the parent TreeNode of the receiver.
	      return (TreeNode)parent;
	}
	public boolean isLeaf(){
		if( size() ==0)
			return true;
			
		return false;
	}
// TreeNode implements end

/*
// MutableTreeNode implements start

	public void insert(MutableTreeNode child, int index) {
		//Adds child to the receiver at index. 
	}
	
	public void remove(int index){
		Removes the child at index from the receiver. 
	}
	
	public void remove(MutableTreeNode node){
		//Removes node from the receiver. 
	}
	
	public void removeFromParent(){
		//Removes the receiver from its parent. 
	}
	
	public void setParent(MutableTreeNode newParent){
		//Sets the parent of the receiver to newParent. 
	}
	public void setUserObject(Object object)  

// MutableTreeNode implements end
*/
}