package org.uengine.processdesigner;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;


/**
 * @author Jinyoung Jang
 */

public class Separator extends JPanel{

	public final static int STYLE_ETCHED = 1;
	public final static int STYLE_NONE = 0;
	
	boolean isVertical = true;
	boolean sized = false;
	boolean parentListening = false;
	int style;

	public Separator(){
		this(true);
	}
	
	public Separator(final boolean isVertical, int style){
		super();
		//this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.isVertical = isVertical;
		this.style = style;
		
/*		if(isVertical){
			setBackground(Color.BLACK);
		}else*/
			setBackground(Color.WHITE);
		
		setMinimumSize(new Dimension(0,0));
		setPreferredSize(new Dimension(3,2));
	}

	public Separator(final boolean isVertical){
		this(isVertical, STYLE_NONE);
	}

	public void setSize(int w, int h){
		Dimension d = getParent().getSize();
			
		int w_=2;
		int h_=2;
		
		if(isVertical){
			h_ = (int)d.getHeight();// - 10;
		}else{
			w_ = w;
		}
				
		if(!sized)
			super.setSize(w_, h_);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(150, 150, 150));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GeneralPath shape = new GeneralPath();

		Stroke stroke = new BasicStroke(1.5f);
		stroke = new BasicStroke(1.3f,0,0,4.0f,null,0.0f);
		g2.setStroke(stroke);
		g2.setColor(new Color(150, 150, 150));
		
		if(isVertical){
			Dimension d = /*getParent().*/getSize();

			int x = (d.width / 2)-1;
			setForeground(Color.GRAY);
			g.fillRect(x, 0, 2, d.height);
		}else{
			shape.moveTo(0, getHeight()/2);
			shape.lineTo(getWidth(), getHeight()/2.0f);
			
			
//			Dimension d = /*getParent().*/getSize();
//
//			int y = (d.height / 2)-1;
//			if(style==STYLE_ETCHED){
//				setForeground(Color.BLACK);
//				g.fillRect(0, y, d.width, 2);
//				setForeground(Color.GRAY);
//				g.fillRect(0, y, d.width, 1);
//			}else{
//				setForeground(Color.GRAY);
//				g.fillRect(0, y, d.width, 2);
//			}
		}
		g2.draw(shape);		
	}
	
	
}

