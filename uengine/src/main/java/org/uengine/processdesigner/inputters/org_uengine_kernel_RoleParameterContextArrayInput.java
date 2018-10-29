package org.uengine.processdesigner.inputters;

import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.metaworks.inputter.MultipleInput;
import org.metaworks.*;
import org.uengine.kernel.RoleParameterContext;
import org.uengine.processdesigner.*;

/**
 * @author Jinyoung Jang
 */

public class org_uengine_kernel_RoleParameterContextArrayInput extends MultipleInput{

	Properties options = new Properties(){
		public String getProperty(String k){
			return "no";
		}
	};
	
	public org_uengine_kernel_RoleParameterContextArrayInput() throws Exception{
		super(
			new ObjectType(RoleParameterContext.class)
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
	
/*		FieldDescriptor fd;
		fd = getTable().getFieldDescriptor("Argument");
		fd.setUpdatable(false);
		fd = getTable().getFieldDescriptor("Type");
		fd.setUpdatable(false);
		fd.setInputter(new DataTypeInput());		

		fd = getTable().getFieldDescriptor("Role");
		fd.setInputter(new ProcessVariableInput(ProcessDesigner.getInstance()));*/
	}

	public Object getValue(){
		Instance[] vals = (Instance[])super.getValue();
			
		RoleParameterContext[] realValues = null;
		if(vals!=null){
			realValues = new RoleParameterContext[vals.length];
			for(int i=0; i<vals.length; i++){
				ObjectInstance rec = (ObjectInstance)vals[i];
				realValues[i] = (RoleParameterContext)rec.getObject();
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
			RoleParameterContext[] vals = (RoleParameterContext[])value;
			for(int i=0; i<vals.length; i++)
				valueVector.add(vals[i]);
		}
	
		Instance[] recValues = null;
		recValues = new Instance[valueVector.size()];
	
		int i=0;
		for(Iterator iter=valueVector.iterator(); iter.hasNext(); i++){
			Object item = iter.next();
		
			ObjectInstance rec = (ObjectInstance)((ObjectType)getTable()).createInstance();
			rec.setObject(item);
			recValues[i] = rec;
		}

		super.setValue(recValues);	
	}
}