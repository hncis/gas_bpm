package org.uengine.kernel.designer;

import javax.swing.*;

import org.uengine.processdesigner.ActivityDesigner;
import org.uengine.processdesigner.ProxyPanel;

import java.awt.*;
import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class CompensatableActivityDesigner extends AllActivityDesigner{

	public CompensatableActivityDesigner(){
		super();
	}
	
/*	protected void initialize(){		
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));
		add(new Separator());		
		centerPanel = new ProxyPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		add(centerPanel);
		
	}
*/
	public void onDropped(Vector components){
		//number of child activities should be kept 2 or below
		if(getChildDesigners().size() == 2) 
			return;
			
		super.onDropped(components);
	}
	
	protected JPanel boxComponent(final ActivityDesigner designer){
		Component comp = designer.getComponent();
		JPanel compPanel = new ProxyPanel(new BorderLayout(0,0));

		if(getChildDesigners().size() == 0)
		 	compPanel.add("North", new JLabel("commitment"));
		if(getChildDesigners().size() == 1)
		 	compPanel.add("North", new JLabel("onCompensate"));
		compPanel.add("Center", comp);
		 	
		return compPanel;
	}
	
}

