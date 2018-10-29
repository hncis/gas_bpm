package org.uengine.processdesigner.framework;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.uengine.processdesigner.ProcessDesigner;

public class Movable extends MouseAdapter implements MouseMotionListener{

	Component movableComponent;
	Point p;
	
	public Movable(Component comp){
		this.movableComponent = comp;
	}

	public void mouseDragged(MouseEvent e) {
		movableComponent.setLocation(
//				movableComponent.getX() - movableComponent.getWidth() / 2 + e.getX(), 
//				movableComponent.getY() - movableComponent.getHeight() / 2 + e.getY()
				movableComponent.getX() - p.x + e.getX(), 
				movableComponent.getY() - p.y + e.getY()
		);
	}
	
	public void mousePressed(MouseEvent e) {
		this.p = e.getPoint();
	}
	
	public static void enable(Component comp){
		comp.addMouseMotionListener((MouseMotionListener) new Movable(comp));
	}

	public void mouseMoved(MouseEvent e) {
		
	}
	
}
