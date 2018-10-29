package org.uengine.kernel.designer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;

import org.uengine.processdesigner.ActivityDesigner;
import org.uengine.processdesigner.ActivityLabel;
import org.uengine.processdesigner.ArrowLabel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;

/**
 * @author Jinyoung Jang
 */

public class AtomicHumanActivityDesigner extends SequenceActivityDesigner {

	boolean collapsed = false;
	
	public AtomicHumanActivityDesigner(){
		super();
	}
	
	protected void initialize(){
		super.initialize();
		
		addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2){
					toggle();
				}
				
			}
			
		});
		
		toggle();
	}
	
	protected void toggle(){
		for(int i=0; i<getChildDesigners().size(); i++){
			ActivityDesigner designer = (ActivityDesigner) getChildDesigners().get(i);
			if(!(designer instanceof HumanActivityDesigner)){
				
				collapsed = designer.getComponent().isVisible();

				designer.getComponent().setVisible(!collapsed);
				designer.getComponent().getParent().setVisible(!collapsed);
				
			}
		}
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

	}
	

}
