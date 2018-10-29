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
 * TreeUI.java
 *
 * Created on August 19, 2002, 1:55 PM
 */

package com.webdeninteractive.xbotts.Mapping.maptool;
import com.webdeninteractive.xbotts.Mapping.compiler.*;
import javax.swing.plaf.metal.MetalTreeUI;
import javax.swing.plaf.basic.BasicTreeUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TooManyListenersException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.tree.*;

import org.uengine.processdesigner.ProcessDesigner;
/**
 *
 * @author  bmadigan
 */
public class BTreeUI extends javax.swing.plaf.basic.BasicTreeUI{
    
    /** Creates a new instance of TreeUI */
    public BTreeUI(boolean ltr) {
        super();
        this.leftToRight = ltr;
    }
    
    int lw;                     //lastWidth, width of tree when last painted.
    boolean leftToRight;        //indicates Left to right rendering...
    NodeDimensionsHandler ndh=null;
    
    public AbstractLayoutCache getTreeState( ){
        return treeState;
    }
    
    public void paint(Graphics g, JComponent c) {
        if (tree != c) {
            throw new InternalError("incorrect component");
        }
        
        // Should never happen if installed for a UI
        if(treeState == null) {
            return;
        }
        if(null==ndh){
            ndh =new NodeDimensionsHandler( );
            treeState.setNodeDimensions(ndh);
        }
        // Update the lastWidth if necessary.
        // This should really come from a ComponentListener installed on
        // the JTree, but for the time being it is here.
        int              width = tree.getWidth();
        
        if (width != lw) {
            lw = width;
            if (!leftToRight) {
                // For RTL when the size changes, we have to refresh the
                // cache as the X position is based off the width.
                //redoTheLayout();
                updateSize();
            }
        }
        
        Rectangle        paintBounds = g.getClipBounds( );
        Insets           insets = new Insets(0,0,0,0);
        
        
        TreePath         initialPath = getClosestPathForLocation
        (tree, 0, paintBounds.y);
        Enumeration      paintingEnumerator = treeState.getVisiblePathsFrom
        (initialPath);
        int              row = treeState.getRowForPath(initialPath);
        int              endY = paintBounds.y + paintBounds.height;
        
        drawingCache.clear();
        
        if(initialPath != null && paintingEnumerator != null) {
            TreePath   parentPath = initialPath;
            
            // Draw the lines, knobs, and rows
            
            // Find each parent and have them draw a line to their last child
            
            
            parentPath = parentPath.getParentPath();
            while(parentPath != null) {
                paintVerticalPartOfLeg(g, paintBounds, insets, parentPath);
                drawingCache.put(parentPath, Boolean.TRUE);
                parentPath = parentPath.getParentPath();
            }
            
            boolean         done = false;
            // Information for the node being rendered.
            boolean         isExpanded;
            boolean         hasBeenExpanded;
            boolean         isLeaf;
            Rectangle       boundsBuffer = new Rectangle();
            Rectangle       bounds;
            TreePath        path;
            boolean         rootVisible = isRootVisible();
            
            while(!done && paintingEnumerator.hasMoreElements()) {
                path = (TreePath)paintingEnumerator.nextElement();
                if(path != null) {
                    isLeaf = treeModel.isLeaf(path.getLastPathComponent());
                    if(isLeaf)
                        isExpanded = hasBeenExpanded = false;
                    else {
                        isExpanded = treeState.getExpandedState(path);
                        hasBeenExpanded = tree.hasBeenExpanded(path);
                    }
                    
                    bounds = treeState.getBounds(path, boundsBuffer);
                    if(bounds == null)
                        // This will only happen if the model changes out
                        // from under us (usually in another thread).
                        // Swing isn't multithreaded, but I'll put this
                        // check in anyway.
                        return;
                    bounds.x += insets.left;
                    bounds.y += insets.top;
                    
                    
                    
                    // See if the vertical line to the parent has been drawn.
                    parentPath = path.getParentPath();
                    
                    if(parentPath != null) {
                        if(drawingCache.get(parentPath) == null) {
                            paintVerticalPartOfLeg(g, paintBounds,
                            insets, parentPath);
                            drawingCache.put(parentPath, Boolean.TRUE);
                        }
                        
                        paintHorizontalPartOfLeg(g, paintBounds, insets,
                        bounds, path, row,
                        isExpanded,
                        hasBeenExpanded, isLeaf);
                        
                        
                    }
                    /*
                    else if(rootVisible && row == 0) {
                        paintHorizontalPartOfLeg(g, paintBounds, insets,
                                                 bounds, path, row,
                                                 isExpanded,
                                                 hasBeenExpanded, isLeaf);
                    }
                     
                     */
                    
                    if(shouldPaintExpandControl(path, row, isExpanded,
                    hasBeenExpanded, isLeaf)) {
                        paintExpandControl(g, paintBounds, insets, bounds,
                        path, row, isExpanded,
                        hasBeenExpanded, isLeaf);
                    }
                    
                    //This is the quick fix for bug 4259260.  Somewhere we
                    //are out by 4 pixels in the RTL layout.  Its probably
                    //due to built in right-side padding in some icons.  Rather
                    //than ferret out problem at the source, this compensates.
                    
                    if (!leftToRight) {
                        bounds.x +=2;
                    }
                    
                    paintRow(g, paintBounds, insets, bounds, path,
                    row, isExpanded, hasBeenExpanded, isLeaf);
                    if((bounds.y + bounds.height) >= endY)
                        done = true;
                }
                else {
                    done = true;
                }
                row++;
            }
        }
        // Empty out the renderer pane, allowing renderers to be gc'ed.
        rendererPane.removeAll();
        
    }
    
