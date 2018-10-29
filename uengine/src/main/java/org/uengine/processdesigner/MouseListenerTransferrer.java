package org.uengine.processdesigner;

import java.awt.event.*;
import java.awt.*;

/**
 * @author Jinyoung Jang
 */

public class MouseListenerTransferrer implements MouseListener{

	Component transferredComp;
	
	public MouseListenerTransferrer(Component comp){
		transferredComp = comp;
	}

	
	
	public synchronized void mouseClicked(MouseEvent e){
		System.out.println("MouseListenerTransferrer::mouseClicked");
		e.setSource(transferredComp);
		
		MouseListener[] listeners = transferredComp.getMouseListeners();
			
		for(int i=0; i<listeners.length; i++)
			listeners[i].mouseClicked(e);
	}
		
	public synchronized void mousePressed(MouseEvent e){
		e.setSource(transferredComp);
		
		MouseListener[] listeners = transferredComp.getMouseListeners();
			
		for(int i=0; i<listeners.length; i++)
			listeners[i].mousePressed(e);
	}

	public synchronized void mouseReleased(MouseEvent e){
		e.setSource(transferredComp);
		
		MouseListener[] listeners = transferredComp.getMouseListeners();
			
		for(int i=0; i<listeners.length; i++)
			listeners[i].mouseReleased(e);
	}			


	public synchronized void mouseEntered(MouseEvent e){
		e.setSource(transferredComp);
		
		MouseListener[] listeners = transferredComp.getMouseListeners();
			
		for(int i=0; i<listeners.length; i++)
			listeners[i].mouseEntered(e);
	}
	
	public synchronized void mouseExited(MouseEvent e) {
		e.setSource(transferredComp);
		
		MouseListener[] listeners = transferredComp.getMouseListeners();
			
		for(int i=0; i<listeners.length; i++)
			listeners[i].mouseExited(e);
	}
	

}