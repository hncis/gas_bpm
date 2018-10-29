package org.uengine.processdesigner.inputters;

import org.uengine.kernel.MessageDefinition;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessDefinition;


import javax.swing.*;
import org.metaworks.inputter.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Jinyoung Jang
 */

public class MessageDefinitionInput extends SelectInput{

	/**
	 * 
	 * @uml.property name="cb"
	 * @uml.associationEnd 
	 * @uml.property name="cb" multiplicity="(0 1)"
	 */
	JComboBox cb;

	/**
	 * 
	 * @uml.property name="parametersInputter"
	 * @uml.associationEnd 
	 * @uml.property name="parametersInputter" multiplicity="(1 1)"
	 */
	ProcessVariableArrayInput parametersInputter;

	/**
	 * 
	 * @uml.property name="pdf"
	 * @uml.associationEnd 
	 * @uml.property name="pdf" multiplicity="(1 1)"
	 */
	ProcessDefinition pdf;

  

	public MessageDefinitionInput(ProcessDefinition pd, ProcessVariableArrayInput parametersInputter){
		super(new String[]{});		
		this.parametersInputter = parametersInputter;
		
		pdf = /*(ProcessDefinition)pd.getProcessDefinitionDesigner().getActivity();*/ pd;
		MessageDefinition[] mds = /*((ProcessDefinition)ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity()).getServiceDefinitions();*/
			pd.getMessageDefinitions();
		setSelectionsWithNull(mds);		
	}
	
	public Component getNewComponent() {
		cb = (JComboBox)super.getNewComponent();

		if(parametersInputter!=null)
		cb.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange()!=ItemEvent.SELECTED) return;
				
//				if(parametersInputter.getValue()!=null) return;
				
				MessageDefinition md = (MessageDefinition)getValue();
				MessageDefinition copyMD = md.copy();
				ParameterContext[] src = copyMD.getParameters();		
				parametersInputter.setValue(src);				
			}
		}); 			
		
		return cb;
	}	

}
	
	
	
