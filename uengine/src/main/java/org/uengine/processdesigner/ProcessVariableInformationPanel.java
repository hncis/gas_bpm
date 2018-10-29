package org.uengine.processdesigner;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;

import org.uengine.processdesigner.inputters.RoleInput;
import org.metaworks.*;
import org.metaworks.inputter.*;

/**
 * @author Jinyoung Jang
 */

public class ProcessVariableInformationPanel extends AbstractInformationPanel{

	SelectInput typeInputter;
		
	public ProcessVariableInformationPanel(ProcessDefinition pd){
		super(pd, GlobalContext.getLocalizedMessage("pd.processvariables.label"), ProcessVariable.class);
		
		java.net.URL imgURL = getClass().getClassLoader().getResource("org/uengine/kernel/images/variable.gif");
		setBorder(new IconTitledBorder(getBorder(), GlobalContext.getLocalizedMessage("pd.processvariables.label"), new ImageIcon(imgURL)));
		
		initialize();		
	}

	protected Object[] getValues() {
		ProcessVariable[] pvs = getProcessDefinition().getProcessVariables();
		if(pvs ==null || pvs.length ==0)
			return pvs;
		
		HashMap objTmp = new HashMap();
		String[] displayNames = new String[pvs.length];
		for(int i=0; i<pvs.length; i++){
			displayNames[i] = pvs[i].getDisplayName().getText();
			objTmp.put(displayNames[i], pvs[i]);
		}
		Arrays.sort(displayNames);
		
		ProcessVariable[] pvsTmp = new ProcessVariable[pvs.length];
		for(int i=0; i<displayNames.length; i++){
			pvsTmp[i] = (ProcessVariable)objTmp.get(displayNames[i]);
			pvsTmp[i].setName(pvsTmp[i].getName().trim());
		}
		
		return pvsTmp;
	}
	
	public void setProcessDefinition(ProcessDefinition pd) {		
		super.setProcessDefinition(pd);
		
		Type table = getApplication().getType();
		FieldDescriptor fd =  table.getFieldDescriptor("OpenRole");
		fd.setInputter(new RoleInput(pd));	
	}

	protected void applyValues(Object[] objs) {
		ProcessVariable[] pvs = new ProcessVariable[objs.length];
		for(int i=0; i<objs.length; i++){
			pvs[i] = (ProcessVariable)objs[i];
			pvs[i].setName(pvs[i].getName().trim());
		}
					
		getProcessDefinition().setProcessVariables(pvs);	
	}
	
/*	protected String getLabel(Object value){
		return value.toString();
	}*/

	public void onEdit(){
		try{
			ProcessDefinition procDef = (ProcessDefinition)ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity();
				
			if(procDef!=null){
				typeInputter.setSelectionsWithNull(procDef.getReservedXmlTypes());
			}
		}catch(Exception e){
		}		
	}

	protected void initialize() {
		Type table = getApplication().getType();
		FieldDescriptor fd;


	}

}

class DynamicInputter extends InputterAdapter{
	JPanel inputterPanel;
	Inputter inputter;
	
	public Component getNewComponent() {
		inputterPanel = new JPanel(new BorderLayout(0,0));
		setInputter(null);
		return inputterPanel;
	}
	
	public void setInputter(Inputter inputter){
		this.inputter = inputter;

		Component addingComp;
		if(inputter==null){
			addingComp = new JLabel("no inputter is available");
		}else{
			inputter.initialize(null);
			addingComp = inputter.getComponent();
		}

		inputterPanel.removeAll();
		inputterPanel.add("Center", addingComp);
		inputterPanel.revalidate();
	}
	
}