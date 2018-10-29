package org.uengine.processdesigner;

import java.awt.event.*;
import java.awt.*;

import org.uengine.kernel.designer.*;

/**
 * @author Jinyoung Jang
 */

public class SimulatorActivityDesignerListener extends MouseAdapter{
	
	Color orgColor;

	public synchronized void mouseClicked(MouseEvent ae){
		if(ae.getClickCount()==1){
			Object source = ae.getSource();

			if(source instanceof ActivityDesigner){
				Component theComp = ((ActivityDesigner)source).getComponent();
				
				Color currColor = theComp.getBackground();
				
				if(currColor!=Color.BLUE){
					orgColor = currColor;				
					theComp.setBackground(Color.BLUE);
				}else{
					theComp.setBackground(orgColor);
				}
			}
		}
	}

}