package org.uengine.processdesigner;

/**
 * �־��� Table�� �� Row�� �Է¹�; �� �ִ� Form Panel (Swing JPanel); ���Ѵ�. 
 * 
 * 
 * @version: 
 * 1.0 2000/2/14 
 * 
 * @example 1) �� ��
 * <pre>
 * 	public static void main(String args[]) throws Exception{
 * 
 * 
 * 		Class.forName("oracle.jdbc.driver.OracleDriver");
 * 																								// ~/orawin95/network/admin/tnsnames.ora file ��v.
 * 		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@165.186.52.29:1526:iman5", "infodba", "ckddnjs5");
 * 		
 * 		// table; ���Ѵ�. db�� 'create'��; ����
 * 		Table px_part_newparts = new Table(
 * 			"px_part_newparts",	//  table��
 * 						//  column		title		type		iskey
 * 			new FieldDescriptor[]{
 * 				new FieldDescriptor("SEQNO", 		"���", 	Types.INTEGER,	true),
 * 				new FieldDescriptor("description",	"����"),
 * 				new FieldDescriptor("developmentdate",	"������d", 	Types.DATE),
 * 				new FieldDescriptor("DIVISION",		"����")
 * 			},
 * 			con
 * 		);
 * 		
 * 		Record [] rec = px_part_newparts.find("seqno=10000");
 * 		
 * 		if(rec.length> 0){
 * 			System.out.println("description = " + rec[0].get("description"));
 * 			System.out.println("seqno = "+rec[0].get("seqno"));
 * 			
 * 		}			
 * 			
 * 		final InputForm newForm = new InputForm(px_part_newparts, "10000");
 * 		
 * 		JFrame frame = new JFrame("test");
 * 		
 * 		frame.getContentPane().setLayout(new BorderLayout());
 * 		frame.getContentPane().add("Center", newForm);	// ������������
 * 		
 * 		JButton saveBtn = new JButton("����");
 * 		frame.getContentPane().add("South", saveBtn);	// ������������
 * 
 * 		saveBtn.addActionListener(new ActionListener(){
 * 			public void actionPerformed(ActionEvent e){
 * 				try{
 * 					newForm.getRecord().save();
 * 				}catch(Exception ex){}
 * 			}
 * 		});
 * 		
 * 
 * 		frame.pack();
 * 		frame.setVisible(true);
 * 	}
 * </pre>	
 * @example 1) ���̾� �α׸� ����� �Է� �� ����
 * <pre>
 * 		InputForm testForm2 = new InputForm(
 * 			new Table("test2",
 * 				new FieldDescriptor[]{
 * 					new FieldDescriptor("column1"),
 * 					new FieldDescriptor("column2"),
 * 					new FieldDescriptor("column3")
 * 				}
 * 			)
 * 		){
 * 			public void onSaveOK(Record rec, JDialog dialog){
 * 				com.ugsolutions.util.MessageBox.post("'"+ rec.get("column1") +"' ���ڵ尡 ����Ǿ�4ϴ�.", "����Ϸ�", 1);
 * 			}
 * 			
 * 			public void onSaveFailed(Exception e, JDialog dialog){
 * 				com.ugsolutions.util.MessageBox.post("���� ���� �߽4ϴ�.\n ����=" + e.getMessage(), "����", 1);
 * 			}
 * 		};
 * 		testForm2.postInputDialog(frame);
 * </pre>
 * @author ���� 
 * @See Tuple, Serialized Form
 */

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.metaworks.*;
import org.metaworks.ui.*;
import org.metaworks.inputter.*;


public class InputFormSubstance extends InputForm{

	public InputFormSubstance(){
		super();
	}

	public InputFormSubstance(Type table){
		super();
		this.table=table;
		
		createForm();
	}
	
	JXTaskPaneContainer taskPaneContainer;


