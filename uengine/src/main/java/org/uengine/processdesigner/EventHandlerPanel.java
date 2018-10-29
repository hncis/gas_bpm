package org.uengine.processdesigner;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.metaworks.InputDialog;
import org.metaworks.InputForm;
import org.metaworks.Instance;
import org.metaworks.ObjectInstance;
import org.metaworks.ObjectType;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.uengine.kernel.Activity;
import org.uengine.kernel.EventHandler;
import org.uengine.kernel.ScopeActivity;
import org.uengine.kernel.SequenceActivity;
import org.uengine.kernel.designer.ComplexActivityDesigner;
import org.uengine.kernel.designer.SequenceActivityDesigner;
/**
 * @author Jinyoung Jang
 */

public class EventHandlerPanel extends ProxyPanel{
	ResizableIcon evtSVGIcon;
	ProxyPanel handlerActivityPanel;
	ProxyPanel handlerIconPanel;
	
	ScopeActivity scopeActivity;
		public ScopeActivity getScopeActivity() {
			return scopeActivity;
		}
		public void setScopeActivity(ScopeActivity scopeActivity) {
			this.scopeActivity = scopeActivity;
			
			EventHandler[] eventHandlers = scopeActivity.getEventHandlers();
			if(eventHandlers!=null)
			for(int i=0; i<eventHandlers.length; i++){
				Activity handlerActivity = eventHandlers[i].getHandlerActivity();				
				ActivityDesigner designer = handlerActivity.createDesigner();
				handlerActivityPanel.add(boxComponent(designer, eventHandlers[i]));				
			}
		}
		
	public EventHandlerPanel(){
		
		super();
		
		setLayout(new BorderLayout());
		
		
		handlerIconPanel = new ProxyPanel();
		
		add("West", handlerIconPanel);
		
		handlerActivityPanel = new ProxyPanel();
		handlerActivityPanel.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				ProcessDesigner.getInstance().getPropertyPanel().removeAll();
				
				ObjectType eventHandlerType;
				try {
					
					
					//find out which handler is selected. Either I know this is not that good code ;)
					int handlerIndex = getScopeActivity().getEventHandlers().length - e.getX() / 32;
					EventHandler theEventHandler = getScopeActivity().getEventHandlers()[handlerIndex];
					//
					
					eventHandlerType = new ObjectType(EventHandler.class);
					
					ObjectInstance eventHandlerInstance = (ObjectInstance)eventHandlerType.createInstance();
					eventHandlerInstance.setObject(theEventHandler);
					
					InputForm eventHandlerInputForm = new InputForm(eventHandlerType){
						public void onSaveOK(Instance rec, JDialog dialog){
							
						}					
						public void onUpdateOK(Instance rec, JDialog dialog){
							onSaveOK(rec, dialog);
						}
					};
									
					eventHandlerInputForm.setInstance(eventHandlerInstance);
				
					JDialog dialog=new InputDialog(eventHandlerInputForm, ProcessDesigner.getInstance()){
						//do not clear the form
						public void onSaveOK(Instance rec){
							getInputForm().onSaveOK(rec, this);
						}
					};
					
					JPanel propertyPanel = InputDialog.createPanel(
							eventHandlerInputForm, 
							null, 
							"Apply", 
							"Apply", 
							"Cancel" 
						);
					//dialog.show();
					//				
					ProcessDesigner.getInstance().getPropertyPanel().add("Center", propertyPanel);
					ProcessDesigner.getInstance().getPropertyPanel().revalidate();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}});
		
		handlerActivityPanel.setLayout(new BoxLayout(handlerActivityPanel, BoxLayout.Y_AXIS));
		handlerActivityPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		//setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		//setBorder(BorderFactory.createTitledBorder("event handlers"));

		//setBackground(Color.LIGHT_GRAY);
		add("Center", handlerActivityPanel);
		
		handlerActivityPanel.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				
			}

			public void mouseEntered(MouseEvent e) {
				if(!ActivityDesignerListener.bDragging) return;
				
				handlerActivityPanel.setBackground(Color.GRAY);
				ActivityDesignerListener.focusedComponent = (Component)e.getSource();
			}

			public void mouseExited(MouseEvent e) {
				handlerActivityPanel.setBackground(Color.WHITE);

				ActivityDesignerListener.focusedComponent = null;
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				if(ActivityDesignerListener.bDragging){
					Vector selectedComps = ActivityDesignerListener.getSelectedComponents();

					onDropped(selectedComps);
				}
				
				ActivityDesignerListener.bDragging = false;
			}
		});
		
		
		//JPanel labelPanel = new ProxyPanel(new FlowLayout());
		//labelPanel.add(new JLabel("<event handlers>"));
		//add(labelPanel);
		
