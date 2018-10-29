package org.uengine.processdesigner.framework;

import java.awt.Graphics2D;

import javax.swing.JComponent;

public class ArrowViewObject implements PaintDelegator{

	public void paint(ProcessDesignerCanvas pdc, Graphics2D g2) {
		g2.drawLine(source.getX() + source.getWidth() ,source.getY() + source.getHeight() / 2,target.getX(),target.getY() + target.getHeight() / 2);

	}
	
	JComponent source, target;

	public JComponent getSource() {
		return source;
	}

	public void setSource(JComponent source) {
		this.source = source;
	}

	public JComponent getTarget() {
		return target;
	}

	public void setTarget(JComponent target) {
		this.target = target;
	}
	

}
