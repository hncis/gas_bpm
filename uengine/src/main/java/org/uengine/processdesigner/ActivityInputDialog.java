package org.uengine.processdesigner;

import org.jdesktop.swingx.JXPanel;
import org.metaworks.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.event.*;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ValidationContext;


/**
 * @author Jinyoung Jang
 */
public class ActivityInputDialog extends InputDialog {
	
	static boolean bMaximized = false;
	static Map sizeInfo = new HashMap();
	
	public ActivityInputDialog(ActivityInputForm form, Class orgActivityCls){
		super(form, "Apply", "Apply", "cancel", "Edit " + org.uengine.util.UEngineUtil.getClassNameOnly(orgActivityCls), ProcessDesigner.getInstance());
	}
	
	public void initialize(String saveTitle, String updateTitle, String cancelTitle){
		
		JPanel ppanel = createPanel(getInputForm(), this, saveTitle, updateTitle, cancelTitle);

		Container panel = getContentPane();
		panel.setLayout(new BorderLayout());
		panel.add("Center", ppanel);

		pack();
		setLocationRelativeTo(ProcessDesigner.getInstance());
	}
	
	public static JPanel createPanel(final InputForm thisForm, final InputDialog thisDialog, String saveTitle, String updateTitle, String cancelTitle){
		//JPanel ppanel = new JPanel();
		
		//property panel
		//ppanel.setLayout(new BorderLayout());
		//ppanel.add("Center", thisForm);
		
		//main tab		
		
		
		final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.RIGHT);
		tabPane.add( GlobalContext.getLocalizedMessage("processdesigner.properties.displayname", "Properties"),
				thisForm);
        
		final JEditorPane validationPanel = new JEditorPane();
		//beanEditorPane.setContentType("text/xml");
		tabPane.add( GlobalContext.getLocalizedMessage("processdesigner.integrity.displayname", "Integrity"), new JScrollPane(validationPanel));
		
		tabPane.getModel().addChangeListener( 
			new ChangeListener() { 
				public void stateChanged(ChangeEvent e) {
					ValidationContext valCtx = ((ActivityRecord)thisForm.getInstance()).getActivity().validate(null);
					if(valCtx!=null && valCtx.size()>0){
						StringBuffer errMsg = new StringBuffer();
						for(Enumeration enumeration = valCtx.elements(); enumeration.hasMoreElements();){
							Object item = (Object)enumeration.nextElement();
							errMsg.append(item +"\n");
						}
						validationPanel.setText(errMsg.toString());
					}else
						validationPanel.setText("This activity has no error.");
										
				}
			}
		);		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel toolbarPanel = new JPanel(new BorderLayout());
		toolbarPanel.add("West", createToolBar(thisForm, thisDialog, saveTitle, updateTitle, cancelTitle));
		
		final JButton expandBtn = new JButton(bMaximized ? "Minimize" : "Maximize");
		toolbarPanel.add("East", expandBtn);
		panel.add("South", toolbarPanel);
		panel.add("Center", tabPane);

		
		expandBtn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				Container root = thisForm.getParent();
				while(root.getParent()!=null &&  !(root instanceof JDialog))
					root = root.getParent();
				
				if(!bMaximized){
					sizeInfo.put("location", root.getLocation());
					sizeInfo.put("size", root.getSize());

					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					screenSize.height = screenSize.height - 100;
					root.setSize(screenSize);
					root.setLocation(0, 50);
					
					expandBtn.setText("Minimize");
					bMaximized = true;
				}else{
					Point location = (Point) sizeInfo.get("location");
					Dimension size = (Dimension) sizeInfo.get("size");
					
					root.setLocation(location);
					root.setSize(size);
					
					expandBtn.setText("Maximize");
					bMaximized = false;
				}
				
			}
			
		});

		return panel;	
	}
	
//	public static JPanel createPanel(final InputFormSubstance thisForm, final InputDialog thisDialog, String saveTitle, String updateTitle, String cancelTitle){
//		JPanel ppanel = new JPanel();
//		
//		//property panel
//		ppanel.setLayout(new BorderLayout());
//		ppanel.add("Center", thisForm);
//		
//		//main tab		
//		final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);
//		tabPane.add( GlobalContext.getLocalizedMessage("processdesigner.properties.displayname", "Properties"), new JScrollPane(ppanel));
//        
//		final JEditorPane validationPanel = new JEditorPane();
//		//beanEditorPane.setContentType("text/xml");
//		tabPane.add( GlobalContext.getLocalizedMessage("processdesigner.integrity.displayname", "Integrity"), new JScrollPane(validationPanel));
//		
//		tabPane.getModel().addChangeListener( 
//			new ChangeListener() { 
//				public void stateChanged(ChangeEvent e) {
//					ValidationContext valCtx = ((ActivityRecord)thisForm.getInstance()).getActivity().validate(null);
//					if(valCtx!=null && valCtx.size()>0){
//						StringBuffer errMsg = new StringBuffer();
//						for(Enumeration enumeration = valCtx.elements(); enumeration.hasMoreElements();){
//							Object item = (Object)enumeration.nextElement();
//							errMsg.append(item +"\n");
//						}
//						validationPanel.setText(errMsg.toString());
//					}else
//						validationPanel.setText("This activity has no error.");
//										
//				}
//			}
//		);		
//		
//		JPanel panel = new JPanel();
//		panel.setLayout(new BorderLayout());
//		panel.add("South", createToolBar(thisForm, thisDialog, saveTitle, updateTitle, cancelTitle));
//		panel.add("Center", tabPane);	
//		
//		return panel;	
//	}
		
}
