/*
 * Created on 2005. 3. 1.
 */
package org.uengine.processdesigner;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JDialog;

import org.metaworks.*;
import org.metaworks.inputter.Inputter;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.KeyedParameter;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ResultPayload;


/**
 * @author Jinyoung Jang
 */
public class HumanActivitySimulator extends JDialog{
	HumanActivity humanActivity;
	InputFormSubstance processVariableInputForm; 
	SimulatorProcessInstance processInstance;
	
	public HumanActivitySimulator(HumanActivity humanActivity, SimulatorProcessInstance instance){
		this.humanActivity = humanActivity;
		this.processInstance = instance;
		
		final org.metaworks.Type processVariableTable = new org.metaworks.Type();{
			ParameterContext[] pcs = humanActivity.getParameters();
			for(int i=0; i<pcs.length; i++){
				ProcessVariable pv = pcs[i].getVariable();
				FieldDescriptor fd = new FieldDescriptor(pv.getName(), pv.getDisplayName().getText());
				fd.setType(pv.getType());
				
				Inputter specifiedInputter = pv.getInputter();
				if(specifiedInputter!=null){
					fd.setInputter(specifiedInputter);
				}
								
				processVariableTable.addFieldDescriptor(fd);
			}
		}
		processVariableInputForm = new InputFormSubstance(processVariableTable);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add("Center", processVariableInputForm);
		
		JButton complete = new JButton("Complete");
		complete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				complete();
			}
		});
		
		setTitle("Work Item Handler :" + humanActivity.getName());
		getContentPane().add("South", complete);
		pack();
	}
	
	public void complete(){
		new Thread(){
			public void run(){
				try {
					Instance result = processVariableInputForm.getInstance();
			
					ParameterContext[] pcs = humanActivity.getParameters();
					KeyedParameter[] kps = new KeyedParameter[pcs.length];
					for(int i=0; i<pcs.length; i++){
						String pvName = pcs[i].getVariable().getName();
						kps[i] = new KeyedParameter();
						kps[i].setKey(pvName);
						
//System.out.println("HumanActivitySimulator:: result = " +  result);
						kps[i].setValue(result.getFieldValueObject(pvName));
					}			
					ResultPayload rp = new ResultPayload();
					rp.setProcessVariableChanges(kps);
					
					dispose();			
					humanActivity.fireReceived(processInstance, rp);
						
				} catch (Exception e) {
					System.err.println(e.toString());
				}				
			}
		}.start();
	}
	
	public void run(){
		setLocationRelativeTo(ProcessDesigner.getInstance());
		show();
	}
	
}
