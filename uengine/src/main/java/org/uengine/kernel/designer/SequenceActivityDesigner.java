package org.uengine.kernel.designer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;

import org.uengine.processdesigner.ActivityLabel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * @author Jinyoung Jang
 */

public class SequenceActivityDesigner extends ComplexActivityDesigner {

	public SequenceActivityDesigner(){
		super();
	}
	
	protected void initialize(){
		super.initialize();
		this.setMaximumSize(new Dimension(80, 2000));
		//add(actLabel = new ActivityLabel(org.uengine.kernel.SequenceActivity.class), 0);
	}

/*	JLabel actLabel;
	protected Component getSymbolicComponent() {		
		return actLabel;
	}
*/	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Stroke stroke = new BasicStroke(0.5f, 1, 1, 1, new float[]{1f,5f}, 3);
		g2.setStroke(stroke);
		g2.setColor(new Color(150, 150, 150));
		g2.drawRoundRect(0, 1, this.getWidth()-1, this.getHeight()-2, 8, 8);
		//g2.dispose();
	}

}
