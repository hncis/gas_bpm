package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.AssignActivity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.ProcessVariable;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;
import org.metaworks.*;

/**
 * @author Jinyoung Jang
 */

public class AssignActivityDescriptor extends ActivityDescriptor{

	public AssignActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		FieldDescriptor fd = getFieldDescriptor("Val");
				
		final ScriptingValueInput scriptingInputter 
			= new ScriptingValueInput(pd,
				"return \"value\";"
			);
		
		fd.setInputter(scriptingInputter);
		
		fd = getFieldDescriptor("Variable");
		fd.setInputter(new ProcessVariableInput(){
			public void onValueChanged(){
//System.out.println("AssignActivityDescriptor::in onValueChanged (35) getValue().class = " + getValue().getClass());
//System.out.println("AssignActivityDescriptor::in onValueChanged (36) getValue() = " + getValue());
				
				try{
					ProcessVariable var = (ProcessVariable)getValue();
				
					String clsName = var.getType().getName();
					if(clsName==null){
						clsName = var.getXmlBindingClassName();
					}
	
					StringBuffer sb = new StringBuffer();
					
					if(clsName!=null){
						sb.append("importPackage(Packages.java.lang);\n");
						sb.append("value = Class.forName(\"");
						sb.append(clsName);
						sb.append("\").newInstance();\n");
					}
					
					if(var.getType()!=null){
						try{
							Type descriptor = new ObjectType(var.getType());
							FieldDescriptor [] fds = descriptor.getFieldDescriptors();
							
							int stringType = FieldDescriptor.getMappingTypeOfClass(String.class);
							for(int i=0; i<fds.length; i++){
								sb.append("value.set");
								sb.append(fds[i].getName());
								sb.append("(");
								if(fds[i].getType() == stringType){
									sb.append("\"\"");
								}
								sb.append(");\n");	
							}
												
						}catch(Exception e){
						}
					}				
					
					sb.append("return value;");
					
					//scriptingInputter.setScript(sb.toString());
					
				}catch(Exception e){
					//e.printStackTrace();
				}
			}
		});
		setFieldDisplayNames(AssignActivity.class);
	}
	
}