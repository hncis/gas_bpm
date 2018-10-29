package org.uengine.kernel.designer;

import org.uengine.processdesigner.*;

import java.awt.BasicStroke; 
import java.awt.Color; 
import java.awt.Component; 
import java.awt.FlowLayout; 
import java.awt.FontMetrics;
import java.awt.Graphics; 
import java.awt.Graphics2D; 

import java.awt.Stroke; 
import java.awt.geom.*; 
import java.awt.RenderingHints; 
import java.util.Vector; 

import javax.swing.BorderFactory; 
import javax.swing.BoxLayout; 

import org.metaworks.ui.Separator; 


/**
 * @author Jinyoung Jang
 */

public class LoopActivityDesigner extends ComplexActivityDesigner{

	Component labelComponent;
	public LoopActivityDesigner(){
		super();	
	}

//	protected void initialize(){
//		super.initialize();
//			
//		add(new ActivityLabel(org.uengine.kernel.LoopActivity.class), 0);
//	}
	protected void initialize(){ 
		setBorder(BorderFactory.createEmptyBorder(15,0,15,0)); 

		if(isVertical) 
		        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		else{ 
		        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2)); 
		} 
		
		//add(new Separator(!isVertical)); 
		
		if(isVertical){ 
		        centerPanel = new ProxyPanel(); 
		        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); 
		}else 
		        centerPanel = new ProxyPanel(new BaseLineFlowLayout(FlowLayout.CENTER, 0, 0)); 
		
		add(centerPanel); 
		
		add(new ArrowLabel(){ 
		        public void onDropped() { 
		                Vector selectedComps = ActivityDesignerListener.getSelectedComponents(); 
		                if(selectedComps!=null){ 
		                        insertActivityDesigners(selectedComps, -1); 
		                } 
		                setSelected(false);	
		                ActivityDesignerListener.bDragging = false;
		                
		        } 
		}); 
		
		//add(new Separator(!isVertical)); 
		
		add(labelComponent = new ActivityLabel(org.uengine.kernel.LoopActivity.class)); 
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Stroke stroke = new BasicStroke(1.5f);
		stroke = new BasicStroke(1.3f,0,0,4.0f,null,0.0f);
		g2.setStroke(stroke);
//		//g2.setStroke(stroke);
		g2.setColor(new Color(150, 150, 150));
		
		String activityName = getActivity().getName().getText();
		if(activityName!=null){
			FontMetrics fm = g.getFontMetrics();
			Rectangle2D rect = fm.getStringBounds(activityName, g);
			
			
			g2.drawString(activityName, (int)(getWidth()-rect.getWidth())/2, 0);
		}
		
//		g2.drawRoundRect(2, 2, this.getWidth()-5, this.getHeight()-5, 8, 8);
//		g2.dispose();

//		RadialGradientPaint paint = new RadialGradientPaint(new Point2D.Double(605.7142944335938, 486.64788818359375), 117.14286f, new Point2D.Double(605.7142944335938, 486.64788818359375), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.7743890285491943f, 0.0f, 0.0f, 1.9697060585021973f, -1891.633056640625f, -872.8853759765625f));
//		g2.setPaint(paint);
		
		labelComponent.getLocation();
		
		int rightPointX = this.getWidth()-labelComponent.getWidth()/2;
		int rightPointY = this.getHeight() / 2 - labelComponent.getWidth()/2;
		
		GeneralPath shape = new GeneralPath();
		shape.moveTo(1, this.getHeight()/2-8);
		shape.lineTo(4, this.getHeight()/2);
		shape.moveTo(4+3, this.getHeight()/2-8);
		shape.lineTo(4, this.getHeight()/2);
		shape.lineTo(4, 10);
		shape.curveTo(4, 10, 4, 1, 10, 1);
//		shape.moveTo(1, 1);
		shape.lineTo(rightPointX-10, 1);
		shape.curveTo(rightPointX-10, 1, rightPointX-1, 0, rightPointX-1, 10);
		shape.lineTo(rightPointX-1, rightPointY);
		//shape.closePath();
		g2.draw(shape);
		g2.dispose();
		
	}

	@Override
	public boolean receiveArrow() {
		return false;
	}


	
	/*protected void initialize(){
		setBorder(BorderFactory.createEmptyBorder());
		
		JPanel p = new ProxyPanel();
				
		p.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		
//		p.add(new Separator());
		centerPanel = new ProxyPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		p.add(centerPanel);
//		p.add(new ArrowLabel());
//		p.add(new Separator());
		
		setLayout(new BorderLayout(0,0));
//		RecursiveLine recursiveLine = new RecursiveLine();
//		add("North", recursiveLine);
		add("Center", p);
						
		add("East", new ActivityLabel(org.uengine.kernel.SwitchActivity.class));
//		JPanel filler = new ProxyPanel();
//		filler.setPreferredSize(recursiveLine.getPreferredSize());
//		add("South", filler);
	}
	
	protected JPanel boxComponent(Component comp){
		JPanel compPanel = new ProxyPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));	
			compPanel.add(comp);
			compPanel.add(new ArrowLabel());
		
		return compPanel;
	}

	public void paint(Graphics g) {
		super.paint(g);

		Dimension d = getSize();
		
	}*/
	
}