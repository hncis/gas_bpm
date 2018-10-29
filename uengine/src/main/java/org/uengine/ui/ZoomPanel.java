package org.uengine.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;

public class ZoomPanel extends JPanel implements ComponentListener{
		private double indice_scaling = 0.8;
		private Dimension preferred;
		private Dimension minimum;
		
		public void miseAJour() {
			preferred = super.getPreferredSize();
			minimum = super.getMinimumSize();
		}
		
		public Dimension getPreferredSize() {
			if(preferred==null)
				miseAJour();
			
			return new Dimension((int)(preferred.width * indice_scaling), (int)(preferred.height * indice_scaling));
		}
		
		public Dimension getMinimumSize() {
			return new Dimension((int)(minimum.width * indice_scaling), (int)(minimum.height * indice_scaling));
		}
		
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			AffineTransform oldTransform = g2.getTransform();
			AffineTransform transform = AffineTransform.getScaleInstance(indice_scaling, indice_scaling);
			g2.setTransform(transform);
			g2.translate(-(getBounds().width / 4), 0.0); //add this line

			super.paint(g);
			g2.setTransform(oldTransform);
		}
		
		public double getScaling() {
			return indice_scaling;
		}

		public void setScaling(double d) {			
			indice_scaling = d;
			revalidate();
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
		 */
		public void componentHidden(ComponentEvent arg0) {
			
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
		 */
		public void componentMoved(ComponentEvent arg0) {
			
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
		 */
		public void componentResized(ComponentEvent arg0) {
			//miseAJour();
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
		 */
		public void componentShown(ComponentEvent arg0) {
			
		}

}