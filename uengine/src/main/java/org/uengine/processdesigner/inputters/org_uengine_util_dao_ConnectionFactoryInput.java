package org.uengine.processdesigner.inputters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import org.metaworks.inputter.*;
import org.metaworks.*;
import org.uengine.util.dao.DataSourceConnectionFactory;
import org.uengine.util.dao.JDBCConnectionFactory;

import javax.swing.*;
import java.awt.event.*;
/**
 * @author Jinyoung Jang
 */

public class org_uengine_util_dao_ConnectionFactoryInput extends InputterAdapter{

	InputForm dataSourceConnectionFactoryConfigPanel;
	InputForm jdbcConnectionFactoryConfigPanel;	
	JPanel configPanel;
//	String selectedConfig = "default";	
	JRadioButton defaultConnectionFactoryRadioButton;
	JRadioButton dataSourceConnectionFactoryRadioButton;
	JRadioButton jdbcConnectionFactoryRadioButton;

	public org_uengine_util_dao_ConnectionFactoryInput(){
		super();
	}

	public Component getNewComponent() {
		JPanel panel = new JPanel(new BorderLayout());
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		try{
			dataSourceConnectionFactoryConfigPanel = new InputForm(new ObjectType(DataSourceConnectionFactory.class));
			jdbcConnectionFactoryConfigPanel = new InputForm(new ObjectType(JDBCConnectionFactory.class));
			configPanel = new JPanel(new BorderLayout());
			
			RadioListener radioListener = new RadioListener();
			
			JPanel radioBtnPanel=new JPanel(new FlowLayout());
			ButtonGroup buttonGroup = new ButtonGroup();{
				JRadioButton radio;

				defaultConnectionFactoryRadioButton = new JRadioButton("default");		
				defaultConnectionFactoryRadioButton.setActionCommand("default");	
				defaultConnectionFactoryRadioButton.addActionListener(radioListener);
				defaultConnectionFactoryRadioButton.setSelected(true);
				radioBtnPanel.add(defaultConnectionFactoryRadioButton);
				buttonGroup.add(defaultConnectionFactoryRadioButton);

				dataSourceConnectionFactoryRadioButton = new JRadioButton("data source");			
				dataSourceConnectionFactoryRadioButton.setActionCommand("datasource");			
				dataSourceConnectionFactoryRadioButton.addActionListener(radioListener);
				radioBtnPanel.add(dataSourceConnectionFactoryRadioButton);
				buttonGroup.add(dataSourceConnectionFactoryRadioButton);
								
				jdbcConnectionFactoryRadioButton = new JRadioButton("direct setting");			
				jdbcConnectionFactoryRadioButton.setActionCommand("direct");			
				jdbcConnectionFactoryRadioButton.addActionListener(radioListener);			
				radioBtnPanel.add(jdbcConnectionFactoryRadioButton);
				buttonGroup.add(jdbcConnectionFactoryRadioButton);				
			}
			
			panel.add("North", radioBtnPanel);		
			panel.add("Center", configPanel);
			
			defaultConnectionFactoryRadioButton.setSelected(true);
			//configPanel.add("Center", dataSourceConnectionFactoryConfigPanel);

		}catch(Exception e){
			panel.add(new JLabel("currently designe support not available"));
		}
		
		return panel;
	}

	public Object getValue() {
		if(defaultConnectionFactoryRadioButton.isSelected()){
			return null;
		}else if(dataSourceConnectionFactoryRadioButton.isSelected()){
			return ((ObjectInstance)dataSourceConnectionFactoryConfigPanel.getInstance()).getObject();
		}else
			return ((ObjectInstance)jdbcConnectionFactoryConfigPanel.getInstance()).getObject();
		
	}

	public void setValue(Object obj) {
		InputForm inputForm;
		
		if(obj==null){
			defaultConnectionFactoryRadioButton.setSelected(true);
			return;
		}
		
		if(obj instanceof DataSourceConnectionFactory){
			inputForm = dataSourceConnectionFactoryConfigPanel;
			dataSourceConnectionFactoryRadioButton.setSelected(true);
			(new RadioListener()).changeConfigPanel("datasource");
		}else{
			inputForm = jdbcConnectionFactoryConfigPanel;
			jdbcConnectionFactoryRadioButton.setSelected(true);
			(new RadioListener()).changeConfigPanel("direct");
		}
		
		ObjectInstance rec = (ObjectInstance)inputForm.getType().createInstance();
		rec.setObject(obj);
		inputForm.setInstance(rec);
			
	}

	private class RadioListener implements ActionListener { 
		 public void actionPerformed(ActionEvent e) {			 
			changeConfigPanel(e.getActionCommand());
		 }
		 
		 public void changeConfigPanel(String selectedConfig_){
			//selectedConfig = selectedConfig_;			 
			configPanel.removeAll();
			 
			if(defaultConnectionFactoryRadioButton.isSelected()){
				//nothing to do
			}else if(dataSourceConnectionFactoryRadioButton.isSelected()){
				configPanel.add("Center", dataSourceConnectionFactoryConfigPanel);
			}else{
				configPanel.add("Center", jdbcConnectionFactoryConfigPanel);
			}
			 
			configPanel.revalidate();
			onValueChanged();
		 }
	 }


}