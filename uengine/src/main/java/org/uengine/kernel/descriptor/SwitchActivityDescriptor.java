package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.SwitchActivity;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;
import org.metaworks.*;
import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class SwitchActivityDescriptor extends ActivityDescriptor{

	public SwitchActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		//FieldDescriptor fd = getFieldDescriptor("Conditions");
		
		/*Table table = new Table(
			"",		
			new FieldDescriptor[]{
				new FieldDescriptor("Conditions")
			}
		);

		table.getFieldDescriptor("Conditions").setInputter(new ScriptingValueInput(pd));
		
		fd.setInputter(new MultipleInput(table){
		
			
			public Object getValue(){
				Record[] records = (Record[])super.getValue();
				Condition[] conditions = new Condition[records.length];
				
				for(int i=0; i<records.length; i++)
					conditions[i] = (Condition)records[i].getFieldValueObject("Conditions");
					
				return conditions;
			}
		});*/
		
		/*ScriptingValueInput scriptingInputter 
			= new ScriptingValueInput(pd,
				"importPackage(Packages.org.uengine.kernel);\n" +
				"conditions = new java.util.Vector();\n" +
				"conditions.add(new Evaluate(\"expression\", \"value\"));\n" +
				"return conditions;"
			){
			public void setValue(Object val){
				
				if(val instanceof Vector){
					Vector vectVal = (Vector)val;
					
					Condition[] conditions = new Condition[vectVal.size()];
					int i=0;		
					for(Enumeration enum = vectVal.elements(); enum.hasMoreElements();){
						Object item = (Object)enum.nextElement();
						conditions[i++] = (Condition)item;
					}
					
					super.setValue(conditions);
				}else
					super.setValue(val);				
			}			
			
			public Object getValue(){
				Object val = super.getValue();
				
				if(val instanceof Vector){
					Vector vectVal = (Vector)val;
					
					Condition[] conditions = new Condition[vectVal.size()];
					int i=0;		
					for(Enumeration enum = vectVal.elements(); enum.hasMoreElements();){
						Object item = (Object)enum.nextElement();
						conditions[i++] = (Condition)item;
					}
					
					return (conditions);
				}else
					return val;
			}
			
		};
		
		fd.setInputter(scriptingInputter);*/		

		setFieldDisplayNames(SwitchActivity.class);
	}

}