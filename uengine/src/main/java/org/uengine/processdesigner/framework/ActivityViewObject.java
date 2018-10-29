package org.uengine.processdesigner.framework;

import java.awt.Graphics2D;

public class ActivityViewObject implements PaintDelegator{

	public void paint(ProcessDesignerCanvas pdc, Graphics2D g2) {
		g2.drawRect(0,0,100,100);
	}

}
