package org.uengine.kernel.designer;

import org.uengine.kernel.Condition;
import org.uengine.kernel.SwitchActivity;
import org.uengine.processdesigner.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.*;

/**
 * @author Jinyoung Jang
 */

public class SwitchActivityDesigner extends AllActivityDesigner{

	public SwitchActivityDesigner(){
		super();	
	}
	
	protected void initialize(){
		super.initialize();
		
		add(new ActivityLabel(org.uengine.kernel.SwitchActivity.class), 0);
	}
	
	protected JPanel boxComponent(final ActivityDesigner designer){
		Component comp = super.boxComponent(designer);
		
		JPanel compPanel = new ProxyPanel(new BorderLayout(0,0));

		final int where = getChildDesigners().size();
		final SwitchActivity switchActivity = (SwitchActivity)getActivity();
		Condition[] conditions = switchActivity.getConditions();
		String conditionDescription;// = "condition" + where;
		
		if(conditions!=null && conditions.length>where){
			conditionDescription = conditions[where].toString();
			switchActivity.setExtendedAttribute("conditionDescriptions_" + where, conditionDescription);
		}else{
			conditionDescription = "condition" + where;
		}
		
		final JPanel conditionLabelPanel = new ProxyPanel(new BorderLayout());
		final JLabel conditionLabel = new JLabel(conditionDescription);

		conditionLabelPanel.add("Center", conditionLabel);
		conditionLabel.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent arg0) {
				conditionLabelPanel.removeAll();
				final JTextField labelEditor = new JTextField();
				labelEditor.setText(conditionLabel.getText());
				conditionLabelPanel.add("Center", labelEditor);
				
				final ActionListener onEditDone = new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						
						Condition theCondition = null; 
						
						int where = switchActivity.getChildActivities().indexOf(designer.getActivity());
						Condition[] conditions = switchActivity.getConditions();
						if(conditions!=null && conditions.length > where){
							theCondition = conditions[where];
						}

						String conditionDescription = labelEditor.getText();
						
						if(theCondition!=null){
							if(conditionDescription==null || conditionDescription.trim().length()==0){
								conditionDescription = theCondition.toString();
							}else{
								theCondition.getDescription().setText(conditionDescription);
							}
						}
						
						conditionLabel.setText(conditionDescription);
						conditionLabelPanel.removeAll();
						conditionLabelPanel.add("Center", conditionLabel);
						conditionLabelPanel.revalidate();
						
						switchActivity.setExtendedAttribute("conditionDescriptions_" + where, conditionDescription);
					}
				};
				
				labelEditor.addFocusListener(new FocusListener(){
					public void focusGained(FocusEvent arg0) {
					}
					public void focusLost(FocusEvent arg0) {
						onEditDone.actionPerformed(null);						
					}
				});
				
				labelEditor.addActionListener(onEditDone);
				
				conditionLabelPanel.revalidate();
			}
			
		});
		
		switchActivity.addProperyChangeListener(
			new PropertyChangeListener(){
				public void propertyChange(PropertyChangeEvent pce){
					if(pce.getPropertyName().equals("conditions")){
						try{
							int where = switchActivity.getChildActivities().indexOf(designer.getActivity());
							Condition[] conditions = (Condition[])pce.getNewValue();	
							conditionLabel.setText(conditions[where].toString());
						}catch(Exception e){
						}
					}
				}
			}
		);

		compPanel.add("North", conditionLabelPanel); 	
		compPanel.add("Center", comp);
		 	
		return compPanel;
	}

	protected Container getBoxingContainer(Component comp){
		return super.getBoxingContainer(comp).getParent();
	}

}