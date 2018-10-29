package org.uengine.processdesigner.inputters;

import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ServiceDefinition;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.metaworks.inputter.*;
import org.metaworks.*;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;

import com.ibm.wsdl.factory.*;
import javax.wsdl.xml.*;
import javax.xml.namespace.*;
import javax.wsdl.*;
import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class ServiceDefinitionInput extends SelectInput{

	/**
	 * 
	 * @uml.property name="cb"
	 * @uml.associationEnd 
	 * @uml.property name="cb" multiplicity="(0 1)"
	 */
	JComboBox cb;

	/**
	 * 
	 * @uml.property name="portTypeInputter"
	 * @uml.associationEnd 
	 * @uml.property name="portTypeInputter" multiplicity="(1 1)"
	 */
	PortTypeInput portTypeInputter;

	/**
	 * 
	 * @uml.property name="pdf"
	 * @uml.associationEnd 
	 * @uml.property name="pdf" multiplicity="(1 1)"
	 */
	ProcessDefinition pdf;

  

	public ServiceDefinitionInput(ProcessDefinition pd){
		this(pd, null);
	}
	public ServiceDefinitionInput(ProcessDefinition pd, PortTypeInput portTypeInputter){
		super(new String[]{});		
		this.portTypeInputter = portTypeInputter;
		
		pdf = /*(ProcessDefinition)pd.getProcessDefinitionDesigner().getActivity();*/ pd;
		ServiceDefinition[] sds = /*((ProcessDefinition)ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity()).getServiceDefinitions();*/
			pd.getServiceDefinitions();
		setSelectionsWithNull(sds);
	}

	public Component getNewComponent() {
		cb = (JComboBox)super.getNewComponent();

		if(portTypeInputter!=null)
		cb.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange()!=ItemEvent.SELECTED) return;
				ServiceDefinition svcdef = (ServiceDefinition)getValue();
				
				portTypeInputter.setServiceDefinition(svcdef);				
			}
		}); 			
		
		return cb;
	}
	
}
	
	
	
