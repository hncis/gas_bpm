package org.uengine.kernel.designer;

import org.metaworks.ui.Separator;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.processdesigner.*;
import org.uengine.util.RecursiveLoop;
import org.uengine.util.UEngineUtil;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.awt.geom.GeneralPath;

/**
 * @author Jinyoung Jang
 */

public class ComplexActivityDesigner extends AbstractActivityDesigner implements ArrowReceiver{

	JPanel centerPanel;
	Hashtable arrowVectors = new Hashtable();
	private Vector childDesigners = new Vector();
	boolean activityHeaderAdded = false;

	
	public ComplexActivityDesigner(){
		super();
		
		initialize();
	}
	
	public void setActivity(Activity act){
		super.setActivity(act);
		centerPanel.removeAll();
		for(Iterator<Activity> iter = ((ComplexActivity)getActivity()).getChildActivities().iterator(); iter.hasNext(); ){
			Activity child = iter.next();
			
			ActivityDesigner designer = child.createDesigner();
			centerPanel.add(boxComponent(designer));
			getChildDesigners().add(designer);
		}
	}

	protected void initialize(){
		setBorder(BorderFactory.createEmptyBorder());
		
		if(isVertical)
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	
		else{
			FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 0, 2);
			setLayout(fl);
		}

//		add(new Separator(!isVertical));	
		
		if(isVertical){
			centerPanel = new ProxyPanel();
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		}else{
			BaseLineFlowLayout fl = new BaseLineFlowLayout(FlowLayout.CENTER, 0, 0);
			centerPanel = new ProxyPanel(fl);
		}
		
		add(centerPanel);
		
		ArrowLabel firstArrow = new ArrowLabel(){				
			public void onDropped() {
				Vector selectedComps = ActivityDesignerListener.getSelectedComponents();
				if(selectedComps!=null){
					insertActivityDesigners(selectedComps, -1);
				}
	
				ActivityDesignerListener.bDragging = false;
				setSelected(false);
			}				
		};
		
		//firstArrow.setPreferredSize(new Dimension(20,20));
		