/*		URL eventIconResourceUrl = getClass().getClassLoader().getResource("org/uengine/kernel/images/svg/event.svg");
		if(eventIconResourceUrl != null) {
			evtSVGIcon = SvgBatikResizableIcon.getSvgIcon(
				eventIconResourceUrl, new Dimension(32, 32));
		}
*/
	}
	
	public void onDropped(Vector activityDesigners){
		for(Enumeration enumeration = activityDesigners.elements(); enumeration.hasMoreElements(); ){
			ActivityDesigner activityDesigner = (ActivityDesigner)enumeration.nextElement();
			
			ComplexActivityDesigner oParent = activityDesigner.getParentDesigner();

			if(oParent!=null){  
				oParent.removeActivity(activityDesigner);
				((JComponent)oParent.getComponent()).revalidate();
			}

			EventHandler newEventHandler;{
				EventHandler[] evs = (getScopeActivity()).getEventHandlers();
				EventHandler newEvs[];
				if(evs==null){
					newEvs = new EventHandler[1];
				}else{
					newEvs = new EventHandler[evs.length + 1];				
					System.arraycopy(evs, 0, newEvs, 0, evs.length);
				}
				
				newEventHandler = new EventHandler();
				newEventHandler.setHandlerActivity(activityDesigner.getActivity());
				newEventHandler = new EventHandler();
				newEventHandler.setHandlerActivity(activityDesigner.getActivity());
				newEventHandler.setName("eventHandler#");
				newEvs[newEvs.length-1] = newEventHandler;
				
				getScopeActivity().setEventHandlers(newEvs);
			}
			
			handlerActivityPanel.add(boxComponent(activityDesigner, newEventHandler));
		}
		
		handlerIconPanel.setPreferredSize(new Dimension((getScopeActivity()).getEventHandlers().length * 32, 1));

		revalidate();
		
	}
	
	protected Component boxComponent(final ActivityDesigner designer, final EventHandler theEventHandler){
		JPanel panel = new ProxyPanel(new BorderLayout());
		//panel.setBackground(Color.LIGHT_GRAY);
		
//		JLabel ehl =  new DesignerLabel(DesignerLabel.EVENTHANDLER);
//		ehl.setVerticalAlignment(JLabel.TOP);
		
		JPanel eventIconPanel = new ProxyPanel()/*{
			public void paint(Graphics g) {
				super.paint(g);
				
				Graphics2D g2 = (Graphics2D) g;
				
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				g2.setColor(new Color(150, 150, 150));
				Stroke stroke = new BasicStroke(1.5f);
				stroke = new BasicStroke(1.3f,0,0,4.0f,null,0.0f);
				g2.setStroke(stroke);
				
				GeneralPath path = new GeneralPath();		
				
				int centerX = getX() + getWidth() / 2;

				path.moveTo(centerX, 0);
				path.lineTo(centerX, getHeight()/2);
				path.lineTo(getX() + getWidth(), getHeight()/2);

				
				if(evtSVGIcon != null) {
					evtSVGIcon.paintIcon(this, g2, 0, 0);
				}
				
				g2.dispose();
			}
		}*/;
		
		//eventIconPanel.setPreferredSize(new Dimension(32*,1));
		
		
		
		/*eventIconPanel.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				ProcessDesigner.getInstance().getPropertyPanel().removeAll();
				
				ObjectType eventHandlerType;
				try {
					eventHandlerType = new ObjectType(EventHandler.class);
					
					ObjectInstance eventHandlerInstance = (ObjectInstance)eventHandlerType.createInstance();
					eventHandlerInstance.setObject(theEventHandler);
					
					InputForm eventHandlerInputForm = new InputForm(eventHandlerType){
						public void onSaveOK(Instance rec, JDialog dialog){
							
						}					
						public void onUpdateOK(Instance rec, JDialog dialog){
							onSaveOK(rec, dialog);
						}
					};
									
					eventHandlerInputForm.setInstance(eventHandlerInstance);
				
					JDialog dialog=new InputDialog(eventHandlerInputForm, ProcessDesigner.getInstance()){
						//do not clear the form
						public void onSaveOK(Instance rec){
							getInputForm().onSaveOK(rec, this);
						}
					};
					
					JPanel propertyPanel = InputDialog.createPanel(
							eventHandlerInputForm, 
							null, 
							"Apply", 
							"Apply", 
							"Cancel" 
						);
					//dialog.show();
					//				
					ProcessDesigner.getInstance().getPropertyPanel().add("Center", propertyPanel);
					ProcessDesigner.getInstance().getPropertyPanel().revalidate();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}});*/
		
		//panel.add("West", eventIconPanel);
		
		
		panel.add("West", designer.getComponent());
		panel.add("Center", new PlaceHolder(new Dimension(20,20), false){

			public void onDropped() {
				super.onDropped();
				
				Vector selectedComps = ActivityDesignerListener.getSelectedComponents();
				
				if (selectedComps != null) {
					Activity act = designer.getActivity();
					selectedComps.add(0, act);
					
					SequenceActivity wrapperActivity = new SequenceActivity();
					SequenceActivityDesigner wrapperActivityDesigner =
						(SequenceActivityDesigner) wrapperActivity.createDesigner();
	
	//				wrapperActivityDesigner.onDropped(selectedComps);
	
				}
			}
			
		});
		
		return panel;
	}

	public void removeActivityDesigner(ActivityDesigner designer){
		handlerActivityPanel.remove(designer.getComponent().getParent());
		
		Activity handlerActivity = designer.getActivity();
		
		EventHandler[] evs = (getScopeActivity()).getEventHandlers();
		
		Vector v = new Vector();
		for(int i=0;i<evs.length;i++){
			v.add(evs[i]);
		}
				
		for(Enumeration enumeration = v.elements(); enumeration.hasMoreElements(); ){
			EventHandler ev = (EventHandler)enumeration.nextElement();
			if(handlerActivity==ev.getHandlerActivity()){
				v.remove(ev);
				break;
			}
		}
		
		Object[] obj = v.toArray();
		EventHandler[] newEvs = new EventHandler[v.size()];
		for(int i=0;i<obj.length;i++){
			newEvs[i]= (EventHandler)obj[i];
		}
		if(newEvs.length!=0)
			getScopeActivity().setEventHandlers(newEvs);
		else
			getScopeActivity().setEventHandlers(null);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		Stroke stroke = new BasicStroke(1.5f);
		stroke = new BasicStroke(1.3f,0,0,4.0f,null,0.0f);
		g2.setStroke(stroke);
		g2.setColor(new Color(150, 150, 150));

		if(scopeActivity!=null && 
				scopeActivity.getEventHandlers()!=null && 
				handlerActivityPanel!=null && 
				handlerActivityPanel.getComponentCount()==scopeActivity.getEventHandlers().length)
			
		for(int i=0; i<scopeActivity.getEventHandlers().length; i++){
			GeneralPath shape = new GeneralPath();
			
			int x = i*32 + 16;
			shape.moveTo(x, 0);
			
			Component unitEventHandlerPanel = handlerActivityPanel.getComponent(scopeActivity.getEventHandlers().length - i -1);
			
			int y = unitEventHandlerPanel.getY() + unitEventHandlerPanel.getHeight()/2;
			shape.lineTo(x, y-10);
			shape.curveTo(x, y-10, x, y, x+10, y);
			
			int handlerPanelX = handlerActivityPanel.getX() + 10;
			
			shape.lineTo(handlerPanelX, y);

			shape.moveTo(handlerPanelX, y);
			shape.lineTo(handlerPanelX-7, y-3);
			shape.moveTo(handlerPanelX, y);
			shape.lineTo(handlerPanelX-7, y+3);			

			g2.draw(shape);
		}
		
		g2.dispose();
	}


}