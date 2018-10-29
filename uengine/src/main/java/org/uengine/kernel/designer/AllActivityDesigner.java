package org.uengine.kernel.designer;

import javax.swing.*;

import org.uengine.processdesigner.Separator;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.SequenceActivity;
import org.uengine.kernel.SwitchActivity;
import org.uengine.processdesigner.ActivityDesigner;
import org.uengine.processdesigner.ActivityDesignerListener;
import org.uengine.processdesigner.ActivityLabel;
import org.uengine.processdesigner.ArrowLabel;
import org.uengine.processdesigner.ArrowReceiver;
import org.uengine.processdesigner.HorizontalSeparator;
import org.uengine.processdesigner.PlaceHolder;
import org.uengine.processdesigner.ProxyPanel;
import org.uengine.processdesigner.VerticalSeparator;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.*;


/**
 * @author Jinyoung Jang
 */

public class AllActivityDesigner extends ComplexActivityDesigner{

	JPanel startArrowPanel;
	JPanel endArrowPanel;

	Component firstPlaceHolder, secondPlaceHolder;
	
	Component start;
	Component end;
	Hashtable arrowVectors = new Hashtable();
	
	public AllActivityDesigner(){
		super();	

	}
	
	protected void initialize(){
		if(isVertical) //isVertical
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	
		else{
			FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 0, 2);
			setLayout(fl);
		}
							
//		add(new Separator(!isVertical));
		
/*		JPanel spacer = new JPanel(); 
		spacer.setPreferredSize(new Dimension(20,1)); 
		add(spacer);
*/		
		//This line make dicision how the child activities are aligned.
		
		centerPanel = new ProxyPanel(){
			@Override
			public void removeAll() {
				super.removeAll();
				centerPanel.add(firstPlaceHolder);
				centerPanel.add(secondPlaceHolder);
			}

		};
		
		if(isVertical) {
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		} else {
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		}
		
/*		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.ipadx=10;
		c.ipady=2;
		
		centerPanel = new ProxyPanel(new GridBagLayout()){
			public Component add(Component comp, int index){
				
				
				return super.add(comp);
			}
		};*/
		
		ProxyPanel firstPlaceHolder = new ProxyPanel();{
			PlaceHolder innerHolder = new PlaceHolder(new Dimension(50,40), false){

				@Override
				public void onDropped() {
					super.onDropped();
					if(ActivityDesignerListener.getSelectedComponents()!=null && ActivityDesignerListener.getSelectedComponents().size()>0)
						AllActivityDesigner.this.onDropped(ActivityDesignerListener.getSelectedComponents());
				}
				
			};
			//innerHolder.setAlwaysDrawRegion(true);
			innerHolder.setText("drop here");
			
			firstPlaceHolder.setLayout(new BorderLayout(0, 0));
			firstPlaceHolder.add("West", new ArrowLabel());
			firstPlaceHolder.add("Center", innerHolder);
			firstPlaceHolder.add("East", new ArrowLabel());
			this.firstPlaceHolder = firstPlaceHolder;
		}
		
		ProxyPanel secondPlaceHolder = new ProxyPanel();{
			secondPlaceHolder.setLayout(new BorderLayout(0, 0));
			secondPlaceHolder.add("West", new ArrowLabel());
			
			ProxyPanel innerPanel = new ProxyPanel(new BorderLayout(0,0));
			innerPanel.add("Center", new HorizontalSeparator());
			
/*			if(this instanceof SwitchActivityDesigner){
				innerPanel.add("West", new JLabel("otherwise"));
			}
*/			
			secondPlaceHolder.add("Center", innerPanel);
			secondPlaceHolder.add("East", new ArrowLabel());
			this.secondPlaceHolder = secondPlaceHolder;
		}
		
