package org.uengine.processdesigner;

import javax.swing.*;

import org.uengine.kernel.designer.AbstractActivityDesigner;
import org.uengine.kernel.designer.ProcessDefinitionDesigner;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * @author Jinyoung Jang
 */

public class ProxyPanel extends JPanel implements ArrowReceiver{

	public ProxyPanel(LayoutManager lm){
		super(lm);		
		initialize();
	}
	
	public ProxyPanel(){
		super();
		initialize();
	}
	
	public int getBaseline(int width, int height) {
		Component comp = this;
		
		while(!(comp instanceof ArrowLabel) && comp instanceof Container && ((Container)comp).getComponentCount() > 0){
			comp = ((Container)comp).getComponent(0);
		}
		
		if(!(comp instanceof Container) || (comp instanceof ArrowLabel))
			return comp.getY() + comp.getHeight();

		return -1;
	}

	protected void initialize(){
		setBackground(Color.WHITE);
	}
	


	public int getArrowReceivePointIn() {
		if(getComponentCount() > 0 && getComponent(0) instanceof ArrowReceiver){
			Component comp = getComponent(0);
			return ((ArrowReceiver)comp).getArrowReceivePointIn() + comp.getY();
		}
			
		return getHeight() / 2;
	}
		
	public int getArrowReceivePointOut() {
		if(getComponentCount() > 0 && getComponent(getComponentCount()-1) instanceof ArrowReceiver){
			Component comp = getComponent(getComponentCount()-1);
			return ((ArrowReceiver)comp).getArrowReceivePointOut() + comp.getY();
		}
		
		return getHeight() / 2;
	}

	public boolean receiveArrow() {
		return arrowReceiveOrNot;
	}
	
	
	boolean arrowReceiveOrNot = false;
	public void setArrowReceiveOrNot(boolean arrowReceiveOrNot) {
		this.arrowReceiveOrNot = arrowReceiveOrNot;
	}

}
