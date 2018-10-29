/*
 * Created on 2004. 12. 29.
 */
package org.uengine.processdesigner;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.lang.reflect.Constructor;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.metaworks.ObjectType;
import org.metaworks.inputter.Inputter;
import org.metaworks.inputter.InputterAdapter;
import org.metaworks.inputter.ObjectInput;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.RoleResolutionContext;
import org.uengine.processdesigner.inputters.*;
import org.uengine.util.UEngineUtil;

/**
 * @author Jinyoung Jang
 */
public class DefaultRolePicker extends RolePicker implements ActionListener{
	RoleResolutionContextTextField rrcText;
	JTabbedPane tab;

	public DefaultRolePicker(JFrame owner){
		super(ProcessDesigner.getInstance());
		setModal(false);
		getContentPane().setLayout(new BorderLayout());
		
		String[] roleResolutionContextClasses = 
			GlobalContext.getPropertyStringArray(
				"roleresolutioncontexts",
				new String[]{
						"org.uengine.kernel.DirectRoleResolutionContext",
						"com.defaultcompany.organization.DefaultCompanyRoleResolutionContext"
				}
			);
		
		tab = new JTabbedPane();
		
		for(int i=0; i<roleResolutionContextClasses.length; i++)
		{//TAB "Direct"
			String className = roleResolutionContextClasses[i];
			JPanel tabPanel = new JPanel(new BorderLayout());
			
			InputterAdapter drrcInput;
			Class classType;
			try {
				classType = Class.forName(className);
				
				ProcessDesigner pd = (ProcessDesigner) getContentPane().getParent().getParent().getParent().getParent();
				String comCode = pd.getRevisionInfo().getAuthorCompany();
				
				try{
					
					//comCode option add
					String pakageName = getClass().getPackage().getName();
					String defaultInpputerClassName = className.replaceAll("[.]", "_")+"Input";
					String inpputterClassName = pakageName+".inputters."+defaultInpputerClassName;
					Class[] type = {String.class};
					Class classDefinition = Class.forName(inpputterClassName);
					Constructor cons = classDefinition.getConstructor(type);
					Object[] param = {comCode};
					drrcInput = (InputterAdapter) cons.newInstance(param);
//					drrcInput = (InputterAdapter)ObjectType.getDefaultInputter(Class.forName(className));
				}catch(Exception e){
					drrcInput = new ObjectInput(classType);
				}
				
				RoleResolutionContext rrc = (RoleResolutionContext)Class.forName(className).newInstance();
				String roleResolutionContextName = rrc.getName();

				drrcInput.addActionListener(this);
			
				tabPanel.add(
					"Center",
					drrcInput.getNewComponent()
				); 		
				tab.addTab(roleResolutionContextName, tabPanel);
				
			} catch (Exception e1) {
				e1.printStackTrace();
				continue;
			}
			
		}
/*		{//TAB "ByRule"
			JPanel tabPanel = new JPanel(new BorderLayout());
			InputterAdapter inputter = new org_uengine_kernel_ByRuleRoleResolutionContextInput();
			inputter.addActionListener(this);
		
			tabPanel.add(
				"Center",
				inputter.getNewComponent()
			); 		
			tab.addTab("By Rule", tabPanel);
		}
*/		
		getContentPane().add(
			"Center",
			tab
		); 

		JPanel selectedRRCPanel = new JPanel(new BorderLayout());
//		selectedRRCPanel.add("North", new JLabel("Role resolution context (drag the text and drop to activities):"));
		rrcText = new RoleResolutionContextTextField();
		selectedRRCPanel.add("Center", rrcText);
		selectedRRCPanel.setBorder(BorderFactory.createTitledBorder("Drag this text and drop to activities"));							
	
		getContentPane().add(
			"South",
			selectedRRCPanel
		);
		
		pack();
		Point pdLoc = ProcessDesigner.getInstance().getLocation();
		pdLoc.y += 100;
		setLocation(pdLoc);
		
		setTitle("Role Picker");
	}

	public void create(
		Inputter inputter,
		RoleResolutionContext roleResolutionContext,
		Boolean isModal) {

	}

	public RoleResolutionContext getRoleResolutionContext() {
		return rrcText.getRoleResolutionContext();
	}

	public void setRoleResolutionContext(RoleResolutionContext roleResolutionContext) {
		rrcText.setRoleResolutionContext(roleResolutionContext);
	}

	public void actionPerformed(ActionEvent e) {
		Inputter rrcInputter = (Inputter)e.getSource();
		setRoleResolutionContext((RoleResolutionContext) rrcInputter.getValue());
	}

	public void setValue(Object val) {
	}

	public Object getValue() {
		return getRoleResolutionContext();
	}

	public boolean showPicker() {
		pack();
		
		new Thread(){
			public void run() {
				try {
					Thread.sleep(100);
					dispose();
					show();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		show();
		
		return true;
	}
	

	public Component getComponent() {
		return this;
	}

}

class RoleResolutionContextTextField extends JTextField implements DragGestureListener,
																DragSourceListener 
{
	public RoleResolutionContextTextField() {
		DragSource dragSource = DragSource.getDefaultDragSource();

		dragSource.createDefaultDragGestureRecognizer(
					this, // component where drag originates
					DnDConstants.ACTION_COPY_OR_MOVE, // actions
					this); // drag gesture recognizer
					
		setEditable(false);
	}
	
	public void dragGestureRecognized(DragGestureEvent e) {
		e.startDrag(DragSource.DefaultCopyDrop, // cursor
			(Transferable) getRoleResolutionContext(),
			this);  // drag source listener
	}
	public void dragDropEnd(DragSourceDropEvent e) {}
	public void dragEnter(DragSourceDragEvent e) {}
	public void dragExit(DragSourceEvent e) {}
	public void dragOver(DragSourceDragEvent e) {}
	public void dropActionChanged(DragSourceDragEvent e) {}
	
	RoleResolutionContext roleResolutionContext;
		public RoleResolutionContext getRoleResolutionContext() {
			return roleResolutionContext;
		}
		public void setRoleResolutionContext(RoleResolutionContext context) {
			roleResolutionContext = context;
			setText(context.getDisplayName());
		}

}
