package org.uengine.processdesigner.inputters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.metaworks.inputter.InputterAdapter;
import org.uengine.kernel.Role;
import org.uengine.ui.XMLValueInput;
import org.uengine.ui.XMLValuePicker;
import org.uengine.util.UEngineUtil;

import com.defaultcompany.organization.DefaultCompanyRoleResolutionContext;

/**
 * @author Jinyoung Jang
 */

public class com_defaultcompany_organization_DefaultCompanyRoleResolutionContextInput extends InputterAdapter{

	private static final String URL_RoleListXML = "/organization/get/xml/role/chart/";
	private static final String URL_GroupListXML = "/organization/get/xml/group/chart/";
	
	private String comCode;
	
	XMLValuePicker xmlValuePicker;
	ButtonGroup groupOption;
	ButtonGroup roleOption;
	InputterAdapter groupInputter;
	InputterAdapter roleInputter; 
	InputterAdapter definedRoleInputter;
	
	DefaultCompanyRoleResolutionContext value;

	public com_defaultcompany_organization_DefaultCompanyRoleResolutionContextInput(){
		super();
	}
	
	public com_defaultcompany_organization_DefaultCompanyRoleResolutionContextInput(String comCode){
		super();
		this.comCode = comCode;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object obj) {
		if(obj instanceof DefaultCompanyRoleResolutionContext)
			value = (DefaultCompanyRoleResolutionContext)obj;
	}

	public Component getNewComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
 
		{//Group:
			JPanel settingPanel = new JPanel();
			settingPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.Y_AXIS));
			settingPanel.setBorder(BorderFactory.createTitledBorder("Group"));							

			ButtonGroup buttonGroup = new ButtonGroup();
			groupOption = buttonGroup;
			{
				JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				
				{
					JRadioButton radio = new JRadioButton("the group that ");
					radio.setActionCommand("relativeGroup");			
					//radio.addActionListener(this);
					buttonGroup.add(radio);
					optionPanel.add(radio);
				}

				definedRoleInputter = new org_uengine_kernel_RoleInput(); 
//				definedRoleInputter = new com_defaultcompany_organization_RoleInput(); 
				optionPanel.add((definedRoleInputter).getComponent());
				optionPanel.add(new JLabel(" belongs to."));
				settingPanel.add(optionPanel);
			}			

			{
				JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				{
					JRadioButton radio = new JRadioButton("the specified group ");
					radio.setActionCommand("specifiedGroup");			
					//radio.addActionListener(this);
					buttonGroup.add(radio);
					optionPanel.add(radio);
				}
				groupInputter = new XMLValueInput(URL_GroupListXML+comCode);
				optionPanel.add((groupInputter).getComponent());
				settingPanel.add(optionPanel);
			}

			{
				JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				{
					JRadioButton radio = new JRadioButton("All ");
					radio.setActionCommand("All");			
					//radio.addActionListener(this);
					buttonGroup.add(radio);
					optionPanel.add(radio);
				}
				settingPanel.add(optionPanel);
			}

			panel.add(settingPanel);
		}
				
		{//Role:
			JPanel settingPanel = new JPanel();
			settingPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.Y_AXIS));
			ButtonGroup buttonGroup = new ButtonGroup();
			roleOption = buttonGroup;
			settingPanel.setBorder(BorderFactory.createTitledBorder("Role"));							
						
			{
				JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				{
					JRadioButton radio = new JRadioButton("the specified role: ");
					radio.setActionCommand("specifiedRole");			
					//radio.addActionListener(this);
					buttonGroup.add(radio);
					optionPanel.add(radio);
				}
				roleInputter = new XMLValueInput(URL_RoleListXML+comCode);
				optionPanel.add((roleInputter).getComponent());
				settingPanel.add(optionPanel);
			}

			{
				JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				{
					JRadioButton radio = new JRadioButton("All ");
					radio.setActionCommand("All");			
					//radio.addActionListener(this);
					buttonGroup.add(radio);
					optionPanel.add(radio);
				}
				settingPanel.add(optionPanel);
			}
			
			panel.add(settingPanel);
		}		
				
		JPanel comp = new JPanel(new BorderLayout());
		comp.add("North", panel);
		
		{
			JPanel buttonPanel = new JPanel(new FlowLayout());
			JButton selectBtn = new JButton("Select");
			selectBtn.addActionListener(
				new ActionListener(){

					public void actionPerformed(ActionEvent e) {
						configure();
					}
					
				}
			);	
			buttonPanel.add(selectBtn);
			
			comp.add("South", buttonPanel);		
		}
		
		return comp;
	}
	
	public void configure(){
		DefaultCompanyRoleResolutionContext brrrc = new DefaultCompanyRoleResolutionContext();
		
		String groupOptionStr = groupOption.getSelection().getActionCommand();
		
		if(groupOptionStr.equals("relativeGroup")){
			brrrc.setRelativeRole((Role) definedRoleInputter.getValue());
			brrrc.setGroupId(null);				
		}else if(groupOptionStr.equals("specifiedGroup")){
			brrrc.setGroupId(""+groupInputter.getValue());
			brrrc.setRelativeRole(null);
		}else{
			brrrc.setGroupId(null);
			brrrc.setRelativeRole(null);
		}
		
		String roleOptionStr = roleOption.getSelection().getActionCommand();
		
		if(roleOptionStr.equals("specifiedRole")){
			brrrc.setRoleId(""+roleInputter.getValue());
		}else{
			brrrc.setRoleId(null);
		}
		
		setValue(brrrc);
		onValueChanged();
	}

}