    /**
     * Paints the expand (toggle) part of a row. The receiver should
     * NOT modify <code>clipBounds</code>, or <code>insets</code>.
     */
    protected void paintExpandControl(Graphics g,
    Rectangle clipBounds, Insets insets,
    Rectangle bounds, TreePath path,
    int row, boolean isExpanded,
    boolean hasBeenExpanded,
    boolean isLeaf) {
        Object       value = path.getLastPathComponent();
        
        // Draw icons if not a leaf and either hasn't been loaded,
        // or the model child count is > 0.
        if (!isLeaf && (!hasBeenExpanded ||
        treeModel.getChildCount(value) > 0)) {
            int middleXOfKnob;
            if (leftToRight) {
                middleXOfKnob = bounds.x - (getRightChildIndent() - 1);
            }
            else {
                middleXOfKnob = bounds.x + bounds.width + (getRightChildIndent() -1);
            }
            int middleYOfKnob = bounds.y + (bounds.height / 2);
            
            if (isExpanded) {
                Icon expandedIcon = getExpandedIcon();
                if(expandedIcon != null)
                    drawCentered(tree, g, expandedIcon, middleXOfKnob,
                    middleYOfKnob );
            }
            else {
                Icon collapsedIcon = getCollapsedIcon();
                if(collapsedIcon != null)
                    drawCentered(tree, g, collapsedIcon, middleXOfKnob,
                    middleYOfKnob);
            }
        }
    }
    
    ImageIcon expanded = new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/expanded.gif"));
    public Icon getExpandedIcon( ){
        return expanded;
    }
    ImageIcon collapsed = new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/collapsed.gif"));
    public Icon getCollapsedIcon( ){
        return collapsed;
    }
    
    protected boolean shouldPaintExpandControl(TreePath path,
    int row,
    boolean isExpanded,
    boolean hasBeenExpanded,
    boolean isLeaf){
        if(isLeaf) return false;
        // if(row<=1) return false;
//        Linkable l = (Linkable) path.getLastPathComponent( );
//        if(l.getMinOccurs( ).equals("1")) return true;
        return true;
    }
    
    /**
     * Paints the horizontal part of the leg. The receiver should
     * NOT modify <code>clipBounds</code>, or <code>insets</code>.<p>
     * NOTE: <code>parentRow</code> can be -1 if the root is not visible.
     */
    protected void paintHorizontalPartOfLeg(Graphics g, Rectangle clipBounds,
    Insets insets, Rectangle bounds,
    TreePath path, int row,
    boolean isExpanded,
    boolean hasBeenExpanded, boolean
    isLeaf) {
        // Don't paint the legs for the root'ish node if the
        int depth = path.getPathCount() - 1;
        if((depth == 0 || (depth == 1 && !isRootVisible())) &&
        !getShowsRootHandles()) {
            return;
        }
        
        int clipLeft = clipBounds.x;
        int clipRight = clipBounds.x + (clipBounds.width - 1);
        int clipTop = clipBounds.y;
        int clipBottom = clipBounds.y + (clipBounds.height - 1);
        int lineY = bounds.y + bounds.height / 2;
        // Offset leftX from parents indent.
        if (leftToRight) {
            int leftX = bounds.x - getRightChildIndent();
            int nodeX = bounds.x - getHorizontalLegBuffer();
            
            if(lineY >= clipTop && lineY <= clipBottom && nodeX >= clipLeft &&
            leftX <= clipRight ) {
                leftX = Math.max(Math.max(insets.left, leftX), clipLeft);
                nodeX = Math.min(Math.max(insets.left, nodeX), clipRight);
                
                if (leftX != nodeX) {
                    g.setColor(getHashColor());
                    paintHorizontalLine(g, tree, lineY, leftX, nodeX);
                }
            }
        }
        else {
            int leftX = bounds.x + bounds.width + getRightChildIndent() - 2;
            int nodeX = bounds.x + bounds.width +
            getHorizontalLegBuffer();
            
            if(lineY >= clipTop && lineY <= clipBottom &&
            leftX >= clipLeft && nodeX <= clipRight) {
                leftX = Math.min(leftX, clipRight);
                nodeX = Math.max(nodeX, clipLeft);
                
                g.setColor(getHashColor());
                paintHorizontalLine(g, tree, lineY, nodeX, leftX);
            }
        }
    }
    
