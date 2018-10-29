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
 * LinkableTreeNode.java
 *
 * Created on August 12, 2002, 10:11 AM
 */

package com.webdeninteractive.xbotts.Mapping.compiler;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.datatransfer.*;
import java.awt.Component;
/**
 *
 * @author  bmadigan
 */
public interface LinkableTreeNode extends javax.swing.tree.TreeNode, java.awt.datatransfer.Transferable, Linkable{
    void setRow( int row );
    int getRow( );
    public void setComponent(Component c);
    public Component getComponent( );
}