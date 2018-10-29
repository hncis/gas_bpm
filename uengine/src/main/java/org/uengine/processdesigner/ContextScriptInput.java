/*
 * Created on 2004. 10. 9.
 */
package org.uengine.processdesigner;

/**
 * @author Jinyoung Jang
 */

import org.uengine.kernel.Activity;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ScriptUtil;
import org.uengine.kernel.ValidationContext;


public class ContextScriptInput extends ScriptInput{

	ProcessDefinition pd;
	ProcessInstance instance;
				
	public ContextScriptInput(ProcessDefinition pd){
		super(ProcessDesigner.getInstance());
		this.pd = pd;
	}
		
	protected org.apache.bsf.BSFManager createBSFManager() throws Exception{
		instance = ProcessInstance.create(pd, "test instance", null);
					
		org.apache.bsf.BSFManager manager = super.createBSFManager();
		manager.declareBean("instance", instance, ProcessInstance.class);
		manager.declareBean("activity", new DefaultActivity(), Activity.class);
		manager.declareBean("util", new ScriptUtil(), ScriptUtil.class);
					
		return manager;
	}	
				
	public void testScript() {
		super.testScript();
		ValidationContext vc = instance.getValidationContext();
		if(vc.size()>0)
			reportError(vc.toString());
	}

}