    /**
     * Paints the vertical part of the leg. The receiver should
     * NOT modify <code>clipBounds</code>, <code>insets</code>.<p>
     */
    protected void paintVerticalPartOfLeg(Graphics g, Rectangle clipBounds,
    Insets insets, TreePath path) {
        int depth = path.getPathCount() - 1;
        if (depth == 0 && !getShowsRootHandles() && !isRootVisible()) {
            return;
        }
        int lineX;
        if (leftToRight) {
            lineX = ((depth + 1 + depthOffset) *
            totalChildIndent) - getRightChildIndent() + insets.left;
        }
        else {
            lineX = lw - ((depth + depthOffset) * totalChildIndent) - 9;
        }
        int clipLeft = clipBounds.x;
        int clipRight = clipBounds.x + (clipBounds.width - 1);
        
        if (lineX >= clipLeft && lineX <= clipRight) {
            int clipTop = clipBounds.y;
            int clipBottom = clipBounds.y + clipBounds.height;
            Rectangle parentBounds = getPathBounds(tree, path);
            Rectangle lastChildBounds = getPathBounds(tree,
            getLastChildPath(path));
            
            if(lastChildBounds == null)
                // This shouldn't happen, but if the model is modified
                // in another thread it is possible for this to happen.
                // Swing isn't multithreaded, but I'll add this check in
                // anyway.
                return;
            
            int       top;
            
            if(parentBounds == null) {
                top = Math.max(insets.top + getVerticalLegBuffer(),
                clipTop);
            }
            else
                top = Math.max(parentBounds.y + parentBounds.height +
                getVerticalLegBuffer(), clipTop);
            if(depth == 0 && !isRootVisible()) {
                TreeModel      model = getModel();
                
                if(model != null) {
                    Object        root = model.getRoot();
                    
                    if(model.getChildCount(root) > 0) {
                        parentBounds = getPathBounds(tree, path.
                        pathByAddingChild(model.getChild(root, 0)));
                        if(parentBounds != null)
                            top = Math.max(insets.top + getVerticalLegBuffer(),
                            parentBounds.y +
                            parentBounds.height / 2);
                    }
                }
            }
            
            int bottom = Math.min(lastChildBounds.y +
            (lastChildBounds.height / 2), clipBottom);
            
            if (top <= bottom) {
                g.setColor(getHashColor( ));
                ((Graphics2D)g).setStroke(new BasicStroke(1.0f));
                paintVerticalLine(g, tree, lineX, top, bottom);
            }
        }
    }
    
    Color hashColor = Color.gray;
    protected Color getHashColor( ){
        return hashColor;
    }
    
    
    
