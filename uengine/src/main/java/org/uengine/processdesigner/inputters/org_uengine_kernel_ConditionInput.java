package org.uengine.processdesigner.inputters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.nio.charset.CodingErrorAction;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.metaworks.ObjectInstance;
import org.metaworks.Instance;
import org.metaworks.inputter.AbstractComponentInputter;
import org.metaworks.inputter.InstanceSensitiveInputter;
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

public class org_uengine_kernel_ConditionInput extends AbstractComponentInputter implements ActivityHandlingInputter, InstanceSensitiveInputter{
	ConditionTable table=null;
	Activity editingActivity;
	
	public Object getValue() {
		DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
		Vector conditions = tableModel.getDataVector();

		Or or = new Or();
		for(int i=0; i<conditions.size(); i++){
			Vector theConditionRow = (Vector)conditions.get(i);
			And and = new And();
			
			for(int j=0; j<theConditionRow.size(); j++){
				
				if(theConditionRow.get(j) instanceof Evaluate){
					Evaluate evaluation = (Evaluate)theConditionRow.get(j);
					if(evaluation!=null)
						and.addCondition(evaluation);
				}else{
					RoleExist roleExist = (RoleExist)theConditionRow.get(j);
					if(roleExist!=null)
						and.addCondition(roleExist);
				}
			}
			
			if(and.getConditionsVt().size()>0){
				or.addCondition(and);
			}
		}
				
		return or;
	}

	public void setValue(Object data) {
		if(data!=null){
			Or or = (Or)data;
			Condition[] rows = or.getConditions();
			((DefaultTableModel)table.getModel()).setRowCount(rows.length+1);
			
			for(int i=0;i<rows.length;i++){
				And and = (And)rows[i];
				Condition[] cols = and.getConditions();
				for(int j=0;j<cols.length;j++){
					if(cols[j] instanceof Evaluate){
						Evaluate eval = (Evaluate)cols[j];
						table.setValueAt(eval, i, j+1);
					}else{
						RoleExist roleExist = (RoleExist)cols[j];
						table.setValueAt(roleExist, i, j+1);
					}
				}
			}
		}
		
		//System.out.println("condition =  " + data);
	}
	
	static final String[] str = {"a", "b", "c"};
	public Component getNewComponent() {
		
		/*ProcessVariableInput pvi = new ProcessVariableInput(ProcessDesigner.getInstance());
		pvi.getComponent();
		ProcessVariable pv = (ProcessVariable)pvi.getValue();
		return pvi.getComponent();*/
		
		table = new ConditionTable();
		table.setPreferredScrollableViewportSize(new Dimension(2000,200));
		//table.setPreferredSize(new Dimension(2000,200));
		//table.setAutoscrolls(true);
		//table.setAutoResizeMode(1);
		//table.setMinimumSize(new Dimension(2000,200));
		JScrollPane conditionTablePane = new JScrollPane(table);
		
		//conditionTablePane.setPreferredSize(new Dimension(500,200));
		//JScrollPane pane2 = new JScrollPane(conditionTablePane);
		//pane2.setPreferredSize(new Dimension(500,200));
		//return pane2;
		
		return conditionTablePane;
	}

	public void setActivity(Activity activity, String propertyName) {
		table.setProcessDefinition(activity.getProcessDefinition());
	}
	
	public void setInstance(Instance rec, String fieldName) {
		if(rec!=null && rec instanceof ObjectInstance){
			editingActivity = (Activity)((ObjectInstance)rec).getObject();
			if(editingActivity!=null && editingActivity instanceof SwitchActivity){
				SwitchActivity switchActivity = (SwitchActivity)editingActivity;
				Map extAttrs = ((SwitchActivity)editingActivity).getExtendedAttributes();
				
				String[] conditionNames = new String[switchActivity.getChildActivities().size()];
				for(int i=0; i<switchActivity.getChildActivities().size(); i++){
					String conditionDescription = (String)extAttrs.get("conditionDescription_" + i);
					conditionNames[i] = (conditionDescription !=null ? conditionDescription : ("condition" + i));
				}
				
				table.setConditionNames(conditionNames);
				table.setProcessDefinition(editingActivity.getProcessDefinition());
			}
		}
	}


	
	
	

	
	/*org.uengine.processdesigner.ScriptingValueInput{

	public org_uengine_kernel_ConditionInput(){
		super(ProcessDesigner.getInstance(), 
			"importPackage(Packages.org.uengine.kernel);\n" +
			"return new Evaluate(\"expression\", \"value\");\n" 
		);
	}
}*/
	
	
}