package org.uengine.processdesigner;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;

import javax.swing.*;

import org.metaworks.*;
import org.metaworks.inputter.*;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessDefinition;

/**
 * @author Jinyoung Jang
 */

public class ActivityInputForm extends InputForm{

	public ActivityInputForm(Type t){
		super(t);
	}

	public Instance getInstance() {

		Instance oldValue = getSourceInstance();
		ProcessDefinition oldDefinition = null;
		if(oldValue!=null){
			ActivityRecord oldActivityRecord  = (ActivityRecord)oldValue;
			oldDefinition = (ProcessDefinition) oldActivityRecord.getActivity().getProcessDefinition().clone();
		}

		Instance newValue = super.getInstance();

		ActivityRecord newActivityRecord  = (ActivityRecord)newValue;
		ProcessDefinition newDefinition = newActivityRecord.getActivity().getProcessDefinition();
		
		newDefinition.firePropertyChangeEvent(new PropertyChangeEvent(newDefinition, "", oldDefinition, newDefinition));
		
		return newValue;

	}

	public void setInstance(Instance record){
		//put the scripts if the scripting inputter is used
		ActivityRecord activityRecord  = (ActivityRecord)record;
		
		FieldDescriptor[] fields = getType().getFieldDescriptors();
		for(int i=0; i<fields.length; i++){
			Inputter inputter=fields[i].getInputter();
			
			if(inputter instanceof ScriptingValueInput){
//System.out.println("ActivityInputForm::setRecord. in scripingValueInput");
				((ScriptingValueInput)inputter).setScript(activityRecord.getActivity(), fields[i].getName());
			}
			
			if(inputter instanceof ActivityHandlingInputter){
				((ActivityHandlingInputter)inputter).setActivity(activityRecord.getActivity(), fields[i].getName());
			}
		}
		//
		
		super.setInstance(record);
	}


	protected void createForm() {
		setFieldOrdering(FIELD_ORDERING_SEQUENTIAL);

		super.createForm();
	}

	public void removeNotify() {
		super.removeNotify();
		try{
			//ObjectLifeCycleListener
		}catch(Exception e){
		}
	}


}

