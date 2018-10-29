package org.uengine.processdesigner;

import javax.swing.ImageIcon;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.Role;

import org.uengine.processdesigner.inputters.*;
import org.metaworks.*;
import org.metaworks.inputter.*;

/**
 * @author Jinyoung Jang
 */

public class RoleInformationPanel extends AbstractInformationPanel{

	SelectInput serviceTypeInputter;
	
	public RoleInformationPanel(ProcessDefinition pd){
		super(pd, GlobalContext.getLocalizedMessage("pd.roles.label"), Role.class);
		
		java.net.URL imgURL = getClass().getClassLoader().getResource("org/uengine/kernel/images/role.gif");
		setBorder(new IconTitledBorder(getBorder(), GlobalContext.getLocalizedMessage("pd.roles.label"), new ImageIcon(imgURL)));

		
		Type type = getApplication().getType();
		FieldDescriptor fd;

		fd = type.getFieldDescriptor("Identifier");
		ProcessVariableInput identifierInput = (ProcessVariableInput) fd.getInputter();
		identifierInput.setProcessDefinition(pd);
		
		//
//		final org.metaworks.inputter.java_lang_BooleanInput humanWorkerInputter = new org.metaworks.inputter.java_lang_BooleanInput(){
//			public void onValueChanged(){
//				Object val = getValue();
//		
//				try{
//					serviceTypeInputter.getComponent().setEnabled(!((Boolean)val).booleanValue());
//				}catch(Exception e){}				
//			}
//		};
//		
//		fd = table.getFieldDescriptor("ServiceType");
//		fd.setInputter(serviceTypeInputter = new SelectInput(new String[]{}){
//			public void setValue(Object value){
//				getComponent().setEnabled(true);
//				super.setValue(value);
//				humanWorkerInputter.onValueChanged();
//			}
//		});
//
//		fd = table.getFieldDescriptor("HumanWorker");
//		fd.setInputter(humanWorkerInputter);
	}
	
	protected Object[] getValues() {
		return getProcessDefinition().getRoles();
	}

	protected void applyValues(Object[] objs) {
		Role[] roles = new Role[objs.length];
		for(int i=0; i<objs.length; i++)
			roles[i] = (Role)objs[i];
					
		getProcessDefinition().setRoles(roles);	
	}
	
/*	protected String getLabel(Object value){
		return ((Role)value).getName();
	}*/
	
	public void onEdit(){
		try{
			ProcessDefinition procDef = (ProcessDefinition)ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity();
				
			if(procDef!=null){
				serviceTypeInputter.setSelectionsWithNull(procDef.getServiceDefinitions());

				//review:
				Type table = getApplication().getType();
				FieldDescriptor fd;
				fd = table.getFieldDescriptor("Identifier");
				fd.setInputter(new ProcessVariableInput());
			}
		}catch(Exception e){
		}		
	}
	
}