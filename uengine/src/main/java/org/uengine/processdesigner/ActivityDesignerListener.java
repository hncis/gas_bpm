package org.uengine.processdesigner;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.designer.ComplexActivityDesigner;
import org.uengine.util.ActivityForLoop;

/**
 * @author Jinyoung Jang
 */

public class ActivityDesignerListener extends MouseAdapter implements KeyListener{

	static Vector selectedComponents = new Vector();
	static Component focusedComponent;
	public static Color focusedComponentOriginalBGColor = (new Button()).getBackground();
	public static Color selectionColor = new Color(158, 187, 228);
	public static Color rollOverColor = Color.LIGHT_GRAY;
	
	public static boolean bDragging = false ;
	static boolean bShift = false ;
	static boolean bCtrl = false ;
	static ActivityDesignerListener instance = new ActivityDesignerListener(); 
	
	private ActivityDesignerListener(){
		
	
	}
	
	public static ActivityDesignerListener getInstance(){
		return instance;
	}
	
	public synchronized void mouseClicked(MouseEvent ae){
		if(bDragging) return;
	
//System.out.println(ae.getClickCount());
		if(ae.getClickCount()==1){
			Object source = ae.getSource();
			
			if(source instanceof ActivityDesigner){
				ActivityDesigner selectedComponent = (ActivityDesigner)source;				
				selectedComponent.openDialog();	
			}
		}
		
		if(ProcessDesigner.getInstance()!=null)
			ProcessDesigner.getInstance().setDocumentChanged(true);
	}
		
	public synchronized void mousePressed(MouseEvent e){
		if(e.getClickCount() > 1) {
			
			if(ProcessDesigner.getInstance().getPropertyDialog() != null)
				ProcessDesigner.getInstance().getPropertyDialog().setVisible(true);
		}
		Object source = e.getSource();

		if(source instanceof ActivityDesigner){
			Component selectedComponent = (Component)source;
					
			if(!bShift){
				for(Enumeration enumeration = selectedComponents.elements(); enumeration.hasMoreElements();){					
					((Component)enumeration.nextElement()).setBackground(focusedComponentOriginalBGColor);
				}				
				selectedComponents.clear();
			}
			
			selectedComponents.add(source);
			//selectedComponent.setBackground(selectionColor);
		}
		
		if(getSelectedComponents().contains(e.getSource())){		
			bDragging = true;
			//ProcessDesigner.getInstance().getGlassPane().setVisible(true);

//			((Component)e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}		
		
		((Component)e.getSource()).requestFocus(); //make sure that selected component is got focused
	}

	public synchronized void mouseReleased(MouseEvent e){
		
		
		if(bDragging){
System.out.print(bDragging);				

/*			if(ProcessDesigner.getInstance()!=null)
				ProcessDesigner.getInstance().saveDefinitionForUndo();
*/			
//			((Component)e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			if(focusedComponent!=null){
				if(focusedComponent instanceof ActivityDesigner){
				   ((ActivityDesigner)focusedComponent).onDropped(getSelectedComponents());
				}else if(focusedComponent instanceof ArrowLabel){
					if(bDragging) ((ArrowLabel)focusedComponent).onDropped();
				}else{/* if(focusedComponent instanceof EventHandlerPanel){
					if(bDragging) ((EventHandlerPanel)focusedComponent).onDropped(getSelectedComponents());*/
					
					MouseListener[] mls = focusedComponent.getMouseListeners();
					for(int i=0; i<mls.length; i++){
						mls[i].mouseReleased(e);
					}
				}
			}
			
			bDragging = false;
			//ProcessDesigner.getInstance().getGlassPane().setVisible(false);
		}
		
		if(ProcessDesigner.getInstance()!=null)
			ProcessDesigner.getInstance().setDocumentChanged(true);
		
		
//		ProcessDesigner.getInstance().getProcessDefinitionDesigner().revalidate();

	}			


	public void mouseEntered(MouseEvent e){
//	System.out.println(e);
		if(e.getSource() instanceof ActivityDesigner){
	  		focusedComponent = (Component)e.getSource();
	  		//focusedComponentOriginalBGColor = focusedComponent.getBackground();
	  		//focusedComponent.setBackground(rollOverColor);
		}
		
//		ProcessDesigner.getInstance().getProcessDefinitionDesigner().revalidate();

	}
	
