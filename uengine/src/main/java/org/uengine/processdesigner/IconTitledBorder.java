package org.uengine.processdesigner;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class IconTitledBorder extends TitledBorder {
	
	ImageIcon icon;
	Border border;
	public IconTitledBorder(Border border, String title, ImageIcon icon){
		super("        " + title);
		this.icon = icon;
		this.border = border;
	}
	
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		
		super.paintBorder(c, g, x, y, width, height);
		Insets insets = getBorderInsets(c);
		
		int tileW = icon.getIconWidth()/2;
        int tileH = icon.getIconHeight()/2;
        g.translate(x-tileW+10, y + 3);
        
        // Paint Icon
        Graphics cg;
        cg = g.create();
        cg.setClip(0, 0, width, insets.top);
        icon.paintIcon(c, cg, x + 8, y);
        cg.dispose();
        g.translate(x+tileW-10, y);
        
		
    }
}
