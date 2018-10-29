package org.uengine.processdesigner;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jinyoung Jang
 */

public class VerticalSeparator extends JPanel{

	public VerticalSeparator(){
		super();
		
		setBackground(Color.BLACK);
	}

	public void setSize(int w, int h){
		Dimension d = getParent().getSize();
				
		super.setSize(2, (int)d.getHeight());
	}
}