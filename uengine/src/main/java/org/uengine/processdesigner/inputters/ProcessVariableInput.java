package org.uengine.processdesigner.inputters;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.metaworks.Instance;
import org.metaworks.inputter.DateInput;
import org.metaworks.inputter.InstanceSensitiveInputter;
import org.metaworks.inputter.TextInput;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityDueDatePointingProcessVariable;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.InstanceDueDatePointingProcessVariable;
import org.uengine.kernel.InstanceNamePointingProcessVariable;
import org.uengine.kernel.InstancePropertyPointingProcessVariable;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.kernel.RolePointingProcessVariable;
import org.uengine.processdesigner.*;
import org.uengine.ui.ObjectLifeCycleListener;
import org.uengine.util.ActivityFor;

/**
 * @author Jinyoung Jang
 */

public class ProcessVariableInput extends org.metaworks.inputter.SelectInput implements ObjectLifeCycleListener, InstanceSensitiveInputter{

	PropertyChangeListener processDefinitionListener;

	
	public ProcessVariableInput(){
		super(null);
	}
	
	public void setProcessDefinition(ProcessDefinition def){
		try{
			final ProcessDefinition finalDef = def;
			
			processDefinitionListener = new PropertyChangeListener(){
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equalsIgnoreCase("processVariables")){
						refreshVariableSelector(finalDef);
					}
					if(evt.getPropertyName().equalsIgnoreCase("roles")){
						refreshVariableSelector(finalDef);
					}
				}
			};
		
			def.addProperyChangeListener(processDefinitionListener);
			
		}catch(Exception e){
		}
		
		refreshVariableSelector(def);
	}
	
	public void refreshVariableSelector(ProcessDefinition def){
		ProcessVariable[] pvds = (def != null ? def.getProcessVariables() : new ProcessVariable[]{});
		Role[] roles = (def != null ? def.getRoles() : new Role[]{});
		
//		System.out.println("ProcessVariableInput.length of pvds: " + pvds.length);
		
		final ArrayList variables = new ArrayList();
		
		if(pvds!=null)
		for(int i=0; i<pvds.length; i++){
//			if(filter!=null && filter.indexOf(pvds[i].getType().getName()) > -1)
			variables.add(pvds[i]);
		}
		
		if(roles!=null)
		for(int i=0; i<roles.length; i++){
			RolePointingProcessVariable rolePV = new RolePointingProcessVariable();
			rolePV.setRole(roles[i]);
			variables.add(rolePV);
		}


		variables.add(new InstancePropertyPointingProcessVariable("InstanceId"));
		variables.add(new InstanceNamePointingProcessVariable());
		variables.add(new InstanceDueDatePointingProcessVariable());


		ActivityFor forLoopForAddingCommandVariables = new ActivityFor(){

			public void logic(Activity activity) {
				if (!(activity instanceof HumanActivity)) return;
				
				ProcessVariable commandVariable = new ProcessVariable();
				final HumanActivity finalActivity = (HumanActivity)activity;
				
				variables.add(new ActivityDueDatePointingProcessVariable(finalActivity));
			}
			
		};
		forLoopForAddingCommandVariables.run(def);

		String[] selections = new String[variables.size() + 1];
		selections[0] = "";
		Object[] values = new Object[variables.size() + 1];
		values[0] = null;

		if(values.length > 1)
		for(int i=1; i<variables.size() + 1; i++){
			selections[i] = variables.get(i-1).toString();
			values[i] = variables.get(i-1);
		}
		
		setSelections(selections);
		setValues(values);
	}
	
	public void setFilter(String filter){
		this.filter = filter;
		
		refreshVariableSelector((ProcessDefinition)ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity());
		
	}
	String filter = null;
	

	public void onDestroy() {
		try{
			final ProcessDefinition def = (ProcessDefinition)ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity(); 
			def.removeProperyChangeListener(processDefinitionListener);
		}catch(Exception e){
		}		
	}

	public void onInitialize() {
	}

	public void setInstance(Instance rec, String fieldName) {
		if (rec instanceof ActivityRecord) {

			ActivityRecord actRec = (ActivityRecord) rec;
			setProcessDefinition(actRec.getActivity().getProcessDefinition());
		}
	}

}