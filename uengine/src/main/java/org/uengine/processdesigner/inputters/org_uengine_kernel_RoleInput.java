package org.uengine.processdesigner.inputters;

import org.uengine.processdesigner.ProcessDesigner;

/**
 * @author Jinyoung Jang
 */

public class org_uengine_kernel_RoleInput extends RoleInput{

	public org_uengine_kernel_RoleInput(ProcessDesigner pd){
		super(pd);
	}
	public org_uengine_kernel_RoleInput(){
		super(ProcessDesigner.getInstance());
	}
}