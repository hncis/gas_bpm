package org.uengine.processdesigner.inputters;

import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ServiceDefinition;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.metaworks.inputter.*;
import org.metaworks.*;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;

import com.ibm.wsdl.factory.*;
import javax.xml.namespace.*;
import javax.wsdl.xml.*;
import javax.wsdl.*;
import java.util.*;
import java.util.List;

/**
 * @author Jinyoung Jang
 */

public class PortTypeInput extends SelectInput{

	/**
	 * 
	 * @uml.property name="cb"
	 * @uml.associationEnd 
	 * @uml.property name="cb" multiplicity="(0 1)"
	 */
	JComboBox cb;

	/**
	 * 
	 * @uml.property name="operationInputter"
	 * @uml.associationEnd 
	 * @uml.property name="operationInputter" multiplicity="(1 1)"
	 */
	SelectInput operationInputter;

	/**
	 * 
	 * @uml.property name="portTypesArr"
	 * @uml.associationEnd 
	 * @uml.property name="portTypesArr" multiplicity="(0 -1)"
	 */
	PortType[] portTypesArr;

	/**
	 * 
	 * @uml.property name="operationsArr"
	 * @uml.associationEnd 
	 * @uml.property name="operationsArr" multiplicity="(0 -1)"
	 */
	Operation[] operationsArr;

	/**
	 * 
	 * @uml.property name="parameterInputter"
	 * @uml.associationEnd 
	 * @uml.property name="parameterInputter" multiplicity="(0 1)"
	 */
	ProcessVariableArrayInput parameterInputter;

	public PortTypeInput(ProcessDesigner pd, SelectInput operationInputter, ProcessVariableArrayInput parameterInputter){
		super(new String[]{});		
		this.operationInputter = operationInputter;
		this.parameterInputter = parameterInputter;
	}

	public Component getNewComponent() {
		cb = (JComboBox)super.getNewComponent();
		cb.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange()!=ItemEvent.SELECTED) return;
				
				if(portTypesArr==null) return;
				PortType portType = (PortType)portTypesArr[cb.getSelectedIndex()];
				if(portType==null) return;
				
				List operations = portType.getOperations();
				Operation[] operationArr = new Operation[operations.size()]; 
				operations.toArray(operationArr);
				//operationInputter.setValues(operationArr);
				
				String selections[] = new String[operationArr.length];
				for(int i=0; i<operationArr.length; i++){
					Operation operation = operationArr[i];
					selections [i] = operation.getName();
				}
				operationsArr = operationArr;
				operationInputter.setSelections(selections);
			}
		});
		
		final JComboBox operationCB = (JComboBox)operationInputter.getComponent();
		operationCB.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange()!=ItemEvent.SELECTED) return;
					
			//	PortType portType = (PortType)portTypesArr[cb.getSelectedIndex()];
				if(operationsArr==null) return;
				Operation operation = operationsArr[operationCB.getSelectedIndex()];
				if(operation==null) return;
				
				Input input = operation.getInput();
				Message message = input.getMessage();
				List parts = message.getOrderedParts(null);

				Type paramTB = parameterInputter.getTable();
				ParameterContext[] parameterContexts = new ParameterContext[parts.size()];
				int i=0;
				
				for(Iterator iter = parts.iterator(); iter.hasNext();){
//				for(Iterator iter = parts.values().iterator(); iter.hasNext();){
					Part part = (Part)iter.next();
					part.getDocumentationElement();
					
					parameterContexts[i] = new ParameterContext();
					parameterContexts[i].getArgument().setText(part.getName());
					parameterContexts[i].setType(part.getTypeName());
					i++;
				}
				
				parameterInputter.setValue(parameterContexts);			
			}
		});		 			

		return cb;
	}

	public void setServiceDefinition(ServiceDefinition svcdef){
		try{
			Definition def = svcdef.getDefinition();
			Map portTypes = def.getPortTypes();
			Collection values = portTypes.values(); // QNames for each Port Type
			PortType [] selectionValues = new PortType[values.size()];
			values.toArray(selectionValues);
							
			String [] selections = new String[selectionValues.length];
			for(int i=0; i<selectionValues.length; i++){
				PortType portType = (PortType)selectionValues[i];
				selections[i] = portType.getQName().getLocalPart();
			}
							
			//portTypeInputter.setValues(selectionValues);
			portTypesArr = selectionValues;
			setSelections(selections);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}

}
	
	
	
