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
 * MapToolController.java
 *
 * Created on August 9, 2002, 10:58 AM
 */

package com.webdeninteractive.xbotts.Mapping.maptool;

import java.awt.BorderLayout;
import java.io.StringBufferInputStream;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.codehaus.janino.SimpleCompiler;

import com.webdeninteractive.xbotts.Mapping.compiler.Record;



public class MapToolPanel extends JPanel{
	
	SchemaJTree srcTree;
	SchemaJTree trgTree;

	MapToolDataModel dm;
		public MapToolDataModel getModel() {
			return dm;
		}
		public void setModel(MapToolDataModel dm) {
			this.dm = dm;
		}
		
	LinkPanel linkPanel;
		public LinkPanel getLinkPanel() {
			return linkPanel;
		}	
		
	public MapToolPanel(SchemaTreeModel srcTreeModel, SchemaTreeModel targetTreeModel){
		setSchemaTreeModels(srcTreeModel, targetTreeModel);
	}
	
	public void setSchemaTreeModels(SchemaTreeModel srcTreeModel, SchemaTreeModel targetTreeModel){
		removeAll();
		
		srcTree = new SchemaJTree(srcTreeModel, false);
		trgTree = new SchemaJTree(targetTreeModel, true);
		dm = new MapToolDataModel();
		linkPanel = new LinkPanel(dm, trgTree, srcTree, this);
		
		setLayout(new BorderLayout());
		
		add("East", srcTree);
		add("West", trgTree);
		add("Center", linkPanel);
	}
    
	public static void main(String args[]) throws Exception{
		
		StringBuffer classContext = new StringBuffer();
		classContext.append("public class TestClass{ String a;}");
		SimpleCompiler compiler = new SimpleCompiler();
		compiler.cook(new StringBufferInputStream(classContext.toString()));
		Class testcls = compiler.getClassLoader().loadClass("TestClass");
		System.out.println(testcls);
		
		
		
		JFrame testFrm = new JFrame();
		
		Record node = new Record("test1");
		Record childNode = new Record("child11");
		Record childNode3 = new Record("child12");
		node.add(childNode);
		childNode.add(childNode3);
		//childNode.setParent(node);

		
		Record node2 = new Record("test2");
		Record childNode2 = new Record("child2");
		node2.add(childNode2);

		SchemaTreeModel model = new SchemaTreeModel(node);
		SchemaTreeModel model2 = new SchemaTreeModel(node2);
		
		testFrm.getContentPane().add(new MapToolPanel(model, model2));
		
		testFrm.pack();
		testFrm.setVisible(true);
		
	}
	
	
	public SchemaJTree getSrcTree() {
		return srcTree;
	}
	public void setSrcTree(SchemaJTree srcTree) {
		this.srcTree = srcTree;
	}
	public SchemaJTree getTrgTree() {
		return trgTree;
	}
	public void setTrgTree(SchemaJTree trgTree) {
		this.trgTree = trgTree;
	}



}

