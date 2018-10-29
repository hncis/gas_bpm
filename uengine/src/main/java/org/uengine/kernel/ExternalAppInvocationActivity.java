package org.uengine.kernel;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ValidationContext;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.util.SystemLauncher;
import org.uengine.util.UEngineUtil;


public class ExternalAppInvocationActivity extends DefaultActivity {

	public ExternalAppInvocationActivity(){
		setName("ExternalAppInvocation");
		setDefaultDirectory(GlobalContext.getPropertyString("application.execute.directory", ""));
	}
	
	String applicationPath;
		public String getApplicationPath() {
			return applicationPath;
		}
		public void setApplicationPath(String applicationPath) {
			this.applicationPath = applicationPath;
		}
	
	ParameterContext[] arguments;
		public ParameterContext[] getArguments() {
			return arguments;
		}
		public void setArguments(ParameterContext[] arguments) {
			this.arguments = arguments;
		}
	
	ProcessVariable variableOut;
		public ProcessVariable getVariableOut() {
			return variableOut;
		}
		public void setVariableOut(ProcessVariable variableOut) {
			this.variableOut = variableOut;
		}
		
	String defaultDirectory; 
		public String getDefaultDirectory() {
			return defaultDirectory;
		}
	
		public void setDefaultDirectory(String defaultDirectory) {
			this.defaultDirectory = defaultDirectory;
		}

	@Override
	public void executeActivity(ProcessInstance instance) throws Exception {

		final ProcessInstance thisInstance = instance;
		
		final Vector outStrings =new Vector();
		SystemLauncher sl =new SystemLauncher(){
			public void write(String out){
				outStrings.add(out);
			}

			/* (non-Javadoc)
			 * @see org.uengine.util.SystemLauncher#completed()
			 */
			/**
			 */
			@Override
			public void completed() {
				try {
					fireComplete(thisInstance);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		};
		
		String aed = getDefaultDirectory();
		
		if(!aed.endsWith("\\") && !aed.endsWith("/")) aed = aed +System.getProperty("file.separator");
		
		String command = "cmd /c "+System.getProperty(aed+getApplicationPath(),aed+getApplicationPath());
		for (int i = 0; i < getArguments().length; i++) {
			String pvName = getArguments()[i].getVariable() !=null? getArguments()[i].getVariable().getName():"";
			if(UEngineUtil.isNotEmpty(pvName))
				command+= " "+instance.get("",pvName);
		}
		
		sl.run(command);
		
		String outString =null; 
		for (int i = 0; i < outStrings.size(); i++) {
			outString +=(String)outStrings.get(i);
			instance.addDebugInfo(outString);
		}
				
		if(getVariableOut()!=null)
			instance.set("",getVariableOut().getName(),(Serializable)outString);
		
	}
	
	@Override
	public ValidationContext validate(Map options) {
		ValidationContext vc = super.validate(options);
		
		
		return vc;
	}

}
