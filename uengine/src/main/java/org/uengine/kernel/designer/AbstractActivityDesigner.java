package org.uengine.kernel.designer;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;

import org.metaworks.Instance;
import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ValidationContext;
import org.uengine.kernel.descriptor.ActivityDescriptor;
import org.uengine.processdesigner.ActivityDesigner;
import org.uengine.processdesigner.ActivityDesignerListener;
import org.uengine.processdesigner.ActivityInputDialog;
import org.uengine.processdesigner.ActivityInputForm;
import org.uengine.processdesigner.ActivityRecord;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.SimulatorActivityDesignerListener;
import org.uengine.util.UEngineUtil;

/**
 * @author Jinyoung Jang
 */

public abstract class AbstractActivityDesigner extends JPanel implements ActivityDesigner, DropTargetListener {
	
	public static final int ACTIVITY_WIDTH = 100;
	public static final int ACTIVITY_HEIGHT = 100;
	public static final boolean isVertical = false;

	protected JPopupMenu popup = new JPopupMenu();
	
	JMenuItem deletePopupMenu = new JMenuItem("Delete");
	JMenuItem copyPopupMenu = new JMenuItem("Copy");
	JMenuItem pastePopupMenu = new JMenuItem("Paste");
	
	static boolean collapsed = false;
	
	abstract protected void toggle();

	static boolean isDebugger = false;
		public static boolean isDebugger() {
			return isDebugger;
		}	
		public static void setDebugger(boolean b) {
			isDebugger = b;
		}
	Hashtable inputFormDialogClasses = new Hashtable();

	Activity activity;
		public Activity getActivity() {
			return activity;
		}
		public void setActivity(Activity value) {
			activity = value;
			refreshActivity();
		}
		public void refreshActivity(){
			if(getActivity()!=null && getActivity().getName()!=null)
				setText(getActivity().getName().getText());
			validateActivity();	
		}
		
	final static int ERRORSTATE_NORMAL = 0;
	final static int ERRORSTATE_WARN = 1;
	final static int ERRORSTATE_ERROR = 2;
	
	int errorState = ERRORSTATE_NORMAL;
		public int getErrorState() {
			return errorState;
		}
		public void setErrorState(int errorState) {
			this.errorState = errorState;
//			revalidate();
			repaint();
		}

	//borders to sign whether the activity configuration is valid.
	Border errorBorder;
	Border warningBorder;
	Border orgBorder;
	//
		
	public AbstractActivityDesigner(){
		super();

		deletePopupMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ActivityDesignerListener.getInstance().deleteSelectedActivity();
			}
		});
		popup.add(deletePopupMenu);

		copyPopupMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ActivityDesignerListener.getInstance().copySelectedActivity();
			}
		});
		popup.add(copyPopupMenu);

		pastePopupMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ActivityDesignerListener.getInstance().pasteSelectedActivity();
			}
		});
		popup.add(pastePopupMenu);

		new DropTarget(this, 
				DnDConstants.ACTION_COPY_OR_MOVE,
				this);


		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		setBackground(Color.WHITE);
		
		//review:
		if(!isDebugger){		
			ActivityDesignerListener activityComponentListener = ActivityDesignerListener.getInstance();		
			addKeyListener(activityComponentListener);	
			addMouseListener(activityComponentListener);
			//addMouseMotionListener(activityComponentListener);
		}else{
			SimulatorActivityDesignerListener activityComponentListener = new SimulatorActivityDesignerListener();		
			addMouseListener(activityComponentListener);
		}
		
		orgBorder = getBorder();
		errorBorder = new javax.swing.plaf.basic.BasicBorders.ButtonBorder(Color.RED,Color.RED,Color.RED,Color.RED);
		errorBorder = BorderFactory.createTitledBorder(errorBorder, "err");
		warningBorder = new javax.swing.plaf.basic.BasicBorders.ButtonBorder(Color.GREEN,Color.GREEN,Color.GREEN,Color.GREEN);
		warningBorder = BorderFactory.createTitledBorder(warningBorder, "warn");
		
