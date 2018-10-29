package org.uengine.processdesigner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.designer.AbstractActivityDesigner;

/**
 * @author Jinyoung Jang
 */

public class PlaceHolder extends DesignerLabel implements DropTargetListener {
	Dimension originalSize;
	Dimension augmentedSize;
	protected boolean selected = false;
	boolean augmentVertically = false;
//	SvgBatikResizableIcon evtSVGIcon;
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if(!isVisible()) return;
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(150, 150, 150));
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(new Color(150, 150, 150));
		Stroke stroke = new BasicStroke(1.5f);
		stroke = new BasicStroke(1.3f,0,0,4.0f,null,0.0f);
		g2d.setStroke(stroke);
		
		GeneralPath path = new GeneralPath();		
		
//		int centerPointX = this.getComponent(0).getX()+ this.getComponent(0).getWidth(); // Activity Label's end point
//		int centerPointY = this.getHeight()/2;
		int centerY = getY() + getHeight() / 2;
		int i=0;		
		boolean centerAdjustingRequired = false;
		
		if (AbstractActivityDesigner.isVertical) {
			path.moveTo(0, getHeight()/2);
			path.lineTo(getWidth(), getHeight()/2);
			
			path.moveTo(getWidth(), getHeight()/2);
			path.lineTo(getWidth()-7, getHeight()/2-3);
			path.moveTo(getWidth(), getHeight()/2);
			path.lineTo(getWidth()-7, getHeight()/2+3);
			
		} else {
		
		}
	
		g2d.draw(path);
		
		if (selected || alwaysDrawRegion) {
//			g2d.setColor(Color.DARK_GRAY);			
//			g2d.fillRect(getWidth()/2-2, getHeight()/2-1, 4, 4);
			
			
/*			if(evtSVGIcon != null) {
				evtSVGIcon.paintIcon(this, g2d, 1, 1);
			}
*/
			Graphics2D g2 = (Graphics2D) g;
			stroke = new BasicStroke(2, 1, 1, 1, new float[]{4f,4f}, 3);
			g2.setStroke(stroke);
			g2.setColor(new Color(100, 100, 100));

			g2d.drawRoundRect(10, 0, getWidth()-20, getHeight()-2, 10, 10);
		
		}
		
		if(getText()!=null)
			g2d.drawString(getText(), 0, 0);
		
		if(centerAdjustingRequired){
			revalidate();
		}
	}

	public PlaceHolder(Dimension originalSize, boolean augmentVertically){
//		super(AbstractActivityDesigner.isVertical ? VERT_ARROW : ARROW);
		super();

		new DropTarget(this, 
				DnDConstants.ACTION_COPY_OR_MOVE,
				this);


		this.augmentVertically = augmentVertically;
		this.originalSize = originalSize;
		if(augmentVertically){
			this.augmentedSize = new Dimension(originalSize.width, originalSize.height*2);
		}else{
			this.augmentedSize = new Dimension(originalSize.width*2, originalSize.height);
		}

		setPreferredSize(originalSize);
		
		//setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
		addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
								
				if(!ActivityDesignerListener.bDragging) return;
				
//				setIcon(getImageIcon(AbstractActivityDesigner.isVertical ? VERT_ARROW_SELECTED : ARROW_SELECTED));
				
				setSelected(true);
				revalidate();
				ActivityDesignerListener.focusedComponent = (Component) e
						.getSource();
			}

			public void mouseExited(MouseEvent e) {
				if(!ActivityDesignerListener.bDragging) return;

//				setIcon(getImageIcon(AbstractActivityDesigner.isVertical ? VERT_ARROW : ARROW));
				setSelected(false);			
				revalidate();
				ActivityDesignerListener.focusedComponent = null;
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				if(ActivityDesignerListener.bDragging) {
					onDropped();
					
				}
			}
		});
		
/*		URL eventIconResourceUrl = getClass().getClassLoader().getResource("org/uengine/kernel/images/svg/event.svg");
		if(eventIconResourceUrl != null) {
			evtSVGIcon = SvgBatikResizableIcon.getSvgIcon(
				eventIconResourceUrl, new Dimension(32, 32));
		}*/
		
	}
	
	
	public void onDropped(){
		setSelected(false);
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		
		if(selected){
			this.setPreferredSize(augmentedSize);
		}else{
			this.setPreferredSize(originalSize);
		}
			
		/*repaint(getWidth()/2-2, getHeight()/2-1, 4, 4);*/
		revalidate();
		ProcessDesigner.getInstance().getProcessDefinitionDesigner()
		.revalidate();
	}
	
	/**
	 * start of implementation of DropTargetListener
	 */
	public void drop(DropTargetDropEvent e) {
		try {
			DataFlavor listFlavor = DataFlavor.javaFileListFlavor;
			Transferable tr = e.getTransferable();
			
			java.util.List list = (java.util.List)tr.getTransferData(listFlavor);
			Class activityClass = (Class)list.get(0);
			
			if(activityClass!=null && activityClass instanceof Class){
				
				Activity activity = (Activity) activityClass.newInstance();
				ActivityDesigner actDesigner = activity.createDesigner();
				
				Vector designerToAdd = new Vector();
				designerToAdd.add(actDesigner);
				
				ActivityDesignerListener.selectedComponents = designerToAdd;
				onDropped();
				ActivityDesignerListener.selectedComponents = new Vector();
			}
			
			e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);	
			e.dropComplete(true);
		} catch(UnsupportedFlavorException ufe) {
			ufe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException exx) {
			exx.printStackTrace();
		}

	}
	public void dragEnter(DropTargetDragEvent e) {setSelected(true); }
	public void dragExit(DropTargetEvent e) {setSelected(false);}
	public void dragOver(DropTargetDragEvent e) { }
	public void dropActionChanged(DropTargetDragEvent e) { }

	boolean alwaysDrawRegion;

	public boolean isAlwaysDrawRegion() {
		return alwaysDrawRegion;
	}

	public void setAlwaysDrawRegion(boolean alwaysDrawRegion) {
		this.alwaysDrawRegion = alwaysDrawRegion;
	}		

	String text;
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}