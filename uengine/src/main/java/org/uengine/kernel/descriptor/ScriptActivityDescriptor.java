package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ScriptActivity;
import org.uengine.kernel.ScriptUtil;
import org.uengine.kernel.ValidationContext;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;
import org.metaworks.*;

/**
 * @author Jinyoung Jang
 */

public class ScriptActivityDescriptor extends ActivityDescriptor{

	public ScriptActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(final ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		FieldDescriptor fd = getFieldDescriptor("Script");
		
		ScriptInput scriptingInputter = new ScriptingInputter2(pd);
		
		fd.setInputter(scriptingInputter);
		setFieldDisplayNames(ScriptActivity.class);
		
	}
	
	class ScriptingInputter2 extends ScriptInput{
		
		ProcessDesigner pd;
		ProcessInstance instance;
				
		public ScriptingInputter2(ProcessDesigner pd){
			super(pd);
			this.pd = pd;
		}
		
		protected org.apache.bsf.BSFManager createBSFManager() throws Exception{
			instance = ProcessInstance.create((ProcessDefinition)pd.getProcessDefinitionDesigner().getActivity(), "test instance", null);
					
			org.apache.bsf.BSFManager manager = super.createBSFManager();
			manager.declareBean("instance", instance, ProcessInstance.class);
			manager.declareBean("activity", new ScriptActivity(), Activity.class);
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
	
}