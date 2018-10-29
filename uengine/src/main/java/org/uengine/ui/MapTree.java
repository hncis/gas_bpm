package org.uengine.ui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class MapTree extends JTree {
    
	MapTree(final JPanel panel, Node root){
       DefaultMutableTreeNode treeRoot = createTreeNode(root);
       DefaultTreeModel dtModel = new DefaultTreeModel(treeRoot);
       
       setModel(dtModel);
       getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
       putClientProperty("JTree.lineStyle", "Angled");
       setShowsRootHandles(true);
       
       setEditable(false);
       
       JScrollPane jScroll = new JScrollPane(){
          //This keeps the scrollpane a reasonable size
           public Dimension getPreferredSize(){
              return new Dimension(panel.getWidth()-20, panel.getHeight()-40);
           }
       };
       
       jScroll.getViewport().add(this);
       panel.add("Center", jScroll);
    }
    
    protected DefaultMutableTreeNode createTreeNode( Node root){
       DefaultMutableTreeNode dmtNode = null;
       
       NamedNodeMap attribs = root.getAttributes();
       String name = "";
       if(attribs != null){
          for(int i = 0; i < attribs.getLength(); i++){
             Node attNode = attribs.item(i);
             if(attNode.getNodeName().trim().equals("name")) name = attNode.getNodeValue().trim();
          }
       }
       if(name.equals("")) name = root.getNodeName();
        
       dmtNode = new DefaultMutableTreeNode(name);
       
       if(root.hasChildNodes()){
          NodeList childNodes = root.getChildNodes();
          if(childNodes != null){
            for(int k=0; k<childNodes.getLength(); k++){
               Node nd = childNodes.item(k);
               if(nd != null){
                  //A special case could be made for each Node Type
                   if(nd.getNodeType() == Node.ELEMENT_NODE){
                      dmtNode.add(createTreeNode(nd));
                   }
               }
            }
          }
       }
       return dmtNode;
    }
}
