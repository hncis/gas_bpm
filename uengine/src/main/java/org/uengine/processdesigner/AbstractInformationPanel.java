package org.uengine.processdesigner;

import org.metaworks.*;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleParameterContext;
import org.uengine.kernel.RoleResolutionContext;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.kernel.designer.ComplexActivityDesigner;
import org.uengine.kernel.designer.ProcessDefinitionDesigner;
import org.uengine.kernel.designer.SubProcessActivityDesigner;
import org.uengine.util.UEngineUtil;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Jinyoung Jang
 */

public abstract class AbstractInformationPanel extends JPanel {

	Application application;
	Class cls;
	String title;
	Hashtable lablesForEachValue;

	WindowAdapter refreshAction;
	JDialog editDlg;
	ProcessDefinition processDefinition;
		public ProcessDefinition getProcessDefinition() {
			return processDefinition;
		}
		public void setProcessDefinition(ProcessDefinition value) {
			processDefinition = value;
			if (value != null) {
				setValues(getValues());
				refreshAction.windowClosing(null);
			}
		}
		
	ProcessDefinitionDesigner processDefinitionDesigner;
		public ProcessDefinitionDesigner getProcessDefinitionDesigner() {
			return processDefinitionDesigner;
		}
		public void setProcessDefinitionDesigner(
				ProcessDefinitionDesigner processDefinitionDesigner) {
			this.processDefinitionDesigner = processDefinitionDesigner;
		}

	public AbstractInformationPanel(ProcessDefinition pd, String label, Class cls)
	{	
		super(new BorderLayout(8,8));
		
		final JPanel contentPanel = new ProxyPanel(new GridLayout(0,1));
			
		setSize( 200, 200);		
		this.cls = cls;
		this.title = label;
		try{
			application = createApplication();
				
			refreshAction = 			
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					//finalThis.revalidate();
						
					contentPanel.removeAll();
					if(lablesForEachValue!=null) lablesForEachValue.clear();
					lablesForEachValue = new Hashtable();
						
					Instance[] records = getApplication().getInstances();
					Object[] values = new Object[records.length];
					for(int i=0; i<records.length; i++){
						ObjectInstance objRec = (ObjectInstance)records[i];
						final Object value = objRec.getObject();
						//
						java.net.URL img = getClass().getClassLoader().getResource("org/uengine/kernel/images/arr_02.gif");
						
						JLabel label = new InformationValueLabel((value), new ImageIcon(img), JLabel.LEFT);
						contentPanel.add(label);
						lablesForEachValue.put(value, label);
						
						DragSource dragSource = DragSource.getDefaultDragSource();
						dragSource.createDefaultDragGestureRecognizer(
							label, // component where drag originates
							DnDConstants.ACTION_COPY_OR_MOVE, // actions
							new DragGestureListener(){
								/**
								 * start of D&D framework implementation
								 */
								
								public void dragGestureRecognized(DragGestureEvent e) {
									e.startDrag(DragSource.DefaultCopyDrop, // cursor
										new Transferable(){

											public DataFlavor[] getTransferDataFlavors() {
												return null;
											}

											public boolean isDataFlavorSupported(DataFlavor flavor) {
												return false;
											}

											public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
												List<Object> list = new ArrayList<Object>();
												list.add(value);
												list.add(getProcessDefinitionDesigner());
												return list;
											}
										
										});  // drag source listener
								}
								public void dragDropEnd(DragSourceDropEvent e) {}
								public void dragEnter(DragSourceDragEvent e) {}
								public void dragExit(DragSourceEvent e) {}
								public void dragOver(DragSourceDragEvent e) {}
								public void dropActionChanged(DragSourceDragEvent e) {}

								/**
								 * end of D&D framework implementation
								 */
							}
						); // drag gesture recognizer
						
						//
						values[i] = value;
					}					
						
					if(e!=null)
						applyValues(values);	
							
					if(values==null || values.length==0) 
						contentPanel.add(new JLabel("        <empty>         "));
						
					contentPanel.revalidate();
				}
			};

			editDlg = new JDialog(ProcessDesigner.getInstance(), "Edit " + title, true);
			editDlg.getContentPane().add(application.createPanel());
			editDlg.addWindowListener(refreshAction);
			((DefaultApplication)application).setOwner(ProcessDesigner.getInstance());

			//setBorder(BorderFactory.createTitledBorder(label));
			//java.net.URL imgURL = getClass().getClassLoader().getResource("org/uengine/kernel/images/role.gif");
			//setBorder(new NewBorder(getBorder(), label, new ImageIcon(imgURL)));
			
			setToolTipText("Click to edit " + label);
			
			addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent me){
					
					onEdit();
					editDlg.setSize(450, 300);
					editDlg.setLocationRelativeTo(ProcessDesigner.getInstance());		
					editDlg.setVisible(true);
				}
			});
			
			add("Center", contentPanel);
					
			setProcessDefinition(pd);
			contentPanel.revalidate();
			
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
    }
	
	public JLabel getJLabel(Object value){
		if(value==null) return null;
		
		if(lablesForEachValue.containsKey(value))
			return (JLabel) lablesForEachValue.get(value);
		
		return null;
	}

	public Application getApplication() {
		return application;
	}
	
	protected Application createApplication() throws Exception{
		return new GridApplication(new ObjectType(getCls()));
	}
	
	protected void setValues(Object[] values){
		if(values!=null){
			((DefaultApplication)getApplication()).clearAll();

			for(int i=0; i<values.length; i++){
				ObjectInstance rec = (ObjectInstance)getApplication().getType().createInstance();
				rec.setObject(values[i]);
				//rec.setFieldValue("name", roles[i].getName());
				
				getApplication().addInstance(rec);
			}		
		}
	}
	
	protected abstract Object[] getValues();
	protected abstract void applyValues(Object[] objs);
	
	protected String getLabel(Object value){
		return value.toString();
	}
	
	public void onEdit(){
	}
	
	public void refresh(){
		setValues(getValues());
		refreshAction.windowClosing(null);
	}
	
