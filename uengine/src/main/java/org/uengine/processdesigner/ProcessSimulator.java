/*
 * Created on 2004. 10. 10.
 */
package org.uengine.processdesigner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.kernel.designer.*;

import org.metaworks.*;
import org.metaworks.inputter.*;

/**
 * @author Jinyoung Jang
 */
public class ProcessSimulator extends JPanel{

	JPanel diagram = new JPanel();
	JPanel processVariable = new JPanel();
	JPanel roles = new JPanel();
	JTextArea output = new JTextArea();
	JButton runBtn = new JButton("Run");
	JButton resumeBtn = new JButton("Resume");
	JButton stopBtn = new JButton("Stop");
	JButton restartBtn = new JButton("Restart");
	InputFormSubstance processVariableInputForm;
	InputFormSubstance roleInputForm;

	static Thread executionThread;
	
	public ProcessSimulator(){
		super(new BorderLayout());
		
		diagram = new JPanel();
		diagram.setBackground(Color.WHITE);
		processVariable = new JPanel();
		JPanel buttons = new JPanel(new FlowLayout());{
			buttons.add(runBtn);
			buttons.add(stopBtn);
			buttons.add(resumeBtn);
			buttons.add(restartBtn);
		}					
		
		JPanel outputPanel = new JPanel(new BorderLayout());
			outputPanel.add("North", new JLabel("Output"));
			outputPanel.add("Center", new JScrollPane(output));
		
		JPanel processVariablePanel = new JPanel(new BorderLayout());
			processVariablePanel.add("North", new JLabel("Variables"));
			processVariablePanel.add("Center", new JScrollPane(processVariable));

		JPanel rolePanel = new JPanel(new BorderLayout());
			rolePanel.add("North", new JLabel("Roles"));
			rolePanel.add("Center", new JScrollPane(roles));
		
		JSplitPane monitor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, processVariablePanel, rolePanel);
		JSplitPane monitor2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, monitor, outputPanel);
		add("Center", new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(diagram), monitor2));
		add("North", buttons);		
	}
	
	public void setProcessDefinition(final ProcessDefinition procDef){
		diagram.removeAll();
		
		AbstractActivityDesigner.setDebugger(true);	
			final ProcessDefinitionDesigner processDefinitionDesigner = (ProcessDefinitionDesigner)procDef.createDesigner(); 
			diagram.add(processDefinitionDesigner.getComponent());
		AbstractActivityDesigner.setDebugger(false);
		
		final Type processVariableTable = new Type();{
			ProcessVariable[] pvs = procDef.getProcessVariables();
			for(int i=0; i<pvs.length; i++){
				ProcessVariable pv = pvs[i];
				FieldDescriptor fd = new FieldDescriptor(pv.getName(), pv.getDisplayName().getText());
				fd.setType(pv.getType());
								
				processVariableTable.addFieldDescriptor(fd);
			}
		}
		processVariableInputForm = new InputFormSubstance(processVariableTable);
		processVariable.removeAll();	
		processVariable.add(processVariableInputForm);		
		processVariable.setEnabled(false);

		runBtn.setEnabled(true);
		stopBtn.setEnabled(false);
		resumeBtn.setEnabled(false);
		restartBtn.setEnabled(false);

		final Type roleTable = new Type();{
			Role[] roles = procDef.getRoles();
			for(int i=0; i<roles.length; i++){
				Role role = roles[i];
				FieldDescriptor fd = new FieldDescriptor(role.getName(), role.getDisplayName().getText());
								
				roleTable.addFieldDescriptor(fd);
			}
		}		
		roleInputForm = new InputFormSubstance(roleTable);
		roles.removeAll();	
		roles.add(roleInputForm);		
		roles.setEnabled(false);
		
		if(runBtn.getActionListeners().length>0)
			runBtn.removeActionListener(runBtn.getActionListeners()[0]);
		
		resumeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(GraphicSimulatorProcessInstance.currentThread!=null)
					GraphicSimulatorProcessInstance.currentThread.resume();
			}				
		});

		runBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
				runBtn.setEnabled(false);
				//stopBtn.setEnabled(true);
				//resumeBtn.setEnabled(true);
				
				//review: move to global?
				System.setOut(new PrintStream(new ByteArrayOutputStream()){
					public void println(String str){
						output.append(str + "\n");
					}

					public void println(Object x) {
						println(x.toString());
					}
					
				});	

				System.setErr(new PrintStream(new ByteArrayOutputStream()){
					public void println(String str){
						output.append(str + "\n");
					}
					public void print(String str){
						output.append(str);
					}
					public void println(Object x) {
						println(x.toString());
					}
				});
				
				if(executionThread!=null){
					executionThread.stop();
				}
				
				executionThread = new Thread(){
					public void run(){
						try{
							GraphicSimulatorProcessInstance instance = new GraphicSimulatorProcessInstance(procDef, "simulation instance");
					
							instance.setProcessVariableInputForm(processVariableInputForm);
							instance.setRoleInputForm(roleInputForm);	
							instance.setDesigner(processDefinitionDesigner);
							instance.setSimulator(ProcessSimulator.this);
									
							procDef.createInstance(instance);
							instance.execute();	
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				};
				
				executionThread.start();

			}
		});
	}

}