    /**
     * Paints the renderer part of a row. The receiver should
     * NOT modify <code>clipBounds</code>, or <code>insets</code>.
     */
    protected void paintRow(Graphics g, Rectangle clipBounds,
    Insets insets, Rectangle bounds, TreePath path,
    int row, boolean isExpanded,
    boolean hasBeenExpanded, boolean isLeaf) {
        // Don't paint the renderer if editing this row.
        if(editingComponent != null && editingRow == row)
            return;
        
        int leadIndex=-1;
        Component component;
        boolean isSelected = tree.isRowSelected(row);
        component = currentCellRenderer.getTreeCellRendererComponent
        (tree, path.getLastPathComponent(),
        isSelected, isExpanded, isLeaf, row,
        (leadIndex == row));
        rendererPane.paintComponent(g, component, tree, bounds.x, bounds.y,
        bounds.width, bounds.height, true);
        g.setColor(getHashColor( ));
        
        //draws a line from the cell renderer component to the facing edge to denote element/attribute links
        /*       Linkable node = (Linkable) path.getLastPathComponent( );
        if(node==null) return;
        
       if(node.isLinked()){
            ((Graphics2D)g).setStroke((isSelected? GraphicsConstants.SELECTED_STROKE:GraphicsConstants.NORMAL_STROKE));
            g.setColor(GraphicsConstants.getLinkColor(node.getLinkType()));
            if(leftToRight){
                g.drawLine(bounds.x+bounds.width,
                bounds.y+(tree.getRowHeight()/2),
                bounds.x+bounds.width+clipBounds.width,
                bounds.y+(tree.getRowHeight()/2));
            }else{
                g.drawLine(bounds.x,
                bounds.y+(tree.getRowHeight()/2),
                bounds.x-clipBounds.width,
                bounds.y+(tree.getRowHeight()/2));
            }
        }
*/    }
    
    private void redoTheLayout( ){
        if(treeState != null){
            treeState.invalidateSizes();
        }
    }
    
    
    /** Determines if a point is inside the expand control
     *
     */
    protected boolean isLocationInExpandControl(TreePath path,
    int mouseX, int mouseY) {
        if(path != null && !treeModel.isLeaf(path.getLastPathComponent())){
            int                     boxWidth;
            Insets                  i = tree.getInsets();
            
            if(getExpandedIcon() != null)
                boxWidth = getExpandedIcon().getIconWidth();
            else
                boxWidth = 8;
            
            int                     boxLeftX = (i != null) ? i.left : 0;
            
            if (leftToRight) {
                boxLeftX += (((path.getPathCount() + depthOffset - 2) *
                totalChildIndent) + getLeftChildIndent()) -
                boxWidth / 2;
            }
            else {
                boxLeftX += lw - 1 -
                ((path.getPathCount() - 2 + depthOffset) *
                totalChildIndent) - getLeftChildIndent() -
                boxWidth / 2;
            }
            int boxRightX = boxLeftX + boxWidth;
            
            return mouseX >= boxLeftX && mouseX <= boxRightX;
        }
        return false;
    }
    
    
    /**
     * Class responsible for getting size of node, method is forwarded
     * to BasicTreeUI method. X location does not include insets, that is
     * handled in getPathBounds.
     */
    // This returns locations that don't include any Insets.
    public class NodeDimensionsHandler extends
    AbstractLayoutCache.NodeDimensions {
        /**
         * Responsible for getting the size of a particular node.
         */
        public Rectangle getNodeDimensions(Object value, int row,
        int depth, boolean expanded,
        Rectangle size) {
            // Return size of editing component, if editing and asking
            // for editing row.
            if(editingComponent != null && editingRow == row) {
                Dimension        prefSize = editingComponent.
                getPreferredSize();
                int              rh = getRowHeight();
                
                if(rh > 0 && rh != prefSize.height)
                    prefSize.height = rh;
                if(size != null) {
                    size.x = getRowX(row, depth);
                    size.width = prefSize.width;
                    size.height = prefSize.height;
                }
                else {
                    size = new Rectangle(getRowX(row, depth), 0,
                    prefSize.width, prefSize.height);
                }
                
                if(!leftToRight) {
                    size.x = lw - size.width - size.x - 2;
                }
                return size;
            }
            // Not editing, use renderer.
            if(currentCellRenderer != null) {
                Component          aComponent;
                
                aComponent = currentCellRenderer.getTreeCellRendererComponent
                (tree, value, tree.isRowSelected(row),
                expanded, treeModel.isLeaf(value), row,
                false);
                if(tree != null) {
                    // Only ever removed when UI changes, this is OK!
                    rendererPane.add(aComponent);
                    aComponent.validate();
                }
                Dimension        prefSize = aComponent.getPreferredSize();
                
                if(size != null) {
                    size.x = getRowX(row, depth);
                    size.width = prefSize.width;
                    size.height = prefSize.height;
                }
                else {
                    size = new Rectangle(getRowX(row, depth), 0, prefSize.width, prefSize.height);
                }
                
                if(!leftToRight) {
                    size.x = lw - size.width - size.x - 2;
                }
                return size;
            }
            return null;
        }
        
        /**
         * @return amount to indent the given row.
         */
        
        protected int getRowX(int row, int depth) {
            if(leftToRight)
                return totalChildIndent * (depth + depthOffset);
            else
                return totalChildIndent * (depth - depthOffset);
        }
        
    }
}