	public void createForm(){
		taskPaneContainer = new JXTaskPaneContainer() {
			public boolean getScrollableTracksViewportWidth() {
				return false;
			}
		};
		taskPaneContainer.setScrollableTracksViewportHeight(false);
		taskPaneContainer.setScrollableTracksViewportWidth(false);
		
		FieldDescriptor[] props=table.getFieldDescriptors();
		
		boolean groupExist = false;
		ArrayList groups = new ArrayList();
		Hashtable groupMembers = new Hashtable();
		final Hashtable collapseGroupOrNot = new Hashtable();
		final Hashtable groupMemberComponents = new Hashtable(); 

		for(int i=0; i<props.length; i++){
			if(props[i].getAttribute("hidden", null)==null){
				String group = (String)props[i].getAttribute("group", null);
				Boolean collapseGroup = (Boolean)props[i].getAttribute("collapseGroup", null);
				if(collapseGroup!=null)
					collapseGroupOrNot.put(group, collapseGroup);
				
				if(group==null) 
					group = (getType().getName() != null ? getType().getName() : "");
				else
					groupExist = true;
				
				if(!groupMembers.containsKey(group)){
					groups.add(group);
					groupMembers.put(group, new ArrayList());
					groupMemberComponents.put(group, new ArrayList());
				}
				
				ArrayList members = (ArrayList)groupMembers.get(group);
				members.add(props[i]);
			}
			
		}

		if(groupExist){
			for(int i=0; i<groups.size(); i++){
				int row = 0;
				
				final String groupName = (String)groups.get(i);
				ArrayList members = (ArrayList)groupMembers.get(groupName);
				final ArrayList memberComponents = (ArrayList)groupMemberComponents.get(groupName);
				boolean collapseOrNot = (collapseGroupOrNot.containsKey(groupName) ? ((Boolean)collapseGroupOrNot.get(groupName)).booleanValue() : false);

				
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.NONE;
				c.ipadx=10;
				c.ipady=5;
				
				
				
				JXTaskPane taskPane = new JXTaskPane();
				JPanel taskPanel = new JPanel();
				
				GridBagLayout gridbag;
				gridbag=new GridBagLayout();
				taskPanel.setLayout(gridbag);

				taskPane.setLayout(new BorderLayout());
				taskPane.add(taskPanel, BorderLayout.CENTER);
				taskPane.setTitle(" " + groupName);
				//taskPane.setExpanded(false);					
					//label.setFont(label.getFont().deriveFont(Font.BOLD,13.0f));
					
				c.weightx = 0.0;
				c.fill = GridBagConstraints.BOTH;
				c.gridwidth = GridBagConstraints.REMAINDER;
				c.gridx = 0;
				c.gridy = row;
				c.anchor= c.WEST;
       			gridbag.setConstraints(taskPanel, c);
       			
       			taskPaneContainer.add(taskPane);
       		 	//panel.add(grpLabelPanel);
				c.gridwidth = 1;
				c.fill = GridBagConstraints.NONE;				
				
				taskPane.setCollapsed(collapseOrNot);

				for(int j=0; j<members.size(); j++){
					row++;
					
					FieldDescriptor prop = (FieldDescriptor)members.get(j);
					
			       	prop.getInputter().initialize(prop.getAttributeTable());
			       	
					JLabel label;{
						label = new JLabel(prop.getDisplayName());
						c.gridx = 0;
						c.gridy = row;
						c.anchor= c.EAST;

		       		 	gridbag.setConstraints(label, c);
		       		 			       		 	
						memberComponents.add(label);	
						taskPanel.add(label);
					}

					Component comp;{				
						comp=prop.getInputComponent();
						
						c.gridx = 1;
						c.anchor= c.WEST;
		       		 	gridbag.setConstraints(comp, c);
		       		 	
						memberComponents.add(comp);	
						taskPanel.add(comp);
					}
					
					row++;
					
					JLabel errLabel;{				
						errLabel=new JLabel();
						errLabel.setFont(label.getFont().deriveFont(Font.ITALIC,10.0f));
						errLabel.setForeground(Color.RED);
						errLabel.setVisible(false);						
						c.gridx = 1;
						c.gridy = row;
						c.anchor= c.WEST;
		       		 	gridbag.setConstraints(errLabel, c);
		       		 	taskPanel.add(errLabel);
			       		errLabels.put(prop.getName(), errLabel);
					}
				}
			}
		}
		
		record = getType().createInstance();
		FieldDescriptor[] fields=getType().getFieldDescriptors();

		for(int i=0; i<fields.length; i++){
			final FieldDescriptor theField = fields[i];
			Inputter inputter=fields[i].getInputter();
			//inputter.initialize(fields[i].getAttributeTable());
			
			if(inputter instanceof InstanceSensitiveInputter){
				((InstanceSensitiveInputter)inputter).setInstance(record, theField.getName());
			}
			
			final Dependancy dependancy = (Dependancy)props[i].getAttribute("dependancy", null);
			if(dependancy!=null){
				final FieldDescriptor theDependantFD = table.getFieldDescriptor(dependancy.getDependantFieldName());
				theDependantFD.getInputter().addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						dependancy.action(theField, theDependantFD);
					}
					
				});
			}
			
			inputter.addActionListener(this);

		}
		
//		setLayout(new BorderLayout());
//		add("Center", panel);
	}

	public Component getComponent() {
		return taskPaneContainer;
	}



}
