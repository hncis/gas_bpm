package org.uengine.processdesigner.inputters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.metaworks.inputter.AbstractComponentInputter;
import org.uengine.kernel.Activity;
import org.uengine.kernel.And;
import org.uengine.kernel.Condition;
import org.uengine.kernel.Evaluate;
import org.uengine.kernel.Or;
import org.uengine.kernel.Otherwise;
import org.uengine.kernel.RoleExist;
import org.uengine.kernel.SwitchActivity;
import org.uengine.processdesigner.ActivityHandlingInputter;
import org.uengine.processdesigner.ConditionTable;

/**
 * @author Jinyoung Jang
 */

public class org_uengine_kernel_ConditionArrayInput extends AbstractComponentInputter implements ActivityHandlingInputter, PropertyChangeListener{
	ConditionTable table=null;
	Activity editingActivity;
	final static int START_INDEX = 2;
	
	public Object getValue() {
		DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
		Vector rows = tableModel.getDataVector();
		
		HashMap map = new HashMap();
		Or ors = new Or();
		for(int i=0; i<rows.size(); i++){
			Vector cols = (Vector)rows.get(i);
			String key = (String)cols.get(1);
			
			boolean isOtherwise = false;
			
			if(key==null)continue;
			if(!map.containsKey(key)){
				Or or = new Or();
				And and = new And();
				for(int j=START_INDEX;j<cols.size();j++){
					if(cols.get(j) instanceof Otherwise){
						isOtherwise = true;
						break;
					}else if(cols.get(j) instanceof RoleExist){
						RoleExist role = (RoleExist)cols.get(j);
						if(role!=null){
							and.addCondition(role);
						}
					}else{
						Evaluate eval = (Evaluate)cols.get(j);
						if(eval!=null){
							and.addCondition(eval);
						}
					}
				}
				if(!isOtherwise && and.getConditionsVt().size()>0){
					or.addCondition(and);
				}
				
				if(isOtherwise){
					Otherwise otherwise = new Otherwise();
					map.put(key, otherwise);
					otherwise.getDescription().setText(key);
					ors.addCondition(otherwise);
				}else{
					map.put(key, or);
					or.getDescription().setText(key);
					ors.addCondition(or);
				}

			}else{
				And and = new And();
				for(int j=START_INDEX;j<cols.size();j++){
					if(cols.get(j) instanceof Evaluate){
						Evaluate eval = (Evaluate)cols.get(j);
						if(eval!=null){
							and.addCondition(eval);
						}
					}else{
						RoleExist role = (RoleExist)cols.get(j);
						if(role!=null){
							and.addCondition(role);
						}
					}
				}
				if(and.getConditionsVt().size()>0){
					((Or)map.get(key)).addCondition(and);
				}
				
			}
			
		}
		
		return ors.getConditions();
	}

	public void setValue(Object data) {
		if(data!=null){
			Condition[] conditions = (Condition[])data;
			
			int rowNum=0;
			for(int i=0;i<conditions.length;i++){
				Condition condition = conditions[i];
				
				if(condition instanceof Evaluate 
						|| condition instanceof Otherwise 
						|| condition instanceof RoleExist){//������ ��츸
					Or wrapperOr = new Or();
					And wrapperAnd = new And();
					wrapperOr.addCondition(wrapperAnd);
					wrapperOr.setDescription(condition.getDescription());
										
					if(condition instanceof Evaluate || condition instanceof RoleExist){
						condition.setDescription(null);
						wrapperAnd.addCondition(condition);
					}else {
						wrapperAnd.addCondition(new Otherwise());
					}
						
					condition = wrapperOr;
				}
				
				Or or = (Or)condition;
				String key = or.getDescription().getText();
				
				Condition[] rows = or.getConditions();
				for(int j=0;j<rows.length;j++){
					And and = (And)rows[j];
					
					Condition[] cols = and.getConditions();
					//Default RowCount�� �Ѿ��츦 '�ؼ� Row Count�� ������� �Ѵ�.
					if(((DefaultTableModel)table.getModel()).getRowCount()<=rowNum)
						((DefaultTableModel)table.getModel()).setRowCount(rowNum+1);
					for(int k=0;k<cols.length;k++){
						Condition cond = cols[k];
						if(cond!=null)
							table.setValueAt(cond, rowNum, k+START_INDEX);
					}
					
					table.setValueAt(key, rowNum, 1);
					rowNum++;
				}
			}
			//((DefaultTableModel)table.getModel()).setRowCount(rowNum+1);
			
		}
		
	}
	
	public Component getNewComponent() {
		
		/*ProcessVariableInput pvi = new ProcessVariableInput(ProcessDesigner.getInstance());
		pvi.getComponent();
		ProcessVariable pv = (ProcessVariable)pvi.getValue();
		return pvi.getComponent();*/
		
		table = new ConditionTable(true);
		
		JScrollPane conditionTablePane = new JScrollPane(table);
		
		conditionTablePane.setPreferredSize(new Dimension(2000,200));
		//JScrollPane pane2 = new JScrollPane(conditionTablePane);
		//pane2.setPreferredSize(new Dimension(500,200));
		//return pane2;
		
		return conditionTablePane;
	}

	public void setActivity(Activity activity, String propertyName) {
		editingActivity = activity;
		if(editingActivity!=null && editingActivity instanceof SwitchActivity){
			SwitchActivity switchActivity = (SwitchActivity)editingActivity;
			switchActivity.addProperyChangeListener(this);
			propertyChange(new PropertyChangeEvent(switchActivity, "extendedAttribute", null, null));
		}
		
		table.setProcessDefinition(activity.getProcessDefinition());
	}

	public void propertyChange(PropertyChangeEvent pce) {		
		if(editingActivity!=null && editingActivity instanceof SwitchActivity && pce.getPropertyName().equals("extendedAttribute")){
			SwitchActivity switchActivity = (SwitchActivity)editingActivity;

			Map extAttrs = ((SwitchActivity)editingActivity).getExtendedAttributes();
			
			String[] conditionNames = new String[switchActivity.getChildActivities().size()];
			for(int i=0; i<switchActivity.getChildActivities().size(); i++){
				String attrKey = "conditionDescriptions_" + i;
				String conditionDescription = null;
				if(extAttrs!=null && extAttrs.containsKey(attrKey)){
					conditionDescription = (String)extAttrs.get(attrKey);
				}
				
				if(conditionDescription == null){
					if(switchActivity.getConditions()!=null && switchActivity.getConditions().length > i){
						conditionDescription = switchActivity.getConditions()[i].toString();
					}
				}
				
				conditionNames[i] = (conditionDescription !=null ? conditionDescription : ("condition" + i));
			}
			
			table.setConditionNames(conditionNames);
		}
	}
	
}