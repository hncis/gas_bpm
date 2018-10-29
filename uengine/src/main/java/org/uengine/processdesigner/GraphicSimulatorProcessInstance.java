package org.uengine.processdesigner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.io.*;
import java.awt.*;

import org.uengine.kernel.Activity;
import org.uengine.kernel.DefaultProcessInstance;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.UEngineException;
import org.uengine.kernel.designer.*;

import org.uengine.processmanager.SimulatorTransactionContext;
import org.uengine.processmanager.ProcessTransactionContext;

import org.metaworks.*;
/**
 * @author Jinyoung Jang
 */

public class GraphicSimulatorProcessInstance extends SimulatorProcessInstance{
	
	static Thread currentThread;
	
	static HashMap statusColors = new HashMap();
	static{
		statusColors.put(Activity.STATUS_READY, Color.WHITE);
		statusColors.put(Activity.STATUS_COMPLETED, Color.GRAY);
		statusColors.put(Activity.STATUS_RUNNING, Color.GREEN);
		statusColors.put(Activity.STATUS_FAULT, Color.RED);
		statusColors.put(Activity.STATUS_SKIPPED, Color.BLUE);
		statusColors.put(Activity.STATUS_SUSPENDED, Color.YELLOW);
		statusColors.put(Activity.STATUS_TIMEOUT, Color.RED);
	}

	protected GraphicSimulatorProcessInstance (ProcessDefinition procDef, String instanceId) throws Exception{
		super(procDef, instanceId, null);
	}

	public void set(String scopeByTracingTag, String key, Serializable val) throws Exception{
		super.set(scopeByTracingTag, key, val);
		
		InputFormSubstance processVariableInputForm = getProcessVariableInputForm();

		if(processVariableInputForm!=null){			
			synchronized(processVariableInputForm){
				Instance rec = processVariableInputForm.getInstance();
	//			if(rec==null) rec = rec.getTable().createRecord();
				if(rec.getType().getFieldDescriptor(key)==null) return;
				rec.setFieldValue(key, val);
				processVariableInputForm.setInstance(rec);
			}
		}
	}

	protected void setFault(String scope, UEngineException value) throws Exception{
		super.setFault(scope, value);
		value.printStackTrace();
	}	
	
	protected void setStatus(String scope, String status) throws Exception{
		super.setStatus(scope, status);
				
		ProcessDefinitionDesigner pdDesigner = getDesigner();
		
		ActivityDesigner designer = pdDesigner;
		
		designer = getProcessDefinition().getActivity(scope).getDesigner();
		
/*		String[] scopes = scope.split("_");
		for(int i=1; i<scopes.length; i++){
			int intScope = Integer.parseInt(scopes[i]);		
			//review: sometimes the designer might be unsynchronized in its number of child.	
			designer = (ActivityDesigner)((ComplexActivityDesigner)designer).getChildDesigners().elementAt(intScope);
		}*/
		
		if(designer==null) return;
		
		if(designer.getComponent().getBackground()==Color.BLUE){
			//review: check the concurrency
			currentThread = Thread.currentThread(); 
			//review: may create uncompleted threads
			getSimulator().resumeBtn.setEnabled(true);				
			currentThread.suspend();
		}
		designer.getComponent().setBackground((Color)statusColors.get(status));			
	}
	
	public void putRoleMapping(RoleMapping roleMap) throws Exception{
		super.putRoleMapping(roleMap);

		if(getRoleInputForm()!=null){
			InputFormSubstance roleInputForm = getRoleInputForm();
			
			synchronized(roleInputForm){
				Instance rec = roleInputForm.getInstance();
//				if(rec==null) rec = rec.getTable().createRecord();
				if(rec.getType().getFieldDescriptor(roleMap.getName())==null) return;
				rec.setFieldValue(roleMap.getName(), roleMap.getEndpoint());
				roleInputForm.setInstance(rec);
			}
		}
	}

	public ProcessTransactionContext getProcessTransactionContext() {
		return new SimulatorTransactionContext();
	}


	/////// properties ////////

	InputFormSubstance processVariableInputForm;

	InputFormSubstance roleInputForm;

	ProcessDefinitionDesigner designer;

	ProcessSimulator simulator;

	public InputFormSubstance getProcessVariableInputForm() {
		return processVariableInputForm;
	}

	public InputFormSubstance getRoleInputForm() {
		return roleInputForm;
	}

	public void setProcessVariableInputForm(InputFormSubstance form) {
		processVariableInputForm = form;
	}

	public void setRoleInputForm(InputFormSubstance form) {
		roleInputForm = form;
	}

	public ProcessDefinitionDesigner getDesigner() {
		return designer;
	}

	public void setDesigner(ProcessDefinitionDesigner designer) {
		this.designer = designer;
	}

	public ProcessSimulator getSimulator() {
		return simulator;
	}

	public void setSimulator(ProcessSimulator simulator) {
		this.simulator = simulator;
	}

	public void fireFault(String tracingTag, UEngineException fault) throws Exception {
		super.fireFault(tracingTag, fault);
		fault.printStackTrace();
	}

}