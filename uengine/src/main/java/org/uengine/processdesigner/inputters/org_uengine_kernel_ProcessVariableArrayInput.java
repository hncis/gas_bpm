package org.uengine.processdesigner.inputters;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.metaworks.FieldDescriptor;
import org.metaworks.inputter.InputterAdapter;
import org.uengine.kernel.AllParticipantRole;
import org.uengine.kernel.CompositeRole;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;

import org.uengine.processdesigner.*;

/**
 * @author Jinyoung Jang
 */

public class org_uengine_kernel_ProcessVariableArrayInput extends InputterAdapter implements PropertyChangeListener{

	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
	final public static int HUMANWORKER_ONLY = 2;
	final public static int WEBSERVICE_ONLY = 1;
	

	ProcessVariable[] processVariables; 
	JList list;
	ProcessDefinition processDefinition;
		public ProcessDefinition getProcessDefinition() {
			return processDefinition;
		}
		public void setProcessDefinition(ProcessDefinition processDefinition) {
			this.processDefinition = processDefinition;
		}
		
	String filter;
		public String getFilter() {
			return filter;
		}
		public void setFilter(String filter) {
			this.filter = filter;
			
			try{
				PropertyChangeEvent evt = new PropertyChangeEvent(this, "processVariables", null, processVariables);
				propertyChange(evt);
			}catch(Exception e){}
		}
			
	public org_uengine_kernel_ProcessVariableArrayInput(){
		this(ProcessDesigner.getInstance());
	}
		
	public org_uengine_kernel_ProcessVariableArrayInput(ProcessDefinition pd){
		this(pd, null);
	}
	
	public org_uengine_kernel_ProcessVariableArrayInput(ProcessDesigner procDesigner){
		this((ProcessDefinition)procDesigner.getProcessDefinitionDesigner().getActivity(), null);
	}
	
	public org_uengine_kernel_ProcessVariableArrayInput(ProcessDesigner procDesigner, String filter){
		this((ProcessDefinition)procDesigner.getProcessDefinitionDesigner().getActivity(), filter);
	}

	public org_uengine_kernel_ProcessVariableArrayInput(ProcessDefinition pd, String filter){
		super();
		
		setFilter(filter);
		setProcessDefinition(pd);		
		getProcessDefinition().addProperyChangeListener(this);
		
		Vector vtPVs = new Vector();

		processVariables = getProcessDefinition().getProcessVariables();
		if(processVariables==null) processVariables = new ProcessVariable[]{};
		
		for(int i=0; i<processVariables.length; i++){
			if(filter==null || filter.indexOf(processVariables[i].getType().getClass().getName()) > -1){
				vtPVs.add(processVariables[i]);
			}
		}
		
		ProcessVariable[] newprocessVariables = new ProcessVariable[vtPVs.size()];
		vtPVs.toArray(newprocessVariables);
		processVariables = newprocessVariables;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equalsIgnoreCase("processVariables")){
			
			try{
				processVariables = (ProcessVariable[])evt.getNewValue();
			}catch(Exception e){			
			}
			
			if(processVariables==null) processVariables = new ProcessVariable[]{};
			
			if(processVariables.length==0){
				ProcessVariable[] newprocessVariables = new ProcessVariable[processVariables.length];
				System.arraycopy(processVariables, 0, newprocessVariables, 0, processVariables.length);
				processVariables = newprocessVariables;
			}

			Vector vtPVs = new Vector();
			
			if(filter!=null){
				for(int i=0; i<processVariables.length; i++){
					if(getFilter().indexOf(processVariables[i].getType().getName()) > -1){
						vtPVs.add(processVariables[i]);
					}
				}
				
				ProcessVariable[] newprocessVariables = new ProcessVariable[vtPVs.size()];
				vtPVs.toArray(newprocessVariables);
				processVariables = newprocessVariables;
			}
			
			if(list!=null){
				Object existingValue = getValue();
				list.setListData(processVariables);
				setValue(existingValue);
			}
		}
	}
	
	public Object getValue(){
		if( list == null ) return null;
		if(list==null) return null;
		
		Object[] selectedValues=list.getSelectedValues();
		
		if(selectedValues.length == 0) return null;		

		ProcessVariable[] selectedProcessVariables = new ProcessVariable[selectedValues.length];
		for(int i=0; i<selectedValues.length; i++)
			selectedProcessVariables[i] = (ProcessVariable)selectedValues[i];
		
		return selectedProcessVariables;
	}
	
	public void setValue(Object obj){	
		if(list==null) return;
		
		if(obj==null){
			list.clearSelection();
			return;
		}
		
		HashMap processVariablesMap = new HashMap();
		
		ProcessVariable[] valueProcessVariables = (ProcessVariable[])obj;
		for(int i=0; i<valueProcessVariables.length; i++){
			processVariablesMap.put(valueProcessVariables[i].getName(), valueProcessVariables[i]);
		}
		
		Vector indices = new Vector();
		for(int i=0; i<processVariables.length; i++){
			if(processVariablesMap.containsKey(processVariables[i].getName())){
				indices.add(new Integer(i));
			}
		}
		
		int[] iIndices = new int[indices.size()];
		for( int i=0; i<indices.size(); i++ ){
			iIndices[i] = ((Integer)indices.get(i)).intValue();
		}
		
		list.setSelectedIndices(iIndices);
			
	}

	public Component getNewComponent(){
		list = new JList(processVariables);
				
//		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				onValueChanged();
			}			
		});
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(300, 80));

		return listScroller;
	}


}
	
	
	
