package org.uengine.processdesigner.framework;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import org.uengine.processdesigner.ProxyPanel;

public class ProcessDesignerCanvas extends ProxyPanel{
	
	Vector paintDelegators = new Vector();
	
	public ProcessDesignerCanvas(){
		super(new AbsoluteLayout());
	}
	
	public void addPaintDelegator(PaintDelegator paintDelegator){
		paintDelegators.add(paintDelegator);
	}

	public void removePaintDelegator(PaintDelegator paintDelegator){
		paintDelegators.remove(paintDelegator);
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D)g;
		
		for(int i=0; i<paintDelegators.size(); i++){
			((PaintDelegator)(paintDelegators.get(i))).paint(this, g2);
		}
	}

	public Component add(Component arg0) {
		
		Component comp = super.add(arg0);
		
		getLayout().addLayoutComponent(null, comp);
		
		return comp;
	}
	
	public static Point getPointIn(Component comp){
		return new Point(comp.getX(), comp.getY() + comp.getHeight()/2);
	}

	public static Point getPointOut(Component comp){
		return new Point(comp.getX() + comp.getWidth(), comp.getY() + comp.getHeight()/2);
	}

	public static void main(String[] args) throws Exception{
		
		final ProcessDesignerCanvas pdc = new ProcessDesignerCanvas();
		
		ActivityViewObject act1 = new ActivityViewObject();
		ActivityViewObject act2 = new ActivityViewObject();
		
		ArrowViewObject arrow = new ArrowViewObject();
		
		pdc.addPaintDelegator(arrow);
		
		
		
		JFrame frame  = new JFrame();
		//frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(pdc);

		frame.setSize(new Dimension(500,500));
		//frame.pack();
		frame.setVisible(true);

		final JButton btn = new JButton("Obj A");
		btn.addMouseMotionListener(new MouseMotionAdapter(){

			public void mouseDragged(MouseEvent arg0) {
				btn.setLocation(btn.getX() + arg0.getX(), btn.getY() + arg0.getY());
				pdc.repaint();
			}
			
		});
		
		pdc.add(btn);

		final JButton btn2 = new JButton("Obj B");
		btn2.addMouseMotionListener(new MouseMotionAdapter(){

			public void mouseDragged(MouseEvent arg0) {
				btn2.setLocation(btn2.getX() + arg0.getX(), btn2.getY() + arg0.getY());

				pdc.repaint();
			}
			
		});
		
		pdc.add(btn2);

	     javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	pdc.setBounds(0,0, 500,500);
	        		btn.setBounds(20,50,100,100);
	        		btn2.setBounds(50,50,150,100);
	            }
	        });

	     
			arrow.setSource(btn);
			arrow.setTarget(btn2);

/*		JButton btn2 = new JButton("Obj B");
		btn2.setLocation(100,100);
		btn2.setSize(new Dimension(20,20));
*/		
//		frame.getContentPane().add(btn2);
		
		
		
	}
	
}
