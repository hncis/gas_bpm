package org.uengine.processdesigner;

import java.awt.datatransfer.Transferable;
import java.io.Serializable;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.designer.ComplexActivityDesigner;

/**
 * @author Jinyoung Jang
 */


public interface ActivityDesigner extends Transferable, Serializable{

	public Activity getActivity();
	public void setActivity(Activity value);
	public void setProcessInstance(ProcessInstance instance);
	public void onDropped(java.util.Vector components);
	public ComplexActivityDesigner getParentDesigner();
	public void openDialog();
	public java.awt.Component getComponent();

	
}

