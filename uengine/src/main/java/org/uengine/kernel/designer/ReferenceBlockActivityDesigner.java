package org.uengine.kernel.designer;

import java.awt.Component;

import javax.swing.JLabel;

import org.uengine.processdesigner.ActivityLabel;

/**
 * @author Jinyoung Jang
 */

public class ReferenceBlockActivityDesigner extends ComplexActivityDesigner {

	public ReferenceBlockActivityDesigner(){
		super();	
	}
	
	protected void initialize(){
		super.initialize();
		
		add(new ActivityLabel(org.uengine.kernel.ReferenceBlockActivity.class), 0);
	}

}