/*	public class ObjectTable2 extends ObjectTable{
		public ObjectTable2(Class type) throws Exception{
			super(type);
			
			FieldDescriptor[] fds = getFieldDescriptors();
			
			for(int i=0; i<fds.length; i++){
				FieldDescriptor fd = fds[i];
				try{
					fd.setInputter(ActivityDescriptor.getDefaultInputter(fd.getClassType()));
				}catch(Exception e){
				}
			}
		}
	}*/
	public Class getCls() {
		return cls;
	}

	public void setCls(Class class1) {
		cls = class1;
	}
	
	
	ImageIcon icon;
	
	@Override
	public void setBorder(Border border) {
		if(border instanceof IconTitledBorder){
			IconTitledBorder iconBorder = (IconTitledBorder)border;
			icon = iconBorder.icon;
			title = iconBorder.getTitle();
			
			add("North", new JLabel( title));
		}else
			super.setBorder(border);
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//BasicStroke stroke = new BasicStroke(2, 1, 1, 1, new float[]{4f,4f}, 3);
		Stroke stroke = new BasicStroke(0.5f);

		g2d.setStroke(stroke);
		g2d.setColor(new Color(100, 100, 200));

		g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10); 
		
		icon.paintIcon(this, g2d, 5, 0);
		
		//g2d.drawString(title, 10, 0);
		g2d.dispose();
	}
	
	class InformationValueLabel extends JLabel implements DropTargetListener {
		Object value;
		
		public InformationValueLabel(Object value, Icon icon, int alignment){
			super(getLabel(value), icon, alignment);
			this.value = value;
			
			new DropTarget(this, 
					DnDConstants.ACTION_COPY_OR_MOVE,
					this);

		}

		public void dragEnter(DropTargetDragEvent dtde) {
			
		}

		public void dragOver(DropTargetDragEvent dtde) {
			
		}

		public void dropActionChanged(DropTargetDragEvent dtde) {
			
		}

		public void dragExit(DropTargetEvent dte) {
			
		}

		public void drop(DropTargetDropEvent e) {
			try {
				DataFlavor listFlavor = DataFlavor.javaFileListFlavor;
				Transferable tr = e.getTransferable();
				
//				System.out.println(tr);
				java.util.List list = (java.util.List)tr.getTransferData(listFlavor);
				
				Object objectGot = list.get(0);
				
				if(objectGot instanceof Role && value instanceof Role && list.size()>1){
					ProcessDefinitionDesigner processDefinitionDesignerFrom = (ProcessDefinitionDesigner)list.get(1);
					Role droppedRole = (Role) objectGot;
					
					if(processDefinitionDesignerFrom!=getProcessDefinitionDesigner()){
						boolean connectedFromMain = true;
						
						try{
							connectedFromMain = !(processDefinitionDesignerFrom.getParentDesigner().getActivity().getProcessDefinition() == processDefinition);
						}catch(Exception ex){
						}
						
						SubProcessActivity subProcAct = null;
						ProcessDefinition mainProcessDefinition, subProcessDefinition;
						Role subRole, mainRole;
						if(connectedFromMain){
							subProcAct = (SubProcessActivity) getSubProcessDesigner(getProcessDefinitionDesigner()).getActivity();
							subProcessDefinition = (ProcessDefinition) getProcessDefinitionDesigner().getActivity();
							subRole = (Role)value;
							mainRole = droppedRole;
						}else{
							subProcAct = (SubProcessActivity) getSubProcessDesigner(processDefinitionDesignerFrom).getActivity();
							subProcessDefinition = (ProcessDefinition) processDefinitionDesignerFrom.getActivity();
							subRole = droppedRole;
							mainRole = (Role)value;
						}
						mainProcessDefinition = subProcAct.getProcessDefinition();
						
						RoleParameterContext roleParamContext = new RoleParameterContext();
						roleParamContext.setArgument(subRole.getName());
						roleParamContext.setRole(mainRole);
						
						RoleParameterContext[] newRPC = (RoleParameterContext[]) UEngineUtil.addArrayElement(subProcAct.getRoleBindings(), roleParamContext);
						subProcAct.setRoleBindings(newRPC);
					}
					
				}				
			} catch(UnsupportedFlavorException ufe) {
				ufe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}			
		}
		
		private SubProcessActivityDesigner getSubProcessDesigner(ProcessDefinitionDesigner designer){
			Container parent = designer.getComponent().getParent();
			
			while(!(parent instanceof ActivityDesigner) && parent!=null){
				parent = parent.getParent();
			}
			
			return (SubProcessActivityDesigner)parent;
		}

		
	}

}

/*class NewBorder extends TitledBorder {
	
	ImageIcon icon;
	public NewBorder(Border border, String title, ImageIcon icon){
		super(title);
		this.icon = icon;
	}
	
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		
		super.paintBorder(c, g, x, y, width, height);
		Insets insets = getBorderInsets(c);
		
		int tileW = icon.getIconWidth()/2;
        int tileH = icon.getIconHeight()/2;
        g.translate(x-tileW, y);
        
        // Paint Icon
        Graphics cg;
        cg = g.create();
        cg.setClip(0, 0, width, insets.top);
        icon.paintIcon(c, cg, x, y);
        cg.dispose();
        g.translate(x+tileW, y);
        
		
    }
}*/