		add(firstArrow);
	
	}
	
	protected void toggle(){
		for(int i=0; i<getChildDesigners().size(); i++){
			ActivityDesigner designer = (ActivityDesigner) getChildDesigners().get(i);
			if(designer instanceof ComplexActivityDesigner){
				((ComplexActivityDesigner)designer).toggle();
			}else
			if(!(designer instanceof HumanActivityDesigner)){
				
				//designer.getComponent().setSize(collapsed ? new Dimension(10,10) : designer.getComponent().getPreferredSize());
				designer.getComponent().setVisible(!collapsed);
				designer.getComponent().getParent().setVisible(!collapsed);
				
			}
		}
	}
	
	public void paintCollapsed(Graphics g) {		
/*		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(150, 150, 150));
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(new Color(150, 150, 150));
		Stroke stroke = new BasicStroke(1.5f);
		stroke = new BasicStroke(1.3f,0,0,4.0f,null,0.0f);
		g2d.setStroke(stroke);
		
		
		//outer border
		g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8,8);

		int rectSize = 8;
		int halfRectSize = rectSize/2;
		int innerCrossGap = 2;
		int half = ArrowLabel.DEFAULT_WIDTH / 2;
		int posY = getHeight()*2 / 3 + halfRectSize;
		
		g2d.setColor(Color.BLACK);

		//collapse button
		GeneralPath path = new GeneralPath();		

		if(collapsed){
			path.moveTo(half, posY-halfRectSize+innerCrossGap);
			path.lineTo(half, posY+halfRectSize-innerCrossGap);
//			path.moveTo(getWidth()-halfRectSize-half, posY-halfRectSize+innerCrossGap);
//			path.lineTo(getWidth()-halfRectSize-half, posY+halfRectSize-innerCrossGap);
		}
		
		path.moveTo(half-halfRectSize+innerCrossGap, posY);
		path.lineTo(half+halfRectSize-innerCrossGap, posY);
//		path.moveTo(getWidth()-half+innerCrossGap, posY);
//		path.lineTo(getWidth()-rectSize-half-innerCrossGap, posY);
	
		g2d.draw(path);
		
		g2d.drawRoundRect(half-halfRectSize, posY-halfRectSize, rectSize, rectSize, 2,2);
//		g2d.drawRoundRect(getWidth()-rectSize-half, posY-halfRectSize, rectSize, rectSize, 2,2);
*/
	}
		
	public java.awt.Component add(java.awt.Component comp){
		comp.addKeyListener(new KeyListenerTransferrer(this));
		return super.add(comp);
	}

	public void addActivity(ActivityDesigner designer, int index){
System.out.println(centerPanel);		
		centerPanel.add(boxComponent(designer), index);
		
		if(index>-1){
			((ComplexActivity)getActivity()).addChildActivity(designer.getActivity(), index);
			getChildDesigners().add(index, designer);
		}else{
			((ComplexActivity)getActivity()).addChildActivity(designer.getActivity());
			getChildDesigners().add(designer);
		}
		
		/*if(comp instanceof ActivityDesigner){
			((ActivityDesigner)comp).setParentDesigner(this);
		}*/	
		
		ProcessDesigner.getInstance().getProcessDefinitionDesigner().revalidate();

	}
	
	protected JPanel boxComponent(final ActivityDesigner designer){
		final Component comp = designer.getComponent();
		
		JPanel compPanel = new ProxyPanel();
		
		if(isVertical){
			compPanel.setLayout(new BoxLayout(compPanel, BoxLayout.Y_AXIS));
		}else{
			BaseLineFlowLayout fl = new BaseLineFlowLayout(FlowLayout.CENTER, 0, 0);
			compPanel.setLayout(fl);
		}
		
		{
			ArrowLabel arrow = new ArrowLabel(){
				
				public void onDropped() {
					Vector selectedComps = ActivityDesignerListener.getSelectedComponents();
					if(selectedComps!=null){
						ActivityDesigner droppingActivity = ((ActivityDesigner)selectedComps.elementAt(0));
						int where = designer.getParentDesigner().indexOf(designer);
						
System.out.println("inserting where : " + where);
						insertActivityDesigners(selectedComps, where);
					}

					ActivityDesignerListener.bDragging = false;
					setSelected(false);
				}
				
			};
				
			compPanel.add(arrow);
			compPanel.add(comp);
		}
		
		return compPanel;
	}

	
	protected Container getBoxingContainer(Component comp){
		return comp.getParent();
	}
		
	public void addActivity(ActivityDesigner designer){
		addActivity(designer, -1);
	}
	
	public synchronized void removeActivity(ActivityDesigner designer){
		ProcessDesigner.getInstance().saveDefinitionForUndo();
		Component comp = designer.getComponent();
		
		centerPanel.remove(getBoxingContainer(comp));
		((ComplexActivity)getActivity()).removeChildActivity(designer.getActivity());
		
		getChildDesigners().remove(comp);
		
		ProcessDesigner.getInstance().getProcessDefinitionDesigner().revalidate();
	}
	
	public int indexOf(ActivityDesigner comp){
		
		return getChildDesigners().indexOf(comp);
	}
		
	public void setText(String text){}
	
	public synchronized void onDropped(Vector components){
		insertActivityDesigners(components, -1);
	}

	protected synchronized void insertActivityDesigners(Vector activityDesigners, int index){
		ProcessDesigner.getInstance().saveDefinitionForUndo();
		
System.out.println("ComplexActivityDesigner::onDropped");
		for(Enumeration enumeration = activityDesigners.elements(); enumeration.hasMoreElements(); ){
			Component comp = (Component)enumeration.nextElement();
			
			if(comp==this){
				System.out.println("what?");
				return;
			}
			
			if(comp instanceof Container)
				if(((Container)comp).isAncestorOf(this)){
				System.out.println("crazy?");
				return;
			}
		}		
		
		for(Enumeration enumeration = activityDesigners.elements(); enumeration.hasMoreElements(); ){
			ActivityDesigner comp = (ActivityDesigner)enumeration.nextElement();
			
			ComplexActivityDesigner oParent = comp.getParentDesigner();

			int realIndex = index;
			ActivityDesigner designerAtTheIndex = null;
			if(oParent == this && index>-1){
				designerAtTheIndex = (ActivityDesigner)getChildDesigners().elementAt(index);
			}

			if(oParent!=null)
				oParent.removeActivity(comp);

			if(designerAtTheIndex!=null){
				realIndex = indexOf(designerAtTheIndex);
			}
				
			addActivity(comp, realIndex);			
			
			if(oParent!=null){
				Component c = oParent.getComponent();
				if(c instanceof JComponent)
					((JComponent)comp).revalidate();
				else
					c.validate();
			}
		}
		
		revalidate();		
		validateActivity();
	}


	public Vector getChildDesigners() {
		return childDesigners;
	}

	public static void main(String [] args){
		JFrame frm = new JFrame("test");
		
		final ComplexActivityDesigner c;
		
		JPanel pan = new JPanel(new BorderLayout());
		
		pan.add("Center", c = new ComplexActivityDesigner());
		
		frm.setSize(200,100);
		
		final JTextField tf;
		pan.add("South", tf = new JTextField());
		
		tf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				DefaultActivityDesigner actComp;
				c.addActivity(actComp = new DefaultActivityDesigner());
				
				DefaultActivity act = new DefaultActivity(tf.getText());
				actComp.setActivity(act);
				
				c.validate();
				
				
			}
		});
		
		frm.getContentPane().add("Center", pan);
		frm.setVisible(true);

		frm.getContentPane().addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e){
				System.out.println(e);
			}
		});		
	}

	public void setProcessInstance(ProcessInstance instance) {
		super.setProcessInstance(instance);
		
		for(int i=0; i<getChildDesigners().size(); i++){
			ActivityDesigner activityDesigner = (ActivityDesigner)getChildDesigners().elementAt(i);
			
			activityDesigner.setProcessInstance(instance);
		}
	}

	public void setStatus(String status) {
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(150, 150, 150));
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(new Color(150, 150, 150));
		Stroke stroke = new BasicStroke(1.5f);
		stroke = new BasicStroke(1.3f,0,0,4.0f,null,0.0f);
		g2d.setStroke(stroke);

		
		drawArrowLine(g2d, this);
		
		paintCollapsed(g2d);
	}
	
	public void drawArrowLine(Graphics2D g2d, Container container){
				
		
		GeneralPath path = new GeneralPath();

		int startY, startX, endX, endY;
		
		Component[] comps = container.getComponents();
		for(int j=0; j<comps.length; j++){
			if(comps[j] instanceof Container && !(comps[j] instanceof ActivityDesigner)){
				drawArrowLine(g2d, (Container) comps[j]);
			}

			if(comps[j].isVisible() && comps[j] instanceof ArrowLabel){
				
				/**
				 * 1. find out former and latter 
				 */
				Component former =null;
				if(j==0){ //if this arrow label located in the first order of panel, 
					/**
					 * find the former from the parent container recursively
					 */
					Container parent = container;
					while(
							!(former instanceof ActivityDesigner) && 
							!(former instanceof ActivityLabel) && 
							parent != this
					){
						Component[] parentComps = parent.getParent().getComponents();

						for(int k=0; k<parentComps.length; k++){
							if(parent == parentComps[k]){
								former = k > 0 ? parentComps[k-1] : null;
								break;
							}
						}
						parent = parent.getParent();
					}
					
				}else{ //otherwise, simply the sibling component whose order is just befor this arrow line would be the former
					former = comps[j-1];
				}
				
				Component latter = null;
				if(j==comps.length-1){ //if this arrow label is located in the last order of panel, 
					/**
					 * find the latter from the parent container recursively
					 */
					Container parent = container;
					while(
							!(latter instanceof ActivityDesigner) && 
							!(latter instanceof ActivityLabel) && 
							parent != this
					){
						Component[] parentComps = parent.getParent().getComponents();

						boolean startFindingFromNow = false;
						for(int k=0; k<parentComps.length; k++){
							if(parent == parentComps[k]){
								startFindingFromNow = true;
							}
							
							if(startFindingFromNow)
								latter = k < parentComps.length-1 ? parentComps[k+1] : null;				
							
							if(latter instanceof ActivityDesigner || latter instanceof ActivityLabel) break;
						}
						
						parent = parent.getParent();
					}
					
				}else{ //otherwise, simply the sibling component whose order is just after this arrow line would be the latter
					latter = j < comps.length-1 ? comps[j+1] : null;
				}
				
				/**
				 * 2. calculate startX, startY, endX, endY for drawing the arrow line.
				 */
				if(former!=null){
					Point relativeLocation = UEngineUtil.getRelativeLocation(this, comps[j]);
					startX = relativeLocation.x;

					if(former instanceof ArrowReceiver){
						startY = ((ArrowReceiver)former).getArrowReceivePointOut() + UEngineUtil.getRelativeLocation(this, former).y;
					}else{
						startY = former.getHeight()/2 + UEngineUtil.getRelativeLocation(this, former).y;
					}
				}else{
					Point relativeLocation = UEngineUtil.getRelativeLocation(this, container);
					startX=relativeLocation.x;
					startY=relativeLocation.y + container.getHeight()/2;
				}
				
				if(latter!=null){
					Point latterLocation = UEngineUtil.getRelativeLocation(this, latter);
					endX = latterLocation.x-1;

					if(latter instanceof ArrowReceiver){
						endY = ((ArrowReceiver)latter).getArrowReceivePointIn() + latterLocation.y;
					}else{
						endY = latter.getHeight()/2 + latterLocation.y;
					}
					
				}else{
					endX=getWidth();
					endY=getHeight()/2;
				}
				
				/**
				 * 3. draw 
				 */
				path.moveTo(startX, startY);
				
				int roundCornerDiff = (endY-startY)/2;
				if(roundCornerDiff < -10) roundCornerDiff = -10;
				if(roundCornerDiff > 10) roundCornerDiff = 10;			

				if(roundCornerDiff!=0){
					path.curveTo(startX, startY, startX+10, startY, startX+10, startY+roundCornerDiff);
					path.lineTo(startX+10, endY-roundCornerDiff);
					path.curveTo(startX+10, endY-roundCornerDiff, startX+10, endY, startX+20, endY);
				}

				path.lineTo(endX, endY);
				
				if((latter instanceof ArrowReceiver && ((ArrowReceiver)latter).receiveArrow()) || latter instanceof ActivityLabel){
					path.moveTo(endX, endY);
					path.lineTo(endX-7, endY-3);
					path.moveTo(endX, endY);
					path.lineTo(endX-7, endY+3);
				}

				g2d.draw(path);


			}	
					
		}
		
	}

	public int getArrowReceivePointIn() {
		return getHeight()/2;
	}

	public int getArrowReceivePointOut() {
		return getHeight()/2;
	}

	public boolean receiveArrow() {
		return true;
	}
}

