package org.uengine.processdesigner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

import org.uengine.kernel.GlobalContext;


class Splash extends JFrame {

    public Splash(){
    	super("uEngine Process Designer");
    	
/*    	ImageIcon icon = new ImageIcon(getClass().getResource(
			GlobalContext.getPropertyString("pd.splashlogo", DesignerLabel.SPLASHLOGO)) + ".gif");
    	setIconImage(icon.getImage());	//set the ganttproject icon
*/    	setDefaultLookAndFeelDecorated(false);
    	setUndecorated(true);
    	setAlwaysOnTop(true);
    	getRootPane().setWindowDecorationStyle(JRootPane.NONE); //set no border

		String splashImageLoc = GlobalContext.getPropertyString("pd.splashlogo.image", DesignerLabel.SPLASHLOGO) + ".gif";        
 		java.net.URL splashImageURL = ActivityLabel.class.getClassLoader().getResource(splashImageLoc);
    	ImageIcon splashImage = new ImageIcon(splashImageURL);
    	
        JLabel l = new JLabel(splashImage)/* {
        	public void paint (Graphics g) {
        		super.paint(g);
        		Graphics2D g2 = (Graphics2D) g;
        		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        		Font font = new Font("Arial", Font.BOLD + Font.ITALIC, 22);
        		g2.setFont(font);
        		g2.setColor(Color.black);
                FontRenderContext frc = g2.getFontRenderContext();
                TextLayout layout = new TextLayout(GlobalContext.STR_UENGINE_VER, font, frc);
                Rectangle2D bounds = layout.getBounds();
        		g2.drawString(GlobalContext.STR_UENGINE_VER, (int) (getSize().getWidth() - bounds.getWidth()-5), 180);
//[original]
//				g2.drawString(GlobalContext.STR_UENGINE_VER, (int) (getSize().getWidth() - bounds.getWidth()-5), 180);
//[end]
        	}
        }*/;
        
        
        getContentPane().add(l, BorderLayout.CENTER);
        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();

        // Put image at the middle of the screen
        setLocation(screenSize.width/2 - (labelSize.width/2),
                    screenSize.height/2 - (labelSize.height/2));
		
    }
    

    public void close() {
    	  new Thread(){

			public void run() {
				try {
					sleep(4500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
		        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				try {
					int velocity = (getY() / 30);
					int diff = (int) (velocity * 0.6);
					int bounce = 3;
					int sleep = 20;

					velocity = velocity * (-1) * bounce; 
					for(int i=getY(); i<screenSize.getHeight(); i+=(velocity+=diff)){
						setLocation(getX(), i);
						sleep(sleep);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				setVisible(false);
		    	dispose();
			}
    		  
    		  
    	  }.start();
          
    }

}