		add("Center", centerPanel);
//		add(new Separator(!isVertical)); 
     		
/*		JPanel spacer = new JPanel(); 
		spacer.setPreferredSize(new Dimension(20,1)); 
		add(spacer); 
*/	
		if(this.getClass().equals(AllActivityDesigner.class))
			add(new ActivityLabel(org.uengine.kernel.AllActivity.class), 0);

		
	}
	

	class ArrowReceiverPanel extends ProxyPanel implements ArrowReceiver{
	};
	
	protected JPanel boxComponent(final ActivityDesigner designer){
		Component comp = designer.getComponent();
		JPanel compPanel;		 	
		 	
		ProxyPanel innerPanel= new ProxyPanel();
		innerPanel.setArrowReceiveOrNot(!(designer instanceof ComplexActivityDesigner));
		
		if (isVertical) {
			innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
			innerPanel.add(comp);
			innerPanel.add(new Separator(true));
		} else {
			innerPanel.setLayout(new BorderLayout(0, 0));

			innerPanel.add("West", comp);
			innerPanel.add("Center", new HorizontalSeparator());
			innerPanel.add("South", new PlaceHolder(new Dimension(30,5), true){
				public void onDropped() {
					setSelected(false);

					Vector selectedComps = ActivityDesignerListener
						.getSelectedComponents();
					
					if (selectedComps != null) {
						int where = designer.getParentDesigner().indexOf(
								designer);
						
						insertActivityDesigners(selectedComps, where+1);
					}
				}
			});
			innerPanel.add("North", new PlaceHolder(new Dimension(30,5), true){
				public void onDropped() {
					setSelected(false);

					Vector selectedComps = ActivityDesignerListener
						.getSelectedComponents();
					
					if (selectedComps != null) {
						int where = designer.getParentDesigner().indexOf(
								designer);
						
						insertActivityDesigners(selectedComps, where);
					}
				}
			});
		}

		ArrowLabel startArrowLabel = new ArrowLabel() {
			public void onDropped() {
				Vector selectedComps = ActivityDesignerListener
						.getSelectedComponents();
				if (selectedComps != null) {

					if (designer instanceof SequenceActivityDesigner) {
						((SequenceActivityDesigner) designer)
								.insertActivityDesigners(selectedComps, 0);
					} else {
						// ActivityDesigner droppingActivity =
						// ((ActivityDesigner)selectedComps.elementAt(0));
						SequenceActivity wrapperActivity = new SequenceActivity();
						SequenceActivityDesigner wrapperActivityDesigner =
							(SequenceActivityDesigner) wrapperActivity.createDesigner();

						wrapperActivityDesigner.onDropped(selectedComps);

						int where = designer.getParentDesigner().indexOf(
								designer);
						Vector compBag = new Vector();
						compBag.add(designer);
						wrapperActivityDesigner.onDropped(compBag);

						selectedComps = new Vector();
						selectedComps.add(wrapperActivityDesigner);
						insertActivityDesigners(selectedComps, where);
					}
				}
				setSelected(false);
			}
		};
		ArrowLabel endArrowLabel = new ArrowLabel() {
			public void onDropped() {
				Vector selectedComps = ActivityDesignerListener
						.getSelectedComponents();
				if (selectedComps != null) {

					if (designer instanceof SequenceActivityDesigner) {
						designer.onDropped(selectedComps);
					} else {
						// ActivityDesigner droppingActivity =
						// ((ActivityDesigner)selectedComps.elementAt(0));
						SequenceActivity wrapperActivity = new SequenceActivity();
						ActivityDesigner wrapperActivityDesigner = wrapperActivity
								.createDesigner();

						int where = designer.getParentDesigner().indexOf(
								designer);
						Vector compBag = new Vector();
						compBag.add(designer);
						wrapperActivityDesigner.onDropped(compBag);

						wrapperActivityDesigner.onDropped(selectedComps);

						selectedComps = new Vector();
						selectedComps.add(wrapperActivityDesigner);
						insertActivityDesigners(selectedComps, where);
					}
				}
				setSelected(false);
			}
		};
		if (isVertical) {
			compPanel = new ProxyPanel();
			compPanel.setLayout(new BoxLayout(compPanel, BoxLayout.Y_AXIS));
			compPanel.add(startArrowLabel);
			compPanel.add(innerPanel);
			compPanel.add(endArrowLabel);
		} else {
			compPanel = new ProxyPanel(new BorderLayout(0, 0));
			compPanel.add("West", startArrowLabel);
			compPanel.add("Center", innerPanel);
			compPanel.add("East", endArrowLabel);
		}
		 	
		return compPanel;
	}

	protected Container getBoxingContainer(Component comp){
		return super.getBoxingContainer(comp).getParent();
	}

	@Override
	protected synchronized void insertActivityDesigners(Vector activityDesigners, int index) {
		super.insertActivityDesigners(activityDesigners, index);
		refreshPlaceHolders();
	}

	@Override
	public synchronized void removeActivity(ActivityDesigner designer) {
		super.removeActivity(designer);
		refreshPlaceHolders();
	}
	

	@Override
	public void setActivity(Activity act) {
		super.setActivity(act);
		refreshPlaceHolders();		
	}

	protected void refreshPlaceHolders(){

		ComplexActivity compAct = ((ComplexActivity)getActivity());
		int size = compAct.getChildActivities().size();
		
		if(size>0){
			centerPanel.remove(firstPlaceHolder);
		}else{
			centerPanel.add(firstPlaceHolder, 1);
		}
		
		if(size>1){
			centerPanel.remove(secondPlaceHolder);
		}else{
			centerPanel.add(secondPlaceHolder, 1);
		}
	}

	@Override
	public synchronized void onDropped(Vector components) {
		super.onDropped(components);
	}
	

}