	public void mouseExited(MouseEvent e) {
/*		if(getSelectedComponents().contains(focusedComponent))
  			focusedComponent.setBackground(selectionColor);
		else{
			if(focusedComponent!=null)
		  		focusedComponent.setBackground(focusedComponentOriginalBGColor );
		}
*/		
//		if(focusedComponent!=null && focusedComponent instanceof JComponent) ((JComponent)focusedComponent).revalidate();

  		focusedComponent = null;

//		ProcessDesigner.getInstance().getProcessDefinitionDesigner().revalidate();
	}
	

// methods implement KeyListener
	public void keyPressed(KeyEvent e){
//System.out.println("getSelectedComponents():"+getSelectedComponents());			
		if(e.getKeyCode()==16){
			bShift = true;
		}else if(e.getKeyCode()==KeyEvent.VK_CONTROL){
			bCtrl = true;	
		}else
		// when user presses "delete"	
		if(e.getKeyCode()==127 && getSelectedComponents()!=null){//when delete key is pressed
			deleteSelectedActivity();
		}else
		// when user presses "ctrl+C"
		if(bCtrl && e.getKeyCode()==KeyEvent.VK_C && getSelectedComponents()!=null){
			copySelectedActivity();
		}else
		// when user presses "ctrl+V"
		if(bCtrl && e.getKeyCode()==KeyEvent.VK_V){
			pasteSelectedActivity();
		}
			
		if(bCtrl && e.getKeyCode()==KeyEvent.VK_Z){
			ProcessDesigner.getInstance().undo();
		}
		
		ProcessDesigner.getInstance().setDocumentChanged(true);
		//ProcessDesigner.getInstance().saveDefinitionForUndo();
	}
	
	public static void deleteSelectedActivity(){
		synchronized(getSelectedComponents())
		{ 
			for(Enumeration enumeration = getSelectedComponents().elements(); enumeration.hasMoreElements();){
				ActivityDesigner selectedComponent = (ActivityDesigner)enumeration.nextElement();
				
				ActivityDesigner parent = selectedComponent.getParentDesigner();

				if(parent!=null && parent instanceof ComplexActivityDesigner){
					((ComplexActivityDesigner)parent).removeActivity(selectedComponent);
					((ComplexActivityDesigner)parent).revalidate();
				}
			}
			getSelectedComponents().clear();
		}
	}
	
	public void copySelectedActivity(){
		if(getSelectedComponents().size()>0){
			ActivityDesigner designer = (ActivityDesigner)getSelectedComponents().elementAt(0);
			
			try{
/*				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				GlobalContext.serialize(designer.getActivity(), bao, String.class);
				Object cloned = GlobalContext.deserialize(bao.toString(GlobalContext.DATABASE_ENCODING), String.class);				
*/
				
				Activity cloned = (Activity) designer.getActivity().clone();//make a copy
				
				//designer = ((Activity)cloned).createDesigner();
				
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(cloned.createTransferrable(), ProcessDesigner.getInstance());
			}catch(Exception ex){
				ex.printStackTrace();
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(null, ProcessDesigner.getInstance());
			}
		}
	}
	
	public void pasteSelectedActivity(){
		Activity acitivtyInClipboard = getActivityInClipboard();
		
		//clear the tracing tags recursively.
		new ActivityForLoop(){
			public void logic(Activity activity) {
				activity.setTracingTag(null);
			}						
		}.run(acitivtyInClipboard);
		
		ProcessDesigner.getInstance().insertActivity(acitivtyInClipboard.createDesigner());
	
	}
	
	public Activity getActivityInClipboard(){
		Transferable tr = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);
		Object data;
		try {
			data = tr.getTransferData(new DataFlavor(Activity.class,"activityDesigner"));
			if(tr!=null && data!=null && data instanceof Activity){
				Activity acitivtyInClipboard = ((Activity)data);

				return acitivtyInClipboard;
			}
		} catch (UnsupportedFlavorException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		
		return null;
	}
	
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()==16){
			bShift = false;
		}else if(e.getKeyCode()==KeyEvent.VK_CONTROL){
			bCtrl = false;
		}
	}

	public static Vector getSelectedComponents() {
		return selectedComponents;
	}
	
	public static Component getFocusedComponent(){
		return focusedComponent;
	}
	
	static Image selectedComponentImage = null;

		public static Image getSelectedComponentImage() {
			return selectedComponentImage;
		}
	
		public static void setSelectedComponentImage(Image selectedComponentImage_) {
			selectedComponentImage = selectedComponentImage_;
		}
		
	public static Point draggingMousePoint;
	
}