/*		DragSource dragSource = DragSource.getDefaultDragSource();

		dragSource.createDefaultDragGestureRecognizer(
				this, // component where drag originates
				DnDConstants.ACTION_COPY_OR_MOVE, // actions
				this); // drag gesture recognizer
*/
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON3){
					copyPopupMenu.setEnabled(ActivityDesignerListener.getInstance().getSelectedComponents().size()>0);
					pastePopupMenu.setEnabled(ActivityDesignerListener.getInstance().getActivityInClipboard() != null);
					
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			
		});


	}
	
	abstract public void setText(String msg);
	abstract public void setStatus(String status);
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Component symbolicComponent = getSymbolicComponent();
		Component container = symbolicComponent;
		
		int relativeX = container.getX() + 4;
		int relativeY = container.getY() + 4;
		
		while(container!= null && container != this){
			relativeX = relativeX + container.getX();
			relativeY = relativeY + container.getY();
			container = container.getParent();
		}
		
		if(getErrorState()==ERRORSTATE_WARN){
			java.net.URL imgURL = getClass().getClassLoader().getResource("org/uengine/kernel/images/Warning.gif");
			g.drawImage(new ImageIcon(imgURL).getImage(), 0,0,13,13, this);
		}else if(getErrorState()==ERRORSTATE_ERROR){
			//setForeground(Color.RED);
			//g.drawRect(0,0, 10, 10);
			java.net.URL imgURL = getClass().getClassLoader().getResource("org/uengine/kernel/images/error_red.gif");
			g.drawImage(new ImageIcon(imgURL).getImage(), relativeX, relativeY,13,13, this);
			
		}
		
		if(ActivityDesignerListener.getSelectedComponents().contains(this) && !(this instanceof ProcessDefinitionDesigner)){
			//this.setBorder()
//			this.setBorder(BorderFactory.createRaisedBevelBorder());
			Graphics2D g2 = (Graphics2D) g;
			Stroke stroke = new BasicStroke(1, 1, 1, 1, new float[]{4f,2f}, 3);
			g2.setStroke(stroke);
			//g2.setStroke(stroke);
			g2.setColor(new Color(255, 0, 0));
			g2.drawRoundRect(2, 2, this.getWidth()-5, this.getHeight()-5, 8, 8);
		//	g2.dispose();
		}
	}

	public synchronized void onDropped(Vector activityDesigners){
System.out.println("onDragged");

		//validation first		
		for(Enumeration enumeration = activityDesigners.elements(); enumeration.hasMoreElements(); ){
			Component comp = (Component)enumeration.nextElement();
			
			if(comp==this){
				System.out.println("what?");
				return;
			}		
			if(comp instanceof Container)
				if(((Container)comp).isAncestorOf(this)){
				System.out.println("crazy?");
				return;
			}
		}
		
		for(Enumeration enumeration = activityDesigners.elements(); enumeration.hasMoreElements(); ){
			ActivityDesigner designer = (ActivityDesigner)enumeration.nextElement();			
			ComplexActivityDesigner parentDesigner = getParentDesigner();
			
/*System.out.println("  parent::  "+parent);			
System.out.println("  comp::  "+comp);	*/
	
			int movingCompIndex = 0;
				if(designer.getParentDesigner()==parentDesigner){
					movingCompIndex = designer.getParentDesigner().indexOf(designer);					
				}
			
			if(designer.getParentDesigner()!=null)
				designer.getParentDesigner().removeActivity(designer);

			int index = ((ComplexActivityDesigner)parentDesigner).indexOf(this);
			if(movingCompIndex <= index) index++; //if user dragged the activity downward, insert it into the next to the focused activity.

			parentDesigner.addActivity(designer, index);
			
			Component parentComp = parentDesigner.getComponent();
			if(parentComp instanceof JComponent)
				((JComponent)parentComp).revalidate();
			else
				parentComp.validate();			
		}
		
		validateActivity();
		revalidate();
	}
	
	public ComplexActivityDesigner getParentDesigner(){
		Container parent = super.getParent();
		
		while(!(parent instanceof ComplexActivityDesigner) && parent!=null){
			parent = parent.getParent();
		}
		
		return (ComplexActivityDesigner)parent;
	}

	public void openDialog(){
		try{
			final ActivityDesigner fThis = this;
			
			ActivityDescriptor activityTable;
			
			Class activityTableCls = null;
			Class activityCls = getActivity().getClass();
			Class orgActivityCls = activityCls;
			
			do{
				String descriptorClsName = UEngineUtil.getComponentClassName(activityCls, "descriptor");
				String activityClsName = UEngineUtil.getClassNameOnly(activityCls);

				if(inputFormDialogClasses.containsKey(activityCls)){
					activityTableCls = (Class)inputFormDialogClasses.get(activityCls);
					
					if(activityCls!=orgActivityCls)
						inputFormDialogClasses.put(orgActivityCls, activityTableCls);			
				}else{
					try{
						activityTableCls = Class.forName(descriptorClsName);
						inputFormDialogClasses.put(orgActivityCls, activityTableCls);			
					}catch(ClassNotFoundException e){
						e.printStackTrace();
					}
				}
				activityCls = activityCls.getSuperclass();
			}while(activityTableCls==null && activityCls!=Object.class);
			
			if(activityCls == Object.class){
				activityTable = new ActivityDescriptor();
				activityTable.setActivityClass(getActivity().getClass());
			}else{
				activityTable = (ActivityDescriptor)activityTableCls.newInstance();
				activityTable.setActivityClass(orgActivityCls);
			}

			try{
				activityTable.initialize(ProcessDesigner.getInstance(), getActivity());
			}catch(Exception e){
				System.out.println("Something wrong with the customized descriptor:");
				e.printStackTrace();
				if(activityCls != Activity.class){
					activityTable = new ActivityDescriptor();
					activityTable.setActivityClass(getActivity().getClass());
					activityTable.initialize(ProcessDesigner.getInstance(), null);
				}
			}
			
			Instance actRec = new ActivityRecord(activityTable, getActivity());

			final ActivityInputForm activityPropertyInputForm = new ActivityInputForm(actRec.getType()){
				public void onSaveOK(Instance rec, JDialog dialog){
					refreshActivity();
					validateActivity();
					//dialog.dispose();
				}					
				public void onUpdateOK(Instance rec, JDialog dialog){
					onSaveOK(rec, dialog);
				}
			};
			
			activityPropertyInputForm.setInstance(actRec);
			
//System.out.println("\n\nACTREC:"+actRec);
			//testForm2.postInputDialog(ProcessDesigner.getInstance(), "Apply", "Apply", "Edit " + UEngineUtil.getClassNameOnly(orgActivityCls));
			
			
			//JDialog dialog=new ActivityInputDialog(activityPropertyInputForm, orgActivityCls);			
			//dialog.show();
			//
			
			JPanel propertyPanel = ActivityInputDialog.createPanel(
				activityPropertyInputForm, 
				null, 
				GlobalContext.getLocalizedMessage("activity.dialog.apply", "Apply"),
				GlobalContext.getLocalizedMessage("activity.dialog.apply", "Apply"), 
				GlobalContext.getLocalizedMessage("activity.dialog.cancel", "Cancel")
			);
		
			if(ProcessDesigner.getInstance()!=null){
				ProcessDesigner.getInstance().getPropertyPanel().removeAll();
				ProcessDesigner.getInstance().getPropertyPanel().add("Center", propertyPanel);//dialog.getContentPane());
				
				ProcessDesigner.getInstance().getPropertyDialog().setEnabled(true);
				
				ProcessDesigner.getInstance().getPropertyDialog().pack();
				
				
				if(ProcessDesigner.getInstance().getPropertyDialog()!=null)
					ProcessDesigner.getInstance().getPropertyDialog().setTitle(UEngineUtil.getClassNameOnly(getActivity().getClass().getName()));
				
				refreshActivity();

				ProcessDesigner.getInstance().getPropertyPanel().revalidate();				
			}else{
				JDialog propertyDialog = new JDialog();
				propertyDialog.getContentPane().setLayout(new BorderLayout());
				propertyDialog.getContentPane().add("Center", propertyPanel);
				propertyDialog.pack();
				propertyDialog.show();
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public Component getComponent(){
		return this;
	}

	public void validateActivity(){
		if(getActivity()!=null && !"PMT".equals(System.getProperty("designerMode"))){
			HashMap validationOption = new HashMap();
			validationOption.put(ValidationContext.OPTIONKEY_DISABLE_REPLICATION, true);
			
			ValidationContext valCtx = getActivity().validate(validationOption);
			
			if(valCtx.size()>0){
				Border border;
				if(valCtx.isWarning()){
					setErrorState(ERRORSTATE_WARN);
					border = warningBorder;
				}else{
					setErrorState(ERRORSTATE_ERROR);
					border = errorBorder;
				}
				
				//repaint();
				updateUI();
				revalidate();
				
				//((JComponent)getComponent()).setBorder(border);
				
				
			}else
				setErrorState(ERRORSTATE_NORMAL);
			//else
				//((JComponent)getComponent()).setBorder(orgBorder);
		}
	}

	public void setProcessInstance(ProcessInstance instance) {
		try{
			String status = instance.getStatus(getActivity().getTracingTag());
			setStatus(status);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Object getTransferData(DataFlavor flavor)
		throws UnsupportedFlavorException, IOException {
		return this;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{new DataFlavor(ActivityDesigner.class,"activityDesigner")};		
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return true;
	}

	protected Component getSymbolicComponent(){
		return this;
	}
	
	/**
	 * drag & drop framework implementation
	 */
	
	/*public void dragGestureRecognized(DragGestureEvent e) {
		e.startDrag(DragSource.DefaultCopyDrop, // cursor
			(Transferable) this,
			this);  // drag source listener
	}
	public void dragDropEnd(DragSourceDropEvent e) {}
	public void dragEnter(DragSourceDragEvent e) {}
	public void dragExit(DragSourceEvent e) {}
	public void dragOver(DragSourceDragEvent e) {}
	public void dropActionChanged(DragSourceDragEvent e) {}

*/	
	/**
	 * end of d&d framework impl.
	 */
	
	
	public Vector<Point> getPointsOfContacts(){
		return null;
	}
	
	
	/**
	 * start of implementation of DropTargetListener
	 */
	public void drop(DropTargetDropEvent e) {
		try {
			DataFlavor listFlavor = DataFlavor.javaFileListFlavor;
			Transferable tr = e.getTransferable();
			
			java.util.List list = (java.util.List)tr.getTransferData(listFlavor);
			Object activityClassObj = list.get(0);
			
			if(activityClassObj!=null && activityClassObj instanceof Class){
				Class activityClass = (Class)activityClassObj;
				
				Activity activity = (Activity) activityClass.newInstance();
				ActivityDesigner actDesigner = activity.createDesigner();
				
				Vector designerToAdd = new Vector();
				designerToAdd.add(actDesigner);
				
				onDropped(designerToAdd);
				
				e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);	
				e.dropComplete(true);

			}
			
		} catch(UnsupportedFlavorException ufe) {
			ufe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException exx) {
			exx.printStackTrace();
		}
	}
	public void dragEnter(DropTargetDragEvent e) { }
	public void dragExit(DropTargetEvent e) { }
	public void dragOver(DropTargetDragEvent e) { }
	public void dropActionChanged(DropTargetDragEvent e) { }		

}

