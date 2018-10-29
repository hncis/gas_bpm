package org.uengine.processdesigner.inputters;

import org.uengine.processdesigner.*;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.metaworks.inputter.InstanceSensitiveInputter;
import org.metaworks.inputter.MultipleInput;
import org.metaworks.*;

import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class ProcessVariableArrayInput extends MultipleInput implements InstanceSensitiveInputter{

	Properties options = new Properties(){
		public String getProperty(String k){
			return "no";
		}
	};

	public ProcessVariableArrayInput() throws Exception{
		super(
				new ObjectType(ParameterContext.class)
			/*new Table("", new FieldDescriptor[]{
				new FieldDescriptor("argument", 
					new Object[]{
						"updatable", new Boolean(false)
					}
				),
				new FieldDescriptor("variable", 
					new Object[]{
						"inputter", new ProcessVariableInput(pd)
					}
				)
			})*/
			);

		FieldDescriptor fd;
		fd = getTable().getFieldDescriptor("Argument");
		fd.setUpdatable(false);
		fd = getTable().getFieldDescriptor("Type");
		fd.setUpdatable(false);
		fd.setInputter(new DataTypeInput());		

		fd = getTable().getFieldDescriptor("Variable");
		fd.setInputter(new ProcessVariableInput());		

	}
	
	public void setProcessDefinition(ProcessDefinition pd){		
		FieldDescriptor fd = getTable().getFieldDescriptor("Variable");
		((ProcessVariableInput)fd.getInputter()).setProcessDefinition(pd);
	}
	
	
	
	public void setHiddenFields(String[] fieldNames){
		FieldDescriptor fd;
		for(int i=0; i<fieldNames.length; i++){
			fd = getTable().getFieldDescriptor(fieldNames[i]);
			fd.setAttribute("hidden", new Boolean(true));
		}
	}
	
	public Object getValue(){
		Instance[] vals = (Instance[])super.getValue();
				
		ParameterContext[] realValues = null;
		if(vals!=null){
			realValues = new ParameterContext[vals.length];
			for(int i=0; i<vals.length; i++){
				ObjectInstance rec = (ObjectInstance)vals[i];
				realValues[i] = (ParameterContext)rec.getObject();
			}
		}		
		return realValues;	
	}
	
	public void setValue(Object value){		
		if(value==null){
			super.setValue(null);
			return;
		}
		
		Vector valueVector = new Vector();
		
		if(value instanceof Object[]){
			Object[] vals = (Object[])value;
			for(int i=0; i<vals.length; i++)
				valueVector.add(vals[i]);
		}else{
			ParameterContext[] vals = (ParameterContext[])value;
			for(int i=0; i<vals.length; i++)
				valueVector.add(vals[i]);
		}
		
		Instance[] recValues = null;
		recValues = new Instance[valueVector.size()];
		
		int i=0;
		for(Iterator iter=valueVector.iterator(); iter.hasNext(); i++){
			Object item = iter.next();
			
			if(item instanceof ProcessVariable){ //for old version
				 ParameterContext making = new ParameterContext();
				 making.setVariable((ProcessVariable)item);
				 item = making;
			}
			
			ObjectInstance rec = (ObjectInstance)((ObjectType)getTable()).createInstance();
			rec.setObject(item);
			recValues[i] = rec;
		}

		super.setValue(recValues);	
	}

	public void setInstance(Instance rec, String fieldName) {
		if (rec instanceof ActivityRecord) {

			ActivityRecord actRec = (ActivityRecord) rec;
			setProcessDefinition(actRec.getActivity().getProcessDefinition());
		}
	}
}
