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
import org.metaworks.Instance;
import org.metaworks.inputter.InputterAdapter;
import org.metaworks.inputter.InstanceSensitiveInputter;
import org.uengine.kernel.AllParticipantRole;
import org.uengine.kernel.CompositeRole;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.Role;

import org.uengine.processdesigner.*;

/**
 * @author Jinyoung Jang
 */

public class RoleInput extends InputterAdapter implements PropertyChangeListener, InstanceSensitiveInputter{
	final public static int HUMANWORKER_ONLY = 2;
	final public static int WEBSERVICE_ONLY = 1;
	final public static int ALL = 0;
	

	Role[] roles = new Role[]{}; 
	JList list;
	ProcessDefinition processDefinition;
		public ProcessDefinition getProcessDefinition() {
			return processDefinition;
		}
		public void setProcessDefinition(ProcessDefinition processDefinition) {
			this.processDefinition = processDefinition;
			
			if(processDefinition==null) return;

			processDefinition.addProperyChangeListener(this);
			
			roles = getProcessDefinition().getRoles();
			if(roles==null) roles = new Role[]{};
			
			Role[] newRoles = new Role[roles.length+1];
			System.arraycopy(roles, 0, newRoles, 0, roles.length);
			newRoles[roles.length] = AllParticipantRole.getInstance();		
			roles = newRoles;
			
			if(list!=null)
				list.setListData(roles);
		}
	
	public RoleInput(){
		this((ProcessDefinition)null);
	}
		
	public RoleInput(ProcessDefinition pd){
		this(pd, ALL);
	}
	
	public RoleInput(ProcessDesigner procDesigner){
		this((ProcessDefinition)procDesigner.getProcessDefinitionDesigner().getActivity(), ALL);
	}
	
	public RoleInput(ProcessDesigner procDesigner, int filter){
		this((ProcessDefinition)procDesigner.getProcessDefinitionDesigner().getActivity(), filter);
	}

	public RoleInput(ProcessDefinition pd, int filter){
		super();
		
		setProcessDefinition(pd);		

	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equalsIgnoreCase("roles")){
			
			try{
				roles = (Role[])evt.getNewValue();
			}catch(Exception e){			
			}
			
			if(roles==null) roles = new Role[]{};
			
			if(roles.length==0 || !(roles[roles.length-1] instanceof AllParticipantRole)){
				Role[] newRoles = new Role[roles.length+1];
				System.arraycopy(roles, 0, newRoles, 0, roles.length);
				newRoles[roles.length] = AllParticipantRole.getInstance();		
				roles = newRoles;
			}
			
			if(list!=null){
				Object existingValue = getValue();
				list.setListData(roles);
				setValue(existingValue); // ����Ʈ���� �ٲ�� ���� ��x ���ð��� ���󰡴� ��� �߻�
			}
		}
	}
	
	public Object getValue(){
		if( list == null ) return null;
		if(list==null) return null;
		
		int[] selectedIndeces=list.getSelectedIndices();
		
		if(selectedIndeces.length == 0) return null;		
		if(selectedIndeces.length == 1) return list.getSelectedValue();		
		if(selectedIndeces.length > 1){
			CompositeRole cr = new CompositeRole();
			Object[] objRoles = list.getSelectedValues();
			Role[] roleRoles = new Role[objRoles.length];

			for(int i=0; i<objRoles.length; i++)
				roleRoles[i] = (Role)objRoles[i];
			
			cr.setRoles(roleRoles);
			
			return cr;
		}else{
			return null;
		}
	}
	
	public void setValue(Object obj){	
		if(list==null) return;
		
		if(obj==null) list.clearSelection();
		
		HashMap rolesMap = new HashMap();
		
		if(obj instanceof CompositeRole){
			CompositeRole cr = (CompositeRole)obj;
			Role[] arrRole = cr.getRoles();
			for(int i=0; i<arrRole.length; i++){
				rolesMap.put(arrRole[i].getName(), arrRole[i]);
			}
		}else if(obj instanceof Role){
			Role role = (Role)obj;
			rolesMap.put(role.getName(), role);
		}
		
		Vector indices = new Vector();
		for(int i=0; i<roles.length; i++){
			if(rolesMap.containsKey(roles[i].getName())){
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
		list = new JList(roles);
				
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
	
	
	public void setInstance(Instance rec, String fieldName) {
		if (rec instanceof ActivityRecord) {

			ActivityRecord actRec = (ActivityRecord) rec;
			setProcessDefinition(actRec.getActivity().getProcessDefinition());
		}
	}
	

}
	
	
	
