package org.uengine.processdesigner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * @author Jinyoung Jang
 */

public class RecursiveLine extends JComponent{

	public RecursiveLine(){
		setMinimumSize(new Dimension(0,20));
		setPreferredSize(new Dimension(0,20));
	}

	public void paint(Graphics g) {
		boolean isVertical = false;
		super.paint(g);
		
		Dimension d = /*getParent().*/getSize();

		g.drawRoundRect(20,1,d.width-40, d.height+4, 10, 10);
	}

}